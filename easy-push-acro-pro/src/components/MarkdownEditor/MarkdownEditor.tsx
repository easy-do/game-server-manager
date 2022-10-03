import React,{ useState } from "react";
import Editor from "md-editor-rt";
import "md-editor-rt/lib/style.css";
import { Notification } from "@arco-design/web-react";
import { downloadPath, upload } from "@/api/oss";

export interface MarkdownEditorProps{

  value?:string;

  onChange?:(value:string)=>void;

  onSave?:(value:string)=>void;

  previewOnly?:boolean;

}

const MarkdownEditor = (props: MarkdownEditorProps) => {
  const { value, onChange, onSave, previewOnly } = props;


  const onUploadImg = async (files:any,callback:any) =>{
    const fileFormData = new FormData();
    fileFormData.append('file',files[0])
    await upload(fileFormData).then((result) => {
      const { success,data } = result.data;
      if (success) {
          Notification.success({content:"上传成功"})
          callback([downloadPath + data.groupName +'/'+data.filePath+'/'+data.fileName])
      }
  })
  }

  return (
    <Editor
      pageFullScreen={false}
      modelValue={value}
      onChange={onChange}
      onSave={onSave}
      toolbarsExclude={["github"]}
      previewOnly={previewOnly}
      codeTheme={"atom"}
      onUploadImg={onUploadImg}
    />
  );
};

export default MarkdownEditor;
