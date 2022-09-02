import { Button, ButtonGroup, Empty, Form, Input, Modal, Popconfirm, Space, Spin, Table, Tag, Typography } from "@douyinfe/semi-ui";
import { observer } from "mobx-react";
import { useEffect } from "react";
import useStores from "../../utils/store";
import {
  IllustrationNoContent,
  IllustrationConstructionDark,
} from "@douyinfe/semi-illustrations";
import DiscussionDtails from "./discussionDtails";
import MarkdownEditor from "../markdownEditor/markdownEditor";
import AuditPage from "../auditManager/auditPage";


const Discussion = () => {

  const { Text } = Typography;
  const { OauthStore, DiscussionStore,AuditStore } = useStores();
  const { loginFlag, userInfo } = OauthStore;

  const { commitAuditRequest } = AuditStore;

  const {
    loadingData,
    currentPage,
    pageSize,
    total,
    onPageChange,
    onPageSizeChange,
    managerPage,
    dataList,
    infoModelshow,
    infoModalCancel,
    discussionId,
    showInfoButton,
    addDiscussionButton,
    addDiscussionShow,
    addDiscussionOk,
    addDiscussionCancel,
    onChangeMarkdownTitle,
    onChangeMarkdownContent,
    savePopconfirmShow,
    savePopconfirmOK,
    savePopconfirmCancel,
    markdownTitle,
    markdownContent,
    onClickDeleteButton,
    auditButton,
    auditshow,
    auditId

  } = DiscussionStore;


  //页面渲染执行
  useEffect(() => {
    if (loginFlag) {
      managerPage();
    }
  }, []);

  const columns = [
    {
      title: <Tag>标题</Tag>,
      dataIndex: "title",
    },
    {
      title: <Tag>状态</Tag>,
      dataIndex: "status",
      render: (text: any, record: any) => {
        switch (record.status) {
          case 0:
            return <Text >草稿</Text>
          case 1:
            return <Text style={{color:'blue'}}>审核中</Text>;
          case 2:
            return <Text style={{color:'green'}}>通过</Text>;
          case 3:
            return <Text style={{color:'orange'}}>驳回</Text>;
          case 4:
            return <Text style={{color:'red'}}>锁定</Text>;
          default:
            return <Text style={{color:'red'}}>未知</Text>;
        }
      },
    },
    {
      title: <Tag>提交时间</Tag>,
      dataIndex: "createTime",
    },
    {
      title: <Tag>最后修改时间</Tag>,
      dataIndex: "updateTime",
    },
    {
      title: <Tag>操作</Tag>,
      dataIndex: "operate",
      render: (text: any, record: any) => {
        return (
          <ButtonGroup theme="borderless">
            <Button type='primary' onClick={()=>showInfoButton(record.id)}>详情</Button>
            {/* <Button>编辑</Button> */}
            {userInfo.isAdmin ? <Button type='danger' onClick={()=> onClickDeleteButton(record.id)}>删除</Button> : null}
            <Button disabled={(record.status === 1 || record.status === 2  || record.status === 4)} onClick={()=>commitAuditRequest(record.id,1,managerPage)}>提交审核</Button>
            { userInfo.isAdmin ? <Button onClick={()=>auditButton(record.id)}>审核</Button> : null}
          </ButtonGroup>
        );
      },
    },
  ];

  return (
    <Spin tip={"加载中...."} spinning={loadingData}>
      <Empty
        style={{ display: !loginFlag ? "block" : "none" }}
        image={<IllustrationNoContent style={{ width: 150, height: 150 }} />}
        darkModeImage={
          <IllustrationConstructionDark style={{ width: 150, height: 150 }} />
        }
        title={"未登录。"}
        description="点击头像登录,开始体验功能。"
      />
      {loginFlag ? (
        <Table
          title={
            <ButtonGroup theme="borderless">
              <Button onClick={() => addDiscussionButton()}>发起话题</Button>
              <Button onClick={managerPage}>刷新</Button>
            </ButtonGroup>
          }
          columns={columns}
          dataSource={dataList}
          pagination={{
            currentPage: currentPage,
            pageSize: pageSize,
            total: total,
            showSizeChanger: true,
            pageSizeOpts: [10, 20, 30, 50, 100],
            onPageChange: onPageChange,
            onPageSizeChange: onPageSizeChange,
          }}
        />
      ) : (
        ""
      )}
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
    title={'发起话题'}
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

    <AuditPage
        title="审核话题"
        visible={auditshow}
        auditId={auditId}
        callBack={managerPage}
        auditType={1}
      />
    
    </Spin>
  );
};

export default observer(Discussion);
