import React, { useEffect, useState } from 'react';
import { Descriptions, Modal, Skeleton } from '@arco-design/web-react';
import locale from './locale';
import useLocale from '@/utils/useLocale';
import { infoRequest } from '@/api/roleManager';
import { DataInfoVo } from './constants';

function InfoPage(props: { id: number; visible; setVisible }) {

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
      label: t['searchTable.columns.delFlag'],
      value: loading? <Skeleton text={{ rows: 1, style: { width: '200px' } }} animation /> : infoData ? infoData.delFlag : '',
    },
    {
      label: t['searchTable.columns.remark'],
      value: infoData ? infoData.remark : '',
    },
    {
      label: t['searchTable.columns.updateTime'],
      value: infoData ? infoData.updateTime : '',
    },
    {
      label: t['searchTable.columns.updateBy'],
      value: infoData ? infoData.updateBy : '',
    },
    {
      label: t['searchTable.columns.createTime'],
      value: infoData ? infoData.createTime : '',
    },
    {
      label: t['searchTable.columns.createBy'],
      value: infoData ? infoData.createBy : '',
    },
    {
      label: t['searchTable.columns.status'],
      value: infoData ? infoData.status : '',
    },
    {
      label: t['searchTable.columns.isDefault'],
      value: infoData ? infoData.isDefault : '',
    },
    {
      label: t['searchTable.columns.roleSort'],
      value: infoData ? infoData.roleSort : '',
    },
    {
      label: t['searchTable.columns.roleKey'],
      value: infoData ? infoData.roleKey : '',
    },
    {
      label: t['searchTable.columns.roleName'],
      value: infoData ? infoData.roleName : '',
    },
    {
      label: t['searchTable.columns.roleId'],
      value: infoData ? infoData.roleId : '',
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
        labelStyle={{ paddingRight: 30,paddingLeft: 60 }}
      />
    </Modal>
  );
}

export default InfoPage;
