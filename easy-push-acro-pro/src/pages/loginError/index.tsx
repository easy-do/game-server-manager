import React from 'react';
import {  Result, Button, } from '@arco-design/web-react';
import useLocale from '@/utils/useLocale';
import locale from './locale';
import styles from './style/index.module.less';

function Success() {
  const t = useLocale(locale);

  const goBack = ()=>{
    window.location.pathname = "/"
  }

  return (
    <div>
      <div className={styles.wrapper}>
        <Result
          className={styles.result}
          status="error"
          title={t['error.result.title']}
          subTitle={t['error.result.subTitle']}
          extra={[
            // <Button key="again" type="secondary" style={{ marginRight: 16 }}>
            //   {t['error.result.goBack']}
            // </Button>,
            <Button key="back" type="primary" onClick={()=>goBack()}>
              {t['error.result.goBack']}
            </Button>,
          ]}
        />
      </div>
    </div>
  );
}

export default Success;
