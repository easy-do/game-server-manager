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
import { getColumns, getDefaultOrders, searchConfig, selectColumns } from './constants';
import { managerPage, removeRequest } from '@/api/dataSourceManager';
import { SorterResult } from '@arco-design/web-react/es/Table/interface';
import InfoPage from './info';
import UpdatePage from './update';
import AddPage from './add';
import { buildSearchCondition } from '@/utils/searchUtil';

const { Title } = Typography;

function SearchTable() {
  const t = useLocale(locale);

  //表格操作按钮回调
  const tableCallback = async (record, type) => {
    console.log(record, type);
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
  };

  const [viewInfoId, setViewInfoId] = useState();
  const [isViewInfo, setisViewInfo] = useState(false);

 //查看
  function viewInfo(id) {
    setViewInfoId(id);
    setisViewInfo(true);
  }

  const [updateId, setUpdateId] = useState();
  const [isUpdateInfo, setisUpdateInfo] = useState(false);

  //新增
  const [isAddData, setIsAddData] = useState(false);

  function addData(){
    setIsAddData(true);
  }

  //编辑
  function updateInfo(id) {
    setUpdateId(id);
    setisUpdateInfo(true);
  }

  //删除
  function removeData(id){
    removeRequest(id).then((res)=>{
      const { success, msg} = res.data
      if(success){
        Notification.success({ content: msg, duration: 300 })
        fetchData();
      }
    })
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
  const searchParam = buildSearchCondition(selectColumns,searchConfig,orders,formParams)
  setLoading(true);
  managerPage({
    currentPage: current-1,
    pageSize,
    ...searchParam

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
        requiredPermissions={[
          { resource: 'dataSource', actions: ['dataSource:add'] },
        ]}
      >
        <div className={styles['button-group']}>
          <Space>
            <Button type="primary" icon={<IconPlus />} onClick={()=>addData()}>
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
        visible={isAddData}
        setVisible={setIsAddData}
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
      />
    </Card>
  );
}

export default SearchTable;
