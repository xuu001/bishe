<template>
  <!--.vue单文件主件-->
  <div class="home">
    <el-container>
      <el-header>
        <header-component :user-info="userInfo"></header-component>
      </el-header>

      <el-container>
        <el-aside width="auto">
          <el-menu router :unique-opened="true" class="el-menu-vertical-demo" :collapse="collapse">
            <menu-component :menu="menuList"></menu-component>
          </el-menu>
        </el-aside>
        <el-container>
          <el-main >
            <div>
              <el-col :span="1" >
                <el-tooltip :content="collapse?'展开':'折叠'">
                  <i v-if="!collapse" class="el-icon-s-fold" @click="isCollapse" style="font-size: 20px;margin-top: -5px"></i>
                  <i v-if="collapse" class="el-icon-s-unfold" @click="isCollapse" style="font-size: 20px;margin-top: -5px"></i>
                </el-tooltip>
              </el-col>
              <breadcrumb-component></breadcrumb-component>
            </div>
            <router-view/>
          </el-main>
          <el-footer>
            <footer-component></footer-component>
          </el-footer>
        </el-container>
      </el-container>
    </el-container>
  </div>
</template>

<script>
// @ is an alias to /src
import MenuComponent from "@/components/MenuComponent";
import BreadcrumbComponent from "@/components/BreadcrumbComponent";
import FooterComponent from "@/components/FooterComponent";
import HeaderComponent from "@/components/HeaderComponent";

export default {
  name: 'Home',
  // 2. 在当前页面声明,我将会引用哪些组件
  components: {
    HeaderComponent,
    FooterComponent,
    MenuComponent,
    BreadcrumbComponent
  },
  data: function () {
    return {
      collapse: false,
      menu: [],
      menuList: [],
      userInfo: [],
      company: '',
      getDate: [],

    }
  },
  mounted() {
  },
  created() {
    this.initMenu();
    this.user();
    this.get();
  },
  methods: {
    initMenu() {
      let menu = sessionStorage.getItem('menu');
      menu = JSON.parse(menu);
      this.menu = menu;
      let menuList = [];
      let map = {}
      this.menu.sort((a, b) => a.pid - b.pid)
      this.menu.forEach(res => {
        res.children = []
        map[res.id] = res
        if (!res.pid) {
          menuList.push(res)
        } else {
          if (map[res.pid]) {
            map[res.pid].children.push(res)
          }
        }
      })
      this.menuList = menuList
    },
    inMenu(){
      this.menu=JSON.parse(sessionStorage.getItem("menu"))
      this.userInfo=JSON.parse(sessionStorage.getItem("userInfo"))

      let rootMenus = [];
      let map = {};
      //排序
      this.menu.sort((a, b) => a.pid - b.pid);
      this.menu.forEach(item => {
        map[item.id] = item;
        item.children = [];
      });
      this.menu.forEach(resource => {
        // map[resource.id] = resource;
        if (!resource.pid) {
          rootMenus.push(resource);
        } else {
          if (map[resource.pid]) {
            map[resource.pid].children.push(resource)
          } else {
            throw new Error(`当前循环的数据pid=${resource.pid}有错误`);
          }
        }
      })
      this.menuList=rootMenus;
      let menuTree=JSON.stringify(this.menu);
      console.log(menuTree);
      // sessionStorage.setItem("menuTree", menuTree);
    },
    isCollapse(){
      this.collapse = !this.collapse
    },
    get() {
      this.$axios.get('/txApi/g2/getOnsInfo?name=disease_h5').then(res => {
        console.log("111111111111111");
        this.getDate = res.data.data;
        // console.log(res.data.data)
      })

    },
    user() {
      let userInfo = JSON.parse(sessionStorage.getItem('userInfo'))
      this.userInfo = userInfo;
      this.name = userInfo.name
    }
  }
}
</script>

<style lang="scss">
.el-footer {
  background: #222c3a;
  color: #333;
  text-align: center;
  line-height: 50px;
  border-top: 1px solid gray !important;
  border-collapse: collapse;
}

.el-header {
  color: #333;
  text-align: center;
  line-height: 50px;
  border-bottom: 1px solid #aaaaaa !important;
  border-collapse: collapse;
}

.el-aside {
  background-color: #f5f4f4;
  color: #333;
  text-align: center;
}

.el-main {
  /*background-color: #E9EEF3;*/
  color: #333;
  text-align: center;
  height: 680px;
}

.el-menu:not(.el-menu--collapse){
  /* 控制菜单宽度保持动画不变 */
  width: 200px;
}

.el-menu-item.is-active {
  background-color: #409EFF !important;
  color: #fff;
}

.el-table td, .el-table th {
  padding: 9px 0;
}

body > .el-container {
  margin-bottom: 40px;
}

</style>
