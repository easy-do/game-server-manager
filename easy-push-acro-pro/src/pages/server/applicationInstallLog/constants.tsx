import React from 'react';
import { Button, Typography, Badge, Popconfirm } from '@arco-design/web-react';
import { SearchTypeEnum } from '@/utils/systemConstant';
import PermissionWrapper from '@/components/PermissionWrapper';
import { dictLabelEnum, getDictList } from '@/utils/dictDataUtils';

export const statusEnum = dictLabelEnum('status_select', 'string');

export interface DataInfoVo {
  id: string;
  applicationId: string;
  applicationName: string;
  version: string;
  clientId: string;
  clientName: string;
  startTime: string;
  endTime: string;
  logData: string;
  status: string;
  createTime: string;
  createBy: string;
  delFlag: string;
}

// 后台sql查询字段
export function getSearChColumns() {
  return [
    'id',
    'applicationId',
    'applicationName',
    'version',
    'clientId',
    'clientName',
    'startTime',
    'endTime',
    'logData',
    'status',
    'createTime',
    'createBy',
    'delFlag',
  ];
}

// 搜索配置
export function searchConfig() {
  return {
    applicationName: SearchTypeEnum.LIKE,
    version: SearchTypeEnum.EQ,
    clientName: SearchTypeEnum.LIKE,
    status: SearchTypeEnum.EQ,
  };
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
      title: t['searchTable.columns.applicationName'],
      dataIndex: 'applicationName',
      ellipsis: true,
    },

    {
      title: t['searchTable.columns.version'],
      dataIndex: 'version',
      ellipsis: true,
    },

    {
      title: t['searchTable.columns.clientName'],
      dataIndex: 'clientName',
      ellipsis: true,
    },

    {
      title: t['searchTable.columns.startTime'],
      dataIndex: 'startTime',
      ellipsis: true,
    },

    {
      title: t['searchTable.columns.endTime'],
      dataIndex: 'endTime',
      ellipsis: true,
    },

    {
      title: t['searchTable.columns.status'],
      dataIndex: 'status',
      ellipsis: true,
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
              onClick={() => callback(record, 'log')}
            >
              {t['searchTable.columns.operations.log']}
            </Button>
          <PermissionWrapper
            requiredPermissions={[
              {
                resource: 'applicationInstallLog',
                actions: ['applicationInstallLog:remove'],
              },
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
