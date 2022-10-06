import React, { useEffect, useState } from 'react';
import { Descriptions, Modal, Skeleton } from '@arco-design/web-react';
import locale from './locale';
import useLocale from '@/utils/useLocale';
import styles from './style/index.module.less';


function InfoPage(props: { id: number, applicationId: string, visible, setVisible }) {

  const t = useLocale(locale);

  const [logResult, setLogResult] = useState([]);

  const socketAddress = "wss://push.easydo.plus/wss/deployLog";

  // const socketAddress = "ws://localhost:30002/wss/deployLog";

  const logDivs = []

  for (let i = 0; i < logResult.length; i++) {
    logDivs.push(
      <div className={styles['log-msg']}>
        <span>{logResult[i]}</span>
      </div>
    )
  }



  /**点击日志详情按钮 */
  function fetchData() {
    if(props.id && props.applicationId){
      const logWebSocket = new WebSocket(socketAddress);
      logWebSocket.onopen = function () {
        console.log('开启websocket连接.')
        const messageParam = {
          "applicationId": props.applicationId,
          "logId": props.id,
          "token": localStorage.getItem("token") ? localStorage.getItem("token") : ""
        }
        logWebSocket.send(JSON.stringify(messageParam));
      }
      logWebSocket.onerror = function () {
        console.log('websocket连接异常.')
      };
      logWebSocket.onclose = function (e: CloseEvent) {
        console.log('websocket 断开: ' + e.code + ' ' + e.reason + ' ' + e.wasClean)
      }
      logWebSocket.onmessage = function (event: any) {
        console.log('接收到服务端消息')
        try {
          const logResult = JSON.parse(event.data);
          setLogResult(logResult.logs)
          } catch (err:any) {
            setLogResult([event.data])
          }
      }
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
      }}
      onCancel={() => {
        props.setVisible(false);
      }}
      style={{ width: "80%", minHeight: "70%" }}
      autoFocus={false}
      focusLock={true}
      maskClosable={false}
    >
      <section className={styles['log-body']}>
        {logDivs}
      </section>
    </Modal>
  );
}

export default InfoPage;
