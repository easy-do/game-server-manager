import React, { useState, useEffect } from 'react';
import {
  Card,
  PaginationProps,
  Button,
  Space,
  Typography,
  Notification,
  Grid,
  Pagination,
  Modal,
} from '@arco-design/web-react';
import PermissionWrapper from '@/components/PermissionWrapper';
import { IconPlus } from '@arco-design/web-react/icon';
import useLocale from '@/utils/useLocale';
import SearchForm from './form';
import locale from './locale';
import styles from './style/index.module.less';
import { getDefaultOrders, getSearChColumns, searchConfig } from './constants';
import { managerPage, removeRequest } from '@/api/serverInfo';
import InfoPage from './info';
import UpdatePage from './update';
import AddPage from './add';
import CardInfo from './card-info';
import ExecScriptPage from '@/components/ExecScript/execScriptPage';
import ExecuteLogSearchTable from '../executeLog';

const { Title } = Typography;

const { Row, Col } = Grid;

function SearchTable() {
  const t = useLocale(locale);

  //表格操作按钮回调
  const tableCallback = async (record, type) => {
    //查看
    if (type === 'view') {
      viewInfo(record.id);
    }

    //编辑
    if (type === 'update') {
      updateInfo(record.id);
    }

    //删除
    if (type === 'remove') {
      removeData(record.id);
    }

    //shell
    if (type === 'shell') {
      // shell(record.id);
      window.open('/xterm');
    }
    //执行脚本
    if (type === 'execScript') {
      execScript(record.id);
    }
        //查看日志
        if (type === 'viewLog') {
          viewLog(record.id);
        }

    
  };

  //查看
  const [viewInfoId, setViewInfoId] = useState();
  const [isViewInfo, setisViewInfo] = useState(false);

  function viewInfo(id) {
    setViewInfoId(id);
    setisViewInfo(true);
  }

  //新增
  const [isAddData, setIsAddData] = useState(false);

  function addData() {
    setIsAddData(true);
  }

  function addDataSuccess() {
    setIsAddData(false);
    fetchData();
  }

  const [updateId, setUpdateId] = useState();
  const [isUpdateInfo, setisUpdateInfo] = useState(false);

  //编辑
  function updateInfo(id) {
    setUpdateId(id);
    setisUpdateInfo(true);
  }

  function updateSuccess() {
    setisUpdateInfo(false);
    fetchData();
  }

  //删除
  function removeData(id) {
    removeRequest(id).then((res) => {
      const { success, msg } = res.data;
      if (success) {
        Notification.success({ content: msg, duration: 300 });
        fetchData();
      }
    });
  }

  //shell
  const [shellId, setShellId] = useState();
  const [isShell, setIsShell] = useState(false);

  function shell(id) {
    setShellId(id);
    setIsShell(true);
  }

  const [execDeviceId, setExecDeviceId] = useState();
  const [isExecSxript, setIsExecScript] = useState(false);

  //执行脚本
  function execScript(id) {
    setExecDeviceId(id);
    setIsExecScript(true);
  }
  function execScriptSuccess() {
    setIsExecScript(false);
    fetchData();
  }

    //查看日志
    const [viewLogId, setViewLogId] = useState();
    const [isViewLog, setIsViewLog] = useState(false);
  
  
    function viewLog(id) {
      setViewLogId(id);
      setIsViewLog(true);
    }

  const [data, setData] = useState([]);
  const [pagination, setPatination] = useState<PaginationProps>({
    sizeCanChange: true,
    showTotal: true,
    pageSize: 20,
    current: 1,
    pageSizeChangeResetCurrent: true,
  });
  const [loading, setLoading] = useState(true);
  const [formParams, setFormParams] = useState({});

  useEffect(() => {
    fetchData();
  }, [pagination.current, pagination.pageSize, JSON.stringify(formParams)]);

  // 获取数据
  function fetchData() {
    const { current, pageSize } = pagination;
    setLoading(true);
    managerPage({
      currentPage: current,
      pageSize,
      searchParam: formParams,
      orders: getDefaultOrders(),
      columns: getSearChColumns(),
      searchConfig: searchConfig(),
    }).then((res) => {
      setData(res.data.data);
      setPatination({
        ...pagination,
        current,
        pageSize,
        total: res.data.total,
      });
      setLoading(false);
    });
  }

  //点击搜索按钮
  function handleSearch(params) {
    setPatination({ ...pagination, current: 1 });
    setFormParams(params);
  }

  //构建卡片列表、绑定操作回调
  const cardList = data.map((item, index) => {
    return (
      <Col xs={24} sm={12} md={8} lg={6} xl={6} xxl={6} key={index}>
        <CardInfo
          card={item}
          loading={loading}
          optionCallCack={tableCallback}
        />
      </Col>
    );
  });

  return (
    <Card>
      <Title heading={6}>{t['list.searchTable']}</Title>
      <SearchForm onSearch={handleSearch} />
      <PermissionWrapper
        requiredPermissions={[
          { resource: 'serverInfo', actions: ['serverInfo:add'] },
        ]}
      >
        <div className={styles['button-group']}>
          <Space>
            <Button
              type="primary"
              icon={<IconPlus />}
              onClick={() => addData()}
            >
              {t['searchTable.operations.add']}
            </Button>
          </Space>
        </div>
      </PermissionWrapper>
      <div className={styles.container}>
        {
          <Row gutter={24} className={styles['card-content']}>
            {cardList}
          </Row>
        }
      </div>
      <Pagination
        sizeCanChange={pagination.sizeCanChange}
        showTotal={pagination.showTotal}
        pageSize={pagination.pageSize}
        current={pagination.current}
        pageSizeChangeResetCurrent={pagination.pageSizeChangeResetCurrent}
        total={pagination.total}
        onChange={(pageNumber, pageSize) =>
          setPatination({
            ...pagination,
            current: pageNumber,
            pageSize: pageSize,
          })
        }
      />
      <AddPage
        visible={isAddData}
        setVisible={setIsAddData}
        successCallBack={addDataSuccess}
      />
      <InfoPage
        id={viewInfoId}
        visible={isViewInfo}
        setVisible={setisViewInfo}
      />
      <UpdatePage
        id={updateId}
        visible={isUpdateInfo}
        setVisible={setisUpdateInfo}
        successCallBack={updateSuccess}
      />
      <ExecScriptPage
        deviceId={execDeviceId}
        deviceType={0}
        visible={isExecSxript}
        setVisible={setIsExecScript}
        successCallBack={execScriptSuccess}
      />
       <Modal
        title={t['searchTable.operations.add']}
        visible={isViewLog}
        onOk={() => {
          setIsViewLog(false);
        }}
        onCancel={() => {
          setIsViewLog(false);
        }}
        autoFocus={false}
        focusLock={true}
        maskClosable={false}
        style={{ width: "100%", minHeight: "100%" }}
      >
        <ExecuteLogSearchTable deviceId={viewLogId} visible={isViewLog} />
      </Modal>
    </Card>
  );
}

export default SearchTable;
