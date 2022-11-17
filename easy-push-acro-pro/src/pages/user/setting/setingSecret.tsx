import { resetSecretRequest } from '@/api/oauth';
import { Modal } from '@arco-design/web-react';
import React from 'react';
import { Notification } from '@arco-design/web-react';
import decode from 'jwt-decode';
import { useDispatch } from 'react-redux';
import locale from './locale';
import useLocale from '@/utils/useLocale';
import cookie from 'react-cookies'

function SetingSecret({ visible, setVisible}) {
  
  const t = useLocale(locale);
  
  const dispatch = useDispatch();

  const handleSubmit = () => {
      resetSecretRequest().then((res) => {
        const { success, msg } = res.data
        if(success){
          const token = cookie.load('token')
          localStorage.setItem('token',token)
          const tokenInfo: any = decode(token);
          localStorage.setItem('userInfo', JSON.stringify(tokenInfo.userInfo));
          dispatch({
            type: 'update-userInfo',
            payload: {
              userInfo: tokenInfo.userInfo,
            },
          });
          Notification.success({ content: msg, duration: 3000 })
          setVisible(false)
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
