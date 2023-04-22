import { list as clientList } from '@/api/clientInfo';
import { GlobalContext } from '@/context';
import useLocale from '@/utils/useLocale';
import {
  Button,
  Divider,
  Drawer,
  Form,
  FormInstance,
  Input,
  Modal,
  Notification,
  Select,
  Space,
  Spin,
  Typography,
} from '@arco-design/web-react';
import React, { useContext, useEffect, useRef, useState } from 'react';
import locale from './locale';
import RequestSelect from '../RequestSelect/RequestSelect';
import { installeRequest } from '@/api/application';
import { infoRequest, versionList } from '@/api/applicationVersion';
import checkLogin from '@/utils/checkLogin';
import { IconDelete } from '@arco-design/web-react/icon';

function InsallApplicationPage({
  applicationId,
  visible,
  setVisible,
  successCallBack,
  version,
}) {
  const t = useLocale(locale);

  const formRef = useRef<FormInstance>();

  const { lang } = useContext(GlobalContext);

  const [loading, setLoading] = useState(false);

  const [configs, setConfigs] = useState([]);

  const onchageVersion = (value) => {
    setLoading(true);
    console.log(value);
    infoRequest(value).then((res) => {
      const { success, data } = res.data;
      if (success) {
        const confData = JSON.parse(data.confData);
        const configsCache = [];
        confData.subApps.map((subApp) => {
          const envs = subApp.envs;
          const portBinds = subApp.portBinds;
          const binds = subApp.binds;

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
                <Typography.Title heading={6}>
                  {t['searchTable.columns.EnvInfo']}
                </Typography.Title>
                <Form.List
                  field={'confData.subApps[' + (subApp.key - 1) + '].envs'}
                  initialValue={envs}
                >
                  {(fields, { add, remove, move }) => {
                    return (
                      <div>
                        {fields.map((item, index) => {
                          return (
                            <div key={item.key}>
                              <Form.Item
                                label={
                                  envs && envs.length > index
                                    ? envs[index].envName
                                    : t['searchTable.columns.EnvInfo'] +
                                      (index + 1)
                                }
                              >
                                <Space
                                  style={{
                                    width: '100%',
                                    justifyContent: 'space-between',
                                  }}
                                  split={<Divider type="vertical" />}
                                  align="baseline"
                                  size="large"
                                >
                                  <Form.Item
                                    label={t['searchTable.columns.envKey']}
                                    field={item.field + '.envKey'}
                                    rules={[
                                      {
                                        required: true,
                                        message:
                                          t[
                                            'searchTable.rules.envKey.required'
                                          ],
                                      },
                                    ]}
                                    disabled={
                                      envs && envs.length > index
                                        ? envs[index].editable
                                        : false
                                    }
                                  >
                                    <Input />
                                  </Form.Item>
                                  <Form.Item
                                    label={t['searchTable.columns.envValue']}
                                    field={item.field + '.envValue'}
                                    rules={[
                                      {
                                        required: true,
                                        message:
                                          t[
                                            'searchTable.rules.envValue.required'
                                          ],
                                      },
                                    ]}
                                  >
                                    <Input />
                                  </Form.Item>
                                  <Form.Item>
                                    <Button
                                      icon={<IconDelete />}
                                      disabled={
                                        envs && envs.length > index
                                          ? envs[index].editable
                                          : false
                                      }
                                      shape="circle"
                                      status="danger"
                                      onClick={() => remove(index)}
                                    ></Button>
                                  </Form.Item>
                                </Space>
                              </Form.Item>
                            </div>
                          );
                        })}
                        <Form.Item>
                          <Button
                            type="dashed"
                            onClick={() => {
                              add();
                            }}
                          >
                            {t['searchTable.columns.addEnv']}
                          </Button>
                        </Form.Item>
                      </div>
                    );
                  }}
                </Form.List>
                <Divider orientation="left"></Divider>
                <Typography.Title heading={6}>
                  {t['searchTable.columns.port']}
                </Typography.Title>
                <Form.List
                  initialValue={portBinds}
                  field={'confData.subApps[' + (subApp.key - 1) + '].portBinds'}
                >
                  {(fields, { add, remove, move }) => {
                    return (
                      <div>
                        {fields.map((item, index) => {
                          return (
                            <div key={item.key}>
                              <Form.Item
                                label={
                                  portBinds && portBinds.length > index
                                    ? portBinds[index].description
                                    : t['searchTable.columns.port'] +
                                      (index + 1)
                                }
                              >
                                <Space
                                  style={{
                                    width: '100%',
                                    justifyContent: 'space-between',
                                  }}
                                  split={<Divider type="vertical" />}
                                  align="center"
                                  size="small"
                                >
                                  <Form.Item
                                    label={
                                      t['searchTable.columns.containerPort']
                                    }
                                    field={item.field + '.containerPort'}
                                    disabled={
                                      portBinds && portBinds.length > index
                                        ? portBinds[index].editable
                                        : false
                                    }
                                  >
                                    <Input />
                                  </Form.Item>

                                  <Form.Item
                                    label={t['searchTable.columns.localPort']}
                                    field={item.field + '.localPort'}
                                    rules={[
                                      {
                                        required: true,
                                        message:
                                          t[
                                            'searchTable.rules.localPort.required'
                                          ],
                                      },
                                    ]}
                                  >
                                    <Input
                                      type={'number'}
                                      max={65535}
                                      min={80}
                                    />
                                  </Form.Item>
                                  <Form.Item
                                    label={t['searchTable.columns.protocol']}
                                    field={item.field + '.protocol'}
                                    disabled={
                                      portBinds && portBinds.length > index
                                        ? portBinds[index].editable
                                        : false
                                    }
                                  >
                                    <Select
                                      options={[
                                        { label: 'tcp', value: 'tcp' },
                                        { label: 'udp', value: 'udp' },
                                      ]}
                                    />
                                  </Form.Item>
                                  <Form.Item>
                                    <Button
                                      icon={<IconDelete />}
                                      disabled={
                                        portBinds && portBinds.length > index
                                          ? portBinds[index].editable
                                          : false
                                      }
                                      shape="circle"
                                      status="danger"
                                      onClick={() => remove(index)}
                                    ></Button>
                                  </Form.Item>
                                </Space>
                              </Form.Item>
                            </div>
                          );
                        })}
                        <Form.Item>
                          <Button
                            type="dashed"
                            onClick={() => {
                              add();
                            }}
                          >
                            {t['searchTable.columns.addPort']}
                          </Button>
                        </Form.Item>
                      </div>
                    );
                  }}
                </Form.List>
                <Divider orientation="left"></Divider>
                <Typography.Title heading={6}>
                  {t['searchTable.columns.binds']}
                </Typography.Title>
                <Form.List
                  field={'confData.subApps[' + (subApp.key - 1) + '].binds'}
                  initialValue={binds}
                >
                  {(fields, { add, remove, move }) => {
                    return (
                      <div>
                        {fields.map((item, index) => {
                          return (
                            <div key={item.key}>
                              <Form.Item
                                label={
                                  binds && binds.length > index
                                    ? binds[index].description
                                    : t['searchTable.columns.binds'] +
                                      (index + 1)
                                }
                              >
                                <Space
                                  style={{
                                    width: '100%',
                                    justifyContent: 'space-between',
                                  }}
                                  split={<Divider type="vertical" />}
                                  align="baseline"
                                  size="large"
                                >
                                  <Form.Item
                                    label={
                                      t['searchTable.columns.containerPath']
                                    }
                                    disabled={
                                      binds && binds.length > index
                                        ? binds[index].editable
                                        : false
                                    }
                                    field={item.field + '.containerPath'}
                                    rules={[
                                      {
                                        required: true,
                                        message:
                                          t[
                                            'searchTable.rules.containerPath.required'
                                          ],
                                      },
                                    ]}
                                  >
                                    <Input />
                                  </Form.Item>

                                  <Form.Item
                                    label={t['searchTable.columns.localPath']}
                                    field={item.field + '.localPath'}
                                    rules={[
                                      {
                                        required: true,
                                        message:
                                          t[
                                            'searchTable.rules.localPath.required'
                                          ],
                                      },
                                    ]}
                                  >
                                    <Input />
                                  </Form.Item>
                                  <Form.Item>
                                    <Button
                                      disabled={
                                        binds && binds.length > index
                                          ? binds[index].editable
                                          : false
                                      }
                                      icon={<IconDelete />}
                                      shape="circle"
                                      status="danger"
                                      onClick={() => remove(index)}
                                    ></Button>
                                  </Form.Item>
                                </Space>
                              </Form.Item>
                            </div>
                          );
                        })}
                        <Form.Item>
                          <Button
                            type="dashed"
                            onClick={() => {
                              add();
                            }}
                          >
                            {t['searchTable.columns.addBinds']}
                          </Button>
                        </Form.Item>
                      </div>
                    );
                  }}
                </Form.List>
                <Divider orientation="left"></Divider>
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
        onchageVersion(version);
      }
    }
  }

  useEffect(() => {
    console.log(visible);
    console.log(applicationId);
    if (visible) {
      fetchData();
    }
  }, [visible]);

  //提交
  const handleSubmit = () => {
    formRef.current.validate().then((values: any) => {
      console.log('data', {
        ...values,
        applicationId: applicationId,
        confData: JSON.stringify(values.confData),
      });
      installeRequest({
        ...values,
        applicationId: applicationId,
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
    <Drawer
      width={'50%'}
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
          hidden
          initialValue={version}
        >
          <Input />
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
    </Drawer>
  );
}

export default InsallApplicationPage;
