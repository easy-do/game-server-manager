import { DatePicker, Form, FormInstance, Input, Modal, Select, Spin, Notification } from '@arco-design/web-react';
import locale from './locale';
import useLocale from '@/utils/useLocale';
import { updateRequest, infoRequest } from '@/api/dictData';
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
          label={t['searchTable.columns.dictLabel']}
          field="dictLabel"
          rules={[
            { required: true, message: t['searchTable.rules.dictLabel.required'] },
          ]}
        >
          <Input placeholder={t['searchForm.dictLabel.placeholder']} allowClear />
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.dictKey']}
          field="dictKey"
          rules={[
            { required: true, message: t['searchTable.rules.dictKey.required'] },
          ]}
        >
          <Input placeholder={t['searchForm.dictKey.placeholder']} allowClear />
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.dictValue']}
          field="dictValue"
          rules={[
            { required: true, message: t['searchTable.rules.dictValue.required'] },
          ]}
        >
          <TextArea placeholder={t['searchForm.dictValue.placeholder']} />
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.dictValueType']}
          field="dictValueType"
          rules={[
            { required: true, message: t['searchTable.rules.dictValueType.required'] },
          ]}
        >
           <DictDataSelect dictCode={'dict_value_type'} placeholder={t['searchForm.dictValueType.placeholder']} />
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.isDefault']}
          field="isDefault"
        >
           <DictDataSelect dictCode={'is_no_select'} placeholder={t['searchForm.isDefault.placeholder']} />
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.dictSort']}
          field="dictSort"
        >
          <Input placeholder={t['searchForm.dictSort.placeholder']} allowClear />
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.status']}
          field="status"
          rules={[
            { required: true, message: t['searchTable.rules.status.required'] },
          ]}
        >
           <DictDataSelect dictCode={'status_select'} placeholder={t['searchForm.status.placeholder']} />
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.remark']}
          field="remark"
        >
          <TextArea placeholder={t['searchForm.remark.placeholder']} />
        </Form.Item>
      </Spin>
      </Form>
    </Modal>
  );
}

export default UpdatePage;