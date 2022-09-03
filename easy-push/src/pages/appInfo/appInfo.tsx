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
  Popover,
  Spin,
  Table,
  Tag,
  Typography,
} from "@douyinfe/semi-ui";
import { observer } from "mobx-react";
import useStores from "../../utils/store";

import { useEffect } from "react";
import FormUploadImg from "../upload/formUploadImg";
import AuditPage from "../auditManager/auditPage";
import { RoleEnum } from "../../utils/systemConstant";

const AppInfo = () => {
  const { Text } = Typography;

  const { OauthStore, AppInfoStore, AuditStore } = useStores();
  const { loginFlag , hasRole } = OauthStore;
  const { commitAuditRequest } = AuditStore;

  const {
    currentPage,
    pageSize,
    total,
    loadingData,
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
    onClickDeleteButton,
    auditButton,
    auditshow,
    auditId,
  } = AppInfoStore;

  const appStateSelctlist = [
    { value: 0, label: "关闭", otherKey: 0 },
    { value: 1, label: "开启", otherKey: 1 },
  ];
  const appScopeSelctlist = [
    { value: "private", label: "私有", otherKey: "install" },
    { value: "public", label: "公开", otherKey: "restart" },
    { value: "subscribe", label: "订阅", otherKey: "stop" },
  ];
  useEffect(() => {
    if (loginFlag) {
      pageRequest();
    }
  }, []);

  const columns = [
    {
      title: <Tag>APP名称</Tag>,
      dataIndex: "appName",
    },
    {
      title: <Tag>APP状态</Tag>,
      render: (text: string, record: any, index: number) => {
        switch (record.state) {
          case 0:
            return "关闭";
          case 1:
            return "开启";
          default:
            return "未知";
        }
      },
    },
    {
      title: (
        <Popover
          content={
            <article style={{ padding: 12 }}>
              审核通过可后应用商店展示，对所有用户可见。
            </article>
          }
        >
          <Tag>审核状态</Tag>
        </Popover>
      ),
      render: (text: any, record: any) => {
        switch (record.isAudit) {
          case 0:
            return <Text>草稿</Text>;
          case 1:
            return <Text style={{ color: "blue" }}>审核中</Text>;
          case 2:
            return <Text style={{ color: "green" }}>通过</Text>;
          case 3:
            return <Text style={{ color: "orange" }}>驳回</Text>;
          case 4:
            return <Text style={{ color: "red" }}>锁定</Text>;
          default:
            return <Text style={{ color: "red" }}>未知</Text>;
        }
      },
    },
    {
      title: <Tag>版本</Tag>,
      dataIndex: "version",
    },
    {
      title: <Tag>APP类型</Tag>,
      dataIndex: "appScope",
      render: (text: string, record: any, index: number) => {
        switch (record.appScope) {
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
      title: <Tag>简介</Tag>,
      dataIndex: "description",
      render: (text: any, record: any, index: any) => {
        return (
          <span style={{ display: "flex", alignItems: "center" }}>
            <Text
              ellipsis={{ showTooltip: true }}
              style={{ width: "calc(200px - 76px)" }}
            >
              {record.description}
            </Text>
          </span>
        );
      },
    },
    {
      title: <Tag>创建时间</Tag>,
      dataIndex: "createTime",
    },
    {
      title: <Tag>更新时间</Tag>,
      dataIndex: "updateTime",
    },
    {
      title: <Tag>操作</Tag>,
      dataIndex: "operate",
      render: (text: any, record: any) => {
        return (
          <ButtonGroup theme="borderless">
            <Button onClick={() => onClickEditButton(record.id)}>编辑</Button>
            <Button
              disabled={
                record.isAudit === 1 ||
                record.isAudit === 2 ||
                record.isAudit === 4
              }
              onClick={() => commitAuditRequest(record.id, 2, pageRequest)}
            >
              提交审核
            </Button>
            <Button hidden={!hasRole(RoleEnum.SUPE_ADMIN)} onClick={() => auditButton(record.id)}>审核</Button>
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
        title="添加APP"
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
          <Form.Input
            field="appName"
            label="应用名称"
            style={{ width: "95%" }}
          />
          <Form.Input label="版本" field="version" style={{ width: "95%" }} />
          <Form.Select
            filter
            label="APP类型"
            field="appScope"
            placeholder="APP类型"
            style={{ width: "95%" }}
            optionList={appScopeSelctlist}
          />
          <Form.Select
            label="状态"
            field="state"
            placeholder="状态"
            style={{ width: "95%" }}
            optionList={appStateSelctlist}
          />
          <Form.Input label="启动命令" field="startCmd"  placeholder={"插件启动后执行的命令,配合平台plugins镜像使用,未集成插件或不执行留空即可"} style={{ width: "95%" }} allowEmptyString showClear/>
          <Form.Input label="停止命令" field="stopCmd" placeholder={"插件通信失败多次后执行的停止命令,配合平台plugins镜像使用,未集成插件或不执行留空即可"} style={{ width: "95%" }} allowEmptyString showClear/>
          <Form.Input label="配置文件路径" field="configFilePath" placeholder={"需要同步的配置文件内容,配合平台plugins镜像使用,未集成插件或不同步配置留空即可。 "} style={{ width: "95%" }} allowEmptyString showClear/>
          <FormUploadImg field="icon" label="图标" limit={1} />
          <FormUploadImg field="picture" label="展示图片" limit={5} />
          <Form.TextArea
            label="简介"
            field="description"
            placeholder="简介"
            style={{ width: "95%" }}
          />
        </Form>
      </Modal>
      <Modal
        title="编辑APP"
        visible={editDataShow}
        onOk={editDataOk}
        onCancel={editDataCancel}
        maskClosable={false}
        width={1200}
        confirmLoading={loadingData}
        cancelLoading={loadingData}
      >
        <Form
          getFormApi={getFormApi}
          onValueChange={(values) => onValueChange(values)}
        >
          <Form.Input
            field="appName"
            label="应用名称"
            style={{ width: "95%" }}
          />
          <Form.Input label="版本" field="version" style={{ width: "95%" }} />
          <Form.Select
            filter
            label="APP类型"
            field="appScope"
            placeholder="APP类型"
            style={{ width: "95%" }}
            optionList={appScopeSelctlist}
          />
          <Form.Input label="启动命令" field="startCmd"  placeholder={"插件启动后执行的命令,配合平台plugins镜像使用,未集成插件或不执行留空即可。"} style={{ width: "95%" }} allowEmptyString showClear/>
          <Form.Input label="停止命令" field="stopCmd" placeholder={"插件通信失败多次后执行的停止命令,配合平台plugins镜像使用,未集成插件或不执行留空即可。"} style={{ width: "95%" }} allowEmptyString showClear/>
          <Form.Input label="配置文件路径" field="configFilePath" placeholder={"需要同步的配置文件内容,配合平台plugins镜像使用,未集成插件或不同步配置留空即可。 "} style={{ width: "95%" }} allowEmptyString showClear/>
          <Form.Select
            label="状态"
            field="state"
            placeholder="状态"
            style={{ width: "95%" }}
            optionList={appStateSelctlist}
          />
          <FormUploadImg field="icon" label="图标" limit={1} />
          <FormUploadImg field="picture" label="展示图片" limit={5} />
          <Form.TextArea
            label="简介"
            field="description"
            placeholder="简介"
            style={{ width: "95%" }}
          />
        </Form>
      </Modal>
      <AuditPage
        title="审核话题"
        visible={auditshow}
        auditId={auditId}
        callBack={pageRequest}
        auditType={2}
      />
    </Spin>
  );
};

export default observer(AppInfo);
