import React, { useContext, useEffect, useRef } from 'react';
import { Form, FormInstance, Input, Modal, Spin } from '@arco-design/web-react';
import locale from './locale';
import useLocale from '@/utils/useLocale';
import { edit, infoRequest } from '@/api/template';
import { GlobalContext } from '@/context';

function UpdatePage(props: { id: number; visible; setVisible }) {
  const formRef = useRef<FormInstance>();

  const { lang } = useContext(GlobalContext);

  const t = useLocale(locale);

  const [loading, setLoading] = React.useState(false);
  
  //加载数据
  function fetchData() {
    if (props.id !== undefined) {
      setLoading(true)
      infoRequest(props.id).then((res) => {
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
  }, [props.id]);


  //提交修改
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
      <Spin tip='loading Data...' loading={loading}>
      <Form.Item
          label={t['searchTable.columns.id']}
          field="id"
          hidden
        >
          <Input placeholder={t['searchForm.id.placeholder']} allowClear />
        </Form.Item>
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
          rules={[
            { required: true, message: t['searchTable.rulesmsg.description'] },
          ]}
        >
          <Input placeholder={t['searchForm.description.placeholder']} allowClear />
        </Form.Item>
      </Spin>
      
      </Form>
    </Modal>
  );
}

export default UpdatePage;