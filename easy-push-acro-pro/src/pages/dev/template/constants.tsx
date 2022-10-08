import React from 'react';
import { Button, Popconfirm } from '@arco-design/web-react';
import { dictLabelEnum } from '@/utils/dictDataUtils';

const scopeSelect = dictLabelEnum("scope_select",'string')

export interface DataInfoVo {
  delFlag: string;
  createName: string;
  updateBy: string;
  createBy: string;
  updateTime: string;
  createTime: string;
  description: string;
  filePath: string;
  fileName: string;
  version: string;
  templateScope: string;
  templateType: string;
  templateCode: string;
  templateName: string;
  id: string;
}

// 后台sql查询字段
export function getSearChColumns() {
  return [
    'delFlag',
    'createName',
    'updateBy',
    'createBy',
    'updateTime',
    'createTime',
    'description',
    'filePath',
    'fileName',
    'version',
    'templateScope',
    'templateType',
    'templateCode',
    'templateName',
    'id',
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
      title: t['searchTable.columns.templateName'],
      dataIndex: 'templateName',
      ellipsis:true,
      sorter: true,
    },
    {
      title: t['searchTable.columns.templateType'],
      dataIndex: 'templateType',
      ellipsis:true,
      sorter: true,
    },
    {
      title: t['searchTable.columns.version'],
      dataIndex: 'version',
      ellipsis:true,
      sorter: true,
    },
    {
      title: t['searchTable.columns.templateScope'],
      dataIndex: 'templateScope',
      render: (_, record) => (scopeSelect[record.templateScope]),
      ellipsis:true,
      sorter: true,
    },
    {
      title: t['searchTable.columns.createName'],
      dataIndex: 'createName',
      ellipsis:true,
      sorter: true,
    },
    {
      title: t['searchTable.columns.description'],
      dataIndex: 'description',
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
          <Button
            type="text"
            size="small"
            onClick={() => callback(record, 'editCode')}
          >
            {t['searchTable.columns.operations.editCode']}
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
