import React from 'react';
import { SearchTypeEnum } from '@/utils/systemConstant';

export const Status = ['正常', '禁用'];

export interface DataInfoVo{
    id: string,
    userId: string,
    serverName: string,
    address: string,
    port: string,
    userName: string,
    password: string,
    createTime: string,
    updateTime: string,
    delFlag: string,
}

// 后台sql查询字段
export function getSearChColumns(){
    return [
    'id',
    'serverName',
    'userName',
    'password',
    'createTime',
      ];
}

// 搜索配置
export function searchConfig() {
  return {
        'serverName': SearchTypeEnum.LIKE,
  }
}

//默认排序字段
export function getDefaultOrders(){
  return [{column: 'createTime', asc: false}];
}

