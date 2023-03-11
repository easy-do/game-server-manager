import React from 'react';
import { Button, Popconfirm } from '@arco-design/web-react';
import { SearchTypeEnum } from '@/utils/systemConstant';
import PermissionWrapper from '@/components/PermissionWrapper';
import { dictLabelEnum } from '@/utils/dictDataUtils';

export const statusEnum = dictLabelEnum('status_select','string')

const scopeSelect = dictLabelEnum("scope_select",'string')

export interface DataInfoVo{
    id: string,
    adaptationAppId: string,
    adaptationAppName: string,
    scriptName: string,
    scriptType: string,
    scriptScope: string,
    basicScript: string,
    version: string,
    scriptFile: string,
    description: string,
    heat: string,
    createTime: string,
    updateTime: string,
    createBy: string,
    author: string,
    updateBy: string,
    delFlag: string,
}

// 后台sql查询字段
export function getSearChColumns(){
    return [
    'id',
    'scriptName',
    'scriptType',
    'scriptScope',
    'version',
    'description',
    'author',
      ];
}

// 搜索配置
export function searchConfig() {
  return {
        'adaptationAppId': SearchTypeEnum.EQ,
        'scriptName': SearchTypeEnum.LIKE,
        'scriptType': SearchTypeEnum.EQ,
        'scriptScope': SearchTypeEnum.EQ,
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
      title: t['searchTable.columns.scriptName'],
      dataIndex: 'scriptName',
      ellipsis:true,
      sorter: true,
    },

    {
      title: t['searchTable.columns.scriptType'],
      dataIndex: 'scriptType',
      ellipsis:true,
      sorter: true,
    },

    {
      title: t['searchTable.columns.scriptScope'],
      dataIndex: 'scriptScope',
      render: (_, record) => (scopeSelect[record.scriptScope]),
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
      title: t['searchTable.columns.author'],
      dataIndex: 'author',
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
              { resource: 'appScript', actions: ['appScript:info'] },
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
              { resource: 'appScript', actions: ['appScript:update'] },
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
              { resource: 'appScript', actions: ['appScript:update'] },
            ]}
          >
            <Button
                type="text"
                size="small"
                onClick={() => callback(record, 'updateScript')}
            >
                {t['searchTable.columns.operations.updateScript']}
            </Button>
          </PermissionWrapper>
          <PermissionWrapper
            requiredPermissions={[
              { resource: 'appScript', actions: ['appScript:remove'] },
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

