import React, { useContext, useRef } from 'react';
import dayjs from 'dayjs';
import { Form, FormInstance, Input, Modal, DatePicker, Select, Notification } from '@arco-design/web-react';
import locale from './locale';
import useLocale from '@/utils/useLocale';
import { addRequest } from '@/api/applicationInfo';
import { GlobalContext } from '@/context';
import { Status } from './constants';

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
          label={t['searchTable.columns.applicationId']}
          field="applicationId"
          rules={[
            { required: true, message: t['searchTable.rules.applicationId.required'] },
          ]}
        >
          <Input placeholder={t['searchForm.applicationId.placeholder']} allowClear />
        </Form.Item>
        
        <Form.Item
          label={t['searchTable.columns.applicationName']}
          field="applicationName"
          rules={[
            { required: true, message: t['searchTable.rules.applicationName.required'] },
          ]}
        >
          <Input placeholder={t['searchForm.applicationName.placeholder']} allowClear />
        </Form.Item>
        
        <Form.Item
          label={t['searchTable.columns.userId']}
          field="userId"
          rules={[
            { required: true, message: t['searchTable.rules.userId.required'] },
          ]}
        >
          <Input placeholder={t['searchForm.userId.placeholder']} allowClear />
        </Form.Item>
        
        <Form.Item
          label={t['searchTable.columns.deviceId']}
          field="deviceId"
          rules={[
            { required: true, message: t['searchTable.rules.deviceId.required'] },
          ]}
        >
          <Input placeholder={t['searchForm.deviceId.placeholder']} allowClear />
        </Form.Item>
        
        <Form.Item
          label={t['searchTable.columns.deviceName']}
          field="deviceName"
          rules={[
            { required: true, message: t['searchTable.rules.deviceName.required'] },
          ]}
        >
          <Input placeholder={t['searchForm.deviceName.placeholder']} allowClear />
        </Form.Item>
        
        <Form.Item 
          label={t['searchTable.columns.deviceType']}
          field="deviceType"
          rules={[
            { required: true, message: t['searchTable.rules.deviceType.required'] },
          ]}
        >
          <Select
            placeholder={t['searchForm.deviceType.placeholder']}
            options={Status.map((item, index) => ({
              label: item,
              value: index,
            }))}
            // mode="multiple"
            allowClear
          />
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.appId']}
          field="appId"
          rules={[
            { required: true, message: t['searchTable.rules.appId.required'] },
          ]}
        >
          <Input placeholder={t['searchForm.appId.placeholder']} allowClear />
        </Form.Item>
        
        <Form.Item
          label={t['searchTable.columns.appName']}
          field="appName"
          rules={[
            { required: true, message: t['searchTable.rules.appName.required'] },
          ]}
        >
          <Input placeholder={t['searchForm.appName.placeholder']} allowClear />
        </Form.Item>
        
        <Form.Item 
          label={t['searchTable.columns.status']}
          field="status"
          rules={[
            { required: true, message: t['searchTable.rules.status.required'] },
          ]}
        >
          <Select
            placeholder={t['searchForm.status.placeholder']}
            options={Status.map((item, index) => ({
              label: item,
              value: index,
            }))}
            // mode="multiple"
            allowClear
          />
        </Form.Item>
        <Form.Item 
          label={t['searchTable.columns.publicKey']}
          field="publicKey"
          rules={[
            { required: true, message: t['searchTable.rules.publicKey.required'] },
          ]}
        >
          <TextArea placeholder={t['searchForm.publicKey.placeholder']} />
        </Form.Item>
        <Form.Item 
          label={t['searchTable.columns.privateKey']}
          field="privateKey"
          rules={[
            { required: true, message: t['searchTable.rules.privateKey.required'] },
          ]}
        >
          <TextArea placeholder={t['searchForm.privateKey.placeholder']} />
        </Form.Item>
        <Form.Item 
          label={t['searchTable.columns.appEnvCache']}
          field="appEnvCache"
          rules={[
            { required: true, message: t['searchTable.rules.appEnvCache.required'] },
          ]}
        >
          <TextArea placeholder={t['searchForm.appEnvCache.placeholder']} />
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.isBlack']}
          field="isBlack"
          rules={[
            { required: true, message: t['searchTable.rules.isBlack.required'] },
          ]}
        >
          <Input placeholder={t['searchForm.isBlack.placeholder']} allowClear />
        </Form.Item>
        
        <Form.Item 
          label={t['searchTable.columns.pluginsData']}
          field="pluginsData"
          rules={[
            { required: true, message: t['searchTable.rules.pluginsData.required'] },
          ]}
        >
          <TextArea placeholder={t['searchForm.pluginsData.placeholder']} />
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.createTime']}
          field="createTime"
          rules={[
            { required: true, message: t['searchTable.rules.createTime.required'] },
          ]}
        >
          <DatePicker
            style={{ width: '100%' }}
            showTime={{
              defaultValue: '04:05:06',
            }}
            format='YYYY-MM-DD HH:mm:ss'
          />
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.updateTime']}
          field="updateTime"
          rules={[
            { required: true, message: t['searchTable.rules.updateTime.required'] },
          ]}
        >
          <DatePicker
            style={{ width: '100%' }}
            showTime={{
              defaultValue: '04:05:06',
            }}
            format='YYYY-MM-DD HH:mm:ss'
          />
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.lastUpTime']}
          field="lastUpTime"
          rules={[
            { required: true, message: t['searchTable.rules.lastUpTime.required'] },
          ]}
        >
          <DatePicker
            style={{ width: '100%' }}
            showTime={{
              defaultValue: '04:05:06',
            }}
            format='YYYY-MM-DD HH:mm:ss'
          />
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.delFlag']}
          field="delFlag"
          rules={[
            { required: true, message: t['searchTable.rules.delFlag.required'] },
          ]}
        >
          <Input placeholder={t['searchForm.delFlag.placeholder']} allowClear />
        </Form.Item>
        
      </Form>
    </Modal>
  );
}

export default AddPage;