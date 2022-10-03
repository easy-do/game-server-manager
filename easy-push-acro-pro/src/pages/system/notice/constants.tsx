import React from 'react';
import { Button, Typography, Badge, Popconfirm } from '@arco-design/web-react';
import { SearchTypeEnum } from '@/utils/systemConstant';
import PermissionWrapper from '@/components/PermissionWrapper';

export const Status = ['正常', '禁用'];

export interface DataInfoVo{
    id: string,
    noticeTitle: string,
    noticeType: string,
    noticeContent: string,
    status: string,
    createBy: string,
    createTime: string,
    updateBy: string,
    updateTime: string,
    remark: string,
    delFlag: string,
}

// 后台sql查询字段
export function getSearChColumns(){
    return [
    'id',
    'noticeTitle',
    'noticeType',
    'status',
    'createTime',
    'updateTime',
    'remark',
      ];
}

// 搜索配置
export function searchConfig() {
  return {
        'noticeTitle': SearchTypeEnum.LIKE,
        'noticeType': SearchTypeEnum.EQ,
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
      title: t['searchTable.columns.noticeTitle'],
      dataIndex: 'noticeTitle',
      ellipsis:true,
    },

    {
      title: t['searchTable.columns.noticeType'],
      dataIndex: 'noticeType',
      ellipsis:true,
    },

    {
      title: t['searchTable.columns.status'],
      dataIndex: 'status',
      ellipsis:true,
    },

    {
      title: t['searchTable.columns.remark'],
      dataIndex: 'remark',
      ellipsis:true,
    },
    {
      title: t['searchTable.columns.createTime'],
      dataIndex: 'createTime',
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
          <PermissionWrapper
            requiredPermissions={[
              { resource: 'uc:notice', actions: ['update'] },
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
              { resource: 'uc:notice', actions: ['remove'] },
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

