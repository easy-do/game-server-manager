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
  SideSheet,
  Spin,
  Table,
  Tag,
  Tooltip,
} from "@douyinfe/semi-ui";
import { observer } from "mobx-react";
import useStores from "../../utils/store";
import { useEffect } from "react";
import Section from "@douyinfe/semi-ui/lib/es/form/section";
import ExecLog from "../execLog/execLog";
import ApplicationInfo from "./applicationInfo";

const Application = () => {
  const { OauthStore, ApplicationStore, ServerInfoStore, AppInfoStore, ClientInfoStore } =
    useStores();

  const { loginFlag } = OauthStore;

  const {
    currentPage,
    pageSize,
    total,
    pageRequest,
    onPageChange,
    onPageSizeChange,
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
    loadingData,
    onClickDeleteButton,
    onClickDeployButton,
    deployAppShow,
    deployAppOk,
    deployAppCancel,
    getDeployFormApi,
    deployOnValueChange,
    deployLogShow,
    deployLogButton,
    deployLogButtonOkOrCe,
    getScriptEnvList,
    deployEnvs,
    currentApplicationId,
    dataInfoShow,
    dataInfoButton,
    dataInfo,
    dataInfoHidden,
    deviceType,
    changeDeviceType
  } = ApplicationStore;

  const deployEnvMoudle: any[] = [];

  const getPopupContainer: any = () =>
    document.querySelector("#deploy-tooltip-container");

  useEffect(() => {
    if (loginFlag) {
      pageRequest();
      serverListRequest();
      appListRequest();
      clientListRequest();
    }
  }, []);

  deployEnvs.map((item: any, i: number) => {
    {
      item.env.map((envItem: any, evenI: number) => {
        switch (envItem.envType) {
          case "text":
            deployEnvMoudle.push(
              <Tooltip
                key={envItem.envKey}
                content={envItem.description}
                trigger="focus"
                arrowPointAtCenter={false}
                spacing={1}
                getPopupContainer={getPopupContainer}
              >
                <Form.Input
                  key={envItem.envKey}
                  field={envItem.envKey}
                  label={envItem.envName + "<" + item.scriptName + ">"}
                  initValue={envItem.envValue}
                  showClear
                />
              </Tooltip>
            );
            break;
          case "boolean":
            deployEnvMoudle.push(
              <Tooltip
                key={envItem.evenKey}
                content={envItem.description}
                trigger="focus"
                arrowPointAtCenter={false}
                spacing={1}
                getPopupContainer={getPopupContainer}
              >
                <Form.Select
                  key={envItem.evenKey}
                  label={envItem.envName + "<" + item.scriptName + ">"}
                  field={envItem.envKey}
                  placeholder={envItem.envName}
                  style={{ width: 600 }}
                  initValue={1}
                  optionList={[
                    { value: 1, label: "是", otherKey: envItem.evenKey + "1" },
                    { value: 0, label: "否", otherKey: envItem.evenKey + "2" },
                  ]}
                />
              </Tooltip>
            );
            break;
          default:
            break;
        }
      });
    }
  });


  //设备类型
  const deviceSelectList = [
    {
      value: 0,
      label: '服务器',
      otherKey: '0', 
    },
    {
      value: 1,
      label: '客户端',
      otherKey: '1',
    }
  ]

  //构建服务器选择框
  const serverListRequest = ServerInfoStore.listRequst;
  const serverList = ServerInfoStore.dataList;
  const serverSelectList: any[] = [];
  serverList.map((item: any, i: number) => {
    serverSelectList.push({
      value: item.id,
      label: item.serverName,
      otherKey: i,
    });
  });

    //构建客户端选择框
    const clientListRequest = ClientInfoStore.listRequst;
    const clientList = ClientInfoStore.dataList;
    const clientSelectList: any[] = [];
    clientList.map((item: any, i: number) => {
      clientSelectList.push({
        value: item.id,
        label: item.clientName,
        otherKey: i,
      });
    });

  //构建APP选择框
  const appListRequest = AppInfoStore.listRequst;
  const appList = AppInfoStore.dataList;
  const appSelectList: any[] = [];
  appList.map((item: any, i: number) => {
    appSelectList.push({
      value: item.id,
      label: item.appName + "   作者: " + (item.author ? item.author : "未知"),
      otherKey: i,
    });
  });

  //构建脚本选择框
  const scriptList = ApplicationStore.scriptList;
  let scriptSelectList: any[] = [];
  if (scriptList.length > 0) {
    scriptList.map((item: any, i: number) => {
      scriptSelectList.push({
        value: item.id,
        label:
          item.scriptName + "   作者: " + (item.author ? item.author : "未知"),
        otherKey: i,
      });
    });
  }

  const columns = [
    {
      title: <Tag>应用名称</Tag>,
      dataIndex: "applicationName",
    },
    {
      title: <Tag>所属设备</Tag>,
      dataIndex: "deviceName",
    },
    {
      title: <Tag>APP类型</Tag>,
      dataIndex: "appName",
    },
    {
      title: <Tag>应用状态</Tag>,
      dataIndex: "status",
    },
    {
      title: <Tag>最后通信时间</Tag>,
      dataIndex: "lastUpTime",
      render: (text: string, record: any, index: number) => {
        if (record.lastUpTime) {
          return record.lastUpTime;
        } else {
          return "未同步";
        }
      },
    },
    {
      title: <Tag>操作</Tag>,
      dataIndex: "operate",
      render: (text: any, record: any) => {
        return (
          <ButtonGroup theme="borderless">
            <Button onClick={() => dataInfoButton(record.applicationId)}>
              详情信息
            </Button>
            {/* <Button onClick={() => onClickEditButton(record.applicationId)}>
              编辑
            </Button> */}
            <Button
              onClick={() =>
                onClickDeployButton(record.applicationId, record.appId)
              }
            >
              部署
            </Button>
            <Button onClick={() => deployLogButton(record.applicationId)}>
              日志
            </Button>
            <Button
              type="danger"
              onClick={() => onClickDeleteButton(record.applicationId)}
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
      <Table
        style={{ display: loginFlag ? "block" : "none" }}
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
          pageSizeOpts: [5, 10, 20, 30, 50, 100],
          onPageChange: onPageChange,
          onPageSizeChange: onPageSizeChange,
        }}
      />
      <Modal
        title="添加应用"
        visible={addDataShow}
        onOk={addDataOk}
        onCancel={addDataCancel}
        maskClosable={false}
        confirmLoading={loadingData}
        cancelLoading={loadingData}
      >
        <Form
          getFormApi={getAddFormApi}
          onValueChange={(values) => onValueChange(values)}
        >
          <Form.Input
            field="applicationName"
            label="应用名称"
            style={{ width: "100%" }}
          />
          <Form.Select
            filter
            label="部署类型"
            field="deviceType"
            placeholder="部署类型"
            style={{ width: "100%" }}
            onChange={changeDeviceType}
            initValue={deviceType}
            optionList={deviceSelectList}
          />
          {deviceType === 0? 
            <Form.Select
              filter
              label="服务器"
              field="deviceId"
              placeholder="所属服务器"
              style={{ width: "100%" }}
              optionList={serverSelectList}
            />:null}

            {deviceType === 1?
            <Form.Select
              filter
              label="客户端"
              field="deviceId"
              placeholder="所属客户端"
              style={{ width: "100%" }}
              optionList={clientSelectList}
            />:null}

          <Form.Select
            filter
            label="APP类型"
            field="appId"
            placeholder="APP类型"
            style={{ width: "100%" }}
            optionList={appSelectList}
          />
        </Form>
      </Modal>
      <Modal
        title="编辑应用"
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
            field="applicationId"
            label="应用ID"
            style={{ width: "100%" }}
            disabled
          />
          <Form.Input
            field="applicationName"
            label="应用名称"
            style={{ width: "100%" }}
          />
          {/* <Form.Select
            filter
            label="所属服务器"
            field="serverId"
            placeholder="所属服务器"
            style={{ width: "100%" }}
            optionList={serverSelectList}
            disabled
          /> */}
          <Form.Select
            filter
            label="APP类型"
            field="appId"
            placeholder="APP类型"
            style={{ width: "100%" }}
            optionList={appSelectList}
            disabled
          />
        </Form>
      </Modal>

      <Modal
        title="执行脚本"
        visible={deployAppShow}
        onOk={deployAppOk}
        onCancel={deployAppCancel}
        maskClosable={false}
        width={600}
        confirmLoading={loadingData}
        cancelLoading={loadingData}
      >
        <Form
          id="deploy-tooltip-container"
          getFormApi={getDeployFormApi}
          onValueChange={(values) => deployOnValueChange(values)}
        >
          <Form.Select
            onChange={getScriptEnvList}
            filter
            label="选择脚本"
            field="appScriptId"
            placeholder="选择脚本"
            style={{ width: "100%" }}
            optionList={scriptSelectList}
          />
          <Section text={"脚本参数"}>
            {deployEnvMoudle.length > 0 ? (
              deployEnvMoudle
            ) : (
              <span>无参数设置</span>
            )}
          </Section>
        </Form>
      </Modal>

      <Modal
        title="执行记录"
        visible={deployLogShow}
        onOk={deployLogButtonOkOrCe}
        onCancel={deployLogButtonOkOrCe}
        maskClosable={false}
        fullScreen
        bodyStyle={{ overflow: "auto" }}
      >
        <ExecLog applicationId={currentApplicationId} />
      </Modal>

      <SideSheet title="应用详情" visible={dataInfoShow} onCancel={dataInfoHidden}>
                <ApplicationInfo dataInfo={dataInfo} />
      </SideSheet>

    </Spin>
  );
};

export default observer(Application);
