import React, { useEffect, useState } from 'react';
import { Descriptions, Modal, Skeleton } from '@arco-design/web-react';
import locale from './locale';
import useLocale from '@/utils/useLocale';
import { infoRequest } from '@/api/clientInfo';
import { DataInfoVo } from './constants';

function InfoPage(props: { id: number; visible; setVisible }) {
  const [loading, setLoading] = useState(false);

  const [infoData, setInfoData] = useState<DataInfoVo>({
    id: null,
    clientName: null,
    serverId: null,
    serverName: null,
    userName: null,
    status: null,
    clientData: null,
    createTime: null,
    updateTime: null,
    lastUpTime: null,
    delFlag: null,
    publicKey: null,
    privateKey: null,
    createBy: null,
    updateBy: null,
  });

  function fetchData() {
    setLoading(true);
    if (props.id !== undefined && props.visible) {
      infoRequest(props.id).then((res) => {
        const { success, data } = res.data;
        if (success) {
          try {
            data.clientData = JSON.parse(data.clientData);
          } catch {}
          setInfoData(data);
        }
        setLoading(false);
      });
    }
  }

  useEffect(() => {
    fetchData();
  }, [props.id, props.visible]);

  const t = useLocale(locale);

  const data = [
    {
      label: t['searchTable.columns.id'],
      value: loading ? (
        <Skeleton text={{ rows: 1, style: { width: '200px' } }} animation />
      ) : infoData ? (
        infoData.id
      ) : (
        ''
      ),
    },
    {
      label: t['searchTable.columns.clientName'],
      value: loading ? (
        <Skeleton text={{ rows: 1, style: { width: '200px' } }} animation />
      ) : infoData ? (
        infoData.clientName
      ) : (
        ''
      ),
    },
    {
      label: t['searchTable.columns.serverName'],
      value: loading ? (
        <Skeleton text={{ rows: 1, style: { width: '200px' } }} animation />
      ) : infoData ? (
        infoData.serverName
      ) : (
        ''
      ),
    },
    {
      label: t['searchTable.columns.status'],
      value: loading ? (
        <Skeleton text={{ rows: 1, style: { width: '200px' } }} animation />
      ) : infoData ? (
        infoData.status
      ) : (
        ''
      ),
    },
    {
      label: t['searchTable.columns.clientData.version'],
      value: loading ? (
        <Skeleton text={{ rows: 1, style: { width: '200px' } }} animation />
      ) : infoData.clientData && infoData.clientData.version ? (
        infoData.clientData.version
      ) : (
        '未同步'
      ),
    },
    {
      label: t['searchTable.columns.clientData.ip'],
      value: loading ? (
        <Skeleton text={{ rows: 1, style: { width: '200px' } }} animation />
      ) : infoData.clientData && infoData.clientData.ip ? (
        infoData.clientData.ip
      ) : (
        '未同步'
      ),
    },
    {
      label: t['searchTable.columns.clientData.port'],
      value: loading ? (
        <Skeleton text={{ rows: 1, style: { width: '200px' } }} animation />
      ) : infoData.clientData && infoData.clientData.port ? (
        infoData.clientData.port
      ) : (
        '未同步'
      ),
    },
    {
      label: t['searchTable.columns.clientData.systemInfo.osName'],
      value: loading ? (
        <Skeleton text={{ rows: 1, style: { width: '200px' } }} animation />
      ) : infoData.clientData && infoData.clientData.systemInfo ? (
        infoData.clientData.systemInfo.osName
      ) : (
        '未同步'
      ),
    },
    {
      label: t['searchTable.columns.clientData.systemInfo.cpuInfo'],
      value: loading ? (
        <Skeleton text={{ rows: 1, style: { width: '200px' } }} animation />
      ) : infoData.clientData && infoData.clientData.systemInfo ? (
        infoData.clientData.systemInfo.cpuInfo
      ) : (
        '未同步'
      ),
    },
    {
      label: t['searchTable.columns.clientData.systemInfo.memory'],
      value: loading ? (
        <Skeleton text={{ rows: 1, style: { width: '200px' } }} animation />
      ) : infoData.clientData && infoData.clientData.systemInfo ? (
        infoData.clientData.systemInfo.memory
      ) : (
        '未同步'
      ),
    },
    {
      label: t['searchTable.columns.createTime'],
      value: loading ? (
        <Skeleton text={{ rows: 1, style: { width: '200px' } }} animation />
      ) : infoData ? (
        infoData.createTime
      ) : (
        ''
      ),
    },
    {
      label: t['searchTable.columns.lastUpTime'],
      value: loading ? (
        <Skeleton text={{ rows: 1, style: { width: '200px' } }} animation />
      ) : infoData ? (
        infoData.lastUpTime
      ) : (
        ''
      ),
    },
  ];

  return (
    <Modal
      title={t['searchTable.info.title']}
      visible={props.visible}
      onOk={() => {
        props.setVisible(false);
      }}
      onCancel={() => {
        props.setVisible(false);
      }}
      autoFocus={false}
      focusLock={true}
      maskClosable={false}
    >
      <Descriptions
        column={1}
        data={data}
        style={{ marginBottom: 20 }}
        labelStyle={{ paddingRight: 36 }}
      />
    </Modal>
  );
}

export default InfoPage;
