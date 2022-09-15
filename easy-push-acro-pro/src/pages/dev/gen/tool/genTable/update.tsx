import { DatePicker, Form, FormInstance, Input, Modal, Select, Spin, Notification, Radio, Tabs, Typography } from '@arco-design/web-react';
import locale from './locale';
import useLocale from '@/utils/useLocale';
import { edit, infoRequest } from '@/api/genTable';
import { GlobalContext } from '@/context';
import { Status } from './constants';
import { useContext, useEffect, useRef, useState } from 'react';
import React from 'react';
import TabPane from '@arco-design/web-react/es/Tabs/tab-pane';

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


  const a:'line' | 'card' | 'card-gutter' | 'text' | 'rounded' | 'capsule' = 'line';

  const [type, setType] = useState(a);
  
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
      style={{ width: '100%', height: '100%' }}
    >
    <div>
      <Tabs type='capsule'>
        <TabPane key='1' title='基本信息'>
      <Form
        style={{ width: '95%', marginTop: '6px' }}
        labelCol={{ span: lang === 'en-US' ? 7 : 6 }}
        wrapperCol={{ span: lang === 'en-US' ? 17 : 18 }}
        ref={formRef}
        labelAlign="left"
      >
      <Spin tip='loading Data...' loading={loading}>
      <Form.Item 
              label={t['searchTable.columns.tableComment']}
              field="tableComment"
            >
              <Input placeholder={t['searchForm.tableComment.placeholder']} />
            </Form.Item>  
      </Spin>
      </Form>

        </TabPane>
        <TabPane key='3' title='字段信息'>
        <Form
        style={{ width: '95%', marginTop: '6px' }}
        labelCol={{ span: lang === 'en-US' ? 7 : 6 }}
        wrapperCol={{ span: lang === 'en-US' ? 17 : 18 }}
        ref={formRef}
        labelAlign="left"
      >
      <Spin tip='loading Data...' loading={loading}>
      <Form.Item 
              label={t['searchTable.columns.tableComment']}
              field="tableComment"
            >
              <Input placeholder={t['searchForm.tableComment.placeholder']} />
            </Form.Item>  
      </Spin>
      </Form>
        </TabPane>
        <TabPane key='4' title='生成信息'>
        <Form
        style={{ width: '95%', marginTop: '6px' }}
        labelCol={{ span: lang === 'en-US' ? 7 : 6 }}
        wrapperCol={{ span: lang === 'en-US' ? 17 : 18 }}
        ref={formRef}
        labelAlign="left"
      >
      <Spin tip='loading Data...' loading={loading}>
      <Form.Item 
              label={t['searchTable.columns.tableComment']}
              field="tableComment"
            >
              <Input placeholder={t['searchForm.tableComment.placeholder']} />
            </Form.Item>  
      </Spin>
      </Form>
        </TabPane>
      </Tabs>
    </div>
    </Modal>
  );
}

export default UpdatePage;