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
import useStores from "../../utils/store";
import { useEffect } from "react";
import Cronc  from '../cron/cron'

const Job = () => {

  useEffect(() => {
    if (loginFlag) {
      serverListRequest();
      scriptListRequest();
    }
  }, []);

  const { OauthStore, ServerInfoStore, JobStore, CronStore, AppScriptStore } = useStores();

  const { loginFlag } = OauthStore;

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
    formApi,
    onRunButton
  } = JobStore;
  

  const { cronDataShow, cronDataOk, cronDataCancel, show, cronValue } = CronStore;


    //构建服务器选择框
    const serverListRequest = ServerInfoStore.listRequst;
    const serverList = ServerInfoStore.dataList;
    const serverSelectList: any[] = [];
    serverList.map((item: any, i: number) => {
      serverSelectList.push(
        {
        value: item.id,
        label: item.serverName,
        otherKey: i,
        }
        );
    });
  
   //构建脚本选择框
   const scriptListRequest = AppScriptStore.listRequst;
   const scriptList = AppScriptStore.dataList;
   let scriptSelectList: any[] = [];
   if(scriptList.length > 0){
     scriptList.map((item: any, i: number) => {
       scriptSelectList.push({
         value: item.id,
         label: item.scriptName+'   作者: '+ (item.author?item.author:'未知'),
         otherKey: i,
       });
     });
   }

  const stateSelctlist = [
    { value: '0', label: "正常", otherKey: 0 },
    { value: '1', label: "暂停", otherKey: 1 },
  ];

  const jobTypeList = [
    { value: 'script', label: "脚本任务", otherKey: 0 },
  ];

  const misfirePolicyList = [
    { value: '1', label: "立即执行", otherKey: 1 },
    { value: '2', label: "执行一次", otherKey: 2 },
    { value: '3', label: "放弃执行", otherKey: 3 },
  ];

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
      title: "cron表达式",
      dataIndex: "cronExpression",
    },
    {
      title: "任务状态",
      render: (text: string, record: any, index: number) => {
        switch (record.status) {
          case '0':
            return "正常";
          case '1':
            return "暂停";
        }
      },
    },
    {
      title: "备注",
      dataIndex: "remark",
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
            <Button onClick={() => onClickEditButton(record.jobId)}>编辑</Button>
            <Button onClick={() => onRunButton(record.jobId)}>运行</Button>
            <Button type="danger" onClick={() => onClickDeleteButton(record.jobId)}>删除</Button>
          </ButtonGroup>
        );
      },
    },
  ];

  return (
    <Spin tip={'加载中....'}  spinning={loadingData}>
      <Empty
        style={{ display: !loginFlag  ? "block" : "none" }}
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
        title="添加任务"
        visible={addDataShow}
        onOk={addDataOk}
        onCancel={addDataCancel}
        maskClosable={false}
        width={1200}
        confirmLoading={loadingData}
        cancelButtonProps={loadingData}
      >
        <Form id='deploy-tooltip-container'
          getFormApi={getAddFormApi}
          onValueChange={(values) => onValueChange(values)}
        >
          <Form.Input
            field="jobName"
            label="任务名称"
            style={{ width: "100%" }}
          />
          <Form.Select
            filter
            label="执行服务器"
            field="serverId"
            placeholder="执行服务器"
            style={{ width: "100%" }}
            optionList={serverSelectList}
          />
          <Form.Select
            label="任务类型"
            field="jobType"
            placeholder="任务类型"
            style={{ width: "100%" }}
            initValue='script'
            optionList={jobTypeList}
          />
          <Form.Select 
            filter
            label="选择脚本"
            field="appScriptId"
            placeholder="选择脚本"
            style={{ width: "100%" }}
            optionList={scriptSelectList}
          />
          <Form.Input 
          label="cron表达式" 
          field="cronExpression"
          style={{ width: "100%" }} 
          onClick={()=>show()}
          initValue={cronValue}
          />
          <Form.Select
            label="执行策略"
            field="misfirePolicy"
            placeholder="执行策略"
            style={{ width: "100%" }}
            initValue='2'
            optionList={misfirePolicyList}
          />
          <Form.Select
            label="状态"
            field="status"
            placeholder="状态"
            style={{ width: "100%" }}
            initValue='1'
            optionList={stateSelctlist}
          />
          <Form.TextArea
            label="备注"
            field="remark"
            placeholder="备注"
            style={{ width: "100%" }}
          />
        </Form>
      </Modal>
      <Modal
        title="编辑任务"
        visible={editDataShow}
        onOk={editDataOk}
        onCancel={editDataCancel}
        maskClosable={false}
        width={1200}
        confirmLoading={loadingData}
        cancelButtonProps={loadingData}
      >
        <Form
          getFormApi={getFormApi}
          onValueChange={(values) => onValueChange(values)}
        >
           <Form.Input
            field="jobName"
            label="任务名称"
            style={{ width: "100%" }}
          />
          <Form.Select
            filter
            label="执行服务器"
            field="serverId"
            placeholder="执行服务器"
            style={{ width: "100%" }}
            optionList={serverSelectList}
          />
          <Form.Select
            label="任务类型"
            field="jobType"
            placeholder="任务类型"
            style={{ width: "100%" }}
            initValue='script'
            optionList={jobTypeList}
          />
          <Form.Select 
            filter
            label="选择脚本"
            field="appScriptId"
            placeholder="选择脚本"
            style={{ width: "100%" }}
            optionList={scriptSelectList}
          />
          <Form.Input 
          label="cron表达式" 
          field="cronExpression" 
          style={{ width: "100%" }} 
          onClick={()=>show()}
          />
          <Form.Select
            label="执行策略"
            field="misfirePolicy"
            placeholder="执行策略"
            style={{ width: "100%" }}
            optionList={misfirePolicyList}
          />
          <Form.Select
            label="状态"
            field="status"
            placeholder="状态"
            style={{ width: "100%" }}
            optionList={stateSelctlist}
          />
          <Form.TextArea
            label="备注"
            field="remark"
            placeholder="备注"
            style={{ width: "100%" }}
          />
        </Form>
      </Modal>
      <Modal
      title="生成表达式"
        visible={cronDataShow}
        onOk={cronDataOk}
        onCancel={cronDataCancel}
        maskClosable={false}
        width={600}
        height={600}
        footer={null}
        >
      <Cronc formApi={formApi} field='cronExpression'/>
      </Modal>
    </Spin>
  );
};

export default observer(Job);
