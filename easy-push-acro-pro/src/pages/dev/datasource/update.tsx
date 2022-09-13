import React, { useContext, useEffect, useRef } from 'react';
import { Form, FormInstance, Input, Modal, Spin } from '@arco-design/web-react';
import locale from './locale';
import useLocale from '@/utils/useLocale';
import { edit, infoRequest } from '@/api/dataSourceManager';
import { GlobalContext } from '@/context';

function UpdatePage(props: { id: number; visible; setVisible }) {
  const formRef = useRef<FormInstance>();

  const { lang } = useContext(GlobalContext);

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

  const t = useLocale(locale);

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
          label={t['searchTable.columns.delFlag']}
          field="delFlag"
          rules={[
            { required: true, message: t['searchTable.rulesmsg.delFlag'] },
          ]}
        >
          <Input placeholder={t['searchForm.delFlag.placeholder']} allowClear />
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.remark']}
          field="remark"
          rules={[
            { required: true, message: t['searchTable.rulesmsg.remark'] },
          ]}
        >
          <Input placeholder={t['searchForm.remark.placeholder']} allowClear />
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.updateTime']}
          field="updateTime"
          rules={[
            { required: true, message: t['searchTable.rulesmsg.updateTime'] },
          ]}
        >
          <Input placeholder={t['searchForm.updateTime.placeholder']} allowClear />
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.updateBy']}
          field="updateBy"
          rules={[
            { required: true, message: t['searchTable.rulesmsg.updateBy'] },
          ]}
        >
          <Input placeholder={t['searchForm.updateBy.placeholder']} allowClear />
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.createTime']}
          field="createTime"
          rules={[
            { required: true, message: t['searchTable.rulesmsg.createTime'] },
          ]}
        >
          <Input placeholder={t['searchForm.createTime.placeholder']} allowClear />
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.createBy']}
          field="createBy"
          rules={[
            { required: true, message: t['searchTable.rulesmsg.createBy'] },
          ]}
        >
          <Input placeholder={t['searchForm.createBy.placeholder']} allowClear />
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.status']}
          field="status"
          rules={[
            { required: true, message: t['searchTable.rulesmsg.status'] },
          ]}
        >
          <Input placeholder={t['searchForm.status.placeholder']} allowClear />
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.params']}
          field="params"
          rules={[
            { required: true, message: t['searchTable.rulesmsg.params'] },
          ]}
        >
          <Input placeholder={t['searchForm.params.placeholder']} allowClear />
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.password']}
          field="password"
          rules={[
            { required: true, message: t['searchTable.rulesmsg.password'] },
          ]}
        >
          <Input placeholder={t['searchForm.password.placeholder']} allowClear />
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.userName']}
          field="userName"
          rules={[
            { required: true, message: t['searchTable.rulesmsg.userName'] },
          ]}
        >
          <Input placeholder={t['searchForm.userName.placeholder']} allowClear />
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.url']}
          field="url"
          rules={[
            { required: true, message: t['searchTable.rulesmsg.url'] },
          ]}
        >
          <Input placeholder={t['searchForm.url.placeholder']} allowClear />
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.sourceType']}
          field="sourceType"
          rules={[
            { required: true, message: t['searchTable.rulesmsg.sourceType'] },
          ]}
        >
          <Input placeholder={t['searchForm.sourceType.placeholder']} allowClear />
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.sourceCode']}
          field="sourceCode"
          rules={[
            { required: true, message: t['searchTable.rulesmsg.sourceCode'] },
          ]}
        >
          <Input placeholder={t['searchForm.sourceCode.placeholder']} allowClear />
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.sourceName']}
          field="sourceName"
          rules={[
            { required: true, message: t['searchTable.rulesmsg.sourceName'] },
          ]}
        >
          <Input placeholder={t['searchForm.sourceName.placeholder']} allowClear />
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.id']}
          field="id"
          rules={[
            { required: true, message: t['searchTable.rulesmsg.id'] },
          ]}
        >
          <Input placeholder={t['searchForm.id.placeholder']} allowClear />
        </Form.Item>
      </Spin>
      </Form>
    </Modal>
  );
}

export default UpdatePage;