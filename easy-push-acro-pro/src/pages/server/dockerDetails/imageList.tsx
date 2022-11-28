import React, { useState, useEffect, useMemo, useRef } from 'react';
import {
  Table,
  Card,
  Typography,
  Notification,
  Modal,
  Space,
  Button,
  Form,
  Input,
} from '@arco-design/web-react';
import { IconPlus } from '@arco-design/web-react/icon';
import useLocale from '@/utils/useLocale';
import locale from './locale';
import { getImageListColumns } from './constants';
import { imagesList, removeImage } from '@/api/dockerApi';
import PermissionWrapper from '@/components/PermissionWrapper';
import styles from './style/index.module.less';
import { socketAddress } from '@/utils/systemConstant';
import LogCompennet from '@/components/LogCompenent/logCompennet';
import { closeWebSocket, createWebSocket } from '@/utils/webSocket';

const { Title } = Typography;

function ImageList(props: { isViewImageList: boolean, dockerId: any }) {

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


  useEffect(() => {
    if (props.dockerId && props.isViewImageList) {
      fetchData();
    }
  }, [props.isViewImageList, props.dockerId]);

  // 获取数据
  function fetchData() {
    setLoading(true);
    imagesList(props.dockerId).then((res) => {
      setData(res.data.data);
      setLoading(false);
    });
  }

  //pull镜像
  const [viewPullImage, setViewPullImage] = useState(false);
  const [pullImageStatus, setPullImageStatus] = useState(false);
  const [pullImageLogData, setPullImageLogData] = useState([]);
  let repository = '';

  const pullImageLogCache = []


  function pullImageButton() {
    setViewPullImage(true)
  }

  function onCancelPullImage() {
    setViewPullImage(false)
    setPullImageStatus(false)
    setPullImageLogData([])
    closeWebSocket()
  }

  function setRepositoryValue(value, _e) {
    repository = value;
  }

  function okPullImage() {
    if (!pullImageStatus) {
      setPullImageLogData([])
      setViewPullImage(true)
      const messageParam = {
        "token": localStorage.getItem("token") ? localStorage.getItem("token") : "",
        "type": "pullImage",
        "data": {
          "dockerId": props.dockerId,
          "repository": repository
        }
      }
      createWebSocket(socketAddress,JSON.stringify(messageParam),(event)=>{
        setPullImageStatus(true)
        const serverMessage = JSON.parse(event.data);
        if(serverMessage.type === 'sync_result_end'){
          Notification.success({ content: "完成", duration: 300 });
        }
        pullImageLogCache.push(serverMessage.data)
        setPullImageLogData([])
        setPullImageLogData(pullImageLogCache)
      });
    } else {
      onCancelPullImage()
    }
  }


  return (
    <Card>
      {/* <Title heading={6}>{t['list.searchTable']}</Title> */}
      {/* <SearchForm onSearch={handleSearch} /> */}
      <PermissionWrapper
        requiredPermissions={[
          { resource: 'server:dockerDetails', actions: ['add'] },
        ]}
      >
        <div className={styles['button-group']}>
          <Space>
            <Button type="primary" icon={<IconPlus />} onClick={() => pullImageButton()}>
              {t['searchTable.operations.pullImage']}
            </Button>
          </Space>
        </div>
      </PermissionWrapper>
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
      <Modal
        title={t['searchTable.info.title']}
        visible={viewPullImage}
        onOk={() => {
          okPullImage();
        }}
        onCancel={() => {
          onCancelPullImage();
        }}
        style={{ width: "80%", minHeight: "70%" }}
        autoFocus={false}
        focusLock={true}
        maskClosable={false}
      >
        {pullImageStatus ?
          <LogCompennet values={pullImageLogData}/>
          :
          <Form
          >
            <Form.Item
              label={t['searchTable.columns.repository']}
              field='repository'
              initialValue={repository}
              required
            >
              <Input onChange={(value, e) => setRepositoryValue(value, e)} />
            </Form.Item>
          </Form>
        }

      </Modal>
    </Card>
  );
}

export default ImageList;
