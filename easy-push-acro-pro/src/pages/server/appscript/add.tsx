import React, { useContext, useRef } from 'react';
import dayjs from 'dayjs';
import {
  Form,
  FormInstance,
  Input,
  Modal,
  DatePicker,
  Select,
  Notification,
} from '@arco-design/web-react';
import locale from './locale';
import useLocale from '@/utils/useLocale';
import { addRequest } from '@/api/appScript';
import { GlobalContext } from '@/context';
import { Status } from './constants';
import DictDataSelect from '@/components/DictCompenent/dictDataSelect';
import RequestSelect from '@/components/RequestSelect/RequestSelect';
import { list as appList } from '@/api/appInfo';
import { list as scriptList } from '@/api/appScript';

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
          label={t['searchTable.columns.scriptName']}
          field="scriptName"
          rules={[
            {
              required: true,
              message: t['searchTable.rules.scriptName.required'],
            },
          ]}
        >
          <Input
            placeholder={t['searchForm.scriptName.placeholder']}
            allowClear
          />
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.adaptationAppId']}
          field="adaptationAppId"
        >
          <RequestSelect
            placeholder={t['searchForm.adaptationAppId.placeholder']}
            lableFiled="appName"
            valueFiled="id"
            valueType="number"
            request={() => appList()}
          />
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.scriptType']}
          field="scriptType"
          rules={[
            {
              required: true,
              message: t['searchTable.rules.scriptType.required'],
            },
          ]}
        >
          <DictDataSelect
            dictCode={'script_type'}
            placeholder={t['searchForm.scriptScope.placeholder']}
          />
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.scriptScope']}
          field="scriptScope"
          rules={[
            {
              required: true,
              message: t['searchTable.rules.scriptScope.required'],
            },
          ]}
        >
          <DictDataSelect
            dictCode={'scope_select'}
            placeholder={t['searchForm.scriptScope.placeholder']}
          />
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.basicScript']}
          field="basicScript"
        >
          <RequestSelect
            mode="multiple"
            placeholder={t['searchForm.basicScript.placeholder']}
            lableFiled="scriptName"
            valueFiled="id"
            request={() => scriptList()}
          />
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.version']}
          field="version"
          rules={[
            {
              required: true,
              message: t['searchTable.rules.version.required'],
            },
          ]}
        >
          <Input placeholder={t['searchForm.version.placeholder']} allowClear />
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.description']}
          field="description"
        >
          <TextArea placeholder={t['searchForm.description.placeholder']} />
        </Form.Item>
      </Form>
    </Modal>
  );
}

export default AddPage;
