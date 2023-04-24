<template>
  <div class="background" style="height: 100%">
    <div class=" login-panel" style="margin: auto;width: 300px">
      <h2 style="text-align: center;margin-top: 160px">欢迎使用YUN提单系统</h2>
      <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" class="demo-ruleForm">
        <el-form-item prop="mail">
          <el-input placeholder="请输入邮箱" suffix-icon="el-icon-message" v-model="ruleForm.mail"
                    autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item prop="verify">
          <el-input placeholder="请输入验证码" suffix-icon="el-icon-lock" v-model="ruleForm.verify"
                    autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="doResetCode('ruleForm')" style="width: 100%;">发送验证码</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="doReset('ruleForm')" style="width: 100%;">重置密码</el-button>
          返回<a href="#" @click="toLogin">登录</a>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>
<script>
export default {
  name: "CompanyChangePass",
  data() {
    const checkMail = (rule, value, callback) => {
      if (!value) {
        return callback(new Error('请输入邮箱'));
      } else {
        callback();
      }
    };
    const checkVerify = (rule, value, callback) => {
      if (!value) {
        return callback(new Error('请输入验证码'));
      } else {
        callback();
      }
    };
    return {
      ruleForm: {
        verify: '',
        mail: '',
      },
      rules: {
        mail: [
          {required:true,validator: checkMail, trigger: 'blur'}
        ],
        verify: [
          {validator: checkVerify, trigger: 'change'}
        ],
      }
    };
  },
  methods: {
    doResetCode() {
      // this.$refs[ruleForm].validate((ruleForm) => {
      //   if (ruleForm.mail) {
          this.$axios.get('/api/user/resetCode', {params: this.ruleForm}).then(res => {
            console.log(this.ruleForm)
            if (res.data.status === 1) {
              this.$notify({
                title: '提示',
                message: '发送成功!',
                type: 'success'
              })
            } else {
              this.$notify({
                title: '提示',
                message: res.data.message,
                type: 'error'
              })
            }
          })
        // }else {
        //   return false
        // }
      // })

    },
    doReset(ruleForm) {
      this.$refs[ruleForm].validate((valid) => {
        if (valid) {
          this.$router.push('/companyChangePass')
        } else {
          return false;
        }
      });
    },
    toLogin() {
      this.$router.push('/login');
    }
  }
}
</script>

<style scoped>

.background {
  width: 100%;
  height: 100%;
  background-size: cover;
  background-position: center;
  position: fixed;
}

</style>
