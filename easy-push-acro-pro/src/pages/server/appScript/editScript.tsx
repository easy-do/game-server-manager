import React, { useEffect, useState } from 'react';
import { Modal } from '@arco-design/web-react';
import locale from './locale';
import useLocale from '@/utils/useLocale';
import { updateRequest, infoRequest } from '@/api/appScript';
import MEditor from '../../../components/MEdit/mEdit';

function EditScriptPage(props: { id: number; visible; setVisible }) {


  function fetchData() {
    setTemplateCode('')
    if (props.id !== undefined && props.visible) {
      infoRequest(props.id).then((res) => {
        const { success, data } = res.data;
        if (success) {
            setTemplateCode(data.scriptFile)
        }
      });
    }
  }

  useEffect(() => {
    fetchData();
  }, [props.id,props.visible]);

  const t = useLocale(locale);

  const [templateCode, setTemplateCode] = useState('')

  const mdCallBack = (value) =>{
    setTemplateCode(value)
  }

  const handleSubmit = () => {
      const param = {
        id:props.id,
        templateCode:templateCode
      }
      updateRequest(param).then((res) => {
        if (res.data.success) {
          props.setVisible(false);
        }
      });
  };


 const clientHeight = document.body.clientHeight

  return (
    <Modal
      title={t['searchTable.update.title']}
      visible={props.visible}
      onOk={() => {
        handleSubmit();
      }}
      onCancel={() => {
        props.setVisible(false);
      }}
      autoFocus={false}
      focusLock={true}
      style={{width:'100%',height:'100%'}}
      wrapStyle={{width:'100%',height:'100%'}}
      unmountOnExit={true}
    >
        <MEditor height={clientHeight/1.3 + 'px'} theme='vs-dark' language='shell' value={templateCode} callBack={mdCallBack} />
    </Modal>
  );
}

export default EditScriptPage;