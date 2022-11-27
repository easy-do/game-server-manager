import React, { useEffect, useState } from 'react';
import groupBy from 'lodash/groupBy';
import {
  Trigger,
  Badge,
  Tabs,
  Spin,
  Button,
  Notification,
} from '@arco-design/web-react';
import { IconCustomerService } from '@arco-design/web-react/icon';
import useLocale from '../../utils/useLocale';
import MessageList, { MessageListType } from './list';
import styles from './style/index.module.less';
import {
  cleanAllMessageRequest,
  count,
  messageList,
  readMessageRequest,
} from '@/api/userMessage';
import checkLogin from '@/utils/checkLogin';

function DropContent() {
  const t = useLocale();
  const [loading, setLoading] = useState(false);
  const [groupData, setGroupData] = useState<{
    [key: string]: MessageListType;
  }>({});
  const [sourceData, setSourceData] = useState<MessageListType>([]);

  function fetchSourceData(showLoading = true) {
    showLoading && setLoading(true);
    messageList()
      .then((res) => {
        setSourceData(res.data.data);
      })
      .finally(() => {
        showLoading && setLoading(false);
      });
  }

  function readMessage(data: MessageListType) {
    const ids = data.map((item) => item.id);
    readMessageRequest(ids).then(() => {
      fetchSourceData();
    });
  }

  function cleanMessage() {
    cleanAllMessageRequest().then((res) => {
      const { success } = res.data;
      if (success) {
        setSourceData([]);
      }
    });
  }

  useEffect(() => {
    fetchSourceData();
  }, []);

  useEffect(() => {
    const groupData: { [key: number]: MessageListType } = groupBy(
      sourceData,
      'type'
    );
    setGroupData(groupData);
  }, [sourceData]);

  const tabList = [
    // {
    //   key: 'message',
    //   title: t['message.tab.title.message'],
    //   titleIcon: <IconMessage />,
    // },
    {
      key: 1,
      title: t['message.tab.title.notice'],
      titleIcon: <IconCustomerService />,
    },
    // {
    //   key: 'todo',
    //   title: t['message.tab.title.todo'],
    //   titleIcon: <IconFile />,
    //   avatar: (
    //     <Avatar style={{ backgroundColor: '#0FC6C2' }}>
    //       <IconDesktop />
    //     </Avatar>
    //   ),
    // },
  ];

  return (
    <div className={styles['message-box']}>
      <Spin loading={loading} style={{ display: 'block' }}>
        <Tabs
          overflow="dropdown"
          type="rounded"
          defaultActiveTab="1"
          destroyOnHide
          extra={
            <Button type="text" onClick={() => cleanMessage()}>
              {t['message.empty']}
            </Button>
          }
        >
          {tabList.map((item) => {
            const { key, title } = item;
            const data = groupData[key] || [];
            const unReadData = data.filter((item) => !item.status);
            return (
              <Tabs.TabPane
                key={key}
                title={
                  <span>
                    {title}
                    {unReadData.length ? `(${unReadData.length})` : ''}
                  </span>
                }
              >
                <MessageList
                  data={data}
                  unReadData={unReadData}
                  onItemClick={(item) => {
                    readMessage([item]);
                  }}
                  onAllBtnClick={(unReadData) => {
                    readMessage(unReadData);
                  }}
                />
              </Tabs.TabPane>
            );
          })}
        </Tabs>
      </Spin>
    </div>
  );
}

function MessageBox({ children, userInfo }) {
  const [messageCount, setMessageCount] = useState(0);
  const [countSetTimeOut, setCountSetTimeOut] = useState(false);

  /**
   * 获取未读消息数量
   */
  const countRequest = () => {
    count().then((result) => {
      if (result.data.success) {
        const count = result.data.data;
        if (count > 0) {
          if (!sessionStorage.getItem('isPlayMessaudio')) {
            // this.audio.play();
            Notification.success({ content: '您有' + count + '条未读消息。' });
            sessionStorage.setItem('isPlayMessaudio', 'true');
          }
        } else {
          sessionStorage.removeItem('isPlayMessaudio');
        }
        setMessageCount(messageCount);
        //30秒后再刷新一次未读消息数量
        if (!countSetTimeOut) {
          setCountSetTimeOut(true);
          setTimeout(countRequest, 30000);
        }
      }
    });
  };

  useEffect(() => {
    if (checkLogin()) {
      setTimeout(countRequest, 5000);
    }
  }, [JSON.stringify(userInfo)]);

  return (
    <Trigger
      trigger="hover"
      popup={() => <DropContent />}
      position="br"
      unmountOnExit={false}
      popupAlign={{ bottom: 4 }}
    >
      <Badge count={messageCount}>{children}</Badge>
    </Trigger>
  );
}

export default MessageBox;
