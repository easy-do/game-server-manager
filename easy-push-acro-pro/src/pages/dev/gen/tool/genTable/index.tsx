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
import { IconDownload, IconPlus } from '@arco-design/web-react/icon';
import useLocale from '@/utils/useLocale';
import SearchForm from './form';
import locale from './locale';
import styles from './style/index.module.less';
import { getColumns, getDefaultOrders, getSearChColumns } from './constants';
import { batchGenCode, managerPage, removeRequest } from '@/api/genTable';
import { SearchTypeEnum } from '@/utils/systemConstant';
import { SorterResult } from '@arco-design/web-react/es/Table/interface';
import UpdatePage from './update';
import ImportTable from './importTable';
import ViewCodePage from './viewCode';
import GenDataBaseDoc from './genDataBaseDoc';

const { Title } = Typography;

function SearchTable() {
  const t = useLocale(locale);

  //表格操作按钮回调
  const tableCallback = async (record, type) => {
    //查看
    if (type === 'view') {
      viewInfo(record.tableId);
    }
    //编辑
    if (type === 'update') {
      updateInfo(record.tableId);
    }
    //生成
    if (type === 'generate') {
      generateCode(record.tableId);
    }
    //同步
    if (type === 'sync') {
      syncData(record.tableId);
    }
    //删除
    if (type === 'remove') {
      removeData(record.tableId);
    }
  };

  const [viewInfoId, setViewInfoId] = useState();
  const [isViewInfo, setisViewInfo] = useState(false);

  //查看
  function viewInfo(id) {
    setViewInfoId(id);
    setisViewInfo(true);
  }

  //导入表结构
  const [isImportTable, setIsImportTable] = useState(false);

  function importTable() {
    setIsImportTable(true);
  }

  function importSuccess() {
    setIsImportTable(false);
    fetchData();
  }

    //生成数据库文档
    const [isGenDatabaseDoc, setIsGenDatabaseDoc] = useState(false);

    function genDatabaseDoc() {
      setIsGenDatabaseDoc(true);
    }
  
    function genDatabaseDocSuccess() {
      setIsGenDatabaseDoc(false);
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

  function generateCode(id) {
    batchGenCode(id);
  }

  function syncData(id) {
    //
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
  const [formParams, setFormParams] = useState({});
  const [orders, setOrders] = useState(getDefaultOrders());

  useEffect(() => {
    fetchData();
  }, [
    pagination.current,
    pagination.pageSize,
    JSON.stringify(formParams),
    orders,
  ]);

  // 获取数据
  function fetchData() {
    const { current, pageSize } = pagination;
    setLoading(true);
    managerPage({
      currentPage: current,
      pageSize,
      searchParam: formParams,
      orders: orders,
      columns: getSearChColumns(),
      searchConfig: {
        nickName: SearchTypeEnum.LIKE,
        createTime: SearchTypeEnum.BETWEEN,
      },
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
    fetchData();
  }

  return (
    <Card>
      <Title heading={6}>{t['list.searchTable']}</Title>
      <SearchForm onSearch={handleSearch} />
      <PermissionWrapper
        requiredPermissions={[{ resource: 'genTable', actions: ['importTable'] }]}
      >
        <div className={styles['button-group']}>
          <Space>
            <Button
              type="primary"
              icon={<IconPlus />}
              onClick={() => importTable()}
            >
              {t['searchTable.operations.importTable']}
            </Button>
            <Button
              type="primary"
              icon={<IconPlus />}
              onClick={() => genDatabaseDoc()}
            >
              {t['searchTable.operations.genDatabaseDoc']}
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
      <UpdatePage
        id={updateId}
        visible={isUpdateInfo}
        setVisible={setisUpdateInfo}
        successCallBack={updateSuccess}
      />
      <ImportTable
        visible={isImportTable}
        setVisible={setIsImportTable}
        importSuccess={importSuccess}
      />
       <ImportTable
        visible={isImportTable}
        setVisible={setIsImportTable}
        importSuccess={importSuccess}
      />
      <GenDataBaseDoc
        visible={isGenDatabaseDoc}
        setVisible={setIsGenDatabaseDoc}
        importSuccess={genDatabaseDocSuccess}
      />
      <ViewCodePage
        id={viewInfoId}
        visible={isViewInfo}
        setVisible={setisViewInfo}
      />
    </Card>
  );
}

export default SearchTable;
