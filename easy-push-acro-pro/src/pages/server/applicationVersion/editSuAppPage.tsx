import React, { useEffect, useRef, useContext, useState } from 'react';
import {
  Typography,
  Modal,
  FormInstance,
  Form,
  Input,
  Button,
  Space,
  Divider,
  Select,
  Checkbox,
} from '@arco-design/web-react';

import useLocale from '@/utils/useLocale';

import { GlobalContext } from '@/context';
import locale from './locale';
import { IconDelete } from '@arco-design/web-react/icon';
import Textarea from '@arco-design/web-react/es/Input/textarea';

function EditSubappPage({ subApp, visible, setVisible, editSubappsCallback }) {
  const formRef1 = useRef<FormInstance>();

  const { lang } = useContext(GlobalContext);

  const t = useLocale(locale);

  const [networkMode, setNetworkMode] = useState('bridge');
  const [publishAllPorts, setPublishAllPorts] = useState(false);

  const networkModeOnchage = (value) => {
    setNetworkMode(value);
  };

  const publishAllPortsOnchage = (value) => {
    setPublishAllPorts(value);
  };

  function handleSubmit() {
    formRef1.current.validate().then((values) => {
      editSubappsCallback(values);
      setVisible(false);
    });
  }

  useEffect(() => {
    if (formRef1.current) {
      console.info(subApp);
      formRef1.current.clearFields();
      formRef1.current.setFieldsValue(subApp);
    }
  }, [subApp]);

  return (
    <Modal
      style={{ width: '100%', minHeight: '70%' }}
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
      <Typography.Title heading={6}>
        {t['searchTable.columns.basicInfo']}
      </Typography.Title>
      <Form
        ref={formRef1}
        style={{ width: '95%', marginTop: '6px' }}
        labelCol={{ span: lang === 'en-US' ? 6 : 5 }}
        wrapperCol={{ span: lang === 'en-US' ? 18 : 19 }}
        labelAlign="left"
        initialValues={subApp}
      >
        <Form.Item field="key" hidden>
          <Input />
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.name']}
          field="name"
          rules={[
            { required: true, message: t['searchTable.rules.name.required'] },
          ]}
        >
          <Input placeholder={t['searchForm.name.placeholder']} allowClear />
        </Form.Item>
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
          <Input placeholder={t['searchForm.version.placeholder']} allowClear />
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.image']}
          field="image"
          rules={[
            { required: true, message: t['searchTable.rules.image.required'] },
          ]}
        >
          <Input placeholder={t['searchForm.image.placeholder']} allowClear />
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.attachStdin']}
          field="attachStdin"
        >
          <Checkbox checked={subApp ? subApp.attachStdin : false} />
        </Form.Item>
        <Form.Item label={t['searchTable.columns.stdinOpen']} field="stdinOpen">
          <Checkbox checked={subApp ? subApp.stdinOpen : false} />
        </Form.Item>
        <Form.Item label={t['searchTable.columns.tty']} field="tty">
          <Checkbox checked={subApp ? subApp.tty : false} />
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.privileged']}
          field="privileged"
        >
          <Checkbox checked={subApp ? subApp.privileged : false} />
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.publishAllPorts']}
          field="publishAllPorts"
        >
          <Checkbox
            checked={subApp ? subApp.publishAllPorts : false}
            onChange={publishAllPortsOnchage}
          />
        </Form.Item>
        <Form.Item label={t['searchTable.columns.nanoCPUs']} field="nanoCPUs">
          <Input
            type="number"
            placeholder={t['searchForm.nanoCPUs.placeholder']}
            allowClear
          />
        </Form.Item>
        <Form.Item label={t['searchTable.columns.memory']} field="memory">
          <Input
            type="number"
            placeholder={t['searchForm.memory.placeholder']}
            allowClear
          />
        </Form.Item>
        <Form.Item label={t['searchTable.columns.shmSize']} field="shmSize">
          <Input
            type="number"
            placeholder={t['searchForm.shmSize.placeholder']}
            allowClear
          />
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.memorySwap']}
          field="memorySwap"
        >
          <Input
            type="number"
            placeholder={t['searchForm.memorySwap.placeholder']}
            allowClear
          />
        </Form.Item>
        <Form.Item label={t['searchTable.columns.cmd']} field="cmd">
          <Textarea placeholder={t['searchForm.cmd.placeholder']} allowClear />
        </Form.Item>
        <Form.Item label={t['searchTable.columns.labels']} field="labels">
          <Textarea
            placeholder={t['searchForm.labels.placeholder']}
            allowClear
          />
        </Form.Item>
        <Form.Item label={t['searchTable.columns.links']} field="links">
          <Textarea
            placeholder={t['searchForm.links.placeholder']}
            allowClear
          />
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.networkMode']}
          field="networkMode"
          rules={[
            {
              required: true,
              message: t['searchTable.rules.networkMode.required'],
            },
          ]}
        >
          <Select
            defaultValue={'bridge'}
            onChange={networkModeOnchage}
            allowCreate
            options={[
              { label: 'host', value: 'host' },
              { label: 'none', value: 'none' },
              { label: 'bridge', value: 'bridge' },
            ]}
          />
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.restartPolicy']}
          field="restartPolicy"
          initialValue={'no'}
          rules={[
            {
              required: true,
              message: t['searchTable.rules.restartPolicy.required'],
            },
          ]}
        >
          <Select
            defaultValue={'no'}
            allowCreate
            options={[
              { label: 'no', value: 'no' },
              { label: 'always', value: 'always' },
              { label: 'unless-stopped', value: 'unless-stopped' },
              { label: 'on-failure', value: 'on-failure' },
            ]}
          />
        </Form.Item>
        <Typography.Title heading={6}>
          {t['searchTable.columns.EnvInfo']}
        </Typography.Title>
        <Form.List field="confData.envs">
          {(fields, { add, remove, move }) => {
            return (
              <div>
                {fields.map((item, index) => {
                  return (
                    <div key={item.key}>
                      <Form.Item
                        label={t['searchTable.columns.EnvInfo'] + (index + 1)}
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
                            label={t['searchTable.columns.envName']}
                            field={item.field + '.envName'}
                            rules={[
                              {
                                required: true,
                                message:
                                  t['searchTable.rules.envName.required'],
                              },
                            ]}
                          >
                            <Input />
                          </Form.Item>

                          <Form.Item
                            label={t['searchTable.columns.envKey']}
                            field={item.field + '.envKey'}
                            rules={[
                              {
                                required: true,
                                message: t['searchTable.rules.envKey.required'],
                              },
                            ]}
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
                                  t['searchTable.rules.envValue.required'],
                              },
                            ]}
                          >
                            <Input />
                          </Form.Item>
                          <Form.Item
                            label={t['searchTable.columns.editable']}
                            field={item.field + '.editable'}
                            initialValue={true}
                          >
                            <Checkbox defaultChecked />
                          </Form.Item>
                          <Form.Item>
                            <Button
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
                    // style={{ width: '100%' }}
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
        {networkMode == 'none' ? null : (
          <>
            <Typography.Title heading={6}>
              {t['searchTable.columns.port']}
            </Typography.Title>
            <Form.List field="confData.portBinds">
              {(fields, { add, remove, move }) => {
                return (
                  <div>
                    {fields.map((item, index) => {
                      return (
                        <div key={item.key}>
                          <Form.Item
                            label={t['searchTable.columns.port'] + (index + 1)}
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
                                label={t['searchTable.columns.envDescription']}
                                initialValue={
                                  t['searchTable.columns.envDescription']
                                }
                                field={item.field + '.description'}
                                rules={[
                                  {
                                    required: true,
                                    message:
                                      t[
                                        'searchTable.rules.envDescription.required'
                                      ],
                                  },
                                ]}
                              >
                                <Input />
                              </Form.Item>
                              <Form.Item
                                label={t['searchTable.columns.containerPort']}
                                field={item.field + '.containerPort'}
                                rules={[
                                  {
                                    required: true,
                                    message:
                                      t[
                                        'searchTable.rules.containerPort.required'
                                      ],
                                  },
                                ]}
                              >
                                <Input type={'number'} max={65535} min={80} />
                              </Form.Item>

                              <Form.Item
                                label={t['searchTable.columns.localPort']}
                                field={item.field + '.localPort'}
                                rules={[
                                  {
                                    required: true,
                                    message:
                                      t['searchTable.rules.localPort.required'],
                                  },
                                ]}
                              >
                                <Input type={'number'} max={65535} min={80} />
                              </Form.Item>
                              <Form.Item
                                label={t['searchTable.columns.protocol']}
                                field={item.field + '.protocol'}
                                initialValue={'tcp'}
                                rules={[
                                  {
                                    required: true,
                                    message:
                                      t['searchTable.rules.envValue.required'],
                                  },
                                ]}
                              >
                                <Select
                                  defaultValue={'tcp'}
                                  options={[
                                    { label: 'tcp', value: 'tcp' },
                                    { label: 'udp', value: 'udp' },
                                  ]}
                                />
                              </Form.Item>
                              <Form.Item
                                label={t['searchTable.columns.editable']}
                                field={item.field + '.editable'}
                                initialValue={true}
                              >
                                <Checkbox defaultChecked />
                              </Form.Item>
                              <Form.Item>
                                <Button
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
                        // style={{ width: '100%' }}
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
          </>
        )}
        <Typography.Title heading={6}>
          {t['searchTable.columns.binds']}
        </Typography.Title>
        <Form.List field="confData.binds">
          {(fields, { add, remove, move }) => {
            return (
              <div>
                {fields.map((item, index) => {
                  return (
                    <div key={item.key}>
                      <Form.Item
                        label={t['searchTable.columns.binds'] + (index + 1)}
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
                            label={t['searchTable.columns.envDescription']}
                            initialValue={
                              t['searchTable.columns.envDescription']
                            }
                            field={item.field + '.description'}
                            rules={[
                              {
                                required: true,
                                message:
                                  t[
                                    'searchTable.rules.envDescription.required'
                                  ],
                              },
                            ]}
                          >
                            <Input />
                          </Form.Item>
                          <Form.Item
                            label={t['searchTable.columns.containerPath']}
                            field={item.field + '.containerPath'}
                            rules={[
                              {
                                required: true,
                                message:
                                  t['searchTable.rules.containerPath.required'],
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
                                  t['searchTable.rules.localPath.required'],
                              },
                            ]}
                          >
                            <Input />
                          </Form.Item>
                          <Form.Item
                            label={t['searchTable.columns.editable']}
                            field={item.field + '.editable'}
                            initialValue={true}
                          >
                            <Checkbox defaultChecked />
                          </Form.Item>
                          <Form.Item>
                            <Button
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
                    // style={{ width: '100%' }}
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
      </Form>
    </Modal>
  );
}

export default EditSubappPage;
