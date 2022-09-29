import React, { useContext, useRef } from 'react';
import dayjs from 'dayjs';
import { Form, FormInstance, Input, Modal, DatePicker, Select, Notification } from '@arco-design/web-react';
import locale from './locale';
import useLocale from '@/utils/useLocale';
import { addRequest } from '@/api/serverInfo';
import { GlobalContext } from '@/context';
import { Status } from './constants';
import DictDataSelect from '@/components/DictCompenent/dictDataSelect';

function AddPage({ visible, setVisible, successCallBack }) {
  
  const TextArea = Input.TextArea;
  
  const formRef = useRef<FormInstance>();

  const { lang } = useContext(GlobalContext);

  const t = useLocale(locale);

  const handleSubmit = () => {
    formRef.current.validate().then((values) => {
      addRequest(values).then((res) => {
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
      >
        <Form.Item
          label={t['searchTable.columns.serverName']}
          field="serverName"
          rules={[
            { required: true, message: t['searchTable.rules.serverName.required'] },
          ]}
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
      </Form>
    </Modal>
  );
}

export default AddPage;