import React, { useState } from 'react';
import { useSelector } from 'react-redux';
import cs from 'classnames';
import { Button, Typography } from '@arco-design/web-react';
import useLocale from '@/utils/useLocale';
import locale from './locale';
import styles from './style/index.module.less';
import SetingSecret from './setingSecret';
import SetingPassword from './setingPassword';

function Security() {

  const { Text } = Typography;
  
  const t = useLocale(locale);

  const userInfo = useSelector((state: any) => {
    return state.userInfo || {};
  });

  const data = [
    {
      title: t['userSetting.security.secret'],
      value: <Text copyable >{userInfo.secret}</Text>,
      placeholder: t['userSetting.security.secret.tips'],
      type: 'secret',
    },
    {
      title: t['userSetting.security.password'],
      value: t['userSetting.security.password.tips'],
      type: 'password',
    },
    {
      title: t['userSetting.security.email'],
      value: userInfo.email,
      placeholder: t['userSetting.security.email.placeholder'],
      type: 'email',
    },
  ];

  const [visibleSetSecret,setVisibleSetSecret] = useState(false);

  const [visiblePassword,setVisiblePassword] = useState(false);

  const setingSecurity  = (type)=> {
    console.log(type)
   switch(type){
    case 'secret':
      setVisibleSetSecret(true);
      break;
    case 'password':
      setVisiblePassword(true);
      break;
    case 'email':
      break;
    default:
      break;
   }
  }

  return (
    <div className={styles['security']}>
      {data.map((item, index) => (
        <div className={styles['security-item']} key={index}>
          <span className={styles['security-item-title']}>{item.title}</span>
          <div className={styles['security-item-content']}>
            <span
              className={cs({
                [`${styles['security-item-placeholder']}`]: !item.value,
              })}
            >
              {item.value || item.placeholder}
            </span>

            <span>
              <Button type="text" onClick={()=>setingSecurity(item.type)}>
                {item.value
                  ? t['userSetting.btn.edit']
                  : t['userSetting.btn.set']}
              </Button>
            </span>
          </div>
        </div>
      ))}
      <SetingSecret visible={visibleSetSecret} setVisible={setVisibleSetSecret} />
      <SetingPassword visible={visiblePassword} setVisible={setVisiblePassword} />
    </div>
  );
}

export default Security;
