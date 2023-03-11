import React from 'react';
import { Button, Popconfirm } from '@arco-design/web-react';
import { SearchTypeEnum } from '@/utils/systemConstant';
import PermissionWrapper from '@/components/PermissionWrapper';
import { dictLabelEnum } from '@/utils/dictDataUtils';

export const statusEnum = dictLabelEnum('status_select','string')

const scopeSelect = dictLabelEnum("scope_select",'string')

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
    'author',
    'isAudit',
    'appScope',
    'description',
    'heat',
    'createTime',
    'updateTime',
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

const auditStatus = dictLabelEnum("audit_status",'string')

const status = dictLabelEnum("status_select",'string')

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
      sorter: true,
    },

    {
      title: t['searchTable.columns.version'],
      dataIndex: 'version',
      ellipsis:true,
      sorter: true,
    },

    {
      title: t['searchTable.columns.state'],
      dataIndex: 'state',
      ellipsis:true,
      sorter: true,
      render: (_, record) => (status[record.state]),
    },

    {
      title: t['searchTable.columns.author'],
      dataIndex: 'author',
      ellipsis:true,
      sorter: true,
    },

    {
      title: t['searchTable.columns.isAudit'],
      dataIndex: 'isAudit',
      ellipsis:true,
      sorter: true,
      render: (_, record) => (auditStatus[record.isAudit]),
    },

    {
      title: t['searchTable.columns.appScope'],
      dataIndex: 'appScope',
      render: (_, record) => (scopeSelect[record.appScope]),
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
              { resource: 'appInfo', actions: ['appInfo:info'] },
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
              { resource: 'appInfo', actions: ['appInfo:update'] },
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
          {
           record.isAudit === 0 || record.isAudit === 3?   
          <Button
                type="text"
                size="small"
                onClick={() => callback(record, 'submitAudit')}
            >
                {t['searchTable.columns.operations.submitAudit']}
          </Button>:null
           } 
          <PermissionWrapper
            requiredPermissions={[
              { resource: 'appInfo', actions: ['appInfo:audit'] },
            ]}
          >
            <Button type="text" size="small">
              {t['searchTable.columns.operations.audit']}
            </Button>
          </PermissionWrapper>
          <PermissionWrapper
            requiredPermissions={[
              { resource: 'appInfo', actions: ['appInfo:remove'] },
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

