import { AuthParams } from '@/utils/authentication';
import { useState } from 'react';

export type IRoute = AuthParams & {
  name: string;
  key: string;
  url: string;
  //启用状态 0 启用
  status?: number;
  icon?: string; 
  //图标类型 0-组件（默认）、1-图片
  iconType?: number;
  // // 当前页是否展示面包屑
  breadcrumb?: boolean;
  children?: IRoute[];
};


export const staticRoutes: IRoute[] = [
  {
    name: "侧边导航",
    key: 'sideNavigate',
    url: '',
    status:1,
    children:[
      {
        name: '首页',
        key: 'home',
        url: 'dashboard/workplace',
        status:0,
        children: [],
    },
    ]
  },
  {
    name: "顶部导航",
    key: 'topNavigate',
    url: '',
    status:1,
    children:[
      {
        name: '首页',
        key: '',
        url: 'dashboard/workplace',
        status:0,
        children: [],
    },
    ]
  },
  {
    name: '登录成功',
    key: '',
    url: 'loginSuccess',
    status:1,
    children:[]
  },
  {
    name: '登录失败',
    key: '',
    url: 'loginError',
    status:1,
    children:[]
  },
  {
    name: '讨论详情页',
    key: '',
    url: '/server/discussion/discussionInfo',
    status:1,
    children:[]
  },
  {
    name: '应用版本详情页',
    key: '',
    url: '/server/applicationVersionDetails',
    status:1,
    children:[]
  },
];

export const defaultRoute = "dashboard/workplace"


export const getRoutes = () =>{
  const menu = localStorage.getItem('userMenu');
    if(menu){
      return JSON.parse(menu);
    }
    return staticRoutes;
  }
