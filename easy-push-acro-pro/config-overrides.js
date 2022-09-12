/* eslint-disable @typescript-eslint/no-var-requires */
const path = require('path');
const {
  override,
  addWebpackModuleRule,
  addWebpackPlugin,
  addWebpackAlias,
} = require('customize-cra');
const ArcoWebpackPlugin = require('@arco-plugins/webpack-react');
const addLessLoader = require('customize-cra-less-loader');
const setting = require('./src/settings.json');
const MonacoWebpackPlugin = require('monaco-editor-webpack-plugin');

module.exports = {
  webpack: override(
    addLessLoader({
      lessLoaderOptions: {
        lessOptions: {},
      },
    }),
    addWebpackModuleRule({
      test: /\.svg$/,
      loader: '@svgr/webpack',
    }),
    addWebpackPlugin(
      new ArcoWebpackPlugin({
        theme: '@arco-themes/react-arco-pro',
        modifyVars: {
          'arcoblue-6': setting.themeColor,
        },
      })
    ),
    addWebpackPlugin(
      //react-monaco-editor的打包加载
      new MonacoWebpackPlugin({
        languages: ['apex', 'azcli', 'bat', 'clojure', 'coffee', 'cpp', 'csharp', 'csp', 'css', 'dockerfile', 'fsharp', 'go', 'handlebars', 'html', 'ini', 'java', 'javascript', 'json', 'less', 'lua', 'markdown', 'msdax', 'mysql', 'objective', 'perl', 'pgsql', 'php', 'postiats', 'powerquery', 'powershell', 'pug', 'python', 'r', 'razor', 'redis', 'redshift', 'ruby', 'rust', 'sb', 'scheme', 'scss', 'shell', 'solidity', 'sql', 'st', 'swift', 'typescript', 'vb', 'xml', 'yaml']
      })
    ),
    addWebpackAlias({
      '@': path.resolve(__dirname, 'src'),
    })
  ),
};
