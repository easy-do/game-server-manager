import { DatePicker, Form, FormInstance, Input, Modal, Select, Spin, Notification } from '@arco-design/web-react';
import locale from './locale';
import useLocale from '@/utils/useLocale';
import { edit, infoRequest } from '@/api/dictType';
import { GlobalContext } from '@/context';
import { Status } from './constants';
import { useContext, useEffect, useRef } from 'react';
import React from 'react';

function UpdatePage(props: { id: number; visible; setVisible }) {

  const TextArea = Input.TextArea;

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
        const { success, msg} = res.data
        if(success){
          Notification.success({ content: msg, duration: 300 })
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
          label={t['searchTable.columns.remark']}
          field="remark"
          rules={[
            { required: true, message: t['searchTable.rules.remark.required'] },
          ]}
        >
          <Input placeholder={t['searchForm.remark.placeholder']} allowClear />
        </Form.Item>
        
        <Form.Item
          label={t['searchTable.columns.updateTime']}
          field="updateTime"
          rules={[
            { required: true, message: t['searchTable.rules.updateTime.required'] },
          ]}
        >
          <DatePicker
            style={{ width: '100%' }}
            showTime={{
              defaultValue: '04:05:06',
            }}
            format='YYYY-MM-DD HH:mm:ss'
          />
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
          label={t['searchTable.columns.createTime']}
          field="createTime"
          rules={[
            { required: true, message: t['searchTable.rules.createTime.required'] },
          ]}
        >
          <DatePicker
            style={{ width: '100%' }}
            showTime={{
              defaultValue: '04:05:06',
            }}
            format='YYYY-MM-DD HH:mm:ss'
          />
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
          label={t['searchTable.columns.status']}
          field="status"
          rules={[
            { required: true, message: t['searchTable.rules.status.required'] },
          ]}
        >
          <Select
            placeholder={t['searchForm.status.placeholder']}
            options={Status.map((item, index) => ({
              label: item,
              value: index,
            }))}
            // mode="multiple"
            allowClear
          />
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.dictCode']}
          field="dictCode"
          rules={[
            { required: true, message: t['searchTable.rules.dictCode.required'] },
          ]}
        >
          <Input placeholder={t['searchForm.dictCode.placeholder']} allowClear />
        </Form.Item>
        
        <Form.Item
          label={t['searchTable.columns.dictName']}
          field="dictName"
          rules={[
            { required: true, message: t['searchTable.rules.dictName.required'] },
          ]}
        >
          <Input placeholder={t['searchForm.dictName.placeholder']} allowClear />
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
        
      </Spin>
      </Form>
    </Modal>
  );
}

export default UpdatePage;