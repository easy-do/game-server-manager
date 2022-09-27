import {
  Form,
  Input,
  Modal,
  Select,
  Spin,
  Notification,
  Tabs,
} from '@arco-design/web-react';
import locale from './locale';
import useLocale from '@/utils/useLocale';
import { updateRequest, infoRequest } from '@/api/genTable';
import { GlobalContext } from '@/context';
import { useContext, useEffect, useState } from 'react';
import React from 'react';
import TabPane from '@arco-design/web-react/es/Tabs/tab-pane';

import EditColumnsPage from './editColumns';
import { yesOrNo } from './constants';
import { listRequest } from '@/api/template';

function UpdatePage({ id, visible, setVisible, successCallBack }) {

  const t = useLocale(locale);

  const TextArea = Input.TextArea;

  const { lang } = useContext(GlobalContext);

  const [loading, setLoading] = React.useState(false);

  const [info, setInfo] = useState({});
  const [genInfo, setGenInfo] = useState({});

  const [columnTableData, setColumnTableData] = useState([]);

  const [infoForm] = Form.useForm();

  const [genInfoForm] = Form.useForm();

  const [templateSelect, setTemplateSelect] = useState([]);

  //获取模板列表
  function getTemplateList() {
    listRequest({ columns: ['id', 'templateName'] }).then((res) => {
      const { success, data } = res.data;
      if (success) {
        const list = [];
        data.map((item) => {
          list.push({
            label: item.templateName,
            value: item.id,
          });
        });
        setTemplateSelect(list);
      }
    });
  }

  //加载数据
  function fetchData() {
    if (id !== undefined && visible) {
      setLoading(true);
      getTemplateList();
      infoRequest(id).then((res) => {
        const { success, data } = res.data;
        if (success) {
          if (data.templateIds) {
            const ids = data.templateIds.split(',');
            ids.map((item, index) => {
              ids[index] = Number(item);
            });
            data.templateIds = ids;
          }
          setInfo(data);
          setGenInfo(data);
          infoForm.setFieldsValue(data);
          genInfoForm.setFieldsValue(data);
          setColumnTableData(data.columns);
        }
        setLoading(false);
      });
    }
  }

  useEffect(() => {
    fetchData();
  }, [id,visible]);

  //提交修改
  const handleSubmit = () => {
    const newGenInfo: any = genInfo;
    console.info(genInfo)
    if(!newGenInfo.templateIds){
      Notification.warning({ content: '未选择模板', duration: 300 });
      return;
    }
    if(typeof newGenInfo.templateIds !== 'string'){
      newGenInfo.templateIds = newGenInfo.templateIds.join(',');
    }
    updateRequest({ ...info, ...newGenInfo, columns: columnTableData }).then((res) => {
      const { success, msg } = res.data;
      if (success) {
        Notification.success({ content: msg, duration: 300 });
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
      style={{ width: '100%', minHeight: '100%' }}
    >
      <Form.Provider
        onFormValuesChange={(name, changedValues) => {
          if (name === 'infoForm') {
            setInfo({ ...info, ...changedValues });
          }
          if (name === 'genInfoForm') {
            setGenInfo({ ...genInfo, ...changedValues });
          }
        }}
      >
        <Tabs type="text">
          <TabPane key="1" title="基本信息">
            <Form
              id="infoForm"
              size="large"
              style={{ width: '95%', marginTop: '6px' }}
              labelCol={{ span: lang === 'en-US' ? 7 : 6 }}
              wrapperCol={{ span: lang === 'en-US' ? 17 : 18 }}
              form={infoForm}
              labelAlign="left"
              // layout='inline'
            >
              <Spin tip="loading Data..." loading={loading}>
                <Form.Item
                  label={t['searchTable.columns.tableName']}
                  field="tableName"
                  rules={[
                    {
                      required: true,
                      message: t['searchTable.rules.tableName.required'],
                    },
                  ]}
                >
                  <Input placeholder={t['searchForm.tableName.placeholder']} />
                </Form.Item>
                <Form.Item
                  label={t['searchTable.columns.tableComment']}
                  field="tableComment"
                  rules={[
                    {
                      required: true,
                      message: t['searchTable.rules.tableComment.required'],
                    },
                  ]}
                >
                  <Input
                    placeholder={t['searchForm.tableComment.placeholder']}
                  />
                </Form.Item>
                <Form.Item
                  label={t['searchTable.columns.functionAuthor']}
                  field="functionAuthor"
                  rules={[
                    {
                      required: true,
                      message: t['searchTable.rules.functionAuthor.required'],
                    },
                  ]}
                >
                  <Input
                    placeholder={t['searchForm.functionAuthor.placeholder']}
                  />
                </Form.Item>
                <Form.Item
                  label={t['searchTable.columns.className']}
                  field="className"
                  rules={[
                    {
                      required: true,
                      message: t['searchTable.rules.className.required'],
                    },
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
          <TabPane key="3" title="字段信息">
            <Spin tip="loading Data..." loading={loading}>
              <EditColumnsPage
                loading={loading}
                columnTableData={columnTableData}
                setColumnTableData={setColumnTableData}
              />
            </Spin>
          </TabPane>
          <TabPane key="4" title="生成信息">
            <Form
              id="genInfoForm"
              size="large"
              style={{ width: '95%', marginTop: '6px' }}
              labelCol={{ span: lang === 'en-US' ? 7 : 6 }}
              wrapperCol={{ span: lang === 'en-US' ? 17 : 18 }}
              form={genInfoForm}
              labelAlign="left"
            >
              <Spin tip="loading Data..." loading={loading}>
                <Form.Item
                  label={t['searchTable.columns.functionName']}
                  field="functionName"
                  rules={[
                    {
                      required: true,
                      message: t['searchTable.rules.functionName.required'],
                    },
                  ]}
                >
                  <Input
                    placeholder={t['searchForm.functionName.placeholder']}
                  />
                </Form.Item>
                <Form.Item
                  label={t['searchTable.columns.businessName']}
                  field="businessName"
                  rules={[
                    {
                      required: true,
                      message: t['searchTable.rules.businessName.required'],
                    },
                  ]}
                >
                  <Input
                    placeholder={t['searchForm.businessName.placeholder']}
                  />
                </Form.Item>
                <Form.Item
                  label={t['searchTable.columns.moduleName']}
                  field="moduleName"
                  rules={[
                    {
                      required: true,
                      message: t['searchTable.rules.moduleName.required'],
                    },
                  ]}
                >
                  <Input placeholder={t['searchForm.moduleName.placeholder']} />
                </Form.Item>
                <Form.Item
                  label={t['searchTable.columns.packageName']}
                  field="packageName"
                  rules={[
                    {
                      required: true,
                      message: t['searchTable.rules.packageName.required'],
                    },
                  ]}
                >
                  <Input
                    placeholder={t['searchForm.packageName.placeholder']}
                  />
                </Form.Item>
                <Form.Item
                  label={t['searchTable.columns.isQuery']}
                  field="isQuery"
                  rules={[
                    {
                      required: true,
                      message: t['searchTable.rules.isQuery.required'],
                    },
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
                    {
                      required: true,
                      message: t['searchTable.rules.isInsert.required'],
                    },
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
                    {
                      required: true,
                      message: t['searchTable.rules.isUpdate.required'],
                    },
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
                    {
                      required: true,
                      message: t['searchTable.rules.isRemove.required'],
                    },
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

                <Form.Item
                  label={t['searchTable.columns.templateIds']}
                  field="templateIds"
                  rules={[
                    {
                      required: true,
                      message: t['searchTable.rules.templateIds.required'],
                    },
                  ]}
                >
                  <Select
                    showSearch
                    placeholder={t['searchForm.templateIds.placeholder']}
                    options={templateSelect}
                    mode="multiple"
                    allowClear
                    filterOption={(inputValue, option) =>
                      option.props.children
                        .toLowerCase()
                        .indexOf(inputValue.toLowerCase()) >= 0
                    }
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
