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
          <el-date-picker v-model="select.data" align="left" placeholder="筛选日期" type="date" value-format="yyyy-MM-dd"
                          :picker-options="pickerOptions">
          </el-date-picker>
        </el-form-item>
      </el-col>
      <el-col :span="4">
        <el-form-item>
          <el-button type="primary" @click="geList">查询</el-button>
        </el-form-item>
      </el-col>
    </el-form>
    <el-table ref="filterTable" :data="allList" style="width: 100%" v-loading="loading">
      <el-table-column prop="time" label="日期" sortable width="120" column-key="date">
      </el-table-column>
      <el-table-column prop="name" label="姓名" width="80">
      </el-table-column>
      <el-table-column prop="department" label="部门" width="80">
      </el-table-column>
      <el-table-column prop="account" label="工号" width="80">
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
      <el-table-column prop="quarantine" label="是否离隔离" width="100">
        <template #default="{row}">
          <el-tag type="danger" v-if="row.quarantine === 1">是</el-tag>
          <el-tag type="success" v-if="row.quarantine === 0">否</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="address" label="打卡地址" width="330" :formatter="formatter">
      </el-table-column>
      <el-table-column prop="phone" label="电话">
      </el-table-column>
      <el-table-column prop="remarks" label="备注">
      </el-table-column>
    </el-table>
    <el-pagination background @current-change="pageChange($event)" @size-change="sizeChange($event)"
                   :page-sizes="[8,20,30]" layout="total,prev,pager,next,sizes" :page-size=this.select.size :total="total" small>
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
      allList: [],
      select: {
        name: '',
        data: '',
        department:'',
        page: 1,
        size: 8,
      },
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
    this.userInfo = JSON.parse(sessionStorage.getItem('userInfo'))
  },
  mounted() {
    this.geList()
  },
  methods: {
    geList() {
      this.TimeOut()
          console.log(this.select)
      this.$axios.get('/api/daKa/getAllList', {params: this.select}).then(res => {
        console.log(res.data.data.list)
        this.allList = res.data.data.list
        this.total = res.data.data.total
      })
    },

    TimeOut() {
      this.loading = true
      setTimeout(() => {
        this.loading = false
      }, 300);
    },
    formatter(row) {
      return row.address;
    },
    pageChange(page) {
      this.select.page = page;
      this.geList()
    },
    sizeChange(size) {
      this.select.size = size;
      this.geList()
    },
  }
}
</script>
<style>

</style>
