import React, { useContext, useRef, useState } from 'react';
import {
  Form,
  FormInstance,
  Input,
  Modal,
  Notification,
  Button,
  Typography,
  Table,
  Space,
  Divider,
  Checkbox,
} from '@arco-design/web-react';
import locale from './locale';
import useLocale from '@/utils/useLocale';
import { addRequest } from '@/api/applicationVersion';
import { GlobalContext } from '@/context';
import { IconDelete, IconEdit, IconPlus } from '@arco-design/web-react/icon';
import MarkdownEditor from '@/components/MarkdownEditor/MarkdownEditor';
import EditSubappPage from './editSuAppPage';

function AddPage({ applicationId, visible, setVisible, successCallBack }) {
  const formRef = useRef<FormInstance>();

  const { lang } = useContext(GlobalContext);

  const t = useLocale(locale);


  const [subApps, setSubapps] = useState([
    {
      key: 1,
      name: '子应用1',
      image: 'hell-word',
      version:'0.0.1',
      networkMode:'bridge',
    },
  ]);

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

const [editSubappDtata,setEditSubappDtata] = useState(subApps[0]);
const [isEditSubappDtata,setIsEditSubappDtata] = useState(false);
const editSubapps = (record) => {
  setEditSubappDtata(record);
  setIsEditSubappDtata(true);
}

const editSubappsCallback = (record) => {
    subApps[record.key-1] = record;
    setSubapps(subApps.concat([]));
}

const [createNetworks, setCreateNetworks] = useState(false);

const createNetworksOnchage = (value) => {
  setCreateNetworks(value);
};

  const suAppColumns = [
    {
      title: t['searchTable.columns.name'],
      dataIndex: 'name',
      ellipsis: true,
    },
    {
      title: t['searchTable.columns.version'],
      dataIndex: 'version',
      ellipsis: true,
    },
    {
      title: t['searchTable.columns.image'],
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

  const handleSubmit = () => {
    formRef.current.validate().then((values) => {
      const confData = values.confData;
      confData.subApps = subApps;
      addRequest({
        ...values,
        applicationId: applicationId,
        confData: JSON.stringify(confData),
      }).then((res) => {
        const { success, msg } = res.data;
        if (success) {
          Notification.success({ content: msg, duration: 300 });
          successCallBack();
        }
      });
    });
  };

  return (
    <><Modal
      style={{ width: '100%', minHeight: '100%' }}
      visible={visible}
      onOk={() => {
        handleSubmit();
      } }
      onCancel={() => {
        setVisible(false);
      } }
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
        <Form.Item
          label={t['searchTable.columns.version']}
          field="version"
          rules={[
            {
              required: true,
              message: t['searchTable.rules.version.required'],
            },
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
        <Form.Item
            label={t['searchTable.columns.createNetworks']}
            field="confData.createNetworks"
            initialValue={false}
            triggerPropName='checked'
          >
            <Checkbox onChange={createNetworksOnchage} />
          </Form.Item>
          {!createNetworks ? null : (
            <Form.List field="confData.networks">
              {(fields, { add, remove, move }) => {
                return (
                  <div>
                    {fields.map((item, index) => {
                      return (
                        <div key={item.key}>
                          <Form.Item
                            label={
                              t['searchTable.columns.networks'] + (index + 1)
                            }
                          >
                            <Space
                              style={{
                                width: '100%',
                                justifyContent: 'space-between',
                              }}
                              split={<Divider type="vertical" />}
                              align="baseline"
                              size="large"
                            >
                              <Form.Item
                                label={t['searchTable.columns.networkName']}
                                field={item.field + '.name'}
                                rules={[
                                  {
                                    required: true,
                                    message:
                                      t['searchTable.rules.name.required'],
                                  },
                                ]}
                              >
                                <Input />
                              </Form.Item>

                              <Form.Item
                                label={t['searchTable.columns.subNet']}
                                field={item.field + '.ipam.Config[0].Subnet'}
                                rules={[
                                  {
                                    required: true,
                                    message:
                                      t['searchTable.rules.subNet.required'],
                                  },
                                ]}
                              >
                                <Input />
                              </Form.Item>
                              <Form.Item
                                label={t['searchTable.columns.gateway']}
                                field={item.field + '.ipam.config[0].gateway'}
                              >
                                <Input />
                              </Form.Item>
                              <Form.Item>
                                <Button
                                  icon={<IconDelete />}
                                  shape="circle"
                                  status="danger"
                                  onClick={() => remove(index)}
                                ></Button>
                              </Form.Item>
                            </Space>
                          </Form.Item>
                        </div>
                      );
                    })}
                    <Form.Item>
                      <Button
                        type="dashed"
                        onClick={() => {
                          add();
                        }}
                      >
                        {t['searchTable.columns.addNetwork']}
                      </Button>
                    </Form.Item>
                  </div>
                );
              }}
            </Form.List>
          )}
      </Form>
      <Typography.Title heading={6}>
        {t['searchTable.columns.subApp']}
      </Typography.Title>
      <Table
        rowKey="idnex"
        loading={false}
        data={subApps}
        columns={suAppColumns}
        pagination={false} />
    </Modal>
    <EditSubappPage subApp={editSubappDtata} visible={isEditSubappDtata} setVisible={setIsEditSubappDtata} editSubappsCallback={editSubappsCallback} /></>
  );
}

export default AddPage;
