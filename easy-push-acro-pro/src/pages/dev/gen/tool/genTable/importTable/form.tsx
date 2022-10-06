import React, { useContext, useEffect, useState } from 'react';
import {
  Form,
  Input,
  Select,
  Button,
  Grid,
} from '@arco-design/web-react';
import { GlobalContext } from '@/context';
import locale from './locale';
import useLocale from '@/utils/useLocale';
import { IconRefresh, IconSearch } from '@arco-design/web-react/icon';
import styles from './style/index.module.less';
import { list } from '@/api/dataSourceManager';


const { Row, Col } = Grid;
const { useForm } = Form;


function SearchForm(props: {
  onSearch: (values: Record<string, any>) => void;
}) {
  

  const [loading, setLoading] = useState(true);

  const [ dataSourceList, setDataSourceList ] = useState([{sourceName:'系统默认',id:'default'}]);

  const fetchData = () => {
    setLoading(true);
    list({
      columns: ['id','sourceName']
    }).then((res) => {
      const { success, data } = res.data;
      if(success){
        data.push({sourceName:'系统默认',id:'default'})
        setDataSourceList(data);
      }
      setLoading(false);
    });

  }

  useEffect(() => {
    fetchData();
  }, []);
  
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
                label={t['searchTable.columns.dataSourceId']}
                field="dataSourceId"
                initialValue={'default'}
              >
                <Select
                  placeholder={t['searchForm.dataSourceId.placeholder']}
                  options={dataSourceList.map((item) => ({
                    label: item.sourceName,
                    value: item.id,
                  }))}
                  // mode="multiple"
                  allowClear
                />
              </Form.Item>
          </Col> 

          <Col span={colSpan}>
            <Form.Item 
              label={t['searchTable.columns.tableComment']}
              field="tableComment"
            >
              <Input placeholder={t['searchForm.tableComment.placeholder']} />
            </Form.Item>
          </Col> 
          <Col span={colSpan}>
            <Form.Item
              label={t['searchTable.columns.tableName']}
              field="tableName"
            >
              <Input placeholder={t['searchForm.tableName.placeholder']} allowClear />
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
