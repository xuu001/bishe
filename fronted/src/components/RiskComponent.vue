<template>
  <div>
    <vue-seamless-scroll :data="midRiskArea" :class-option="optionSingleHeight" class="seamless-warp">
      <ul class="item">
        <li v-for="(item,index) in midRiskArea" :key="index" style=" list-style-type: none; text-align: left">
          <span v-text="item"></span>
        </li>
      </ul>
    </vue-seamless-scroll>
  </div>
</template>

<script>

import vueSeamlessScroll from 'vue-seamless-scroll'
export default {
  name: "RiskComponent",
  components:{vueSeamlessScroll},
  data() {
    return {
      midRiskArea:{},
      midTotal:'',
    }
  },
  created() {
    this.getData()
  },
  computed:{
      optionSingleHeight () {
        return {
          step: 0.5, // 数值越大速度滚动越快
          hoverStop: true, // 是否开启鼠标悬停stop
          direction: 1, // 0向下 1向上 2向左 3向右
          openWatch: true, // 开启数据实时监控刷新dom
          singleHeight: 21, // 单步运动停止的高度(默认值0是无缝不停止的滚动) direction => 0/1
        }
      }
  },
  methods:{
    getData() {
      this.$axios.get('http://api.tianapi.com/ncov/index?key=d334721cf6eba2d619a5855420ec352c').then(res => {
        if (res.data.code === 200) {
          this.midRiskArea = res.data.newslist[0].riskarea.mid
          this.midTotal = this.midRiskArea.length
          // this.highRiskArea = res.data.newslist[0].riskarea.high
          // this.highTotal = this.highRiskArea.length
          console.log(this.midRiskArea)
          // console.log(this.highRiskArea)
        } else {
          console.log('News新闻接口失效')
        }
      })
    },
  }
}
</script>

<style scoped>
.seamless-warp {
  height: 229px;
  overflow: hidden;
}
</style>
