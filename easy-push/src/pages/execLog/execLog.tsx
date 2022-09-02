import {
  IllustrationNoContent,
  IllustrationConstructionDark,
} from "@douyinfe/semi-illustrations";
import {
  Button,
  ButtonGroup,
  Empty,
  Modal,
  Spin,
  Table,
} from "@douyinfe/semi-ui";
import { observer } from "mobx-react";
import { useEffect } from "react";
import useStores from "../../utils/store";
import LineModel from "../lineModel/lineModel";

const ExecLog = (props: any) => {
  const { applicationId } = props;
  const { OauthStore, ExecLogStore } = useStores();
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
    logResults,
    onClickLogInfoButton,
    logDataShow,
    logDataCancel,
  } = ExecLogStore;

  //页面渲染执行
  useEffect(() => {
    if (loginFlag) {
      pageRequest(applicationId);
    }
  }, []);

  const applicationColumns = [
    {
      title: "APP",
      dataIndex: "appName",
    },
    {
      title: "应用",
      dataIndex: "applicationName",
    },
    {
      title: "设备",
      dataIndex: "deviceName",
    },
    {
      title: "脚本",
      dataIndex: "scriptName",
    },
    {
      title: "状态",
      dataIndex: "executeState",
    },
    {
      title: "提交时间",
      dataIndex: "createTime",
    },
    {
      title: "开始时间",
      dataIndex: "startTime",
    },
    {
      title: "结束时间",
      dataIndex: "endTime",
    },
    {
      title: "操作",
      dataIndex: "operate",
      render: (text: any, record: any) => {
        return (
          <ButtonGroup theme="borderless">
            <Button onClick={() => onClickLogInfoButton(record.id)}>
              详情
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
              <Button onClick={() => pageRequest(applicationId)}>刷新</Button>
            </ButtonGroup>
          }
          columns={applicationColumns}
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
        title="日志详情"
        visible={logDataShow}
        onOk={logDataCancel}
        onCancel={logDataCancel}
        maskClosable={false}
        fullScreen
        bodyStyle={{ overflow: "auto" }}
      >
        <LineModel lines={logResults} />
      </Modal>
    </Spin>
  );
};

export default observer(ExecLog);
