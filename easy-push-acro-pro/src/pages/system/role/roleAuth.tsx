import { authRoleResource, resourceInfoTree, roleResourceIds } from '@/api/resource';
import useLocale from '@/utils/useLocale';
import { Modal, Spin, Tree, Notification } from '@arco-design/web-react';
import React, { useEffect, useState } from 'react';
import locale from './locale';

export interface Props {
  roleId: number
  visible: boolean
  setVisible
  successCallBack
}

function RoleAuthPage(props: Props) {

  const t = useLocale(locale);

  const [loading, setLoading] = React.useState(false);

  const [resourceTree, setResourceTree] = useState([]);

  const [checkedKeys, setCheckedKeys] = useState([]);


  //加载数据
  function fetchData() {
    setLoading(true)
    setResourceTree([])
    setCheckedKeys([])
    resourceInfoTree().then((res) => {
      const { success, data } = res.data
      if (success) {
        setResourceTree(data);
      }
    })

    if(props.roleId){
      roleResourceIds(props.roleId).then((res) => {
        const { success, data } = res.data
        if (success) {
          setCheckedKeys(data);
          setLoading(false)
        }
      })
    }
    
  }

  useEffect(() => {
    fetchData();
  }, [props.roleId]);


  //提交修改
  const handleSubmit = () => {
    authRoleResource({roleId:props.roleId,resourceIds:checkedKeys}).then((res) => {
      const { success, msg } = res.data
      if (success) {
        Notification.success({ content: msg, duration: 300 })
        props.successCallBack();
      }
    })
  };

  return (
    <Modal
      title={t['searchTable.update.title']}
      visible={props.visible}
      onOk={() => {
        handleSubmit();
      }}
      onCancel={() => {
        props.setVisible(false);
      }}
      autoFocus={false}
      focusLock={true}
      maskClosable={false}
    >
      <Spin tip='loading Data...' loading={loading}>
        <Tree
          checkable
          treeData={resourceTree}
          showLine={true}
          checkStrictly={true}
          fieldNames={{
            key: 'id',
            title: 'name',
            children: 'children',
          }}
          checkedKeys={checkedKeys}
          onCheck={(keys, extra) => {
            setCheckedKeys(keys);
          }}
          >
        </Tree>
      </Spin>

    </Modal>
  )

}


export default RoleAuthPage;