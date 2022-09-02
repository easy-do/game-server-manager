import {
  Badge,
  Button,
  List,
  Modal,
  Pagination,
  Spin,
  Tooltip
} from "@douyinfe/semi-ui";
import { IconBell } from "@douyinfe/semi-icons";
import { observer } from "mobx-react";
import { useEffect } from "react";
import useStores from "../../utils/store";

const UserMessage = () => {
  const { OauthStore, UserMessageStore } = useStores();
  const { loginFlag } = OauthStore;
  const {
    loadingData,
    countRequest,
    messageCount,
    messageListShow,
    cleanAllMessage,
    messageListShowButton,
    messageListShowCancel,
    dataList,
    currentPage,
    pageSize,
    total,
    onPageChange,
    readMessage,
    readAllMessage

  } = UserMessageStore;

  //页面渲染执行
  useEffect(() => {
    if (loginFlag) {
      countRequest();
    }
  }, []);

  return (
    <>
      <Tooltip content={"站内消息"}>
        <Badge theme="light" position="leftTop" count={messageCount}>
          <Button
            theme="borderless"
            icon={<IconBell size="large" />}
            style={{
              color: "var(--semi-color-text-2)",
              marginRight: "12px",
            }}
            onClick={messageListShowButton}
          />
        </Badge>
      </Tooltip>
      <Modal
        title="我的消息"
        visible={messageListShow}
        onOk={readAllMessage}
        onCancel={messageListShowCancel}
        maskClosable={false}
        width="50%"
        bodyStyle={{ overflow: "auto" }}
        footer={
          <>
          <Button type="primary" loading={loadingData} onClick={cleanAllMessage} >清空消息</Button>
          <Button type="primary" loading={loadingData} onClick={messageListShowCancel} >关闭窗口</Button>
          <Button type="primary" loading={loadingData} onClick={readAllMessage} >全部已读</Button>
              </>
        }
      >
        <Spin tip={"加载中...."} spinning={loadingData}>
            <List
              dataSource={dataList}
              renderItem={(item) => (
                <List.Item
                  main={
                      <div>
                        <span
                          style={{
                            color: "var(--semi-color-text-0)",
                            fontWeight: 500,
                          }}
                        >
                          {item.title}
                        </span>
                        <p
                          style={{
                            color: "var(--semi-color-text-2)",
                            margin: "4px 0",
                          }}
                        >
                            {item.content}
                        </p>
                      </div>
                  }
                  extra={item.status === 0 ?<Button onClick={()=>readMessage(item.id)} theme="borderless">已读</Button>:null}
                />
              )}
            />
            <Pagination size='small' style={{ width: '100%', flexBasis: '100%', justifyContent: 'center' }} pageSize={pageSize} total={total} currentPage={currentPage} onChange={cPage => onPageChange(cPage)} />

        </Spin>
      </Modal>
    </>
  );
};

export default observer(UserMessage);
