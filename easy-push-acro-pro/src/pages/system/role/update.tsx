import React, { useContext, useEffect, useRef } from 'react';
import { Form, FormInstance, Input, Modal } from '@arco-design/web-react';
import locale from './locale';
import useLocale from '@/utils/useLocale';
import { edit, infoRequest } from '@/api/roleManager';
import { GlobalContext } from '@/context';

function UpdatePage(props: { id: number; visible; setVisible }) {
  const formRef = useRef<FormInstance>();

  const { lang } = useContext(GlobalContext);

  function fetchData() {
    if (props.id !== undefined) {
      infoRequest(props.id).then((res) => {
        const { success, data } = res.data;
        if (success) {
          formRef.current.setFieldsValue(data);
        }
      });
    }
  }

  useEffect(() => {
    fetchData();
  }, [props.id]);

  const t = useLocale(locale);

  const handleSubmit = () => {
    formRef.current.validate().then((values) => {
      edit(values).then((res) => {
        if (res.data.success) {
          props.setVisible(false);
        }
      });
    });
  };

  return (
    <Modal
      title={t['searchTable.update.title']}
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
        style={{ width: '95%', marginTop: '6px' }}
        labelCol={{ span: lang === 'en-US' ? 7 : 6 }}
        wrapperCol={{ span: lang === 'en-US' ? 17 : 18 }}
        ref={formRef}
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

export default UpdatePage;
