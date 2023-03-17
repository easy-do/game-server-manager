import React from 'react';
import { Button, Popconfirm } from '@arco-design/web-react';
import { SearchTypeEnum } from '@/utils/systemConstant';
import PermissionWrapper from '@/components/PermissionWrapper';
import { dictLabelEnum } from '@/utils/dictDataUtils';

export const statusEnum = dictLabelEnum('application_status','string')
export const scopeEnum = dictLabelEnum('application_scope','string')

export interface DataInfoVo{
    id: string,
    applicationName: string,
    status: string,
    icon: string,
    author: string,
    scope: string,
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
    'applicationName',
    'status',
    'icon',
    'author',
    'scope',
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
        'applicationName': SearchTypeEnum.LIKE,
        'status': SearchTypeEnum.EQ,
        'author': SearchTypeEnum.EQ,
        'scope': SearchTypeEnum.EQ,
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
      title: t['searchTable.columns.applicationName'],
      dataIndex: 'applicationName',
      ellipsis:true,
    },

    {
      title: t['searchTable.columns.scope'],
      dataIndex: 'scope',
      ellipsis:true,
      render: (_, record) => (scopeEnum[record.scope]),
    },

    {
      title: t['searchTable.columns.status'],
      dataIndex: 'status',
      ellipsis:true,
      render: (_, record) => (statusEnum[record.status]),
    },

    {
      title: t['searchTable.columns.heat'],
      dataIndex: 'heat',
      ellipsis:true,
    },

    {
      title: t['searchTable.columns.author'],
      dataIndex: 'author',
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
              { resource: 'application', actions: ['application:info'] },
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
              { resource: 'application', actions: ['application:update'] },
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
              { resource: 'application', actions: ['application:remove'] },
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
          <PermissionWrapper
            requiredPermissions={[
              { resource: 'application', actions: ['application:update'] },
            ]}
          >
            {record.status !==1 && record.status !==4 ?<Button
                type="text"
                size="small"
                onClick={() => callback(record, 'submitAudit')}
            >
                {'提审'}
            </Button>:null}
          </PermissionWrapper>
          <PermissionWrapper
            requiredPermissions={[
              { resource: 'application', actions: ['application:update'] },
            ]}
          >
            {record.status ===2 || record.status ===3 ?<Button
                type="text"
                size="small"
                onClick={() => callback(record, 'audit')}
            >
                {'审核'}
            </Button>:null}
          </PermissionWrapper>
        </div>
      ),
    },
  ];
}

