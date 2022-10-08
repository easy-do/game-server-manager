import React from 'react';
import { Button, Typography, Badge, Popconfirm } from '@arco-design/web-react';
import { SearchTypeEnum } from '@/utils/systemConstant';
import PermissionWrapper from '@/components/PermissionWrapper';
import { dictLabelEnum } from '@/utils/dictDataUtils';

export const statusEnum = dictLabelEnum('status_select','string')

export interface DataInfoVo{
    id: string,
    groupName: string,
    filePath: string,
    fileName: string,
    fileSize: string,
    ossType: string,
    createTime: string,
    updateTime: string,
    createBy: string,
    updateBy: string,
    remark: string,
    delFlag: string,
}

// 后台sql查询字段
export function getSearChColumns(){
    return [
    'id',
    'groupName',
    'filePath',
    'fileName',
    'fileSize',
    'ossType',
    'createTime',
    'updateTime',
    'createBy',
    'updateBy',
    'remark',
    'delFlag',
      ];
}

// 搜索配置
export function searchConfig() {
  return {
        'groupName': SearchTypeEnum.LIKE,
        'filePath': SearchTypeEnum.LIKE,
        'fileName': SearchTypeEnum.LIKE,
        'fileSize': SearchTypeEnum.EQ,
        'ossType': SearchTypeEnum.EQ,
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
      title: t['searchTable.columns.groupName'],
      dataIndex: 'groupName',
      ellipsis:true,
    },

    {
      title: t['searchTable.columns.fileName'],
      dataIndex: 'fileName',
      ellipsis:true,
    },

    {
      title: t['searchTable.columns.fileSize'],
      dataIndex: 'fileSize',
      ellipsis:true,
    },

    {
      title: t['searchTable.columns.ossType'],
      dataIndex: 'ossType',
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
          <PermissionWrapper
            requiredPermissions={[
              { resource: 'oss:ossManagement', actions: ['info'] },
            ]}
          >
            <Button
                type="text"
                size="small"
                onClick={() => callback(record, 'view')}
            >
                {t['searchTable.columns.operations.view']}
            </Button>
          </PermissionWrapper>
          <PermissionWrapper
            requiredPermissions={[
              { resource: 'oss:ossManagement', actions: ['remove'] },
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

