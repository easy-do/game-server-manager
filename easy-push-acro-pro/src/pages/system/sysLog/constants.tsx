import React from 'react';
import { Button, Popconfirm } from '@arco-design/web-react';
import { SearchTypeEnum } from '@/utils/systemConstant';

export interface DataInfoVo{
    id: string,
    data: string,
    createTime: string,
}

// 后台sql查询字段
export function getSearChColumns(){
    return [
    'id',
    'data',
    'createTime',
      ];
}

// 搜索配置
export function searchConfig() {
  return {
        'data': SearchTypeEnum.LIKE,
        'createTime': SearchTypeEnum.BETWEEN,
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
      title: t['searchTable.columns.id'],
      dataIndex: 'id',
      ellipsis:true,
      sorter: true,
    },
    {
      title: t['searchTable.columns.moduleName'],
      render: (text: string, record: any) => {
        const fieldValue = JSON.parse(record.data);
        return fieldValue.moduleName;
      },
    },
    {
      title: t['searchTable.columns.actionType'],
      ellipsis:true,
      sorter: true,
      render: (text: string, record: any, index: number) => {
        const fieldValue = JSON.parse(record.data);
        return fieldValue.actionType;
      },
    },
    {
      title: t['searchTable.columns.operationStatus'],
      ellipsis:true,
      sorter: true,
      render: (text: string, record: any, index: number) => {
        const fieldValue = JSON.parse(record.data);
        return fieldValue.operationStatus;
      },
    },
    {
      title: t['searchTable.columns.sourceIp'],
      ellipsis:true,
      sorter: true,
      render: (text: string, record: any, index: number) => {
        const fieldValue = JSON.parse(record.data);
        return fieldValue.sourceIp;
      },
    },
    {
      title: t['searchTable.columns.description'],
      ellipsis:true,
      sorter: true,
      render: (text: any, record: any, index: any) => {
        const fieldValue = JSON.parse(record.data);
        return fieldValue.description;
      },
    },
    {
      title: t['searchTable.columns.operationTime'],
      ellipsis:true,
      sorter: true,
      render: (text: string, record: any, index: number) => {
        const fieldValue = JSON.parse(record.data);
        return fieldValue.operationTime;
      },
    },
    {
      title: t['searchTable.columns.operatorName'],
      ellipsis:true,
      sorter: true,
      render: (text: any, record: any, index: any) => {
        const fieldValue = JSON.parse(record.data);
        return fieldValue.operatorName;
      },
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

