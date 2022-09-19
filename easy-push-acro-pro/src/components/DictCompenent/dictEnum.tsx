import { listByCode } from '@/api/dictData';
import React, { useEffect, useState } from 'react';


export interface props{
    dictCode:string
    value:object
}

function DictEnum(props) {

    const [dictDatas, setDictDatas] = useState([]);

    

    useEffect(() => {
        const  localDictData = localStorage.getItem(props.dictCode)
        if(localDictData){
            setDictDatas(JSON.parse(localDictData));
            return;
        }
        listByCode(props.dictCode).then((res) => {
            const { success, data } = res.data;
            if (success) {
                setDictDatas(data);
                localStorage.setItem(props.dictCode,data)
            }
        });
    }, []);

    return (
        <>
            {
                dictDatas.length > 0 ? dictDatas.forEach((item => {
                    if (item.dictValue === props.value) {
                        return item.dictLabel
                    }
                })) : ''
            }
        </>

    )

}

export default DictEnum;