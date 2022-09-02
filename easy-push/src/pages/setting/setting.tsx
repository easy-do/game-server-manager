import {
  IllustrationNoContent,
  IllustrationConstructionDark,
} from "@douyinfe/semi-illustrations";
import {
  Button,
  Card,
  Descriptions,
  Empty,
  Form,
  Input,
  Modal,
  Typography,
} from "@douyinfe/semi-ui";
import { observer } from "mobx-react";
import { useEffect } from "react";
import useStores from "../../utils/store";
import UserPoints from "../userPoints/userPoints";
import Paragraph from "@douyinfe/semi-ui/lib/es/typography/paragraph";

const Setting = () => {
  const { Text } = Typography;

  const { OauthStore, SettingStore } = useStores();
  const {
    emailModelShow,
    authorizationCode,
    authModelShow,
    onclickBingEmailButton,
    onclickOauthButton,
    onclickOauthOk,
    onclickOauthCancel,
    authorizationCodeOnChange,
    onclickBingEmailOk,
    onclickBingEmailCancel,
    emailValueOnChange,
    emailCodeDisable,
    sendEmailButtonDisable,
    sendEmailcode,
    validateEmail,
    loadingData,
    resetSecret,
    showPointsLogButton,
    showPointsModal,
    showPointsCancel
  } = SettingStore;
  const { loginFlag, userInfo, logout } = OauthStore;

  const { id, nickName, platform, authorization, email, secret, points, loginIp, lastLoginTime } = userInfo;
  let authorizationInfo = authorization
    ? JSON.parse(authorization)
    : authorization;

  useEffect(() => {
    OauthStore.getUserInfoRequst();
  }, []);


  const resetSecretButton = () =>{
    resetSecret();
    logout();
  }

  return (
    <>
      <Empty
        style={{ display: !loginFlag ? "block" : "none" }}
        image={<IllustrationNoContent style={{ width: 150, height: 150 }} />}
        darkModeImage={
          <IllustrationConstructionDark style={{ width: 150, height: 150 }} />
        }
        title={"未登录。"}
        description="点击头像登录,开始体验功能。"
      />

      <Card
        title="账户信息"
        bordered={false}
        style={{ marginBottom: 20, display: loginFlag ? "block" : "none" }}
        header={<Text>账户信息</Text>}
      >
        <Descriptions>
          <Descriptions.Item itemKey="用户名"><Paragraph copyable={{content:nickName}} >{nickName}</Paragraph></Descriptions.Item>
          <Descriptions.Item itemKey="账号登录平台">{platform}</Descriptions.Item>
          <Descriptions.Item itemKey="最近登录">
            {lastLoginTime}
          </Descriptions.Item>
          <Descriptions.Item itemKey="登录ip">
            {loginIp}
          </Descriptions.Item>
          <Descriptions.Item itemKey="密钥">
            <Paragraph copyable={{content:secret}} >*************</Paragraph>
            <Button theme="borderless" onClick={resetSecretButton} >重置密钥</Button>
          </Descriptions.Item>
          <Descriptions.Item itemKey="积分">
            {points}
            <Button theme="borderless" onClick={showPointsLogButton} >积分记录</Button>
            </Descriptions.Item>
          <Descriptions.Item itemKey="电子邮箱">
            {email ? (
              email
            ) : (
              <Button theme="borderless"
                type="secondary"
                style={{ marginRight: 8 }}
                onClick={onclickBingEmailButton}
              >
                绑定邮箱
              </Button>
            )}
          </Descriptions.Item>
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
          <Descriptions.Item itemKey="授权">
            {authorizationInfo ? (
              <Button
                theme="solid"
                type="secondary"
                onClick={onclickOauthButton}
              >
                已授权重新授权
              </Button>
            ) : (
              <Button
                theme="solid"
                type="secondary"
                onClick={onclickOauthButton}
              >
                {" "}
                未授权立即授权
              </Button>
            )}
          </Descriptions.Item>
        </Descriptions>
      </Card>

      <Modal
        title="输入授权码进行授权"
        visible={authModelShow}
        onOk={onclickOauthOk}
        onCancel={onclickOauthCancel}
        maskClosable={false}
      >
        <Input
          showClear
          value={authorizationCode}
          onChange={authorizationCodeOnChange}
        ></Input>
      </Modal>

      <Modal
        title="绑定邮箱"
        visible={emailModelShow}
        onOk={onclickBingEmailOk}
        onCancel={onclickBingEmailCancel}
        maskClosable={false}
        confirmLoading={loadingData}
        cancelLoading={loadingData}
      >
        <Form onValueChange={(values) => emailValueOnChange(values)}>
          <Form.Input
            label="邮箱"
            showClear
            validate={validateEmail}
            placeholder={"填写邮箱地址"}
            field="email"
          />
          <Form.Input
            label="验证码"
            showClear
            placeholder={"填写收到的验证码"}
            disabled={emailCodeDisable}
            noLabel
            field="emailCode"
          />
          <Button
            type="primary"
            disabled={sendEmailButtonDisable}
            onClick={sendEmailcode}
            htmlType="submit"
          >
            发送验证码
          </Button>
        </Form>
      </Modal>

      
      <Modal
        title="积分记录"
        visible={showPointsModal}
        onOk={showPointsCancel}
        onCancel={showPointsCancel}
        maskClosable={false}
        confirmLoading={loadingData}
        cancelLoading={loadingData}
        width="50%"
      >
        <UserPoints userId={id}/>
      </Modal>
    </>
  );
};

export default observer(Setting);
