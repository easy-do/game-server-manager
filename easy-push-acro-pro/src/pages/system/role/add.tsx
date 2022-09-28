import React, { useContext, useRef } from 'react';
import { Form, FormInstance, Input, Modal, Notification } from '@arco-design/web-react';
import locale from './locale';
import useLocale from '@/utils/useLocale';
import { addRequest } from '@/api/role';
import { GlobalContext } from '@/context';
import DictDataSelect from '@/components/DictCompenent/dictDataSelect';

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
          label={t['searchTable.columns.roleName']}
          field="roleName"
          rules={[
            { required: true, message: t['searchTable.rules.roleName.required'] },
          ]}
        >
          <Input placeholder={t['searchForm.roleName.placeholder']} allowClear />
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.roleKey']}
          field="roleKey"
          rules={[
            { required: true, message: t['searchTable.rules.roleKey.required'] },
          ]}
        >
          <Input placeholder={t['searchForm.roleKey.placeholder']} allowClear />
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.roleSort']}
          field="roleSort"
        >
          <Input placeholder={t['searchForm.roleSort.placeholder']} allowClear />
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.isDefault']}
          field="isDefault"
          rules={[
            { required: true, message: t['searchTable.rules.isDefault.required'] },
          ]}
        >
           <DictDataSelect dictCode={'is_no_select'} placeholder={t['searchForm.isDefault.placeholder']} />
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.status']}
          field="status"
        >
           <DictDataSelect dictCode={'status_select'} placeholder={t['searchForm.status.placeholder']} />
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