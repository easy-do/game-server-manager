import { list as clientList } from '@/api/clientInfo';
import { GlobalContext } from '@/context';
import useLocale from '@/utils/useLocale';
import {
  Form,
  FormInstance,
  Input,
  Modal,
  Notification,
  Select,
  Spin,
  Typography,
} from '@arco-design/web-react';
import React, { useContext, useEffect, useRef, useState } from 'react';
import locale from './locale';
import RequestSelect from '../RequestSelect/RequestSelect';
import { installeRequest } from '@/api/application';
import { infoRequest, versionList } from '@/api/applicationVersion';
import checkLogin from '@/utils/checkLogin';

function InsallApplicationPage({
  applicationId,
  visible,
  setVisible,
  successCallBack,
}) {
  const t = useLocale(locale);

  const formRef = useRef<FormInstance>();

  const { lang } = useContext(GlobalContext);

  const [loading, setLoading] = useState(false);

  const [envs, setEnvs] = useState([]);

  const [options, setOptions] = useState([]);

  const onchageVersion = (value) => {
    setLoading(true);
        const values = value.split('--');
        infoRequest(values[0]).then((res) => {
            const { success, data } = res.data;
            if (success) {
              const env = JSON.parse(data.confData).envs;
              const envsCache = []
                env.map((item) => {
                    envsCache.push(
                      <Form.Item
                        label={item.envName}
                        initialValue={item.envValue}
                        field={'env.'+item.envKey}
                      >
                        <Input />
                      </Form.Item>
                    );
                });
                setEnvs(envsCache);
                setLoading(false);
            }
          });
  };

  //加载数据
  function fetchData() {
      setOptions([])
      setEnvs([])
    if (applicationId !== undefined && visible) {
        if(!checkLogin()){
            Notification.success({
                title:'',
                content:'未登录,跳转至登录页面'
            })
            setTimeout(() => {
                window.location.href = "/login"
            }, 1000);
            return;
          }else{
              versionList(applicationId).then((res) => {
                const { success, data } = res.data;
                if (success) {
                  const newOptions = [];
                  data.map((item) => {
                    newOptions.push({
                      value: item.id + '--' + item.applicationName,
                      label:
                        item.applicationName + ':' + item.version,
                      key: item.id,
                    });
                  });
                  setOptions(newOptions);
                }
                setLoading(false);
              });
          }
    }
  }

  useEffect(() => {
    fetchData();
  }, [visible, applicationId]);

  //提交
  const handleSubmit = () => {
    formRef.current.validate().then((values: any) => {
      installeRequest({ ...values, applicationId: applicationId, version:values.version.split('--')[0] }).then(
        (res) => {
          const { success, msg } = res.data;
          if (success) {
            formRef.current.clearFields();
            setEnvs([]);
            Notification.success(msg);
            successCallBack();
          }
          setLoading(false);
        }
      );
    });
  };

  return (
    <Modal
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
        layout="vertical"
      >
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
          <Select
            showSearch
            placeholder={t['searchForm.version.placeholder']}
            options={options}
            onChange={onchageVersion}
          />
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.client']}
          field="clientId"
          rules={[
            {
              required: true,
              message: t['searchTable.rules.client.required'],
            },
          ]}
        >
          <RequestSelect
            placeholder={t['searchForm.client.placeholder']}
            lableFiled="clientName"
            valueFiled="id"
            request={() => clientList()}
          />
        </Form.Item>

        <Typography.Title heading={6}>
          {t['searchTable.columns.EnvInfo']}
        </Typography.Title>
        <Spin loading={loading} style={{ width: '100%' }}>
        <Form.Item
                    field={'env'}
                >
                    {
                        envs
                    }

                </Form.Item>
        </Spin>
      </Form>
    </Modal>
  );
}

export default InsallApplicationPage;
