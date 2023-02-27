import { DatePicker, Form, FormInstance, Input, Modal, Select, Spin, Notification } from '@arco-design/web-react';
import locale from './locale';
import useLocale from '@/utils/useLocale';
import { updateRequest, infoRequest } from '@/api/oauthClient';
import { GlobalContext } from '@/context';
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
          label={t['searchTable.columns.clientId']}
          field="clientId"
          hidden
        >
          <Input placeholder={t['searchForm.clientId.placeholder']} allowClear />
        </Form.Item>
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
          label={t['searchTable.columns.clientSecret']}
          field="clientSecret"
        >
          <Input placeholder={t['searchForm.clientSecret.placeholder']} allowClear />
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
           <DictDataSelect dictCode={'oauth2_grant_type'} placeholder={t['searchForm.authorizedGrantTypes.placeholder']} />
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
      </Spin>
      </Form>
    </Modal>
  );
}

export default UpdatePage;