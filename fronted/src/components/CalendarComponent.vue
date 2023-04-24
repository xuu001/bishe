<template>
  <el-calendar>
    <template slot="dateCell" slot-scope="{data}">
      <div class="el-calendar-day">{{ data.day.split('-').slice(2).join('-') }}</div>
      <div class="solar" v-if="data.day.split('-')[2] === value">
        <p v-if="selectDay(data.day) === 0 ">未打卡</p>
        <p v-if="selectDay(data.day) === 1 ">已打卡</p>
      </div>
    </template>
  </el-calendar>
</template>

<script>
export default {
  name: "CalendarComponent",
  data() {
    return {
      value:1
    }
  },
  create() {

  },
  mounted() {

  },
  methods: {
    selectDay(day){
      const status = 0
      //TODO 查询方法看看自己大卡没
      console.log(day)
      // status = 1
      return status

    },
  }

}
</script>

<style lang="scss" scoped>
/* 让日历高度改变 */
/deep/ .el-calendar-table .el-calendar-day {
  height: 30px;
  padding: 3px;
  thead {
    th {
      padding: 1px;
    }
  }
}
/deep/.el-calendar-table.current:nth-last-child(-n+2).solar{
  color: red;
}
/deep/ .el-calendar__header {
  display: flex;
  justify-content: space-between;
  padding: 5px 5px;
}

/deep/ .el-calendar__body {
  padding: 0;
}
</style>
