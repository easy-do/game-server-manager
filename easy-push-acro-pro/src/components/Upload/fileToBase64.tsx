import { remove, upload } from '@/api/oss';
import { Modal, Upload } from '@arco-design/web-react';
import { UploadItem } from '@arco-design/web-react/es/Upload';
import React from 'react';

export interface props{
    multiple?:boolean;
    limit:number;
    autoUpload?:boolean;
    onChange?:(value)=>void;
    defaultFileList?:UploadItem[];
    fileList?: UploadItem[];
    resultBase64?:(value)=>void;
  }

export const customRequest = (option) => {
    const { onProgress, onError, onSuccess, file } = option;
    const reader = new FileReader();
    reader.readAsDataURL(option.file);
    reader.onloadend = function(e) {
      console.log(e.target.result);// 打印图片的base64
      onProgress(100)
      onSuccess(e.target.result);
    };
  }

export const onPreview=(file) => {
    Modal.info({
      content: (
        <img
          src={file.url || URL.createObjectURL(file.originFile)}
          style={{
            maxWidth: '100%',
          }}
        ></img>
      ),
    });
  }

export const onRemove=(file:any)=>{
  return true
  }

  export const base6CcovertFile = (base64:string)=>{
    const files = []
    files.push(
        {
            uid: 1,
            url: base64,
            name: "123",
            response:base64
          }
    )
    return files;
}


