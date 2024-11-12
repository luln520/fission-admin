/*
 *  ajax请求
 *
 * @Author:    1024创新实验室-主任：卓大
 * @Date:      2022-09-06 20:46:03
 * @Wechat:    zhuda1024
 * @Email:     lab1024@163.com
 * @Copyright  1024创新实验室 （ https://1024lab.net ），Since 2012
 */

import axios, { AxiosRequestConfig } from 'axios';
import { getTokenFromCookie } from '/@/utils/cookie-util';
import { message } from 'ant-design-vue';
import { clearAllCoolies } from '/@/utils/cookie-util';
import { smartSentry } from '/@/lib/smart-sentry';
import { localClear } from '/@/utils/local-util';

const TOKEN_HEADER: string = 'x-access-token';
const hostname = window.location.hostname;
const apiUrlMap = {
  "localhost": "http://localhost:1025",//"http://127.0.0.1:1084",
  '206.238.199.169': "http://206.238.199.169:1024",
  "2024.pz167.com":'https://2024api.pz167.com',
  "2024.bic6699.com":'https://api.bic6699.com',
}
export const BASE_API_URL = apiUrlMap[hostname] ? apiUrlMap[hostname] : `https://${hostname}`.replace("2024.","api.");
export const BASE_IMG_URL = BASE_API_URL.replace(":1024", '');
//基础请求地址
//export const BASE_API_URL = "http://127.0.0.1:1084"
// export const BASE_API_URL = "http://206.238.199.169:1024"
// export const BASE_IMG_URL = "http://206.238.199.169"
// export const BASE_API_URL = "https://2024api.pz167.com"
// export const BASE_API_URL = "https://api.bic6699.com"
// export const BASE_IMG_URL = BASE_API_URL

const smartAxios = axios.create({
  baseURL: BASE_API_URL,
});

// ================================= 请求拦截器 =================================

smartAxios.interceptors.request.use(
  (config) => {
    // 在发送请求之前消息头加入token token
    const token = getTokenFromCookie();
    let companyId=localStorage.getItem("companyId");
    if (token) {
      config.headers[TOKEN_HEADER] = token;
    } else {
      delete config.headers[TOKEN_HEADER];
    }
    //公司id添加
    if (companyId) {
      companyId=parseInt(companyId);
      //get添加companyId
      if (config.method === "get" || config.method === "GET") {
        if (!config.params) {
          config.params = {}
        }
        config.params["companyId"] = companyId;
      }
      //post添加companyId
      if (config.method === "post" || config.method === "POST") {
        if (!config.data) {
          config.data = {};
        }
        config.data.companyId = companyId;
      }
    }
    return config;
  },
  (error) => {
    // 对请求错误做些什么
    return Promise.reject(error);
  }
);

// ================================= 响应拦截器 =================================

// 添加响应拦截器
smartAxios.interceptors.response.use(
  (response) => {
    // 对响应数据做点什么
    const res = response.data;
    if (res.code && res.code !== 1) {
      // `token` 过期或者账号已在别处登录
      if (res.code === 30007 || res.code === 30008) {
        message.error('您没有登录，请重新登录');
        clearAllCoolies();
        localClear();
        //跳转到登录页面，直接使用页面刷新的策略
        setTimeout(() => {
          location.href = '/';
        }, 300);
        return Promise.resolve(res);
      }
      if (res.code === 30005) {
        message.error(res.msg);
      }
      // message.error(res.msg);
      return Promise.resolve(res);
    } else {
      return Promise.resolve(res);
    }
  },
  (error) => {
    // 对响应错误做点什么
    if (error.message.indexOf('timeout') != -1) {
      message.error('网络超时');
    } else if (error.message == 'Network Error') {
      message.error('网络连接错误');
    } else if (error.message.indexOf('Request') != -1) {
      message.error('网络发生错误');
    }
    return Promise.reject(error);
  }
);

// ================================= 对外提供请求方法：通用请求，get， post, 下载download等 =================================

/**
 * 通用请求封装
 * @param config
 */
export const request = <T = any>(config: AxiosRequestConfig): Promise<T> => {
  return smartAxios.request(config);
};

/**
 * post请求
 */
export const postRequest = <T = any>(url: string, data: any): Promise<T> => {
  return request({ data, url, method: 'post' });
};

/**
 * get请求
 */
export const getRequest = <T = any>(url: string, params?: any): Promise<T> => {
  return request({ url, method: 'get', params });
};

/**
 * 下载
 */
export const download = function (fileName: string, url: string, params?: any): void {
  request({
    method: 'get',
    url: url,
    params: params,
    responseType: 'blob',
  })
    .then((data) => {
      if (!data) {
        return;
      }
      let url = window.URL.createObjectURL(new Blob([data]));
      let link = document.createElement('a');
      link.style.display = 'none';
      link.href = url;
      link.setAttribute('download', fileName);
      document.body.appendChild(link);
      link.click();
    })
    .catch((error) => {
      smartSentry.captureException(error);
    });
};
