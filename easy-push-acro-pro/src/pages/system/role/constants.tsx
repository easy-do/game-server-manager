import React from 'react';
import { Button, Popconfirm } from '@arco-design/web-react';
import { SearchTypeEnum } from '@/utils/systemConstant';
import PermissionWrapper from '@/components/PermissionWrapper';

export const Status = ['开启', '关闭'];

export interface DataInfoVo{
    roleId: string,
    roleName: string,
    roleKey: string,
    roleSort: string,
    isDefault: string,
    status: string,
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
    'roleId',
    'roleName',
    'roleKey',
    'roleSort',
    'isDefault',
    'status',
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
        'roleName': SearchTypeEnum.LIKE,
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
      title: t['searchTable.columns.roleName'],
      dataIndex: 'roleName',
      ellipsis:true,
      sorter: true,
    },

    {
      title: t['searchTable.columns.roleKey'],
      dataIndex: 'roleKey',
      ellipsis:true,
      sorter: true,
    },

    {
      title: t['searchTable.columns.status'],
      dataIndex: 'status',
      ellipsis:true,
      sorter: true,
      render: (_, record) => (
        Status[record.status]
      ),
    },

    {
      title: t['searchTable.columns.remark'],
      dataIndex: 'remark',
      ellipsis:true,
      sorter: true,
    },
    {
      title: t['searchTable.columns.operations'],
      dataIndex: 'operations',
      headerCellStyle: { paddingLeft: '15px' },
      render: (_, record) => (
        <div>  
          <PermissionWrapper
            requiredPermissions={[
              { resource: 'role', actions: ['role:info'] },
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
              { resource: 'role', actions: ['role:auth'] },
            ]}
          >
            <Button
                type="text"
                size="small"
                onClick={() => callback(record, 'auth')}
            >
                {t['searchTable.columns.operations.auth']}
            </Button>
          </PermissionWrapper>
          <PermissionWrapper
            requiredPermissions={[
              { resource: 'role', actions: ['role:update'] },
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
              { resource: 'role', actions: ['role:remove'] },
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

