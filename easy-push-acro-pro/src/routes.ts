import auth, { AuthParams } from '@/utils/authentication';
import { useEffect, useMemo, useState } from 'react';

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
    name: 'menu.visualization',
    key: 'visualization',
    type: "M",
    children: [
      {
        name: 'menu.visualization.dataAnalysis',
        key: 'visualization/data-analysis',
        type: "M",
        requiredPermissions: [
          { resource: 'menu.visualization.dataAnalysis', actions: ['read'] },
        ],
      },
      {
        name: 'menu.visualization.multiDimensionDataAnalysis',
        key: 'visualization/multi-dimension-data-analysis',
        type: "M",
        requiredPermissions: [
          {
            resource: 'menu.visualization.dataAnalysis',
            actions: ['read', 'write'],
          },
          {
            resource: 'menu.visualization.multiDimensionDataAnalysis',
            actions: ['write'],
          },
        ],
        oneOfPerm: true,
      },
    ],
  },
  {
    name: 'menu.list',
    key: 'list',
    type: "M",
    children: [
      {
        name: 'menu.list.searchTable',
        key: 'list/search-table',
        type: "M",
      },
      {
        name: 'menu.list.cardList',
        key: 'list/card',
        type: "M",
      },
    ],
  },
  {
    name: 'menu.form',
    key: 'form',
    type: "M",
    children: [
      {
        name: 'menu.form.group',
        key: 'form/group',
        type: "M",
        requiredPermissions: [
          { resource: 'menu.form.group', actions: ['read', 'write'] },
        ],
      },
      {
        name: 'menu.form.step',
        key: 'form/step',
        type: "M",
        requiredPermissions: [
          { resource: 'menu.form.step', actions: ['read'] },
        ],
      },
    ],
  },
  {
    name: 'menu.profile',
    key: 'profile',
    type: "M",
    children: [
      {
        name: 'menu.profile.basic',
        key: 'profile/basic',
        type: "M",
      },
    ],
  },

  {
    name: 'menu.result',
    key: 'result',
    type: "M",
    children: [
      {
        name: 'menu.result.success',
        key: 'result/success',
        type: "M",
        breadcrumb: false,
      },
      {
        name: 'menu.result.error',
        key: 'result/error',
        type: "M",
        breadcrumb: false,
      },
    ],
  },
  {
    name: 'menu.exception',
    key: 'exception',
    type: "M",
    children: [
      {
        name: 'menu.exception.403',
        key: 'exception/403',
        type: "M",
      },
      {
        name: 'menu.exception.404',
        key: 'exception/404',
        type: "M",
      },
      {
        name: 'menu.exception.500',
        key: 'exception/500',
        type: "M",
      },
    ],
  },
  {
    name: 'menu.user',
    key: 'user',
    type: "M",
    children: [
      {
        name: 'menu.user.info',
        key: 'user/info',
        type: "M",
      },
      {
        name: 'menu.user.setting',
        key: 'user/setting',
        type: "M",
      },
    ],
  },
  {
    name: '登录成功',
    key: 'loginSuccess',
    type: "M",
    visible:true,
  },
  {
    name: '登录失败',
    key: 'loginError',
    type: "M",
    visible:true,
  },
];



const getRoutes = () =>{
const menu = localStorage.getItem('userMenu');
  if(menu){
    return JSON.parse(menu);
  }
  return staticRoutes;
} 

export const getName = (path: string, routes) => {
  return routes.find((item) => {
    const itemPath = `/${item.key}`;
    if (path === itemPath) {
      return item.name;
    } else if (item.children) {
      return getName(path, item.children);
    }
  });
};

export const generatePermission = (role: string) => {
  // const actions = role === 'supe_admin' ? ['*'] : ['read'];
  const actions = ['*'];
  const result = {};
  const routes = getRoutes();
  console.info(routes)
  routes.forEach((item) => {
    if (item.children) {
      item.children.forEach((child) => {
        result[child.perms] = actions;
      });
    }
  });
  return result;
};



const useRoute = (systemRoutes): [IRoute[], string] => {

  const routes = getRoutes();
  
  const [permissionRoute, setPermissionRoute] = useState(routes);

  const defaultRoute = useMemo(() => {
    const first = permissionRoute[0];
    if (first) {
      const firstRoute = first?.children?.[0]?.key || first.key;
      return firstRoute;
    }
    return '';
  }, [permissionRoute]);

  return [permissionRoute, defaultRoute];
};

export default useRoute;
