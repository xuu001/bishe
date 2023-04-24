<template>
  <div>
    <el-card>
      <el-form label-position="right" :inline="true" :model="outApply" :rules="rules" ref="outApply"
               style="height: 250px">
        <el-row :gutter="20">
          <el-col :span="10">
            <el-form-item label="外出目的地" prop="destination">
              <el-autocomplete v-model="outApply.destination" :fetch-suggestions="Search" placeholder="请输入申请外出目的地"
                               :trigger-on-focus="false" @select="handleSelect"  style="width: 300px">
              </el-autocomplete>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="预计出发日期" prop="departureTime">
              <el-date-picker v-model="outApply.departureTime" align="right" type="date" placeholder="选择出发日期"
                              :picker-options="pickerOptions" value-format="yyyy-MM-dd" >
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="10">
            <el-form-item label="乘交通工具" prop="transport">
              <el-autocomplete v-model="outApply.transport" :fetch-suggestions="trans" placeholder="预计外出乘坐交通工具" style="width: 300px">
              </el-autocomplete>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="预计外出天数" prop="outTime" size="small">
              <el-input v-model="outApply.outTime" placeholder="需要外出几天" style="width: 220px;" size="large">
              </el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="10">
            <el-form-item prop="remarks" label-width="90px">
              <div slot="label" style="margin-left:25px;">备注</div>
              <el-input type="textarea" :autosize="{ minRows: 1.5, maxRows: 3}" placeholder="请输入备注内容"
                        v-model="outApply.remarks" style="width: 300px">
              </el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-form-item>
            <el-button type="primary" @click="onSubmit('outApply')" >提交</el-button>
          </el-form-item>
        </el-row>
      </el-form>
    </el-card>

    <div style="width:100%; height:295px;">
      <div  style="float:left ; width:50%; height:100%;position: relative;">
        <el-card>
          <div slot="header" style="float:left" class="clearfix">
            <span>中风险区：{{midTotal}}个</span>
          </div>
          <vue-seamless-scroll :data="midRiskArea" :class-option="optionSingleHeight" class="seamless-warp">
            <ul class="item">
              <li v-for="(item,index) in midRiskArea" :key="index" style="list-style-type: none; text-align: left">
                <span style="margin-left: -30px" v-text="item"></span>
              </li>
            </ul>
          </vue-seamless-scroll>
<!--          <div class="example-split"></div>-->
          <div slot="header" style="float:right" class="clearfix">
            <span>高风险区 {{highTotal}}个</span>
          </div>
        </el-card>
      </div>
      <div  style="float:left ; width:50%; height:100%;">
        <el-card>
          <baidu-map class="map-view" :center="mapCenter" :zoom="mapZoom" :scroll-wheel-zoom="true" ak="baidu-ak" @ready="handlerBMap">
            <bm-geolocation anchor="BMAP_ANCHOR_TOP_RIGHT" :showAddressBar="true"
                            :autoLocation="true"></bm-geolocation>
          </baidu-map>
        </el-card>
      </div>
    </div>
  </div>
</template>


<script>
import vueSeamlessScroll from 'vue-seamless-scroll'
export default {
  name: "OutApply",
  components:{vueSeamlessScroll},
  data() {
    return {
      pickerOptions: {
        disabledDate(time) {
          return time.getTime() < Date.now();
        },
        shortcuts: [{
          text: '今天',
          onClick(picker) {
            picker.$emit('pick', new Date());
          }
        }, {
          text: '明天',
          onClick(picker) {
            const date = new Date();
            date.setTime(date.getTime() + 3600 * 1000 * 24);
            picker.$emit('pick', date);
          }
        }, {
          text: '一周后',
          onClick(picker) {
            const date = new Date();
            date.setTime(date.getTime() + 3600 * 1000 * 24 * 7);
            picker.$emit('pick', date);
          }
        }]
      },
      userInfo: '',
      transportList: '',
      mapZoom: 13,
      mapCenter: {lng: 0, lat: 0},
      outApply: {
        accountId: '',
        createUserName:'',
        destination: '',
        departureTime: '',
        transport: '',
        outTime: '',
        remarks:'',
        statue:'',
      },
      midRiskArea:[],
      midTotal:'',
      highRiskArea:[],
      highTotal:'',
      rules:{
        destination: [{required: true, message: '该选项必填', trigger: 'change'}],
        departureTime: [{required: true, message: '该选项必填', trigger: 'change'}],
        transport: [{required: true, message: '该选项必填', trigger: 'change'}],
        outTime: [{required: true, message: '该选项必填', trigger: 'change'}],
      }
    }
  },
  created() {
    this.userInfo = JSON.parse(sessionStorage.getItem('userInfo'))
    this.outApply.accountId = this.userInfo.id
    this.outApply.createUserName = this.userInfo.name
    this.getData()
  },
  mounted() {
    this.transportList = this.AllTransport();

  },
  computed:{
    optionSingleHeight () {
      return {
        step: 0.5, // 数值越大速度滚动越快
        hoverStop: true, // 是否开启鼠标悬停stop
        // singleHeight: 21,  //单行滚动
        openWatch: true, // 开启数据实时监控刷新dom
      }
    }
  },
  methods: {
    onSubmit(out){
      this.$refs[out].validate(val =>{
        if (val){
          this.$axios.post('/api/out/apply',this.outApply).then(res =>{
            if (res.data.data === 1 ){
              this.$notify({
                title: '提示',
                message: "申请成功",
                type: "success",
              })
              this.$refs[out].resetFields();
            }else {
              this.$notify({
                title: '提示',
                message: res.data.message,
                type: 'error'
              })
            }
          })
        }else {
          return false
        }
      })
    },
    getData() {
      this.$axios.get('http://api.tianapi.com/ncov/index?key=d334721cf6eba2d619a5855420ec352c').then(res => {
        if (res.data.code === 200) {
          this.midRiskArea = res.data.newslist[0].riskarea.mid
          this.midTotal = this.midRiskArea.length
          this.highRiskArea = res.data.newslist[0].riskarea.high
          this.highTotal = this.highRiskArea.length
          console.log(this.midRiskArea)
          // console.log(this.highRiskArea)
        } else {
          console.log('News新闻接口失效')
        }
      })
    },
    Search(queryString, cb) {
      let that = this  // map方法中的this指向不对。所以申明一个(小细节的东西)
      let myGeo = new this.BMap.Geocoder()
      myGeo.getPoint(queryString, function (point) {
        if (point) {
          that.outApply.coordinate = point
          that.makerCenter(point)
        } else {
          that.outApply.coordinate = null
        }
      }, this.locationCity)
      let options = {
        onSearchComplete: function (results) {
          if (local.getStatus() === 0) {
            // 判断状态是否正确
            let s = []
            for (let i = 0; i < results.getCurrentNumPois(); i++) {
              let x = results.getPoi(i)
              let item = {value: x.address + x.title, point: x.point}
              s.push(item)
              cb(s)
            }
          } else {
            cb()
          }
        }
      }
      let local = new this.BMap.LocalSearch(this.map, options)
      local.search(queryString)
    },
    //el的条件赛选输入框的方法
    trans(queryString, back) {
      let transportList = this.transportList;
      let results = queryString ? transportList.filter(this.createFilter(queryString)) : transportList;
      // 调用 callback 返回建议列表的数据
      back(results);
    },
    createFilter(queryString) {
      return (transportList) => {
        return (transportList.value.toLowerCase().indexOf(queryString.toLowerCase()) === 0);
      }
    },
    makerCenter(point) { //搜索的地址返给地图
      if (this.map) {
        this.map.addOverlay(new this.BMap.Marker(point))
        this.mapCenter.lng = point.lng
        this.mapCenter.lat = point.lat
        this.mapZoom = 15
      }
    },
    handleSelect(item) {
      let {point} = item
      this.outApply.coordinate = point
      this.makerCenter(point)
    },
    handlerBMap({BMap, map}) {
      this.BMap = BMap
      this.map = map
      if (this.outApply.coordinate && this.outApply.coordinate.lng) {
        this.mapCenter.lng = this.outApply.coordinate.lng
        this.mapCenter.lat = this.outApply.coordinate.lat
        this.mapZoom = 15
      } else {
        this.mapCenter.lng = 108.728199
        this.mapCenter.lat = 34.361419
        this.mapZoom = 10
      }
    },
    AllTransport() {
      return [
        {"value": "火车"},
        {"value": "高铁"},
        {"value": "飞机"},
        {"value": "客车"},
        {"value": "自驾"},
        {"value": "自行车"},
        {"value": "公交"},
        {"value": "打的"},
        {"value": "客船"},
      ];
    }
  }
}
</script>

<style  scoped>
/deep/.el-card__header{
  border-bottom: 0
}
.map-view{
  width: 100%;
  height: 295px;
}
.clearfix:before,
.clearfix {
  font-size: 20px;
  align-items: center;
}
.clearfix:after {
  clear: both
}
.seamless-warp {
  height: 258px;
  overflow: hidden;
}
.example-split {
  display: block;
  position: absolute;
  top: 0;
  bottom: 0;
  left: 50%;
  border: 1px dashed #eee;
}
</style>
