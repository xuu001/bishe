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
          <el-date-picker v-model="select.data" align="left" placeholder="筛选申请日期" type="date" value-format="yyyy-MM-dd"
                          :picker-options="pickerOptions">
          </el-date-picker>
        </el-form-item>
      </el-col>
      <el-col :span="4">
        <el-form-item>
          <el-button type="primary" @click="getList">查询</el-button>
        </el-form-item>
      </el-col>
    </el-form>
    <el-table ref="filterTable" :data="allList" style="width: 100%" v-loading="loading">
      <el-table-column prop="createDate" label="申请日期" sortable width="115" column-key="date"
                       :filter-method="filterHandler">
      </el-table-column>
      <el-table-column prop="name" label="姓名" width="75">
      </el-table-column>
      <el-table-column prop="department" label="部门" width="75">
      </el-table-column>
      <el-table-column prop="transport" label="抗原结果" width="120">
        <template #default="{row}">
          <el-tag type="success" v-if="row.quarantine === 0" >阴性</el-tag>
          <el-tag type="danger" v-if="row.quarantine === 1" >阳性</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="remarks" label="抗原结果证明" width="150">
        <template #default="{row}">
          <el-image @click.stop="1" :src='"/api/fileserver/" + row.file' style="width: 80px; height: 80px;"
                    :preview-src-list="['/api/fileserver/' + row.file]"></el-image>
        </template>
      </el-table-column>
      <el-table-column prop="remarks" label="备注" width="100">
      </el-table-column>
      <el-table-column prop="returnDate" label="审批时间" width="100">
      </el-table-column>
      <el-table-column prop="reason" label="审批意见">
      </el-table-column>
      <el-table-column fixed="right" label="审核" width="100" >
        <template slot-scope="scope">
          <el-button :disabled="scope.row.status!==0" @click="pass(scope.row)" type="text" size="small">通过</el-button>
          <el-button :disabled="scope.row.status!==0" @click="reject(scope.row)" type="text" size="small" >驳回</el-button>
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
  name: "ReApplications",
  data() {
    return {
      loading:'',
      select:{
        name:'',
        data:'',
        department:'',
        page: 1,
        size: 8
      },
      total:0 ,
      allList:[],
      userInfo:[],
      upData: {
        version: '',
        statue: '',
        reason: '',
        id: ''
      },
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
mounted() {
    this.userInfo = JSON.parse(sessionStorage.getItem("userInfo"))
  this.getList()
},
  methods:{
    getList() {
      this.TimeOut()
      this.$axios.get('/api/reWork/getList',{params:this.select}).then(res =>{
        if (res.data.status ===1){
          this.allList = res.data.data.list
          this.total = res.data.data.total
        }
      })
    },
    //审核通过
    pass(row){
      this.upData.id = row.id
      this.upData.status = 1
      this.upData.version = row.version
      this.upData.modifyUserName = this.userInfo.name
      this.$axios.post('/api/reWork/edit',this.upData).then(res =>{
        console.log(res)
        if (res.data.status ===1 ){
          this.$notify({
            title:'提示',
            message: '审核通过成功',
            type:"success"
          })
        }else {
          this.$notify({
            title:'提示',
            message: '审核失败，请更新数据',
            type:"warning"
          })
        }
        this.getList()
      })
    },
    //审核驳回
    reject(row){
      this.upData.id = row.id
      this.upData.status = 2
      this.upData.version = row.version
      this.upData.modifyUserName = this.userInfo.name
      this.$axios.post('/api/out/edit',this.upData).then(res =>{
        console.log(res)
        if (res.data.status === 1){
          this.$notify({
            title:'提示',
            message: '审核驳回成功',
            type:"success"
          })
        }else {
          this.$notify({
            title:'提示',
            message: '审核失败，请更新数据',
            type:"warning"
          })
        }
        this.getList()
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
