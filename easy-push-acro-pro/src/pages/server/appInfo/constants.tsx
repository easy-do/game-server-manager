import React from 'react';
import { Button, Typography, Badge, Popconfirm } from '@arco-design/web-react';
import { SearchTypeEnum } from '@/utils/systemConstant';
import PermissionWrapper from '@/components/PermissionWrapper';

export const Status = ['正常', '禁用'];

export interface DataInfoVo{
    id: string,
    appName: string,
    version: string,
    state: string,
    startCmd: string,
    stopCmd: string,
    configFilePath: string,
    icon: string,
    picture: string,
    author: string,
    isAudit: string,
    appScope: string,
    deployType: string,
    description: string,
    heat: string,
    createTime: string,
    updateTime: string,
    createBy: string,
    updateBy: string,
    delFlag: string,
}

// 后台sql查询字段
export function getSearChColumns(){
    return [
    'id',
    'appName',
    'version',
    'state',
    'startCmd',
    'stopCmd',
    'configFilePath',
    'icon',
    'picture',
    'author',
    'isAudit',
    'appScope',
    'deployType',
    'description',
    'heat',
    'createTime',
    'updateTime',
    'createBy',
    'updateBy',
    'delFlag',
      ];
}

// 搜索配置
export function searchConfig() {
  return {
        'appName': SearchTypeEnum.LIKE,
        'state': SearchTypeEnum.EQ,
        'configFilePath': SearchTypeEnum.EQ,
        'icon': SearchTypeEnum.EQ,
        'picture': SearchTypeEnum.EQ,
        'isAudit': SearchTypeEnum.EQ,
        'appScope': SearchTypeEnum.EQ,
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
      title: t['searchTable.columns.appName'],
      dataIndex: 'appName',
      ellipsis:true,
    },

    {
      title: t['searchTable.columns.version'],
      dataIndex: 'version',
      ellipsis:true,
    },

    {
      title: t['searchTable.columns.state'],
      dataIndex: 'state',
      ellipsis:true,
    },

    {
      title: t['searchTable.columns.author'],
      dataIndex: 'author',
      ellipsis:true,
    },

    {
      title: t['searchTable.columns.isAudit'],
      dataIndex: 'isAudit',
      ellipsis:true,
    },

    {
      title: t['searchTable.columns.appScope'],
      dataIndex: 'appScope',
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
              { resource: 'server:appInfo', actions: ['info'] },
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
              { resource: 'server:appInfo', actions: ['update'] },
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
              { resource: 'server:appInfo', actions: ['remove'] },
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

