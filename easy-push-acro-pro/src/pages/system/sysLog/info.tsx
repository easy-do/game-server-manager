import React, { useEffect, useState } from 'react';
import { Descriptions, Modal, Skeleton } from '@arco-design/web-react';
import locale from './locale';
import useLocale from '@/utils/useLocale';
import { DataInfoVo } from './constants';


function InfoPage(props: {visible,setVisible,data}) {

    const t = useLocale(locale);
  
    const [loading,setLoading] = useState(false)

    const [infoData, setInfoData] = useState<DataInfoVo>();

    const [fieldValue,setFieldValue] = useState<any>();
    
    function fetchData() {
      if(props.data){
        setInfoData(props.data)
        setFieldValue(JSON.parse(props.data.data))
        setLoading(false)
      }
    }

    useEffect(() => {
        fetchData();
      }, [ props.visible ]);

 

  const data = [
    {
      label: t['searchTable.columns.id'],
      value: loading? <Skeleton text={{ rows: 1, style: { width: '200px' } }} animation /> : infoData? infoData.id:'',
    },
    {
      label: t['searchTable.columns.operationTime'],
      value: loading? <Skeleton text={{ rows: 1, style: { width: '200px' } }} animation /> : fieldValue? fieldValue.operationTime:'',
    },
    {
      label: t['searchTable.columns.sourceIp'],
      value: loading? <Skeleton text={{ rows: 1, style: { width: '200px' } }} animation /> : fieldValue? fieldValue.sourceIp:'',
    },
    {
      label: t['searchTable.columns.targetIp'],
      value: loading? <Skeleton text={{ rows: 1, style: { width: '200px' } }} animation /> : fieldValue? fieldValue.targetIp:'',
    },
    {
      label: t['searchTable.columns.operatorName'],
      value: loading? <Skeleton text={{ rows: 1, style: { width: '200px' } }} animation /> : fieldValue? fieldValue.operatorName:'',
    },
    {
      label: t['searchTable.columns.systemName'],
      value: loading? <Skeleton text={{ rows: 1, style: { width: '200px' } }} animation /> : fieldValue? fieldValue.systemName:'',
    },
    {
      label: t['searchTable.columns.moduleName'],
      value: loading? <Skeleton text={{ rows: 1, style: { width: '200px' } }} animation /> : fieldValue? fieldValue.moduleName:'',
    },
    {
      label: t['searchTable.columns.path'],
      value: loading? <Skeleton text={{ rows: 1, style: { width: '200px' } }} animation /> : fieldValue? fieldValue.path:'',
    },
    {
      label: t['searchTable.columns.actionType'],
      value: loading? <Skeleton text={{ rows: 1, style: { width: '200px' } }} animation /> : fieldValue? fieldValue.actionType:'',
    },
    {
      label: t['searchTable.columns.description'],
      value: loading? <Skeleton text={{ rows: 1, style: { width: '200px' } }} animation /> : fieldValue? fieldValue.description:'',
    },
    {
      label: t['searchTable.columns.params'],
      value: loading? <Skeleton text={{ rows: 1, style: { width: '200px' } }} animation /> : fieldValue? fieldValue.params:'',
    },
    {
      label: t['searchTable.columns.operationStatus'],
      value: loading? <Skeleton text={{ rows: 1, style: { width: '200px' } }} animation /> : fieldValue? fieldValue.operationStatus:'',
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
