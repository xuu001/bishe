<template>
  <div>
    <el-table ref="filterTable" :data="allList" style="width: 100%" v-loading="loading">
      <el-table-column prop="createDate" label="申请日期" sortable width="115" column-key="date"
                       :filter-method="filterHandler">
      </el-table-column>
      <el-table-column prop="transport" label="抗原结果" width="150" align="center">
        <template #default="{row}">
          <el-tag type="success" v-if="row.quarantine === 0">阴性</el-tag>
          <el-tag type="danger" v-if="row.quarantine === 1">阳性</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="remarks" label="抗原结果证明" width="180">
        <template #default="{row}">
          <el-image @click.stop="1" :src='"/api/fileserver/" + row.file' style="width: 80px; height: 80px;"
                    :preview-src-list="['/api/fileserver/' + row.file]"></el-image>
        </template>
      </el-table-column>
      <el-table-column prop="remarks" label="备注" width="230">
      </el-table-column>
      <el-table-column prop="returnDate" label="审批时间" width="230">
      </el-table-column>
      <el-table-column prop="reason" label="审批意见">
      </el-table-column>
      <el-table-column prop="statue" label="审核结果" width="120" align="center" >
        <template #default="{row}">
          <el-tag type="success" v-if="row.status === 1" >已通过</el-tag>
          <el-tag v-if="row.status === 0" >未审核</el-tag>
          <el-tag type="danger" v-if="row.status === 2" >已驳回</el-tag>
        </template>
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
  name: "ReWorkList",
  data() {
    return {
      loading:'',
      select: {
        accountId: '',
        page: 1,
        size: 8
      },
      total:0 ,
      allList:[],
      userInfo:[],
    }
  },
  mounted() {
    this.userInfo = JSON.parse(sessionStorage.getItem("userInfo"))
    this.getList()
  },
  methods:{
    getList() {
      this.TimeOut()
      this.select.accountId = this.userInfo.id
      console.log(this.select)
      this.$axios.get('/api/reWork/getList',{params:this.select}).then(res =>{
        console.log(res)
        if (res.data.status ===1){
          this.allList = res.data.data.list
          this.total = res.data.data.total
        }else {
          this.$notify({
            title:'提示',
            message: '网络错误，联系管理员',
            type:"warning"
          })
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
    filterHandler(value, row, column) {
      const property = column['property'];
      return row[property] === value;
    },
  }

}
</script>

<style scoped>

</style>
