import { DatePicker, Form, FormInstance, Input, Modal, Select, Spin, Notification } from '@arco-design/web-react';
import locale from './locale';
import useLocale from '@/utils/useLocale';
import { updateRequest, infoRequest } from '@/api/serverInfo';
import { GlobalContext } from '@/context';
import { Status } from './constants';
import { useContext, useEffect, useRef } from 'react';
import React from 'react';
import DictDataSelect from '@/components/DictCompenent/dictDataSelect';

function UpdatePage({ id, visible, setVisible, successCallBack }) {

  const TextArea = Input.TextArea;

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
          formRef.current.setFieldsValue(data);
        }
        setLoading(false)
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
      updateRequest(values).then((res) => {
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
      <Spin tip='loading Data...' loading={loading}>
        <Form.Item
          label={t['searchTable.columns.id']}
          field="id"
          hidden
        >
          <Input placeholder={t['searchForm.id.placeholder']} allowClear />
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.serverName']}
          field="serverName"
          rules={[
            { required: true, message: t['searchTable.rules.serverName.required'] },
          ]}
          disabled
        >
          <Input placeholder={t['searchForm.serverName.placeholder']} allowClear />
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.address']}
          field="address"
          rules={[
            { required: true, message: t['searchTable.rules.address.required'] },
          ]}
        >
          <Input placeholder={t['searchForm.address.placeholder']} allowClear />
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.port']}
          field="port"
          rules={[
            { required: true, message: t['searchTable.rules.port.required'] },
          ]}
        >
          <Input placeholder={t['searchForm.port.placeholder']} allowClear />
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.userName']}
          field="userName"
          rules={[
            { required: true, message: t['searchTable.rules.userName.required'] },
          ]}
        >
          <Input placeholder={t['searchForm.userName.placeholder']} allowClear />
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.password']}
          field="password"
          rules={[
            { required: true, message: t['searchTable.rules.password.required'] },
          ]}
        >
          <Input.Password placeholder={t['searchForm.password.placeholder']} allowClear />
        </Form.Item>
      </Spin>
      </Form>
    </Modal>
  );
}

export default UpdatePage;