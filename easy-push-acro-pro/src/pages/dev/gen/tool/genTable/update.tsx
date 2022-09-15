import { DatePicker, Form, FormInstance, Input, Modal, Select, Spin, Notification, Radio, Tabs, Typography, Space, Table } from '@arco-design/web-react';
import locale from './locale';
import useLocale from '@/utils/useLocale';
import { edit, infoRequest } from '@/api/genTable';
import { GlobalContext } from '@/context';
import { Status } from './constants';
import { useContext, useEffect, useRef, useState } from 'react';
import React from 'react';
import TabPane from '@arco-design/web-react/es/Tabs/tab-pane';

import { SortableContainer, SortableElement } from 'react-sortable-hoc';

function UpdatePage(props: { id: number; visible; setVisible }) {

  const RadioGroup = Radio.Group;

  const t = useLocale(locale);

  const TextArea = Input.TextArea;

  const { lang } = useContext(GlobalContext);

  const [loading, setLoading] = React.useState(false);

  const yesOrNo = [{key:'否',value:'0'},{key:'是',value:'1'}]

  const [info, setInfo] = useState({})
  const [colums, setColumns] = useState()
  const [genInfo, setGenInfo] = useState()


  const [columnTableData,setColumnTableData] = useState([])


  const columsValueChange = (value,index,field) =>{
    console.info(value,index,field)
    const newData = columnTableData;
    columnTableData[index][field]=value;
    setColumnTableData(newData)
    console.info(columnTableData[index])
  }


  const arrayMoveMutate = (array, from, to) => {
    const startIndex = to < 0 ? array.length + to : to;
  
    if (startIndex >= 0 && startIndex < array.length) {
      const item = array.splice(from, 1)[0];
      array.splice(startIndex, 0, item);
    }
  };

  const arrayMove = (array, from, to) => {
    array = [...array];
    arrayMoveMutate(array, from, to);
    return array;
  };

  function onSortEnd({ oldIndex, newIndex }) {
    if (oldIndex !== newIndex) {
      const newData = arrayMove([].concat(columnTableData), oldIndex, newIndex).filter((el) => !!el);
      console.log('New Data: ', newData);
      setColumnTableData(newData);
    }
  }

  const SortableWrapper = SortableContainer((props) => {
    return <tbody {...props} />;
  });
  const SortableItem = SortableElement((props) => {
    return (
      <tr
        style={{
          cursor: 'move',
        }}
        {...props}
      />
    );
  });

  const DraggableContainer = (props) => (
    <SortableWrapper
      onSortEnd={onSortEnd}
      helperContainer={() => document.querySelector('.arco-drag-table-container table tbody')}
      updateBeforeSortStart={({ node }) => {
        const tds = node.querySelectorAll('td');
        tds.forEach((td) => {
          td.style.width = td.clientWidth + 'px';
        });
      }}
      {...props}
    />
  );

  const DraggableRow = (props) => {
    const { record, index, ...rest } = props;
    return <SortableItem index={index} {...rest} />;
  };

  const components = {
    body: {
      tbody: DraggableContainer,
      row: DraggableRow,
    },
  };


  const columnTableColumns = [
    {
      title: t['searchTable.columns.columnName'],
      dataIndex: 'columnName',
    },
    {
      title: t['searchTable.columns.columnType'],
      dataIndex: 'columnType',
    },
    {
      title: t['searchTable.columns.columnComment'],
      dataIndex: 'columnComment',
      render: (_, record, index) => (
        <Form.Item 
          field="tableComment"
          rules={[
            { required: true, message: t['searchTable.rules.tableComment.required'] },
          ]}
        >
          {/* <Input type='text' size='small' value={record.columnComment} onChange={(value)=>columsValueChange(value,index,'columnComment')} /> */}
          <Input type='text' size='small' defaultValue={record.columnComment} />
        </Form.Item>  
        
      ),
    },
    {
      title: t['searchTable.columns.javaField'],
      dataIndex: 'javaField',
      render: (_, record) => (
        <Input size='small' value={record.javaField} />
      ),
    },
    {
      title: t['searchTable.columns.javaType'],
      dataIndex: 'javaType',
      render: (_, record) => (
        <Input size='small' value={record.javaType} />
      ),
    },
    {
      title: t['searchTable.columns.defaultValue'],
      dataIndex: 'defaultValue',
      render: (_, record) => (
        <Input size='small' value={record.defaultValue} />
      ),
    },
    {
      title: t['searchTable.columns.sort'],
      dataIndex: 'sort',
      render: (_, record) => (
        <Input size='small' value={record.sort} />
      ),
    },
    {
      title: t['searchTable.columns.htmlType'],
      dataIndex: 'htmlType',
      render: (_, record) => (
        <Input size='small' value={record.htmlType} />
      ),
    },
    {
      title: t['searchTable.columns.queryType'],
      dataIndex: 'queryType',
      render: (_, record) => (
        <Input size='small' value={record.queryType} />
      ),
    },
    {
      title: t['searchTable.columns.isList'],
      dataIndex: 'isList',
      render: (_, record) => (
        <RadioGroup
        type='button'
        defaultValue={record.isList}
        style={{ marginRight: 20, marginBottom: 20 }}
      >
        <Radio value={'1'}>是</Radio>
        <Radio value={'0'}>否</Radio>
      </RadioGroup>
      ),
    },
    {
      title: t['searchTable.columns.isEdit'],
      dataIndex: 'isEdit',
      render: (_, record) => (
        <RadioGroup
        type='button'
        defaultValue={record.isEdit}
        style={{ marginRight: 20, marginBottom: 20 }}
      >
        <Radio value={'1'}>是</Radio>
        <Radio value={'0'}>否</Radio>
      </RadioGroup>
      ),
    },
    {
      title: t['searchTable.columns.isInsert'],
      dataIndex: 'isInsert',
      render: (_, record) => (
        <RadioGroup
        type='button'
        defaultValue={record.isInsert}
        style={{ marginRight: 20, marginBottom: 20 }}
      >
        <Radio value={'1'}>是</Radio>
        <Radio value={'0'}>否</Radio>
      </RadioGroup>
      ),
    },
    {
      title: t['searchTable.columns.isRequired'],
      dataIndex: 'isRequired',
      render: (_, record) => (
        <RadioGroup
        type='button'
        defaultValue={record.isRequired}
        style={{ marginRight: 20, marginBottom: 20 }}
      >
        <Radio value={'1'}>是</Radio>
        <Radio value={'0'}>否</Radio>
      </RadioGroup>
      ),
    },
    {
      title: t['searchTable.columns.isPk'],
      dataIndex: 'isPk',
      render: (_, record) => (
        <RadioGroup
        type='button'
        defaultValue={record.isPk}
        style={{ marginRight: 20, marginBottom: 20 }}
      >
        <Radio value={'1'}>是</Radio>
        <Radio value={'0'}>否</Radio>
      </RadioGroup>
      ),
    },
    {
      title: t['searchTable.columns.dictType'],
      dataIndex: 'dictType',
      render: (_, record) => (
        <Input size='small' value={record.dictType} />
      ),
    },
  ];
  

  const [infoForm] = Form.useForm()

  const [genInfoForm] = Form.useForm()

  //加载数据
  function fetchData() {
    if (props.id !== undefined) {
      setLoading(true)
      infoRequest(props.id).then((res) => {
        const { success, data } = res.data;
        if (success) {
          infoForm.setFieldsValue(data.info);
          genInfoForm.setFieldsValue(data.info);
          setColumnTableData(data.info.columns)
          setColumns(data.info.c)
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
    // formRef.current.validate().then((values) => {
    //   console.info(values)
    //   // edit(values).then((res) => {
    //   //   const { success, msg} = res.data
    //   //   if(success){
    //   //     Notification.success({ content: msg, duration: 300 })
    //   //     props.setVisible(false);
    //   //   }
    //   // });

    // }).catch((e)=>{
    //   Notification.warning({content:'补全必填项',duration:3000})
    // });
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
      maskClosable={false}
      style={{ width: '100%', height: 'auto' }}
    >

     <Form.Provider
        onFormValuesChange={(name, changedValues, info) => {
          console.log('onFormValuesChange: ', name, changedValues, info);
          if(name === 'infoForm'){
            setInfo(changedValues)
          }
          if(name === 'columnsForm'){
            setColumns(changedValues)
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
      
      <Form
        id='columnsForm'
        size='large'
        style={{ width: '95%', marginTop: '6px' }}
        labelCol={{ span: lang === 'en-US' ? 7 : 6 }}
        wrapperCol={{ span: lang === 'en-US' ? 17 : 18 }}
        labelAlign="left"
      >
        <Form.List field='users'>
          {(fields, { add, remove, move }) => {
            return (
              <Table
              className='arco-drag-table-container'
              rowKey="id"
              loading={loading}
              pagination={false}
              columns={columnTableColumns}
              components={components}
              data={columnTableData}
            />
            );
          }}
        </Form.List>
          

        

      </Form>
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