import React, { useContext, useRef } from 'react';
import dayjs from 'dayjs';
import {
  Form,
  FormInstance,
  Input,
  Modal,
  DatePicker,
  Select,
  Notification,
  Button,
  Space,
  Divider,
  Typography,
  Grid,
} from '@arco-design/web-react';
import locale from './locale';
import useLocale from '@/utils/useLocale';
import { addRequest } from '@/api/appScript';
import { GlobalContext } from '@/context';
import { statusEnum } from './constants';
import DictDataSelect from '@/components/DictCompenent/dictDataSelect';
import RequestSelect from '@/components/RequestSelect/RequestSelect';
import { list as appList } from '@/api/appInfo';
import { list as scriptList } from '@/api/appScript';
import { IconDelete } from '@arco-design/web-react/icon';

function AddPage({ visible, setVisible, successCallBack }) {
  const TextArea = Input.TextArea;

  const formRef = useRef<FormInstance>();

  const { lang } = useContext(GlobalContext);

  const t = useLocale(locale);

  const handleSubmit = () => {
    formRef.current.validate().then((values) => {
      addRequest(values).then((res) => {
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
        layout='vertical'
      >
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
              label={t['searchTable.columns.adaptationAppId']}
              field="adaptationAppId"
            >
              <RequestSelect
                placeholder={t['searchForm.adaptationAppId.placeholder']}
                lableFiled="appName"
                valueFiled="id"
                valueType="string"
                request={() => appList()}
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

export default AddPage;
