
import { Descriptions} from "@douyinfe/semi-ui";
const RoleManagerInfo = (props: any) => {
  const {dataInfo} = props;


  return (

    <Descriptions>
    <Descriptions.Item itemKey="角色id">{dataInfo.roleId}</Descriptions.Item>
    <Descriptions.Item itemKey="角色名称">{dataInfo.roleName}</Descriptions.Item>
    <Descriptions.Item itemKey="权限代码">{dataInfo.roleKey}</Descriptions.Item>
    <Descriptions.Item itemKey="开启状态">{dataInfo.status === 0 ? '正常':'禁用'}</Descriptions.Item>
    <Descriptions.Item itemKey="创建时间">{dataInfo.createTime}</Descriptions.Item>
    <Descriptions.Item itemKey="更新时间">{dataInfo.updateTime}</Descriptions.Item>
    </Descriptions>
  );
};

export default RoleManagerInfo;

