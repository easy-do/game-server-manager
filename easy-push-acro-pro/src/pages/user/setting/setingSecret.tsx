import { getUserInfo, resetSecretRequest } from '@/api/oauth';
import { Modal } from '@arco-design/web-react';
import React from 'react';
import { Notification } from '@arco-design/web-react';
import decode from 'jwt-decode';
import { useDispatch } from 'react-redux';
import locale from './locale';
import useLocale from '@/utils/useLocale';

function SetingSecret({ visible, setVisible}) {
  
  const t = useLocale(locale);
  
  const dispatch = useDispatch();

  const handleSubmit = () => {
      resetSecretRequest().then((res) => {
        const { success, data, msg } = res.data
        if(success){
          localStorage.setItem('token',data)
          Notification.success({ content: msg, duration: 3000 })
          setVisible(false)
          getUserInfo().then((res) => {
            const { success, data } = res.data;
            if (success) {
              localStorage.setItem('userInfo', JSON.stringify(data));
              dispatch({
                type: 'update-userInfo',
                payload: {
                  userInfo: data,
                },
              });
            }
          });
        }
      });
  };

  return (
<Modal
      title={t['userSetting.security.operation.resetSecet']}
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
      {t['userSetting.security.operation.confirmResetSecet']}
  </Modal>

  );
}

export default SetingSecret;
