import React, { useContext, useRef } from 'react';
import dayjs from 'dayjs';
import { Form, FormInstance, Input, Modal, DatePicker, Select, Notification } from '@arco-design/web-react';
import locale from './locale';
import useLocale from '@/utils/useLocale';
import { addRequest } from '@/api/dictType';
import { GlobalContext } from '@/context';
import { Status } from './constants';

function AddPage(props: { visible; setVisible; successCallback }) {
  
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
          props.successCallback()
        }
      });
    });
  };

  return (
    <Modal
      title={t['searchTable.operations.add']}
      visible={props.visible}
      onOk={() => {
        handleSubmit();
      }}
      onCancel={() => {
        props.setVisible(false);
      }}
      autoFocus={false}
      focusLock={true}
    >
      <Form
        ref={formRef}
        style={{ width: '95%', marginTop: '6px' }}
        labelCol={{ span: lang === 'en-US' ? 7 : 6 }}
        wrapperCol={{ span: lang === 'en-US' ? 17 : 18 }}
        labelAlign="left"
      >
         <Form.Item
          label={t['searchTable.columns.dictName']}
          field="dictName"
          rules={[
            { required: true, message: t['searchTable.rules.dictName.required'] },
          ]}
        >
          <Input placeholder={t['searchForm.dictName.placeholder']} allowClear />
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.dictCode']}
          field="dictCode"
          rules={[
            { required: true, message: t['searchTable.rules.dictCode.required'] },
          ]}
        >
          <Input placeholder={t['searchForm.dictCode.placeholder']} allowClear />
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
          label={t['searchTable.columns.remark']}
          field="remark"
        >
          <Input placeholder={t['searchForm.remark.placeholder']} allowClear />
        </Form.Item>
        
      </Form>
    </Modal>
  );
}

export default AddPage;