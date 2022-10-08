import React, { useContext, useRef, useState } from 'react';
import dayjs from 'dayjs';
import { Form, FormInstance, Input, Modal, DatePicker, Select, Notification } from '@arco-design/web-react';
import locale from './locale';
import useLocale from '@/utils/useLocale';
import { addRequest } from '@/api/applicationInfo';
import { GlobalContext } from '@/context';
import { statusEnum } from './constants';
import RequestSelect from '@/components/RequestSelect/RequestSelect';
import { list as serverList } from '@/api/serverInfo';
import { list as appList } from '@/api/appInfo';
import { list as clientList } from '@/api/clientInfo';
import DictDataSelect from '@/components/DictCompenent/dictDataSelect';

function AddPage({ visible, setVisible, successCallBack }) {

  const TextArea = Input.TextArea;

  const formRef = useRef<FormInstance>();

  const { lang } = useContext(GlobalContext);

  const t = useLocale(locale);

  const serverSelect = <RequestSelect
    placeholder={t['searchForm.serverId.placeholder']}
    lableFiled="serverName"
    valueFiled="id"
    request={() => serverList()}
  />;

  const clientSelect = <RequestSelect
    placeholder={t['searchForm.serverId.placeholder']}
    lableFiled="clientName"
    valueFiled="id"
    request={() => clientList()}
  />

  const handleSubmit = () => {
    formRef.current.validate().then((values) => {
      addRequest(values).then((res) => {
        const { success, msg } = res.data
        if (success) {
          Notification.success({ content: msg, duration: 300 })
          successCallBack();
        }
      });
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
      >
        <Form.Item
          label={t['searchTable.columns.applicationName']}
          field="applicationName"
          rules={[
            { required: true, message: t['searchTable.rules.applicationName.required'] },
          ]}
        >
          <Input placeholder={t['searchForm.applicationName.placeholder']} allowClear />
        </Form.Item>

        <Form.Item
          label={t['searchTable.columns.deviceType']}
          field="deviceType"
          initialValue={0}
          rules={[
            { required: true, message: t['searchTable.rules.deviceType.required'] },
          ]}
        >
          <DictDataSelect dictCode='device_type' placeholder={t['searchForm.deviceType.placeholder']} />
        </Form.Item>

              <Form.Item
                shouldUpdate
                label={t['searchTable.columns.deviceId']}
                field="deviceId"
                rules={[
                  { required: true, message: t['searchTable.rules.deviceId.required'] },
                ]}
              >
                { 
                (values) => {
                 return(values.deviceType === 0 ? serverSelect:clientSelect)
                }
                }
              </Form.Item>
        <Form.Item
          label={t['searchTable.columns.appId']}
          field="appId"
          rules={[
            { required: true, message: t['searchTable.rules.appId.required'] },
          ]}
        >
          <RequestSelect placeholder={t['searchForm.appId.placeholder']} lableFiled='appName' valueFiled='id' request={() => appList()} />
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.status']}
          field="status"
        >
        <DictDataSelect
                placeholder={t['searchForm.status.placeholder']}
                dictCode={'status_select'}
          />
        </Form.Item>

      </Form>
    </Modal>
  );
}

export default AddPage;