import {
  Avatar,
  Banner,
  Button,
  ButtonGroup,
  Input,
  List,
  Modal,
  Popconfirm,
  Space,
  Spin,
  Tooltip,
} from "@douyinfe/semi-ui";
import { observer } from "mobx-react";
import { useEffect } from "react";
import useStores from "../../utils/store";
import InfiniteScroll from "react-infinite-scroller";
import {
  IconLikeThumb,
  IconDislikeThumb,
} from "@douyinfe/semi-icons";
import MarkdownEditor from "../markdownEditor/markdownEditor";
import DiscussionDtails from "./discussionDtails";
import DiscussionManager from "./discussionManager";


const Discussion = () => {
  const { OauthStore, DiscussionStore } = useStores();
  const { loginFlag } = OauthStore;
  const {
    loadingData,
    currentPage,
    total,
    pageRequest,
    dataList,
    hasMore,
    showLoadMore,
    addDiscussionButton,
    markdownTitle,
    markdownContent,
    addDiscussionShow,
    onChangeMarkdownTitle,
    onChangeMarkdownContent,
    addDiscussionOk,
    addDiscussionCancel,
    savePopconfirmShow,
    savePopconfirmOK,
    savePopconfirmCancel,
    showInfoButton,
    infoModelshow,
    infoModalCancel,
    discussionId,
    myDiscussionShow,
    myDiscussionButton,
    myDiscussionCancel
  } = DiscussionStore;

  //页面渲染执行
  useEffect(() => {
    pageRequest(currentPage);
  }, []);

  const loadMore =
    dataList.length < total ? (
      <div
        style={{
          textAlign: "center",
          marginTop: 12,
          height: 32,
          lineHeight: "32px",
        }}
      >
        <Button onClick={() => pageRequest(currentPage + 1)}>显示更多</Button>
      </div>
    ) : null;

  return (
    <Spin size="large" tip='加载中' spinning={loadingData}>
      <InfiniteScroll
        initialLoad={true}
        pageStart={1}
        threshold={20}
        loadMore={() => pageRequest(currentPage + 1)}
        hasMore={hasMore && !showLoadMore}
        useWindow={false}
      >
        {loginFlag? null:<Banner description="未登录。"/>} 
        {loginFlag? <Tooltip content={'发起话题需管理审核，优质话题会有奖励。'}><Button onClick={() => addDiscussionButton()}>发起话题</Button></Tooltip>:null} 
        {loginFlag? <Tooltip content={'管理个人话题'}><Button onClick={() => myDiscussionButton()}>我的话题</Button></Tooltip>:null} 
        <List
          loadMore={loadMore}
          dataSource={dataList}
          renderItem={(item) => (
            <List.Item
              header={<Avatar color={"red"}>help</Avatar>}
              main={
                <Tooltip content={'点击查看详情'}>
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
                    {item.createName + '  ' + item.createTime}
                    {/* <span onClick={()=>showInfoButton(item.id)}><text style={{color:'blue'}}>详情</text></span> */}
                  </p>
                </div>
                </Tooltip>
              }
              extra={
                <ButtonGroup theme="borderless">
                  <Button>
                    <IconLikeThumb style={{ color: "red" }} />
                    <span style={{ color: "red" }}>{item.agree}</span>
                  </Button>
                  <Button>
                    <IconDislikeThumb style={{ color: "black" }} />
                    <span style={{ color: "black" }}>{item.oppose}</span>
                  </Button>
                </ButtonGroup>
              }
              onClick={()=>showInfoButton(item.id)}
            />
            
          )}
        />
        {dataList.lenght < total ? (
          <div style={{ textAlign: "center" }}>{loadMore}</div>
        ) : null}
      </InfiniteScroll>
      <Modal
        title={'发起讨论'}
        visible={addDiscussionShow}
        onOk={addDiscussionOk}
        onCancel={addDiscussionCancel}
        cancelText={'关闭'}
        okText={'保存'}
        maskClosable={false}
        fullScreen
        confirmLoading={loadingData}
        cancelLoading={loadingData}
      >
        <Popconfirm
          visible={savePopconfirmShow}
          title="保存确认"
          content="确定是否要保存？"
          onConfirm={savePopconfirmOK}
          onCancel={savePopconfirmCancel}
        >
        </Popconfirm>
        <Input  placeholder="标题" value={markdownTitle} onChange={onChangeMarkdownTitle} width={'100%'}/>
        <Space vertical>
          <span>&nbsp;</span>
        </Space>
        <MarkdownEditor
          modelValue={markdownContent}
          onChange={onChangeMarkdownContent}
          onSave={addDiscussionOk}
          previewOnly={false}
        />
      </Modal>

      <Modal
        title={'查看话题'}
        visible={infoModelshow}
        maskClosable={false}
        fullScreen
        onCancel={infoModalCancel}
        footer={''}
        bodyStyle={{ overflow: "auto" }}
      >
        <DiscussionDtails discussionId={discussionId}/>
      </Modal>

      <Modal
        title={'我的话题'}
        visible={myDiscussionShow}
        maskClosable={false}
        fullScreen
        onCancel={myDiscussionCancel}
        footer={''}
        bodyStyle={{ overflow: "auto" }}
      >
        <DiscussionManager/>
      </Modal>

      </Spin>
  );
};

export default observer(Discussion);
