import React, { useContext, useRef } from 'react';
import { Form, FormInstance, Input, Modal } from '@arco-design/web-react';
import locale from './locale';
import useLocale from '@/utils/useLocale';
import { addRequest } from '@/api/roleManager';
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
          label={t['searchTable.columns.delFlag']}
          field="delFlag"
          rules={[
            { required: true, message: t['searchTable.columns.delFlag'] },
          ]}
        >
          <Input placeholder={t['searchForm.delFlag.placeholder']} allowClear />
        </Form.Item>

        <Form.Item label={t['searchTable.columns.remark']} field="remark">
          <Input placeholder={t['searchForm.remark.placeholder']} allowClear />
        </Form.Item>

        <Form.Item
          label={t['searchTable.columns.updateTime']}
          field="updateTime"
        >
          <Input
            placeholder={t['searchForm.updateTime.placeholder']}
            allowClear
          />
        </Form.Item>

        <Form.Item label={t['searchTable.columns.updateBy']} field="updateBy">
          <Input
            placeholder={t['searchForm.updateBy.placeholder']}
            allowClear
          />
        </Form.Item>

        <Form.Item
          label={t['searchTable.columns.createTime']}
          field="createTime"
        >
          <Input
            placeholder={t['searchForm.createTime.placeholder']}
            allowClear
          />
        </Form.Item>

        <Form.Item label={t['searchTable.columns.createBy']} field="createBy">
          <Input
            placeholder={t['searchForm.createBy.placeholder']}
            allowClear
          />
        </Form.Item>

        <Form.Item label={t['searchTable.columns.status']} field="status">
          <Input placeholder={t['searchForm.status.placeholder']} allowClear />
        </Form.Item>

        <Form.Item label={t['searchTable.columns.isDefault']} field="isDefault">
          <Input
            placeholder={t['searchForm.isDefault.placeholder']}
            allowClear
          />
        </Form.Item>

        <Form.Item label={t['searchTable.columns.roleSort']} field="roleSort">
          <Input
            placeholder={t['searchForm.roleSort.placeholder']}
            allowClear
          />
        </Form.Item>

        <Form.Item label={t['searchTable.columns.roleKey']} field="roleKey">
          <Input placeholder={t['searchForm.roleKey.placeholder']} allowClear />
        </Form.Item>

        <Form.Item label={t['searchTable.columns.roleName']} field="roleName">
          <Input
            placeholder={t['searchForm.roleName.placeholder']}
            allowClear
          />
        </Form.Item>

        <Form.Item label={t['searchTable.columns.roleId']} field="roleId">
          <Input placeholder={t['searchForm.roleId.placeholder']} allowClear />
        </Form.Item>
      </Form>
    </Modal>
  );
}

export default AddPage;
