module.exports = {
    //开启代理服务器
    devServer:{
        proxy:{
            '/api':{
                target:' http://localhost:8888/',
                // target:' http://123.56.226.42:8888', //服务器
                changeOrigin: true,//用于控制请求头中的host值。撒谎与服务器同号。默认为true
                pathRewrite:{//访问服务器地址时删除前缀
                    '^/api':''
                }
            },
            '/txApi':{
                target: 'https://view.inews.qq.com/',
                changeOrigin: true,
                pathRewrite: {
                    '^/txApi': ''
                }
            },
            '/wbApi':{
                target: 'https://interface.sina.cn/',
                changeOrigin: true,
                pathRewrite: {
                    '^/wbApi': ''
                }
            },
        }
    }
}

