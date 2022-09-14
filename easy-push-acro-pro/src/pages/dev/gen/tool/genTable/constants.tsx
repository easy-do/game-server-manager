import React from 'react';
import { Button, Typography, Badge, Popconfirm } from '@arco-design/web-react';

export const Status = ['正常', '禁用'];

export interface DataInfoVo{
    isWrapper: string,
    templateIds: string,
    isManager: string,
    isRemove: string,
    isUpdate: string,
    isInsert: string,
    isQuery: string,
    remark: string,
    updateTime: string,
    updateBy: string,
    createTime: string,
    createBy: string,
    options: string,
    genPath: string,
    genType: string,
    functionAuthor: string,
    functionName: string,
    businessName: string,
    moduleName: string,
    packageName: string,
    tplCategory: string,
    className: string,
    subTableFkName: string,
    subTableName: string,
    tableComment: string,
    tableName: string,
    dataSourceId: string,
    tableId: string,
}

// 后台sql查询字段
export function getSearChColumns(){
    return [
    'isWrapper',
    'templateIds',
    'isManager',
    'isRemove',
    'isUpdate',
    'isInsert',
    'isQuery',
    'remark',
    'updateTime',
    'updateBy',
    'createTime',
    'createBy',
    'options',
    'genPath',
    'genType',
    'functionAuthor',
    'functionName',
    'businessName',
    'moduleName',
    'packageName',
    'tplCategory',
    'className',
    'subTableFkName',
    'subTableName',
    'tableComment',
    'tableName',
    'dataSourceId',
    'tableId',
      ];
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
      title: t['searchTable.columns.tableId'],
      dataIndex: 'tableId',
    },
    {
      title: t['searchTable.columns.tableName'],
      dataIndex: 'tableName',
    }, 
    {
      title: t['searchTable.columns.tableComment'],
      dataIndex: 'tableComment',
    },
    {
      title: t['searchTable.columns.createTime'],
      dataIndex: 'createTime',
    }, 
    {
      title: t['searchTable.columns.updateTime'],
      dataIndex: 'updateTime',
    },
    {
      title: t['searchTable.columns.remark'],
      dataIndex: 'remark',
    },

    {
      title: t['searchTable.columns.operations'],
      dataIndex: 'operations',
      headerCellStyle: { paddingLeft: '15px' },
      render: (_, record) => (
        <div>
          <Button
            type="text"
            size="small"
            onClick={() => callback(record, 'view')}
          >
            {t['searchTable.columns.operations.view']}
          </Button>
          <Button
            type="text"
            size="small"
            onClick={() => callback(record, 'update')}
          >
            {t['searchTable.columns.operations.update']}
          </Button>
          <Popconfirm
            title={t['searchTable.columns.operations.remove.confirm']}
            onOk={() => callback(record, 'remove')}
          >
            <Button type="text" status="warning" size="small">
              {t['searchTable.columns.operations.remove']}
            </Button>
          </Popconfirm>
        </div>
      ),
    },
  ];
}

