import {
  IllustrationNoContent,
  IllustrationConstructionDark,
} from "@douyinfe/semi-illustrations";
import {
  ArrayField,
  Button,
  ButtonGroup,
  Empty,
  Form,
  Modal,
  Space,
  Spin,
  Table,
  Tag,
} from "@douyinfe/semi-ui";
import { observer } from "mobx-react";
import useStores from "../../utils/store";
import { IconMinusCircle, IconPlusCircle } from "@douyinfe/semi-icons";
import { useEffect } from "react";
import React from "react";
import Section from "@douyinfe/semi-ui/lib/es/form/section";
import MEditor from "../mEdit/mEdit";

const TemplateManager = () => {
  const { OauthStore, TemplateStore, MEditStore } = useStores();

  const { loginFlag } = OauthStore;

  const {
    currentPage,
    pageSize,
    total,
    pageRequest,
    onPageChange,
    onPageSizeChange,
    dataList,
    dataInfo,
    getEditFormApi,
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
    loadingData,
    onClickDeleteButton,
    onClickEditTemplateButton,
    editTemplateShow,
    editTemplateOk,
    editTemplateCancel,
  } = TemplateStore;

  useEffect(() => {
    if (loginFlag) {
      pageRequest();
    }
  }, []);

  MEditStore.value = dataInfo.templateCode;

  const typeSelctlist = [
    { value: "通用", label: "通用", otherKey: "basic" },
    { value: "java", label: "java", otherKey: "java" },
    { value: "Dockerfile", label: "Dockerfile", otherKey: "Dockerfile" },
    { value: "shell", label: "shell", otherKey: "shell" },
    { value: "其他", label: "其他", otherKey: "orther" },
  ];

  const scopeSelctlist = [
    { value: "private", label: "私有", otherKey: "install" },
    { value: "public", label: "公开", otherKey: "restart" },
    { value: "subscribe", label: "订阅", otherKey: "stop" },
  ];

  const columns = [
    {
      title: <Tag>模板名称</Tag>,
      dataIndex: "templateName",
    },
    {
      title: <Tag>类型</Tag>,
      dataIndex: "templateType",
    },
    {
      title: <Tag>可见范围</Tag>,
      dataIndex: "templateScope",
      render: (text: string, record: any, index: number) => {
        switch (record.templateScope) {
          case "private":
            return "私有";
          case "public":
            return "公开";
          case "subscribe":
            return "订阅";
        }
      },
    },
    {
      title: <Tag>作者</Tag>,
      dataIndex: "createName",
    },
    {
      title: <Tag>版本</Tag>,
      dataIndex: "version",
    },
    {
      title: <Tag>描述</Tag>,
      dataIndex: "description",
    },
    {
      title: <Tag>操作</Tag>,
      dataIndex: "operate",
      render: (text: any, record: any) => {
        return (
          <ButtonGroup theme="borderless">
            <Button onClick={() => onClickEditButton(record.id)}>
              编辑信息
            </Button>
            <Button onClick={() => onClickEditTemplateButton(record.id)}>
              编辑模板
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
        title={"未登录或无权限。"}
        description="未登录或无权限"
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
        title="添加模板"
        visible={addDataShow}
        onOk={addDataOk}
        onCancel={addDataCancel}
        maskClosable={false}
        width={1200}
        confirmLoading={loadingData}
        cancelLoading={loadingData}
      >
        <Form
          getFormApi={getAddFormApi}
          onValueChange={(values) => onValueChange(values)}
        >
          <Section text={"模板信息"}>
            <Form.Input
              field="templateName"
              label="模板名称"
              style={{ width: "95%" }}
            />
            <Form.Select
              label="类型"
              field="templateType"
              placeholder="类型"
              style={{ width: "95%" }}
              initValue={"通用"}
              optionList={typeSelctlist}
            />
            <Form.Select
              filter
              label="可见范围"
              field="templateScope"
              placeholder="可见范围"
              style={{ width: "95%" }}
              optionList={scopeSelctlist}
            />
            <Form.Input
              field="fileName"
              label="文件名"
              style={{ width: "95%" }}
            />
            <Form.Input
              field="filePath"
              label="生成路径"
              style={{ width: "95%" }}
            />
            <Form.Input label="版本" field="version" style={{ width: "95%" }} />
            <Form.TextArea
              label="描述"
              field="description"
              placeholder="描述"
              style={{ width: "95%" }}
            />
          </Section>
          <Section text={"配置模板变量"}>
            <ArrayField field="templateEnv" initValue={[]}>
              {({ add, arrayFields, addWithInitValue }) => (
                <React.Fragment>
                  <Button
                    theme="borderless"
                    icon={<IconPlusCircle />}
                    onClick={() => {
                      addWithInitValue({
                        envName: "",
                        envKey: "",
                        envValue: "",
                        description: "填写参数时会提示",
                        envType: "text",
                      });
                    }}
                    style={{ marginLeft: 8 }}
                  >
                    添加文本变量
                  </Button>
                  <Button
                    theme="borderless"
                    icon={<IconPlusCircle />}
                    onClick={() => {
                      addWithInitValue({
                        envName: "",
                        envKey: "",
                        envValue: "1",
                        description: "填写参数时会提示",
                        envType: "boolean",
                      });
                    }}
                    style={{ marginLeft: 8 }}
                  >
                    添加单选变量
                  </Button>
                  {arrayFields.map(({ field, key, remove }, i) => (
                    <div key={key} style={{ width: 200, display: "flex" }}>
                      <Space>
                        <Form.Input
                          field={`${field}[envName]`}
                          label={"变量名"}
                          style={{ width: 200, marginRight: 16 }}
                        />
                        <Form.Input
                          field={`${field}[envKey]`}
                          label={"变量key"}
                          style={{ width: 200, marginRight: 16 }}
                        />
                        <Form.Input
                          field={`${field}[envValue]`}
                          label={`默认值`}
                          style={{ width: 200, marginRight: 16 }}
                        />
                        <Form.Input
                          field={`${field}[description]`}
                          label={"提示"}
                          style={{ width: 200, marginRight: 16 }}
                        />
                        <Form.Input
                          field={`${field}[envType]`}
                          label={"变量类型"}
                          style={{ width: 200, marginRight: 16 }}
                          disabled
                        />
                        <Button
                          type="danger"
                          theme="borderless"
                          icon={<IconMinusCircle />}
                          onClick={remove}
                          style={{ margin: 12 }}
                        ></Button>
                      </Space>
                    </div>
                  ))}
                </React.Fragment>
              )}
            </ArrayField>
          </Section>
        </Form>
      </Modal>
      <Modal
        title="编辑模板信息"
        visible={editDataShow}
        onOk={editDataOk}
        onCancel={editDataCancel}
        maskClosable={false}
        width={1200}
        confirmLoading={loadingData}
        cancelLoading={loadingData}
      >
        <Form
          getFormApi={getEditFormApi}
          onValueChange={(values) => onValueChange(values)}
        >
          <Section text={"模板信息"}>
            <Form.Input
              field="templateName"
              label="模板名称"
              style={{ width: "95%" }}
            />
            <Form.Select
              label="类型"
              field="templateType"
              placeholder="类型"
              style={{ width: "95%" }}
              initValue={"通用"}
              optionList={typeSelctlist}
            />
            <Form.Select
              filter
              label="可见范围"
              field="templateScope"
              placeholder="可见范围"
              style={{ width: "95%" }}
              optionList={scopeSelctlist}
            />
            <Form.Input
              field="fileName"
              label="文件名"
              style={{ width: "95%" }}
            />
            <Form.Input
              field="filePath"
              label="生成路径"
              style={{ width: "95%" }}
            />
            <Form.Input label="版本" field="version" style={{ width: "95%" }} />
            <Form.TextArea
              label="描述"
              field="description"
              placeholder="描述"
              style={{ width: "95%" }}
            />
          </Section>
          <Section text={"配置模板变量"}>
            <ArrayField field="templateEnv" initValue={dataInfo.scroptenv}>
              {({ add, arrayFields, addWithInitValue }) => (
                <React.Fragment>
                  <Button
                    icon={<IconPlusCircle />}
                    onClick={() => {
                      addWithInitValue({
                        envName: "",
                        envKey: "",
                        envValue: "",
                        description: "填写参数时会提示",
                        envType: "text",
                      });
                    }}
                    style={{ marginLeft: 8 }}
                  >
                    添加文本变量
                  </Button>
                  <Button
                    icon={<IconPlusCircle />}
                    onClick={() => {
                      addWithInitValue({
                        envName: "",
                        envKey: "",
                        envValue: "1",
                        description: "填写参数时会提示",
                        envType: "boolean",
                      });
                    }}
                    style={{ marginLeft: 8 }}
                  >
                    添加单选变量
                  </Button>
                  {arrayFields.map(({ field, key, remove }, i) => (
                    <div key={key} style={{ width: 95, display: "flex" }}>
                      <Space>
                        <Form.Input
                          field={`${field}[envName]`}
                          label={"变量名"}
                          style={{ width: 200, marginRight: 16 }}
                        />
                        <Form.Input
                          field={`${field}[envKey]`}
                          label={"变量key"}
                          style={{ width: 200, marginRight: 16 }}
                        />
                        <Form.Input
                          field={`${field}[envValue]`}
                          label={`默认值`}
                          style={{ width: 200, marginRight: 16 }}
                        />
                        <Form.Input
                          field={`${field}[description]`}
                          label={"提示"}
                          style={{ width: 200, marginRight: 16 }}
                        />
                        <Form.Input
                          field={`${field}[envType]`}
                          label={"变量类型"}
                          style={{ width: 200, marginRight: 16 }}
                          disabled
                        />
                        <Button
                          type="danger"
                          theme="borderless"
                          icon={<IconMinusCircle />}
                          onClick={remove}
                          style={{ margin: 12 }}
                        ></Button>
                      </Space>
                    </div>
                  ))}
                </React.Fragment>
              )}
            </ArrayField>
          </Section>
        </Form>
      </Modal>
      <Modal
        title="编辑模板信息"
        visible={editTemplateShow}
        onOk={() => editTemplateOk(MEditStore.value)}
        onCancel={editTemplateCancel}
        maskClosable={false}
        fullScreen
        confirmLoading={loadingData}
        cancelLoading={loadingData}
      >
        <MEditor />
      </Modal>
    </Spin>
  );
};

export default observer(TemplateManager);
