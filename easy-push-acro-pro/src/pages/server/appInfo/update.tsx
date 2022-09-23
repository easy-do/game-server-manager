import { DatePicker, Form, FormInstance, Input, Modal, Select, Spin, Notification } from '@arco-design/web-react';
import locale from './locale';
import useLocale from '@/utils/useLocale';
import { updateRequest, infoRequest } from '@/api/appInfo';
import { GlobalContext } from '@/context';
import { Status } from './constants';
import { useContext, useEffect, useRef } from 'react';
import React from 'react';
import DictDataSelect from '@/components/DictCompenent/dictDataSelect';

function UpdatePage({ id, visible, setVisible, successCallBack }) {

  const TextArea = Input.TextArea;

  const formRef = useRef<FormInstance>();

  const { lang } = useContext(GlobalContext);

  const [loading, setLoading] = React.useState(false);
  
  //加载数据
  function fetchData() {
    if (id !== undefined) {
      setLoading(true)
      infoRequest(id).then((res) => {
        const { success, data } = res.data;
        if (success) {
          formRef.current.setFieldsValue(data);
        }
        setLoading(false)
      });
    }
  }

  useEffect(() => {
    fetchData();
  }, [id]);

  const t = useLocale(locale);

  //提交修改
  const handleSubmit = () => {
    formRef.current.validate().then((values) => {
      updateRequest(values).then((res) => {
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
      title={t['searchTable.update.title']}
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
        style={{ width: '95%', marginTop: '6px' }}
        labelCol={{ span: lang === 'en-US' ? 7 : 6 }}
        wrapperCol={{ span: lang === 'en-US' ? 17 : 18 }}
        ref={formRef}
        labelAlign="left"
      >
      <Spin tip='loading Data...' loading={loading}>
        <Form.Item
          label={t['searchTable.columns.id']}
          field="id"
          hidden
        >
          <Input placeholder={t['searchForm.id.placeholder']} allowClear />
        </Form.Item>
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
        <Form.Item
          label={t['searchTable.columns.heat']}
          field="heat"
        >
          <Input placeholder={t['searchForm.heat.placeholder']} allowClear />
        </Form.Item>
      </Spin>
      </Form>
    </Modal>
  );
}

export default UpdatePage;