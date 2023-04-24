<template>
  <div>
    <!-- 初始化echarts需要个 有宽高的 盒子 -->
    <div ref='mapbox' style='height:400px;width:600px'></div>
  </div>
</template>

<script>
import *as echarts from 'echarts'
import 'echarts/map/js/china.js' //echarts 5.0-下引入地图
import  jsonp  from 'jsonp'
// import china from "../assets/data";  //老版本的echarts
// import china from 'echarts/map/json/china.json'
// echarts.registerMap('china', china)

//注册一个地图模块 部分数据需要冲方法里传值
let option = {
  title: {
    text: "HHAO",
    link: "https://github.com/xuu001",
    subtext: `企业疫情防控系统 数据`,
    sublink: "https://gitee.com/hhangvip/"
  },
  series: [{
    name: "现有确诊人数",
    type: 'map',    //用去渲染的是一个地图
    map: 'china',    //告诉echarts  要去渲染中国地图
    label: {
      // 控制对应地区的汉字
      show: true,
      color: '#000000', // 控制地区名的字体颜色
      fontSize: 10
    },
    itemStyle: {
      // 地图板块的颜色和边框
      areaColor: '#f8f6f6',
    },
    roam: true,
    zoom: 1.2,// 控制地图的放大和缩小
    emphasis: {
      // 控制鼠标滑过之后的背景色和字体颜色
      label: {
        color: '#fff',
        fontSize: 12
      },
      itemStyle: {
        areaColor: '#92c3f5'
      }
    },
    data: []// 用来展示后台给的数据的 {name:xx,value:xxx}
  }],
  visualMap: [{
    type: 'piecewise',
    show: true,
    // splitNumber:4
    pieces: [
      // 分段
      {min: 10000},
      {min: 1000, max: 9999},
      {min: 100, max: 999},
      {min: 10, max: 99},
      {min: 1, max: 9}
    ],
    // align:'right',// 左下角分段 默认left
    // orient:'horizontal' 默认竖直
    // left right //这些属性控制 分段坐在的位置
    // showLabel:false,  //显示分段范围
    // textStyle:{}
    inRange: {
      symbol: 'rect',
      color: ['#faf3f0', '#c85000'] //自动划分颜色等级区域
    },
    itemWidth: 10,
    itemHeight: 10
  }],
  tooltip: {
    trigger: 'item'
  },
  toolbox: {
    show: true,
    orient: 'vertical',
    left: 'left',
    top: 'center',
    feature: {
      // dataView: {readOnly: this}, //数据视图
      restore: {},
      saveAsImage: {}
    }
  },
}
export default {
  name: "ChinaMap",
  data() {
    return {
      country: '',
      countryList: '',
      time:'',
    }
  },
  created() {
    // this.getData()
  },
  mounted() {
    // this.getWbData();       //微博数据接口
    // this.getTxData()      //腾讯数据接口
    this.getData()          //jsonp服务器请求方式
    this.mychart = echarts.init(this.$refs.mapbox);
    this.mychart.setOption(option)
  },
  methods: {
    getTxData() {
      this.$axios.get('/txApi/g2/getOnsInfo?name=disease_h5').then(res => {

        // console.log(res.data.data)
        this.country = JSON.parse(res.data.data)
        console.log(this.country)
        this.countryList = this.country.areaTree[0].children
        // console.log(this.countryList)
        // let list = this.countryList.map(item =>({name:item.name,value:item.total.nowConfirm}))
        // console.log(list);
        option.series[0].data = this.countryList.map(item => ({name: item.name, value: item.total.nowConfirm}))
        this.mychart.setOption(option);
        // 这行代码能执行的前提是 DOM已经渲染完成，只有DOM渲染完成才能够执行即用mounted()而不用create() echarts初始化
      })
    },
    getWbData() {
      this.$axios.get('/wbApi/news/wap/fymap2020_data.d.json').then(data => {
        console.log("list请求成功，分析死你")
        console.log(data.data.data);
        let list = data.data.data.list.map(item => ({name: item.name, value: item.econNum}))
        // console.log(list);
        this.time = data.data.data.times
        //将得到数据data放入option的series里
        console.log(option)
        option.series[0].data = list;
        option.title.subtext= option.title.subtext.concat(this.time)
        this.mychart.setOption(option);
        // 这行代码能执行的前提是 DOM已经渲染完成，只有DOM渲染完成才能够执行 echarts初始化
      })
    },
    getData(){
      jsonp('https://interface.sina.cn/news/wap/fymap2020_data.d.json',{},(err,data)=>{
        if(!err){
          // 代表请求数据成功
          console.log("list请求成功，分析。。")
          let list = data.data.list.map(item=>({name:item.name,value:item.econNum}))
          this.time = data.data.times
          option.series[0].data = list;
          //将时间放入副标题
          option.title.subtext= option.title.subtext.concat(this.time)
          this.mychart.setOption(option);
          // 这行代码能执行的前提是 DOM已经渲染完成，只有DOM渲染完成才能够执行 echarts初始化
        } else {
          console.log("接口请求失败")
        }
      })
    },
  }
}
</script>

<style scoped>

</style>
