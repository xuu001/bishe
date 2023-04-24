<template>
  <el-row class="top_bar">
    <el-col :span="1">
      <img src="../img/img.png" alt="" height="55" style="margin-top: 2px;text-align: left">
    </el-col>
    <el-col :offset="16" :span="7">
      <el-row>
        <el-col :offset="7" :span="4">
          <div class="top_btn">
            <el-icon :size="10" class="el-icon-search"></el-icon>
            <span class="">搜索</span>
          </div>
        </el-col>
        <el-col :span="4">
          <div class="top_btn">
            <el-icon :size="10" class="el-icon-help"></el-icon>
            <span>帮助</span>
          </div>
        </el-col>
        <el-col :span="4">
          <el-dropdown @command="clickSelect">
            <div class="top_btn">
              <el-icon :size="10" class="el-icon-user"></el-icon>
              <span>{{ userInfo.name }}</span>
            </div>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item command="toUserInfo">个人信息</el-dropdown-item>
                <el-dropdown-item>系统消息<el-badge :value="2"/> </el-dropdown-item>
                <el-dropdown-item command="toRestPass">修改密码</el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </el-col>
        <el-col :span="4">
          <div class="top_btn" @click="logout">
            <el-icon :size="10" class="el-icon-open">
            </el-icon>
            <span>退出</span>
          </div>
        </el-col>
      </el-row>
    </el-col>
  </el-row>
</template>

<script>
export default {
  name: "HeaderComponent",
  props: ['userInfo'],
  data() {
    return {}
  },
  create() {

  },
  methods:{
    logout(){
      this.$confirm('确定退出系统吗', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() =>{
        sessionStorage.clear();
        this.$router.push('/')
      })
    },
    clickSelect(command){
      if (command === "toUserInfo"){
        console.log("点击了个人信息")
        this.$router.push('/home/userInfo')
      }
      if (command === "toRestPass"){
        console.log("点击了修改密码")
        this.$router.push('/home/changePass')
      }
    },
    toUserInfo(){

    },
    toRestPass(){
      console.log("修改密码！！！")
      this.$router.push('/changePass')
    }
  }

}
</script>

<style >
.top_bar {
  background: #fdfdfd;
  height: 50px;
  text-align: center;
}

.top_btn {
  cursor: pointer;
  line-height: 60px;
  text-align: center;
  color: #000000;
}

.top_btn i {
  position: relative;
  top: 2px;
}

.top_btn span {
  margin-left: 5px;
}

.top_btn:hover {
  color: #409EFF;
}
</style>
