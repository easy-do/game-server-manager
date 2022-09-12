import React, { useContext } from 'react';
import dayjs from 'dayjs';
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

const { Row, Col } = Grid;
const { useForm } = Form;

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
            <Form.Item label={t['searchTable.columns.delFlag']} field="delFlag">
              <Input placeholder={t['searchForm.delFlag.placeholder']} allowClear />
            </Form.Item>
          </Col>
          <Col span={colSpan}>
            <Form.Item label={t['searchTable.columns.remark']} field="remark">
              <Input placeholder={t['searchForm.remark.placeholder']} allowClear />
            </Form.Item>
          </Col>
          <Col span={colSpan}>
            <Form.Item label={t['searchTable.columns.updateTime']} field="updateTime">
              <Input placeholder={t['searchForm.updateTime.placeholder']} allowClear />
            </Form.Item>
          </Col>
          <Col span={colSpan}>
            <Form.Item label={t['searchTable.columns.updateBy']} field="updateBy">
              <Input placeholder={t['searchForm.updateBy.placeholder']} allowClear />
            </Form.Item>
          </Col>
          <Col span={colSpan}>
            <Form.Item label={t['searchTable.columns.createTime']} field="createTime">
              <Input placeholder={t['searchForm.createTime.placeholder']} allowClear />
            </Form.Item>
          </Col>
          <Col span={colSpan}>
            <Form.Item label={t['searchTable.columns.createBy']} field="createBy">
              <Input placeholder={t['searchForm.createBy.placeholder']} allowClear />
            </Form.Item>
          </Col>
          <Col span={colSpan}>
            <Form.Item label={t['searchTable.columns.status']} field="status">
              <Input placeholder={t['searchForm.status.placeholder']} allowClear />
            </Form.Item>
          </Col>
          <Col span={colSpan}>
            <Form.Item label={t['searchTable.columns.isDefault']} field="isDefault">
              <Input placeholder={t['searchForm.isDefault.placeholder']} allowClear />
            </Form.Item>
          </Col>
          <Col span={colSpan}>
            <Form.Item label={t['searchTable.columns.roleSort']} field="roleSort">
              <Input placeholder={t['searchForm.roleSort.placeholder']} allowClear />
            </Form.Item>
          </Col>
          <Col span={colSpan}>
            <Form.Item label={t['searchTable.columns.roleKey']} field="roleKey">
              <Input placeholder={t['searchForm.roleKey.placeholder']} allowClear />
            </Form.Item>
          </Col>
          <Col span={colSpan}>
            <Form.Item label={t['searchTable.columns.roleName']} field="roleName">
              <Input placeholder={t['searchForm.roleName.placeholder']} allowClear />
            </Form.Item>
          </Col>
          <Col span={colSpan}>
            <Form.Item label={t['searchTable.columns.roleId']} field="roleId">
              <Input placeholder={t['searchForm.roleId.placeholder']} allowClear />
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
