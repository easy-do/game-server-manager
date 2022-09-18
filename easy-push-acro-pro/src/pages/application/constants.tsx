import React from 'react';
import { Button, Typography, Badge, Popconfirm } from '@arco-design/web-react';

export const Status = ['正常', '禁用'];

export interface DataInfoVo{
    applicationId: string,
    applicationName: string,
    userId: string,
    deviceId: string,
    deviceName: string,
    deviceType: string,
    appId: string,
    appName: string,
    status: string,
    publicKey: string,
    privateKey: string,
    appEnvCache: string,
    isBlack: string,
    pluginsData: string,
    createTime: string,
    updateTime: string,
    lastUpTime: string,
    delFlag: string,
}

// 后台sql查询字段
export function getSearChColumns(){
    return [
    'applicationId',
    'applicationName',
    'userId',
    'deviceId',
    'deviceName',
    'deviceType',
    'appId',
    'appName',
    'status',
    'publicKey',
    'privateKey',
    'appEnvCache',
    'isBlack',
    'pluginsData',
    'createTime',
    'updateTime',
    'lastUpTime',
    'delFlag',
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
      title: t['searchTable.columns.applicationId'],
      dataIndex: 'applicationId',
      ellipsis:true
    },
    {
      title: t['searchTable.columns.applicationName'],
      dataIndex: 'applicationName',
    },
    {
      title: t['searchTable.columns.deviceName'],
      dataIndex: 'deviceName',
    },
    {
      title: t['searchTable.columns.appName'],
      dataIndex: 'appName',
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

