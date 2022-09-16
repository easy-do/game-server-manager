import { DatePicker, Form, FormInstance, Input, Modal, Select, Spin, Notification, Radio, Tabs, Typography, Space, Table } from '@arco-design/web-react';
import locale from './locale';
import useLocale from '@/utils/useLocale';
import { edit, infoRequest } from '@/api/genTable';
import { GlobalContext } from '@/context';
import { Status } from './constants';
import { useCallback, useContext, useEffect, useRef, useState } from 'react';
import React from 'react';
import TabPane from '@arco-design/web-react/es/Tabs/tab-pane';

import { SortableContainer, SortableElement } from 'react-sortable-hoc';
import FormItem from '@arco-design/web-react/es/Form/form-item';
import EditColumnsPage from './editColumns';

function UpdatePage({ id, visible, setVisible, successCallBack }) {

  const RadioGroup = Radio.Group;

  const t = useLocale(locale);

  const TextArea = Input.TextArea;

  const { lang } = useContext(GlobalContext);

  const [loading, setLoading] = React.useState(false);

  const yesOrNo = [{key:'否',value:'0'},{key:'是',value:'1'}]

  const [info, setInfo] = useState({})
  const [genInfo, setGenInfo] = useState({})

  const [columnTableData,setColumnTableData] = useState([])

  const [infoForm] = Form.useForm()

  const [genInfoForm] = Form.useForm()

  //加载数据
  function fetchData() {
    if (id !== undefined) {
      setLoading(true)
      infoRequest(id).then((res) => {
        const { success, data } = res.data;
        if (success) {
          infoForm.setFieldsValue(data);
          genInfoForm.setFieldsValue(data);
          setColumnTableData(data.columns)
        }
        setLoading(false)
      });
    }
  }

  useEffect(() => {
    fetchData();
  }, [id]);

 

  //提交修改
  const handleSubmit = () => {
      edit({...info, ...genInfo, columns:columnTableData}).then((res) => {
        const { success, msg} = res.data
        if(success){
          Notification.success({ content: msg, duration: 300 })
          successCallBack();
        }
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
      style={{ width: '100%', height: 'auto' }}
    >

     <Form.Provider
        onFormValuesChange={(name, changedValues) => {
          if(name === 'infoForm'){
            setInfo(changedValues)
          }
          if(name === 'genInfoForm'){
            setGenInfo(changedValues)
          }
        }}
        onFormSubmit={(name, values, info) => {
          console.log('onFormSubmit: ', name, values, info);
        }}
      >

      <Tabs type='text'>
        <TabPane key='1' title='基本信息'>
      <Form
        id='infoForm'
        size='large'
        style={{ width: '95%', marginTop: '6px' }}
        labelCol={{ span: lang === 'en-US' ? 7 : 6 }}
        wrapperCol={{ span: lang === 'en-US' ? 17 : 18 }}
        form={infoForm}
        labelAlign="left"
        // layout='inline'
      >
      <Spin tip='loading Data...' loading={loading}>
        <Form.Item 
                label={t['searchTable.columns.tableName']}
                field="tableName"
                rules={[
                  { required: true, message: t['searchTable.rules.tableName.required'] },
                ]}
              >
                <Input placeholder={t['searchForm.tableName.placeholder']} />
        </Form.Item>  
        <Form.Item 
                label={t['searchTable.columns.tableComment']}
                field="tableComment"
                rules={[
                  { required: true, message: t['searchTable.rules.tableComment.required'] },
                ]}
              >
                <Input placeholder={t['searchForm.tableComment.placeholder']} />
        </Form.Item>  
        <Form.Item 
                label={t['searchTable.columns.functionAuthor']}
                field="functionAuthor"
                rules={[
                  { required: true, message: t['searchTable.rules.functionAuthor.required'] },
                ]}
              >
                <Input placeholder={t['searchForm.functionAuthor.placeholder']} />
        </Form.Item>  
        <Form.Item 
                label={t['searchTable.columns.className']}
                field="className"
                rules={[
                  { required: true, message: t['searchTable.rules.className.required'] },
                ]}
              >
                <Input placeholder={t['searchForm.className.placeholder']} />
        </Form.Item>  
        <Form.Item 
                label={t['searchTable.columns.remark']}
                field="remark"
              >
                <TextArea placeholder={t['searchForm.remark.placeholder']} />
        </Form.Item>  
      </Spin>
      </Form>
        </TabPane>
        <TabPane key='3' title='字段信息'>
      <Spin tip='loading Data...' loading={loading}>
        <EditColumnsPage  loading={loading} columnTableData={columnTableData} setColumnTableData={setColumnTableData}/>
      </Spin>
        </TabPane>
        <TabPane key='4' title='生成信息'>
        <Form
        id='genInfoForm'
                size='large'
        style={{ width: '95%', marginTop: '6px' }}
        labelCol={{ span: lang === 'en-US' ? 7 : 6 }}
        wrapperCol={{ span: lang === 'en-US' ? 17 : 18 }}
        form={genInfoForm}
        labelAlign="left"
      >
      <Spin tip='loading Data...' loading={loading}>
        <Form.Item 
                    label={t['searchTable.columns.functionName']}
                    field="functionName"
                    rules={[
                      { required: true, message: t['searchTable.rules.functionName.required'] },
                    ]}
                  >
                    <Input placeholder={t['searchForm.functionName.placeholder']} />
          </Form.Item>  
          <Form.Item 
                    label={t['searchTable.columns.businessName']}
                    field="businessName"
                    rules={[
                      { required: true, message: t['searchTable.rules.businessName.required'] },
                    ]}
                  >
                    <Input placeholder={t['searchForm.businessName.placeholder']} />
          </Form.Item>  
          <Form.Item 
                    label={t['searchTable.columns.moduleName']}
                    field="moduleName"
                    rules={[
                      { required: true, message: t['searchTable.rules.moduleName.required'] },
                    ]}
                  >
                    <Input placeholder={t['searchForm.moduleName.placeholder']} />
          </Form.Item>  
          <Form.Item 
                    label={t['searchTable.columns.packageName']}
                    field="packageName"
                    rules={[
                      { required: true, message: t['searchTable.rules.packageName.required'] },
                    ]}
                  >
                    <Input placeholder={t['searchForm.packageName.placeholder']} />
          </Form.Item>  
          <Form.Item 
              label={t['searchTable.columns.isQuery']}
              field="isQuery"
              rules={[
                { required: true, message: t['searchTable.rules.isQuery.required'] },
              ]}
            >
              <Select
                placeholder={t['searchForm.isQuery.placeholder']}
                options={yesOrNo.map((item) => ({
                  label: item.key,
                  value: item.value,
                }))}
                // mode="multiple"
                allowClear
              />
            </Form.Item>
            <Form.Item 
              label={t['searchTable.columns.isInsert']}
              field="isInsert"
              rules={[
                { required: true, message: t['searchTable.rules.isInsert.required'] },
              ]}
            >
              <Select
                placeholder={t['searchForm.isInsert.placeholder']}
                options={yesOrNo.map((item) => ({
                  label: item.key,
                  value: item.value,
                }))}
                // mode="multiple"
                allowClear
              />
            </Form.Item>
            <Form.Item 
              label={t['searchTable.columns.isUpdate']}
              field="isUpdate"
              rules={[
                { required: true, message: t['searchTable.rules.isUpdate.required'] },
              ]}
            >
              <Select
                placeholder={t['searchForm.isUpdate.placeholder']}
                options={yesOrNo.map((item) => ({
                  label: item.key,
                  value: item.value,
                }))}
                // mode="multiple"
                allowClear
              />
            </Form.Item>
            <Form.Item 
              label={t['searchTable.columns.isRemove']}
              field="isRemove"
              rules={[
                { required: true, message: t['searchTable.rules.isRemove.required'] },
              ]}
            >
              <Select
                placeholder={t['searchForm.isRemove.placeholder']}
                options={yesOrNo.map((item) => ({
                  label: item.key,
                  value: item.value,
                }))}
                // mode="multiple"
                allowClear
              />
            </Form.Item>
      </Spin>
      </Form>
        </TabPane>
      </Tabs>
      </Form.Provider>
    
    </Modal>
  );
}

export default UpdatePage;