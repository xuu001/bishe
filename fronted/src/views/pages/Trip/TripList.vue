<template>
  <div>
    <el-table ref="filterTable" :data="list" style="width: 100%" v-loading="loading">
      <el-table-column prop="createTime" label="上报日期" sortable width="120" column-key="date"
                       :filters="this.month"
                       :filter-method="filterHandler">
      </el-table-column>
      <el-table-column prop="departureTime" label="出发日期" width="100">
      </el-table-column>
      <el-table-column prop="transport" label="出交通工具" width="120">
      </el-table-column>
      <el-table-column prop="arrivalTime" label="抵达日期" width="100">
      </el-table-column>
      <el-table-column prop="returnTime" label="回抵日期" width="100">
      </el-table-column>
      <el-table-column prop="returnTransport" label="回交通工具" width="100">
      </el-table-column>
      <el-table-column prop="destination" label="外出目的地" width="300">
      </el-table-column>
      <el-table-column prop="isRisk" label="目地风险等级" width="110">
        <template #default="{row}">
          <el-tag type="success" v-if="row.isRisk === 0">无风险</el-tag>
          <el-tag type="warning" v-if="row.isRisk === 1">中级风险区</el-tag>
          <el-tag type="danger" v-if="row.isRisk === 2">高级风险区</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="remarks" label="备注">
      </el-table-column>
      <el-table-column fixed="right" label="操作" width="100">
        <template slot-scope="scope">
          <el-button @click="handleClick(scope.row)" type="text" size="small">查看</el-button>
          <el-button type="text" size="small">编辑</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination background @current-change="pageChange($event)" @size-change="sizeChange($event)"
                   :page-sizes="[10,20,30]" layout="total,prev,pager,next,sizes" :page-size=this.params.size
                   :total="total" small>
    </el-pagination>
  </div>
</template>

<script>
export default {
  name: "TripList",
  data() {
    return {
      userInfo: '',
      loading:'',
      params: {
        accountId: '',
        page: 1,
        size: 10,
      },
      total: 0,
      month:[],
      list: [],
    }
  },
  created() {
    this.userInfo = JSON.parse(sessionStorage.getItem("userInfo"))
  },
  mounted() {
    this.getList()
  },
  methods: {
    getList() {
      this.TimeOut()
      this.params.accountId = this.userInfo.id
      this.$axios.get('/api/trip/getList', {params: this.params}).then(res => {
        if (res.data.status === 1) {
          this.list = res.data.data.list
          this.total = res.data.data.total
        }
      })
    }, handleClick(row) {
      console.log(row);
    },


    filterHandler(value, row, column) {
      const property = column['property'];
      return row[property] === value;
    },
    TimeOut() {
      this.loading = true
      setTimeout(() => {
        this.loading = false
      }, 500);
    },
    pageChange(page) {
      this.params.page = page;
      this.geList()
    },
    sizeChange(size) {
      this.params.size = size;
      this.geList()
    },
  }
}
</script>

<style scoped>

</style>
