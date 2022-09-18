import React, { useContext, useRef } from 'react';
import dayjs from 'dayjs';
import { Form, FormInstance, Input, Modal, DatePicker, Select, Notification } from '@arco-design/web-react';
import locale from './locale';
import useLocale from '@/utils/useLocale';
import { add } from '@/api/clientManager';
import { GlobalContext } from '@/context';
import { Status } from './constants';

function AddPage({ visible, setVisible, successCallBack }) {
  
  const TextArea = Input.TextArea;
  
  const formRef = useRef<FormInstance>();

  const { lang } = useContext(GlobalContext);

  const t = useLocale(locale);

  const handleSubmit = () => {
    formRef.current.validate().then((values) => {
      add(values).then((res) => {
        const { success, msg} = res.data
        if(success){
          Notification.success({ content: msg, duration: 300 })
          setVisible(false);
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
          label={t['searchTable.columns.id']}
          field="id"
          rules={[
            { required: true, message: t['searchTable.rules.id.required'] },
          ]}
        >
          <Input placeholder={t['searchForm.id.placeholder']} allowClear />
        </Form.Item>
        
        <Form.Item
          label={t['searchTable.columns.clientName']}
          field="clientName"
          rules={[
            { required: true, message: t['searchTable.rules.clientName.required'] },
          ]}
        >
          <Input placeholder={t['searchForm.clientName.placeholder']} allowClear />
        </Form.Item>
        
        <Form.Item
          label={t['searchTable.columns.serverId']}
          field="serverId"
          rules={[
            { required: true, message: t['searchTable.rules.serverId.required'] },
          ]}
        >
          <Input placeholder={t['searchForm.serverId.placeholder']} allowClear />
        </Form.Item>
        
        <Form.Item
          label={t['searchTable.columns.serverName']}
          field="serverName"
          rules={[
            { required: true, message: t['searchTable.rules.serverName.required'] },
          ]}
        >
          <Input placeholder={t['searchForm.serverName.placeholder']} allowClear />
        </Form.Item>
        
        <Form.Item
          label={t['searchTable.columns.userName']}
          field="userName"
          rules={[
            { required: true, message: t['searchTable.rules.userName.required'] },
          ]}
        >
          <Input placeholder={t['searchForm.userName.placeholder']} allowClear />
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
          label={t['searchTable.columns.clientData']}
          field="clientData"
          rules={[
            { required: true, message: t['searchTable.rules.clientData.required'] },
          ]}
        >
          <TextArea placeholder={t['searchForm.clientData.placeholder']} />
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
          label={t['searchTable.columns.createBy']}
          field="createBy"
          rules={[
            { required: true, message: t['searchTable.rules.createBy.required'] },
          ]}
        >
          <Input placeholder={t['searchForm.createBy.placeholder']} allowClear />
        </Form.Item>
        
        <Form.Item
          label={t['searchTable.columns.updateBy']}
          field="updateBy"
          rules={[
            { required: true, message: t['searchTable.rules.updateBy.required'] },
          ]}
        >
          <Input placeholder={t['searchForm.updateBy.placeholder']} allowClear />
        </Form.Item>
        
      </Form>
    </Modal>
  );
}

export default AddPage;