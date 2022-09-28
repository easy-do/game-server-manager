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
import ResourceTreeSelect from './resourceSelectTree';

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
              label={t['searchTable.columns.resourceName']}
              field="resourceName"
            >
              <Input placeholder={t['searchForm.resourceName.placeholder']} allowClear />
            </Form.Item>
          </Col> 
          <Col span={colSpan}>
            <Form.Item
              label={t['searchTable.columns.resourceCode']}
              field="resourceCode"
            >
              <Input placeholder={t['searchForm.resourceCode.placeholder']} allowClear />
            </Form.Item>
          </Col> 
          <Col span={colSpan}>
            <Form.Item
              label={t['searchTable.columns.resourceType']}
              field="resourceType"
            >
              <DictDataSelect dictCode={'system_resource_type'} placeholder={t['searchForm.resourceType.placeholder']} />
            </Form.Item>
          </Col> 
          <Col span={colSpan}>
            <Form.Item
              label={t['searchTable.columns.path']}
              field="path"
            >
              <Input placeholder={t['searchForm.path.placeholder']} allowClear />
            </Form.Item>
          </Col> 
          <Col span={colSpan}>
            <Form.Item
              label={t['searchTable.columns.isCache']}
              field="isCache"
            >
              <DictDataSelect dictCode={'is_no_select'} placeholder={t['searchForm.isCache.placeholder']} />
            </Form.Item>
          </Col> 
          <Col span={colSpan}>
            <Form.Item
              label={t['searchTable.columns.status']}
              field="status"
            >
              <DictDataSelect dictCode={'status_select'} placeholder={t['searchForm.status.placeholder']} />
            </Form.Item>
          </Col> 
          <Col span={colSpan}>
            <Form.Item
              label={t['searchTable.columns.parentId']}
              field="parentId"
            >
              <ResourceTreeSelect/>
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
