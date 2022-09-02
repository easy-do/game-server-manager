import {
  IllustrationNoContent,
  IllustrationConstructionDark,
} from "@douyinfe/semi-illustrations";
import {
  Button,
  ButtonGroup,
  Col,
  Empty,
  Form,
  Modal,
  Popconfirm,
  Row,
  SideSheet,
  Spin,
  Switch,
  Table,
  Tree,
  Typography,
} from "@douyinfe/semi-ui";
import { observer } from "mobx-react";
import { useEffect } from "react";
import useStores from "../../utils/store";
import RoleManagerInfo from "./roleManagerInfo";

const RoleManager = () => {
  const { Text } = Typography;
  const { OauthStore, RoleManagerStore, MenuManagerStore } = useStores();
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
    onClickDeleteButton,
    getSerchFormapi,
    serchFormValueChange,
    searchButton,
    cleanSearchform,
    deletecConfirmShow,
    deletecConfirmOK,
    deletecConfirmCancel,
    dataInfoShow,
    dataInfoButton,
    dataInfo,
    dataInfoHiden,
    changeStatusRequest,
    getFormApi,
    onValueChange,
    addDataShow,
    onClickAddButton,
    addDataOk,
    addDataCancel,
    editDataShow,
    onClickEditButton,
    editDataOk,
    editDataCancel,
    onClickAuthMenuButton,
    authMenuShow,
    roleAuthMenuIds,
    setRoleAuthMenuIds,
    authMenuOk,
    authMenuCancel,
  } = RoleManagerStore;

  const { treeSelectList, treeSelectRequest } = MenuManagerStore;

  const statuSelctlist = [
    { value: 0, label: "开启", otherKey: 0 },
    { value: 1, label: "禁用", otherKey: 1 },
  ];

  const defaultSelctlist = [
    { value: 0, label: "否", otherKey: 0 },
    { value: 1, label: "是", otherKey: 1 },
  ];

  //页面渲染执行
  useEffect(() => {
    if (loginFlag) {
      pageRequest();
      treeSelectRequest();
    }
  }, []);

  const columns = [
    {
      title: "角色名",
      dataIndex: "roleName",
    },
    {
      title: "权限字符",
      dataIndex: "roleKey",
    },
    {
      title: "启用",
      render: (text: any, record: any) => {
        switch (record.status) {
          case 0:
            return (
              <Switch
                defaultChecked={true}
                onChange={() => changeStatusRequest(record.roleId, 1)}
                aria-label="a switch for semi demo"
              ></Switch>
            );
          case 1:
            return (
              <Switch
                defaultChecked={false}
                onChange={() => changeStatusRequest(record.roleId, 0)}
                aria-label="a switch for semi demo"
              ></Switch>
            );
          default:
            return <Text style={{ color: "red" }}>未知</Text>;
        }
      },
    },
    {
      title: "默认角色",
      render: (text: any, record: any) => {
        switch (record.isDefault) {
          case 0:
            return "否";
          case 1:
            return "是";
          default:
            return <Text style={{ color: "red" }}>未知</Text>;
        }
      },
    },
    {
      title: "创建时间",
      dataIndex: "createTime",
    },
    {
      title: "更新时间",
      dataIndex: "updateTime",
    },
    {
      title: "操作",
      dataIndex: "operate",
      render: (text: any, record: any) => {
        return (
          <ButtonGroup theme="borderless">
            <Button onClick={() => dataInfoButton(record.roleId)}>详情</Button>
            <Button onClick={() => onClickEditButton(record.roleId)}>
              编辑
            </Button>
            <Button onClick={() => onClickAuthMenuButton(record.roleId)}>
              授权
            </Button>
            <Button
              type="danger"
              onClick={() => onClickDeleteButton(record.roleId)}
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
        <>
          <Popconfirm
            visible={deletecConfirmShow}
            title="删除确认"
            content="确定删除？"
            onConfirm={deletecConfirmOK}
            onCancel={deletecConfirmCancel}
          ></Popconfirm>
          <Table
            title={
              <>
                <Form
                  labelPosition="left"
                  labelAlign="left"
                  style={{ padding: "10px" }}
                  getFormApi={getSerchFormapi}
                  onValueChange={serchFormValueChange}
                >
                  <Row>
                    <Col span={4}>
                      <Form.Input
                        label="角色名"
                        field="roleName"
                        style={{ width: "90%" }}
                        showClear
                      ></Form.Input>
                    </Col>
                    <Col span={4}>
                      <Form.Input
                        label="角色id"
                        field="roleId"
                        style={{ width: "90%" }}
                        showClear
                      ></Form.Input>
                    </Col>
                    <Col span={4}>
                      <Form.Select
                        label="角色状态"
                        field="status"
                        style={{ width: "90%" }}
                        showClear
                      >
                        <Form.Select.Option label="正常" value={0} />
                        <Form.Select.Option label="禁用" value={1} />
                      </Form.Select>
                    </Col>
                  </Row>
                </Form>
                <ButtonGroup theme="borderless">
                  <Button onClick={searchButton}>搜索</Button>
                  <Button onClick={cleanSearchform}>重置</Button>
                  <Button onClick={pageRequest}>刷新</Button>
                  <Button onClick={onClickAddButton}>添加</Button>
                </ButtonGroup>
              </>
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
          <SideSheet
            title="角色详情"
            visible={dataInfoShow}
            onCancel={dataInfoHiden}
          >
            <RoleManagerInfo dataInfo={dataInfo} />
          </SideSheet>

          <Modal
            title="添加角色"
            visible={addDataShow}
            onOk={addDataOk}
            onCancel={addDataCancel}
            maskClosable={false}
            confirmLoading={loadingData}
            cancelLoading={loadingData}
          >
            <Form
              getFormApi={getFormApi}
              onValueChange={(values) => onValueChange(values)}
            >
              <Form.Input
                field="roleName"
                label="角色名称"
                style={{ width: "95%" }}
              />
              <Form.Input
                field="roleKey"
                label="权限字符"
                style={{ width: "95%" }}
              />
              <Form.Select
                label="默认角色"
                field="isDefault"
                placeholder="默认角色"
                style={{ width: "95%" }}
                initValue={0}
                optionList={defaultSelctlist}
              />
              <Form.InputNumber
                field="roleSort"
                label="排序"
                type="number"
                max={9999}
                min={0}
                initValue={0}
                style={{ width: "95%" }}
              />
              <Form.Select
                label="开启状态"
                field="status"
                placeholder="状态"
                style={{ width: "95%" }}
                initValue={0}
                optionList={statuSelctlist}
              />

              <Form.TextArea
                label="备注"
                field="remark"
                placeholder="简介"
                style={{ width: "95%" }}
              />
            </Form>
          </Modal>

          <Modal
            title="编辑角色"
            visible={editDataShow}
            onOk={editDataOk}
            onCancel={editDataCancel}
            maskClosable={false}
            confirmLoading={loadingData}
            cancelLoading={loadingData}
          >
            <Form
              getFormApi={getFormApi}
              onValueChange={(values) => onValueChange(values)}
            >
              <Form.Input
                field="roleName"
                label="角色名称"
                style={{ width: "95%" }}
              />
              <Form.Input
                field="roleKey"
                label="权限字符"
                style={{ width: "95%" }}
              />
              <Form.Select
                label="默认角色"
                field="isDefault"
                placeholder="默认角色"
                style={{ width: "95%" }}
                optionList={defaultSelctlist}
              />
              <Form.InputNumber
                field="roleSort"
                label="排序"
                type="number"
                max={9999}
                min={0}
                style={{ width: "95%" }}
              />
              <Form.Select
                label="开启状态"
                field="status"
                placeholder="状态"
                style={{ width: "95%" }}
                optionList={statuSelctlist}
              ></Form.Select>
              <Form.TextArea
                label="备注"
                field="remark"
                placeholder="简介"
                style={{ width: "95%" }}
              />
            </Form>
          </Modal>

          <Modal
            title="授权"
            visible={authMenuShow}
            onOk={authMenuOk}
            onCancel={authMenuCancel}
            maskClosable={false}
            confirmLoading={loadingData}
            cancelLoading={loadingData}
          >
              <Tree
                style={{ width: "95%" }}
                treeData={treeSelectList}
                multiple={true}
                leafOnly={false}
                value={roleAuthMenuIds}
                onChange={setRoleAuthMenuIds}
                autoExpandParent={true}
                checkRelation={'unRelated'}
              />
          </Modal>
        </>
      ) : (
        ""
      )}
    </Spin>
  );
};

export default observer(RoleManager);
