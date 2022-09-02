import {
  IllustrationNoContent,
  IllustrationConstructionDark,
} from "@douyinfe/semi-illustrations";
import {
  Button,
  ButtonGroup,
  Empty,
  Form,
  Modal,
  Spin,
  Table,
  Tag,
} from "@douyinfe/semi-ui";
import { observer } from "mobx-react";
import { useEffect } from "react";
import useStores from "../../utils/store";

const Server = () => {
  const { OauthStore, ServerInfoStore } = useStores();
  const { loginFlag } = OauthStore;
  const {
    currentPage,
    pageSize,
    total,
    pageRequest,
    onPageChange,
    onPageSizeChange,
    loadingData,
    dataList,
    getFormApi,
    onValueChange,
    addDataShow,
    onClickAddButton,
    addDataOk,
    addDataCancel,
    getAddFormApi,
    editDataShow,
    onClickEditButton,
    editDataOk,
    editDataCancel,
    onClickDeleteButton,
    testServer
  } = ServerInfoStore;

  //页面渲染执行
  useEffect(() => {
    if (loginFlag) {
      pageRequest();
    }
  }, []);

  const columns = [
    {
      title: <Tag>名称</Tag>,
      dataIndex: "serverName",
    },
    {
      title: <Tag>地址</Tag>,
      dataIndex: "address",
    },
    {
      title: <Tag>用户</Tag>,
      dataIndex: "userName",
    },
    {
      title: <Tag>操作</Tag>,
      dataIndex: "operate",
      render: (text: any, record: any) => {
        return (
          <ButtonGroup theme="borderless">
            <Button onClick={() => onClickEditButton(record.id)}>编辑</Button>
            <Button
              type="danger"
              onClick={() => onClickDeleteButton(record.id)}
            >
              删除
            </Button>
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
              <Button onClick={onClickAddButton}>添加</Button>
              <Button onClick={pageRequest}>刷新</Button>
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
        title="添加服务器"
        visible={addDataShow}
        onOk={addDataOk}
        onCancel={addDataCancel}
        maskClosable={false}
        confirmLoading={loadingData}
        cancelButtonProps={loadingData}
        footer={
          <>
          <Button type="primary" loading={loadingData} onClick={testServer} >连接测试</Button>
          <Button type="primary" loading={loadingData} onClick={addDataCancel} >取消</Button>
          <Button type="primary" loading={loadingData} onClick={addDataOk} >提交</Button>
              </>
        }
      >
        <Form
          getFormApi={getAddFormApi}
          onValueChange={(values) => onValueChange(values)}
        >
          <Form.Input
            field="serverName"
            label="服务名"
            style={{ width: "90%" }}
            maxLength={12}
            minLength={2}
          />
          <Form.Input
            field="address"
            label="地址"
            style={{ width: "90%" }}
            maxLength={32}
            minLength={2}
          />
          <Form.Input
            field="port"
            label="端口"
            style={{ width: "90%" }}
            maxLength={6}
            minLength={2}
          />
          <Form.Input
            field="userName"
            label="用户名"
            style={{ width: "90%" }}
            maxLength={12}
            minLength={2}
          />
          <Form.Input
            field="password"
            mode="password"
            label="密码"
            style={{ width: "90%" }}
            maxLength={256}
            minLength={2}
          />
        </Form>
      </Modal>

      <Modal
        title="编辑配置信息"
        visible={editDataShow}
        onOk={editDataOk}
        onCancel={editDataCancel}
        maskClosable={false}
        confirmLoading={loadingData}
        cancelButtonProps={loadingData}
        footer={
          <>
          <Button type="primary" loading={loadingData} onClick={testServer} >连接测试</Button>
          <Button type="primary" loading={loadingData} onClick={editDataCancel} >取消</Button>
          <Button type="primary" loading={loadingData} onClick={editDataOk} >提交</Button>
              </>
        }
      >
        <Form
          getFormApi={getFormApi}
          onValueChange={(values) => onValueChange(values)}
        >
          <Form.Input
            field="id"
            label="服务器ID"
            style={{ width: "90%" }}
            disabled
          />
          <Form.Input
            field="serverName"
            label="服务名"
            style={{ width: "90%" }}
            disabled
          />
          <Form.Input
            field="address"
            label="地址"
            style={{ width: "90%" }}
            disabled
          />
          <Form.Input field="port" label="端口" style={{ width: "90%" }} />
          <Form.Input
            field="userName"
            label="用户名"
            style={{ width: "90%" }}
          />
          <Form.Input field="password" mode="password" label="密码" style={{ width: "90%" }} />
        </Form>
      </Modal>
    </Spin>
  );
};

export default observer(Server);
