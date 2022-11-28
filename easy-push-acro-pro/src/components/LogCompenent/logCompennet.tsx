
import { List } from '@arco-design/web-react';
import React, { useEffect, useState } from 'react';
import styles from './style/index.module.less';

export interface props{
  values?:string[];
}

function LogCompennet(props:props) {
  const [loading, setLoading] = useState(false);


  useEffect(() => {
    setLoading(true);

  }, []);

  return (
    <div className={styles['log-body']} style={{ minHeight: '100%',minWidth:'100%' }}>
      <List
        bordered={false}
        split={false}
        size={'small'}
        virtualListProps={{
          threshold: 500,
        }}
        dataSource={props.values}
        render={(item, index) => (
            <div key={index} className={styles['log-msg']}>
            <span>{item}</span>
          </div>
        )}
      />
    </div>

  );
}

export default LogCompennet;
