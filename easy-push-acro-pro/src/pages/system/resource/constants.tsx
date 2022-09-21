import React from 'react';
import { Button, Typography, Badge, Popconfirm, Switch } from '@arco-design/web-react';
import { SearchTypeEnum } from '@/utils/systemConstant';
import PermissionWrapper from '@/components/PermissionWrapper';

export const Status = ['正常', '禁用'];

export interface DataInfoVo{
    id: string,
    resourceName: string,
    resourceCode: string,
    parentId: string,
    resourceType: string,
    orderNumber: string,
    path: string,
    param: string,
    isCache: string,
    status: string,
    icon: string,
    createBy: string,
    createTime: string,
    updateBy: string,
    updateTime: string,
    remark: string,
    delFlag: string,
}

// 后台sql查询字段
export function getSearChColumns(){
    return [
    'id',
    'resourceName',
    'resourceCode',
    'parentId',
    'resourceType',
    'orderNumber',
    'path',
    'param',
    'isCache',
    'status',
    'icon',
    'createBy',
    'createTime',
    'updateBy',
    'updateTime',
    'remark',
    'delFlag',
      ];
}

// 搜索配置
export function searchConfig() {
  return {
        'resourceName': SearchTypeEnum.LIKE,
        'resourceCode': SearchTypeEnum.EQ,
        'resourceType': SearchTypeEnum.EQ,
        'path': SearchTypeEnum.EQ,
        'isCache': SearchTypeEnum.EQ,
        'status': SearchTypeEnum.EQ,
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
      title: t['searchTable.columns.resourceName'],
      dataIndex: 'resourceName',
      ellipsis:true,
    },

    {
      title: t['searchTable.columns.resourceCode'],
      dataIndex: 'resourceCode',
      ellipsis:true,
    },

    {
      title: t['searchTable.columns.resourceType'],
      dataIndex: 'resourceType',
      ellipsis:true,
      render: (_, record) => (
        record.resourceType === 'M' ?  "菜单" : record.resourceType === 'I' ?  "接口" : record.resourceType === 'A'? "按钮": record.resourceType
        ),
    },

    {
      title: t['searchTable.columns.path'],
      dataIndex: 'path',
      ellipsis:true,
    },
    {
      title: t['searchTable.columns.status'],
      dataIndex: 'status',
      ellipsis:true,
      render: (_, record) => (
        <Switch type='round' checkedText='开启' onClick={() => callback(record, 'chageStatus')} uncheckedText='关闭' checked={record.status === 0}  />
        ),
    },

    {
      title: t['searchTable.columns.icon'],
      dataIndex: 'icon',
      ellipsis:true,
    },

    {
      title: t['searchTable.columns.remark'],
      dataIndex: 'remark',
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
              { resource: 'uc:resource', actions: ['info'] },
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
              { resource: 'uc:resource', actions: ['update'] },
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
              { resource: 'uc:resource', actions: ['remove'] },
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

