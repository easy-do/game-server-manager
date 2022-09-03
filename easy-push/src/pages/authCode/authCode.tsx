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
  Table,
} from "@douyinfe/semi-ui";
import { observer } from "mobx-react";
import useStores from "../../utils/store";
import { useEffect } from "react";
import { RoleEnum } from "../../utils/systemConstant";

const AuthCode = () => {
  const { OauthStore, AuthCodeStore } = useStores();

  const { loginFlag, userInfo, hasRole } = OauthStore;

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
    onClickDeleteButton,
    editDataOk,
    editDataCancel,
    listRequstloading,
  } = AuthCodeStore;

  useEffect(() => {
    if (loginFlag && hasRole(RoleEnum.SUPE_ADMIN)) {
      pageRequest();
    }
  }, []);

  const applicationColumns = [
    {
      title: "序列号",
      dataIndex: "id",
    },
    {
      title: "服务数",
      render: (text: string, record: any, index: number) => {
        return JSON.parse(record.config).serverNum;
      },
    },
    {
      title: "应用数",
      render: (text: string, record: any, index: number) => {
        return JSON.parse(record.config).applicationNum;
      },
    },
    {
      title: "APP数",
      render: (text: string, record: any, index: number) => {
        return JSON.parse(record.config).appCreationNum;
      },
    },
    {
      title: "脚本数",
      render: (text: string, record: any, index: number) => {
        return JSON.parse(record.config).scriptCreationNum;
      },
    },
    {
      title: "状态",
      render: (text: string, record: any, index: number) => {
        switch (record.state) {
          case 0:
            return "未使用";
          case 1:
            return "已使用";
          case 2:
            return "可重复使用";
          case 3:
            return "已废弃";
          default:
            return "未知";
        }
      },
    },
    {
      title: "到期时间",
      render: (text: string, record: any, index: number) => {
        return JSON.parse(record.config).expires;
      },
    },
    {
      title: "描述信息",
      dataIndex: "description",
    },
    {
      title: "授权码",
      dataIndex: "code",
    },
    {
      title: "生成时间",
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
    <>
      <Empty
        style={{ display: !loginFlag || !hasRole(RoleEnum.SUPE_ADMIN) ? "block" : "none" }}
        image={<IllustrationNoContent style={{ width: 150, height: 150 }} />}
        darkModeImage={
          <IllustrationConstructionDark style={{ width: 150, height: 150 }} />
        }
        title={"未登录或无权限。"}
        description="未登录或无权限"
      />
      {loginFlag && hasRole(RoleEnum.SUPE_ADMIN) ? (
        <Table
          title={
            <ButtonGroup theme="borderless">
              <Button onClick={onClickAddButton}>生成</Button>
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
          loading={listRequstloading}
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
            field="serverNum"
            label="授权服务数量"
            style={{ width: "90%" }}
            initValue={0}
            type={"number"}
          />
          <Form.Input
            field="applicationNum"
            label="授权应用数量"
            style={{ width: "90%" }}
            initValue={0}
            type={"number"}
          />
          <Form.Input
            field="appCreationNum"
            label="应用创作数量"
            style={{ width: "90%" }}
            initValue={0}
            type={"number"}
          />
          <Form.Input
            field="scriptCreationNum"
            label="脚本创作数量"
            style={{ width: "90%" }}
            initValue={0}
            type={"number"}
          />
          <Form.Select
            label="授权码类型"
            field="state"
            placeholder="授权码类型"
            style={{ width: "90%" }}
            initValue={0}
            optionList={[
              { value: 0, label: "一次性", otherKey: 1 },
              { value: 2, label: "重复使用", otherKey: 1 },
            ]}
          />
          <Form.DatePicker
            type="dateTime"
            field="expires"
            label="到期时间"
            style={{ width: "90%" }}
          />
          <Form.Input
            field="description"
            label="描述信息"
            style={{ width: "90%" }}
          />
          <Form.Input
            field="genNum"
            label="生成数量"
            style={{ width: "90%" }}
            initValue={1}
            type={"number"}
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
            field="serverNum"
            label="授权服务数量"
            style={{ width: "90%" }}
          />
          <Form.Input
            field="appNum"
            label="授权应用数量"
            style={{ width: "90%" }}
          />
          <Form.Input
            field="appCreationNum"
            label="应用创作数量"
            style={{ width: "90%" }}
          />
          <Form.Input
            field="scriptCreationNum"
            label="脚本创作数量"
            style={{ width: "90%" }}
          />
          <Form.Select
            label="授权码类型"
            field="state"
            placeholder="授权码类型"
            style={{ width: 180 }}
            optionList={[
              { value: 0, label: "一次性", otherKey: 1 },
              { value: 2, label: "重复使用", otherKey: 1 },
            ]}
          />
          <Form.DatePicker
            type="dateTime"
            field="expires"
            label="到期时间"
            style={{ width: "90%" }}
          />
          <Form.Input
            field="description"
            label="描述信息"
            style={{ width: "90%" }}
          />
          <Form.Input
            field="genNum"
            label="生成数量"
            style={{ width: "90%" }}
          />
        </Form>
      </Modal>
    </>
  );
};

export default observer(AuthCode);
