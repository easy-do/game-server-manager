import React, { useContext, useRef } from 'react';
import dayjs from 'dayjs';
import { Form, FormInstance, Input, Modal, DatePicker, Select, Notification } from '@arco-design/web-react';
import locale from './locale';
import useLocale from '@/utils/useLocale';
import { addRequest } from '@/api/dictData';
import { GlobalContext } from '@/context';
import DictDataSelect from '@/components/DictCompenent/dictDataSelect';

function AddPage({ dictTypeId, visible, setVisible, successCallBack }) {
  
  const TextArea = Input.TextArea;
  
  const formRef = useRef<FormInstance>();

  const { lang } = useContext(GlobalContext);

  const t = useLocale(locale);

  const handleSubmit = () => {
    formRef.current.validate().then((values) => {
      values.dictTypeId = dictTypeId;
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
          label={t['searchTable.columns.dictLabel']}
          field="dictLabel"
          rules={[
            { required: true, message: t['searchTable.rules.dictLabel.required'] },
          ]}
        >
          <Input placeholder={t['searchForm.dictLabel.placeholder']} allowClear />
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.dictKey']}
          field="dictKey"
          rules={[
            { required: true, message: t['searchTable.rules.dictKey.required'] },
          ]}
        >
          <Input placeholder={t['searchForm.dictKey.placeholder']} allowClear />
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.dictValue']}
          field="dictValue"
          rules={[
            { required: true, message: t['searchTable.rules.dictValue.required'] },
          ]}
        >
          <TextArea placeholder={t['searchForm.dictValue.placeholder']} />
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.dictValueType']}
          field="dictValueType"
          rules={[
            { required: true, message: t['searchTable.rules.dictValueType.required'] },
          ]}
        >
           <DictDataSelect dictCode={'dict_value_type'} placeholder={t['searchForm.dictValueType.placeholder']} />
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.isDefault']}
          field="isDefault"
        >
           <DictDataSelect dictCode={'is_no_select'} placeholder={t['searchForm.isDefault.placeholder']} />
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.status']}
          field="status"
          rules={[
            { required: true, message: t['searchTable.rules.status.required'] },
          ]}
        >
           <DictDataSelect dictCode={'status_select'} placeholder={t['searchForm.status.placeholder']} />
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.dictSort']}
          field="dictSort"
        >
          <Input placeholder={t['searchForm.dictSort.placeholder']} allowClear />
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