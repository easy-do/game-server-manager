import { editLanguages } from "@/utils/systemConstant";
import { Select, Space } from "@arco-design/web-react";
import React,{ useState } from "react";
import MonacoEditor from "react-monaco-editor";

export interface MEditorProps{

  value:string;

  language?: string;

  width?: string;

  height?: string;

  theme?: string;

  options? : {
    selectOnLineNumbers: true,
  };

  callBack:(value:string)=>void

}

function MEditor (props:MEditorProps) {

  //在编辑器挂载之前发出的事件
  const editorWillMount = (monaco: any) => {
    return
  };

  //安装编辑器时发出的事件
  const editorDidMount = (editor: any, monaco: any) => {
    console.info('editorDidMount')
  };

  // 编辑器内容变化事件
 const onChange = (newValue: any, e: any) => {
      props.callBack(newValue)
  }

  //编辑器卸载之前发出的事件
  const editorWillUnmount = (editor: any, monaco: any) => {
    console.info('editorWillUnmount')
  };

  const [languages,setLanguages] = useState(props.language)

  return (
    <div>
      <Space>
      切换语言<Select value={languages} size='small' showSearch style={{width:'120px'}} options={editLanguages} onChange={(value)=>setLanguages(value)} />
      </Space>
      
      <MonacoEditor
      width={props.width}
      height={props.height}
      language={languages}
      theme={props.theme}
      value={props.value}
      options={props.options}
      editorWillMount={editorWillMount}
      editorDidMount={editorDidMount}
      onChange={onChange}
      editorWillUnmount={editorWillUnmount}
    />
    </div>
    
  );
}

export default MEditor;
