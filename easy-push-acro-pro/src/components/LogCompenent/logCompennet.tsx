
import { List } from '@arco-design/web-react';
import React, { useEffect, useState } from 'react';
import styles from './style/index.module.less';
import MEditor from '../Medit/MEditor';

export interface props{
  values?:string[];
}

function LogCompennet(props:props) {
  const [loading, setLoading] = useState(false);


  useEffect(() => {
    setLoading(true);

  }, []);

  const clientHeight = document.body.clientHeight

  return (
    // <div className={styles['log-body']}>
    //   <List
    //     bordered={false}
    //     split={false}
    //     size={'small'}
    //     virtualListProps={{
    //       threshold: 500,
    //     }}
    //     dataSource={props.values}
    //     render={(item, index) => (
    //         <div key={index} className={styles['log-msg']}>
    //         <span>{item}</span>
    //       </div>
    //     )}
    //   />
      
    // </div>
    <MEditor showLanguageSelect={false} height={clientHeight/1.3 + 'px'} theme='vs-dark' language='azcli' options={{readonly:true,selectOnLineNumbers:true,domReadOnly:true}} value={props.values.join('\n')} callBack={null} />

  );
}

export default LogCompennet;
