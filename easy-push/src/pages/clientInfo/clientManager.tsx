import {
  IllustrationNoContent,
  IllustrationConstructionDark,
} from "@douyinfe/semi-illustrations";
import {
  Button,
  ButtonGroup,
  Col,
  Empty,
  Form,
  Modal,
  Popconfirm,
  Row,
  SideSheet,
  Spin,
  Table,
  Tag,
  Typography,
} from "@douyinfe/semi-ui";
import Paragraph from "@douyinfe/semi-ui/lib/es/typography/paragraph";
import { observer } from "mobx-react";
import { useEffect } from "react";
import useStores from "../../utils/store";
import ExecLog from "../execLog/execLog";
import ClientIndoManager from "./clientManagerInfo";

const ClientManager = () => {
  const { Text } = Typography;
  const { OauthStore, ClientInfoStore, ServerInfoStore } = useStores();
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
    onClickAddButton,
    addDataShow,
    addDataOk,
    addDataCancel,
    getFormApi,
    onValueChange,
    onClickDeleteButton,
    getSerchFormapi,
    serchFormValueChange,
    searchButton,
    cleanSearchform,
    deletecConfirmShow,
    deletecConfirmOK,
    deletecConfirmCancel,
    dataInfoShow,
    dataInfoButton,
    dataInfo,
    dataInfoHiden,
    oninstallButton,
    installShow,
    installOk,
    installCancel,
    installCmd,
    currentClientId,
    deployLogShow,
    deployLogButton,
    deployLogButtonOkOrCe,
  } = ClientInfoStore;



  //页面渲染执行
  useEffect(() => {
    if (loginFlag) {
      pageRequest();
      serverListRequest();
    }
  }, []);

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

  const columns = [
    {
      title: <Tag>ID</Tag>,
      dataIndex: "id",
    },
    {
      title: <Tag>名称</Tag>,
      dataIndex: "clientName",
    },
    {
      title: <Tag>状态</Tag>,
      dataIndex: "status",
    },
    {
      title: "最后在线时间",
      dataIndex: "lastUpTime",
    },
    {
      title: "创建时间",
      dataIndex: "createTime",
    },
    {
      title: "操作",
      dataIndex: "operate",
      render: (text: any, record: any) => {
        return (
          <ButtonGroup theme="borderless">
            <Button onClick={() => oninstallButton(record.id)}>安装</Button>
             <Button
              onClick={() => dataInfoButton(record.id)}
            >
              详情
            </Button>
            <Button onClick={() => deployLogButton(record.id)}>
              安装日志
            </Button>
            <Button
              type="danger"
              onClick={() => onClickDeleteButton(record.id)}
            >
              卸载
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
        <>
          <Popconfirm
            visible={deletecConfirmShow}
            title="卸载确认"
            content="确定卸载吗,卸载后所有数据将清空,请谨慎操作."
            onConfirm={deletecConfirmOK}
            onCancel={deletecConfirmCancel}
          >
          </Popconfirm>
        <Table
          title={<>
            {/* <Form
              labelPosition="left"
              labelAlign="left"
              style={{ padding: "10px" }}
              getFormApi={getSerchFormapi}
              onValueChange={serchFormValueChange}
            >
              <Row>
                <Col span={4}>
                  <Form.Input label="名称" field="clientName" style={{ width: "90%" }} showClear></Form.Input>
                </Col>
                <Col span={4}>
                  <Form.Input label="用户id" field="id" style={{ width: "90%" }} showClear></Form.Input>
                </Col>
                <Col span={4}>
                  <Form.Select label="状态" field="state" style={{ width: "90%" }} showClear>
                    <Form.Select.Option label="正常" value={0} />
                    <Form.Select.Option label="禁用" value={1} />
                  </Form.Select>
                </Col>
              </Row>
            </Form> */}
            <ButtonGroup theme="borderless">
            <Button onClick={onClickAddButton}>添加</Button>
              {/* <Button onClick={searchButton}>搜索</Button> */}
              {/* <Button onClick={cleanSearchform}>重置</Button> */}
              <Button onClick={pageRequest}>刷新</Button>
            </ButtonGroup>
          </>}
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
          }} />
        <Modal
        title="添加客户端"
        visible={addDataShow}
        onOk={addDataOk}
        onCancel={addDataCancel}
        maskClosable={false}
        confirmLoading={loadingData}
        cancelButtonProps={loadingData}
      >
           <Form
          getFormApi={getFormApi}
          onValueChange={(values) => onValueChange(values)}
        >
          <Form.Input
            field="clientName"
            label="客户端名称"
            style={{ width: "90%" }}
            maxLength={12}
            minLength={2}
          />
          <Form.Select
            filter
            label="是否绑定服务器"
            field="serverId"
            placeholder="手动安装则不选"
            style={{ width: "100%" }}
            optionList={serverSelectList}
          />
          </Form>
      </Modal>
      <Modal
        title="安装客户端"
        visible={installShow}
        onOk={installOk}
        onCancel={installCancel}
        maskClosable={false}
        confirmLoading={loadingData}
        cancelButtonProps={loadingData}
        footer={
          <>
          {dataInfo && dataInfo.serverId && dataInfo.status === '未部署'?<Button type="primary" loading={loadingData} onClick={()=>installOk(dataInfo.id)} >在线安装</Button>:null}
          {dataInfo && dataInfo.serverId && dataInfo.status !== '未部署'?<Button type="primary" loading={loadingData} onClick={()=>installOk(dataInfo.id)} >重新在线安装</Button>:null}
          <Button type="primary" loading={loadingData} onClick={installCancel} >关闭窗口</Button>
              </>
        }
      >
        使用手动安装脚本需要安装wget和jq,centos系统直接运行 yum install -y wget jq ,其他不支持yum的系统请自行百度安装.<br/>
        <Paragraph copyable={{content:installCmd}} >安装命令(不支持离线设备):<br/>{installCmd}</Paragraph>
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
        <ExecLog applicationId={currentClientId} />
      </Modal>
             <SideSheet title="客户端详情" visible={dataInfoShow} onCancel={dataInfoHiden}>
                <ClientIndoManager dataInfo={dataInfo} />
            </SideSheet>
          </>
      ) : (
        ""
      )}
    </Spin>
  );
};

export default observer(ClientManager);
