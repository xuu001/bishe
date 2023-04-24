<template>
  <div>
    <el-card>
      <el-form ref="elForm" :inline="true" :model="elForm" :rules="rules" style="height: 250px">
        <el-row :gutter="20">
          <el-col :span="10">
            <el-form-item label="今日是否健康" prop="healthy">
              <el-radio-group v-model="elForm.healthy" size="medium">
                <el-radio v-for="(item, index) in healthyOptions" :key="index" :label="item.value" border>
                  {{ item.label }}
                </el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="体温" prop="temperature">
              <el-input v-model="elForm.temperature" placeholder="请输入您的体温" prop="temperature" style="width: 300px">
                <template slot="append">℃</template>
              </el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="10">
            <el-form-item label="今日是否隔离" prop="quarantine">
              <el-radio-group v-model="elForm.quarantine" size="medium">
                <el-radio v-for="(item, index) in quarantineOptions" :key="index" :label="item.value" border>
                  {{ item.label }}
                </el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="地址" prop="address">
              <el-autocomplete v-model="elForm.address" :fetch-suggestions="Search"
                               :trigger-on-focus="false" placeholder="请输入您现处详细地址" prop="address"
                               style="width: 300px" @select="handleSelect"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="10">
            <el-form-item label="是否离开本市" prop="isLive">
              <el-radio-group v-model="elForm.isLive" size="medium">
                <el-radio v-for="(item, index) in isLiveOptions" :key="index" :label="item.value" border>
                  {{ item.label }}
                </el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item prop="remarks">
              <div slot="label" style="margin-left:11px;">备注</div>
              <el-input v-model="elForm.remarks" :autosize="{ minRows: 1.5, maxRows: 3}" placeholder="请输入内容"
                        style="width: 300px"
                        type="textarea">
              </el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-form-item>
            <el-button type="primary" @click="onSubmit('elForm')">提交</el-button>
          </el-form-item>
        </el-row>
      </el-form>
    </el-card>
    <el-card>
      <baidu-map :center="mapCenter" :scroll-wheel-zoom="true" :zoom="mapZoom" ak="baidu-ak" class="bm-view"
                 @ready="handlerBMap">
        <bm-geolocation :autoLocation="true" :showAddressBar="true"
                        anchor="BMAP_ANCHOR_TOP_RIGHT" @locationSuccess="locationSuccess"></bm-geolocation>
      </baidu-map>
    </el-card>
  </div>
</template>

<script>
import BaiduMap from 'vue-baidu-map/components/map/Map.vue'

export default {
  name: 'Daka',
  components: {
    BaiduMap
  },
  data() {
    let checkTemp = (rule, value, callback) => {
      if (!value) {
        return callback(new Error('体温不能为空'));
      }
        if (!Number(value)) {
          callback(new Error('请输入数字值'));
        } else {
          if (value <30){
            callback(new Error('体温应在30-45℃'))
          }
          if(value >45){
            callback(new Error('体温应在30-45℃'))
          }else {
            callback()
          }
        }
    };
    return {
      select:{
        accountId: '',
        time:'',
      },
      elForm: {
        accountId: '',
        healthy: '',
        temperature: '',
        quarantine: '',
        isLive: '',
        address: '',
        coordinate: [],
        remarks: '',
      },
      isOK:[],
      mapZoom: 15,
      mapCenter: {lng: 0, lat: 0},
      locationdata: {
        center: '',
        addr: '',
      },
      userInfo: '',
      rules: {
        healthy: [{required: true, message: '该选项必填', trigger: 'change'}],
        temperature: [{required: true, validator: checkTemp, trigger: 'blur'}],
        quarantine: [{required: true, message: '该选项必填', trigger: 'change'}],
        isLive: [{required: true, message: '该选项必填', trigger: 'change'}],
        address: [{required: true, message: '请输入您的地址', trigger: 'change'}],
      },
      healthyOptions: [{
        "label": "是",
        "value": 0
      }, {
        "label": "否",
        "value": 1
      }],
      quarantineOptions: [{
        "label": "是",
        "value": 1
      }, {
        "label": "否",
        "value": 0
      }],
      isLiveOptions: [{
        "label": "是",
        "value": 1
      }, {
        "label": "否",
        "value": 0
      }],
    }
  },
  created() {
  },
  mounted() {
    this.userInfo = JSON.parse(sessionStorage.getItem('userInfo'))
    this.elForm.accountId = this.userInfo.id
    //先判断今日是否打卡 返回isOK 0/1
    this.geList()
  },
  methods: {
    locationSuccess(point) {
      //定位成功后
      console.log(point);
      this.locationdata.center = point.point
      const {city, province, street, streetNumber, district} = point.addressComponent;
      this.locationdata.addr = province + city + district + street + streetNumber
      this.center = point.point
      console.log(this.locationdata.addr)
      this.elForm.address = this.locationdata.addr
    },
    handlerBMap({BMap, map}) {
      this.BMap = BMap
      this.map = map
      if (this.elForm.coordinate && this.elForm.coordinate.lng) {
        this.mapCenter.lng = this.elForm.coordinate.lng
        this.mapCenter.lat = this.elForm.coordinate.lat
        this.mapZoom = 15
        map.addOverlay(new this.BMap.Marker(this.elForm.coordinate))
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
          that.elForm.coordinate = point
          that.makerCenter(point)
        } else {
          that.elForm.coordinate = null
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
    handleSelect(item) {
      let {point} = item
      this.elForm.coordinate = point
      this.makerCenter(point)
    },
    makerCenter(point) {
      if (this.map) {
        this.map.clearOverlays()
        this.map.addOverlay(new this.BMap.Marker(point))
        this.mapCenter.lng = point.lng
        this.mapCenter.lat = point.lat
        this.mapZoom = 15
      }
    },
    onSubmit(elForm) {
      this.$refs[elForm].validate(valid => {
        if (valid) {
          this.geList()
          //isOK 为 0 表示今天未打卡  1 表示已经打卡
          if (this.isOK === 0){
            this.$axios.post('/api/daKa/save', this.elForm).then(res => {
              console.log(res)
              if (res.data.data === 1) {
                this.$notify({
                  title: '提示',
                  message: res.data.message,
                  type: 'success'
                });
                this.$refs[elForm].resetFields();
              } else {
                this.$notify({
                  title: '提示',
                  message: res.data.message,
                  type: 'error'
                })
              }
            })
          }else {
            this.$notify({
              title: '(＃°Д°)',
              message: '今天已经打过卡啦',
              type: 'warning'
            })
          }
        }
      })
    },
    geList() {
      let now = new Date();
      const year = now.getFullYear(); //得到年份
      const month = now.getMonth()+1;//得到月份
      const date = now.getDate();//得到日期
      this.select.time = year + '' + '-'+month+''+'-'+date+''
      this.select.accountId = this.userInfo.id
      this.$axios.get('/api/daKa/getList', {params: this.select}).then(res => {
        this.isOK = res.data.data.list.length
        console.log(this.isOK === 0 )
        if (this.isOK ===1){
          console.log('已经打卡')
        }
      })
    },
  }
}
</script>

<style>
.bm-view {
  width: 100%;
  height: 260px;
}
</style>
