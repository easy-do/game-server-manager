import { DatePicker, Form, FormInstance, Input, Modal, Select, Spin, Notification, Button, Space, Typography, Divider } from '@arco-design/web-react';
import locale from './locale';
import useLocale from '@/utils/useLocale';
import { updateRequest, infoRequest } from '@/api/applicationVersion';
import { GlobalContext } from '@/context';
import { useContext, useEffect, useRef } from 'react';
import React from 'react';
import { IconDelete } from '@arco-design/web-react/icon';
import MarkdownEditor from '@/components/MarkdownEditor/MarkdownEditor';

function UpdatePage({ id, visible, setVisible, successCallBack }) {

  const formRef = useRef<FormInstance>();

  const { lang } = useContext(GlobalContext);

  const [loading, setLoading] = React.useState(false);
  
  //加载数据
  function fetchData() {
    if (id !== undefined && visible) {
      setLoading(true)
      infoRequest(id).then((res) => {
        const { success, data } = res.data;
        if (success) {
          data.confData = JSON.parse(data.confData)
          formRef.current.setFieldsValue(data);
          setLoading(false)
        }
      });
    }
  }

  useEffect(() => {
    fetchData();
  }, [id,visible]);

  const t = useLocale(locale);

  //提交修改
  const handleSubmit = () => {
    formRef.current.validate().then((values) => {
      updateRequest({...values,confData:JSON.stringify(values.confData)}).then((res) => {
        const { success, msg} = res.data
        if(success){
          Notification.success({ content: msg, duration: 300 })
          successCallBack();
        }
      });
    });
  };

  return (
    <Modal
    style={{ width: '80%', minHeight: '70%' }}
      title={t['searchTable.update.title']}
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
        style={{ width: '95%', marginTop: '6px' }}
        labelCol={{ span: lang === 'en-US' ? 7 : 6 }}
        wrapperCol={{ span: lang === 'en-US' ? 17 : 18 }}
        ref={formRef}
        labelAlign="left"
      >

      </Form>
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
       <Spin tip='loading Data...' loading={loading}>
      <Form.Item
          field="id"
          hidden
        >
          <Input />
        </Form.Item>

        <Form.Item
          label={t['searchTable.columns.version']}
          field="version"
          disabled
          rules={[
            { required: true, message: t['searchTable.rules.version.required'] },
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
          <MarkdownEditor />
        </Form.Item>

        <Typography.Title heading={6}>
          {t['searchTable.columns.EnvInfo']}
        </Typography.Title>
        <Form.List field='confData.envs'>
          {(fields, { add, remove, move }) => {
            return (
              <div>
                {fields.map((item, index) => {
                  return (
                    <div key={item.key}>
                      <Form.Item label={t['searchTable.columns.EnvInfo'] + (index+1)}>
                        <Space style={{ width: '100%', justifyContent: 'space-between' }} split={<Divider type='vertical' />} align='center' size='large'>
                          <Form.Item
                            label={t['searchTable.columns.envName']}
                            field={item.field + '.envName'}
                            rules={[{ required: true, message: t['searchTable.rules.envName.required'] }]}
                          >
                            <Input />
                          </Form.Item>

                          <Form.Item
                            label={t['searchTable.columns.envKey']}
                            field={item.field + '.envKey'}
                            rules={[{ required: true, message: t['searchTable.rules.envKey.required'] }]}
                          >
                            <Input />
                          </Form.Item>
                          <Form.Item
                            label={t['searchTable.columns.envValue']}
                            field={item.field + '.envValue'}
                            rules={[{ required: true, message: t['searchTable.rules.envValue.required'] }]}
                          >
                            <Input />
                          </Form.Item>
                          <Form.Item
                            label={t['searchTable.columns.envDescription']}
                            initialValue={t['searchTable.columns.envDescription']}
                            field={item.field + '.description'}
                            rules={[{ required: true, message: t['searchTable.rules.envDescription.required'] }]}
                          >
                            <Input />
                          </Form.Item>
                          <Button
                            icon={<IconDelete />}
                            shape='circle'
                            status='danger'
                            onClick={() => remove(index)}
                          >
                          </Button>
                        </Space>
                      </Form.Item>
                    </div>
                  );
                })}
                <Form.Item >
                  <Button
                    type='dashed'
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
      
      </Spin>
      </Form>
    </Modal>
  );
}

export default UpdatePage;