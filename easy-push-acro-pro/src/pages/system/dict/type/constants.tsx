import React from 'react';
import { Button, Typography, Badge, Popconfirm, Link } from '@arco-design/web-react';
import { SearchTypeEnum } from '@/utils/systemConstant';
import { dictLabelEnum } from '@/utils/dictDataUtils';


export const statusEnum = dictLabelEnum('status_select','string')

export interface DataInfoVo{
    remark: string,
    updateTime: string,
    updateBy: string,
    createTime: string,
    createBy: string,
    status: string,
    dictCode: string,
    dictName: string,
    id: string,
}

// 后台sql查询字段
export function getSearChColumns(){
    return [
    'remark',
    'updateTime',
    'updateBy',
    'createTime',
    'createBy',
    'status',
    'dictCode',
    'dictName',
    'id',
      ];
}

// 搜索配置
export function searchConfig() {
  return {
    nickName: SearchTypeEnum.LIKE,
    createTime: SearchTypeEnum.BETWEEN,
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
    },
    {
      title: t['searchTable.columns.dictName'],
      dataIndex: 'dictName',
    },
    {
      title: t['searchTable.columns.dictCode'],
      // dataIndex: 'dictCode',
      render: (_col, record) => (
        <div>
         <Link onClick={() => callback(record, 'updateDictData')}>{record.dictCode}</Link>
        </div>
      ),
    },
    {
      title: t['searchTable.columns.status'],
      dataIndex: 'status',
      render: (_, record) => (statusEnum[record.status]),
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

