import DictTypeSelect from '@/components/DictCompenent/dictTypeCodeSelect';
import { GlobalContext } from '@/context';
import useLocale from '@/utils/useLocale';
import {
  Form,
  Input,
  Modal,
  Radio,
  Select,
  Spin,
} from '@arco-design/web-react';
import React, { useContext, useEffect } from 'react';
import { htmlTypeSelect, javaTypeSelect, queryTypeSelect } from './constants';
import locale from './locale';

function EditColumn({ data, visible, setVisible, successCallBack }) {
  const [form] = Form.useForm();

  const { lang } = useContext(GlobalContext);

  const t = useLocale(locale);

  //加载数据
  function fetchData() {
    if (data !== null) {
      form.setFieldsValue(data);
    }
  }

  useEffect(() => {
    fetchData();
  }, [data]);

  //提交修改
  const handleSubmit = () => {
    form.validate().then((values) => {
      successCallBack({ ...data, ...values });
      setVisible(false);
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
    >
      <Form
        style={{ width: '95%', marginTop: '6px' }}
        labelCol={{ span: lang === 'en-US' ? 7 : 6 }}
        wrapperCol={{ span: lang === 'en-US' ? 17 : 18 }}
        form={form}
        labelAlign="left"
      >
          <Form.Item
            disabled
            label={t['searchTable.columns.columnName']}
            field="columnName"
          >
            <Input
              placeholder={t['searchForm.columnName.placeholder']}
              allowClear
            />
          </Form.Item>
          <Form.Item
            disabled
            label={t['searchTable.columns.columnType']}
            field="columnType"
          >
            <Input
              placeholder={t['searchForm.columnType.placeholder']}
              allowClear
            />
          </Form.Item>
          <Form.Item
            disabled
            label={t['searchTable.columns.defaultValue']}
            field="defaultValue"
          >
            <Input
              placeholder={t['searchForm.defaultValue.placeholder']}
              allowClear
            />
          </Form.Item>
          <Form.Item
            label={t['searchTable.columns.columnComment']}
            field="columnComment"
            rules={[
              {
                required: true,
                message: t['searchTable.rules.columnComment.required'],
              },
            ]}
          >
            <Input placeholder={t['searchForm.columnComment.placeholder']} />
          </Form.Item>
          <Form.Item
            label={t['searchTable.columns.javaField']}
            field="javaField"
            rules={[
              {
                required: true,
                message: t['searchTable.rules.javaField.required'],
              },
            ]}
          >
            <Input
              placeholder={t['searchForm.javaField.placeholder']}
              allowClear
            />
          </Form.Item>

          <Form.Item
            label={t['searchTable.columns.javaType']}
            field="javaType"
            rules={[
              {
                required: true,
                message: t['searchTable.rules.javaType.required'],
              },
            ]}
          >
            <Select
              placeholder={t['searchForm.javaType.placeholder']}
              options={javaTypeSelect}
              allowClear
            />
          </Form.Item>
          <Form.Item
            label={t['searchTable.columns.htmlType']}
            field="htmlType"
            rules={[
              {
                required: true,
                message: t['searchTable.rules.htmlType.required'],
              },
            ]}
          >
            <Select
              placeholder={t['searchForm.htmlType.placeholder']}
              options={htmlTypeSelect}
              allowClear
            />
          </Form.Item>
          <Form.Item
            label={t['searchTable.columns.queryType']}
            field="queryType"
            rules={[
              {
                required: true,
                message: t['searchTable.rules.queryType.required'],
              },
            ]}
          >
            <Select
              placeholder={t['searchForm.queryType.placeholder']}
              options={queryTypeSelect}
              // mode="multiple"
              allowClear
            />
          </Form.Item>
          <Form.Item
            label={t['searchTable.columns.isPk']}
            field="isPk"
            rules={[
              { required: true, message: t['searchTable.rules.isPk.required'] },
            ]}
          >
            <Radio.Group type="button">
              <Radio value={'1'}>是</Radio>
              <Radio value={'0'}>否</Radio>
            </Radio.Group>
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
            <Radio.Group type="button">
              <Radio value={'1'}>是</Radio>
              <Radio value={'0'}>否</Radio>
            </Radio.Group>
          </Form.Item>

          <Form.Item
            label={t['searchTable.columns.isList']}
            field="isList"
            rules={[
              {
                required: true,
                message: t['searchTable.rules.isList.required'],
              },
            ]}
          >
            <Radio.Group type="button">
              <Radio value={'1'}>是</Radio>
              <Radio value={'0'}>否</Radio>
            </Radio.Group>
          </Form.Item>

          <Form.Item
            label={t['searchTable.columns.isInfo']}
            field="isInfo"
            rules={[
              {
                required: true,
                message: t['searchTable.rules.isInfo.required'],
              },
            ]}
          >
            <Radio.Group type="button">
              <Radio value={'1'}>是</Radio>
              <Radio value={'0'}>否</Radio>
            </Radio.Group>
          </Form.Item>

          <Form.Item
            label={t['searchTable.columns.isEdit']}
            field="isEdit"
            rules={[
              {
                required: true,
                message: t['searchTable.rules.isEdit.required'],
              },
            ]}
          >
            <Radio.Group type="button">
              <Radio value={'1'}>是</Radio>
              <Radio value={'0'}>否</Radio>
            </Radio.Group>
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
            <Radio.Group type="button">
              <Radio value={'1'}>是</Radio>
              <Radio value={'0'}>否</Radio>
            </Radio.Group>
          </Form.Item>

          <Form.Item
            label={t['searchTable.columns.isRequired']}
            field="isRequired"
            rules={[
              {
                required: true,
                message: t['searchTable.rules.isRequired.required'],
              },
            ]}
          >
            <Radio.Group type="button">
              <Radio value={'1'}>是</Radio>
              <Radio value={'0'}>否</Radio>
            </Radio.Group>
          </Form.Item>
          <Form.Item
            label={t['searchTable.columns.dictType']}
            field="dictType"
          >
            <DictTypeSelect/>
          </Form.Item>
      </Form>
    </Modal>
  );
}

export default EditColumn;
