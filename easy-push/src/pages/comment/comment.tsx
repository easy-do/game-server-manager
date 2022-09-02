import {
  Avatar,
  Button,
  Empty,
  List,
  Modal,
  Pagination,
  Popconfirm,
  Spin,
} from "@douyinfe/semi-ui";
import { observer } from "mobx-react";
import { useEffect } from "react";
import useStores from "../../utils/store";
import { IllustrationNoContent } from "@douyinfe/semi-illustrations";
import MarkdownEditor from "../markdownEditor/markdownEditor";

const Comment = (props: any) => {
  const { CommentStore } = useStores();
  const { discussionId, type } = props;
  const {
    loadingData,
    dataList,
    total,
    currentPage,
    pageSize,
    onPageChange,
    pageRequest,
    commentMarkdown,
    commentValueChange,
    saveComment,
    saveCommentShow,
    saveCommentOK,
    saveCommentCancel,
    commentModalButton,
    commentModalShow,
    commentModalCancel,
    commentModalTile,
  } = CommentStore;

  const { OauthStore } = useStores();

  const { loginFlag } = OauthStore;

  useEffect(() => {
    pageRequest(discussionId);
  }, [discussionId]);

  return (
    <>
      <Spin tip={"加载中...."} spinning={loadingData}>
        {dataList.length === 0 ? (
          <Empty
            image={
              <IllustrationNoContent style={{ width: 150, height: 150 }} />
            }
            title={""}
            description="来做第一个回复的人吧！"
          />
        ) : (
          <>
            <List
              dataSource={dataList}
              renderItem={(item) => (
                <List.Item
                  header={<Avatar src={item.userAvatar} color={"red"} />}
                  main={
                    <div>
                      <span
                        style={{
                          fontSize: 9,
                        }}
                      >
                        {item.userName}
                      </span>
                      <p
                        style={{
                          margin: "4px 0",
                          fontSize: 10,
                        }}
                      >
                        &nbsp;&nbsp;{item.content}
                      </p>
                      <p
                        style={{
                          margin: "4px 0",
                          fontSize: 8,
                        }}
                      >
                        {item.createTime}&nbsp;&nbsp;
                        {loginFlag ? (
                          <Button
                            size="small"
                            onClick={() =>
                              commentModalButton(
                                discussionId,
                                item.parentId === 0 ? item.id : item.parentId,
                                item.id
                              )
                            }
                            theme="borderless"
                          >
                            回复
                          </Button>
                        ) : null}
                      </p>
                      {item.children.length > 0 ? (
                        <>
                          <List
                            dataSource={item.children}
                            renderItem={(item1) => (
                              <List.Item
                                // header={
                                //   <Avatar src={item1.userAvatar} color={"red"}/>
                                // }
                                main={
                                  <div>
                                    <span
                                      style={{
                                        margin: "1px 0",
                                        fontSize: 8,
                                      }}
                                    >
                                      {item1.userName}@{item1.toUserName}
                                    </span>
                                    <p
                                      style={{
                                        margin: "1px 0",
                                        fontSize: 9,
                                      }}
                                    >
                                      &nbsp;&nbsp;{item1.content}
                                    </p>
                                    <p
                                      style={{
                                        margin: "1px 0",
                                        fontSize: 7,
                                      }}
                                    >
                                      {item1.createTime}&nbsp;&nbsp;
                                      {loginFlag ? (
                                        <Button
                                          size="small"
                                          onClick={() =>
                                            commentModalButton(
                                              discussionId,
                                              item1.parentId === 0
                                                ? item1.id
                                                : item1.parentId,
                                              item1.id
                                            )
                                          }
                                          theme="borderless"
                                        >
                                          回复
                                        </Button>
                                      ) : null}
                                    </p>
                                  </div>
                                }
                                extra={null}
                              />
                            )}
                          />
                          <Pagination
                            size="small"
                            style={{
                              width: "100%",
                              flexBasis: "100%",
                              justifyContent: "center",
                            }}
                            pageSize={pageSize}
                            total={total}
                            currentPage={currentPage}
                            onChange={(cPage) => onPageChange(cPage)}
                          />
                        </>
                      ) : null}
                    </div>
                  }
                  extra={null}
                />
              )}
            />
          </>
        )}

        {loginFlag ? (
          <Button
            style={{
              width: "100%",
              flexBasis: "100%",
              justifyContent: "center",
            }}
            onClick={() => commentModalButton(discussionId, 0, 0)}
            theme="borderless"
          >
            评论话题
          </Button>
        ) : null}

        <Modal
          title={commentModalTile}
          visible={commentModalShow}
          onOk={saveComment}
          onCancel={commentModalCancel}
          cancelText={"关闭"}
          okText={"提交"}
          maskClosable={false}
          fullScreen
          confirmLoading={loadingData}
          cancelLoading={loadingData}
        >
          <Popconfirm
            visible={saveCommentShow}
            title="提交确认"
            content="确定提交评论？"
            onConfirm={() => saveCommentOK(discussionId, type)}
            onCancel={saveCommentCancel}
          ></Popconfirm>
          <MarkdownEditor
            onSave={saveComment}
            modelValue={commentMarkdown}
            onChange={commentValueChange}
          ></MarkdownEditor>
        </Modal>
      </Spin>
    </>
  );
};

export default observer(Comment);
