export default {
    /**
     * 校验密码是否为空，且不少6位
     * @param rule
     * @param value
     * @param callback
     */
    validatePass(rule, value, callback) {
        if (value === '') {
            callback(new Error('请输入密码'));
        } else if (value.length < 6) {
            callback(new Error('密码不得少于6位'));
        } else {
            callback();
        }
    },
    /**
     *
     * @param rule 校验手机号是否合法
     * @param value
     * @param callback
     */
    checkPhone(rule,value,callback){
        let phone = /^1[3456789]\d{9}$/
        if (!phone.test(value)){
            callback(new Error('手机号不合法'))
        } else {
            callback()
        }
    }
}


