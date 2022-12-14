import React, { useContext } from 'react';
import {
  Form,
  Input,
  Button,
  Grid,
} from '@arco-design/web-react';
import { GlobalContext } from '@/context';
import locale from './locale';
import useLocale from '@/utils/useLocale';
import { IconRefresh, IconSearch } from '@arco-design/web-react/icon';
import styles from './style/index.module.less';
import DictDataSelect from '@/components/DictCompenent/dictDataSelect';

const { Row, Col } = Grid;
const { useForm } = Form;
const TextArea = Input.TextArea;

function SearchForm(props: {
  onSearch: (values: Record<string, any>) => void;
}) {
  
  
  const { lang } = useContext(GlobalContext);

  const t = useLocale(locale);
  
  const [form] = useForm();

  const handleSubmit = () => {
    const values = form.getFieldsValue();
    props.onSearch(values);
  };

  const handleReset = () => {
    form.resetFields();
    props.onSearch({});
  };

  const colSpan = lang === 'zh-CN' ? 8 : 12;

  return (
    <div className={styles['search-form-wrapper']}>
      <Form
        form={form}
        className={styles['search-form']}
        labelAlign="left"
        labelCol={{ span: 5 }}
        wrapperCol={{ span: 19 }}
      >
        <Row gutter={24}>
          <Col span={colSpan}>
            <Form.Item 
              label={t['searchTable.columns.status']}
              field="status"
              rules={[
                { required: true, message: t['searchTable.rules.status.required'] },
              ]}
            >
              <DictDataSelect
                placeholder={t['searchForm.status.placeholder']}
                dictCode={'status_select'}
              />
            </Form.Item>
          </Col>
          <Col span={colSpan}>
            <Form.Item 
              label={t['searchTable.columns.params']}
              field="params"
              rules={[
                { required: true, message: t['searchTable.rules.params.required'] },
              ]}
            >
              <TextArea placeholder={t['searchForm.params.placeholder']} />
            </Form.Item>
          </Col> 
          <Col span={colSpan}>
            <Form.Item
              label={t['searchTable.columns.url']}
              field="url"
              rules={[
                { required: true, message: t['searchTable.rules.url.required'] },
              ]}
            >
              <Input placeholder={t['searchForm.url.placeholder']} allowClear />
            </Form.Item>
          </Col> 
          <Col span={colSpan}>
            <Form.Item 
              label={t['searchTable.columns.sourceType']}
              field="sourceType"
              rules={[
                { required: true, message: t['searchTable.rules.sourceType.required'] },
              ]}
            >
              <DictDataSelect
                placeholder={t['searchForm.status.placeholder']}
                dictCode={'status_select'}
              />
            </Form.Item>
          </Col>
          <Col span={colSpan}>
            <Form.Item
              label={t['searchTable.columns.sourceCode']}
              field="sourceCode"
              rules={[
                { required: true, message: t['searchTable.rules.sourceCode.required'] },
              ]}
            >
              <Input placeholder={t['searchForm.sourceCode.placeholder']} allowClear />
            </Form.Item>
          </Col> 
          <Col span={colSpan}>
            <Form.Item
              label={t['searchTable.columns.sourceName']}
              field="sourceName"
              rules={[
                { required: true, message: t['searchTable.rules.sourceName.required'] },
              ]}
            >
              <Input placeholder={t['searchForm.sourceName.placeholder']} allowClear />
            </Form.Item>
          </Col> 
        </Row>
      </Form>
      <div className={styles['right-button']}>
        <Button type="primary" icon={<IconSearch />} onClick={handleSubmit}>
          {t['searchTable.form.search']}
        </Button>
        <Button icon={<IconRefresh />} onClick={handleReset}>
          {t['searchTable.form.reset']}
        </Button>
      </div>
    </div>
  );
}

export default SearchForm;
