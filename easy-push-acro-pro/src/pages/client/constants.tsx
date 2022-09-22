import React from 'react';
import { Button, Typography, Badge, Popconfirm } from '@arco-design/web-react';
import { SearchTypeEnum } from '@/utils/systemConstant';

export const Status = ['正常', '禁用'];

export interface DataInfoVo{
    id: string,
    clientName: string,
    serverId: string,
    serverName: string,
    userName: string,
    status: string,
    clientData: any,
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
    'createTime',
    'updateTime',
    'lastUpTime',
    'delFlag',
    'publicKey',
    'privateKey',
    'createBy',
    'updateBy',
      ];
}

// 搜索配置
export function searchConfig() {
  return {
        'clientName': SearchTypeEnum.LIKE,
        'serverName': SearchTypeEnum.LIKE,
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
      title: t['searchTable.columns.clientName'],
      dataIndex: 'clientName',
      ellipsis:true,
    },
    {
      title: t['searchTable.columns.serverName'],
      dataIndex: 'serverName',
      ellipsis:true,
    },
    {
      title: t['searchTable.columns.status'],
      dataIndex: 'status',
      ellipsis:true,
    },
    {
      title: t['searchTable.columns.lastUpTime'],
      dataIndex: 'lastUpTime',
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
            onClick={() => callback(record, 'install')}
          >
            {t['searchTable.columns.operations.install']}
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

