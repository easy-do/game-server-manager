import React from 'react';
import { Button, Popconfirm } from '@arco-design/web-react';
import { dictLabelEnum, getDictList } from '@/utils/dictDataUtils';
import PermissionWrapper from '@/components/PermissionWrapper';

import {
  SearchType,
  SelectColumnType,
} from '@/utils/searchUtil';

export const statusEnum = dictLabelEnum('status_select','string')

export interface DataInfoVo{
    delFlag: string,
    remark: string,
    updateTime: string,
    updateBy: string,
    createTime: string,
    createBy: string,
    status: string,
    params: string,
    password: string,
    userName: string,
    url: string,
    sourceType: string,
    sourceCode: string,
    sourceName: string,
    id: string,
}

// 后台sql查询字段
export const selectColumns = {
  columns: [
    'id',
    'templateName',
    'templateCode',
    'templateType',
    'templateScope',
    'version',
    'fileName',
    'filePath',
    'description',
    'createTime',
    'updateTime',
    'createBy',
    'updateBy',
    'createName',
    'delFlag',
  ],
  type: SelectColumnType.onlySelect,
}


// 搜索配置
export const searchConfig = {
        'templateName': SearchType.LIKE,
        'templateCode': SearchType.EQ,
        'templateType': SearchType.EQ,
        'templateScope': SearchType.EQ,
        'version': SearchType.EQ,
        'fileName': SearchType.LIKE,
        'filePath': SearchType.EQ,
        'description': SearchType.EQ,
        'createName': SearchType.LIKE,
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
      title: t['searchTable.columns.remark'],
      dataIndex: 'remark',
    },
    {
      title: t['searchTable.columns.createTime'],
      dataIndex: 'createTime',
    },
    {
      title: t['searchTable.columns.status'],
      dataIndex: 'status',
    },
    {
      title: t['searchTable.columns.params'],
      dataIndex: 'params',
    },
    {
      title: t['searchTable.columns.userName'],
      dataIndex: 'userName',
    },
    {
      title: t['searchTable.columns.url'],
      dataIndex: 'url',
    },
    {
      title: t['searchTable.columns.sourceType'],
      dataIndex: 'sourceType',
    },
    {
      title: t['searchTable.columns.sourceCode'],
      dataIndex: 'sourceCode',
    },
    {
      title: t['searchTable.columns.sourceName'],
      dataIndex: 'sourceName',
    },
    {
      title: t['searchTable.columns.id'],
      dataIndex: 'id',
    },
    {
      title: t['searchTable.columns.operations'],
      dataIndex: 'operations',
      headerCellStyle: { paddingLeft: '15px' },
      render: (_, record) => (
        <div>
          <PermissionWrapper
            requiredPermissions={[
              { resource: 'dataSource', actions: ['dataSource:info'] },
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
              { resource: 'dataSource', actions: ['dataSource:update'] },
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
              { resource: 'dataSource', actions: ['dataSource:remove'] },
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

