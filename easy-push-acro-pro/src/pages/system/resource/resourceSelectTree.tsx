import { resourceInfoTree } from '@/api/resource';
import { Spin, Tree, TreeSelect } from '@arco-design/web-react';
import React, { useEffect, useState } from 'react';


export interface Props{

    value?: string,

    onChange?:(value)=>void;
}


function ResourceTreeSelect(props:Props){


 
const [resourceTree, setResourceTree] = useState([]);
  

      //加载数据
  function fetchData() {
    setResourceTree([])
    resourceInfoTree().then((res) => {
      const { success, data } = res.data
      if (success) {
        const newData = [{
          id: 0,
          name: '根节点',
          children: data,
        }]
        setResourceTree(newData);
      }
    })   
  }

  useEffect(() => {
    fetchData();
  }, []);

    return (

        <TreeSelect 
            fieldNames={{
                key: 'id',
                title: 'name',
                children: 'children',
            }}
            // defaultValue={props.value} 
            value={props.value} 
            allowClear
            treeData={resourceTree} 
            onChange={props.onChange}
            >
        </TreeSelect>
    )

}

export default ResourceTreeSelect;

 