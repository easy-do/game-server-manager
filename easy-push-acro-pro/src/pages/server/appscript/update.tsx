import {
  DatePicker,
  Form,
  FormInstance,
  Input,
  Modal,
  Select,
  Spin,
  Notification,
} from '@arco-design/web-react';
import locale from './locale';
import useLocale from '@/utils/useLocale';
import { updateRequest, infoRequest } from '@/api/appScript';
import { GlobalContext } from '@/context';
import { useContext, useEffect, useRef } from 'react';
import React from 'react';
import DictDataSelect from '@/components/DictCompenent/dictDataSelect';
import RequestSelect from '@/components/RequestSelect/RequestSelect';
import { list as appList } from '@/api/appInfo';
import { list as scriptList } from '@/api/appScript';

function UpdatePage({ id, visible, setVisible, successCallBack }) {
  const TextArea = Input.TextArea;

  const formRef = useRef<FormInstance>();

  const { lang } = useContext(GlobalContext);

  const [loading, setLoading] = React.useState(false);

  //加载数据
  function fetchData() {
    if (id !== undefined) {
      setLoading(true);
      infoRequest(id).then((res) => {
        const { success, data } = res.data;
        if (success) {
          const newBasicScript = data.basicScript
            ? data.basicScript.split(',')
            : [];
          formRef.current.setFieldsValue({
            ...data,
            basicScript: newBasicScript,
          });
        }
        setLoading(false);
      });
    }
  }

  useEffect(() => {
    fetchData();
  }, [id]);

  const t = useLocale(locale);

  //提交修改
  const handleSubmit = () => {
    formRef.current.validate().then((values: any) => {
      const newBasicScript = values.basicScript
        ? values.basicScript.join(',')
        : '';
      updateRequest({ ...values, basicScript: newBasicScript }).then((res) => {
        const { success, msg } = res.data;
        if (success) {
          Notification.success({ content: msg, duration: 300 });
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
        <Spin tip="loading Data..." loading={loading}>
          <Form.Item label={t['searchTable.columns.id']} field="id" hidden>
            <Input placeholder={t['searchForm.id.placeholder']} allowClear />
          </Form.Item>
          <Form.Item
            label={t['searchTable.columns.scriptName']}
            field="scriptName"
            rules={[
              {
                required: true,
                message: t['searchTable.rules.scriptName.required'],
              },
            ]}
          >
            <Input
              placeholder={t['searchForm.scriptName.placeholder']}
              allowClear
            />
          </Form.Item>
          <Form.Item
            label={t['searchTable.columns.adaptationAppId']}
            field="adaptationAppId"
          >
            <RequestSelect
              placeholder={t['searchForm.adaptationAppId.placeholder']}
              lableFiled="appName"
              valueFiled="id"
              valueType="number"
              request={() => appList()}
            />
          </Form.Item>
          <Form.Item
            label={t['searchTable.columns.scriptType']}
            field="scriptType"
            rules={[
              {
                required: true,
                message: t['searchTable.rules.scriptType.required'],
              },
            ]}
          >
            <DictDataSelect
              dictCode={'script_type'}
              placeholder={t['searchForm.scriptScope.placeholder']}
            />
          </Form.Item>
          <Form.Item
            label={t['searchTable.columns.scriptScope']}
            field="scriptScope"
            rules={[
              {
                required: true,
                message: t['searchTable.rules.scriptScope.required'],
              },
            ]}
          >
            <DictDataSelect
              dictCode={'scope_select'}
              placeholder={t['searchForm.scriptScope.placeholder']}
            />
          </Form.Item>
          <Form.Item
            label={t['searchTable.columns.basicScript']}
            field="basicScript"
          >
            <RequestSelect
              mode="multiple"
              placeholder={t['searchForm.basicScript.placeholder']}
              lableFiled="scriptName"
              valueFiled="id"
              request={() => scriptList()}
            />
          </Form.Item>
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
            <Input
              placeholder={t['searchForm.version.placeholder']}
              allowClear
            />
          </Form.Item>
          <Form.Item
            label={t['searchTable.columns.description']}
            field="description"
          >
            <TextArea placeholder={t['searchForm.description.placeholder']} />
          </Form.Item>
        </Spin>
      </Form>
    </Modal>
  );
}

export default UpdatePage;
