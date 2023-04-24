import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from "@/views/Login";

Vue.use(VueRouter)

const routes = [
    {
        //访问浏览器访问的路径
        path: '/',
        name: 'Login',
        //当访问到上述路径时，显示那个组件
        component: Login
    },
    {
        path: '/home',
        name: 'Home',
        component: () => import('../views/Home'),
        meta: {
            requireAuth: true,            // 添加该字段，表示进入这个路由是需要登
            title: '首页'
        },
        children: [
            {
                path: 'data',
                name: 'data',
                component: () => import('../views/pages/Data/Data'),
                meta: {
                    requireAuth: true,            // 添加该字段，表示进入这个路由是需要登
                    title: '今日数据'
                },
            },
            {
                path: 'epidemic',
                name: 'epidemic',
                component: () => import('../views/pages/Epidemic/Epidemic'),
                meta: {
                    requireAuth: true,            // 添加该字段，表示进入这个路由是需要登
                    title: '疫情数据展示'
                },
            },
            {
                path: 'department',
                name: 'department',
                component: () => import('../views/pages/UserManage/Department'),
                meta: {
                    requireAuth: true,            // 添加该字段，表示进入这个路由是需要登
                    title: '部门管理'
                },
            },
            {
                path: 'userInfo',
                name: 'data',
                component: () => import('../views/pages/User/UserInfo'),
                meta: {
                    requireAuth: true,            // 添加该字段，表示进入这个路由是需要登
                    title: '个人信息'
                },
            },
            {
                path: 'changePass',
                name: 'data',
                component: () => import('../views/pages/User/ChangePass'),
                meta: {
                    requireAuth: true,            // 添加该字段，表示进入这个路由是需要登
                    title: '修改密码'
                },
            },
        ]
    },
    {
        path: '/home',
        name: 'Home',
        component: () => import('../views/Home'),
        meta: {
            requireAuth: true,            // 添加该字段，表示进入这个路由是需要登
            title: '打卡管理'
        },
        children: [
            {
                path: 'daka',
                name: 'daKa',
                component: () => import('../views/pages/DaKa/Daka'),
                meta: {
                    requireAuth: true,            // 添加该字段，表示进入这个路由是需要登
                    title: '每日打卡'
                },
            },
            {
                path: 'dakalist',
                name: 'daKaList',
                component: () => import('../views/pages/DaKa/DaKaList'),
                meta: {
                    requireAuth: true,            // 添加该字段，表示进入这个路由是需要登
                    title: '我的记录'
                },
            },
            {
                path: 'dakaAllList',
                name: 'AllList',
                component: () => import('../views/pages/DaKa/AllList'),
                meta: {
                    requireAuth: true,            // 添加该字段，表示进入这个路由是需要登
                    title: '员工记录'
                },
            },
        ]
    },
    {
        path: '/home',
        name: 'home',
        component: () => import('../views/Home'),
        meta: {
            requireAuth: true,            // 添加该字段，表示进入这个路由是需要登
            title: '外出管理'
        },
        children: [
            {
                path: 'outApply',
                name: 'outApply',
                component: () => import('../views/pages/Out/OutApply'),
                meta: {
                    requireAuth: true,            // 添加该字段，表示进入这个路由是需要登
                    title: '外出申请'
                },
            },
            {
                path: 'outlist',
                name: 'outList',
                component: () => import('../views/pages/Out/OutList'),
                meta: {
                    requireAuth: true,            // 添加该字段，表示进入这个路由是需要登
                    title: '申请记录'
                },
            },
            {
                path: 'applications',
                name: 'applications',
                component: () => import('../views/pages/Out/OutApplications'),
                meta: {
                    requireAuth: true,            // 添加该字段，表示进入这个路由是需要登
                    title: '申请审核'
                },
            }
        ]
    },
    {
        path: '/home',
        name: 'home',
        component: () => import('../views/Home'),
        meta: {
            requireAuth: true,            // 添加该字段，表示进入这个路由是需要登
            title: '行程管理'
        },
        children: [
            {
                path: 'trip',
                name: 'trip',
                component: () => import('../views/pages/Trip/Trip'),
                meta: {
                    requireAuth: true,            // 添加该字段，表示进入这个路由是需要登
                    title: '行程上报'
                },
            },
            {
                path: 'triplist',
                name: 'tripList',
                component: () => import('../views/pages/Trip/TripList'),
                meta: {
                    requireAuth: true,            // 添加该字段，表示进入这个路由是需要登
                    title: '行程记录'
                },
            },
            {
                path: 'tripAllList',
                name: 'tripAllList',
                component: () => import('../views/pages/Trip/AllList'),
                meta: {
                    requireAuth: true,            // 添加该字段，表示进入这个路由是需要登
                    title: '员工记录'
                },
            }
        ]
    },
    {
        path: '/home',
        name: 'home',
        component: () => import('../views/Home'),
        meta: {
            requireAuth: true,            // 添加该字段，表示进入这个路由是需要登
            title: '员工管理'
        },
        children: [
            {
                path: 'user',
                name: 'userManage',
                component: () => import('../views/pages/UserManage/UserInformation'),
                meta: {
                    requireAuth: true,            // 添加该字段，表示进入这个路由是需要登
                    title: '员工管理'
                },
            },
            {
                path: 'authority',
                name: 'authority',
                component: () => import('../views/pages/User/UserRole'),
                meta: {
                    requireAuth: true,            // 添加该字段，表示进入这个路由是需要登
                    title: '员工权限'
                },
            },
        ]
    },
    {
        path: '/home',
        name: 'Home',
        component: () => import('../views/Home'),
        meta: {
            requireAuth: true,            // 添加该字段，表示进入这个路由是需要登
            title: '复工管理'
        },
        children: [
            {
                path: 'reWork',
                name: 'reWork',
                component: () => import('../views/pages/ReWork/ReWorkApply'),
                meta: {
                    requireAuth: true,            // 添加该字段，表示进入这个路由是需要登
                    title: '复工申请'
                },
            },
            {
                path: 'reWorkList',
                name: 'reWorkList',
                component: () => import('../views/pages/ReWork/ReWorkList'),
                meta: {
                    requireAuth: true,            // 添加该字段，表示进入这个路由是需要登
                    title: '申请记录'
                },
            },
            {
                path: 'reworkApplication',
                name: 'reworkApplication',
                component: () => import('../views/pages/ReWork/ReApplications'),
                meta: {
                    requireAuth: true,            // 添加该字段，表示进入这个路由是需要登
                    title: '复工审批'
                },
            },
        ]
    },
    {
        path: '/login',
        name: '用户登录',
        component: () => import('../views/Login')
    },
    {
        path: '/companyResetCode',
        name: '发送验证码',
        component: () => import('../views/pages/User/ResetCode')
    },
    {
        path: '/companyChangePass',
        name: '重置密码',
        component: () => import('../views/pages/User/ChangePass')
    }


]

const router = new VueRouter({
    routes
})

export default router
