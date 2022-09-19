import { listByCode } from '@/api/dictData';
import { Select } from '@arco-design/web-react';
import React, { useEffect, useState } from 'react';

export interface props{
  dictCode:string
  value?:string;
  onChange?:(value)=>void;
  placeholder?:string
}

function DictDataSelect(props:props) {
  const [loading, setLoading] = useState(false);

  const [options, setOptions] = useState([]);

  useEffect(() => {
    setLoading(true);
    listByCode(props.dictCode).then((res) => {
      const { success, data } = res.data;
      if (success) {
        const options = [];
        data.map((item) => {
          if (item.dictValueType === 'string') {
            options.push({
              value: item.dictValue,
              label: item.dictLabel,
            });
          } else {
            options.push({
              value: Number(item.dictValue),
              label: item.dictLabel,
            });
          }
        });
        setOptions(options);
      }
      setLoading(false);
    });
  }, []);

  return (
    <Select
      value={props.value}
      onChange={props.onChange}
      placeholder={props.placeholder}
      loading={loading}
      showSearch
      options={options}
      filterOption={(inputValue, option) =>
        option.props.children.toLowerCase().indexOf(inputValue.toLowerCase()) >=
        0
      }
    />
  );
}

export default DictDataSelect;
