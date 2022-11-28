import React, { useEffect, useState } from 'react';
import { Modal } from '@arco-design/web-react';
import locale from './locale';
import useLocale from '@/utils/useLocale';
import { socketAddress } from '@/utils/systemConstant';
import LogCompennet from '@/components/LogCompenent/logCompennet';
import { createWebSocket,closeWebSocket } from '@/utils/webSocket';


function InfoPage(props: { id: number, applicationId: string, visible, setVisible }) {

  const t = useLocale(locale);

  const [logResult, setLogResult] = useState([]);



  /**点击日志详情按钮 */
  function fetchData() {
    if(props.id && props.visible){
      const messageParam = {
        "token": localStorage.getItem("token") ? localStorage.getItem("token") : "",
        "type": "deploy_log",
        "data":{
          "applicationId": props.applicationId,
          "logId": props.id
        }
      }
      createWebSocket(socketAddress,JSON.stringify(messageParam),(event)=>{
        try {
          const logResult = JSON.parse(event.data);
          if(logResult.type === 'success'){
            setLogResult(JSON.parse(logResult.data))
          }else{
            setLogResult(JSON.parse(logResult.data))
          }
          } catch (err:any) {
            setLogResult([event.data])
          }
      });
    }
  }


  useEffect(() => {
    fetchData();
  }, [props.id,props.applicationId,props.visible]);

  return (
    <Modal
      title={t['searchTable.info.title']}
      visible={props.visible}
      onOk={() => {
        props.setVisible(false);
        closeWebSocket();
      }}
      onCancel={() => {
        props.setVisible(false);
        closeWebSocket();
      }}
      style={{ width: "80%", minHeight: "70%" }}
      autoFocus={false}
      focusLock={true}
      maskClosable={false}
    >
      <LogCompennet values={logResult}/>
    </Modal>
  );
}

export default InfoPage;
