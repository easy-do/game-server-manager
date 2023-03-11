import React, { useContext, useRef } from 'react';
import {
  Form,
  FormInstance,
  Input,
  Modal,
  Notification,
} from '@arco-design/web-react';
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
        const { success, msg } = res.data;
        if (success) {
          Notification.success({ content: msg, duration: 300 });
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
            {
              required: true,
              message: t['searchTable.rules.resourceName.required'],
            },
          ]}
        >
          <Input
            placeholder={t['searchForm.resourceName.placeholder']}
            allowClear
          />
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.resourceCode']}
          field="resourceCode"
          rules={[
            {
              required: true,
              message: t['searchTable.rules.resourceCode.required'],
            },
          ]}
        >
          <Input
            placeholder={t['searchForm.resourceCode.placeholder']}
            allowClear
          />
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.parentId']}
          field="parentId"
          rules={[
            {
              required: true,
              message: t['searchTable.rules.parentId.required'],
            },
          ]}
        >
          <ResourceTreeSelect />
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.resourceType']}
          field="resourceType"
          rules={[
            {
              required: true,
              message: t['searchTable.rules.resourceType.required'],
            },
          ]}
        >
          <DictDataSelect
            dictCode={'system_resource_type'}
            placeholder={t['searchForm.resourceType.placeholder']}
          />
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.url']}
          field="url"
          // rules={[
          //   {
          //     required: true,
          //     message: t['searchTable.rules.url.required'],
          //   },
          // ]}
        >
          <TextArea placeholder={t['searchForm.url.placeholder']} allowClear />
        </Form.Item>
        <Form.Item label={t['searchTable.columns.icon']} field="icon">
          <Input placeholder={t['searchForm.icon.placeholder']} allowClear />
        </Form.Item>
        <Form.Item label={t['searchTable.columns.iconType']} field="iconType">
        <DictDataSelect
            dictCode={'icon_type'}
            placeholder={t['searchForm.iconType.placeholder']}
          />
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.orderNumber']}
          field="orderNumber"
        >
          <Input
            placeholder={t['searchForm.orderNumber.placeholder']}
            allowClear
          />
        </Form.Item>
        <Form.Item label={t['searchTable.columns.authFlag']} field="authFlag" initialValue={1}>
          <DictDataSelect
            dictCode={'is_no_select'}
            placeholder={t['searchForm.authFlag.placeholder']}
          />
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.permissions']}
          field="permissions"
        >
          <TextArea placeholder={t['searchForm.permissions.placeholder']} />
        </Form.Item>
        <Form.Item label={t['searchTable.columns.routeMode']} field="routeMode">
          <DictDataSelect
            dictCode={'route_mode'}
            placeholder={t['searchForm.routeMode.placeholder']}
          />
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.embeddedType']}
          field="embeddedType"
        >
          <DictDataSelect
            placeholder={t['searchForm.embeddedType.placeholder']}
            dictCode={'embedded_type'}
          />
        </Form.Item>
        <Form.Item label={t['searchTable.columns.subRoutes']} field="subRoutes">
          <TextArea placeholder={t['searchForm.subRoutes.placeholder']} />
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.status']}
          field="status"
          initialValue={0}
        >
          <DictDataSelect
            dictCode={'status_select'}
            placeholder={t['searchForm.status.placeholder']}
          />
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.resourceDesc']}
          field="resourceDesc"
        >
          <TextArea placeholder={t['searchForm.resourceDesc.placeholder']} />
        </Form.Item>
      </Form>
    </Modal>
  );
}

export default AddPage;
