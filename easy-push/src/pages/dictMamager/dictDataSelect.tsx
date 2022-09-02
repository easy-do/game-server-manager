import { Form } from "@douyinfe/semi-ui";
import { useEffect, useState } from "react";
import { listByCode } from "../../api/dictData";

const DictDataSelect = (props: any) => {
    const { label, field, initValue, dictCode, placeholder} = props

    const [dictDataList, setDictDataList] = useState([]);

    const listByCodeRequest= (dictCode:string) => {
      listByCode(dictCode).then((result) => {
        if (result.data.success) {
          setDictDataList(result.data.data)   
        }
      })
    }

    let optionList = new Array<{ value: any; label: any; otherKey: any; }>()

    if(dictDataList !== undefined && dictDataList.length > 0){
      dictDataList.map((item:any)=>{

        if(item.dictValueType === 'str'){
          optionList.push(
            {
              value: item.dictValue, label: item.dictLabel, otherKey:item.dictKey 
            })
        }else{
          optionList.push(
            {
              value: Number(item.dictValue), label: item.dictLabel, otherKey:item.dictKey 
            })
        }

      })
    }
  
    
  //页面渲染执行
  useEffect(() => {
        listByCodeRequest(dictCode);
  }, []);

    return <Form.Select 
            style={{ width: "95%" }}
            label={label}
            field={field}
            placeholder={placeholder}
            initValue={initValue}
            optionList={optionList}
            />
}



export default DictDataSelect;