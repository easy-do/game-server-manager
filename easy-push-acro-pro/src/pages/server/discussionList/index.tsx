import React, { useState, useEffect, useMemo } from 'react';
import {
  Card,
  PaginationProps,
  Button,
  Space,
  List,
  Skeleton,
  Avatar,
  Pagination,
} from '@arco-design/web-react';
import PermissionWrapper from '@/components/PermissionWrapper';
import { IconDown, IconLoading, IconPlus } from '@arco-design/web-react/icon';
import useLocale from '@/utils/useLocale';
import locale from '../discussion/locale';
import styles from '../discussion/style/index.module.less';
import { getDefaultOrders, getSearChColumns, searchConfig } from '../discussion/constants';
import { page } from '@/api/discussion';
import { SorterResult } from '@arco-design/web-react/es/Table/interface';
import Paragraph from '@arco-design/web-react/es/Typography/paragraph';


function DiscussionList() {
  const t = useLocale(locale);


  function viewInfo(id) {
    window.open('/server/discussion/discussionInfo/'+id)
  }

    //新增
    const [isAddData, setIsAddData] = useState(false);

    function addData(){
      setIsAddData(true);
    }
    
    function addDataSuccess() {
      setIsAddData(false);
      fetchData();
    }

  const [data, setData] = useState([]);
  const [pagination, setPatination] = useState<PaginationProps>({
    sizeCanChange: true,
    showTotal: true,
    pageSize: 5,
    current: 1,
    simple:true,
    pageSizeChangeResetCurrent: true,
    onChange:(pageNumber, pageSize)=> setPatination({
      ...pagination,
      current:pageNumber,
      pageSize
    })
  });
  const [loading, setLoading] = useState(true);
  const [orders, setOrders] = useState(getDefaultOrders());

  useEffect(() => {
    fetchData();
  }, [
    pagination.current,
    pagination.pageSize,
  ]);

  // 获取数据
  function fetchData() {
    const { current, pageSize } = pagination;
    setLoading(true);
    page({
      currentPage: current,
      pageSize,
      searchParam: {},
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

  return (
    <Card>
      <PermissionWrapper
        requiredPermissions={[
          { resource: 'server:discussion', actions: ['add'] },
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
      <List
      pagination={pagination}
      dataSource={data}
      render={(item, index) => {
        return (
          <List.Item 
          style={{ maxWidth: '100%', maxHeight: '100%' }}
          onClick={()=>viewInfo(item.id)} key={index} 
          // style={{ padding: '24px 20px 24px 0px' }}
          >
            
            {loading ? (
              <Skeleton
                animation
                text={{ width: ['60%', '90%'], rows: 2 }}
                image={{ shape: 'circle' }}
              />
            ) : (
              <List.Item.Meta
                className={styles['list-meta-ellipsis']}
                avatar={
                  <Avatar size={48}>
                    <img src={item.createName} />
                  </Avatar>
                }
                title={item.title}
                description={
                  <Paragraph
                    ellipsis={{ rows: 1 }}
                    type="secondary"
                    style={{ fontSize: '12px', margin: 0 }}
                  >
                    {item.createName} -- {item.createTime} 
                  </Paragraph>
                }
              />
            )}
          </List.Item>
        );
      }}
    />
    </Card>
  );
}

export default DiscussionList;
