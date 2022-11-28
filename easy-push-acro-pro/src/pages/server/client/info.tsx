import React, { useEffect, useState } from 'react';
import { Descriptions, Modal, Skeleton } from '@arco-design/web-react';
import locale from './locale';
import useLocale from '@/utils/useLocale';
import { infoRequest } from '@/api/clientInfo';
import { DataInfoVo } from './constants';


function InfoPage(props: { id: number, visible, setVisible }) {

  const [loading, setLoading] = useState(false)

  const [clientData, setClientData] = useState({
    clientId: "",
    env: "",
    ip: "",
    systemInfo: {
      cpuInfo: "",
      memory: "",
      osName: ""
    },
    version: ""
  })

  const [infoData, setInfoData] = useState<DataInfoVo>();

  function fetchData() {
    setLoading(true)
    if (props.id !== undefined && props.visible) {
      infoRequest(props.id).then((res) => {
        const { success, data } = res.data;
        if (success) {
          setInfoData(data);
          let clientData;
          try{
            clientData = JSON.parse(infoData.clientData)
            setClientData(clientData);
          }catch{
          }
          
        }
        setLoading(false)
      });
    }
  }

  useEffect(() => {
    fetchData();
  }, [props.id,props.visible]);

  const t = useLocale(locale);

  const data = [
    {
      label: t['searchTable.columns.id'],
      value: loading ? <Skeleton text={{ rows: 1, style: { width: '200px' } }} animation /> : infoData ? infoData.id : '',
    },
    {
      label: t['searchTable.columns.clientName'],
      value: loading ? <Skeleton text={{ rows: 1, style: { width: '200px' } }} animation /> : infoData ? infoData.clientName : '',
    },
    {
      label: t['searchTable.columns.serverName'],
      value: loading ? <Skeleton text={{ rows: 1, style: { width: '200px' } }} animation /> : infoData ? infoData.serverName : '',
    },
    {
      label: t['searchTable.columns.status'],
      value: loading ? <Skeleton text={{ rows: 1, style: { width: '200px' } }} animation /> : infoData ? infoData.status : '',
    },
    {
      label: t['searchTable.columns.clientData.version'],
      value: loading ? <Skeleton text={{ rows: 1, style: { width: '200px' } }} animation /> : clientData && clientData.version ? clientData.version : '未同步',
    },
    {
      label: t['searchTable.columns.clientData.systemInfo.osName'],
      value: loading ? <Skeleton text={{ rows: 1, style: { width: '200px' } }} animation /> : clientData && clientData.systemInfo ? clientData.systemInfo.osName : '未同步',
    },
    {
      label: t['searchTable.columns.clientData.systemInfo.cpuInfo'],
      value: loading ? <Skeleton text={{ rows: 1, style: { width: '200px' } }} animation /> : clientData && clientData.systemInfo ? clientData.systemInfo.cpuInfo : '未同步',
    },
    {
      label: t['searchTable.columns.clientData.systemInfo.memory'],
      value: loading ? <Skeleton text={{ rows: 1, style: { width: '200px' } }} animation /> : clientData && clientData.systemInfo ? clientData.systemInfo.memory : '未同步',
    },
    {
      label: t['searchTable.columns.createTime'],
      value: loading ? <Skeleton text={{ rows: 1, style: { width: '200px' } }} animation /> : infoData ? infoData.createTime : '',
    },
    {
      label: t['searchTable.columns.lastUpTime'],
      value: loading ? <Skeleton text={{ rows: 1, style: { width: '200px' } }} animation /> : infoData ? infoData.lastUpTime : '',
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
