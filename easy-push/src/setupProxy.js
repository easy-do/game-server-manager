const { createProxyMiddleware } = require('http-proxy-middleware');
 
// module.exports = function (app) { 
//     app.use(createProxyMiddleware('/api', {
//         // target: 'https://local.easydo.plus', //转发到的域名或者ip地址
//         target: 'http://localhost:8888', //转发到的域名或者ip地址
//         // target: 'https://manager.easydo.plus', //转发到的域名或者ip地址
//         pathRewrite: {
//             '^/api': '', //接口地址里没有"/api",将其重写置空
//         },
//         changeOrigin: true, //必须设置为true
//         secure: false //是否验证https的安全证书，如果域名是https需要配置此项
//     }));
// };