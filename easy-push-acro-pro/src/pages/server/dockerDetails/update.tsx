import {
  DatePicker,
  Form,
  FormInstance,
  Input,
  Modal,
  Select,
  Spin,
  Notification,
} from '@arco-design/web-react';
import locale from './locale';
import useLocale from '@/utils/useLocale';
import { updateRequest, infoRequest } from '@/api/dockerDetails';
import { GlobalContext } from '@/context';
import { Status } from './constants';
import { useContext, useEffect, useRef, useState } from 'react';
import React from 'react';
import DictDataSelect from '@/components/DictCompenent/dictDataSelect';
import RequestSelect from '@/components/RequestSelect/RequestSelect';
import { list as clientList } from '@/api/clientInfo';

function UpdatePage({ id, visible, setVisible, successCallBack }) {
  const TextArea = Input.TextArea;

  const formRef = useRef<FormInstance>();

  const { lang } = useContext(GlobalContext);

  const [loading, setLoading] = React.useState(false);

  const [dockerModel, setDockerModel] = useState('http');

  const chageDockerModel = (value) => {
    setDockerModel(value);
  };

  //加载数据
  function fetchData() {
    if (id !== undefined && visible) {
      setLoading(true);
      infoRequest(id).then((res) => {
        const { success, data } = res.data;
        if (success) {
          setDockerModel(data.dockerModel);
          formRef.current.setFieldsValue(data);
        }
        setLoading(false);
      });
    }
  }

  useEffect(() => {
    fetchData();
  }, [id, visible]);

  const t = useLocale(locale);

  //提交修改
  const handleSubmit = () => {
    formRef.current.validate().then((values) => {
      updateRequest(values).then((res) => {
        const { success, msg } = res.data;
        if (success) {
          Notification.success({ content: msg, duration: 300 });
          successCallBack();
        }
      });
    });
  };

  return (
    <Modal
      title={t['searchTable.update.title']}
      visible={visible}
      onOk={() => {
        handleSubmit();
      }}
      onCancel={() => {
        setVisible(false);
      }}
      autoFocus={false}
      focusLock={true}
      maskClosable={false}
    >
      <Form
        style={{ width: '95%', marginTop: '6px' }}
        labelCol={{ span: lang === 'en-US' ? 7 : 6 }}
        wrapperCol={{ span: lang === 'en-US' ? 17 : 18 }}
        ref={formRef}
        labelAlign="left"
      >
        <Spin tip="loading Data..." loading={loading}>
          <Form.Item label={t['searchTable.columns.id']} field="id" hidden>
            <Input placeholder={t['searchForm.id.placeholder']} allowClear />
          </Form.Item>
          <Form.Item
            label={t['searchTable.columns.dockerName']}
            field="dockerName"
            rules={[
              {
                required: true,
                message: t['searchTable.rules.dockerName.required'],
              },
            ]}
          >
            <Input
              placeholder={t['searchForm.dockerName.placeholder']}
              allowClear
            />
          </Form.Item>
          <Form.Item
            label={t['searchTable.columns.dockerModel']}
            field="dockerModel"
            initialValue={'socket'}
            rules={[
              {
                required: true,
                message: t['searchTable.rules.dockerModel.required'],
              },
            ]}
          >
            <Select
              placeholder={t['searchForm.dockerModel.placeholder']}
              onChange={chageDockerModel}
              allowClear
            >
              <Select.Option value={'http'}>API直连</Select.Option>
              <Select.Option value={'socket'}>客户端</Select.Option>
            </Select>
          </Form.Item>
          {dockerModel === 'http' ? (
            <Form.Item
              label={t['searchTable.columns.dockerHost']}
              field="dockerHost"
              rules={[
                {
                  required: true,
                  message: t['searchTable.rules.dockerHost.required'],
                },
              ]}
            >
              <Input
                placeholder={t['searchForm.dockerHost.placeholder']}
                allowClear
              />
            </Form.Item>
          ) : null}
          {dockerModel === 'socket' ? (
            <Form.Item
              label={t['searchTable.columns.imageList.clientId']}
              field="clientId"
              rules={[
                {
                  required: true,
                  message: t['searchTable.rules.clientId.required'],
                },
              ]}
            >
              <RequestSelect
                disabled
                placeholder={t['searchForm.clientId.placeholder']}
                lableFiled="clientName"
                valueFiled="id"
                request={() => clientList()}
              />
            </Form.Item>
          ) : null}
          {/* <Form.Item
          label={t['searchTable.columns.dockerIsSsl']}
          field="dockerIsSsl"
        >
           <DictDataSelect dictCode={'is_no_select'} placeholder={t['searchForm.dockerIsSsl.placeholder']} />
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.dockerCert']}
          field="dockerCert"
        >
          <Input placeholder={t['searchForm.dockerCert.placeholder']} allowClear />
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.dockerCertPassword']}
          field="dockerCertPassword"
        >
          <Input placeholder={t['searchForm.dockerCertPassword.placeholder']} allowClear />
        </Form.Item> */}
        </Spin>
      </Form>
    </Modal>
  );
}

export default UpdatePage;
