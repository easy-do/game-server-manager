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
import DictData from "./dictData";
import DictDataSelect from "./dictDataSelect";

const DictType = (props: any) => {
  const { Text } = Typography;
  const { OauthStore, DictTypeStore } = useStores();
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
    dictDataModalButton,
    dictDataModalShow,
    dictDataModaOk,
    dictDataModaCancel,
    dictTypeId
  } = DictTypeStore;

  //页面渲染执行
  useEffect(() => {
    if (loginFlag) {
      pageRequest();
    }
  }, []);

  const columns = [
    {
      title: "字典编码",
      dataIndex: "dictCode",
    },
    {
      title: "字典名称",
      render: (text: any, record: any) => {
        return <Text onClick={()=>dictDataModalButton(record.id)} style={{color:'blue'}}>{record.dictName}</Text>
      },
      
    },
    {
      title: "启用",
      render: (text: any, record: any) => {
        switch (record.status) {
          case 0:
            return (
              <Switch
                defaultChecked={true}
                onChange={() => changeStatusRequest(record.id, 1)}
              ></Switch>
            );
          case 1:
            return (
              <Switch
                defaultChecked={false}
                onChange={() => changeStatusRequest(record.id, 0)}
              ></Switch>
            );
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
                        label="字典名"
                        field="dictName"
                        style={{ width: "90%" }}
                        showClear
                      ></Form.Input>
                    </Col>
                    <Col span={4}>
                      <Form.Input
                        label="编码"
                        field="dictCode"
                        style={{ width: "90%" }}
                        showClear
                      ></Form.Input>
                    </Col>
                    <Col span={4}>
                      <Form.Input
                        label="字典id"
                        field="id"
                        style={{ width: "90%" }}
                        showClear
                      ></Form.Input>
                    </Col>
                    <Col span={4}>
                    <DictDataSelect
                        dictCode="status_select"
                        label="字典状态"
                        field="status"
                        placeholder=""
                        style={{ width: "90%" }}
                      />
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

          <Modal
            title="添加字典"
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
                field="dictName"
                label="字典名称"
                style={{ width: "95%" }}
              />
              <Form.Input
                field="dictCode"
                label="字典编码"
                style={{ width: "95%" }}
              />
                      <DictDataSelect
                        dictCode="status_select"
                        label="状态"
                        field="status"
                        placeholder="状态"
                        initvalue={0}
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
            title="编辑字典"
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
                field="dictName"
                label="字典名称"
                style={{ width: "95%" }}
              />
              <Form.Input
                field="dictCode"
                label="字典编码"
                style={{ width: "95%" }}
              />
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


          <Modal
            title="字典数据"
            fullScreen
            visible={dictDataModalShow}
            onOk={dictDataModaOk}
            onCancel={dictDataModaCancel}
            maskClosable={false}
            confirmLoading={loadingData}
            cancelLoading={loadingData}
          >
            <DictData dictTypeId={dictTypeId}/>
          </Modal>
        </>
      ) : null}
    </Spin>
  );
};

export default observer(DictType);
