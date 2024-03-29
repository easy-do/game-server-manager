import React, { useContext, useRef } from 'react';
import { Form, FormInstance, Input, Modal, DatePicker, Select, Notification, Upload } from '@arco-design/web-react';
import locale from './locale';
import useLocale from '@/utils/useLocale';
import { addRequest } from '@/api/application';
import { GlobalContext } from '@/context';
import DictDataSelect from '@/components/DictCompenent/dictDataSelect';
import { customRequest, onPreview, onRemove } from '@/components/Upload/fileToBase64';

function AddPage({ visible, setVisible, successCallBack }) {
  
  const TextArea = Input.TextArea;
  
  const formRef = useRef<FormInstance>();

  const { lang } = useContext(GlobalContext);

  const t = useLocale(locale);

  const handleSubmit = () => {
    formRef.current.validate().then((values) => {
      const icon = values.icon;
      const iocnBase64 = icon[0].response;
      addRequest({
        ...values,
        icon: iocnBase64
      }).then((res) => {
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
          label={t['searchTable.columns.applicationName']}
          field="applicationName"
          rules={[
            { required: true, message: t['searchTable.rules.applicationName.required'] },
          ]}
        >
          <Input placeholder={t['searchForm.applicationName.placeholder']} allowClear />
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.icon']}
          field="icon"
          rules={[
            { required: true, message: t['searchTable.rules.icon.required'] },
          ]}
        >
          <Upload
            listType="picture-card"
            multiple={false}
            limit={1}
            autoUpload={true}
            onRemove={onRemove}
            customRequest={customRequest}
            onPreview={onPreview}
          />
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.scope']}
          field="scope"
          initialValue={0}
          rules={[
            { required: true, message: t['searchTable.rules.scope.required'] },
          ]}
        >
          <DictDataSelect placeholder={t['searchForm.scope.placeholder']} dictCode={'application_scope'}/>
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.description']}
          field="description"
          rules={[
            { required: true, message: t['searchTable.rules.description.required'] },
          ]}
        >
          <TextArea placeholder={t['searchForm.description.placeholder']} />
        </Form.Item>
      </Form>
    </Modal>
  );
}

export default AddPage;