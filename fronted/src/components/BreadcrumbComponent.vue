<template>
  <el-breadcrumb separator-class="el-icon-arrow-right" style="height: 30px;border-bottom: #333333 1px">
    <el-breadcrumb-item v-for="item in breadList" :to="{path:item.path}" :key="item.name">{{ item.meta.title }}
    </el-breadcrumb-item>
  </el-breadcrumb>
</template>

<script>
export default {
  name: "breadcrumb",
  created() {
    this.getBreadcrumb()
  },
  data() {
    return {
      breadList: []
    }
  },
  watch: {
    $route() {
      this.getBreadcrumb()
    }
  },
  methods: {
    getBreadcrumb() {
      let matched = this.$route.matched.filter(item => item.name)
      this.breadList = matched
    }
  }
}
</script>

<style scoped>

</style>
