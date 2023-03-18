import React from 'react';
import { Button, Typography, Badge, Popconfirm } from '@arco-design/web-react';
import { SearchTypeEnum } from '@/utils/systemConstant';
import PermissionWrapper from '@/components/PermissionWrapper';
import { dictLabelEnum, getDictList } from '@/utils/dictDataUtils';

export const statusEnum = dictLabelEnum('application_status','string')

export interface DataInfoVo{
    id: number,
    applicationId: string,
    applicationName: string,
    version: string,
    status: string,
    description: string,
    heat: string,
    confData: {
      envs:[],
      image:''
    },
    createTime: string,
    updateTime: string,
    createBy: string,
    updateBy: string,
    delFlag: string,
}

// 后台sql查询字段
export function getSearChColumns(){
    return [
    'id',
    'applicationId',
    'applicationName',
    'version',
    'status',
    'description',
    'heat',
    'confData',
    'createTime',
    'updateTime',
    'createBy',
    'updateBy',
    'delFlag',
      ];
}

// 搜索配置
export function searchConfig() {
  return {
        'applicationId': SearchTypeEnum.EQ,
        'applicationName': SearchTypeEnum.LIKE,
        'version': SearchTypeEnum.EQ,
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
      title: t['searchTable.columns.id'],
      dataIndex: 'id',
      ellipsis:true,
    },

    {
      title: t['searchTable.columns.applicationName'],
      dataIndex: 'applicationName',
      ellipsis:true,
    },

    {
      title: t['searchTable.columns.version'],
      dataIndex: 'version',
      ellipsis:true,
    },

    {
      title: t['searchTable.columns.status'],
      dataIndex: 'status',
      ellipsis:true,
      render: (_, record) => (statusEnum[record.status]),
    },

    {
      title: t['searchTable.columns.heat'],
      dataIndex: 'heat',
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
              { resource: 'application', actions: ['application:info'] },
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
              { resource: 'application', actions: ['application:update'] },
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
              { resource: 'application', actions: ['application:remove'] },
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
          <PermissionWrapper
            requiredPermissions={[
              { resource: 'application', actions: ['application:update'] },
            ]}
          >
           {record.status !==1 && record.status !==2 && record.status !==4 ?<Button
                type="text"
                size="small"
                onClick={() => callback(record, 'submitAudit')}
            >
                {t['searchTable.columns.operations.submitAudit']}
            </Button>:null}
            {record.status ===2 || record.status ===3 ?<Button
                type="text"
                size="small"
                onClick={() => callback(record, 'audit')}
            >
                {t['searchTable.columns.operations.audit']}
            </Button>:null}
          </PermissionWrapper>
        </div>
      ),
    },
  ];
}

