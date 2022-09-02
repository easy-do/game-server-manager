import { Dropdown, Avatar, Modal, Form, Button} from '@douyinfe/semi-ui';
import { IconChainStroked, IconExit, IconUser, IconRedoStroked } from '@douyinfe/semi-icons';
import { observer } from "mobx-react";
import useStores from '../../utils/store';
import { useNavigate } from 'react-router-dom';

const Avata = () =>{

    const navigate = useNavigate();

    const { OauthStore }  = useStores();

    const loginFlag = OauthStore.loginFlag
    let userInfo = OauthStore.userInfo;


    const {
        login,
        oncklickLoginButton,
        logout,
        loginModelShow,
        loginCancel,
        accountOnChange,
        sendEmailButtonDisable,
        emailCodeDisable,
        sendEmailcode,
        validateEmail,
        loginOk,
        loadingData,
        loginModel,
        getLoginModelFormApi,
        resetPasswordButton,
        resetPasswordModelShow,
        onclickresetPasswordOk,
        onclickresetPasswordCancel,
        resetPasswordValueOnChange,
        sendresetPasswordEmailcode

    } = OauthStore

    //页面渲染执行
    // useEffect(() => {
    //     OauthStore.autoLoginBytoken();
    // },[OauthStore])



    //进入个人中心
    const toSetting = () =>{
        navigate("/setting")
    }

    return (
            <><Dropdown
            render={<Dropdown.Menu>
                <Dropdown.Item style={{ display: !loginFlag ? 'block' : 'none' }} icon={<IconChainStroked />} type="tertiary" onClick={() => oncklickLoginButton()}> 普通登录</Dropdown.Item>
                <Dropdown.Item style={{ display: !loginFlag ? 'block' : 'none' }} icon={<IconChainStroked />} type="tertiary" onClick={() => login("baidu")}> 百度</Dropdown.Item>
                <Dropdown.Item style={{ display: !loginFlag ? 'block' : 'none' }} icon={<IconChainStroked />} type="tertiary" onClick={() => login("github")}> github</Dropdown.Item>
                <Dropdown.Item style={{ display: !loginFlag ? 'block' : 'none' }} icon={<IconChainStroked />} type="tertiary" onClick={() => login("gitee")}> gitee</Dropdown.Item>
                <Dropdown.Item style={{ display: !loginFlag ? 'block' : 'none' }} icon={<IconChainStroked />} type="tertiary" onClick={() => login("alipay")}> 支付宝</Dropdown.Item>
                <Dropdown.Item style={{ display: !loginFlag ? 'block' : 'none' }} icon={<IconChainStroked />} type="tertiary" onClick={() => login("dingtalk")}> 钉钉</Dropdown.Item>
                <Dropdown.Item style={{ display: !loginFlag ? 'block' : 'none' }} icon={<IconChainStroked />} type="tertiary" onClick={() => login("wechat_enterprise")}> 企业微信</Dropdown.Item>
                <Dropdown.Item style={{ display: !loginFlag ? 'block' : 'none' }} icon={<IconChainStroked />} type="tertiary" onClick={() => login("coding")}> 腾讯云</Dropdown.Item>
                <Dropdown.Item style={{ display: !loginFlag ? 'block' : 'none' }} icon={<IconChainStroked />} type="tertiary" onClick={() => login("oschina")}> 开源中国</Dropdown.Item>
                <Dropdown.Item style={{ display: !loginFlag ? 'block' : 'none' }} icon={<IconChainStroked />} type="tertiary" onClick={() => login("huawei")}> 华为</Dropdown.Item>

                <Dropdown.Item style={{ display: loginFlag ? 'block' : 'none' }} icon={<IconUser />} type="tertiary" onClick={() => toSetting()}>{userInfo ? userInfo.nickName : "未知用户名"}</Dropdown.Item>
                <Dropdown.Item style={{ display: loginFlag ? 'block' : 'none' }} icon={<IconRedoStroked />} type="tertiary" onClick={() => resetPasswordButton()}>重置密码</Dropdown.Item>
                <Dropdown.Item style={{ display: loginFlag ? 'block' : 'none' }} icon={<IconExit />} type="tertiary" onClick={() => logout()}>退出登陆</Dropdown.Item>
            </Dropdown.Menu>}>
            {/* <Tag
        style={{
            backgroundColor: 'white',
            overflow: 'visible',
            opacity:0.0
        }}
    > */}
            <Avatar
                alt='NL'
                size='default'
                src={userInfo ? userInfo.avatarUrl : ""}
                style={{ margin: 3 }} />

            {/* </Tag> */}
        </Dropdown>
        <Modal
        title="登录"
        okText={'确认登录'}
        cancelText={'暂不登录'}
        visible={loginModelShow}
        onCancel={loginCancel}
        maskClosable={false}
        onOk={loginOk}
        confirmLoading = {loadingData}
        cancelLoading = {loadingData}
        >
            <Form 
              getFormApi={getLoginModelFormApi}
              onValueChange={(values) => accountOnChange(values)}
            >
            <Form.Select noLabel initValue={'email'} field={'loginType'}>
                <Form.Select.Option label="密码登录" value={'password'} />
                <Form.Select.Option label="邮箱登录" value={'email'} />
                <Form.Select.Option label="密钥登陆" value={'secret'} />
            </Form.Select>
             {
              loginModel.loginType !== 'email'? '':                    
              <>
                    <Form.Input showClear noLabel validate={validateEmail} placeholder='请输入绑定的邮箱地址' field="userName" />
                    <Form.Input showClear noLabel placeholder='请输入接收到的验证码' disabled={emailCodeDisable} field="password" />
                    <Button type="primary" disabled={sendEmailButtonDisable} onClick={sendEmailcode} htmlType="submit">发送验证码</Button>
               </>
               }
               {loginModel.loginType !== 'password'? '': 
                    <>
                    <Form.Input showClear noLabel validate={validateEmail} placeholder='请输入绑定的邮箱地址' field="userName" />
                    <Form.Input showClear noLabel mode="password" placeholder='请输入账号密码' field="password" />
                    </>
                }
                {loginModel.loginType !== 'secret'? '': 
                    <>
                    <Form.Input showClear noLabel  mode="password" placeholder='请输入账号密钥' field="password" />
                    </>
                }
            </Form>
        </Modal>
        <Modal
        title="重置密码"
        visible={resetPasswordModelShow}
        onOk={onclickresetPasswordOk}
        onCancel={onclickresetPasswordCancel}
        maskClosable={false}
        confirmLoading={loadingData}
        cancelLoading={loadingData}
        >
            <Form 
                    onValueChange={(values) => resetPasswordValueOnChange(values)}
            >
            <Form.Input  showClear placeholder={'设置新密码'} mode="password"  noLabel field="password"/>
            <Form.Input  showClear placeholder={'填写收到的邮箱验证码'} disabled={emailCodeDisable}   noLabel field="emailCode"/>
            <Button type="primary" onClick={sendresetPasswordEmailcode} htmlType="submit">发送验证码</Button>
            </Form>
        </Modal>
        </>


    );
}


export default observer(Avata);