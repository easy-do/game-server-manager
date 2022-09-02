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
import useStores from "../../utils/store";
import DictDataSelect from "./dictDataSelect";

const DictData = (props: any) => {
  const { dictTypeId } = props;
  const { Text } = Typography;
  const { OauthStore, DictDataStore } = useStores();
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
  } = DictDataStore;

  const isDefaultSelctlist = [
    { value: 0, label: "否", otherKey: 0 },
    { value: 1, label: "是", otherKey: 1 },
  ];

  //页面渲染执行
  useEffect(() => {
    if (loginFlag) {
      pageRequest(dictTypeId);
    }
  }, [dictTypeId]);

  const columns = [
    {
      title: "编码",
      dataIndex: "id",
    },
    {
      title: "显示标签",
      dataIndex: "dictLabel",
    },
    {
      title: "key",
      dataIndex: "dictKey",
    },
    {
      title: "value",
      dataIndex: "dictValue",
    },
    {
      title: "参数类型",
      dataIndex: "dictValueType",
    },
    {
      title: "排序",
      dataIndex: "dictSort",
    },
    {
      title: "开启状态",
      render: (text: any, record: any) => {
        switch (record.status) {
          case 0:
            return (
              <Switch
                defaultChecked={true}
                onChange={() => changeStatusRequest(record.id, 1)}
                aria-label="a switch for semi demo"
              ></Switch>
            );
          case 1:
            return (
              <Switch
                defaultChecked={false}
                onChange={() => changeStatusRequest(record.id, 0)}
                aria-label="a switch for semi demo"
              ></Switch>
            );
          default:
            return <Text style={{ color: "red" }}>未知</Text>;
        }
      },
    },
    {
      title: "是否默认",
      render: (text: any, record: any) => {
        switch (record.isDefault) {
          case 0:
            return <Text style={{ color: "red" }}>否</Text>;
          case 1:
            return <Text style={{ color: "green" }}>是</Text>;
          default:
            return <Text style={{ color: "red" }}>未知</Text>;
        }
      },
    },
    {
      title: "备注",
      dataIndex: "remark",
    },
    {
      title: "操作",
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
                        label="主键"
                        field="id"
                        style={{ width: "90%" }}
                        showClear
                      ></Form.Input>
                    </Col>
                    <Col span={4}>
                      <Form.Input
                        label="key"
                        field="dictKey"
                        style={{ width: "90%" }}
                        showClear
                      ></Form.Input>
                    </Col>
                    <Col span={4}>
                      <DictDataSelect
                        dictCode="status_select"
                        label="状态"
                        field="status"
                        placeholder="状态"
                        style={{ width: "95%" }}
                      />
                    </Col>
                  </Row>
                </Form>
                <ButtonGroup theme="borderless">
                  <Button onClick={searchButton}>搜索</Button>
                  <Button onClick={cleanSearchform}>重置</Button>
                  <Button onClick={() => pageRequest(dictTypeId)}>刷新</Button>
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

          <Modal
            title="添加数据"
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
                field="dictLabel"
                label="显示标签"
                style={{ width: "95%" }}
              />
              <Form.Input
                field="dictKey"
                label="key"
                style={{ width: "95%" }}
              />
              <Form.Input
                field="dictValue"
                label="value"
                style={{ width: "95%" }}
              />
                <DictDataSelect
                dictCode="dict_value_type"
                label="参数类型"
                field="dictValueType"
                placeholder="参数类型"
                initValue={'str'}
                style={{ width: "95%" }}
              />
              <Form.InputNumber
                field="dictSort"
                label="排序"
                type="number"
                max={9999}
                min={0}
                initValue={0}
                style={{ width: "95%" }}
              />
              <Form.Select
                label="是否默认"
                field="isDefault"
                placeholder="状态"
                style={{ width: "95%" }}
                initValue={0}
                optionList={isDefaultSelctlist}
              ></Form.Select>
              <DictDataSelect
                dictCode="status_select"
                label="状态"
                field="status"
                placeholder="状态"
                initValue={0}
                style={{ width: "95%" }}
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
            title="编辑数据"
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
                field="dictLabel"
                label="显示标签"
                style={{ width: "95%" }}
              />
              <Form.Input
                field="dictKey"
                label="key"
                style={{ width: "95%" }}
              />
              <Form.Input
                field="dictValue"
                label="value"
                style={{ width: "95%" }}
              />
              <DictDataSelect
                dictCode="dict_value_type"
                label="参数类型"
                field="dictValueType"
                placeholder="参数类型"
                style={{ width: "95%" }}
              />
              <Form.InputNumber
                field="dictSort"
                label="排序"
                type="number"
                max={9999}
                min={0}
                initValue={0}
                style={{ width: "95%" }}
              />
              <Form.Select
                label="是否默认"
                field="isDefault"
                placeholder="状态"
                style={{ width: "95%" }}
                initValue={0}
                optionList={isDefaultSelctlist}
              ></Form.Select>
              <DictDataSelect
                dictCode="status_select"
                label="状态"
                field="status"
                placeholder="状态"
                style={{ width: "95%" }}
              />
              <Form.TextArea
                label="备注"
                field="remark"
                placeholder="简介"
                style={{ width: "95%" }}
              />
            </Form>
          </Modal>
        </>
      ) : null}
    </Spin>
  );
};

export default observer(DictData);
