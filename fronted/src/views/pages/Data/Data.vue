<template>
  <div class="container-fluid">
    <div class="row">
      <div class="col-lg-3 col-6">
        <!-- small box  员工总数 -->
        <div class="small-box bg-info">
          <div class="inner">
            <h3>{{this.totalNum}}</h3>
            <p>员工总数</p>
          </div>
          <div class="icon">
            <i class="el-icon-user"></i>
          </div>
        </div>
      </div>
      <!-- ./col -->
      <div class="col-lg-3 col-6">
        <!-- small box 打卡人数  -->
        <div class="small-box bg-success">
          <div class="inner">
            <h3>{{this.reportNum}}</h3>
            <p>打卡人数</p>
          </div>
          <div class="icon">
            <i class="el-icon-edit-outline"></i>
          </div>
        </div>
      </div>
      <!-- ./col -->
      <div class="col-lg-3 col-6">
        <!-- small box  -->
        <div class="small-box bg-warning">
          <div class="inner">
            <h3>{{ this.unReportNum }}</h3>
            <p>未打卡人数</p>
          </div>
          <div class="icon">
            <i class="el-icon-document-delete"></i>
          </div>
        </div>
      </div>
      <!-- ./col -->
      <div class="col-lg-3 col-6">
        <!-- small box -->
        <div class="small-box bg-danger">
          <div class="inner">
            <h3>{{this.quarantineNum}}</h3>
            <p>隔离人数</p>
          </div>
          <div class="icon">
            <i class="el-icon-office-building"></i>
          </div>
        </div>
      </div>
      <!-- ./col -->
    </div>
    <!--  ⬆ 顶部数据展示-->
    <div class="container">
      <el-row>
        <el-col :span="14" style="padding-left:10px;">
          <el-row :gutter="10">
            <el-col :span="8">
              <el-card shadow="hover">
                直接传播
              </el-card>
            </el-col>
            <el-col :span="8">
              <el-card shadow="hover">
                喷嚏、咳嗽、说话的飞沫
              </el-card>
            </el-col>
            <el-col :span="8">
              <el-card shadow="hover">
                直接吸入导致的感染
              </el-card>
            </el-col>
          </el-row>
          <el-row style="margin-top:10px;" :gutter="10">
            <el-col :span="8">
              <el-card shadow="hover">
                接触传播
              </el-card>
            </el-col>
            <el-col :span="8">
              <el-card shadow="hover">
                飞沫沉积在物品表面
              </el-card>
            </el-col>
            <el-col :span="8">
              <el-card shadow="hover">
                接触污染手再接触口鼻感染
              </el-card>
            </el-col>
          </el-row>

          <el-card shadow="hover" style="height:300px;margin-top:10px;">
            <div class="text item">
              {{'打卡状况 '  }}
            </div>
          </el-card>
<!--          <el-card shadow="hover" style="height:300px;margin-top:10px;">-->
<!--            <div class="text item">-->
<!--              {{'列表内容 ' + o }}-->
<!--            </div>-->
<!--          </el-card>-->
        </el-col>
        <el-col :span="10" style="padding-left:10px;">
          <el-card shadow="hover" style="height:310px;">
            <div class="text item">
              <calendar-component></calendar-component>
            </div>
          </el-card>

          <el-card shadow="hover" style="height:300px;margin-top:10px;">
            <div class="text item">
              {{'预防小知识 '  }}
            </div>

          </el-card>

<!--          <el-card shadow="hover" style="height:245px;margin-top:10px;">-->
<!--            <div class="text item">-->
<!--              {{'列表内容 ' + o }}-->
<!--            </div>-->
<!--          </el-card>-->
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>

import CalendarComponent from "@/components/CalendarComponent";
export default {
  name: "Data",
  components: {CalendarComponent},
  data(){
    return {
      totalNum:'',
      reportNum:'',       //打卡人数
      unReportNum:'',     //未打卡人数
      quarantineNum:'',   //隔离人数
    }
  },
  created() {
  },
  mounted() {
    this.getReport()
  },
  methods:{
    getReport() {
      this.$axios.get('/api/data/findReport').then(res =>{
        if (res.data.status === 1){
          this.totalNum = res.data.data.total
          this.reportNum = res.data.data.report
          this.quarantineNum = res.data.data.quarantine
          if (this.totalNum>this.reportNum){
            this.unReportNum = this.totalNum - this.reportNum
          }
        }else {
          this.reportNum = this.quarantineNum = this.unReportNum = 0
        }
      })
    },
    notice(){
      this.$notify({
        title: '提示',
        message: '数据统计失败，检测网络链接',
        type: 'error'
      })
    }
  }
}
</script>

<style lang="scss" scoped>

.container-fluid {
  width: 100%;
  padding-right: 8px;
  padding-left: 8px;
  margin-right: auto;
  margin-left: auto;
}
.row {
  display: -webkit-flex;
  display: -ms-flexbox;
  display: flex;
  -webkit-flex-wrap: wrap;
  -ms-flex-wrap: wrap;
  flex-wrap: wrap;
  margin-right: -7.5px;
  margin-left: -7.5px;
}
.col-lg-3 {
  -webkit-flex: 0 0 25%;
  -ms-flex: 0 0 25%;
  flex: 0 0 25%;
  max-width: 25%;
}
.col-6 {
  -webkit-flex: 0 0 50%;
  -ms-flex: 0 0 50%;
  flex: 0 0 25%;
  max-width: 50%;
  position: relative;
  width: 100%;
  padding-left: 6px;
  padding-right: 6px;
}
.small-box {
  height: 100px;
  border-radius: 0.25rem;
  box-shadow: 0 0 1px rgba(0, 0, 0, 0.125), 0 1px 3px rgba(0, 0, 0, 0.2);
  display: block;
  margin-bottom: 20px;
  position: relative;
}
.small-box > .inner {
  padding: 10px;
}
.small-box h3 {
  font-size: 2.2rem;
  font-weight: 700;
  margin: 0 0 10px;
  padding: 0;
  white-space: nowrap;
}
@media (min-width: 992px) {
  .col-xl-2 .small-box h3,
  .col-lg-2 .small-box h3,
  .col-md-2 .small-box h3 {
    font-size: 1.6rem;
  }
  .col-xl-3 .small-box h3,
  .col-lg-3 .small-box h3,
  .col-md-3 .small-box h3 {
    font-size: 1.6rem;
  }
}
.small-box p {
  font-size: 1rem;
}
.small-box p > small {
  color: #f8f9fa;
  display: block;
  font-size: .9rem;
  margin-top: 5px;
}

.small-box h3, .small-box p {
  z-index: 5;
}

.small-box .icon {
  color: rgba(0, 0, 0, 0.08);
  z-index: 0;
}
.small-box .icon > i {
  font-size: 90px;
  position: absolute;
  left: 10px;
  top: 1px;
  transition: -webkit-transform 0.3s linear;
}
/* 卡片背景颜色 */
.bg-info {
  background-color: #409EFF !important;
  color: #ffffff;
}
.bg-success {
  background-color: #67C23A !important;
  color: #ffffff;
}
.bg-warning {
  background-color: #ffc107 !important;
  color: #ffffff;
}
.bg-danger {
  background-color: #F56C6C !important;
  color: #ffffff;
}

</style>
