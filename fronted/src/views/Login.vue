<template>
  <div class="login">
    <div style="display:flex;justify-content:space-between;align-items: center;">
      <div style="padding: 0 60px 0 0">
        <h3 style="color: #ffffff;font-size: 24px;padding: 0;margin-bottom: 10px">
          HHAO企业疫情防控系统
        </h3>
        <ul style="margin: 0;color: #ffffff;font-size: 15px;line-height: 25px;padding-left: 20px">
          <li>Spring</li>
          <li>SpringMVC</li>
          <li>Mybatis</li>
          <li>ElementUI</li>
          <li>MySQL</li>
          <li>Vue</li>
        </ul>
        <h3 style="color: #ffffff;">
          欢迎使用
        </h3>
      </div>
      <el-form ref="loginForm" :model="user" :rules="rules" label-position="left" label-width="0px"
               class="login-form">
        <h3 class="title">
          HHAO企业疫情防控系统
        </h3>
        <el-form-item prop="account">
          <el-input v-model="user.account" type="text" auto-complete="off" suffix-icon="el-icon-user"
                    placeholder="请输入工号">
          </el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input v-model="user.password" type="password" auto-complete="off" suffix-icon="el-icon-lock"
                    placeholder="请输入密码" @keyup.enter.native="doLogin" show-password>
          </el-input>
        </el-form-item>
        <el-form-item prop="code">
          <el-input v-model="user.verify" auto-complete="off" suffix-icon="el-icon-key" placeholder="请输入验证码"
                    style="width: 65%" @keyup.enter.native="doLogin">
          </el-input>
          <div class="login-code">
            <img :src="verifyCode" @click="getVerifySrc">
          </div>
        </el-form-item>
        <el-checkbox v-model="user.remember" style="margin:0 0 25px 0;">记住我</el-checkbox>
        <el-link style="margin-left: 61%" @click="toResetCode">忘记密码</el-link>
        <el-form-item style="width:100%;">
          <el-button size="medium" type="primary" style="width:100%;" @click.native.prevent="doLogin('ruleForm')">登 录
          </el-button>
        </el-form-item>
      </el-form>
    </div>
    <!--  底部  -->
    <!-- <div v-if="settings.showFooter" id="el-login-footer">
      <span v-html="settings.footerTxt"/>
      <span> ⋅ </span>
      <a href="http://wov.cn" target="_blank">{{ settings.caseNumber }}</a>
    </div> -->
  </div>
</template>


<script>
export default {
  name: "Login",
  data: function () {
    return {
      user: {
        account: null,
        password: null
      },
      verifyCode: '/api/user/verifyCode',
      settings: {
        showFooter: true,
        footerTxt: '© 2020-2022 HHANO企业疫情防控系统 <a href="https://gitee.com/hhangvip/EEPC" target="_blank">All Rights Reserved</a>',
        caseNumber: '豫ICP备2020030914号-1'
      },
      rules: {
        account: [
          {required: true, message: '用户名不能为空', trigger: ['change', 'blur']}
        ],
        password: [
          {required: true, message: '密码不能为空', trigger: ['change', 'blur']},
          {min: 6, message: '密码不能小于6', trigger: ['change', 'blur']}
        ],
        verify: [
          {required: true, message: '验证码不能为空', trigger: ['change', 'blur']}
        ]
      }
    }
  },
  created() {
  },
  methods: {

    doLogin() {
      this.$refs.loginForm.validate(res => {
        if (res) {
          this.$axios.post('/api/user/tologin', this.user).then(res => {
            if (res.data.status === 1) {
              sessionStorage.setItem("menu", JSON.stringify(res.data.data.menu));
              sessionStorage.setItem("userInfo", JSON.stringify(res.data.data))
              this.$router.push('/home/data');
            } else {
              this.$notify({
                title: '提示',
                message: res.data.message,
                type: 'error'
              })
            }
          }).catch(e => {
            console.log(e)
            this.$notify({
              title: '提示',
              message: '请求失败',
              type: 'error'
            })
          })
        }
      })
    },
    toRegister() {
      this.$router.push('/companyRegister');
    },
    getVerifySrc() {
      this.verifyCode = '/api/user/verifyCode?' + new Date();
    },
    toResetCode() {
      this.$router.push('/companyResetCode')
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss">
@import "../assets/styles/index";
.login {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100% ;
  background-image: url("../img/bg1.webp");
  animation: bag 12s infinite;
}
@keyframes bag {
  0%{
    background-image: url("../img/bg1.webp");background-size: 100% 100%;background-repeat: no-repeat
  }
  33%{
    background-image: url("../img/bg2.webp");background-size: 100% 100%;background-repeat: no-repeat
  }
  66%{
    background-image: url("../img/bg3.webp");background-size: 100% 100%;background-repeat: no-repeat
  }
  100%{
    background-image: url("../img/bg4.webp");background-size: 100% 100%;background-repeat: no-repeat
  }
}
.title {
  margin: 0 auto 30px auto;
  text-align: center;
  color: #707070;
}

.login-form {
  border-radius: 6px;
  background: #ffffff;
  width: 385px;
  padding: 25px 25px 5px 25px;
  .el-input {
    height: 38px;
    input {
      height: 38px;
    }
  }
}

.login-tip {
  font-size: 13px;
  text-align: center;
  color: #bfbfbf;
}

.login-code {
  width: 33%;
  display: inline-block;
  height: 38px;
  float: right;
  img {
    width: 100%;
    height: 101%;
    cursor: pointer;
    vertical-align: middle
  }
}

.footer {
  bottom: 0;
  position: fixed;
  height: 40px;
  line-height: 40px;
  text-align: center;
  color: #fff;
  font-family: Arial, serif;
  font-size: 12px;
  letter-spacing: 1px;
}

</style>
