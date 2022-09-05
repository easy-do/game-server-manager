import axios from "axios";
import { Component } from "react";
import { Toast } from '@douyinfe/semi-ui';
import cookie from 'react-cookies'
// let baseUrl = process.env.NODE_ENV == 'development' ? "/api" : "https://manager.easydo.plus";
let baseUrl = "/api";

// 请求前拦截
axios.interceptors.request.use(
  config => {
    return config;
  },
  err => {
    console.log("请求超时");
    Toast.warning({ content: '请求超时.', duration: 3 })
    return Promise.reject(err);
  }
);

// 返回后拦截
axios.interceptors.response.use(
  data => {
    const resultcode = data.data.code;
    const errorCode = data.data.errorCode;
    if (!data.data.success) {
      if (resultcode === 403 || errorCode === 'AUTH_ERROR') {
        Toast.warning({ content: data.data.errorMessage, duration: 3 })
        cookie.remove('secret');
        cookie.remove('token');
        localStorage.clear()
        sessionStorage.clear()
        window.location.pathname = '/home'
      } else {
        Toast.warning({ content: data.data.errorMessage, duration: 3 })
      }
    }
    return data;
  },
  err => {
    if (err.response.status !== 200) {
      Toast.warning({ content: err.response.status + '请求异常.', duration: 3 })
    }
    return Promise.reject(err);
  }
);

// @RequestBody请求
export const postRequestBody = (url, params) => {
  return axios({
    method: "post",
    url: `${baseUrl}${url}`,
    data: params,
    headers: {
      "secret": localStorage.getItem("secret") ? localStorage.getItem("secret") : "",
      "token": localStorage.getItem("token") ? localStorage.getItem("token") : "",
      "Content-Type": "application/json",
      charset: "utf-8"
    }
  });
};

// @RequsetParam请求
export const postRequestParam = (url, params) => {
  return axios({
    method: "post",
    url: `${baseUrl}${url}`,
    data: params,
    transformRequest: [
      function (data) {
        let ret = "";
        for (let it in data) {
          ret +=
            encodeURIComponent(it) + "=" + encodeURIComponent(data[it]) + "&";
        }
        return ret;
      }
    ],
    headers: {
      "secret": localStorage.getItem("secret") ? localStorage.getItem("secret") : "",
      "token": localStorage.getItem("token") ? localStorage.getItem("token") : "",
      "Content-Type": "application/x-www-form-urlencoded"
    }
  });
};

// @form-data请求
export const postFormData = (url, params) => {
  return axios({
    method: "post",
    url: `${baseUrl}${url}`,
    data: params,
    headers: {
      "secret": localStorage.getItem("secret") ? localStorage.getItem("secret") : "",
      "token": localStorage.getItem("token") ? localStorage.getItem("token") : "",
      "Content-Type": "multipart/form-data"
    }
  });
};

export const get = url => {
  return axios({
    method: "get",
    url: `${baseUrl}${url}`,
    headers: {
      "secret": localStorage.getItem("secret") ? localStorage.getItem("secret") : "",
      "token": localStorage.getItem("token") ? localStorage.getItem("token") : "",
      "Content-Type": "text/plain"
    }
  });
};

export const multiple = function (requsetArray, callback) {
  axios.all(requsetArray).then(axios.spread(callback));
};

Component.prototype.get = get;
Component.prototype.postRequestBody = postRequestBody;
Component.prototype.postRequestParam = postRequestParam;
Component.prototype.postFormData = postFormData;
Component.prototype.multiple = multiple;
