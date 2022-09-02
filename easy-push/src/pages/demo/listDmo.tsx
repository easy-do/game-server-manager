import { IconCopyAdd, IconRedoStroked } from "@douyinfe/semi-icons";
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
  Spin,
  Table,
} from "@douyinfe/semi-ui";
import { observer } from "mobx-react";
import { useEffect } from "react";
import useStores from "../../utils/store";

const ListDemo = () => {
  const { OauthStore, ServerInfoStore } = useStores();
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
    onClickDeleteButton
  } = ServerInfoStore;

  //页面渲染执行
  useEffect(() => {
    if (loginFlag) {
      pageRequest();
    }
  }, []);


  const scripTypeSelctlist = [
    { value: "部署", label: "部署", otherKey: "install" },
    { value: "重启", label: "重启", otherKey: "restart" },
    { value: "停止", label: "停止", otherKey: "stop" },
    { value: "卸载", label: "卸载", otherKey: "uninstall" },
    { value: "自定义", label: "自定义", otherKey: "custom" },
  ];





    const applicationColumns = [
      {
        title: "z字段1",
        dataIndex: "serverName",
      },
      {
        title: "字段2",
        dataIndex: "address",
      },
      {
        title: "字段3",
        dataIndex: "userName",
      },
      {
        title: "字段4",
        render: (text: string, record: any, index: number) => {
          return '*******';
        }
      },
      {
        title: "switch",
        dataIndex: "scriptScope",
              render: (text: string, record: any, index: number) => {
          switch (record.scriptScope) {
            case 'private':
              return "私有";
            case 'public':
              return "公开";
            case 'subscribe':
              return "订阅";
          }
      },
    },
      {
        title: "操作",
        dataIndex: "operate",
        render: (text: any, record: any) => {
          return (
            <ButtonGroup theme="borderless">
              <Button>编辑</Button>
            </ButtonGroup>
          );
        },
      },
    ];



  return (
    <Spin tip={'加载中....'}  spinning={loadingData}>
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
            <Button icon={<IconCopyAdd onClick={onClickAddButton} />} />
            <Button icon={<IconRedoStroked onClick={pageRequest} />} />
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
        title="添加"
        visible={addDataShow}
        onOk={addDataOk}
        onCancel={addDataCancel}
        maskClosable={false}
      >
        <Form
          getFormApi={getAddFormApi}
          onValueChange={(values) => onValueChange(values)}
        >
          <Form.Input
            field="serverName"
            label="输入框"
            style={{ width: "90%" }}
          />
          <Form.Select
              label="选择框"
              field="scriptType"
              placeholder="选择框"
              style={{ width: "95%" }}
              optionList={scripTypeSelctlist}
            />
        </Form>
      </Modal>

      <Modal
        title="编辑"
        visible={editDataShow}
        onOk={editDataOk}
        onCancel={editDataCancel}
        maskClosable={false}
      >
        <Form
          getFormApi={getFormApi}
          onValueChange={(values) => onValueChange(values)}
        >
          <Form.Input
            field="serverName"
            label="输入框"
            style={{ width: "90%" }}
          />
          <Form.Select
              label="选择框"
              field="scriptType"
              placeholder="选择框"
              style={{ width: "95%" }}
              optionList={scripTypeSelctlist}
            />
        </Form>
      </Modal>
    </Spin>
  );
};

export default observer(ListDemo);
