import React, { useEffect, useState } from 'react';
import {
  Descriptions,
  Modal,
  Skeleton,
  Table,
  Typography,
} from '@arco-design/web-react';
import locale from './locale';
import useLocale from '@/utils/useLocale';
import { infoRequest } from '@/api/applicationVersion';
import { DataInfoVo, statusEnum } from './constants';
import MarkdownEditor from '@/components/MarkdownEditor/MarkdownEditor';

function InfoPage(props: { id: number; visible; setVisible }) {
  const [loading, setLoading] = useState(false);

  const [infoData, setInfoData] = useState<DataInfoVo>({
    id: null,
    applicationId: null,
    applicationName: null,
    version: null,
    status: null,
    description: null,
    heat: null,
    confData: {
      envs: [],
      image: '',
    },
    createTime: null,
    updateTime: null,
    createBy: null,
    updateBy: null,
    delFlag: null,
  });

  const [confDataNode, setConfDataNode] = useState();

  function fetchData() {
    setLoading(true);
    if (props.id !== undefined && props.visible) {
      infoRequest(props.id).then((res) => {
        const { success, data } = res.data;
        if (success) {
          data.confData = JSON.parse(data.confData);
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

  const tablecolumns = [
    {
      title: t['searchTable.columns.envName'],
      dataIndex: 'envName',
      ellipsis: true,
    },
    {
      title: t['searchTable.columns.envKey'],
      dataIndex: 'envKey',
      ellipsis: true,
    },
    {
      title: t['searchTable.columns.envValue'],
      dataIndex: 'envValue',
      ellipsis: true,
    },
    {
      title: t['searchTable.columns.envDescription'],
      dataIndex: 'envDescription',
      ellipsis: true,
    },
  ];

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
      label: t['searchTable.columns.applicationName'],
      value: loading ? (
        <Skeleton text={{ rows: 1, style: { width: '200px' } }} animation />
      ) : infoData ? (
        infoData.applicationName
      ) : (
        ''
      ),
    },
    {
      label: t['searchTable.columns.version'],
      value: loading ? (
        <Skeleton text={{ rows: 1, style: { width: '200px' } }} animation />
      ) : infoData ? (
        infoData.version
      ) : (
        ''
      ),
    },
    {
      label: t['searchTable.columns.image'],
      value: loading ? (
        <Skeleton text={{ rows: 1, style: { width: '200px' } }} animation />
      ) : infoData ? (
        infoData.confData.image
      ) : (
        ''
      ),
    },
    {
      label: t['searchTable.columns.status'],
      value: loading ? (
        <Skeleton text={{ rows: 1, style: { width: '200px' } }} animation />
      ) : infoData ? (
        statusEnum[infoData.status]
      ) : (
        ''
      ),
    },
    {
      label: t['searchTable.columns.heat'],
      value: loading ? (
        <Skeleton text={{ rows: 1, style: { width: '200px' } }} animation />
      ) : infoData ? (
        infoData.heat
      ) : (
        ''
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
      label: t['searchTable.columns.updateTime'],
      value: loading ? (
        <Skeleton text={{ rows: 1, style: { width: '200px' } }} animation />
      ) : infoData ? (
        infoData.updateTime
      ) : (
        ''
      ),
    },
  ];

  return (
    <Modal
      style={{ width: '100%', minHeight: '70%' }}
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
      <Typography.Title heading={6}>
        {t['searchTable.columns.basicInfo']}
      </Typography.Title>
      <Descriptions
        column={1}
        data={data}
        style={{ marginBottom: 20 }}
        labelStyle={{ paddingRight: 36 }}
      />
      <Typography.Title heading={6}>
        {t['searchTable.columns.EnvInfo']}
      </Typography.Title>
      <Table
        rowKey="key"
        columns={tablecolumns}
        data={infoData.confData.envs}
        pagination={false}
      />
      <Typography.Title heading={6}>
        {t['searchTable.columns.description']}
      </Typography.Title>
      <MarkdownEditor
        value={infoData ? infoData.description : ''}
        previewOnly
      />
    </Modal>
  );
}

export default InfoPage;
