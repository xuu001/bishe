<template>
  <div class="news">
    <el-timeline>
      <el-timeline-item v-for="(item,index) in newsList" :key="index"
                        :timestamp="item.pubDateStr+ item.infoSource" placement="top">
        <el-card @click="item.sourceUrl">
          <span class="title">{{ item.title }}</span>
          <div class="bottom clearfix">
            {{ item.summary }}
          </div>
        </el-card>
      </el-timeline-item>
    </el-timeline>
  </div>
</template>

<script>
export default {
  name: "NewsComponent",
  data() {
    return {
      newsList: [],
    }
  },
  created() {
    this.getData()
  },
  mounted() {

  },
  methods: {
    getData() {
      this.$axios.get('http://api.tianapi.com/ncov/index?key=d334721cf6eba2d619a5855420ec352c').then(res => {
        if (res.data.code === 200) {
          this.newsList = res.data.newslist[0].news
          this.sortByKey(this.newsList, 'pubDateStr', true)
        } else {
          console.log('News新闻接口失效')
        }
      })
    },
    /**
     *  因为新闻数据时间排序不定写一个排序方法
     * @param array 需要排序的数组
     * @param key   按照某字段排序
     * @param order true为升 false为降
     * @returns {*}
     */
    sortByKey(array, key, order) {
      return array.sort(function (a, b) {
        let x = a[key];
        let y = b[key]
        if (order) {
          return ((x < y) ? -1 : ((x > y) ? 1 : 0))
        } else {
          return ((x < y) ? ((x > y) ? 1 : 0) : -1)
        }
      })
    }
  }
}
</script>

<style scoped>
.news {
  width: 390px;
}
.el-card {
  padding: -5px;
  height: 100px;
  transition: 0.5s;
}

/deep/ .el-card:hover {
  height: 180px;
}
.bottom {
  margin-top: 5px;
  line-height: 15px;
}

.title {
  font-weight: bold;
  white-space: nowrap;
}

.image {
  width: 100%;
  display: block;
}

.clearfix:before,
.clearfix {
  color: #7f7f7f;
  font-size: smaller;
  text-overflow: ellipsis;
}

.clearfix:after {
  display: table;
  content: "";
}

.clearfix:after {
  clear: both
}
</style>
