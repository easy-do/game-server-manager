import React, { useState, useEffect, useMemo } from 'react';
import {
  Table,
  Card,
  PaginationProps,
  Button,
  Space,
  Typography,
  Notification,
} from '@arco-design/web-react';
import PermissionWrapper from '@/components/PermissionWrapper';
import { IconPlus } from '@arco-design/web-react/icon';
import useLocale from '@/utils/useLocale';
import SearchForm from './form';
import locale from './locale';
import styles from './style/index.module.less';
import {
  getColumns,
  getDefaultOrders,
  getSearChColumns,
  searchConfig,
} from './constants';
import { managerPage, removeRequest } from '@/api/applicationVersion';
import { SorterResult } from '@arco-design/web-react/es/Table/interface';
import InfoPage from './info';
import UpdatePage from './update';
import AddPage from './add';
import AuditModal from '@/pages/audit/auditModal';
import { commitAudit } from '@/api/audit';

const { Title } = Typography;

function ApplicationVersionSearchTable({ applicationId, visible }) {
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
    //提交审核
    if (type === 'submitAudit') {
      submitAudit({ id: record.id, auditType: 3 });
    }
    //审核
    if (type === 'audit') {
      audit(record.id);
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

  //提交审核
  function submitAudit(id) {
    commitAudit(id).then((res) => {
      const { success, msg } = res.data;
      if (success) {
        Notification.success({ content: msg, duration: 300 });
        fetchData();
      }
    });
  }

  //审核
  function audit(id) {
    setAuditId(id);
    setIsaudit(true);
  }

  const [auditId, setAuditId] = useState();
  const [isAudit, setIsaudit] = useState(false);

  function auditSuccess() {
    setIsaudit(false);
    fetchData();
  }
  const [versionId, setVersionId] = useState();
  const [isVersion, setIsVersion] = useState(false);

  //获取表格展示列表、绑定操作列回调
  const columns = useMemo(() => getColumns(t, tableCallback), [t]);

  const [data, setData] = useState([]);
  const [pagination, setPatination] = useState<PaginationProps>({
    sizeCanChange: true,
    showTotal: true,
    pageSize: 10,
    current: 1,
    pageSizeChangeResetCurrent: true,
  });
  const [loading, setLoading] = useState(true);
  const [formParams, setFormParams] = useState({
    applicationId: applicationId,
  });
  const [orders, setOrders] = useState(getDefaultOrders());

  useEffect(() => {
    fetchData();
  }, [
    applicationId,
    visible,
    pagination.current,
    pagination.pageSize,
    JSON.stringify(formParams),
    orders,
  ]);

  // 获取数据
  function fetchData() {
    const { current, pageSize } = pagination;
    setLoading(true);
    formParams.applicationId = applicationId;
    managerPage({
      currentPage: current,
      pageSize,
      searchParam: formParams,
      orders: orders,
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

  //表格搜索排序回调函数
  function onChangeTable(
    pagination: PaginationProps,
    sorter: SorterResult,
    _filters: Partial<any>,
    _extra: {
      currentData: any[];
      action: 'paginate' | 'sort' | 'filter';
    }
  ) {
    setPatination({
      ...pagination,
      current: pagination.current,
      pageSize: pagination.pageSize,
    });
    if (sorter) {
      if (sorter.direction === 'ascend') {
        setOrders([{ column: sorter.field, asc: true }]);
      } else if (sorter.direction === 'descend') {
        setOrders([{ column: sorter.field, asc: false }]);
      } else if (sorter.direction === undefined) {
        setOrders(getDefaultOrders());
      }
    }
  }

  //点击搜索按钮
  function handleSearch(params) {
    setPatination({ ...pagination, current: 1 });
    setFormParams(params);
  }

  return (
    <Card>
      <Title heading={6}>{t['list.searchTable']}</Title>
      <SearchForm onSearch={handleSearch} applicationId={applicationId} />
      <PermissionWrapper
        requiredPermissions={[
          { resource: 'application', actions: ['application:add'] },
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
      <Table
        rowKey="id"
        loading={loading}
        onChange={onChangeTable}
        pagination={pagination}
        columns={columns}
        data={data}
      />
      <AddPage
        applicationId={applicationId}
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
      <AuditModal
        title="应用审核"
        id={auditId}
        auditType={3}
        visible={isAudit}
        setVisible={setIsaudit}
        successCallBack={auditSuccess}
      />
    </Card>
  );
}

export default ApplicationVersionSearchTable;
