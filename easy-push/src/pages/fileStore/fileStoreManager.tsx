import {
  IllustrationNoContent,
  IllustrationConstructionDark,
} from "@douyinfe/semi-illustrations";
import {
  Avatar,
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

const FileStoreManager = () => {
  const { Text } = Typography;
  const { OauthStore, UploadStore } = useStores();
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
  } = UploadStore;

  //页面渲染执行
  useEffect(() => {
    if (loginFlag) {
      pageRequest();
    }
  }, []);

  const columns = [
    {
      title: "序号",
      dataIndex: "id",
    },
    {
      title: "文件名",
      dataIndex: "fileName",
    },
    {
      title: "预览",
      render: (text: any, record: any) => {
        return (
          <Avatar src={'data:image/png;base64,'+record.file}/>
        );
      },
    },
    {
      title: "大小(kb)",
      dataIndex: "fileSize",
    },
    {
      title: "上传时间",
      dataIndex: "createTime",
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
    </Spin>
  );
};

export default observer(FileStoreManager);
