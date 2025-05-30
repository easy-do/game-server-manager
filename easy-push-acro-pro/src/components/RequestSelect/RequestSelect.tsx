import { Select } from '@arco-design/web-react';
import { AxiosPromise } from 'axios';
import React, { useEffect, useState } from 'react';

export interface props {
    value?: string;
    placeholder?: string;
    onChange?: (value) => void;
    lableFiled: string;
    valueFiled: string;
    request?: () => AxiosPromise<any>;
    valueType?: 'string' | 'number';
    mode?: 'multiple' | 'tags';
    disabled?:boolean;
}

function RequestSelect(props?: props) {

    const [loading, setLoading] = useState(false);

    const [options, setOptions] = useState([]);

    useEffect(() => {
        setLoading(true);
        props.request().then((res) => {
            const { success, data } = res.data;
            if (success) {
                const newOptions = [];
                data.map((item) => {

                    if (props.valueType === 'number') {
                        newOptions.push({
                            value: Number(item[props.valueFiled]),
                            label: item[props.lableFiled],
                            key: item[props.lableFiled],
                        });
                    } else {
                        newOptions.push({
                            value: item[props.valueFiled] + '',
                            label: item[props.lableFiled],
                            key: item[props.lableFiled],
                        });
                    }
                });
                setOptions(newOptions);
            }
            setLoading(false);
        });
    }, [props.lableFiled]);

    return (
        <Select
            disabled={props.disabled}
            mode={props.mode}
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

export default RequestSelect;
