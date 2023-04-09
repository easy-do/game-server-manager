import { list as clientList } from '@/api/clientInfo';
import { GlobalContext } from '@/context';
import useLocale from '@/utils/useLocale';
import {
  Form,
  FormInstance,
  Input,
  Modal,
  Notification,
  Select,
  Spin,
  Typography,
} from '@arco-design/web-react';
import React, { useContext, useEffect, useRef, useState } from 'react';
import locale from './locale';
import RequestSelect from '../RequestSelect/RequestSelect';
import { installeRequest } from '@/api/application';
import { infoRequest, versionList } from '@/api/applicationVersion';
import checkLogin from '@/utils/checkLogin';

function InsallApplicationPage({
  applicationId,
  visible,
  setVisible,
  successCallBack,
}) {
  const t = useLocale(locale);

  const formRef = useRef<FormInstance>();

  const { lang } = useContext(GlobalContext);

  const [loading, setLoading] = useState(false);

  const [configs, setConfigs] = useState([]);

  const [options, setOptions] = useState([]);

  const onchageVersion = (value) => {
    setLoading(true);
    const values = value.split('--');
    infoRequest(values[0]).then((res) => {
      const { success, data } = res.data;
      if (success) {
        const confData = JSON.parse(data.confData);
        const configsCache = [];
        confData.subApps.map((subApp) => {
          const envsCache = [];
          const portBindsCache = [];
          const bindsCache = [];
          const env = subApp.envs;
          if (env) {
            env.map((item, index) => {
              envsCache.push(
                <Form.Item
                  field={
                    'confData.subApps[' +
                    (subApp.key - 1) +
                    '].envs[' +
                    index +
                    ']'
                  }
                >
                  <Form.Item
                    label={item.envName}
                    field={
                      'confData.subApps[' +
                      (subApp.key - 1) +
                      '].envs[' +
                      index +
                      '].' +
                      item.envKey
                    }
                    initialValue={item.envValue}
                    disabled={!item.editable}
                  >
                    <Input />
                  </Form.Item>
                </Form.Item>
              );
            });
          }

          const portBinds = subApp.portBinds;

          if (portBinds) {
            portBinds.map((item, index) => {
              console.log(item)
              portBindsCache.push(
                <Form.Item field={'portBinds[' + index + ']'}>
                  <Form.Item
                    field={
                      'confData.subApps[' +
                      (subApp.key - 1) +
                      '].portBinds[' +
                      index +
                      '].containerPort'
                    }
                    initialValue={item.containerPort}
                    hidden
                  >
                    <Input />
                  </Form.Item>
                  <Form.Item
                    field={
                      'confData.subApps[' +
                      (subApp.key - 1) +
                      '].portBinds[' +
                      index +
                      '].protocol'
                    }
                    initialValue={item.protocol}
                    hidden
                  >
                    <Input />
                  </Form.Item>
                  <Form.Item
                    field={
                      'confData.subApps[' +
                      (subApp.key - 1) +
                      '].portBinds[' +
                      index +
                      '].description'
                    }
                    initialValue={item.description}
                    hidden
                  >
                    <Input />
                  </Form.Item>
                  <Form.Item
                    label={item.description}
                    field={
                      'confData.subApps[' +
                      (subApp.key - 1) +
                      '].portBinds[' +
                      index +
                      '].localPort'
                    }
                    initialValue={item.localPort}
                    hidden={!item.editable}
                  >
                    <Input type={'number'} max={65535} min={80} />
                  </Form.Item>
                </Form.Item>
              );
            });
          }

          const binds = subApp.binds;

          if (binds) {
            binds.map((item, index) => {
              bindsCache.push(
                <Form.Item field={'binds[' + index + ']'}>
                  <Form.Item
                    field={
                      'confData.subApps[' +
                      (subApp.key - 1) +
                      '].binds[' +
                      index +
                      '].containerPath'
                    }
                    initialValue={item.containerPath}
                    hidden
                  >
                    <Input />
                  </Form.Item>
                  <Form.Item
                    field={
                      'confData.subApps[' +
                      (subApp.key - 1) +
                      '].binds[' +
                      index +
                      '].description'
                    }
                    initialValue={item.description}
                    hidden
                  >
                    <Input />
                  </Form.Item>
                  <Form.Item
                    label={item.description}
                    field={
                      'confData.subApps[' +
                      (subApp.key - 1) +
                      '].binds[' +
                      index +
                      '].localPath'
                    }
                    initialValue={item.localPath}
                    disabled={!item.editable}
                  >
                    <Input />
                  </Form.Item>
                </Form.Item>
              );
            });
          }

          configsCache.push(
            <Form.Item field={'confData'}>
              <Form.Item
                field="confData.createNetworks"
                initialValue={confData.createNetworks}
                hidden
              >
                <Input />
              </Form.Item>
              <Form.Item
                field="confData.networks"
                initialValue={confData.networks}
                hidden
              >
                <Input />
              </Form.Item>
              <Form.Item field={'confData.subApps[' + (subApp.key - 1) + ']'}>
                <Typography.Title heading={6}>
                  {subApp.name + t['searchTable.columns.appConfig']}
                </Typography.Title>
                <Form.Item
                  field={'confData.subApps[' + (subApp.key - 1) + '].key'}
                  hidden
                  initialValue={subApp.key - 1}
                >
                  <Input />
                </Form.Item>
                <Form.Item
                  field={'confData.subApps[' + (subApp.key - 1) + '].name'}
                  hidden
                  initialValue={subApp.name}
                >
                  <Input />
                </Form.Item>
                <Form.Item
                  field={'confData.subApps[' + (subApp.key - 1) + '].version'}
                  hidden
                  initialValue={subApp.version}
                >
                  <Input />
                </Form.Item>
                <Form.Item
                  field={'confData.subApps[' + (subApp.key - 1) + '].image'}
                  hidden
                  initialValue={subApp.image}
                >
                  <Input />
                </Form.Item>
                <Form.Item
                  field={
                    'confData.subApps[' + (subApp.key - 1) + '].attachStdin'
                  }
                  hidden
                  initialValue={subApp.attachStdin}
                >
                  <Input />
                </Form.Item>
                <Form.Item
                  field={'confData.subApps[' + (subApp.key - 1) + '].stdinOpen'}
                  hidden
                  initialValue={subApp.stdinOpen}
                >
                  <Input />
                </Form.Item>
                <Form.Item
                  field={'confData.subApps[' + (subApp.key - 1) + '].tty'}
                  hidden
                  initialValue={subApp.tty}
                >
                  <Input />
                </Form.Item>
                <Form.Item
                  field={
                    'confData.subApps[' + (subApp.key - 1) + '].privileged'
                  }
                  hidden
                  initialValue={subApp.privileged}
                >
                  <Input />
                </Form.Item>
                <Form.Item
                  field={'confData.subApps[' + (subApp.key - 1) + '].labels'}
                  hidden
                  initialValue={subApp.labels}
                >
                  <Input />
                </Form.Item>
                <Form.Item
                  field={'confData.subApps[' + (subApp.key - 1) + '].cmd'}
                  hidden
                  initialValue={subApp.cmd}
                >
                  <Input />
                </Form.Item>
                <Form.Item
                  field={
                    'confData.subApps[' + (subApp.key - 1) + '].networkMode'
                  }
                  hidden
                  initialValue={subApp.networkMode}
                >
                  <Input />
                </Form.Item>
                <Form.Item
                  field={'confData.subApps[' + (subApp.key - 1) + '].links'}
                  hidden
                  initialValue={subApp.links}
                >
                  <Input />
                </Form.Item>
                <Form.Item
                  field={
                    'confData.subApps[' + (subApp.key - 1) + '].publishAllPorts'
                  }
                  hidden
                  initialValue={subApp.publishAllPorts}
                >
                  <Input />
                </Form.Item>
                <Form.Item
                  field={'confData.subApps[' + (subApp.key - 1) + '].nanoCPUs'}
                  hidden
                  initialValue={subApp.nanoCPUs}
                >
                  <Input />
                </Form.Item>
                <Form.Item
                  field={'confData.subApps[' + (subApp.key - 1) + '].memory'}
                  hidden
                  initialValue={subApp.memory}
                >
                  <Input />
                </Form.Item>
                <Form.Item
                  field={'confData.subApps[' + (subApp.key - 1) + '].shmSize'}
                  hidden
                  initialValue={subApp.shmSize}
                >
                  <Input />
                </Form.Item>
                <Form.Item
                  field={
                    'confData.subApps[' + (subApp.key - 1) + '].memorySwap'
                  }
                  hidden
                  initialValue={subApp.memorySwap}
                >
                  <Input />
                </Form.Item>
                <Form.Item
                  field={'confData.subApps[' + (subApp.key - 1) + '].envs'}
                >
                  {envsCache}
                </Form.Item>
                <Form.Item
                  field={'confData.subApps[' + (subApp.key - 1) + '].portBinds'}
                >
                  {portBindsCache}
                </Form.Item>
                <Form.Item
                  field={'confData.subApps[' + (subApp.key - 1) + '].binds'}
                >
                  {bindsCache}
                </Form.Item>
              </Form.Item>
            </Form.Item>
          );
        });
        console.log(configsCache);
        setConfigs(configsCache);
      }
      setLoading(false);
    });
  };

  //加载数据
  function fetchData() {
    setOptions([]);
    setConfigs([]);
    if (applicationId !== undefined && visible) {
      if (!checkLogin()) {
        Notification.success({
          title: '',
          content: '未登录,跳转至登录页面',
        });
        setTimeout(() => {
          window.location.href = '/login';
        }, 1000);
        return;
      } else {
        versionList(applicationId).then((res) => {
          const { success, data } = res.data;
          if (success) {
            const newOptions = [];
            data.map((item) => {
              newOptions.push({
                value: item.id + '--' + item.applicationName,
                label: item.applicationName + ':' + item.version,
                key: item.id,
              });
            });
            setOptions(newOptions);
          }
          setLoading(false);
        });
      }
    }
  }

  useEffect(() => {
    fetchData();
  }, [visible, applicationId]);

  //提交
  const handleSubmit = () => {
    formRef.current.validate().then((values: any) => {
      installeRequest({
        ...values,
        applicationId: applicationId,
        version: values.version.split('--')[0],
        confData: JSON.stringify(values.confData),
      }).then((res) => {
        const { success, msg } = res.data;
        if (success) {
          formRef.current.clearFields();
          setConfigs([]);
          Notification.success(msg);
          successCallBack();
        }
        setLoading(false);
      });
    });
  };

  return (
    <Modal
      title={t['searchTable.operations.add']}
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
    >
      <Form
        ref={formRef}
        style={{ width: '95%', marginTop: '6px' }}
        labelCol={{ span: lang === 'en-US' ? 7 : 6 }}
        wrapperCol={{ span: lang === 'en-US' ? 17 : 18 }}
        labelAlign="left"
        layout="vertical"
      >
        <Form.Item
          label={t['searchTable.columns.version']}
          field="version"
          rules={[
            {
              required: true,
              message: t['searchTable.rules.version.required'],
            },
          ]}
        >
          <Select
            showSearch
            placeholder={t['searchForm.version.placeholder']}
            options={options}
            onChange={onchageVersion}
          />
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.client']}
          field="clientId"
          rules={[
            {
              required: true,
              message: t['searchTable.rules.client.required'],
            },
          ]}
        >
          <RequestSelect
            placeholder={t['searchForm.client.placeholder']}
            lableFiled="clientName"
            valueFiled="id"
            request={() => clientList()}
          />
        </Form.Item>
        {configs}
      </Form>
    </Modal>
  );
}

export default InsallApplicationPage;
