<template>
  <div>
      <el-table ref="filterTable" :data="tableData" style="width: 100%" v-loading="loading" highlight-current-row>
        <el-table-column prop="time" label="日期" sortable width="180" column-key="date"
                         :filters="this.month"
                         :filter-method="filterHandler">
        </el-table-column>
        <el-table-column prop="temperature" label="体温(℃ )" width="80">
        </el-table-column>
        <el-table-column prop="healthy" label="健康状况" width="90">
          <template #default="{row}">
            <el-tag type="success" v-if="row.healthy === 0">健康</el-tag>
            <el-tag type="danger" v-if="row.healthy === 1">不健康</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="isLive" label="是否离开本市" width="110">
          <template #default="{row}">
            <el-tag type="danger" v-if="row.isLive === 1">是</el-tag>
            <el-tag type="success" v-if="row.isLive === 0">否</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="quarantine" label="是否离隔离" width="110">
          <template #default="{row}">
            <el-tag type="danger" v-if="row.quarantine === 1">是</el-tag>
            <el-tag type="success" v-if="row.quarantine === 0">否</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="address" label="打卡地址" width="360" :formatter="formatter">
        </el-table-column>
<!--        <el-table-column prop="" label="标签" width="100"-->
<!--                         :filters="[{ text: '家', value: '家' }, { text: '公司', value: '公司' }]"-->
<!--                         :filter-method="filterTag" filter-placement="bottom-end">-->
<!--          <template slot-scope="scope">-->
<!--            <el-tag :type="scope.row.tag === '家' ? 'primary' : 'success'" disable-transitions>{{scope.row.tag }}-->
<!--            </el-tag>-->
<!--          </template>-->
<!--        </el-table-column>-->
        <el-table-column prop="remarks" label="备注">
        </el-table-column>
      </el-table>
        <el-pagination background @current-change="pageChange($event)" @size-change="sizeChange($event)"
                       :page-sizes="[10,20,30]" layout="total,prev,pager,next,sizes" :page-size=this.param.size
                       :total="total" small>
        </el-pagination>
  </div>
</template>

<script>
export default {
  name: 'DaKaList',
  data() {
    return {
      loading: '',
      userInfo: '',
      total: 0,
      param: {
        accountId: '',
        page: 1,
        size: 10,
      },
      tableData: [],
      month: [
        {text: '2022-04-15', value: '2022-04-15'},
        {text: '2022-04-14', value: '2022-04-14'}
      ],
    }
  },
  created() {
    this.userInfo = JSON.parse(sessionStorage.getItem('userInfo'))


  },
  mounted() {
    this.geList()
  },
  methods: {
    geList() {
      this.TimeOut()
      this.param.accountId = this.userInfo.id
      this.$axios.get('/api/daKa/getList', {params: this.param}).then(res => {
        this.tableData = res.data.data.list
        this.total = res.data.data.total
      })
    },
    getMonth() {

    },
    resetDateFilter() {
      this.$refs.filterTable.clearFilter('date');
    },
    clearFilter() {
      this.$refs.filterTable.clearFilter();
    },
    formatter(row) {
      return row.address;
    },
    filterTag(value, row) {
      return row.tag === value;
    },
    filterHandler(value, row, column) {
      const property = column['property'];
      return row[property] === value;
    },
    TimeOut() { //加载动画
      this.loading = true
      setTimeout(() => {
        this.loading = false
      }, 500);
    },

    pageChange(page) {
      this.param.page = page;
      this.geList()
    },
    sizeChange(size) {
      this.param.size = size;
      this.geList()
    },
  }
}
</script>
<style>
.el-table-column {
  height: 40px;
}

.paginationClass {
  bottom: 52px;
  height: 40px;
  text-align: center;
}
</style>
