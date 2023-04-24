<template>
  <div>
    <el-card>
      <el-form label-position="right" :inline="true" :model="trip" :rules="rules" ref="trip" style="height: auto">
        <el-row :gutter="20">
          <el-col :span="10">
            <el-form-item label="外出目的地" prop="destination" >
              <el-autocomplete v-model="trip.destination" :fetch-suggestions="Search" placeholder="请输入外出目的地"
                               :trigger-on-focus="false" @select="handleSelect" size="small" style="width: 310px">
              </el-autocomplete>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="出发日期" prop="departureTime">
              <el-date-picker v-model="trip.departureTime" align="right" type="date" placeholder="选择出发日期"
                              :picker-options="pickerOptions" value-format="yyyy-MM-dd" size="small">
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="10">
            <el-form-item label="出交通工具" prop="transport">
              <el-autocomplete v-model="trip.transport" :fetch-suggestions="trans" placeholder="请输入内容" size="small" style="width: 310px">
              </el-autocomplete>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="到达日期" prop="arrivalTime">
              <el-date-picker v-model="trip.arrivalTime" align="right" type="date" placeholder="选择出发日期"
                              :picker-options="pickerOptions" value-format="yyyy-MM-dd" size="small">
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="10">
            <el-form-item label="回交通工具" prop="transport">
              <el-autocomplete v-model="trip.returnTransport" :fetch-suggestions="trans" placeholder="请输入内容" size="small" style="width: 310px">
              </el-autocomplete>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="回抵日期" prop="returnTime">
              <el-date-picker v-model="trip.returnTime" align="right" type="date" placeholder="选择回来日期"
                              :picker-options="pickerOptions" value-format="yyyy-MM-dd" size="small">
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="10">
            <el-form-item label="风险区等级" prop="isRisk" size="small">
              <el-radio-group v-model="trip.isRisk">
                <el-radio border v-for="(item, index) in isRisk" :key="index" :label="item.value">
                  {{ item.label }}
                </el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item prop="remarks" label-width="80px">
              <div slot="label" style="margin-left:11px;">备注</div>
              <el-input type="textarea" :autosize="{ minRows: 1, maxRows: 3}" placeholder="请输入内容" v-model="trip.remarks" size="small" style="width: 220px">
              </el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-form-item>
            <el-button type="primary" @click="onSubmit('trip')" size="small">提交</el-button>
          </el-form-item>
        </el-row>
      </el-form>
    </el-card>
    <el-card>
      <baidu-map class="map-view" :center="mapCenter" :zoom="mapZoom" :scroll-wheel-zoom="true" ak="baidu-ak"
                 @ready="handlerBMap">
        <bm-geolocation anchor="BMAP_ANCHOR_TOP_RIGHT" :showAddressBar="true"
                        :autoLocation="true" ></bm-geolocation>
      </baidu-map>
    </el-card>
  </div>
</template>

<script>
export default {
  name: "Trip",
  data() {
    return {
      pickerOptions: {
        disabledDate(time) {
          return time.getTime() > Date.now();
        },
        shortcuts: [{
          text: '今天',
          onClick(picker) {
            picker.$emit('pick', new Date());
          }
        }, {
          text: '昨天',
          onClick(picker) {
            const date = new Date();
            date.setTime(date.getTime() - 3600 * 1000 * 24);
            picker.$emit('pick', date);
          }
        }, {
          text: '一周前',
          onClick(picker) {
            const date = new Date();
            date.setTime(date.getTime() - 3600 * 1000 * 24 * 7);
            picker.$emit('pick', date);
          }
        }]
      },
      trip: {
        accountId:'',
        destination: '',
        departureTime: '',
        transport: '',
        returnTransport: '',
        arrivalTime: '',
        returnTime:'',
      },
      mapZoom: 13,
      mapCenter: {lng: 0, lat: 0},
      transportList: '',
      userInfo:'',
      rules:{
        destination: [{required: true, message: '该选项必填', trigger: 'change'}],
        departureTime: [{required: true, message: '该选项必填', trigger: 'change'}],
        transport: [{required: true, message: '该选项必填', trigger: 'change'}],
        returnTransport: [{required: true, message: '该选项必填', trigger: 'change'}],
        arrivalTime: [{required: true, message: '该选项必填', trigger: 'change'}],
        returnTime: [{required: true, message: '该选项必填', trigger: 'change'}],
        isRisk:[{required: true, message: '该选项必填', trigger: 'change'}],
      },
      isRisk: [{
        "label": "不是",
        "value": 0
      }, {
        "label": "中级",
        "value": 1
      }, {
          "label": "高级",
          "value": 2
        }],
    }
  },
  created() {
    this.userInfo = JSON.parse(sessionStorage.getItem('userInfo'))
    this.trip.accountId = this.userInfo.id
  },
  mounted() {
    this.transportList = this.AllTransport();
  },
  methods: {
    onSubmit(trip){
      this.$refs[trip].validate(val =>{
        if (val){
          this.$axios.post('/api/trip/save',this.trip).then(res =>{
            console.log(res)
            if (res.data.data === 1 ){
              this.$notify({
                title: '提示',
                message: res.data.message,
                type: "success",
              })
              this.$refs[trip].resetFields();
            }else {
              this.$notify({
                title: '提示',
                message: res.data.message,
                type: 'error'
              })
            }
          })
        }
      })
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
    handleSelect(item) {
      let {point} = item
      this.trip.coordinate = point
      this.makerCenter(point)
    },
    makerCenter(point) { //搜索的地址返给地图
      if (this.map) {
        this.map.addOverlay(new this.BMap.Marker(point))
        this.mapCenter.lng = point.lng
        this.mapCenter.lat = point.lat
        this.mapZoom = 15
      }
    },
    handlerBMap({BMap, map}) {
      this.BMap = BMap
      this.map = map
      if (this.trip.coordinate && this.trip.coordinate.lng) {
        this.mapCenter.lng = this.trip.coordinate.lng
        this.mapCenter.lat = this.trip.coordinate.lat
        this.mapZoom = 15
        // map.addOverlay(new this.BMap.Marker(this.trip.coordinate))
      } else {
        this.mapCenter.lng = 108.728199
        this.mapCenter.lat = 34.361419
        this.mapZoom = 10
      }
    },
    Search(queryString, cb) {
      let that = this  // map方法中的this指向不对。所以申明一个(小细节的东西)
      let myGeo = new this.BMap.Geocoder()
      myGeo.getPoint(queryString, function (point) {
        if (point) {
          that.trip.coordinate = point
          that.makerCenter(point)
        } else {
          that.trip.coordinate = null
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

<style scoped>
.map-view{
  width: 100%;
  height: 200px;
}
</style>
