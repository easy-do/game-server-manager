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
  }

export const customRequest = (option) => {
    const { onProgress, onError, onSuccess, file } = option;
    const fileFormData = new FormData();
    fileFormData.append('file',file)
    onProgress(0)
    upload(fileFormData).then((res)=>{
      const { success, data} = res.data
      if(success){
        onProgress(100)
        onSuccess(data);
      }else{
        onError(data);
      }
    });
  }

export const  onPreview=(file) => {
    Modal.info({
      title: 'Preview',
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
    remove(file.response.url).then((res)=>{
      const { success } = res.data
      if(success){
        return true;
      }else{
        return false;
      }
      })
  }

export const imgeUrlcovertFiles = (imgesUrls:string)=>{
    const urls = imgesUrls.split(',')
    const files = []
    urls.map((item,index)=>{
        files.push(
            {
                uid: index+1+'',
                url: '/api/oss/minio/'+item,
                name: item,
                response:{
                  url:item
                }
              }
        )
    })
    return files;
}
