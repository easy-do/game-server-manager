const MonacoWebpackPlugin = require('monaco-editor-webpack-plugin');
const {
  override,
} = require('customize-cra');

module.exports = override(
  (config) => {
    //暴露webpack的配置
    // 去掉打包生产map 文件
    if (process.env.NODE_ENV === "production") config.devtool = false;
    //react-monaco-editor的打包加载
    config.plugins.push(new MonacoWebpackPlugin({
      languages: ['apex', 'azcli', 'bat', 'clojure', 'coffee', 'cpp', 'csharp', 'csp', 'css', 'dockerfile', 'fsharp', 'go', 'handlebars', 'html', 'ini', 'java', 'javascript', 'json', 'less', 'lua', 'markdown', 'msdax', 'mysql', 'objective', 'perl', 'pgsql', 'php', 'postiats', 'powerquery', 'powershell', 'pug', 'python', 'r', 'razor', 'redis', 'redshift', 'ruby', 'rust', 'sb', 'scheme', 'scss', 'shell', 'solidity', 'sql', 'st', 'swift', 'typescript', 'vb', 'xml', 'yaml']
    }));
    return config
  }
);

