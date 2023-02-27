import React from 'react';
import { Button, Typography, Badge, Popconfirm } from '@arco-design/web-react';
import { SearchTypeEnum } from '@/utils/systemConstant';
import PermissionWrapper from '@/components/PermissionWrapper';
import { dictLabelEnum, getDictList } from '@/utils/dictDataUtils';

export const statusEnum = dictLabelEnum('status_select','string')

export interface DataInfoVo{
    clientId: string,
    clientName: string,
    clientSecret: string,
    scope: string,
    authorizedGrantTypes: string,
    redirectUri: string,
    accessTokenValidity: string,
    refreshTokenValidity: string,
    clientTokenValidity: string,
    additionalInformation: string,
    status: string,
    isAudit: string,
    remark: string,
    createBy: string,
    createTime: string,
    updateBy: string,
    updateTime: string,
}

// 后台sql查询字段
export function getSearChColumns(){
    return [
    'clientId',
    'clientName',
    'clientSecret',
    'scope',
    'authorizedGrantTypes',
    'redirectUri',
    'accessTokenValidity',
    'refreshTokenValidity',
    'clientTokenValidity',
    'additionalInformation',
    'status',
    'isAudit',
    'remark',
    'createBy',
    'createTime',
    'updateBy',
    'updateTime',
      ];
}

// 搜索配置
export function searchConfig() {
  return {
        'clientId': SearchTypeEnum.EQ,
        'clientName': SearchTypeEnum.LIKE,
        'authorizedGrantTypes': SearchTypeEnum.LIKE,
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
      title: t['searchTable.columns.clientId'],
      dataIndex: 'clientId',
      ellipsis:true,
    },

    {
      title: t['searchTable.columns.clientName'],
      dataIndex: 'clientName',
      ellipsis:true,
    },

    {
      title: t['searchTable.columns.clientSecret'],
      dataIndex: 'clientSecret',
      ellipsis:true,
    },

    {
      title: t['searchTable.columns.scope'],
      dataIndex: 'scope',
      ellipsis:true,
    },

    {
      title: t['searchTable.columns.authorizedGrantTypes'],
      dataIndex: 'authorizedGrantTypes',
      ellipsis:true,
    },

    {
      title: t['searchTable.columns.accessTokenValidity'],
      dataIndex: 'accessTokenValidity',
      ellipsis:true,
    },

    {
      title: t['searchTable.columns.status'],
      dataIndex: 'status',
      ellipsis:true,
    },

    {
      title: t['searchTable.columns.isAudit'],
      dataIndex: 'isAudit',
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
              { resource: 'uc:oauthClient', actions: ['info'] },
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
              { resource: 'uc:oauthClient', actions: ['update'] },
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
              { resource: 'uc:oauthClient', actions: ['remove'] },
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

