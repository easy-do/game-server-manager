
import { Avatar, Descriptions} from "@douyinfe/semi-ui";
const UserManagerInfo = (props: any) => {
  const {dataInfo} = props;

  let authorizationInfo = dataInfo.authorization
  ? JSON.parse(dataInfo.authorization)
  : dataInfo.authorization;

  return (

    <Descriptions>
    <Descriptions.Item itemKey=""><Avatar src={dataInfo.avatarUrl}/></Descriptions.Item>
    <Descriptions.Item itemKey="用户id">{dataInfo.id}</Descriptions.Item>
    <Descriptions.Item itemKey="unionId">{dataInfo.unionId}</Descriptions.Item>
    <Descriptions.Item itemKey="密钥">{dataInfo.secret}</Descriptions.Item>
    <Descriptions.Item itemKey="用户名">{dataInfo.nickName}</Descriptions.Item>
    <Descriptions.Item itemKey="状态">{dataInfo.state === 0 ? '正常':'禁用'}</Descriptions.Item>
    <Descriptions.Item itemKey="积分">{dataInfo.points}</Descriptions.Item>
    <Descriptions.Item itemKey="邮箱">{dataInfo.email}</Descriptions.Item>
    <Descriptions.Item itemKey="平台">{dataInfo.platform}</Descriptions.Item>
    <Descriptions.Item itemKey="登录ip">{dataInfo.loginIp}</Descriptions.Item>
    <Descriptions.Item itemKey="最后登录时间">{dataInfo.lastLoginTime}</Descriptions.Item>
    <Descriptions.Item itemKey="注册时间">{dataInfo.createTime}</Descriptions.Item>
    <Descriptions.Item itemKey="授权到期时间">
            {authorizationInfo ? authorizationInfo.expires : "未授权"}
          </Descriptions.Item>
          <Descriptions.Item itemKey="服务授权数量">
            {authorizationInfo ? authorizationInfo.serverNum : "未授权"}
          </Descriptions.Item>
          <Descriptions.Item itemKey="应用授权数量">
            {authorizationInfo ? authorizationInfo.appNum : "未授权"}
          </Descriptions.Item>
          <Descriptions.Item itemKey="应用创作数量">
            {authorizationInfo ? authorizationInfo.appCreationNum : "未授权"}
          </Descriptions.Item>
          <Descriptions.Item itemKey="脚本创作数量">
            {authorizationInfo ? authorizationInfo.scriptCreationNum : "未授权"}
          </Descriptions.Item>
    </Descriptions>
  );
};

export default UserManagerInfo;

