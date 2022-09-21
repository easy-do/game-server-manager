import React, { useState, useEffect, useMemo } from 'react';
import {
  Table,
  Card,
  PaginationProps,
  Button,
  Space,
  Typography,
  Notification,
  Modal,
} from '@arco-design/web-react';
import PermissionWrapper from '@/components/PermissionWrapper';
import { IconDownload, IconPlus } from '@arco-design/web-react/icon';
import useLocale from '@/utils/useLocale';
import SearchForm from './form';
import locale from './locale';
import styles from './style/index.module.less';
import { getColumns, getDefaultOrders, getSearChColumns, searchConfig } from './constants';
import { managerPage, removeRequest } from '@/api/dictType';
import { SearchTypeEnum } from '@/utils/systemConstant';
import { SorterResult } from '@arco-design/web-react/es/Table/interface';
import UpdatePage from './update';
import AddPage from './add';
import DictDataSearchTable from '../data';

const { Title } = Typography;

function SearchTable() {
  const t = useLocale(locale);

  //表格操作按钮回调
  const tableCallback = async (record, type) => {
    console.log(record, type);
    //编辑
    if (type === 'update') {
      updateInfo(record.id);
    }
    //删除
    if (type === 'remove') {
      removeData(record.id);
    }
    //编辑字典数据
    if (type === 'updateDictData') {
      updateDictData(record.id);
    }

  };


  const [updateId, setUpdateId] = useState();
  const [isUpdateInfo, setisUpdateInfo] = useState(false);

  //新增
  const [isAddData, setIsAddData] = useState(false);

  function addData() {
    setIsAddData(true);
  }

  function addSuccessCallback(){
    setIsAddData(false);
    fetchData();
  }

  //编辑
  function updateInfo(id) {
    setUpdateId(id);
    setisUpdateInfo(true);
  }

  function updateSuccessCallback(){
    setisUpdateInfo(false);
    fetchData();
  }


  //删除
  function removeData(id) {
    removeRequest(id).then((res) => {
      const { success, msg } = res.data
      if (success) {
        Notification.success({ content: msg, duration: 300 })
        fetchData();
      }
    })
  }

  const [dictId, setDictId] = useState();
  const [isUpdateDictData, setIsUpdateDictData] = useState(false);

  //编辑字典数据
  function updateDictData(dictId) {
    setDictId(dictId)
    setIsUpdateDictData(true)
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
      searchConfig: searchConfig
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
      {/* <PermissionWrapper
        requiredPermissions={[
          { resource: 'uc:dictType', actions: ['add'] },
        ]}
      > */}
        <div className={styles['button-group']}>
          <Space>
            <Button type="primary" icon={<IconPlus />} onClick={() => addData()}>
              {t['searchTable.operations.add']}
            </Button>
          </Space>
        </div>
      {/* </PermissionWrapper> */}
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
        successCallback={addSuccessCallback}
      />
      <UpdatePage
        id={updateId}
        visible={isUpdateInfo}
        setVisible={setisUpdateInfo}
        successCallback={updateSuccessCallback}
      />
      <Modal
        style={{minHeight:'100%',width:'100%'}}
        title={t['searchTable.update.title']}
        visible={isUpdateDictData}
        onOk={() => {
          setIsUpdateDictData(false);
        }}
        onCancel={() => {
          setIsUpdateDictData(false);
        }}
        autoFocus={false}
        focusLock={true}
      >
        <DictDataSearchTable dictTypeId={dictId} />
      </Modal>

    </Card>
  );
}

export default SearchTable;
