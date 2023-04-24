<template>
  <div>
    <div>
      <el-form :inline="true" :model="param">
        <el-col :span="6">
          <el-form-item label="姓名">
            <el-input v-model="param.name" placeholder="筛选姓名" clearable prefix-icon="el-icon-search"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="部门">
            <el-input v-model="param.department" placeholder="筛选部门" clearable prefix-icon="el-icon-search"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="日期">
            <el-date-picker v-model="param.data" align="left" placeholder="筛选入职日期" type="date" value-format="yyyy-MM-dd"
                            :picker-options="pickerOptions">
            </el-date-picker>
          </el-form-item>
        </el-col>
        <el-col :span="4">
          <el-form-item>
            <el-button type="primary" @click="findUser">查询</el-button>
          </el-form-item>
        </el-col>
      </el-form>
    </div>

    <el-table ref="userTable" :data="tableData" :stripe="true" :border="true" @row-click="rowClick"
              @selection-change="tableSelect=$event" style="width: 100%">
      <el-table-column type="selection" width="40"></el-table-column>
      <el-table-column prop="account" label="账号" width="120"></el-table-column>
      <el-table-column prop="name" label="姓名" width="100"></el-table-column>
      <el-table-column prop="createDate" label="入职时间" width="100"></el-table-column>
      <el-table-column prop="department" label="部门" width="100"></el-table-column>
      <el-table-column prop="mail" label="邮箱" width="190"></el-table-column>
      <el-table-column prop="address" label="家庭住址"></el-table-column>
      <el-table-column prop="status" label="启用状态" width="100" align="center">
        <template #default="{row}">
          <el-tag type="danger" v-if="row.status === 0" size="small">禁用</el-tag>
          <el-tag v-if="row.status === 1" size="small">启用</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="modifyDate" label="修改时间" width="100" align="center"></el-table-column>
      <el-table-column prop="modifyName" label="修改人姓名" width="100"></el-table-column>
      <el-table-column label="操作" width="100">
          <template slot-scope="scope">
            <el-button :disabled="scope.row.isAdmin!==0" @click="admin(scope.row)" type="text" size="small">设为部长</el-button>
          </template>
      </el-table-column>
    </el-table>
    <el-pagination background :page-sizes="[8, 16, 30]" @current-change="pageChange($event)"
                   :page-size=this.param.size @size-change="sizeChange($event)"
                   layout="total,prev,pager,next,sizes" :total="total" small>
    </el-pagination>
  </div>
</template>


<script>
export default {
  name: "UserRole",
  data() {
    return {
      userInfo: [],
      tableData: [],
      tableSelect: {},
      total: 0,
      isAdding: false,
      departmentList:[],
      param: {
        account: '',
        name: '',
        department: '',
        date: '',
        page: 1,
        size: 8,
      },
      user: {
        account:'',
        departmentId:'',
        name: '',
        id: '',
        phone: '',
        pass: '',
        isDel: 0,
        version: '',
        modifyName: '',
        status: 1,
      },
      depart:[],
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
    this.findUser()
    this.userInfo = JSON.parse(sessionStorage.getItem("userInfo"))
    this.user.modifyName = this.userInfo.name
  },
  methods:{
    findUser() {
      this.$axios.get('/api/user/findUser', {params: this.param}).then(res => {
        console.log(res)
        this.tableData = res.data.data.list;
        this.total = res.data.data.total;
      }).catch(e => console.log(e));
    },
    admin(){

    },
    pageChange(page) {
      this.param.page = page;
      this.findUser()
    },
    sizeChange(size) {
      this.param.size = size;
      this.findUser()
    },
    getDepartment() {
      this.$axios.get('/api/company/findDepart').then(res => {
        this.departmentList = res.data.data
        console.log(this.departmentList)
      })
    },
    rowClick(row) {
      this.$refs.userTable.toggleRowSelection(row)
    },
  }

}
</script>

<style scoped>

</style>
