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
import {
  IconMinusCircle,
  IconPlusCircle,
} from "@douyinfe/semi-icons";
import { useEffect } from "react";
import React from "react";
import Section from "@douyinfe/semi-ui/lib/es/form/section";
import MEditor from "../mEdit/mEdit";

const AppScript = () => {
  const { OauthStore, AppScriptStore, AppInfoStore, MEditStore } = useStores();

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
    onClickEditScriptButton,
    editScriptShow,
    editScriptOk,
    editScriptCancel,
  } = AppScriptStore;

  useEffect(() => {
    if (loginFlag) {
      pageRequest();
      appListRequest();
      basicScriptListRequest();
    }
  }, []);

  //构建APP选择框
  const appListRequest = AppInfoStore.listRequst;
  const appList = AppInfoStore.dataList;
  const appSelectList: any[] = [];
  appSelectList.push({
    value: "no",
    label: "不关联APP",
    otherKey: "no",
  });
  appList.map((item: any, i: number) => {
    appSelectList.push({
      value: item.id + "",
      label: item.appName,
      otherKey: i,
    });
  });

  //构建依赖脚本选择框
  const basicScriptListRequest = AppScriptStore.listRequst;
  const basicScriptList = AppScriptStore.listData;
  const basicScriptSelectList: any[] = [];
  basicScriptList.map((item: any, i: number) => {
    basicScriptSelectList.push({
      value: item.id + "",
      label: item.scriptName,
      otherKey: i,
    });
  });

  MEditStore.value = dataInfo.scriptFile;

  const scripTypeSelctlist = [
    { value: "部署", label: "部署", otherKey: "install" },
    { value: "重启", label: "重启", otherKey: "restart" },
    { value: "停止", label: "停止", otherKey: "stop" },
    { value: "卸载", label: "卸载", otherKey: "uninstall" },
    { value: "安装环境", label: "安装环境", otherKey: "basic" },
    { value: "自定义", label: "自定义", otherKey: "custom" },
  ];

  const scopeSelctlist = [
    { value: "private", label: "私有", otherKey: "install" },
    { value: "public", label: "公开", otherKey: "restart" },
    { value: "subscribe", label: "订阅", otherKey: "stop" },
  ];

  const columns = [
    {
      title: <Tag>脚本名称</Tag>,
      dataIndex: "scriptName",
    },
    {
      title: <Tag>类型</Tag>,
      dataIndex: "scriptType",
      render: (text: string, record: any, index: number) => {
        switch (record.scriptType) {
          case "install":
            return "部署";
          case "restart":
            return "重启";
          case "stop":
            return "停止";
          case "uninstall":
            return "卸载";
          case "basic":
            return "安装环境";
          case "custom":
            return "自定义";
          default:
            return record.scriptType;
        }
      },
    },
    {
      title: <Tag>可见范围</Tag>,
      dataIndex: "scriptScope",
      render: (text: string, record: any, index: number) => {
        switch (record.scriptScope) {
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
      dataIndex: "author",
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
            <Button onClick={() => onClickEditScriptButton(record.id)}>
              编辑脚本
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
        title="添加脚本"
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
          <Section text={"脚本信息"}>
            <Form.Input
              field="scriptName"
              label="脚本名称"
              style={{ width: "95%" }}
            />
            <Form.Select
              label="脚本类型"
              field="scriptType"
              placeholder="脚本类型"
              style={{ width: "95%" }}
              initValue={"部署"}
              optionList={scripTypeSelctlist}
            />
            <Form.Select
              filter
              multiple
              maxTagCount={5}
              label="依赖脚本"
              field="basicScript"
              placeholder="依赖脚本"
              style={{ width: "95%" }}
              optionList={basicScriptSelectList}
            />
            <Form.Select
              filter
              label="可见范围"
              field="scriptScope"
              placeholder="可见范围"
              style={{ width: "95%" }}
              optionList={scopeSelctlist}
            />
            <Form.Select
              filter
              label="适配APP"
              field="adaptationAppId"
              placeholder="适配APP"
              style={{ width: "95%" }}
              optionList={appSelectList}
            />
            <Form.Input label="版本" field="version" style={{ width: "95%" }} />
            <Form.TextArea
              label="描述"
              field="description"
              placeholder="描述"
              style={{ width: "95%" }}
            />
          </Section>
          <Section text={"配置脚本变量"}>
            <ArrayField field="scriptEnv" initValue={[]}>
              {({ add, arrayFields, addWithInitValue }) => (
                <React.Fragment>
                  <Button theme="borderless"
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
                  <Button theme="borderless"
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
        title="编辑脚本信息"
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
          <Section text={"脚本信息"}>
            <Form.Input
              field="scriptName"
              label="脚本名称"
              style={{ width: "95%" }}
            />
            <Form.Select
              label="脚本类型"
              field="scriptType"
              placeholder="脚本类型"
              style={{ width: "95%" }}
              optionList={scripTypeSelctlist}
            />
            <Form.Select
              filter
              multiple
              maxTagCount={5}
              label="依赖脚本"
              field="basicScript"
              placeholder="依赖脚本"
              style={{ width: "95%" }}
              optionList={basicScriptSelectList}
            />
            <Form.Select
              filter
              label="可见范围"
              field="scriptScope"
              placeholder="可见范围"
              style={{ width: "95%" }}
              optionList={scopeSelctlist}
            />
            <Form.Select
              filter
              label="适配APP"
              field="adaptationAppId"
              placeholder="适配APP"
              style={{ width: "95%" }}
              optionList={appSelectList}
            />
            <Form.Input label="版本" field="version" style={{ width: "95%" }} />
            <Form.TextArea
              label="描述"
              field="description"
              placeholder="描述"
              style={{ width: "95%" }}
            />
          </Section>
          <Section text={"配置脚本变量"}>
            <ArrayField field="scriptEnv" initValue={dataInfo.scroptenv}>
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
        title="编辑脚本信息"
        visible={editScriptShow}
        onOk={() => editScriptOk(MEditStore.value)}
        onCancel={editScriptCancel}
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

export default observer(AppScript);
