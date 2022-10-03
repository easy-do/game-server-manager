import React, { useContext, useRef } from 'react';
import dayjs from 'dayjs';
import { Form, FormInstance, Input, Modal, DatePicker, Select, Notification } from '@arco-design/web-react';
import locale from './locale';
import useLocale from '@/utils/useLocale';
import { addRequest } from '@/api/notice';
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
          label={t['searchTable.columns.noticeTitle']}
          field="noticeTitle"
          rules={[
            { required: true, message: t['searchTable.rules.noticeTitle.required'] },
          ]}
        >
          <Input placeholder={t['searchForm.noticeTitle.placeholder']} allowClear />
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.noticeType']}
          field="noticeType"
          rules={[
            { required: true, message: t['searchTable.rules.noticeType.required'] },
          ]}
        >
          <DictDataSelect dictCode={'notice_type'} placeholder={t['searchForm.noticeType.placeholder']} />
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.noticeContent']}
          field="noticeContent"
          rules={[
            { required: true, message: t['searchTable.rules.noticeContent.required'] },
          ]}
        >
          <MarkdownEditor />
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
      </Form>
    </Modal>
  );
}

export default AddPage;