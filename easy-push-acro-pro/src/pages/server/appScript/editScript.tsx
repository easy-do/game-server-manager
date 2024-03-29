import React, { useEffect, useState } from 'react';
import { Modal } from '@arco-design/web-react';
import locale from './locale';
import useLocale from '@/utils/useLocale';
import { updateRequest, infoRequest } from '@/api/scriptData';
import MEditor from '@/components/Medit/MEditor';

function EditScriptPage(props: { id: number; visible; setVisible }) {

  const t = useLocale(locale);

  const [ dataInfo, setDataInfo ] = useState({});

  const [templateCode, setTemplateCode] = useState('')

  function fetchData() {
    setTemplateCode('')
    if (props.id !== undefined && props.visible) {
      infoRequest(props.id).then((res) => {
        const { success, data } = res.data;
        if (success) {
            setTemplateCode(data.scriptFile)
            setDataInfo(data)
        }
      });
    }
  }

  useEffect(() => {
    fetchData();
  }, [props.id,props.visible]);



  
  const mdCallBack = (value) =>{
    setTemplateCode(value)
  }

  const handleSubmit = () => {
      const param = {
        ...dataInfo,
        id:props.id,
        scriptFile:templateCode
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
        <MEditor height={clientHeight/1.3 + 'px'} theme='vs-dark' showLanguageSelect language='shell' value={templateCode} callBack={mdCallBack} />
    </Modal>
  );
}

export default EditScriptPage;