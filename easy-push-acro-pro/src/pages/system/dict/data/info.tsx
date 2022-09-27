import React, { useEffect, useState } from 'react';
import { Descriptions, Modal, Skeleton } from '@arco-design/web-react';
import locale from './locale';
import useLocale from '@/utils/useLocale';
import { infoRequest } from '@/api/dictData';
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
      label: t['searchTable.columns.id'],
      value: loading? <Skeleton text={{ rows: 1, style: { width: '200px' } }} animation /> : infoData? infoData.id:'',
    },
    {
      label: t['searchTable.columns.dictLabel'],
      value: loading? <Skeleton text={{ rows: 1, style: { width: '200px' } }} animation /> : infoData? infoData.dictLabel:'',
    },
    {
      label: t['searchTable.columns.dictKey'],
      value: loading? <Skeleton text={{ rows: 1, style: { width: '200px' } }} animation /> : infoData? infoData.dictKey:'',
    },
    {
      label: t['searchTable.columns.dictValue'],
      value: loading? <Skeleton text={{ rows: 1, style: { width: '200px' } }} animation /> : infoData? infoData.dictValue:'',
    },
    {
      label: t['searchTable.columns.dictValueType'],
      value: loading? <Skeleton text={{ rows: 1, style: { width: '200px' } }} animation /> : infoData? infoData.dictValueType:'',
    },
    {
      label: t['searchTable.columns.isDefault'],
      value: loading? <Skeleton text={{ rows: 1, style: { width: '200px' } }} animation /> : infoData? infoData.isDefault:'',
    },
    {
      label: t['searchTable.columns.status'],
      value: loading? <Skeleton text={{ rows: 1, style: { width: '200px' } }} animation /> : infoData? infoData.status:'',
    },
    {
      label: t['searchTable.columns.dictSort'],
      value: loading? <Skeleton text={{ rows: 1, style: { width: '200px' } }} animation /> : infoData? infoData.dictSort:'',
    },
    {
      label: t['searchTable.columns.createTime'],
      value: loading? <Skeleton text={{ rows: 1, style: { width: '200px' } }} animation /> : infoData? infoData.createTime:'',
    },
    {
      label: t['searchTable.columns.updateTime'],
      value: loading? <Skeleton text={{ rows: 1, style: { width: '200px' } }} animation /> : infoData? infoData.updateTime:'',
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
