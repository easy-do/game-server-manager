import React from 'react';
import { Button, Typography, Badge, Popconfirm } from '@arco-design/web-react';
import { SearchTypeEnum } from '@/utils/systemConstant';
import PermissionWrapper from '@/components/PermissionWrapper';

export const Status = ['正常', '禁用'];

export interface DataInfoVo{
    id: string,
    dockerName: string,
    dockerHost: string,
    dockerCert: string,
    dockerIsSsl: string,
    dockerCertPassword: string,
    createBy: string,
    createTime: string,
    updateBy: string,
    updateTime: string,
}

// 后台sql查询字段
export function getSearChColumns(){
    return [
    'id',
    'dockerName',
    'dockerHost',
    'dockerCert',
    'dockerIsSsl',
    'dockerCertPassword',
    'createBy',
    'createTime',
    'updateBy',
    'updateTime',
      ];
}

// 搜索配置
export function searchConfig() {
  return {
        'dockerName': SearchTypeEnum.LIKE,
        'dockerHost': SearchTypeEnum.LIKE,
        'dockerIsSsl': SearchTypeEnum.EQ,
        'dockerCertPassword': SearchTypeEnum.EQ,
  }
}

//默认排序字段
export function getDefaultOrders(){
  return [{column: 'createTime', asc: false}];
}


//表单展示字段
export function getColumns(
  t: any,
  callback: (record: Record<string, any>, type: string) => Promise<void>
) {
  return [

    {
      title: t['searchTable.columns.dockerName'],
      dataIndex: 'dockerName',
      ellipsis:true,
    },

    {
      title: t['searchTable.columns.dockerIsSsl'],
      dataIndex: 'dockerIsSsl',
      ellipsis:true,
    },

    {
      title: t['searchTable.columns.createTime'],
      dataIndex: 'createTime',
      ellipsis:true,
    },
    {
      title: t['searchTable.columns.operations'],
      dataIndex: 'operations',
      headerCellStyle: { paddingLeft: '15px' },
      render: (_, record) => (
        <div>  
          <PermissionWrapper
            requiredPermissions={[
              { resource: 'server:dockerDetails', actions: ['info'] },
            ]}
          >
            <Button
                type="text"
                size="small"
                onClick={() => callback(record, 'view')}
            >
                {t['searchTable.columns.operations.view']}
            </Button>
          </PermissionWrapper>
          <PermissionWrapper
            requiredPermissions={[
              { resource: 'server:dockerDetails', actions: ['update'] },
            ]}
          >
            <Button
                type="text"
                size="small"
                onClick={() => callback(record, 'update')}
            >
                {t['searchTable.columns.operations.update']}
            </Button>
          </PermissionWrapper>
          <PermissionWrapper
            requiredPermissions={[
              { resource: 'server:dockerDetails', actions: ['remove'] },
            ]}
          >
            <Popconfirm
                title={t['searchTable.columns.operations.remove.confirm']}
                onOk={() => callback(record, 'remove')}
            >
                <Button type="text" status="warning" size="small">
                {t['searchTable.columns.operations.remove']}
                </Button>
            </Popconfirm>
          </PermissionWrapper>
        </div>
      ),
    },
  ];
}

