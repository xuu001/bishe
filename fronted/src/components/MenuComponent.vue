<template>
  <div>
    <template v-for="row of menu">
      <el-submenu v-if="row.type===0" :key="row.id" :index="row.text">
        <template #title>
          <i :class="row.icon"></i>
          <span>{{ row.text }}</span>
        </template>
        <menu-component v-if="row.type===0" :menu="row.children"></menu-component>
      </el-submenu>

      <el-menu-item v-else :key="row.id" :index="row.text" :route="row.url">
        <i :class="row.icon"></i>
        <template #title>
          <span>{{ row.text }}</span>
        </template>
      </el-menu-item>
    </template>
  </div>
</template>

<script>
export default {
  name: "MenuComponent",
  props: ['menu'],
}
</script>

<style lang="scss">
ul li {
  text-align: left;
}

/* 修复菜单折叠 带子菜单的父菜单文字不隐藏 */
.el-menu--collapse .el-submenu__title span {
  display: none;
}

/* 修复菜单折叠 带子菜单的父菜右箭头不隐藏 */
.el-menu--collapse .el-submenu__title .el-submenu__icon-arrow{
  display: none;
}
</style>
