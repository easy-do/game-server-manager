import React from 'react';
import { Button, Typography, Badge, Popconfirm } from '@arco-design/web-react';


export const Status = ['开启', '禁用'];


export interface DataInfoVo{
    id: string,
    dictTypeId: string,
    dictSort: string,
    dictLabel: string,
    dictKey: string,
    dictValue: string,
    dictValueType: string,
    isDefault: string,
    status: string,
    createBy: string,
    createTime: string,
    updateBy: string,
    updateTime: string,
    remark: string,
}

// 后台sql查询字段
export function getSearChColumns(){
    return [
    'id',
    'dictTypeId',
    'dictSort',
    'dictLabel',
    'dictKey',
    'dictValue',
    'dictValueType',
    'isDefault',
    'status',
    'createBy',
    'createTime',
    'updateBy',
    'updateTime',
    'remark',
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
      title: t['searchTable.columns.dictLabel'],
      dataIndex: 'dictLabel',
      ellipsis:true,
    },
    {
      title: t['searchTable.columns.dictKey'],
      dataIndex: 'dictKey',
      ellipsis:true,
    },
    {
      title: t['searchTable.columns.dictValueType'],
      dataIndex: 'dictValueType',
      ellipsis:true,
    },
    {
      title: t['searchTable.columns.status'],
      dataIndex: 'status',
      ellipsis:true,
      render:(_cell,record)=>(
        Status[record.status]
      ),
    },
    {
      title: t['searchTable.columns.dictSort'],
      dataIndex: 'dictSort',
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

