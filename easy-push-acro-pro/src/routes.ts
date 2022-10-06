import { AuthParams } from '@/utils/authentication';
import { useState } from 'react';

export type IRoute = AuthParams & {
  name: string;
  key: string;
  // 当前页是否展示面包屑
  breadcrumb?: boolean;
  children?: IRoute[];
  // 当前路由是否渲染菜单项，为 true 的话不会在菜单中显示，但可通过路由地址访问。
  visible?: boolean;
  // 是否禁用
  disabled?: false
  icon?: string;
  details?: any;
  type?:string;
  resourceCode?:string;
};


export const staticRoutes: IRoute[] = [
  {
    name: "侧边导航",
    key: '',
    type: "M",
    visible:true,
    children:[
      {
        name: '首页',
        key: 'dashboard/workplace',
        type: "M",
        visible:true,
        children: [],
    },
    ]
  },
  {
    name: "顶部导航",
    key: '',
    type: "M",
    visible:true,
    children:[
      {
        name: '首页',
        key: 'dashboard/workplace',
        type: "M",
        visible:true,
        children: [],
    },
    ]
  },
  {
    name: '登录成功',
    key: 'loginSuccess',
    type: "M",
    visible:true,
    children:[]
  },
  {
    name: '登录失败',
    key: 'loginError',
    type: "M",
    visible:true,
    children:[]
  },
  {
    name: '讨论详情页',
    key: '/server/discussion/discussionInfo',
    type: "M",
    visible:true,
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
