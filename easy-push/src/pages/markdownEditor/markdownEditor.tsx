import Editor from "md-editor-rt";
import { observer } from "mobx-react";
import "md-editor-rt/lib/style.css";
import { Toast } from "@douyinfe/semi-ui";
import { dlAddress, upload } from "../../api/upload";

const MarkdownEditor = (props: any) => {
  const { modelValue, onChange, onSave, previewOnly } = props;


  const onUploadImg = async (files:any,callback:any) =>{
    let fileFormData = new FormData();
    fileFormData.append('file',files[0])
    await upload(fileFormData).then((result) => {
      if (result.data.success) {
          Toast.success('上传成功')
          callback([dlAddress+result.data.data])
      }
  })
  }
  return (
    <Editor
      pageFullScreen={false}
      modelValue={modelValue}
      onChange={onChange}
      onSave={onSave}
      toolbarsExclude={["github"]}
      previewOnly={previewOnly}
      codeTheme={"atom"}
      onUploadImg={onUploadImg}
    />
  );
};

export default observer(MarkdownEditor);
