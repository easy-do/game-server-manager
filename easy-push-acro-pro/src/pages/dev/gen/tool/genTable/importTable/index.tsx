import React, { useState, useEffect, useMemo } from 'react';
import {
  Table,
  Card,
  PaginationProps,
  Typography,
  Notification,
  Modal,
} from '@arco-design/web-react';
import useLocale from '@/utils/useLocale';
import SearchForm from './form';
import locale from './locale';
import { getColumns} from './constants';
import { dbList, importTable } from '@/api/genTable';
import { SorterResult } from '@arco-design/web-react/es/Table/interface';

const { Title } = Typography;

function ImportTable(props: { visible; setVisible }) {
  
  const t = useLocale(locale);

  const [selectedRowKeys, setSelectedRowKeys] = useState([]);


  //表格操作按钮回调
  const tableCallback = async (record, type) => {
    console.log(record, type);
  };

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
  const [formParams, setFormParams] = useState({dataSourceId:'default'});

  useEffect(() => {
    fetchData();
  }, [
    pagination.current,
    pagination.pageSize,
    JSON.stringify(formParams),
  ]);

  // 获取数据
  function fetchData() {
    const { current, pageSize } = pagination;
    setLoading(true);
    dbList({
      currentPage: current,
      pageSize,
      ...formParams,
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
  }

  //点击搜索按钮
  function handleSearch(params) {
    setPatination({ ...pagination, current: 1 });
    setFormParams(params);
  }

  //确认导入表结构
  const handleSubmit = () => {
    if(selectedRowKeys.length === 0){
      Notification.warning({ content: '未选择任何表', duration: 300 });
      return;
    }
    const param = {
      tables: selectedRowKeys.join(','),
      dataSourceId: formParams.dataSourceId,
    };
    importTable(param).then((res) => {
      const { success, msg } = res.data;
      if (success) {
        Notification.success({ content: msg, duration: 300 });
        props.setVisible(false);
      }
    });
  };

  return (
    <Modal
      title={t['searchTable.operations.importTable']}
      visible={props.visible}
      onOk={() => {
        handleSubmit();
      }}
      onCancel={() => {
        props.setVisible(false);
      }}
      style={{height:'auto',width:'80%'}}
      autoFocus={false}
      focusLock={true}
    >
      <Card>
        <Title heading={6}>{t['list.searchTable']}</Title>
        <SearchForm onSearch={handleSearch} />
        <Table
          rowKey="tableName"
          loading={loading}
          onChange={onChangeTable}
          pagination={pagination}
          columns={columns}
          data={data}
          rowSelection={{
            type:'checkbox',
            selectedRowKeys,
            onChange: (selectedRowKeys) => {
              setSelectedRowKeys(selectedRowKeys);
            },
            onSelect: (selected, record, selectedRows) => {
              console.log('onSelect:', selected, record, selectedRows);
            },
            // checkboxProps: (record) => {
            //   return {
            //     disabled: record.id === '4',
            //   };
            // },
          }}
        />
      </Card>
    </Modal>
  );
}

export default ImportTable;
