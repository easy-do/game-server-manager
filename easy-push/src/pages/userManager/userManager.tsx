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
  Select,
  SideSheet,
  Spin,
  Switch,
  Table,
  Typography,
} from "@douyinfe/semi-ui";
import { observer } from "mobx-react";
import { useEffect } from "react";
import useStores from "../../utils/store";
import UserPoints from "../userPoints/userPoints";
import UserManagerInfo from "./userManagerInfo";

const UserManager = () => {
  const { Text } = Typography;
  const { OauthStore, UserManagerStore,RoleManagerStore } = useStores();
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
    editRoleButton,
    editRoleShow,
    setUserRole,
    editRoleOk,
    editRoleCancel,
    userRolersIds,
    userPointsOperationButton,
    userPointsOperationShow,
    userPointsOperationOk,
    userPointsOperationCancel,
    userPointsOperationsShow,
    userPointsOperationsButton,
    userPointsOperationsOk,
    userPointsOperationsCancel,
    userPointsOperationFormOnChage,
    userHandleSearch,
    selectUserList,
    getUserPointsOperationsFormApi,
    showPointsButton,
    showPointsModal,
    showPointsCancel,
    showPointsUserid,

  } = UserManagerStore;

const {selectList, selectListRequest} = RoleManagerStore;

let roleSelectData = new Array<any>(); 

selectList.map((item: any, i: number) => {
  roleSelectData.push(
    {
      key: i,
      label: item.roleName,
      value: item.roleId
    } 
  )
});



  //页面渲染执行
  useEffect(() => {
    if (loginFlag) {
      pageRequest();
      selectListRequest();
    }
  }, []);

  const columns = [
    {
      title: "用户名",
      dataIndex: "nickName",
    },
    {
      title: "平台",
      dataIndex: "platform",
    },
    {
      title: "启用",
      render: (text: any, record: any) => {
        switch (record.state) {
          case 0:
            return<Switch defaultChecked={true} onChange={()=>changeStatusRequest(record.id,1)} aria-label="a switch for semi demo"></Switch>
          case 1:
            return<Switch defaultChecked={false} onChange={()=>changeStatusRequest(record.id,0)} aria-label="a switch for semi demo"></Switch>
          default:
            return <Text style={{ color: "red" }}>未知</Text>;
        }
      },
    },
    {
      title: "授权到期时间",
      render: (text: any, record: any) => {
        if (record.authorization) {
          let authorizationInfo = record.authorization
            ? JSON.parse(record.authorization)
            : record.authorization;
          return (
            <Text style={{ color: "green" }}>{authorizationInfo.expires}</Text>
          );
        } else {
          return <Text style={{ color: "red" }}>未授权</Text>;
        }
      },
    },
    {
      title: "登录ip",
      dataIndex: "loginIp",
    },
    {
      title: "登录时间",
      dataIndex: "lastLoginTime",
    },
    {
      title: "注册时间",
      dataIndex: "createTime",
    },
    {
      title: "操作",
      dataIndex: "operate",
      render: (text: any, record: any) => {
        return (
          <ButtonGroup theme="borderless">
             <Button
              onClick={() => dataInfoButton(record.id)}
            >
              详情
            </Button>
            <Button
              onClick={() => editRoleButton(record.id)}
            >
              关联角色
            </Button>
            <Button
              onClick={() => userPointsOperationButton(record.id)}
            >
              操作积分
            </Button>
            <Button
              onClick={() => showPointsButton(record.id)}
            >
              积分记录
            </Button>
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
        <>
          <Popconfirm
            visible={deletecConfirmShow}
            title="删除确认"
            content="确定删除？"
            onConfirm={deletecConfirmOK}
            onCancel={deletecConfirmCancel}
          >
          </Popconfirm>
        <Table
          title={<>
            <Form
              labelPosition="left"
              labelAlign="left"
              style={{ padding: "10px" }}
              getFormApi={getSerchFormapi}
              onValueChange={serchFormValueChange}
            >
              <Row>
                <Col span={4}>
                  <Form.Input label="用户名" field="nickName" style={{ width: "90%" }} showClear></Form.Input>
                </Col>
                <Col span={4}>
                  <Form.Input label="用户id" field="id" style={{ width: "90%" }} showClear></Form.Input>
                </Col>
                <Col span={4}>
                  <Form.Select label="账户状态" field="state" style={{ width: "90%" }} showClear>
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
              <Button onClick={userPointsOperationsButton}>批量操作积分</Button>
            </ButtonGroup>
          </>}
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
          }} />
             <SideSheet title="用户详情" visible={dataInfoShow} onCancel={dataInfoHiden}>
                <UserManagerInfo dataInfo={dataInfo} />
            </SideSheet>
            <Modal
                     title="关联角色"
                     visible={editRoleShow}
                     onOk={editRoleOk}
                     onCancel={editRoleCancel}
                     maskClosable={false}
                     confirmLoading={loadingData}
                     cancelLoading={loadingData}        
            >
                <Select multiple onChange={setUserRole} optionList={roleSelectData} value={userRolersIds} style={{ width: "95%" }}/>
            </Modal>
            <Modal
              title="积分操作"
              visible={userPointsOperationShow}
              onOk={userPointsOperationOk}
              onCancel={userPointsOperationCancel}
              maskClosable={false}
            >
              <Form
                onValueChange={(values) => userPointsOperationFormOnChage(values)}
              >
                <Form.InputNumber
                  field="points"
                  label="积分"
                  style={{ width: "90%" }}
                  type='number'
                  initValue={10}
                />
                <Form.Input
                  field="description"
                  label="操作描述"
                  style={{ width: "90%" }}
                  initValue={'无'}
                />
              </Form>
          </Modal>

          <Modal
              title="积分批量操作"
              visible={userPointsOperationsShow}
              onOk={userPointsOperationsOk}
              onCancel={userPointsOperationsCancel}
              maskClosable={false}

            >
              <Form
                getFormApi={getUserPointsOperationsFormApi}
              >
               <Form.Select 
               label="用户" 
               field="userId" 
               style={{ width: "90%" }} 
               showClear
               remote
               filter
               multiple
               onSearch={userHandleSearch}
               optionList={selectUserList}
               loading={loadingData}
               />
                <Form.InputNumber
                  field="points"
                  label="积分"
                  style={{ width: "90%" }}
                  type='number'
                  initValue={10}
                />
                <Form.Input
                  field="description"
                  label="操作描述"
                  style={{ width: "90%" }}
                  initValue={'无'}
                />
              </Form>
          </Modal>

          <Modal
            title="积分记录"
            visible={showPointsModal}
            onOk={showPointsCancel}
            onCancel={showPointsCancel}
            maskClosable={false}
            confirmLoading={loadingData}
            cancelLoading={loadingData}
            width="50%"
          >
            <UserPoints userId={showPointsUserid}/>
          </Modal>
          </>
      ) : (
        ""
      )}
    </Spin>
  );
};

export default observer(UserManager);
