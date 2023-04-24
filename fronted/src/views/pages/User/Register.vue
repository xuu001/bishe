<template>
  <div class="background" style="height: 100%">
    <div class=" login-panel" style="margin: auto;width: 300px">
      <h2 style="text-align: center;margin-top: 50px">欢迎使用YUN提单系统</h2>
      <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" class="demo-ruleForm">
        <el-form-item prop="account">
          <el-input placeholder="请输入用户名" suffix-icon="el-icon-user" v-model="ruleForm.account"
                    autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item prop="mail">
          <el-input placeholder="请输入邮箱" suffix-icon="el-icon-message" v-model="ruleForm.mail"
                    autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item prop="company">
          <el-input placeholder="请输入企业名称" suffix-icon="el-icon-office-building" v-model="ruleForm.company"
                    autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item prop="pass">
          <el-input type="password" placeholder="请输入密码" suffix-icon="el-icon-lock" v-model="ruleForm.pass"
                    autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item prop="checkPass">
          <el-input type="password" placeholder="请输再次入密码" suffix-icon="el-icon-lock" v-model="ruleForm.checkPass"
                    autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="doRegister('ruleForm')" style="width: 100%;">注 册</el-button>
          已有企业账号？去<a href="#" @click="toLogin">登录</a>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>
<script>
export default {
  name: "CompanyRegister",
  data() {
    const checkAccount = (rule, value, callback) => {
      if (!value) {
        return callback(new Error('请输入用户名'));
      } else {
        callback();
      }
    };
    const checkMail = (rule, value, callback) => {
      if (!value) {
        return callback(new Error('请输入邮箱'));
      } else {
        callback();
      }
    };
    const checkCompany = (rule, value, callback) => {
      if (!value) {
        return callback(new Error('请输入企业名称'));
      } else {
        callback();
      }
    };
    const validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入密码'));
      } else {
        if (this.ruleForm.checkPass !== '') {
          this.$refs.ruleForm.validateField('checkPass');
        }
        callback();
      }
    };
    const validatePass2 = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'));
      } else if (value !== this.ruleForm.pass) {
        callback(new Error('两次输入密码不一致!'));
      } else {
        callback();
      }
    };
    return {
      ruleForm: {
        account: '',
        mail: '',
        company: '',
        pass: '',
        checkPass: '',
      },
      rules: {
        mail: [
          {validator: checkMail, trigger: 'blur'}
        ],
        company: [
          {validator: checkCompany, trigger: 'blur'}
        ],
        pass: [
          {validator: validatePass, trigger: 'blur'}
        ],
        checkPass: [
          {validator: validatePass2, trigger: 'blur'}
        ],
        account: [
          {validator: checkAccount, trigger: 'blur'}
        ],
      }
    };
  },

  methods: {
    doRegister(ruleForm) {
      this.$refs[ruleForm].validate((valid) => {
        if (valid) {
          this.$axios.post('/api/company/register', this.ruleForm).then(res => {
            if (res.data.status === 1) {
              this.$notify({
                title: '提示',
                message: '注册成功!',
                type: 'success'
              })
              this.$router.push('/login');
            } else {
              this.$notify({
                title: '提示',
                message: res.data.message,
                type: 'error'
              })
            }
          })
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
