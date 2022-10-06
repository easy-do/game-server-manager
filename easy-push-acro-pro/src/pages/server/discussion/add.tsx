import React, { useContext, useRef } from 'react';
import dayjs from 'dayjs';
import { Form, FormInstance, Input, Modal, DatePicker, Select, Notification } from '@arco-design/web-react';
import locale from './locale';
import useLocale from '@/utils/useLocale';
import { addRequest } from '@/api/discussion';
import { GlobalContext } from '@/context';
import { Status } from './constants';
import DictDataSelect from '@/components/DictCompenent/dictDataSelect';
import MarkdownEditor from '@/components/MarkdownEditor/MarkdownEditor';

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
      style={{minHeight:'100%',width:'100%'}}
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
          label={t['searchTable.columns.title']}
          field="title"
          rules={[
            { required: true, message: t['searchTable.rules.title.required'] },
          ]}
        >
          <Input placeholder={t['searchForm.title.placeholder']} allowClear />
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.content']}
          field="content"
          rules={[
            { required: true, message: t['searchTable.rules.content.required'] },
          ]}
        >
          <MarkdownEditor />
        </Form.Item>
      </Form>
    </Modal>
  );
}

export default AddPage;