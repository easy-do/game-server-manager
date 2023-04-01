import { Form, FormInstance, Input, Modal,  Spin, Notification, Button,  Typography,  Table } from '@arco-design/web-react';
import locale from './locale';
import useLocale from '@/utils/useLocale';
import { updateRequest, infoRequest } from '@/api/applicationVersion';
import { GlobalContext } from '@/context';
import { useContext, useEffect, useRef, useState } from 'react';
import React from 'react';
import { IconDelete, IconEdit, IconPlus } from '@arco-design/web-react/icon';
import MarkdownEditor from '@/components/MarkdownEditor/MarkdownEditor';
import EditSubappPage from './editSuappPage';

function UpdatePage({ id, visible, setVisible, successCallBack }) {
  
  const t = useLocale(locale);

  const formRef = useRef<FormInstance>();

  const { lang } = useContext(GlobalContext);

  const [loading, setLoading] = React.useState(false);
  
  //加载数据
  function fetchData() {
    if (id !== undefined && visible) {
      setLoading(true)
      infoRequest(id).then((res) => {
        const { success, data } = res.data;
        if (success) {
          data.confData = JSON.parse(data.confData)
          setSubapps(data.confData)
          setEditSubappDtata(data.confData[0])
          formRef.current.setFieldsValue(data);
          setLoading(false)
        }
      });
    }
  }

  const [subApps, setSubapps] = useState<any>();

  const addSubApps = (record) =>{
      setSubapps(
        subApps.concat({
          key: record.key+1,
          name: '子应用'+(record.key+1),
          image: 'hell-word',
          version:'0.0.1',
          networkMode:'bridge',
        })
      );
  }

  const removeSubApps = (record) =>{
    setSubapps(subApps.filter((item) => item.key !== record.key));
}

const [editSubappDtata,setEditSubappDtata] = useState();
const [isEditSubappDtata,setIsEditSubappDtata] = useState(false);
const editSubapps = (record) => {
  setEditSubappDtata(record);
  setIsEditSubappDtata(true);
}

const editSubappsCallback = (record) => {
    subApps[record.key-1] = record;
    setSubapps(subApps.concat([]));
}

  const suAppColumns = [
    {
      title: '名称',
      dataIndex: 'name',
      ellipsis: true,
    },
    {
      title: '版本',
      dataIndex: 'version',
      ellipsis: true,
    },
    {
      title: '镜像',
      dataIndex: 'image',
      ellipsis: true,
    },
    {
      title: t['searchTable.columns.operations'],
      dataIndex: 'operations',
      headerCellStyle: { paddingLeft: '15px' },
      render: (_, record) => (
        <div>
          <Button icon={<IconEdit />} onClick={()=>editSubapps(record)} type='dashed'/>
          <Button
            icon={<IconDelete />}
            disabled={record.key == 1}
            onClick={()=>removeSubApps(record)}
            status='danger'
          />
          <Button icon={<IconPlus />} disabled={record.key != subApps.length}  type='dashed' onClick={() => addSubApps(record)} />
        </div>
      ),
    },
  ];

  useEffect(() => {
    fetchData();
  }, [id,visible]);


  //提交修改
  const handleSubmit = () => {
    formRef.current.validate().then((values) => {
      updateRequest({...values,confData:JSON.stringify(subApps)}).then((res) => {
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
    style={{ width: '80%', minHeight: '70%' }}
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
      <Typography.Title heading={6}>
          {t['searchTable.columns.basicInfo']}
        </Typography.Title>

      <Form
        ref={formRef}
        style={{ width: '95%', marginTop: '6px' }}
        labelCol={{ span: lang === 'en-US' ? 7 : 6 }}
        wrapperCol={{ span: lang === 'en-US' ? 17 : 18 }}
        labelAlign="left"
      >
       <Spin tip='loading Data...' loading={loading}>
      <Form.Item
          field="id"
          hidden
        >
          <Input />
        </Form.Item>

        <Form.Item
          label={t['searchTable.columns.version']}
          field="version"
          disabled
          rules={[
            { required: true, message: t['searchTable.rules.version.required'] },
          ]}
        >
          <Input placeholder={t['searchForm.version.placeholder']} allowClear />
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.description']}
          field="description"
        >
          <MarkdownEditor />
        </Form.Item>

        <Typography.Title heading={6}>
        {t['searchTable.columns.subApp']}
      </Typography.Title>
      </Spin>
      </Form>
      <Table
        rowKey="idnex"
        loading={false}
        data={subApps}
        columns={suAppColumns}
        pagination={false} />
       <EditSubappPage subApp={editSubappDtata} visible={isEditSubappDtata} setVisible={setIsEditSubappDtata} editSubappsCallback={editSubappsCallback} /> 
    </Modal>
  );
}

export default UpdatePage;