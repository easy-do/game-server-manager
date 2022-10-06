import React, { useState, useEffect, useMemo } from 'react';
import {
  Table,
  Card,
  PaginationProps,
  Typography,
} from '@arco-design/web-react';
import useLocale from '@/utils/useLocale';
import SearchForm from './form';
import locale from './locale';
import { getColumns, getDefaultOrders, getSearChColumns, searchConfig } from './constants';
import { managerPage } from '@/api/executeLog';
import { SorterResult } from '@arco-design/web-react/es/Table/interface';
import InfoPage from './info';

const { Title } = Typography;

function ExecuteLogSearchTable(props:{applicationId?:string,visible}) {
  const t = useLocale(locale);

  const {visible=true} = props
  //表格操作按钮回调
  const tableCallback = async (record, type) => {
    //查看
    if (type === 'view') {
      viewInfo(record.id);
    }
  };


  //查看
  const [viewInfoId, setViewInfoId] = useState();
  const [isViewInfo, setisViewInfo] = useState(false);

  function viewInfo(id) {
    setViewInfoId(id);
    setisViewInfo(true);
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
    props.applicationId,
    props.visible,
  ]);

  // 获取数据
  function fetchData() {
    if(visible){
      const { current, pageSize } = pagination;
      setLoading(true);
      const newFormParams:any = {...formParams, applicationId:props.applicationId}
      managerPage({
        currentPage: current,
        pageSize,
        searchParam: newFormParams,
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
      <Table
        rowKey="id"
        loading={loading}
        onChange={onChangeTable}
        pagination={pagination}
        columns={columns}
        data={data}
      />
      <InfoPage
        id={viewInfoId}
        visible={isViewInfo}
        setVisible={setisViewInfo}
        applicationId={props.applicationId}
      />
    </Card>
  );
}

export default ExecuteLogSearchTable;
