import {
  IllustrationNoContent,
  IllustrationConstructionDark,
} from "@douyinfe/semi-illustrations";
import {
  Button,
  ButtonGroup,
  Empty,
  Spin,
  Table,
  Typography,
} from "@douyinfe/semi-ui";
import { observer } from "mobx-react";
import { useEffect } from "react";
import useStores from "../../utils/store";

const SysLog = () => {
  const { Text } = Typography;
  const { OauthStore, SysLogStore } = useStores();
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
    onClickClanButton
  } = SysLogStore;

  //页面渲染执行
  useEffect(() => {
    if (loginFlag) {
      pageRequest();
    }
  }, []);

  const applicationColumns = [
    {
      title: "模块",
      render: (text: string, record: any, index: number) => {
        let fieldValue = JSON.parse(record.data);
        return fieldValue.moduleName;
      },
    },
    {
      title: "操作类型",
      render: (text: string, record: any, index: number) => {
        let fieldValue = JSON.parse(record.data);
        return fieldValue.actionType;
      },
    },
    {
      title: "请求状态",
      render: (text: string, record: any, index: number) => {
        let fieldValue = JSON.parse(record.data);
        return fieldValue.operationStatus;
      },
    },
    {
      title: "IP",
      render: (text: string, record: any, index: number) => {
        let fieldValue = JSON.parse(record.data);
        return (
          <span style={{ display: "flex", alignItems: "center" }}>
            {/* 宽度计算方式为单元格设置宽度 - 非文本内容宽度 */}
            <Text
              ellipsis={{ showTooltip: true }}
              style={{ width: "calc(200px - 76px)" }}
            >
              {fieldValue.sourceIp}
            </Text>
          </span>
        );
      },
    },
    {
      title: "操作人",
      render: (text: any, record: any, index: any) => {
        let fieldValue = JSON.parse(record.data);
        return (
          <span style={{ display: "flex", alignItems: "center" }}>
            {/* 宽度计算方式为单元格设置宽度 - 非文本内容宽度 */}
            <Text
              ellipsis={{ showTooltip: true }}
              style={{ width: "calc(200px - 76px)" }}
            >
              {fieldValue.operatorName}
            </Text>
          </span>
        );
      },
    },
    {
      title: "详情",
      render: (text: any, record: any, index: any) => {
        let fieldValue = JSON.parse(record.data);
        return (
          <span style={{ display: "flex", alignItems: "center" }}>
            {/* 宽度计算方式为单元格设置宽度 - 非文本内容宽度 */}
            <Text
              ellipsis={{ showTooltip: true }}
              style={{ width: "calc(200px - 76px)" }}
            >
              {fieldValue.description}
            </Text>
          </span>
        );
      },
    },
    {
      title: "时间",
      render: (text: string, record: any, index: number) => {
        let fieldValue = JSON.parse(record.data);
        return fieldValue.operationTime;
      },
    },
    {
      title: "操作",
      dataIndex: "operate",
      render: (text: any, record: any) => {
        return (
          <ButtonGroup theme="borderless">
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
        <Table
          title={
            <ButtonGroup theme="borderless">
              <Button onClick={pageRequest}>刷新</Button>
              <Button onClick={onClickClanButton}>清空日志</Button>
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
    </Spin>
  );
};

export default observer(SysLog);
