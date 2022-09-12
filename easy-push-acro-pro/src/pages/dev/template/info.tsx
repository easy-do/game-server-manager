import React, { useEffect, useState } from 'react';
import { Descriptions, Modal, Skeleton } from '@arco-design/web-react';
import locale from './locale';
import useLocale from '@/utils/useLocale';
import { infoRequest } from '@/api/template';
import { DataInfoVo } from './constants';


function InfoPage(props: {id:number,visible,setVisible}) {
  
  const [loading,setLoading] = useState(false)

  const [infoData, setInfoData] = useState<DataInfoVo>();


  function fetchData() {
    setLoading(true)
    if (props.id !== undefined) {
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
  }, [props.id]);
  const t = useLocale(locale);

  const data = [
    {
      label: t['searchTable.columns.id'],
      value: loading? <Skeleton text={{ rows: 1, style: { width: '200px' } }} animation /> : infoData? infoData.id:'',
    },
    {
      label: t['searchTable.columns.templateName'],
      value: loading? <Skeleton text={{ rows: 1, style: { width: '200px' } }} animation /> : infoData? infoData.templateName:'',
    },
    {
      label: t['searchTable.columns.templateType'],
      value: loading? <Skeleton text={{ rows: 1, style: { width: '200px' } }} animation /> : infoData? infoData.templateType:'',
    },
    {
      label: t['searchTable.columns.templateScope'],
      value: loading? <Skeleton text={{ rows: 1, style: { width: '200px' } }} animation /> : infoData? infoData.templateScope:'',
    },
    {
      label: t['searchTable.columns.description'],
      value: loading? <Skeleton text={{ rows: 1, style: { width: '200px' } }} animation /> : infoData? infoData.description:'',
    },
    {
      label: t['searchTable.columns.fileName'],
      value: loading? <Skeleton text={{ rows: 1, style: { width: '200px' } }} animation /> : infoData? infoData.fileName:'',
    },
    {
      label: t['searchTable.columns.filePath'],
      value: loading? <Skeleton text={{ rows: 1, style: { width: '200px' } }} animation /> : infoData? infoData.filePath:'',
    },
    {
      label: t['searchTable.columns.version'],
      value: loading? <Skeleton text={{ rows: 1, style: { width: '200px' } }} animation /> : infoData? infoData.version:'',
    },
    {
      label: t['searchTable.columns.createName'],
      value: loading? <Skeleton text={{ rows: 1, style: { width: '200px' } }} animation /> : infoData? infoData.createName:'',
    },
    {
      label: t['searchTable.columns.createTime'],
      value: loading? <Skeleton text={{ rows: 1, style: { width: '200px' } }} animation /> : infoData? infoData.createTime:'',
    },
    {
      label: t['searchTable.columns.updateTime'],
      value: loading? <Skeleton text={{ rows: 1, style: { width: '200px' } }} animation /> : infoData? infoData.updateTime:'',
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
