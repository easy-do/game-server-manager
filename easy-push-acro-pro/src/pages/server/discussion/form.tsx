import React, { useContext } from 'react';
import dayjs from 'dayjs';
import {
  Form,
  Input,
  Select,
  DatePicker,
  Button,
  Grid,
} from '@arco-design/web-react';
import { GlobalContext } from '@/context';
import locale from './locale';
import useLocale from '@/utils/useLocale';
import { IconRefresh, IconSearch } from '@arco-design/web-react/icon';
import styles from './style/index.module.less';
import { Status } from './constants';
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
              label={t['searchTable.columns.title']}
              field="title"
            >
              <Input placeholder={t['searchForm.title.placeholder']} allowClear />
            </Form.Item>
          </Col> 
          <Col span={colSpan}>
            <Form.Item
              label={t['searchTable.columns.status']}
              field="status"
            >
              <DictDataSelect dictCode={'audit_status'} placeholder={t['searchForm.status.placeholder']} />
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
