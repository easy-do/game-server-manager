import React, { useEffect, useState } from 'react';
import { Descriptions, Modal, Skeleton } from '@arco-design/web-react';
import locale from './locale';
import useLocale from '@/utils/useLocale';
import { infoRequest } from '@/api/oauthClient';
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
      label: t['searchTable.columns.clientId'],
      value: loading? <Skeleton text={{ rows: 1, style: { width: '200px' } }} animation /> : infoData? infoData.clientId:'',
    },
    {
      label: t['searchTable.columns.clientName'],
      value: loading? <Skeleton text={{ rows: 1, style: { width: '200px' } }} animation /> : infoData? infoData.clientName:'',
    },
    {
      label: t['searchTable.columns.clientSecret'],
      value: loading? <Skeleton text={{ rows: 1, style: { width: '200px' } }} animation /> : infoData? infoData.clientSecret:'',
    },
    {
      label: t['searchTable.columns.scope'],
      value: loading? <Skeleton text={{ rows: 1, style: { width: '200px' } }} animation /> : infoData? infoData.scope:'',
    },
    {
      label: t['searchTable.columns.authorizedGrantTypes'],
      value: loading? <Skeleton text={{ rows: 1, style: { width: '200px' } }} animation /> : infoData? infoData.authorizedGrantTypes:'',
    },
    {
      label: t['searchTable.columns.redirectUri'],
      value: loading? <Skeleton text={{ rows: 1, style: { width: '200px' } }} animation /> : infoData? infoData.redirectUri:'',
    },
    {
      label: t['searchTable.columns.status'],
      value: loading? <Skeleton text={{ rows: 1, style: { width: '200px' } }} animation /> : infoData? infoData.status:'',
    },
    {
      label: t['searchTable.columns.isAudit'],
      value: loading? <Skeleton text={{ rows: 1, style: { width: '200px' } }} animation /> : infoData? infoData.isAudit:'',
    },
    {
      label: t['searchTable.columns.remark'],
      value: loading? <Skeleton text={{ rows: 1, style: { width: '200px' } }} animation /> : infoData? infoData.remark:'',
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
