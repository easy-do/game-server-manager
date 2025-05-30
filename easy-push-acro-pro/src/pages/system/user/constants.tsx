import React from 'react';
import { Button, Typography, Badge } from '@arco-design/web-react';
import { dictLabelEnum } from '@/utils/dictDataUtils';
import {
  SearchType,
  SelectColumnType,
} from '@/utils/searchUtil';

const { Text } = Typography;

export const statusEnum = dictLabelEnum('status_select', 'string');

export interface UserInfoVo {
  authorization: string;
  avatarUrl: string;
  createTime: string;
  delFlag: number;
  id: number;
  lastLoginTime: string;
  loginIp: string;
  nickName: string;
  email: string;
  platform: string;
  points: number;
  secret: string;
  state: number;
  unionId: string;
  updateBy: number;
  updateTime: string;
}

//查询条件配置
export const searchConfig = {
  nickName: SearchType.LIKE,
  createTime: SearchType.BETWEEN,
};

// 后台sql查询字段
export const selectColumns = {
  columns: [
    'id',
    'nickName',
    'platform',
    'state',
    'authorization',
    'loginIp',
    'lastLoginTime',
    'createTime',
  ],
  type: SelectColumnType.onlySelect,
};

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
      title: t['searchTable.columns.id'],
      dataIndex: 'id',
      ellipsis: true,
      sorter: true,
      render: (value) => <Text copyable>{value}</Text>,
    },
    {
      title: t['searchTable.columns.name'],
      dataIndex: 'nickName',
      ellipsis: true,
      sorter: true,
    },
    {
      title: t['searchTable.columns.platform'],
      dataIndex: 'platform',
      ellipsis: true,
      sorter: true,
    },
    {
      title: t['searchTable.columns.loginIp'],
      dataIndex: 'loginIp',
      ellipsis: true,
      sorter: true,
    },
    {
      title: t['searchTable.columns.lastLoginTime'],
      dataIndex: 'lastLoginTime',
      ellipsis: true,
      sorter: true,
    },
    {
      title: t['searchTable.columns.createTime'],
      dataIndex: 'createTime',
      ellipsis: true,
      sorter: true,
    },
    {
      title: t['searchTable.columns.status'],
      dataIndex: 'state',
      render: (x) => {
        if (x === 1) {
          return <Badge status="error" text={dictLabelEnum[x]}></Badge>;
        }
        return <Badge status="success" text={dictLabelEnum[x]}></Badge>;
      },
    },
    {
      title: t['searchTable.columns.operations'],
      dataIndex: 'operations',
      headerCellStyle: { paddingLeft: '15px' },
      render: (_, record) => (
        <Button
          type="text"
          size="small"
          onClick={() => callback(record, 'view')}
        >
          {t['searchTable.columns.operations.view']}
        </Button>
      ),
    },
  ];
}
