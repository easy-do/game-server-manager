import { list } from '@/api/dictType';
import { Select } from '@arco-design/web-react';
import React, { useEffect, useState } from 'react';

export interface props{
  value?:string;
  onChange?:(value)=>void;
  placeholder?:string
}

function DictTypeCodeSelect(props?:props) {

  const [loading, setLoading] = useState(false);

  const [options, setOptions] = useState([]);

  useEffect(() => {
    setLoading(true);
    list().then((res) => {
      const { success, data } = res.data;
      if (success) {
        const newOptions = [];
        data.map((item) => {
          newOptions.push({
              value: item.dictCode,
              label: item.dictName,
              key: item.dictCode,
            });
        });
        setOptions(newOptions);
      }
      setLoading(false);
    });
  }, []);

  return (
    <Select
      value={props.value}
      placeholder={props.placeholder}
      loading={loading}
      showSearch
      options={options}
      onChange={props.onChange}
      filterOption={(inputValue, option) =>
        option.props.children.toLowerCase().indexOf(inputValue.toLowerCase()) >=
        0
      }
    />
  );
}

export default DictTypeCodeSelect;
