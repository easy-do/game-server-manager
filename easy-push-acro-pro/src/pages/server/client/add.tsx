import React, { useContext, useRef } from 'react';
import { Form, FormInstance, Input, Modal, Notification } from '@arco-design/web-react';
import locale from './locale';
import useLocale from '@/utils/useLocale';
import { addRequest } from '@/api/clientInfo';
import { GlobalContext } from '@/context';

import { list } from '@/api/serverInfo';
import RequestSelect from '@/components/RequestSelect/RequestSelect';

function AddPage({ visible, setVisible, successCallBack }) {
  
  
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
          label={t['searchTable.columns.clientName']}
          field="clientName"
          rules={[
            { required: true, message: t['searchTable.rules.clientName.required'] },
          ]}
        >
          <Input placeholder={t['searchForm.clientName.placeholder']} allowClear />
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.serverId']}
          field="serverId"
        >
          <RequestSelect 
           placeholder={t['searchForm.serverId.placeholder']}
           lableFiled="serverName"
           valueFiled="id"
           request={()=>list()}
          />
        </Form.Item>
      </Form>
    </Modal>
  );
}

export default AddPage;