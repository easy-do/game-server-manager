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

const JobLog = () => {
  const { OauthStore, JobLogStore } = useStores();
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
    editDataShow,
    onClickLogInfoButton,
    editDataCancel,
    onClickDeleteButton,
    logResults,
  } = JobLogStore;

  //页面渲染执行
  useEffect(() => {
    if (loginFlag) {
      pageRequest();
    }
  }, []);

  const applicationColumns = [
    {
      title: "任务名称",
      dataIndex: "jobName",
    },
    {
      title: "执行状态",
      render: (text: string, record: any, index: number) => {
        switch (record.status) {
          case "0":
            return "成功";
          case "1":
            return "失败";
        }
      },
    },
    {
      title: "任务消息",
      dataIndex: "jobMessage",
    },
    {
      title: "记录时间",
      dataIndex: "createTime",
    },
    {
      title: "操作",
      dataIndex: "operate",
      render: (text: any, record: any) => {
        return (
          <ButtonGroup theme="borderless">
            <Button onClick={() => onClickLogInfoButton(record.jobLogId)}>
              日志详情
            </Button>
            <Button
              type="danger"
              onClick={() => onClickDeleteButton(record.jobLogId)}
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
        <Table
          title={
            <ButtonGroup theme="borderless">
              <Button onClick={pageRequest}>刷新</Button>
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
        title="详细日志"
        visible={editDataShow}
        onOk={editDataCancel}
        onCancel={editDataCancel}
        maskClosable={false}
        fullScreen
        bodyStyle={{ overflow: "auto" }}
      >
        <LineModel lines={logResults} />
      </Modal>
    </Spin>
  );
};

export default observer(JobLog);
