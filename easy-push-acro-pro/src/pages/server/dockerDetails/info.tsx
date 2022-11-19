import React, { useEffect, useState } from 'react';
import { Descriptions, Modal, Skeleton } from '@arco-design/web-react';
import locale from './locale';
import useLocale from '@/utils/useLocale';
import { infoRequest } from '@/api/dockerDetails';
import { DataInfoVo } from './constants';


function InfoPage(props: {id:number,visible,setVisible}) {
  
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

  const t = useLocale(locale);

  const data = [
    {
      label: t['searchTable.columns.dockerName'],
      value: loading? <Skeleton text={{ rows: 1, style: { width: '200px' } }} animation /> : infoData? infoData.dockerName:'',
    },
    {
      label: t['searchTable.columns.dockerHost'],
      value: loading? <Skeleton text={{ rows: 1, style: { width: '200px' } }} animation /> : infoData? infoData.dockerHost:'',
    },
    {
      label: t['searchTable.columns.Version'],
      value: loading? <Skeleton text={{ rows: 1, style: { width: '200px' } }} animation /> : infoData && infoData.versionJson ? JSON.parse(infoData.versionJson).Version:'',
    },
    {
      label: t['searchTable.columns.Arch'],
      value: loading? <Skeleton text={{ rows: 1, style: { width: '200px' } }} animation /> : infoData && infoData.versionJson ? JSON.parse(infoData.versionJson).Arch:'',
    }, 
    {
      label: t['searchTable.columns.KernelVersion'],
      value: loading? <Skeleton text={{ rows: 1, style: { width: '200px' } }} animation /> : infoData && infoData.versionJson ? JSON.parse(infoData.versionJson).KernelVersion:'',
    },
    {
      label: t['searchTable.columns.GoVersion'],
      value: loading? <Skeleton text={{ rows: 1, style: { width: '200px' } }} animation /> : infoData && infoData.versionJson ? JSON.parse(infoData.versionJson).GoVersion:'',
    },
    {
      label: t['searchTable.columns.ApiVersion'],
      value: loading? <Skeleton text={{ rows: 1, style: { width: '200px' } }} animation /> : infoData && infoData.versionJson ? JSON.parse(infoData.versionJson).ApiVersion:'',
    },
    {
      label: t['searchTable.columns.MinAPIVersion'],
      value: loading? <Skeleton text={{ rows: 1, style: { width: '200px' } }} animation /> : infoData && infoData.versionJson ? JSON.parse(infoData.versionJson).MinAPIVersion:'',
    },
    {
      label: t['searchTable.columns.OperatingSystem'],
      value: loading? <Skeleton text={{ rows: 1, style: { width: '200px' } }} animation /> : infoData && infoData.detailsJson ? JSON.parse(infoData.detailsJson).OperatingSystem:'',
    },
    {
      label: t['searchTable.columns.Images'],
      value: loading? <Skeleton text={{ rows: 1, style: { width: '200px' } }} animation /> : infoData && infoData.detailsJson ? JSON.parse(infoData.detailsJson).Images:'',
    },
    {
      label: t['searchTable.columns.Containers'],
      value: loading? <Skeleton text={{ rows: 1, style: { width: '200px' } }} animation /> : infoData && infoData.detailsJson ? JSON.parse(infoData.detailsJson).Containers:'',
    },
    {
      label: t['searchTable.columns.ContainersRunning'],
      value: loading? <Skeleton text={{ rows: 1, style: { width: '200px' } }} animation /> : infoData && infoData.detailsJson ? JSON.parse(infoData.detailsJson).ContainersRunning:'',
    },
    {
      label: t['searchTable.columns.ContainersPaused'],
      value: loading? <Skeleton text={{ rows: 1, style: { width: '200px' } }} animation /> : infoData && infoData.detailsJson ? JSON.parse(infoData.detailsJson).ContainersPaused:'',
    },
    {
      label: t['searchTable.columns.ContainersStopped'],
      value: loading? <Skeleton text={{ rows: 1, style: { width: '200px' } }} animation /> : infoData && infoData.detailsJson ? JSON.parse(infoData.detailsJson).ContainersStopped:'',
    },
    {
      label: t['searchTable.columns.MemTotal'],
      value: loading? <Skeleton text={{ rows: 1, style: { width: '200px' } }} animation /> : infoData && infoData.detailsJson ? (JSON.parse(infoData.detailsJson).MemTotal/8388608).toFixed() +'MB':'',
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
