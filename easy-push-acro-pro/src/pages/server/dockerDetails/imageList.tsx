import React, { useState, useEffect, useMemo } from 'react';
import {
  Table,
  Card,
  Typography,
  Notification,
  Modal,
} from '@arco-design/web-react';
import { IconPlus } from '@arco-design/web-react/icon';
import useLocale from '@/utils/useLocale';
import locale from './locale';
import { getImageListColumns } from './constants';
import { imagesList, removeImage } from '@/api/dockerApi';

const { Title } = Typography;

function ImageList(props: { dockerId: any }) {
  const t = useLocale(locale);

  //表格操作按钮回调
  const tableCallback = async (record, type) => {
    //查看
    if (type === 'view') {
      viewInfo(record);
    }

    //删除
    if (type === 'remove') {
      removeData(props.dockerId, record.Id);
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
  function removeData(dockerId, imageId) {
    removeImage(dockerId, imageId).then((res) => {
      const { success, msg } = res.data;
      if (success) {
        Notification.success({ content: msg, duration: 300 });
        fetchData();
      }
    });
  }

  //获取表格展示列表、绑定操作列回调
  const columns = useMemo(() => getImageListColumns(t, tableCallback), [t]);

  const [data, setData] = useState([]);

  const [loading, setLoading] = useState(true);
  // const [formParams, setFormParams] = useState({});
  // const [orders, setOrders] = useState(getDefaultOrders());

  useEffect(() => {
    if (props.dockerId) {
      fetchData();
    }
  }, []);

  // 获取数据
  function fetchData() {
    setLoading(true);
    imagesList(props.dockerId).then((res) => {
      setData(res.data.data);
      setLoading(false);
    });
  }

  //表格搜索排序回调函数
  // function onChangeTable(
  //   pagination: PaginationProps,
  //   sorter: SorterResult,
  //   _filters: Partial<any>,
  //   _extra: {
  //     currentData: any[];
  //     action: 'paginate' | 'sort' | 'filter';
  //   }
  // ) {
  //   setPatination({
  //     ...pagination,
  //     current: pagination.current,
  //     pageSize: pagination.pageSize,
  //   });
  //   if (sorter) {
  //     if (sorter.direction === 'ascend') {
  //       setOrders([{ column: sorter.field, asc: true }]);
  //     } else if (sorter.direction === 'descend') {
  //       setOrders([{ column: sorter.field, asc: false }]);
  //     } else if (sorter.direction === undefined) {
  //       setOrders(getDefaultOrders());
  //     }
  //   }
  // }

  // //点击搜索按钮
  // function handleSearch(params) {
  //   setPatination({ ...pagination, current: 1 });
  //   setFormParams(params);
  // }

  return (
    <Card>
      <Title heading={6}>{t['list.searchTable']}</Title>
      {/* <SearchForm onSearch={handleSearch} /> */}
      {/* <PermissionWrapper
        requiredPermissions={[
          { resource: 'server:dockerDetails', actions: ['add'] },
        ]}
      >
        <div className={styles['button-group']}>
          <Space>
            <Button type="primary" icon={<IconPlus />} onClick={()=>addData()}>
              {t['searchTable.operations.add']}
            </Button>
          </Space>
        </div>
      </PermissionWrapper> */}
      <Table rowKey="id" loading={loading} columns={columns} data={data} />
      <Modal
        // style={{ minHeight: '100%', width: '100%' }}
        title={t['searchTable.info.title']}
        visible={isViewInfo}
        onOk={() => {
          setisViewInfo(false);
        }}
        onCancel={() => {
          setisViewInfo(false);
        }}
        autoFocus={false}
        focusLock={true}
      >
        <Typography>
          <Typography.Paragraph copyable>
            {JSON.stringify(viewInfoData)}
          </Typography.Paragraph>
        </Typography>
      </Modal>
    </Card>
  );
}

export default ImageList;
