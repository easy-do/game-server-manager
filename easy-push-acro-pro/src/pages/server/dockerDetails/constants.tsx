import React from 'react';
import { Button, Typography, Badge, Popconfirm } from '@arco-design/web-react';
import { SearchTypeEnum } from '@/utils/systemConstant';
import PermissionWrapper from '@/components/PermissionWrapper';
import moment from 'moment';

export const Status = ['正常', '禁用'];

export interface DataInfoVo{
    id: string,
    dockerName: string,
    dockerHost: string,
    dockerCert: string,
    dockerIsSsl: string,
    dockerCertPassword: string,
    createBy: string,
    createTime: string,
    updateBy: string,
    updateTime: string,
    info:any,
    version:any,
}

// 后台sql查询字段
export function getSearChColumns(){
    return [
    'id',
    'dockerName',
    'dockerHost',
    'dockerCert',
    'sslFlag',
    'dockerCertPassword',
    'createBy',
    'createTime',
    'updateBy',
    'updateTime',
      ];
}

// 搜索配置
export function searchConfig() {
  return {
        'dockerName': SearchTypeEnum.LIKE,
        'dockerHost': SearchTypeEnum.LIKE,
        'dockerIsSsl': SearchTypeEnum.EQ,
        'dockerCertPassword': SearchTypeEnum.EQ,
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
      title: t['searchTable.columns.dockerName'],
      dataIndex: 'dockerName',
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
              { resource: 'dockerDetails', actions: ['dockerDetails:info'] },
            ]}
          >
            <Button
                type="text"
                size="small"
                onClick={() => callback(record, 'imageList')}
            >
                {t['searchTable.columns.imageList.view']}
            </Button>
          </PermissionWrapper> 
          <PermissionWrapper
            requiredPermissions={[
              { resource: 'dockerDetails', actions: ['dockerDetails:info'] },
            ]}
          >
            <Button
                type="text"
                size="small"
                onClick={() => callback(record, 'containerList')}
            >
                {t['searchTable.columns.containerList.view']}
            </Button>
          </PermissionWrapper> 
          <PermissionWrapper
            requiredPermissions={[
              { resource: 'dockerDetails', actions: ['dockerDetails:info'] },
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
              { resource: 'dockerDetails', actions: ['dockerDetails:update'] },
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
              { resource: 'dockerDetails', actions: ['dockerDetails:remove'] },
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


//镜像列表展示字段
export function getImageListColumns(
  t: any,
  callback: (record: Record<string, any>, type: string) => Promise<void>
) {
  return [
    {
      title: t['searchTable.columns.Id'],
      dataIndex: 'Id',
      ellipsis:true,
      render: (_, record) => (
        <Typography.Paragraph copyable>
        {record.Id}
      </Typography.Paragraph>
      ),
    },
    // {
    //   title: t['searchTable.columns.Repo'],
    //   dataIndex: 'RepoTags[0]',
    //   ellipsis:true,
    //   render: (_, record) => (
    //     <Typography.Paragraph copyable>
    //     {record.RepoTags && record.RepoTags[0]? record.RepoTags[0].split(":")[0]:''}
    //   </Typography.Paragraph>
        
    //   ),
    // },
    // {
    //   title: t['searchTable.columns.Tag'],
    //   dataIndex: 'RepoTags[0]',
    //   ellipsis:true,
    //   render: (_, record) => (
    //     <Typography.Paragraph copyable>
    //     {record.RepoTags && record.RepoTags[0]? record.RepoTags[0].split(":")[1]:''}
    //   </Typography.Paragraph>
    //   ),
    // },
    {
      title: t['searchTable.columns.Digests'],
      dataIndex: 'RepoTags[0]',
      ellipsis:true,
      render: (_, record) => (
        <Typography.Paragraph copyable>
        {record.RepoTags[0]}
      </Typography.Paragraph>
      ),
    },
    {
      title: '大小',
      dataIndex: 'Size',
      ellipsis:true,
      render: (_, record) => (
        (record.Size/1024/1024).toFixed() +'MB'
      ),
    },
    {
      title: '创建日期',
      dataIndex: 'Created',
      ellipsis:true,
      render: (_, record) => (
        moment(record.created).format("YYYY/MM/DD HH:mm:ss")
      ),
    },
    {
      title: t['searchTable.columns.operations'],
      dataIndex: 'operations',
      headerCellStyle: { paddingLeft: '15px' },
      render: (_, record) => (
        <div> 
          {/* <PermissionWrapper
            requiredPermissions={[
              { resource: 'dockerDetails', actions: ['dockerDetails:info'] },
            ]}
          >
            <Button
                type="text"
                size="small"
                onClick={() => callback(record, 'view')}
            >
                {t['searchTable.columns.operations.view']}
            </Button>
          </PermissionWrapper> */}
          <PermissionWrapper
            requiredPermissions={[
              { resource: 'dockerDetails', actions: ['dockerDetails:remove'] },
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


//容器列表展示字段
export function getContainerListColumns(
  t: any,
  callback: (record: Record<string, any>, type: string) => Promise<void>
) {
  return [
    
    {
      title: t['searchTable.columns.Id'],
      dataIndex: 'Id',
      ellipsis:true,
      render: (_, record) => (
        <Typography.Paragraph copyable>
        {record.Id}
      </Typography.Paragraph>
      ),
    },
    {
      title: t['searchTable.columns.Names'],
      dataIndex: 'Names',
      ellipsis:true,
      render: (_, record) => (
        <Typography.Paragraph copyable>
        {record.Names[0].replace('/','')}
      </Typography.Paragraph>
      ),
    },
    {
      title: t['searchTable.columns.Image'],
      dataIndex: 'Image',
      ellipsis:true,
      render: (_, record) => (
        <Typography.Paragraph copyable>
        {record.Image}
      </Typography.Paragraph>
      ),
    },
    {
      title: t['searchTable.columns.Status'],
      dataIndex: 'State',
      ellipsis:true,
      render: (_, record) => (
        <Typography.Paragraph>
        {t['searchTable.columns.'+record.State]}
      </Typography.Paragraph>
      ),
    },
    {
      title: t['searchTable.columns.runTime'],
      dataIndex: 'Status',
      ellipsis:true,
      render: (_, record) => (
        <Typography.Paragraph>
        {record.Status}
      </Typography.Paragraph>
      ),
    },
    // {
    //   title: t['searchTable.columns.NetworkMode'],
    //   dataIndex: 'HostConfig.NetworkMode',
    //   ellipsis:true,
    //   render: (_, record) => (
    //     <Typography.Paragraph>
    //     {record.HostConfig.NetworkMode}
    //   </Typography.Paragraph>
    //   ),
    // },
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
                onClick={() => callback(record, 'log')}
            >
                {t['searchTable.columns.operations.log']}
            </Button>
            <Button
                type="text"
                size="small"
                disabled={record.State === 'running'}
                onClick={() => callback(record, 'start')}
            >
                {t['searchTable.columns.operations.start']}
            </Button>
            <Button
                type="text"
                size="small"
                onClick={() => callback(record, 'restart')}
            >
                {t['searchTable.columns.operations.restart']}
            </Button>
            <Button
                type="text"
                size="small"
                disabled={record.State === 'exited'}
                onClick={() => callback(record, 'stop')}
            >
                {t['searchTable.columns.operations.stop']}
            </Button>
            <Button
                type="text"
                size="small"
                onClick={() => callback(record, 'rename')}
            >
                {t['searchTable.columns.operations.rename']}
            </Button>
            <Popconfirm
                disabled={record.State === 'running'}
                title={t['searchTable.columns.operations.remove.confirm']}
                onOk={() => callback(record, 'remove')}
            >
                <Button type="text" disabled={record.State === 'running'} status="warning" size="small">
                {t['searchTable.columns.operations.remove']}
                </Button>
            </Popconfirm>
        </div>
      ),
    },
  ];
}
