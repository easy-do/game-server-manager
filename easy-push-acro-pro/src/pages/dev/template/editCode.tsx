import React, { useEffect, useState } from 'react';
import { Modal } from '@arco-design/web-react';
import locale from './locale';
import useLocale from '@/utils/useLocale';
import { edit, infoRequest } from '@/api/template';
import MEditor from '@/components/mEdit/mEdit';

function EditCodePage(props: { id: number; visible; setVisible }) {


  function fetchData() {
    setTemplateCode('')
    if (props.id !== undefined) {
      infoRequest(props.id).then((res) => {
        const { success, data } = res.data;
        if (success) {
            setTemplateCode(data.templateCode)
        }
      });
    }
  }

  useEffect(() => {
    fetchData();
  }, [props.id]);

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
      edit(param).then((res) => {
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
        <MEditor height={clientHeight/1.2 + 'px'} theme='vs-dark' language='shell' value={templateCode} callBack={mdCallBack} />
    </Modal>
  );
}

export default EditCodePage;