<template>
  <div>
    <div style="height: auto; width: 100%; margin: 10px 0; border-radius: 5px;text-align: left">
      <el-form :inline="true" :model="param" label-width="100px">
        <el-form-item>
          <el-input v-model="param.account" placeholder="员工账号" clearable style="width: 200px"></el-input>
          <el-date-picker v-model="param.startDate" placeholder="入职开始时间" clearable
                          style="margin-left:10px;width: 200px"
                          format="yyyy 年 MM 月 dd 日" value-format="yyyy-MM-dd"></el-date-picker>
          <el-date-picker v-model="param.endDate" placeholder="入职结束时间" clearable
                          style="margin-left:10px;width: 200px"
                          format="yyyy 年 MM 月 dd 日" value-format="yyyy-MM-dd"></el-date-picker>
        </el-form-item>
        <el-button type="primary" @click="findUser">查询</el-button>
      </el-form>
    </div>
  </div>
</template>

<script>
export default {
  name: "UserAuthority"
}
</script>

<style scoped>

</style>