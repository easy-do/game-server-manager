import React, { useEffect, useState } from 'react';
import { Modal } from '@arco-design/web-react';
import locale from './locale';
import useLocale from '@/utils/useLocale';
import { socketAddress } from '@/utils/systemConstant';
import LogCompennet from '@/components/LogCompenent/logCompennet';
import { createWebSocket,closeWebSocket } from '@/utils/webSocket';


function InstallLogInfoPage(props: { logId: string, visible, setVisible }) {

  const t = useLocale(locale);

  const [logResult, setLogResult] = useState([]);

  const logCache = []

  /**点击日志详情按钮 */
  function fetchData() {
    logCache.length=0
    if(props.logId && props.visible){
      const messageParam = {
        "token": localStorage.getItem("token") ? localStorage.getItem("token") : "",
        "type": "application_install_log",
        "data":{
          "logId": props.logId
        }
      }
      createWebSocket(socketAddress,JSON.stringify(messageParam),(event)=>{
          const logResult = JSON.parse(event.data);
          const data = logResult.data.split("\n");
          for (let index = 0; index < data.length; index++) {
            logCache.push(data[index])
          }
          setLogResult([])
          setLogResult(logCache)
      });
    }
  }


  useEffect(() => {
    fetchData();
  }, [props.logId,props.visible]);

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

export default InstallLogInfoPage;
