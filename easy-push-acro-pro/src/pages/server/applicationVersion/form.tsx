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

function SearchForm(props: {
  onSearch: (values: Record<string, any>) => void;
  applicationId
}) {
  
  
  const { lang } = useContext(GlobalContext);

  const t = useLocale(locale);
  
  const [form] = useForm();

  const handleSubmit = () => {
    const values = form.getFieldsValue();
    values.applicationId = props.applicationId;
    props.onSearch(values);
  };

  const handleReset = () => {
    form.resetFields();
    props.onSearch({applicationId:props.applicationId});
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
              label={t['searchTable.columns.applicationName']}
              field="applicationName"
            >
              <Input placeholder={t['searchForm.applicationName.placeholder']} allowClear />
            </Form.Item>
          </Col> 
          <Col span={colSpan}>
            <Form.Item
              label={t['searchTable.columns.version']}
              field="version"
            >
              <Input placeholder={t['searchForm.version.placeholder']} allowClear />
            </Form.Item>
          </Col> 
          <Col span={colSpan}>
            <Form.Item 
              label={t['searchTable.columns.status']}
              field="status"
            >
              <DictDataSelect
                dictCode='application_status'
                placeholder={t['searchForm.status.placeholder']}

              />
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
