import { Button, Form} from "@douyinfe/semi-ui";
import useStores from "../../utils/store";
import { IconUpload } from '@douyinfe/semi-icons';
const FormUploadImg = (props: any) => {
  const {field, label, limit} = props;

  const { UploadStore } = useStores();
  const { uploadHeaders, uploadAddress} = UploadStore;


  const onsuccess = (response: any, file:any) =>{
    console.info(response)
    console.info(file)
    file.uid = response.data
  }

  return (
    <Form.Upload style={{ width: "95%" }}
    field={field}
    label={label}
    limit={limit}
    action={uploadAddress}
    headers={uploadHeaders}
    onSuccess={onsuccess}
    fileName={'file'}
>
    <Button icon={<IconUpload />} theme="light">
        点击上传
    </Button>
    </Form.Upload>
  );
};

export default FormUploadImg;
