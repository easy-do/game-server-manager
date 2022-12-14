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
                    { value: 1, label: "???", otherKey: envItem.evenKey + "1" },
                    { value: 0, label: "???", otherKey: envItem.evenKey + "2" },
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


  //????????????
  const deviceSelectList = [
    {
      value: 0,
      label: '?????????',
      otherKey: '0', 
    },
    {
      value: 1,
      label: '?????????',
      otherKey: '1',
    }
  ]

  //????????????????????????
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

    //????????????????????????
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

  //??????APP?????????
  const appListRequest = AppInfoStore.listRequst;
  const appList = AppInfoStore.dataList;
  const appSelectList: any[] = [];
  appList.map((item: any, i: number) => {
    appSelectList.push({
      value: item.id,
      label: item.appName + "   ??????: " + (item.author ? item.author : "??????"),
      otherKey: i,
    });
  });

  //?????????????????????
  const scriptList = ApplicationStore.scriptList;
  let scriptSelectList: any[] = [];
  if (scriptList.length > 0) {
    scriptList.map((item: any, i: number) => {
      scriptSelectList.push({
        value: item.id,
        label:
          item.scriptName + "   ??????: " + (item.author ? item.author : "??????"),
        otherKey: i,
      });
    });
  }

  const columns = [
    {
      title: <Tag>????????????</Tag>,
      dataIndex: "applicationName",
    },
    {
      title: <Tag>????????????</Tag>,
      dataIndex: "deviceName",
    },
    {
      title: <Tag>APP??????</Tag>,
      dataIndex: "appName",
    },
    {
      title: <Tag>????????????</Tag>,
      dataIndex: "status",
    },
    {
      title: <Tag>??????????????????</Tag>,
      dataIndex: "lastUpTime",
      render: (text: string, record: any, index: number) => {
        if (record.lastUpTime) {
          return record.lastUpTime;
        } else {
          return "?????????";
        }
      },
    },
    {
      title: <Tag>??????</Tag>,
      dataIndex: "operate",
      render: (text: any, record: any) => {
        return (
          <ButtonGroup theme="borderless">
            <Button onClick={() => dataInfoButton(record.applicationId)}>
              ????????????
            </Button>
            {/* <Button onClick={() => onClickEditButton(record.applicationId)}>
              ??????
            </Button> */}
            <Button
              onClick={() =>
                onClickDeployButton(record.applicationId, record.appId)
              }
            >
              ??????
            </Button>
            <Button onClick={() => deployLogButton(record.applicationId)}>
              ??????
            </Button>
            <Button
              type="danger"
              onClick={() => onClickDeleteButton(record.applicationId)}
            >
              ??????
            </Button>
          </ButtonGroup>
        );
      },
    },
  ];

  return (
    <Spin tip={"?????????...."} spinning={loadingData}>
      <Empty
        style={{ display: !loginFlag ? "block" : "none" }}
        image={<IllustrationNoContent style={{ width: 150, height: 150 }} />}
        darkModeImage={
          <IllustrationConstructionDark style={{ width: 150, height: 150 }} />
        }
        title={"????????????"}
        description="??????????????????,?????????????????????"
      />
      <Table
        style={{ display: loginFlag ? "block" : "none" }}
        title={
          <ButtonGroup theme="borderless">
            <Button onClick={onClickAddButton}>??????</Button>
            <Button onClick={pageRequest}>??????</Button>
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
        title="????????????"
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
            label="????????????"
            style={{ width: "100%" }}
          />
          <Form.Select
            filter
            label="????????????"
            field="deviceType"
            placeholder="????????????"
            style={{ width: "100%" }}
            onChange={changeDeviceType}
            initValue={deviceType}
            optionList={deviceSelectList}
          />
          {deviceType === 0? 
            <Form.Select
              filter
              label="?????????"
              field="deviceId"
              placeholder="???????????????"
              style={{ width: "100%" }}
              optionList={serverSelectList}
            />:null}

            {deviceType === 1?
            <Form.Select
              filter
              label="?????????"
              field="deviceId"
              placeholder="???????????????"
              style={{ width: "100%" }}
              optionList={clientSelectList}
            />:null}

          <Form.Select
            filter
            label="APP??????"
            field="appId"
            placeholder="APP??????"
            style={{ width: "100%" }}
            optionList={appSelectList}
          />
        </Form>
      </Modal>
      <Modal
        title="????????????"
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
            label="??????ID"
            style={{ width: "100%" }}
            disabled
          />
          <Form.Input
            field="applicationName"
            label="????????????"
            style={{ width: "100%" }}
          />
          {/* <Form.Select
            filter
            label="???????????????"
            field="serverId"
            placeholder="???????????????"
            style={{ width: "100%" }}
            optionList={serverSelectList}
            disabled
          /> */}
          <Form.Select
            filter
            label="APP??????"
            field="appId"
            placeholder="APP??????"
            style={{ width: "100%" }}
            optionList={appSelectList}
            disabled
          />
        </Form>
      </Modal>

      <Modal
        title="????????????"
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
            label="????????????"
            field="appScriptId"
            placeholder="????????????"
            style={{ width: "100%" }}
            optionList={scriptSelectList}
          />
          <Section text={"????????????"}>
            {deployEnvMoudle.length > 0 ? (
              deployEnvMoudle
            ) : (
              <span>???????????????</span>
            )}
          </Section>
        </Form>
      </Modal>

      <Modal
        title="????????????"
        visible={deployLogShow}
        onOk={deployLogButtonOkOrCe}
        onCancel={deployLogButtonOkOrCe}
        maskClosable={false}
        fullScreen
        bodyStyle={{ overflow: "auto" }}
      >
        <ExecLog applicationId={currentApplicationId} />
      </Modal>

      <SideSheet title="????????????" visible={dataInfoShow} onCancel={dataInfoHidden}>
                <ApplicationInfo dataInfo={dataInfo} />
      </SideSheet>

    </Spin>
  );
};

export default observer(Application);
