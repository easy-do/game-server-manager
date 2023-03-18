import React, { useContext, useRef } from 'react';
import {
  Form,
  FormInstance,
  Input,
  Modal,
  DatePicker,
  Select,
  Notification,
  Button,
  Space,
  Divider,
  Typography,
  Grid,
} from '@arco-design/web-react';
import locale from './locale';
import useLocale from '@/utils/useLocale';
import { addRequest } from '@/api/applicationVersion';
import { GlobalContext } from '@/context';
import { IconDelete } from '@arco-design/web-react/icon';
import MarkdownEditor from '@/components/MarkdownEditor/MarkdownEditor';

function AddPage({ applicationId, visible, setVisible, successCallBack }) {
  
  const formRef = useRef<FormInstance>();

  const { lang } = useContext(GlobalContext);

  const t = useLocale(locale);

  const handleSubmit = () => {
    formRef.current.validate().then((values) => {
      addRequest({
        ...values,
        applicationId: applicationId,
        confData: JSON.stringify(values.confData),
      }).then((res) => {
        const { success, msg } = res.data;
        if (success) {
          Notification.success({ content: msg, duration: 300 });
          successCallBack();
        }
      });
    });
  };

  return (
    <Modal
      style={{ width: '100%', minHeight: '70%' }}
      // title={t['searchTable.operations.add']}
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
        ref={formRef}
        style={{ width: '95%', marginTop: '6px' }}
        labelCol={{ span: lang === 'en-US' ? 7 : 6 }}
        wrapperCol={{ span: lang === 'en-US' ? 17 : 18 }}
        labelAlign="left"
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
          <Input placeholder={t['searchForm.version.placeholder']} allowClear />
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.image']}
          field="confData.image"
          rules={[
            { required: true, message: t['searchTable.rules.image.required'] },
          ]}
        >
          <Input placeholder={t['searchForm.image.placeholder']} />
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.description']}
          field="description"
        >
          {/* <TextArea placeholder={t['searchForm.description.placeholder']} /> */}
          <MarkdownEditor />
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
                          align="center"
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
                          <Button
                            icon={<IconDelete />}
                            shape="circle"
                            status="danger"
                            onClick={() => remove(index)}
                          ></Button>
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
      </Form>
    </Modal>
  );
}

export default AddPage;
