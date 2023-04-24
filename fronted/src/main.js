import Vue from 'vue'
import App from './App.vue'
import router from './router'
import axios from "axios";
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import BaiduMap from 'vue-baidu-map'
Vue.config.productionTip = false
//将所有的ElementUI组件，全局注册
Vue.use(ElementUI);
//全局注册axios
Vue.prototype.$axios = axios;
Vue.use(BaiduMap, {
  /* https://lbsyun.baidu.com/apiconsole/key注册百度地图开发者来获取你的ak */
  ak: 'RBvvbbpHcaZEm5djBUjZG868oivTntTt'
})
//  挂载路由导航守卫
router.beforeEach((to, from, next) => {
  if (to.meta.requireAuth) {// 判断该路由是否需要登录权限
    // let menu = JSON.parse(sessionStorage.getItem('menu'))
    // let userInfo= JSON.parse(sessionStorage.getItem('userInfo'))
    // console.log(menu[0].id)
    // console.log(userInfo.menu[0].id)
    // if(menu === userInfo.menu){  //判断本地是否存在access_token
    if(JSON.parse(sessionStorage.getItem('menu'))){  //判断本地是否存在access_token
        next();
    }else {
      next({
        path:'/login'
      })
    }
  }
  else {
    next();
  }
  /*如果本地 存在 token 则 不允许直接跳转到 登录页面*/
  // if(to.fullPath == "/login"){
  //   if(JSON.parse(sessionStorage.getItem('menu'))){
  //     next({
  //       path:from.fullPath
  //     });
  //   }else {
  //     next();
  //   }
  // }
});

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
