import React, { useEffect, useState } from 'react';

import locale from './locale';
import useLocale from '@/utils/useLocale';
import { infoRequest } from '@/api/application';
import MarkdownEditor from '@/components/MarkdownEditor/MarkdownEditor';
import Row from '@arco-design/web-react/es/Grid/row';
import Col from '@arco-design/web-react/es/Grid/col';
import { Button, Card, Descriptions, Divider, Modal, Select, Space } from '@arco-design/web-react';
import { versionList } from '@/api/applicationVersion';
import { Image } from '@arco-design/web-react';
import InsallApplicationPage from '@/components/InstallApplication/installApplication';

function ApplicationVersionDetailsPage() {
  const [loading, setLoading] = useState(false);

  const [infoData, setInfoData] = useState<any>({
    applicationName: null,
    description: null,
    heat: 0,
    updateTime: null,
  });


  const [selectOption, setSelectOption] = useState<any>([]);

  const [versionListData, setVersionListData] = useState<any>([]);

  const [currentVersionData, setCurrentVersionData] = useState<any>({
    description: null,
  });
  
  const versionChage = (versionIndex)=>{
    setCurrentVersionData(versionListData[versionIndex]);
  }


  const [isInstallApplication, setIsInstallApplication] = useState(false);

    //安装应用
    function installApplication() {
      setIsInstallApplication(true);
    }

    function installApplicationSuccess() {
      setIsInstallApplication(false);
      setJumpLogPageModal(true);
    }

  const [jumpLogPageModal, setJumpLogPageModal] = useState(false);

  const jumpLogPage = ()=>{
    window.location.href="/server/aolicationInstallLog"
  }


  const search = window.location.search;
  const param = new URLSearchParams(search);
  const id = param.get('id');

  function fetchData() {
    setLoading(true);
    if (id) {
      infoRequest(id).then((res) => {
        const { success, data } = res.data;
        if (success) {
          setInfoData(data);
          versionList(data.id).then((res) => {
            const { success, data } = res.data;
            if (success) {
              setVersionListData(data);
              if (data.length > 0) {
                setCurrentVersionData(data[0]);
                const optionCache = [];
                data.map((item, index) => (
                  optionCache.push({
                    label:item.version,
                    value:index,
                  })
                ))
                setSelectOption(optionCache);
              }
            }
          });
        }
        setLoading(false);
      });
    }
  }

  useEffect(() => {
    fetchData();
  }, []);
  const t = useLocale(locale);

  const data = [
    {
      label: t['searchTable.columns.applicationName'],
      value: infoData.applicationName,
    },
    {
      label: t['searchTable.columns.description'],
      value: infoData.description,
    },
    {
      label: t['searchTable.columns.heat'],
      value: infoData.heat,
    },
    {
      label: t['searchTable.columns.updateTime'],
      value: infoData.updateTime,
    },

    {
      label: t['searchTable.columns.version'],
      value: (
        <Select value={currentVersionData.version} onChange={versionChage} options={selectOption}/>
      ),
    },
    {
      label: t['searchTable.columns.operations'],
      value: <Button type='primary' onClick={installApplication}>{t['searchTable.columns.operations.install']}</Button>,
    },
  ];

  return (
    <div style={{ width: '100%' }}>
      <Row>
        <Col span={24}>
          <Card  title={t['searchTable.columns.basicInfo']} hoverable bordered>
            <Space size={'large'} split={<Divider type='vertical' />}>
            <Image width={100} src={infoData.icon} alt="lamp" />
            <Descriptions
              column={1}
              data={data}
              style={{ marginBottom: 20 }}
              labelStyle={{ paddingRight: 36 }}
            />
            </Space>
          </Card>
        </Col>
        <Col span={24}>
          <Card  title={t['searchTable.columns.description']}>
            <MarkdownEditor value={currentVersionData.description} previewOnly />
          </Card>
        </Col>
      </Row>
      <InsallApplicationPage
        applicationId={id}
        version={currentVersionData.id}
        visible={isInstallApplication}
        setVisible={setIsInstallApplication}
        successCallBack={installApplicationSuccess} />
        <Modal
        visible={jumpLogPageModal}
        onOk={() => jumpLogPage()}
        onCancel={() => setJumpLogPageModal(false)}
        autoFocus={false}
        focusLock={true}
      >
        <p>
          {t['jumpLogContent']}
        </p>
      </Modal>
    </div>
  );
}

export default ApplicationVersionDetailsPage;
