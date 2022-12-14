import React, { useEffect, useState } from 'react';
import { Descriptions, Modal, Skeleton } from '@arco-design/web-react';
import locale from './locale';
import useLocale from '@/utils/useLocale';
import { infoRequest } from '@/api/applicationInfo';
import { DataInfoVo } from './constants';
import Paragraph from '@arco-design/web-react/es/Typography/paragraph';


function InfoPage(props: {id:number,visible,setVisible}) {

  const t = useLocale(locale);
  
    const [loading,setLoading] = useState(false)

    const [infoData, setInfoData] = useState<DataInfoVo>();
    
    function fetchData() {
      setLoading(true)
      if (props.id !== undefined && props.visible) {
        infoRequest(props.id).then((res) => {
          const { success, data } = res.data;
          if (success) {
            setInfoData(data);
          }
          setLoading(false)
        });
      }
    }

    useEffect(() => {
        fetchData();
      }, [props.id,props.visible]);



  let pluginsData:any = {}
  if(infoData && infoData.pluginsData){
    pluginsData = JSON.parse(infoData.pluginsData)
  }

  const data = [
    {
      label: t['searchTable.columns.applicationId'],
      value: loading? <Skeleton text={{ rows: 1, style: { width: '200px' } }} animation /> : infoData? infoData.applicationId:'',
    },
    {
      label: t['searchTable.columns.applicationName'],
      value: loading? <Skeleton text={{ rows: 1, style: { width: '200px' } }} animation /> : infoData? infoData.applicationName:'',
    },
    {
      label: t['searchTable.columns.deviceName'],
      value: loading? <Skeleton text={{ rows: 1, style: { width: '200px' } }} animation /> : infoData? infoData.deviceName:'',
    },
    // {
    //   label: t['searchTable.columns.deviceType'],
    //   value: loading? <Skeleton text={{ rows: 1, style: { width: '200px' } }} animation /> : infoData? infoData.deviceType:'',
    // },
    {
      label: t['searchTable.columns.appName'],
      value: loading? <Skeleton text={{ rows: 1, style: { width: '200px' } }} animation /> : infoData? infoData.appName:'',
    },
    {
      label: t['searchTable.columns.status'],
      value: loading? <Skeleton text={{ rows: 1, style: { width: '200px' } }} animation /> : infoData? infoData.status:'',
    },
    {
      label: t['searchTable.columns.createTime'],
      value: loading? <Skeleton text={{ rows: 1, style: { width: '200px' } }} animation /> : infoData? infoData.createTime:'',
    },
    // {
    //   label: t['searchTable.columns.updateTime'],
    //   value: loading? <Skeleton text={{ rows: 1, style: { width: '200px' } }} animation /> : infoData? infoData.updateTime:'',
    // },
    {
      label: t['searchTable.columns.lastUpTime'],
      value: loading? <Skeleton text={{ rows: 1, style: { width: '200px' } }} animation /> : infoData? infoData.lastUpTime:'',
    },
    {
      label: t['searchTable.columns.pluingsData.version'],
      value: loading ? <Skeleton text={{ rows: 1, style: { width: '200px' } }} animation /> : pluginsData && pluginsData.version ? pluginsData.version : '?????????',
    },
    {
      label: t['searchTable.columns.pluingsData.ip'],
      value: loading ? <Skeleton text={{ rows: 1, style: { width: '200px' } }} animation /> : pluginsData && pluginsData.ip ? pluginsData.ip : '?????????',
    },
    {
      label: t['searchTable.columns.pluingsData.systemInfo.osName'],
      value: loading ? <Skeleton text={{ rows: 1, style: { width: '200px' } }} animation /> : pluginsData && pluginsData.systemInfo ? pluginsData.systemInfo.osName : '?????????',
    },
    {
      label: t['searchTable.columns.pluingsData.systemInfo.cpuInfo'],
      value: loading ? <Skeleton text={{ rows: 1, style: { width: '200px' } }} animation /> : pluginsData && pluginsData.systemInfo ? pluginsData.systemInfo.cpuInfo : '?????????',
    },
    {
      label: t['searchTable.columns.pluingsData.systemInfo.memory'],
      value: loading ? <Skeleton text={{ rows: 1, style: { width: '200px' } }} animation /> : pluginsData && pluginsData.systemInfo ? pluginsData.systemInfo.memory : '?????????',
    },
    {
      label: t['searchTable.columns.pluginsData'],
      value: loading ? <Skeleton text={{ rows: 1, style: { width: '200px' } }} animation /> : pluginsData ? <Paragraph copyable={{tooltips:pluginsData.config,text:pluginsData.config}} >????????????</Paragraph> : '?????????',
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
