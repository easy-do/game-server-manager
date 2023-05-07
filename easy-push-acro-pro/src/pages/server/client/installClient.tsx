import { getInstallCmd, infoRequest, onlineInstall } from '@/api/clientInfo';
import useLocale from '@/utils/useLocale';
import { Button, Modal, Notification, Spin } from '@arco-design/web-react';
import Paragraph from '@arco-design/web-react/es/Typography/paragraph';
import React, { useEffect, useState } from 'react';
import locale from './locale';

function InstallClientPage({ id, visible, setVisible, successCallBack }) {
  const t = useLocale(locale);

  const [loading, setLoading] = useState(false);

  const [installCmd, setInstallCmd] = useState();

  const [dataInfo, setDataInfo] = useState({
    serverId: null,
    status: '',
  });

  const [dockerInstall, setDockerInstall] = useState('');
  
  const handleSubmit = () => {
    onlineInstall(id).then((res) => {
      const { success, msg } = res.data;
      if (success) {
        Notification.success({ content: msg, duration: 300 });
        successCallBack();
      }
    });
  };

  //加载数据
  function fetchData() {
    if (id !== undefined && visible) {
      setLoading(true);
      setDockerInstall('docker run -dit -e CLIENT_ID='+id+' --privileged=true --pid=host -v /:/host -v /var/run/docker.sock:/var/run/docker.sock -v $(which docker):/usr/bin/docker --name manager-client registry.cn-hangzhou.aliyuncs.com/gebilaoyu/game-server-manager:client-1.0.0')
      getInstallCmd(id).then((res) => {
        const { success, data } = res.data;
        if (success) {
          setInstallCmd(data);
        }
      });
      infoRequest(id).then((res) => {
        const { success, data } = res.data;
        if (success) {
          setDataInfo(data);
        }
        setLoading(false);
      });
    }
  }

  useEffect(() => {
    fetchData();
  }, [id,visible]);

  return (
    <Modal
      title={t['searchTable.operations.install']}
      visible={visible}
      onOk={() => {
        handleSubmit();
      }}
      onCancel={() => {
        setVisible(false);
      }}
      autoFocus={false}
      focusLock={true}
      maskClosable={false}
      footer={
        <>
          {dataInfo && dataInfo.serverId && dataInfo.status === '未部署' ? (
            <Button
              type="primary"
              loading={loading}
              onClick={() => handleSubmit()}
            >
              在线安装
            </Button>
          ) : null}
          {dataInfo && dataInfo.serverId && dataInfo.status !== '未部署' ? (
            <Button
              type="primary"
              loading={loading}
              onClick={() => handleSubmit()}
            >
              重新在线安装
            </Button>
          ) : null}
          <Button
            type="primary"
            loading={loading}
            onClick={() => setVisible(false)}
          >
            关闭窗口
          </Button>
        </>
      }
    >
      <Spin tip="loading Data..." loading={loading}>
        <Paragraph copyable={{ text: dockerInstall }} code>
        <strong>docker安装(暂不支持执行脚本)</strong>:
          <br />
          {dockerInstall}
        </Paragraph>
        <Paragraph copyable={{ text: installCmd}} code>
          <strong>宿主机安装(可以跑脚本但不不推荐,需要jdk-17环境)</strong>:
          <br />
          {installCmd}
        </Paragraph>
      </Spin>
    </Modal>
  );
}

export default InstallClientPage;
