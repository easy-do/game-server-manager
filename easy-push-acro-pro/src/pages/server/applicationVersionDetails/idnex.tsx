import React, { useEffect, useState } from 'react';

import locale from './locale';
import useLocale from '@/utils/useLocale';
import { infoRequest } from '@/api/application';
import { DataInfoVo, statusEnum } from '../applicationVersion/constants';
import MarkdownEditor from '@/components/MarkdownEditor/MarkdownEditor';
import Row from '@arco-design/web-react/es/Grid/row';
import Col from '@arco-design/web-react/es/Grid/col';
import { Card, Link } from '@arco-design/web-react';

function ApplicationVersionDetailsPage(props: { id: number}) {
  const [loading, setLoading] = useState(false);

  const [infoData, setInfoData] = useState<DataInfoVo>({
    id: null,
    applicationId: null,
    applicationName: null,
    version: null,
    status: null,
    description: null,
    heat: null,
    confData: null,
    createTime: null,
    updateTime: null,
    createBy: null,
    updateBy: null,
    delFlag: null,
  });

  function fetchData() {
    setLoading(true);
    if (props.id !== undefined) {
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
  }, [props.id]);

  const t = useLocale(locale);


  return (
    <div style={{ width: '100%' }}>
      <Row
      >
        <Col span={24}>
        <Card style={{ width: '100%' }}
        hoverable
        title='基础信息'
      >
        123
      </Card>
        </Col>
        <Col span={24}>
        <Card style={{ width: '100%' }}
        hoverable
        title='详情'
      >
        ByteDance s core product, Toutiao , is a content platform in China and around
        the world. Toutiao started out as a news recommendation engine and gradually evolved into a
        platform delivering content in various formats.
      </Card>
        </Col>
      </Row>
    </div>
  );
}

export default ApplicationVersionDetailsPage;
