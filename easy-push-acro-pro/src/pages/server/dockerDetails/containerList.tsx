import React, { useState, useEffect, useMemo, useRef } from 'react';
import {
  Table,
  Card,
  Typography,
  Notification,
  Modal,
  Form,
  Input,
  FormInstance,
} from '@arco-design/web-react';
import { IconPlus } from '@arco-design/web-react/icon';
import useLocale from '@/utils/useLocale';
import locale from './locale';
import { getContainerListColumns } from './constants';
import { containerList, logContainer, removeContainer, renameContainer, restartContainer, startContainer, stopContainer } from '@/api/dockerApi';
import styles from './style/index.module.less';

const { Title } = Typography;

function ContainerList(props: { isViewContainerList: boolean; dockerId: any }) {

  const t = useLocale(locale);

  const formRef = useRef<FormInstance>();


  //表格操作按钮回调
  const tableCallback = async (record, type) => {
    //查看
    if (type === 'view') {
      viewInfo(record);
    }

        //日志
        if (type === 'log') {
          logContainer(props.dockerId,record.Id).then((res) => {
            const { success, msg, data } = res.data;
            if (success) {
              Notification.success({ content: msg, duration: 300 });
              setIsViewLog(true);
              const logResult = data.split("\r\n")
              const logDivs = []
              for (let i = 0; i < logResult.length; i++) {
                logDivs.push(
                  <div className={styles['log-msg']}>
                    <span>{logResult[i]}</span>
                  </div>
                )
              }
              setViewLogData(logDivs)
            }
          });
        }

    //启动
    if (type === 'start') {
      startContainer(props.dockerId,record.Id).then((res) => {
        const { success, msg } = res.data;
        if (success) {
          Notification.success({ content: msg, duration: 300 });
          fetchData();
        }
      });
    }
    //重启
    if (type === 'restart') {
      restartContainer(props.dockerId,record.Id).then((res) => {
        const { success, msg } = res.data;
        if (success) {
          Notification.success({ content: msg, duration: 300 });
          fetchData();
        }
      });
    }
    //停止
    if (type === 'stop') {
      stopContainer(props.dockerId,record.Id).then((res) => {
        const { success, msg } = res.data;
        if (success) {
          Notification.success({ content: msg, duration: 300 });
          fetchData();
        }
      });
    }
    //重命名
    if (type === 'rename') {
      rename(record);
    }

    //删除
    if (type === 'remove') {
      removeData(props.dockerId, record.Id);
    }
  };

  //查看
  const [viewInfoData, setViewInfoData] = useState();
  const [isViewInfo, setisViewInfo] = useState(false);

  function viewInfo(record) {
    setViewInfoData(record);
    setisViewInfo(true);
  }


    //查看日志
    const [viewLogData, setViewLogData] = useState([]);
    const [isViewLog, setIsViewLog] = useState(false);

    //重命名
    const [viewRenameId, setViewRenameId] = useState();
    const [isViewRename, setIsViewRename] = useState(false);
  
    function rename(record) {
      setViewRenameId(record.Id);
      setIsViewRename(true);
    }

    function confirmRename(){
      formRef.current.validate().then((values) => {
        renameContainer(props.dockerId,viewRenameId,values.names).then((res) => {
          const { success, msg } = res.data;
          if (success) {
            Notification.success({ content: msg, duration: 300 });
            setIsViewRename(false)
            fetchData();
          }
        });
      });
    }

  //删除
  function removeData(dockerId, imageId) {
    removeContainer(dockerId, imageId).then((res) => {
      const { success, msg } = res.data;
      if (success) {
        Notification.success({ content: msg, duration: 300 });
        fetchData();
      }
    });
  }

  //获取表格展示列表、绑定操作列回调
  const columns = useMemo(() => getContainerListColumns(t, tableCallback), [t]);

  const [data, setData] = useState([]);

  const [loading, setLoading] = useState(true);
  // const [formParams, setFormParams] = useState({});
  // const [orders, setOrders] = useState(getDefaultOrders());

  useEffect(() => {
    if (props.dockerId && props.isViewContainerList) {
      fetchData();
    }
  }, [props.isViewContainerList, props.dockerId]);

  // 获取数据
  function fetchData() {
    setLoading(true);
    containerList(props.dockerId).then((res) => {
      setData(res.data.data);
      setLoading(false);
    });
  }

  //表格搜索排序回调函数
  // function onChangeTable(
  //   pagination: PaginationProps,
  //   sorter: SorterResult,
  //   _filters: Partial<any>,
  //   _extra: {
  //     currentData: any[];
  //     action: 'paginate' | 'sort' | 'filter';
  //   }
  // ) {
  //   setPatination({
  //     ...pagination,
  //     current: pagination.current,
  //     pageSize: pagination.pageSize,
  //   });
  //   if (sorter) {
  //     if (sorter.direction === 'ascend') {
  //       setOrders([{ column: sorter.field, asc: true }]);
  //     } else if (sorter.direction === 'descend') {
  //       setOrders([{ column: sorter.field, asc: false }]);
  //     } else if (sorter.direction === undefined) {
  //       setOrders(getDefaultOrders());
  //     }
  //   }
  // }

  // //点击搜索按钮
  // function handleSearch(params) {
  //   setPatination({ ...pagination, current: 1 });
  //   setFormParams(params);
  // }

  return (
    <Card>
      {/* <Title heading={6}>{t['list.searchTable']}</Title> */}
      {/* <SearchForm onSearch={handleSearch} /> */}
      {/* <PermissionWrapper
        requiredPermissions={[
          { resource: 'server:dockerDetails', actions: ['add'] },
        ]}
      >
        <div className={styles['button-group']}>
          <Space>
            <Button type="primary" icon={<IconPlus />} onClick={()=>addData()}>
              {t['searchTable.operations.add']}
            </Button>
          </Space>
        </div>
      </PermissionWrapper> */}
      <Table rowKey="id" loading={loading} columns={columns} data={data} />
      <Modal
        // style={{ minHeight: '100%', width: '100%' }}
        title={t['searchTable.info.title']}
        visible={isViewInfo}
        onOk={() => {
          setisViewInfo(false);
        }}
        onCancel={() => {
          setisViewInfo(false);
        }}
        autoFocus={false}
        focusLock={true}
      >
        <Typography>
          <Typography.Paragraph copyable>
            {JSON.stringify(viewInfoData)}
          </Typography.Paragraph>
        </Typography>
      </Modal>
      <Modal
        title={t['searchTable.update.title']}
        visible={isViewRename}
        onOk={() => {
          confirmRename();
        }}
        onCancel={() => {
          setIsViewRename(false);
        }}
        autoFocus={false}
        focusLock={true}
      >
        <Form ref={formRef}>
          <Form.Item
          field={'names'}
          label={t['searchTable.columns.Names']}
          rules={[
            { required: true, message: t['searchForm.dockerName.placeholder'] },
          ]}
          >
            <Input/>
          </Form.Item>
        </Form>
      </Modal>
      <Modal
      title={t['searchTable.info.title']}
      visible={isViewLog}
      onOk={() => {
        setIsViewLog(false);
      }}
      onCancel={() => {
        setIsViewLog(false);
      }}
      style={{ width: "80%", minHeight: "70%" }}
      autoFocus={false}
      focusLock={true}
      maskClosable={false}
    >
      <section className={styles['log-body']}>
        {viewLogData}
      </section>
    </Modal> 
    </Card>
  );
}

export default ContainerList;