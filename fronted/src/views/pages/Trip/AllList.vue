<template>
  <div>
    <el-form :inline="true" :model="select">
      <el-col :span="6">
        <el-form-item label="姓名">
          <el-input v-model="select.name" placeholder="筛选姓名" clearable prefix-icon="el-icon-search"></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item label="部门">
          <el-input v-model="select.department" placeholder="筛选部门" clearable prefix-icon="el-icon-search"></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item label="日期">
          <el-date-picker v-model="select.data" align="left" placeholder="筛选出发日期" type="date" value-format="yyyy-MM-dd"
                          :picker-options="pickerOptions">
          </el-date-picker>
        </el-form-item>
      </el-col>
      <el-col :span="4">
        <el-form-item>
          <el-button type="primary" @click="geList">查询</el-button>
        </el-form-item>
      </el-col>
      <el-col :span="1">
        <el-form-item>
          <el-button type="primary" @click="exportList">导出</el-button>
        </el-form-item>
      </el-col>
    </el-form>
    <el-table ref="filterTable" :data="allList" style="width: 100%" v-loading="loading">
      <el-table-column prop="createTime" label="报备日期" sortable width="120" column-key="date">
      </el-table-column>
      <el-table-column prop="name" label="姓名" width="80">
      </el-table-column>
      <el-table-column prop="department" label="部门" width="80">
      </el-table-column>
      <el-table-column prop="account" label="工号" width="80">
      </el-table-column>
      <el-table-column prop="departureTime" label="出发日期"  width="100">
      </el-table-column>
      <el-table-column prop="transport" label="出交通工具"  width="110">
      </el-table-column>
      <el-table-column prop="arrivalTime" label="抵达日期"  width="100">
      </el-table-column>
      <el-table-column prop="returnTime" label="回抵日期"  width="100">
      </el-table-column>
      <el-table-column prop="returnTransport" label="回交通工具"  width="110">
      </el-table-column>
      <el-table-column prop="destination" label="目的地" width="330">
      </el-table-column>
      <el-table-column prop="isRisk" label="风险区等级" width="110">
        <template #default="{row}">
          <el-tag type="danger" v-if="row.isRisk === 2">高级风险区</el-tag>
          <el-tag type="warning" v-if="row.isRisk === 1">中级风险区</el-tag>
          <el-tag type="success" v-if="row.isRisk === 0">无风险</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="phone" label="电话" width="115">
      </el-table-column>
      <el-table-column prop="remarks" label="备注">
      </el-table-column>
    </el-table>
    <el-pagination background @current-change="pageChange($event)" @size-change="sizeChange($event)"
                   :page-sizes="[8,20,30]" layout="total,prev,pager,next,sizes" :page-size=this.select.size
                   :total="total" small>
    </el-pagination>
  </div>
</template>

<script>
export default {
  name: "AllList",
  data() {
    return {
      loading:'',
      select: {
        name:'',
        data:'',
        department:'',
        page: 1,
        size: 8,
      },
      allList: [],
      total: 0,
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
    }
  },
  created() {
  },
  mounted() {
    this.geList()
  },
  methods: {
    geList() {
      this.TimeOut()
      this.$axios.get('/api/trip/getAllList',{params:this.select}).then(res =>{
        this.allList = res.data.data.list
        this.total = res.data.data.total
        console.log(res)
      })

    },
    exportList(){
      // this.$axios.get('/api/trip/export',{params:this.select}).then(res =>{
      //   // 这里文件下载流不能用axios的get或者post
      //   console.log(res)
      // })
      location.href = '/api/trip/export'
    },
    pageChange(page) {
      this.select.page = page;
      this.geList()
    },
    sizeChange(size) {
      this.select.size = size;
      this.geList()
    },
    TimeOut() {
      this.loading = true
      setTimeout(() => {
        this.loading = false
      }, 500);
    },
  }
}
</script>

<style scoped>

</style>
