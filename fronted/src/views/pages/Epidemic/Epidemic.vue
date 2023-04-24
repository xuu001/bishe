<template>
  <div>
    <el-container>
      <h2>疫情数据中心</h2>
      <el-container>
            <news-component></news-component>
        <el-main>
          <el-card>
            <china-map></china-map>
          </el-card>
          </el-main>
      </el-container>
    </el-container>
  </div>

</template>

<script>
import ChinaMap from "@/components/ChinaMap";
import NewsComponent from "@/components/NewsComponent";
export default {
  name: "Epidemic",
  components: {NewsComponent, ChinaMap}
}
</script>

<style scoped>

</style>
