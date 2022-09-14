import { DatePicker, Form, FormInstance, Input, Modal, Select, Spin, Notification } from '@arco-design/web-react';
import locale from './locale';
import useLocale from '@/utils/useLocale';
import { edit, infoRequest } from '@/api/genTable';
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
          label={t['searchTable.columns.isWrapper']}
          field="isWrapper"
          rules={[
            { required: true, message: t['searchTable.rules.isWrapper.required'] },
          ]}
        >
          <Input placeholder={t['searchForm.isWrapper.placeholder']} allowClear />
        </Form.Item>
        
        <Form.Item
          label={t['searchTable.columns.templateIds']}
          field="templateIds"
          rules={[
            { required: true, message: t['searchTable.rules.templateIds.required'] },
          ]}
        >
          <Input placeholder={t['searchForm.templateIds.placeholder']} allowClear />
        </Form.Item>
        
        <Form.Item
          label={t['searchTable.columns.isManager']}
          field="isManager"
          rules={[
            { required: true, message: t['searchTable.rules.isManager.required'] },
          ]}
        >
          <Input placeholder={t['searchForm.isManager.placeholder']} allowClear />
        </Form.Item>
        
        <Form.Item
          label={t['searchTable.columns.isRemove']}
          field="isRemove"
          rules={[
            { required: true, message: t['searchTable.rules.isRemove.required'] },
          ]}
        >
          <Input placeholder={t['searchForm.isRemove.placeholder']} allowClear />
        </Form.Item>
        
        <Form.Item
          label={t['searchTable.columns.isUpdate']}
          field="isUpdate"
          rules={[
            { required: true, message: t['searchTable.rules.isUpdate.required'] },
          ]}
        >
          <Input placeholder={t['searchForm.isUpdate.placeholder']} allowClear />
        </Form.Item>
        
        <Form.Item
          label={t['searchTable.columns.isInsert']}
          field="isInsert"
          rules={[
            { required: true, message: t['searchTable.rules.isInsert.required'] },
          ]}
        >
          <Input placeholder={t['searchForm.isInsert.placeholder']} allowClear />
        </Form.Item>
        
        <Form.Item
          label={t['searchTable.columns.isQuery']}
          field="isQuery"
          rules={[
            { required: true, message: t['searchTable.rules.isQuery.required'] },
          ]}
        >
          <Input placeholder={t['searchForm.isQuery.placeholder']} allowClear />
        </Form.Item>
        
        <Form.Item 
          label={t['searchTable.columns.remark']}
          field="remark"
          rules={[
            { required: true, message: t['searchTable.rules.remark.required'] },
          ]}
        >
          <TextArea placeholder={t['searchForm.remark.placeholder']} />
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
          label={t['searchTable.columns.options']}
          field="options"
          rules={[
            { required: true, message: t['searchTable.rules.options.required'] },
          ]}
        >
          <TextArea placeholder={t['searchForm.options.placeholder']} />
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.genPath']}
          field="genPath"
          rules={[
            { required: true, message: t['searchTable.rules.genPath.required'] },
          ]}
        >
          <Input placeholder={t['searchForm.genPath.placeholder']} allowClear />
        </Form.Item>
        
        <Form.Item 
          label={t['searchTable.columns.genType']}
          field="genType"
          rules={[
            { required: true, message: t['searchTable.rules.genType.required'] },
          ]}
        >
          <Select
            placeholder={t['searchForm.genType.placeholder']}
            options={Status.map((item, index) => ({
              label: item,
              value: index,
            }))}
            // mode="multiple"
            allowClear
          />
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.functionAuthor']}
          field="functionAuthor"
          rules={[
            { required: true, message: t['searchTable.rules.functionAuthor.required'] },
          ]}
        >
          <Input placeholder={t['searchForm.functionAuthor.placeholder']} allowClear />
        </Form.Item>
        
        <Form.Item
          label={t['searchTable.columns.functionName']}
          field="functionName"
          rules={[
            { required: true, message: t['searchTable.rules.functionName.required'] },
          ]}
        >
          <Input placeholder={t['searchForm.functionName.placeholder']} allowClear />
        </Form.Item>
        
        <Form.Item
          label={t['searchTable.columns.businessName']}
          field="businessName"
          rules={[
            { required: true, message: t['searchTable.rules.businessName.required'] },
          ]}
        >
          <Input placeholder={t['searchForm.businessName.placeholder']} allowClear />
        </Form.Item>
        
        <Form.Item
          label={t['searchTable.columns.moduleName']}
          field="moduleName"
          rules={[
            { required: true, message: t['searchTable.rules.moduleName.required'] },
          ]}
        >
          <Input placeholder={t['searchForm.moduleName.placeholder']} allowClear />
        </Form.Item>
        
        <Form.Item
          label={t['searchTable.columns.packageName']}
          field="packageName"
          rules={[
            { required: true, message: t['searchTable.rules.packageName.required'] },
          ]}
        >
          <Input placeholder={t['searchForm.packageName.placeholder']} allowClear />
        </Form.Item>
        
        <Form.Item
          label={t['searchTable.columns.tplCategory']}
          field="tplCategory"
          rules={[
            { required: true, message: t['searchTable.rules.tplCategory.required'] },
          ]}
        >
          <Input placeholder={t['searchForm.tplCategory.placeholder']} allowClear />
        </Form.Item>
        
        <Form.Item
          label={t['searchTable.columns.className']}
          field="className"
          rules={[
            { required: true, message: t['searchTable.rules.className.required'] },
          ]}
        >
          <Input placeholder={t['searchForm.className.placeholder']} allowClear />
        </Form.Item>
        
        <Form.Item
          label={t['searchTable.columns.subTableFkName']}
          field="subTableFkName"
          rules={[
            { required: true, message: t['searchTable.rules.subTableFkName.required'] },
          ]}
        >
          <Input placeholder={t['searchForm.subTableFkName.placeholder']} allowClear />
        </Form.Item>
        
        <Form.Item
          label={t['searchTable.columns.subTableName']}
          field="subTableName"
          rules={[
            { required: true, message: t['searchTable.rules.subTableName.required'] },
          ]}
        >
          <Input placeholder={t['searchForm.subTableName.placeholder']} allowClear />
        </Form.Item>
        
        <Form.Item 
          label={t['searchTable.columns.tableComment']}
          field="tableComment"
          rules={[
            { required: true, message: t['searchTable.rules.tableComment.required'] },
          ]}
        >
          <TextArea placeholder={t['searchForm.tableComment.placeholder']} />
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.tableName']}
          field="tableName"
          rules={[
            { required: true, message: t['searchTable.rules.tableName.required'] },
          ]}
        >
          <Input placeholder={t['searchForm.tableName.placeholder']} allowClear />
        </Form.Item>
        
        <Form.Item
          label={t['searchTable.columns.dataSourceId']}
          field="dataSourceId"
          rules={[
            { required: true, message: t['searchTable.rules.dataSourceId.required'] },
          ]}
        >
          <Input placeholder={t['searchForm.dataSourceId.placeholder']} allowClear />
        </Form.Item>
        
        <Form.Item
          label={t['searchTable.columns.tableId']}
          field="tableId"
          rules={[
            { required: true, message: t['searchTable.rules.tableId.required'] },
          ]}
        >
          <Input placeholder={t['searchForm.tableId.placeholder']} allowClear />
        </Form.Item>
        
      </Spin>
      </Form>
    </Modal>
  );
}

export default UpdatePage;