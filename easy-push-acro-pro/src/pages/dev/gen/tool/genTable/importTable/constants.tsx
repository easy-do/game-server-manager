import React from 'react';

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

//表单展示字段
export function getColumns(
  t: any,
  callback: (record: Record<string, any>, type: string) => Promise<void>
) {
  return [
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
  ];
}

