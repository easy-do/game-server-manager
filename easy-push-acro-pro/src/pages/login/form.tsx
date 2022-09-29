import {
  Form,
  Input,
  Link,
  Button,
  Space,
  Select,
  Notification,
} from '@arco-design/web-react';
import { FormInstance } from '@arco-design/web-react/es/Form';
import { IconLock, IconUser } from '@arco-design/web-react/icon';
import React, { useEffect, useRef, useState } from 'react';
import useStorage from '@/utils/useStorage';
import useLocale from '@/utils/useLocale';
import locale from './locale';
import styles from './style/index.module.less';
import { loginRequst, platformLogin } from '@/api/oauth';
import decode from 'jwt-decode';
import { useDispatch } from 'react-redux';
import { userResource } from '@/api/resource';
import { IRoute, staticRoutes } from '@/routes';

export default function LoginForm() {
  const formRef = useRef<FormInstance>();
  const [errorMessage, setErrorMessage] = useState('');
  const [loading, setLoading] = useState(false);

  const t = useLocale(locale);

  const [loginType, setLoginType] = useState('password');
  const [sendMail, setSendMail] = useState(false);

  const [token,setToken] = useStorage('token');

  const dispatch = useDispatch();

  function flatform(platFrom){
    platformLogin(platFrom).then((response) => {
      Notification.success({content:'跳转至授权页面'})
      window.location.href = response.data.data;
    })
  }


  /**登录 */
  function login(params) {
    setErrorMessage('');
    setLoading(true);
    loginRequst(params)
      .then((res) => {
        const { success, msg, data } = res.data;
        if (success) {
          window.location.href = data;
        } else {
          setErrorMessage(msg || t['login.form.login.errMsg']);
        }
      })
      .finally(() => {
        setLoading(false);
      });
  }

  function onSubmitClick() {
    formRef.current.validate().then((values) => {
      login(values);
    });
  }

  /**发送验证码 */
  function onSendMailCode() {
    setSendMail(true);
    console.log('send mail code');
  }

  // 读取 localStorage，设置初始值
  useEffect(() => {

    // if (token) {
    //   window.location.href = '/';
    // }

    const search = window.location.search;
    const param = new URLSearchParams(search);
    const token = param.get('token');
    if (token) {
      setToken(token)
      const tokenInfo: any = decode(token);
      localStorage.setItem('userInfo', JSON.stringify(tokenInfo.userInfo));
      dispatch({
        type: 'update-userInfo',
        payload: {
          userInfo: tokenInfo.userInfo,
        },
      });
      // 获取菜单
      userResource().then((res) => {
        const { success } = res.data;
        const data: IRoute[] = res.data.data;
        if (success) {
          const side: IRoute = data[0];
          staticRoutes.forEach((item) => {
            side.children.push(item);
          });   
          data[0] = side;  
          // 更新菜单
          dispatch({
            type: 'update-routes',
            payload: { systemRoutes: data},
          });
        // 跳转登录成功页
         window.location.href = '/loginSuccess';
        }
      });
    }
  }, []);

  return (
    <div className={styles['login-form-wrapper']}>
      <div className={styles['login-form-title']}>{t['login.form.title']}</div>
      <div className={styles['login-form-sub-title']}>
        {t['login.form.title']}
      </div>
      <div className={styles['login-form-error-msg']}>{errorMessage}</div>
      <Form
        className={styles['login-form']}
        layout="vertical"
        ref={formRef}
        initialValues={{ userName: '', password: '' }}
      >
        <Form.Item
          field="loginType"
          initialValue={'password'}
          rules={[
            { required: true, message: t['login.form.loginType.errMsg'] },
          ]}
        >
          <Select placeholder={t['login.form.loginType.placeholder']}>
            <Select.Option
              onClick={() => setLoginType('platform')}
              value="platform"
            >
              {' '}
              第三方账号登录
            </Select.Option>
            <Select.Option
              onClick={() => setLoginType('password')}
              value="password"
            >
              {' '}
              账号登录
            </Select.Option>
            <Select.Option
              onClick={() => setLoginType('secret')}
              value="secret"
            >
              {' '}
              密钥登录
            </Select.Option>
            <Select.Option onClick={() => setLoginType('email')} value="email">
              {' '}
              验证码登录
            </Select.Option>
          </Select>
        </Form.Item>

        {loginType === 'email' || loginType === 'password' ? (
          <Form.Item
            field="userName"
            rules={[{ required: true, message: '账号不能为空' }]}
            shouldUpdate
          >
            <Input
              prefix={<IconUser />}
              placeholder={t['已绑定账户的邮箱']}
              onPressEnter={onSubmitClick}
            />
          </Form.Item>
        ) : null}

        {loginType === 'password' ? (
          <Form.Item
            field="password"
            rules={[{ required: true, message: t['密码不能为空'] }]}
          >
            <Input.Password
              prefix={<IconLock />}
              placeholder={t['密码']}
              onPressEnter={onSubmitClick}
            />
          </Form.Item>
        ) : null}

        {loginType === 'email' ? (
          <Form.Item
            field="password"
            rules={[{ required: true, message: t['验证码不能为空'] }]}
          >
            <Input
              prefix={<IconLock />}
              placeholder={t['验证码']}
              onPressEnter={onSubmitClick}
            />
          </Form.Item>
        ) : null}

        {loginType === 'secret' ? (
          <Form.Item
            field="password"
            rules={[{ required: true, message: t['密钥不能为空'] }]}
          >
            <Input.Password
              prefix={<IconLock />}
              placeholder={t['账号密钥']}
              onPressEnter={onSubmitClick}
            />
          </Form.Item>
        ) : null}

        { loginType === 'platform'?
         (
          <Form.Item>
          <Button type="text" onClick={() => flatform("baidu")}> 百度</Button>
          <Button type="text" onClick={() => flatform("github")}> github</Button>
          <Button type="text" onClick={() => flatform("gitee")}> gitee</Button>
          <Button type="text" onClick={() => flatform("alipay")}> 支付宝</Button>
          <Button type="text" onClick={() => flatform("dingtalk")}> 钉钉</Button>
          <Button type="text" onClick={() => flatform("wechat_enterprise")}> 企业微信</Button>
          <Button type="text" onClick={() => flatform("coding")}> 腾讯云</Button>
          <Button type="text" onClick={() => flatform("oschina")}> 开源中国</Button>
          <Button type="text" onClick={() => flatform("huawei")}> 华为</Button>
          </Form.Item>
         ):null
        }

        <Space size={16} direction="vertical">
          <div className={styles['login-form-password-actions']}>
            {loginType === 'email' ? (
              <Link disabled={sendMail} onClick={() => onSendMailCode()}>
                {t['login.form.sendmail']}
              </Link>
            ) : null
            }
          </div>
          {
          loginType !== 'platform'?
          (
            <Button type="primary" long onClick={onSubmitClick} loading={loading}>
            {t['login.form.login']}
             </Button>):null
          }
        </Space>
      </Form>
    </div>
  );
}
