import React, { useContext, useRef } from 'react';
import { Form, FormInstance, Input, Modal } from '@arco-design/web-react';
import locale from './locale';
import useLocale from '@/utils/useLocale';
import { addRequest } from '@/api/template';
import { GlobalContext } from '@/context';

function AddPage(props: { visible; setVisible }) {
  const formRef = useRef<FormInstance>();

  const { lang } = useContext(GlobalContext);

  const t = useLocale(locale);

  const handleSubmit = () => {
    formRef.current.validate().then((values) => {
      addRequest(values).then((res) => {
        if (res.data.success) {
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
          label={t['searchTable.columns.templateName']}
          field="templateName"
          rules={[
            { required: true, message: t['searchTable.rulesmsg.templateName'] },
          ]}
        >
          <Input placeholder={t['searchForm.templateName.placeholder']} allowClear />
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.templateType']}
          field="templateType"
          rules={[
            { required: true, message: t['searchTable.rulesmsg.templateType'] },
          ]}
        >
          <Input placeholder={t['searchForm.templateType.placeholder']} allowClear />
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.filePath']}
          field="filePath"
          rules={[
            { required: true, message: t['searchTable.rulesmsg.filePath'] },
          ]}
        >
          <Input placeholder={t['searchForm.filePath.placeholder']} allowClear />
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.fileName']}
          field="fileName"
          rules={[
            { required: true, message: t['searchTable.rulesmsg.fileName'] },
          ]}
        >
          <Input placeholder={t['searchForm.fileName.placeholder']} allowClear />
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.version']}
          field="version"
          rules={[
            { required: true, message: t['searchTable.rulesmsg.version'] },
          ]}
        >
          <Input placeholder={t['searchForm.version.placeholder']} allowClear />
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.templateScope']}
          field="templateScope"
          rules={[
            { required: true, message: t['searchTable.rulesmsg.templateScope'] },
          ]}
        >
          <Input placeholder={t['searchForm.templateScope.placeholder']} allowClear />
        </Form.Item>

        <Form.Item
          label={t['searchTable.columns.description']}
          field="description"
        >
          <Input placeholder={t['searchForm.description.placeholder']} allowClear />
        </Form.Item>
      </Form>
    </Modal>
  );
}

export default AddPage;