import React, { useContext, useRef } from 'react';
import dayjs from 'dayjs';
import { Form, FormInstance, Input, Modal, DatePicker, Select, Notification } from '@arco-design/web-react';
import locale from './locale';
import useLocale from '@/utils/useLocale';
import { addRequest } from '@/api/dockerDetails';
import { GlobalContext } from '@/context';
import { Status } from './constants';
import DictDataSelect from '@/components/DictCompenent/dictDataSelect';
import RequestSelect from '@/components/RequestSelect/RequestSelect';
import { list as clientList } from '@/api/clientInfo';

function AddPage({ visible, setVisible, successCallBack }) {
  
  const TextArea = Input.TextArea;
  
  const formRef = useRef<FormInstance>();

  const { lang } = useContext(GlobalContext);

  const t = useLocale(locale);

  const handleSubmit = () => {
    formRef.current.validate().then((values) => {
      addRequest(values).then((res) => {
        const { success, msg} = res.data
        if(success){
          Notification.success({ content: msg, duration: 300 })
          successCallBack();
        }
      });
    });
  };

  return (
    <Modal
      title={t['searchTable.operations.add']}
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
        ref={formRef}
        style={{ width: '95%', marginTop: '6px' }}
        labelCol={{ span: lang === 'en-US' ? 7 : 6 }}
        wrapperCol={{ span: lang === 'en-US' ? 17 : 18 }}
        labelAlign="left"
      >
        <Form.Item
          label={t['searchTable.columns.dockerName']}
          field="dockerName"
          rules={[
            { required: true, message: t['searchTable.rules.dockerName.required'] },
          ]}
        >
          <Input placeholder={t['searchForm.dockerName.placeholder']} allowClear />
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.dockerHost']}
          field="dockerHost"
          // rules={[
          //   { required: true, message: t['searchTable.rules.dockerHost.required'] },
          // ]}
        >
          <Input placeholder={t['searchForm.dockerHost.placeholder']} allowClear />
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.dockerModel']}
          field="dockerModel"
          initialValue={'socket'}
          rules={[
            { required: true, message: t['searchTable.rules.dockerModel.required'] },
          ]}
        >
          <Select placeholder={t['searchForm.dockerModel.placeholder']} allowClear >
            <Select.Option value={'http'}>API直连</Select.Option>
            <Select.Option value={'socket'}>socket通信</Select.Option>
            </Select>
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.imageList.clientId']}
          field="clientId"
          rules={[
            { required: true, message: t['searchTable.rules.clientId.required'] },
          ]}
        >
          <RequestSelect placeholder={t['searchForm.clientId.placeholder']} lableFiled='clientName' valueFiled='id' request={() => clientList()} />
        </Form.Item>
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
      </Form>
    </Modal>
  );
}

export default AddPage;