<template>
<div>
  <el-table ref="filterTable" :data="allList" style="width: 100%" v-loading="loading">
    <el-table-column prop="createDate" label="申请日期" sortable width="115" column-key="date">
    </el-table-column>
    <el-table-column prop="name" label="姓名" width="75">
    </el-table-column>
    <el-table-column prop="department" label="部门" width="75">
    </el-table-column>
    <el-table-column prop="transport" label="交通工具" width="95">
    </el-table-column>
    <el-table-column prop="departureTime" label="预计出发日期" width="120">
    </el-table-column>
    <el-table-column prop="destination" label="外出目的地" width="300">
    </el-table-column>
    <el-table-column prop="outTime" label="外出周期" width="80">
    </el-table-column>
    <el-table-column prop="remarks" label="备注" width="110">
    </el-table-column>
    <el-table-column prop="modifyUserName" label="审批人" width="75">
    </el-table-column>
    <el-table-column prop="modifyDate" label="审批时间" width="100">
    </el-table-column>
    <el-table-column prop="reason" label="审批意见">
    </el-table-column>
    <el-table-column prop="statue" label="审核结果" width="100" >
      <template #default="{row}">
        <el-tag type="success" v-if="row.statue === 1" >已通过</el-tag>
        <el-tag v-if="row.statue === 0" >未审核</el-tag>
        <el-tag type="danger" v-if="row.statue === 2" >已驳回</el-tag>
      </template>
    </el-table-column>
  </el-table>
  <el-pagination background @current-change="pageChange($event)" @size-change="sizeChange($event)"
                 :page-sizes="[10,20,30]" layout="total,prev,pager,next,sizes" :page-size=this.select.size
                 :total="total" small>
  </el-pagination>
</div>
</template>

<script>
export default {
  name: "OutList",
  data(){
    return {
      loading:'',
      userInfo:[],
      allList:[],
      total: 0,
      select:{
        id:'',
        name:'',
        page: 1,
        size: 10,
      },
    }
  },
  mounted() {
    this.userInfo = JSON.parse(sessionStorage.getItem("userInfo"))
    this.getList()
  },
  methods: {
    getList() {
      this.TimeOut()
      this.select.id = this.userInfo.id
      this.select.name = this.userInfo.name
      console.log(this.select)
      this.$axios.get('/api/out/getAllList',{params:this.select}).then(res =>{
        if (res.data.status ===1){
          this.allList = res.data.data.list
          this.total = res.data.data.total
        }
      })
    },
    //控制分页部分
    pageChange(page) {
      this.select.page = page;
      this.geList()
    },
    sizeChange(size) {
      this.select.size = size;
      this.geList()
    },
    //  控制加载缓存图像
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
