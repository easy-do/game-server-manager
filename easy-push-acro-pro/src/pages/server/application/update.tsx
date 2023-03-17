import { Form, FormInstance, Input, Modal, Select, Spin, Notification, Upload } from '@arco-design/web-react';
import locale from './locale';
import useLocale from '@/utils/useLocale';
import { updateRequest, infoRequest } from '@/api/application';
import { GlobalContext } from '@/context';
import { useContext, useEffect, useRef } from 'react';
import React from 'react';
import DictDataSelect from '@/components/DictCompenent/dictDataSelect';
import { base6CcovertFile, customRequest, onPreview, onRemove } from '@/components/Upload/fileToBase64';

function UpdatePage({ id, visible, setVisible, successCallBack }) {

  const TextArea = Input.TextArea;

  const formRef = useRef<FormInstance>();

  const { lang } = useContext(GlobalContext);

  const [loading, setLoading] = React.useState(false);
  
  //加载数据
  function fetchData() {
    if (id !== undefined && visible) {
      setLoading(true)
      infoRequest(id).then((res) => {
        const { success, data } = res.data;
        if (success) {
          const icons = base6CcovertFile(data.icon)
          formRef.current.setFieldsValue({...data,icon:icons});
        }
        setLoading(false)
      });
    }
  }

  useEffect(() => {
    fetchData();
  }, [id,visible]);

  const t = useLocale(locale);

  //提交修改
  const handleSubmit = () => {
    formRef.current.validate().then((values) => {
      const icon = values.icon;
      const iocnBase64 = icon[0].response;
      updateRequest({
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
          label={t['searchTable.columns.applicationName']}
          field="applicationName"
          rules={[
            { required: true, message: t['searchTable.rules.applicationName.required'] },
          ]}
        >
          <Input placeholder={t['searchForm.applicationName.placeholder']} allowClear />
        </Form.Item>
        <Form.Item
          triggerPropName='fileList'
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
      </Spin>
      </Form>
    </Modal>
  );
}

export default UpdatePage;