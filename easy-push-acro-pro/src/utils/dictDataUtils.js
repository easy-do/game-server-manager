import { listByCode } from "../api/dictData"

export function getDictList(dictCode){
    const localDictData = sessionStorage.getItem(dictCode)
    if(localDictData){
        return JSON.parse(localDictData)
    }else{
        listByCode(dictCode).then((res)=>{
            const { success, data } = res.data
            if(success){
                sessionStorage.setItem(dictCode,JSON.stringify(data))
                return data;
            }else{
                return []
            }
        })
    }
}

export const dictLabelEnum = (dictCode,type) => {
    const dictList = getDictList(dictCode);
    const dictEnum = {}
    if(dictList && dictList.length > 0){
        dictList.map((item) => {
            let dictValue = '';
            const dictLabel = item.dictLabel;
            if(type === 'number'){
                dictValue = Number(item.dictValue)
            }
            if(type === 'string'){
                dictValue = item.dictValue+'';
            }
    
            dictEnum[dictValue]=dictLabel
        })
    }
    return dictEnum;
}

