import React from 'react';
import { Button, Typography, Badge, Popconfirm } from '@arco-design/web-react';
import { dictLabelEnum } from '@/utils/dictDataUtils';

export const statusEnum = dictLabelEnum('status_select','string')

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
    'deviceName',
    'appName',
    'status',
    'createTime',
    'updateTime',
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
      title: t['searchTable.columns.applicationId'],
      dataIndex: 'applicationId',
      ellipsis:true,
      sorter: true,
    },
    {
      title: t['searchTable.columns.applicationName'],
      dataIndex: 'applicationName',
      ellipsis:true,
      sorter: true,
    },
    {
      title: t['searchTable.columns.deviceName'],
      dataIndex: 'deviceName',
      ellipsis:true,
      sorter: true,
    },
    {
      title: t['searchTable.columns.appName'],
      dataIndex: 'appName',
      ellipsis:true,
      sorter: true,
    },
    {
      title: t['searchTable.columns.status'],
      dataIndex: 'status',
      ellipsis:true,
      sorter: true,
    },
    {
      title: t['searchTable.columns.lastUpTime'],
      dataIndex: 'lastUpTime',
      ellipsis:true,
      sorter: true,
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
            onClick={() => callback(record, 'execScript')}
          >
            {t['searchTable.columns.operations.execScript']}
          </Button>
          <Button
            type="text"
            size="small"
            onClick={() => callback(record, 'log')}
          >
            {t['searchTable.columns.operations.log']}
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

