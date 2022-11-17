import { resetSecretRequest, userResetPassword } from '@/api/oauth';
import { Form, FormInstance, Input, Modal } from '@arco-design/web-react';
import React, { useRef } from 'react';
import { Notification } from '@arco-design/web-react';
import locale from './locale';
import useLocale from '@/utils/useLocale';

function SetingPassword({ visible, setVisible}) {
  
  const t = useLocale(locale);

  const formRef = useRef<FormInstance>();

  const handleSubmit = () => {
    formRef.current.validate().then((values) => {
      userResetPassword(values).then((res) => {
          const { success, msg } = res.data
          if(success){
            setVisible(false)
            Notification.success({ content: msg, duration: 3000 })
          }
        });
    });
  };

  return (
<Modal
      title={t['userSetting.security.operation.resetPassword']}
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
      >
        <Form.Item
        field={'password'}
        label={t['userSetting.security.newPassword']}
        >
        <Input placeholder={t['userSetting.security.newPassword']} />
        </Form.Item>
      </Form>
  </Modal>

  );
}

export default SetingPassword;
