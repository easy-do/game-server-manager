import React from 'react';
import { Button, Typography, Badge, Popconfirm } from '@arco-design/web-react';
import { SearchTypeEnum } from '@/utils/systemConstant';
import PermissionWrapper from '@/components/PermissionWrapper';
import { dictLabelEnum } from '@/utils/dictDataUtils';

export const statusEnum = dictLabelEnum('status_select','string')

export interface DataInfoVo{
    id: string,
    applicationId: string,
    applicationName: string,
    appId: string,
    appName: string,
    scriptId: string,
    scriptName: string,
    deviceId: string,
    deviceName: string,
    deviceType: string,
    startTime: string,
    endTime: string,
    logData: string,
    executeState: string,
    createTime: string,
    createBy: string,
    delFlag: string,
}

// 后台sql查询字段
export function getSearChColumns(){
    return [
    'id',
    'applicationId',
    'applicationName',
    'appId',
    'appName',
    'scriptId',
    'scriptName',
    'deviceId',
    'deviceName',
    'deviceType',
    'startTime',
    'endTime',
    'logData',
    'executeState',
    'createTime',
    'createBy',
    'delFlag',
      ];
}

// 搜索配置
export function searchConfig() {
  return {
        'applicationName': SearchTypeEnum.LIKE,
        'appName': SearchTypeEnum.LIKE,
        'scriptName': SearchTypeEnum.LIKE,
        'deviceName': SearchTypeEnum.LIKE,
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
      title: t['searchTable.columns.applicationName'],
      dataIndex: 'applicationName',
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
      title: t['searchTable.columns.scriptName'],
      dataIndex: 'scriptName',
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
      title: t['searchTable.columns.startTime'],
      dataIndex: 'startTime',
      ellipsis:true,
      sorter: true,
    },

    {
      title: t['searchTable.columns.endTime'],
      dataIndex: 'endTime',
      ellipsis:true,
      sorter: true,
    },

    {
      title: t['searchTable.columns.executeState'],
      dataIndex: 'executeState',
      ellipsis:true,
      sorter: true,
    },
    {
      title: t['searchTable.columns.operations'],
      dataIndex: 'operations',
      headerCellStyle: { paddingLeft: '15px' },
      render: (_, record) => (
        <div>  
          <PermissionWrapper
            requiredPermissions={[
              { resource: 'server:executeLog', actions: ['info'] },
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
        </div>
      ),
    },
  ];
}

