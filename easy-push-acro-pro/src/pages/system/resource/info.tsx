import React, { useEffect, useState } from 'react';
import { Descriptions, Modal, Skeleton } from '@arco-design/web-react';
import locale from './locale';
import useLocale from '@/utils/useLocale';
import { infoRequest } from '@/api/resource';
import { DataInfoVo } from './constants';
import { dictLabelEnum } from '@/utils/dictDataUtils';


function InfoPage(props: {id:number,visible,setVisible}) {

  const statusEnum = dictLabelEnum('status_select','string')
  const isNoSelecEnum = dictLabelEnum('is_no_select','string')
  const embeddedTypeEnum = dictLabelEnum('embedded_type','string')
  const iconTypeEnum = dictLabelEnum('icon_type','string')
  const routeModeEnum = dictLabelEnum('route_mode','string')
  
  
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
      label: t['searchTable.columns.resourceCode'],
      value: loading? <Skeleton text={{ rows: 1, style: { width: '200px' } }} animation /> : infoData? infoData.resourceCode:'',
    },
    {
      label: t['searchTable.columns.resourcePath'],
      value: loading? <Skeleton text={{ rows: 1, style: { width: '200px' } }} animation /> : infoData? infoData.resourcePath:'',
    },
    {
      label: t['searchTable.columns.resourceType'],
      value: loading? <Skeleton text={{ rows: 1, style: { width: '200px' } }} animation /> : infoData? infoData.resourceType:'',
    },
    {
      label: t['searchTable.columns.icon'],
      value: loading? <Skeleton text={{ rows: 1, style: { width: '200px' } }} animation /> : infoData? infoData.icon:'',
    },
    {
      label: t['searchTable.columns.iconType'],
      value: loading? <Skeleton text={{ rows: 1, style: { width: '200px' } }} animation /> : infoData? iconTypeEnum[infoData.iconType]:'',
    },
    {
      label: t['searchTable.columns.status'],
      value: loading? <Skeleton text={{ rows: 1, style: { width: '200px' } }} animation /> : infoData? statusEnum[infoData.status]:'',
    },
    {
      label: t['searchTable.columns.orderNumber'],
      value: loading? <Skeleton text={{ rows: 1, style: { width: '200px' } }} animation /> : infoData? infoData.orderNumber:'',
    },
    {
      label: t['searchTable.columns.authFlag'],
      value: loading? <Skeleton text={{ rows: 1, style: { width: '200px' } }} animation /> : infoData? isNoSelecEnum[infoData.authFlag]:'',
    },
    {
      label: t['searchTable.columns.url'],
      value: loading? <Skeleton text={{ rows: 1, style: { width: '200px' } }} animation /> : infoData? infoData.url:'',
    },
    {
      label: t['searchTable.columns.permissions'],
      value: loading? <Skeleton text={{ rows: 1, style: { width: '200px' } }} animation /> : infoData? infoData.permissions:'',
    },
    {
      label: t['searchTable.columns.routeMode'],
      value: loading? <Skeleton text={{ rows: 1, style: { width: '200px' } }} animation /> : infoData? routeModeEnum[infoData.routeMode]:'',
    },
    {
      label: t['searchTable.columns.embeddedType'],
      value: loading? <Skeleton text={{ rows: 1, style: { width: '200px' } }} animation /> : infoData? embeddedTypeEnum[infoData.embeddedType]:'',
    },
    {
      label: t['searchTable.columns.subRoutes'],
      value: loading? <Skeleton text={{ rows: 1, style: { width: '200px' } }} animation /> : infoData? infoData.subRoutes:'',
    },
    {
      label: t['searchTable.columns.resourceDesc'],
      value: loading? <Skeleton text={{ rows: 1, style: { width: '200px' } }} animation /> : infoData? infoData.resourceDesc:'',
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
