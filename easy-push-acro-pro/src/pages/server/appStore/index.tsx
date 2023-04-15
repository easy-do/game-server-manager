import React, { useState, useEffect } from 'react';
import {
  Card,
  PaginationProps,
  Grid,
  Pagination,
} from '@arco-design/web-react';
import useLocale from '@/utils/useLocale';
import locale from '../appScript/locale';
import styles from './style/index.module.less';
import {
  getDefaultOrders,
  getSearChColumns,
  searchConfig,
} from '../appScript/constants';
import { page } from '@/api/appStore';
import CardInfo from './card-info';


const { Row, Col } = Grid;

function SearchTable() {
  const t = useLocale(locale);

  const [data, setData] = useState([]);
  const [pagination, setPatination] = useState<PaginationProps>({
    sizeCanChange: true,
    showTotal: true,
    pageSize: 20,
    current: 1,
    pageSizeChangeResetCurrent: true,
  });
  const [loading, setLoading] = useState(true);
  const [formParams, _setFormParams] = useState({});

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
    page({
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



  //安装应用
  function installApplication(id) {
    window.location.href = '/server/applicationVersionDetails?id='+id;
  }


  //构建卡片列表、绑定操作回调
  const cardList = data.map((item, index) => {
    return <Col xs={12} sm={12} md={12} lg={12} xl={12} xxl={12} key={index}>
           <CardInfo data={item} loading={loading} installApplication={installApplication}/>
           </Col>;
  })

  return (
    <Card>
      <div className={styles.container}>
        {<Row gutter={24} className={styles['card-content']}>
          {cardList}
        </Row>}
      </div>
      <Pagination
        sizeCanChange={pagination.sizeCanChange}
        showTotal={pagination.showTotal}
        pageSize={pagination.pageSize}
        current={pagination.current}
        pageSizeChangeResetCurrent={pagination.pageSizeChangeResetCurrent}
        total={pagination.total}
        onChange={(pageNumber, pageSize) => setPatination({
          ...pagination,
          current: pageNumber,
          pageSize: pageSize,
        })} />
    </Card>
  );
}

export default SearchTable;
