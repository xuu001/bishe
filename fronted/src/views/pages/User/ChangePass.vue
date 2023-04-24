<template>
  <div class="background" >
    <div class=" login-panel" style="width: 300px;margin:100px auto">
      <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" class="demo-ruleForm">
        <el-form-item prop="pass">
          <el-input type="password" placeholder="请输入密码" suffix-icon="el-icon-lock" v-model="ruleForm.pass"
                    autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item prop="checkPass">
          <el-input type="password" placeholder="请输再次入密码" suffix-icon="el-icon-lock" v-model="ruleForm.checkPass"
                    autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="doRegister('ruleForm')" style="width: 100%;">确认修改</el-button>
          <span v-if="!userInfo">
          <!-- 这判断如果有session则不显示返回登录 若无 则认为是未登录修改密码显示返回登录  -->
            返回<a href="#" @click="toLogin">登录</a>
          </span>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>
<script>
export default {
  name: "CompanyChangePass",
  data() {
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
      userInfo:[],
      ruleForm: {
        pass: '',
        checkPass: '',
      },
      rules: {
        pass: [
          {validator: validatePass, trigger: 'blur'}
        ],
        checkPass: [
          {validator: validatePass2, trigger: 'blur'}
        ],
      }
    };
  },
  mounted() {
    this.userInfo = sessionStorage.getItem("userInfo")
  },

  methods: {
    doRegister() {
      this.$axios.post('/api/user/doChangePass', this.ruleForm).then(res => {
        if (res.data.status === 1) {
          this.$notify({
            title: '提示',
            message: '修改成功!',
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
  height: 70%;
  background-size: cover;
  background-position: center;
  /*position: fixed;*/
}

</style>
