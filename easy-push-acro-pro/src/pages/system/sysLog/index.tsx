import React, { useState, useEffect, useMemo } from 'react';
import {
  Table,
  Card,
  PaginationProps,
  Typography,
  Notification,
  Button,
  Space,
  Popconfirm,
} from '@arco-design/web-react';
import useLocale from '@/utils/useLocale';
import SearchForm from './form';
import locale from './locale';
import {
  getColumns,
  getDefaultOrders,
  getSearChColumns,
  searchConfig,
} from './constants';
import { clean, managerPage, removeRequest } from '@/api/sysLog';
import { SorterResult } from '@arco-design/web-react/es/Table/interface';
import InfoPage from './info';

const { Title } = Typography;

import styles from './style/index.module.less';
import { IconDelete } from '@arco-design/web-react/icon';

function SearchTable() {
  const t = useLocale(locale);

  //表格操作按钮回调
  const tableCallback = async (record, type) => {
    //查看
    if (type === 'view') {
      viewInfo(record);
    }

    //删除
    if (type === 'remove') {
      removeData(record.id);
    }

    //清空
    if (type === 'clean') {
      clean().then((res)=>{
        const { success, msg } = res.data
        if(success){
          Notification.success({content:msg})
          fetchData()
        }
      });
    }
  };

  //查看
  const [viewInfoData, setViewInfoData] = useState();
  const [isViewInfo, setisViewInfo] = useState(false);

  function viewInfo(record) {
    setViewInfoData(record);
    setisViewInfo(true);
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
      <SearchForm onSearch={handleSearch} />
      <div className={styles['button-group']}>
        <Space>
          <Popconfirm
            title={t['searchTable.columns.operations.clean.confirm']}
            onOk={() => tableCallback(null, 'clean')}
          >
            <Button type="primary" status="warning" icon={<IconDelete />}>
              {t['searchTable.operations.clean']}
            </Button>
          </Popconfirm>
        </Space>
      </div>
      <Table
        rowKey="id"
        loading={loading}
        onChange={onChangeTable}
        pagination={pagination}
        columns={columns}
        data={data}
      />
      <InfoPage
        data={viewInfoData}
        visible={isViewInfo}
        setVisible={setisViewInfo}
      />
    </Card>
  );
}

export default SearchTable;
