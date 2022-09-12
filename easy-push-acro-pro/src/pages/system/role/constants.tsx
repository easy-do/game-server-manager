import React from 'react';
import { Button, Typography, Badge } from '@arco-design/web-react';

const { Text } = Typography;

export interface DataInfoVo {
  delFlag: string;
  remark: string;
  updateTime: string;
  updateBy: string;
  createTime: string;
  createBy: string;
  status: string;
  isDefault: string;
  roleSort: string;
  roleKey: string;
  roleName: string;
  roleId: string;
}

// 后台sql查询字段
export function getSearChColumns() {
  return [
    'delFlag',
    'remark',
    'updateTime',
    'updateBy',
    'createTime',
    'createBy',
    'status',
    'isDefault',
    'roleSort',
    'roleKey',
    'roleName',
    'roleId',
  ];
}

//默认排序字段
export function getDefaultOrders() {
  return [{ column: 'createTime', asc: false }];
}

//表单展示字段
export function getColumns(
  t: any,
  callback: (record: Record<string, any>, type: string) => Promise<void>
) {
  return [
    {
      title: t['searchTable.columns.delFlag'],
      dataIndex: 'delFlag',
    },
    {
      title: t['searchTable.columns.remark'],
      dataIndex: 'remark',
    },
    {
      title: t['searchTable.columns.updateTime'],
      dataIndex: 'updateTime',
    },
    {
      title: t['searchTable.columns.updateBy'],
      dataIndex: 'updateBy',
    },
    {
      title: t['searchTable.columns.createTime'],
      dataIndex: 'createTime',
    },
    {
      title: t['searchTable.columns.createBy'],
      dataIndex: 'createBy',
    },
    {
      title: t['searchTable.columns.status'],
      dataIndex: 'status',
    },
    {
      title: t['searchTable.columns.isDefault'],
      dataIndex: 'isDefault',
    },
    {
      title: t['searchTable.columns.roleSort'],
      dataIndex: 'roleSort',
    },
    {
      title: t['searchTable.columns.roleKey'],
      dataIndex: 'roleKey',
    },
    {
      title: t['searchTable.columns.roleName'],
      dataIndex: 'roleName',
    },
    {
      title: t['searchTable.columns.roleId'],
      dataIndex: 'roleId',
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
        </div>
      ),
    },
  ];
}
