import React, { useContext, useRef } from 'react';
import dayjs from 'dayjs';
import { Form, FormInstance, Input, Modal, DatePicker, Select, Notification } from '@arco-design/web-react';
import locale from './locale';
import useLocale from '@/utils/useLocale';
import { addRequest } from '@/api/appInfo';
import { GlobalContext } from '@/context';
import { Status } from './constants';
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
          label={t['searchTable.columns.appName']}
          field="appName"
          rules={[
            { required: true, message: t['searchTable.rules.appName.required'] },
          ]}
        >
          <Input placeholder={t['searchForm.appName.placeholder']} allowClear />
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
          label={t['searchTable.columns.state']}
          field="state"
          rules={[
            { required: true, message: t['searchTable.rules.state.required'] },
          ]}
        >
           <DictDataSelect dictCode={'status_select'} placeholder={t['searchForm.state.placeholder']} />
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.startCmd']}
          field="startCmd"
        >
          <TextArea placeholder={t['searchForm.startCmd.placeholder']} />
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.stopCmd']}
          field="stopCmd"
        >
          <TextArea placeholder={t['searchForm.stopCmd.placeholder']} />
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.configFilePath']}
          field="configFilePath"
        >
          <TextArea placeholder={t['searchForm.configFilePath.placeholder']} />
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.icon']}
          field="icon"
          rules={[
            { required: true, message: t['searchTable.rules.icon.required'] },
          ]}
        >
          <TextArea placeholder={t['searchForm.icon.placeholder']} />
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.picture']}
          field="picture"
          rules={[
            { required: true, message: t['searchTable.rules.picture.required'] },
          ]}
        >
          <TextArea placeholder={t['searchForm.picture.placeholder']} />
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.appScope']}
          field="appScope"
          rules={[
            { required: true, message: t['searchTable.rules.appScope.required'] },
          ]}
        >
           <DictDataSelect dictCode={'scope_select'} placeholder={t['searchForm.appScope.placeholder']} />
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