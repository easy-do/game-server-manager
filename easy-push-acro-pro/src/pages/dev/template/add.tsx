import React, { useContext, useRef } from 'react';
import { Form, FormInstance, Input, Modal } from '@arco-design/web-react';
import locale from './locale';
import useLocale from '@/utils/useLocale';
import { add } from '@/api/template';
import { GlobalContext } from '@/context';

function AddPage(props: { visible; setVisible }) {
  const formRef = useRef<FormInstance>();

  const { lang } = useContext(GlobalContext);

  const t = useLocale(locale);

  const handleSubmit = () => {
    formRef.current.validate().then((values) => {
      add(values).then((res) => {
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
          label={t['searchTable.columns.delFlag']}
          field="delFlag"
          rules={[
            { required: true, message: t['searchTable.rules.delFlag.required'] },
          ]}
        >
          <Input placeholder={t['searchForm.delFlag.placeholder']} allowClear />
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.createName']}
          field="createName"
          rules={[
            { required: true, message: t['searchTable.rules.createName.required'] },
          ]}
        >
          <Input placeholder={t['searchForm.createName.placeholder']} allowClear />
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
          label={t['searchTable.columns.createBy']}
          field="createBy"
          rules={[
            { required: true, message: t['searchTable.rules.createBy.required'] },
          ]}
        >
          <Input placeholder={t['searchForm.createBy.placeholder']} allowClear />
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.updateTime']}
          field="updateTime"
          rules={[
            { required: true, message: t['searchTable.rules.updateTime.required'] },
          ]}
        >
          <Input placeholder={t['searchForm.updateTime.placeholder']} allowClear />
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.createTime']}
          field="createTime"
          rules={[
            { required: true, message: t['searchTable.rules.createTime.required'] },
          ]}
        >
          <Input placeholder={t['searchForm.createTime.placeholder']} allowClear />
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.description']}
          field="description"
          rules={[
            { required: true, message: t['searchTable.rules.description.required'] },
          ]}
        >
          <Input placeholder={t['searchForm.description.placeholder']} allowClear />
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.filePath']}
          field="filePath"
          rules={[
            { required: true, message: t['searchTable.rules.filePath.required'] },
          ]}
        >
          <Input placeholder={t['searchForm.filePath.placeholder']} allowClear />
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.fileName']}
          field="fileName"
          rules={[
            { required: true, message: t['searchTable.rules.fileName.required'] },
          ]}
        >
          <Input placeholder={t['searchForm.fileName.placeholder']} allowClear />
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.version']}
          field="version"
          rules={[
            { required: true, message: t['searchTable.rules.version.required'] },
          ]}
        >
          <Input placeholder={t['searchForm.version.placeholder']} allowClear />
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.templateScope']}
          field="templateScope"
          rules={[
            { required: true, message: t['searchTable.rules.templateScope.required'] },
          ]}
        >
          <Input placeholder={t['searchForm.templateScope.placeholder']} allowClear />
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.templateType']}
          field="templateType"
          rules={[
            { required: true, message: t['searchTable.rules.templateType.required'] },
          ]}
        >
          <Input placeholder={t['searchForm.templateType.placeholder']} allowClear />
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.templateCode']}
          field="templateCode"
          rules={[
            { required: true, message: t['searchTable.rules.templateCode.required'] },
          ]}
        >
          <Input placeholder={t['searchForm.templateCode.placeholder']} allowClear />
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.templateName']}
          field="templateName"
          rules={[
            { required: true, message: t['searchTable.rules.templateName.required'] },
          ]}
        >
          <Input placeholder={t['searchForm.templateName.placeholder']} allowClear />
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