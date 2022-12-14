import React, { useContext, useRef } from 'react';
import { Form, FormInstance, Input, Modal, DatePicker, Select, Notification } from '@arco-design/web-react';
import locale from './locale';
import useLocale from '@/utils/useLocale';
import { addRequest } from '@/api/dataSourceManager';
import { GlobalContext } from '@/context';
import DictDataSelect from '@/components/DictCompenent/dictDataSelect';

function AddPage(props: { visible; setVisible }) {
  
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
          props.setVisible(false);
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
          label={t['searchTable.columns.delFlag']}
          field="delFlag"
          rules={[
            { required: true, message: t['searchTable.rules.delFlag.required'] },
          ]}
        >
          <Input placeholder={t['searchForm.delFlag.placeholder']} allowClear />
        </Form.Item>
        
        <Form.Item
          label={t['searchTable.columns.remark']}
          field="remark"
          rules={[
            { required: true, message: t['searchTable.rules.remark.required'] },
          ]}
        >
          <Input placeholder={t['searchForm.remark.placeholder']} allowClear />
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
          label={t['searchTable.columns.updateBy']}
          field="updateBy"
          rules={[
            { required: true, message: t['searchTable.rules.updateBy.required'] },
          ]}
        >
          <Input placeholder={t['searchForm.updateBy.placeholder']} allowClear />
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
          label={t['searchTable.columns.createBy']}
          field="createBy"
          rules={[
            { required: true, message: t['searchTable.rules.createBy.required'] },
          ]}
        >
          <Input placeholder={t['searchForm.createBy.placeholder']} allowClear />
        </Form.Item>
        
        <Form.Item 
          label={t['searchTable.columns.status']}
          field="status"
          rules={[
            { required: true, message: t['searchTable.rules.status.required'] },
          ]}
        >
          <DictDataSelect
            placeholder={t['searchForm.status.placeholder']}
            dictCode={'status_select'}
          />
        </Form.Item>
        <Form.Item 
          label={t['searchTable.columns.params']}
          field="params"
          rules={[
            { required: true, message: t['searchTable.rules.params.required'] },
          ]}
        >
          <TextArea placeholder={t['searchForm.params.placeholder']} />
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.password']}
          field="password"
          rules={[
            { required: true, message: t['searchTable.rules.password.required'] },
          ]}
        >
          <Input placeholder={t['searchForm.password.placeholder']} allowClear />
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
          label={t['searchTable.columns.url']}
          field="url"
          rules={[
            { required: true, message: t['searchTable.rules.url.required'] },
          ]}
        >
          <Input placeholder={t['searchForm.url.placeholder']} allowClear />
        </Form.Item>
        
        <Form.Item 
          label={t['searchTable.columns.sourceType']}
          field="sourceType"
          rules={[
            { required: true, message: t['searchTable.rules.sourceType.required'] },
          ]}
        >
          <DictDataSelect
            placeholder={t['searchForm.sourceType.placeholder']}
            dictCode={'status_select'}
          />
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.sourceCode']}
          field="sourceCode"
          rules={[
            { required: true, message: t['searchTable.rules.sourceCode.required'] },
          ]}
        >
          <Input placeholder={t['searchForm.sourceCode.placeholder']} allowClear />
        </Form.Item>
        
        <Form.Item
          label={t['searchTable.columns.sourceName']}
          field="sourceName"
          rules={[
            { required: true, message: t['searchTable.rules.sourceName.required'] },
          ]}
        >
          <Input placeholder={t['searchForm.sourceName.placeholder']} allowClear />
        </Form.Item>
        
        <Form.Item
          label={t['searchTable.columns.id']}
          field="id"
          rules={[
            { required: true, message: t['searchTable.rules.id.required'] },
          ]}
        >
          <Input placeholder={t['searchForm.id.placeholder']} allowClear />
        </Form.Item>
        
      </Form>
    </Modal>
  );
}

export default AddPage;