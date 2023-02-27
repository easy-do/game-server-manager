import React, { useContext, useRef } from 'react';
import { Form, FormInstance, Input, Modal, DatePicker, Select, Notification } from '@arco-design/web-react';
import locale from './locale';
import useLocale from '@/utils/useLocale';
import { addRequest } from '@/api/oauthClient';
import { GlobalContext } from '@/context';
import DictDataSelect from '@/components/DictCompenent/dictDataSelect';

function AddPage({ visible, setVisible, successCallBack }) {
  
  const TextArea = Input.TextArea;
  
  const formRef = useRef<FormInstance>();

  const { lang } = useContext(GlobalContext);

  const t = useLocale(locale);

  const handleSubmit = () => {
    formRef.current.validate().then((values) => {
      values.authorizedGrantTypes = values.authorizedGrantTypes.join(",");
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
          label={t['searchTable.columns.scope']}
          field="scope"
        >
          <TextArea placeholder={t['searchForm.scope.placeholder']} />
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.authorizedGrantTypes']}
          field="authorizedGrantTypes"
          rules={[
            { required: true, message: t['searchTable.rules.authorizedGrantTypes.required'] },
          ]}
        >
           <DictDataSelect modal={'multiple'} dictCode={'oauth2_grant_type'} placeholder={t['searchForm.authorizedGrantTypes.placeholder']} />
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.redirectUri']}
          field="redirectUri"
          rules={[
            { required: true, message: t['searchTable.rules.redirectUri.required'] },
          ]}
        >
          <TextArea placeholder={t['searchForm.redirectUri.placeholder']} />
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.accessTokenValidity']}
          field="accessTokenValidity"
        >
          <Input placeholder={t['searchForm.accessTokenValidity.placeholder']} allowClear />
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.refreshTokenValidity']}
          field="refreshTokenValidity"
        >
          <Input placeholder={t['searchForm.refreshTokenValidity.placeholder']} allowClear />
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.clientTokenValidity']}
          field="clientTokenValidity"
        >
          <Input placeholder={t['searchForm.clientTokenValidity.placeholder']} allowClear />
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.status']}
          field="status"
          rules={[
            { required: true, message: t['searchTable.rules.status.required'] },
          ]}
        >
           <DictDataSelect dictCode={'status_select'} placeholder={t['searchForm.status.placeholder']} />
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.remark']}
          field="remark"
        >
          <TextArea placeholder={t['searchForm.remark.placeholder']} />
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.createTime']}
          field="createTime"
        >
          <DatePicker
            style={{ width: '100%' }}
            showTime={{
              defaultValue: '04:05:06',
            }}
            format='YYYY-MM-DD HH:mm:ss'
          />
        </Form.Item>
      </Form>
    </Modal>
  );
}

export default AddPage;