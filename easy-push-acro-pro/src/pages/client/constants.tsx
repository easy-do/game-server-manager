import React from 'react';
import { Button, Typography, Badge, Popconfirm } from '@arco-design/web-react';

export const Status = ['正常', '禁用'];

export interface DataInfoVo{
    id: string,
    clientName: string,
    serverId: string,
    serverName: string,
    userName: string,
    status: string,
    clientData: string,
    createTime: string,
    updateTime: string,
    lastUpTime: string,
    delFlag: string,
    publicKey: string,
    privateKey: string,
    createBy: string,
    updateBy: string,
}

// 后台sql查询字段
export function getSearChColumns(){
    return [
    'id',
    'clientName',
    'serverId',
    'serverName',
    'userName',
    'status',
    'clientData',
    'lastUpTime',
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
      title: t['searchTable.columns.id'],
      dataIndex: 'id',
      ellipsis:true
    },
    {
      title: t['searchTable.columns.clientName'],
      dataIndex: 'clientName',
    },
    {
      title: t['searchTable.columns.serverName'],
      dataIndex: 'serverName',
    },
    {
      title: t['searchTable.columns.userName'],
      dataIndex: 'userName',
    },
    {
      title: t['searchTable.columns.status'],
      dataIndex: 'status',
    },
    {
      title: t['searchTable.columns.lastUpTime'],
      dataIndex: 'lastUpTime',
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

