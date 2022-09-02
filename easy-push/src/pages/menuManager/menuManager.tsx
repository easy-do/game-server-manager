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
  Spin,
  Switch,
  Table,
  Typography,
} from "@douyinfe/semi-ui";
import { observer } from "mobx-react";
import { useEffect } from "react";
import { iconKeyList, iocnMap } from "../../utils/iconUtil";
import useStores from "../../utils/store";



const MenuManager = () => {
  const { Text } = Typography;
  const { OauthStore, MenuManagerStore } = useStores();
  const { loginFlag } = OauthStore;
  const {
    listRequest,
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
    treeSelectList,
  } = MenuManagerStore;

  const statuSelctlist = [
    { value: 0, label: "开启", otherKey: 0 },
    { value: 1, label: "禁用", otherKey: 1 },
  ];

  const visibleSelctlist = [
    { value: 0, label: "显示", otherKey: 0 },
    { value: 1, label: "隐藏", otherKey: 1 },
  ];

  const cacheSelctlist = [
    { value: 0, label: "缓存", otherKey: 0 },
    { value: 1, label: "不缓存", otherKey: 1 },
  ];

  const isFrameSelctlist = [
    { value: 0, label: "是", otherKey: 0 },
    { value: 1, label: "否", otherKey: 1 },
  ];
  const menuTypeSelctlist = [
    { value: "M", label: "目录", otherKey: 0 },
    { value: "C", label: "菜单", otherKey: 0 },
    { value: "F", label: "按钮", otherKey: 0 }
  ];

  //页面渲染执行
  useEffect(() => {
    if (loginFlag) {
      listRequest();
    }
  }, []);


  


  const columns = [
    {
      title: "菜单",
      render: (text: any, record: any) => {
        return <Text icon={iocnMap.get(record.details.icon)}>
        {record.details.menuName}
        </Text>
      }
    },
    {
        title: "菜单类型",
        render: (text: any, record: any) => {
        switch (record.details.menuType) {
          case "M":
            return '目录';
            case "C":
                return '菜单';
                case "F":
                    return '按钮';          
                    case "T":
                    return '顶部'; 
          default:
            return <Text style={{ color: "red" }}>未知</Text>;
        }
      }
    },
    {
      title: "排序",
      dataIndex: "details.orderNum",
    },
    {
      title: "开关",
      render: (text: any, record: any) => {
        switch (record.details.status) {
          case 0:
            return (
              <Switch
                defaultChecked={true}
                onChange={() => changeStatusRequest(record.details.menuId, 1)}
                aria-label="a switch for semi demo"
              ></Switch>
            );
          case 1:
            return (
              <Switch
                defaultChecked={false}
                onChange={() => changeStatusRequest(record.details.menuId, 0)}
                aria-label="a switch for semi demo"
              ></Switch>
            );
          default:
            return <Text style={{ color: "red" }}>未知</Text>;
        }
      },
    },
    // {
    //   title: "是否隐藏",
    //   render: (text: any, record: any) => {
    //     switch (record.details.status) {
    //       case 0:
    //         return (
    //           <Switch
    //             defaultChecked={false}
    //             onChange={() => changeStatusRequest(record.details.menuId, 0)}
    //             aria-label="a switch for semi demo"
    //           ></Switch>
    //         );
    //       case 1:
    //         return (
    //           <Switch
    //             defaultChecked={true}
    //             onChange={() => changeStatusRequest(record.details.menuId, 1)}
    //             aria-label="a switch for semi demo"
    //           ></Switch>
    //         );
    //       default:
    //         return <Text style={{ color: "red" }}>未知</Text>;
    //     }
    //   },
    // },
    {
      title: "创建时间",
      dataIndex: "details.createTime",
    },
    {
      title: "操作",
      dataIndex: "operate",
      render: (text: any, record: any) => {
        return (
          <ButtonGroup theme="borderless">
            <Button onClick={() => onClickEditButton(record.details.menuId)}>
              编辑
            </Button>
            <Button
              type="danger"
              onClick={() => onClickDeleteButton(record.details.menuId)}
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
                        label="菜单名"
                        field="menuName"
                        style={{ width: "90%" }}
                        showClear
                      ></Form.Input>
                    </Col>
                    <Col span={4}>
                      <Form.Input
                        label="菜单id"
                        field="menuId"
                        style={{ width: "90%" }}
                        showClear
                      ></Form.Input>
                    </Col>
                    <Col span={4}>
                      <Form.Select
                        label="菜单状态"
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
                  <Button onClick={listRequest}>刷新</Button>
                  <Button onClick={onClickAddButton}>添加</Button>
                </ButtonGroup>
              </>
            }
            columns={columns}
            dataSource={dataList}
            rowKey={"details.menuId"}
            pagination={false}
            rowExpandable={(record) =>
                record.children !== undefined && record.children.length !== 0
              }
          />

          <Modal
            title="添加菜单"
            visible={addDataShow}
            onOk={addDataOk}
            onCancel={addDataCancel}
            maskClosable={false}
            confirmLoading={loadingData}
            cancelLoading={loadingData}
            height={600}
            // fullScreen
            bodyStyle={{ overflow: "auto" }}
          >
            <Form
              getFormApi={getFormApi}
              onValueChange={(values) => onValueChange(values)}
            >
              <Form.Input
                field="menuName"
                label="菜单名称"
                style={{ width: "95%" }}
              />
              <Form.TreeSelect
                label="父菜单"
                style={{ width: "95%" }}
                field="parentId"
                initValue={0}
                treeData={treeSelectList}
                multiple={false}
              />
              <Form.Select
                label="菜单类型"
                field="menuType"
                placeholder="状态"
                style={{ width: "95%" }}
                initValue={"C"}
                optionList={menuTypeSelctlist}
              ></Form.Select>
              <Form.Select
                field="icon"
                label="图标"
                style={{ width: "95%" }}
              >
               {iconKeyList}
              </Form.Select>
              <Form.Input
                field="perms"
                label="权限字符"
                style={{ width: "95%" }}
              />
              <Form.Input
                field="path"
                label="路径"
                initValue={""}
                style={{ width: "95%" }}
              />
              <Form.Input
                field="component"
                label="组件"
                initValue={""}
                style={{ width: "95%" }}
              />
              <Form.Input
                field="query"
                label="参数"
                initValue={""}
                style={{ width: "95%" }}
              />
              <Form.InputNumber
                field="orderNum"
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
              >
                <Form.Select
                  label="是否隐藏"
                  field="visible"
                  placeholder="状态"
                  style={{ width: "95%" }}
                  initValue={0}
                  optionList={visibleSelctlist}
                ></Form.Select>
              </Form.Select>
              <Form.Select
                label="是否缓存"
                field="isCache"
                placeholder="状态"
                style={{ width: "95%" }}
                initValue={0}
                optionList={cacheSelctlist}
              ></Form.Select>
              <Form.Select
                label="是否外链"
                field="isFrame"
                placeholder="状态"
                style={{ width: "95%" }}
                initValue={1}
                optionList={isFrameSelctlist}
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
            title="编辑菜单"
            visible={editDataShow}
            onOk={editDataOk}
            onCancel={editDataCancel}
            maskClosable={false}
            height={600}
            confirmLoading={loadingData}
            cancelLoading={loadingData}
            // fullScreen
            bodyStyle={{ overflow: "auto" }}
          >
            <Form
              getFormApi={getFormApi}
              onValueChange={(values) => onValueChange(values)}
            >
              <Form.Input
                field="menuName"
                label="菜单名称"
                style={{ width: "95%" }}
              />
              <Form.TreeSelect
                label="父菜单"
                style={{ width: "95%" }}
                field="parentId"
                treeData={treeSelectList}
                multiple={false}
              />
              <Form.Select
                label="菜单类型"
                field="menuType"
                placeholder="状态"
                style={{ width: "95%" }}
                optionList={menuTypeSelctlist}
              ></Form.Select>
              <Form.Select
                field="icon"
                label="图标"
                style={{ width: "95%" }}
              >
               {iconKeyList}
              </Form.Select>
              <Form.Input
                field="perms"
                label="权限字符"
                style={{ width: "95%" }}
              />
              <Form.Input
                field="path"
                label="路径"
                style={{ width: "95%" }}
              />
              <Form.Input
                field="component"
                label="组件"
                style={{ width: "95%" }}
              />
              <Form.Input
                field="query"
                label="参数"
                style={{ width: "95%" }}
              />
              <Form.InputNumber
                field="orderNum"
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
              >
                <Form.Select
                  label="是否隐藏"
                  field="visible"
                  placeholder="状态"
                  style={{ width: "95%" }}
                  optionList={visibleSelctlist}
                ></Form.Select>
              </Form.Select>
              <Form.Select
                label="是否缓存"
                field="isCache"
                placeholder="状态"
                style={{ width: "95%" }}
                optionList={cacheSelctlist}
              ></Form.Select>
              <Form.Select
                label="是否外链"
                field="isFrame"
                placeholder="状态"
                style={{ width: "95%" }}
                optionList={isFrameSelctlist}
              ></Form.Select>
              <Form.TextArea
                label="备注"
                field="remark"
                placeholder="简介"
                style={{ width: "95%" }}
              />
            </Form>
          </Modal>
        </>
      ) : (
        ""
      )}
    </Spin>
  );
};

export default observer(MenuManager);
