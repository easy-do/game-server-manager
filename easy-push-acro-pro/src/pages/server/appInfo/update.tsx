import { DatePicker, Form, FormInstance, Input, Modal, Select, Spin, Notification, Upload, Icon } from '@arco-design/web-react';
import locale from './locale';
import useLocale from '@/utils/useLocale';
import { updateRequest, infoRequest } from '@/api/appInfo';
import { GlobalContext } from '@/context';
import { statusEnum } from './constants';
import { useContext, useEffect, useRef, useState } from 'react';
import React from 'react';
import DictDataSelect from '@/components/DictCompenent/dictDataSelect';
import { customRequest, imgeUrlcovertFiles, onPreview, onRemove } from '@/components/Upload/uploadImage';

function UpdatePage({ id, visible, setVisible, successCallBack }) {

  const TextArea = Input.TextArea;

  const formRef = useRef<FormInstance>();

  const { lang } = useContext(GlobalContext);

  const [loading, setLoading] = React.useState(false);

  // const [icons,setIcons] = useState([]); 

  // const [pictures,setPictures] = useState([]); 
  
  //加载数据
  function fetchData() {
    if (id !== undefined && visible) {
      setLoading(true)
      infoRequest(id).then((res) => {
        const { success, data } = res.data;
        if (success) {
          const icons = imgeUrlcovertFiles(data.icon)
          const pictures = imgeUrlcovertFiles(data.picture);
          // setIcons(icons)
          // setPictures(pictures)
          formRef.current.setFieldsValue({...data,icon:icons,picture:pictures});
        }
        setLoading(false)
      });
    }
  }

  useEffect(() => {
    fetchData();
  }, [id,visible]);

  const t = useLocale(locale);

  //提交修改
  const handleSubmit = () => {
    formRef.current.validate().then((values) => {
      const icon = values.icon;
      const picture = values.picture;
      const iocnStr = icon[0].response.url;
      const pictureStrs = [];
      picture.map((item) => {
        pictureStrs.push(item.response.url);
      });
      updateRequest({
        ...values,
        icon: iocnStr,
        picture: pictureStrs.join(','),
      }).then((res) => {
        console.info(values)
        const { success, msg} = res.data
        if(success){
          Notification.success({ content: msg, duration: 300 })
          successCallBack();
        }
      });
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
      maskClosable={false}
    >
      <Form
        style={{ width: '95%', marginTop: '6px' }}
        labelCol={{ span: lang === 'en-US' ? 7 : 6 }}
        wrapperCol={{ span: lang === 'en-US' ? 17 : 18 }}
        ref={formRef}
        labelAlign="left"
      >
      <Spin tip='loading Data...' loading={loading}>
        <Form.Item
          label={t['searchTable.columns.id']}
          field="id"
          hidden
        >
          <Input placeholder={t['searchForm.id.placeholder']} allowClear />
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.appName']}
          field="appName"
          rules={[
            { required: true, message: t['searchTable.rules.appName.required'] },
          ]}
        >
          <Input placeholder={t['searchForm.appName.placeholder']} allowClear />
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.version']}
          field="version"
          rules={[
            { required: true, message: t['searchTable.rules.version.required'] },
          ]}
        >
          <Input placeholder={t['searchForm.version.placeholder']} allowClear />
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.state']}
          field="state"
          rules={[
            { required: true, message: t['searchTable.rules.state.required'] },
          ]}
        >
           <DictDataSelect dictCode={'status_select'} placeholder={t['searchForm.state.placeholder']} />
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.startCmd']}
          field="startCmd"
        >
          <TextArea placeholder={t['searchForm.startCmd.placeholder']} />
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.stopCmd']}
          field="stopCmd"
        >
          <TextArea placeholder={t['searchForm.stopCmd.placeholder']} />
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.configFilePath']}
          field="configFilePath"
        >
          <TextArea placeholder={t['searchForm.configFilePath.placeholder']} />
        </Form.Item>
        <Form.Item
          triggerPropName='fileList'
          label={t['searchTable.columns.icon']}
          field="icon"
          rules={[
            { required: true, message: t['searchTable.rules.icon.required'] },
          ]}
        >
          <Upload
            listType="picture-card"
            multiple={false}
            limit={1}
            autoUpload={true}
            onRemove={onRemove}
            customRequest={customRequest}
            onPreview={onPreview}
          />
        </Form.Item>
        <Form.Item
          triggerPropName='fileList'
          label={t['searchTable.columns.picture']}
          field="picture"
          rules={[
            {
              required: true,
              message: t['searchTable.rules.picture.required'],
            },
          ]}
        >
          <Upload
            listType="picture-card"
            multiple={false}
            limit={3}
            autoUpload={true}
            onRemove={onRemove}
            customRequest={customRequest}
            onPreview={onPreview}
          />
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.appScope']}
          field="appScope"
          rules={[
            { required: true, message: t['searchTable.rules.appScope.required'] },
          ]}
        >
           <DictDataSelect dictCode={'scope_select'} placeholder={t['searchForm.appScope.placeholder']} />
        </Form.Item>
        <Form.Item
          label={t['searchTable.columns.description']}
          field="description"
          rules={[
            { required: true, message: t['searchTable.rules.description.required'] },
          ]}
        >
          <TextArea placeholder={t['searchForm.description.placeholder']} />
        </Form.Item>
      </Spin>
      </Form>
    </Modal>
  );
}

export default UpdatePage;