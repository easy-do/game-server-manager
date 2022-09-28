import React, { useContext, useRef } from 'react';
import { Form, FormInstance, Input, Modal, Notification } from '@arco-design/web-react';
import locale from './locale';
import useLocale from '@/utils/useLocale';
import { addRequest } from '@/api/resource';
import { GlobalContext } from '@/context';
import DictDataSelect from '@/components/DictCompenent/dictDataSelect';
import ResourceTreeSelect from './resourceSelectTree';

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
          label={t['searchTable.columns.resourceName']}
          field="resourceName"
          rules={[
            { required: true, message: t['searchTable.rules.resourceName.required'] },
          ]}
        >
          <Input placeholder={t['searchForm.resourceName.placeholder']} allowClear />
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.resourceCode']}
          field="resourceCode"
          rules={[
            { required: true, message: t['searchTable.rules.resourceCode.required'] },
          ]}
        >
          <Input placeholder={t['searchForm.resourceCode.placeholder']} allowClear />
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.parentId']}
          field="parentId"
          rules={[
            { required: true, message: t['searchTable.rules.parentId.required'] },
          ]}
        >
          <ResourceTreeSelect/>
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.resourceType']}
          field="resourceType"
          rules={[
            { required: true, message: t['searchTable.rules.resourceType.required'] },
          ]}
        >
           <DictDataSelect dictCode={'system_resource_type'} placeholder={t['searchForm.resourceType.placeholder']} />
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.orderNumber']}
          field="orderNumber"
        >
          <Input placeholder={t['searchForm.orderNumber.placeholder']} allowClear />
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.path']}
          field="path"
        >
          <Input placeholder={t['searchForm.path.placeholder']} allowClear />
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.param']}
          field="param"
        >
          <TextArea placeholder={t['searchForm.param.placeholder']} />
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.isCache']}
          field="isCache"
        >
           <DictDataSelect dictCode={'is_no_select'} placeholder={t['searchForm.isCache.placeholder']} />
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.status']}
          field="status"
        >
           <DictDataSelect dictCode={'status_select'} placeholder={t['searchForm.status.placeholder']} />
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.icon']}
          field="icon"
        >
          <Input placeholder={t['searchForm.icon.placeholder']} allowClear />
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.remark']}
          field="remark"
        >
          <TextArea placeholder={t['searchForm.remark.placeholder']} />
        </Form.Item>
      </Form>
    </Modal>
  );
}

export default AddPage;