import {
  DatePicker,
  Form,
  FormInstance,
  Input,
  Modal,
  Select,
  Spin,
  Notification,
  Button,
  Space,
  Grid,
  Typography,
  Divider,
} from '@arco-design/web-react';
import locale from './locale';
import useLocale from '@/utils/useLocale';
import { updateRequest, infoRequest } from '@/api/scriptData';
import { GlobalContext } from '@/context';
import { useContext, useEffect, useRef } from 'react';
import React from 'react';
import DictDataSelect from '@/components/DictCompenent/dictDataSelect';
import RequestSelect from '@/components/RequestSelect/RequestSelect';
import { list as appList } from '@/api/appInfo';
import { list as scriptList } from '@/api/scriptData';
import { IconDelete } from '@arco-design/web-react/icon';

function UpdatePage({ id, visible, setVisible, successCallBack }) {
  const TextArea = Input.TextArea;

  const formRef = useRef<FormInstance>();

  const { lang } = useContext(GlobalContext);

  const [loading, setLoading] = React.useState(false);


  //加载数据
  function fetchData() {
    if (id !== undefined && visible) {
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
  }, [id,visible]);

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
      <Form
        style={{ width: '95%', marginTop: '6px' }}
        labelCol={{ span: lang === 'en-US' ? 7 : 6 }}
        wrapperCol={{ span: lang === 'en-US' ? 17 : 18 }}
        ref={formRef}
        labelAlign="left"
        layout='vertical'
      >
        <Form.Item
          field="id"
          hidden
        >
          <Input />
        </Form.Item>
        <Typography.Title heading={6}>
          {t['searchTable.columns.basicInfo']}
        </Typography.Title>
        <Grid.Row gutter={80}>
          <Grid.Col span={8}>
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
          </Grid.Col>
          <Grid.Col span={8}>
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
          </Grid.Col>
          <Grid.Col span={8}>
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
          </Grid.Col>
          <Grid.Col span={8}>
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
          </Grid.Col>
          <Grid.Col span={8}>
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
          </Grid.Col>

        </Grid.Row>

        <Form.Item
          label={t['searchTable.columns.description']}
          field="description"
        >
          <TextArea placeholder={t['searchForm.description.placeholder']} />
        </Form.Item>
        <Divider />

        <Typography.Title heading={6}>
          {t['searchTable.columns.EnvInfo']}
        </Typography.Title>

        <Form.List field='scriptEnv'>
          {(fields, { add, remove, move }) => {
            return (
              <div>
                {fields.map((item, index) => {
                  return (
                    <div key={item.key}>
                      <Form.Item label={'Env' + index}>
                        <Space style={{ width: '100%', justifyContent: 'space-between' }} split={<Divider type='vertical' />} align='center' size='large'>
                          <Form.Item
                            label={t['searchTable.columns.envName']}
                            field={item.field + '.envName'}
                            rules={[{ required: true, message: t['searchTable.rules.envName.required'] }]}
                          >
                            <Input />
                          </Form.Item>

                          <Form.Item
                            label={t['searchTable.columns.envKey']}
                            field={item.field + '.envKey'}
                            rules={[{ required: true, message: t['searchTable.rules.envKey.required'] }]}
                          >
                            <Input />
                          </Form.Item>
                          <Form.Item
                            label={t['searchTable.columns.envValue']}
                            field={item.field + '.envValue'}
                            rules={[{ required: true, message: t['searchTable.rules.envValue.required'] }]}
                          >
                            <Input />
                          </Form.Item>
                          <Form.Item
                            label={t['searchTable.columns.envDescription']}
                            initialValue={'填写参数时会提示'}
                            field={item.field + '.description'}
                            rules={[{ required: true, message: t['searchTable.rules.envDescription.required'] }]}
                          >
                            <Input />
                          </Form.Item>
                          <Form.Item
                            initialValue={'text'}
                            label={t['searchTable.columns.envType']}
                            field={item.field + '.envType'}
                            rules={[{ required: true, message: t['searchTable.rules.envType.required'] }]}
                          >
                            <DictDataSelect dictCode='env_type' />
                          </Form.Item>
                          <Button
                            icon={<IconDelete />}
                            shape='circle'
                            status='danger'
                            onClick={() => remove(index)}
                          >
                          </Button>
                        </Space>
                      </Form.Item>
                    </div>
                  );
                })}
                <Form.Item >
                  <Button
                    type='dashed'
                    style={{ width: '100%' }}
                    onClick={() => {
                      add();
                    }}
                  >
                    {t['searchTable.columns.addEnv']}
                  </Button>
                </Form.Item>
              </div>
            );
          }}
        </Form.List>
      </Form>
    </Modal>
  );
}

export default UpdatePage;
