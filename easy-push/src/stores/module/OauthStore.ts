import { Toast } from '@douyinfe/semi-ui';
import { makeAutoObservable, runInAction } from 'mobx'
import { getUserInfo, platformLogin, logout, sendEmailcode, login, resetPassword } from '../../api/oauth';
import cookie from 'react-cookies'
import { userMenu } from '../../api/menuManager';

class OauthStore {

  constructor() {
    makeAutoObservable(this)
  }

  inFifteenMinutes = new Date(new Date().getTime() + 0.1 * 3600 * 1000);

  loginFlag:boolean = localStorage.getItem("loginFlag") ? true : false;

  userInfoStr = localStorage.getItem("userInfo");

  userInfo: any = this.userInfoStr ? JSON.parse(this.userInfoStr) : JSON.parse("{}")

  loadingData = false


  loginModelFormApi = {}

  loginModelShow = false;

  loginModel = {
    userName: "",
    password: "",
    loginType: "email"
  }

  emailModelShow = false
  emailCodeDisable = true
  sendEmailButtonDisable = true

  resetPasswordMode = {
    'userId': 0,
    'emailCode': '',
    'password': ''
  }
  resetPasswordModelShow = false

  userMenuTree:any = []

  loading = () => {
    runInAction(() => {
      this.loadingData = !this.loadingData;
    })
  }

  /**
 * 登录
 * @param platFrom 第三方平台登录
 */
  login(platFrom: string) {
    platformLogin(platFrom).then((response) => {
      Toast.info('跳转至授权页面')
      window.location.href = response.data.data;
    })
  }

  /**
   * 点击普通登录按钮
   */
  oncklickLoginButton = () => {
    runInAction(() => {
      this.loginModelShow = true;
    })
  }

  /**
   * 取消普通登录
   */
  loginCancel = () => {
    runInAction(() => {
      this.loginModelShow = false;
    })
  }

  /**
   * 登录表单数据更新
   */
  accountOnChange = (value: any) => {
    runInAction(() => {
      this.loginModel = value;
      if (value.password) {

      }
    })
  }

  //校验邮箱地址
  validateEmail = (value: string) => {
    const regexp = new RegExp(/^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/);
    const serchfind = regexp.test(value);
    console.log(serchfind)
    if (serchfind) {
      this.sendEmailButtonDisable = false;
      return '';
    } else {
      this.sendEmailButtonDisable = true;
      return '邮箱格式错误';
    }
  }

  /**
* 发送邮箱验证码
*/
  sendEmailcode = () => {
    this.loading();
    let email = this.loginModel.userName;
    sendEmailcode(email).then((result) => {
      if (result.data.success) {
        runInAction(() => {
          this.emailCodeDisable = false
          this.sendEmailButtonDisable = true
        })
        Toast.success("发送成功,请尽快填写。")
        setTimeout(() => {
          this.sendEmailButtonDisable = false
        }, 60000);
      }
      this.loading();
    })
  }

  getLoginModelFormApi = (api: any) => {
    this.loginModelFormApi = api;
  }

  /**
 * 确认普通登录
 */
  loginOk = () => {
    this.loading();
    login(this.loginModel).then((result) => {
      if (result.data.success) {
        Toast.success('登录成功')
        window.location.href = result.data.data;
      }
      this.loading();
    })
  }

  /** 
   * 退出登录
   */
  logout = () => {
    runInAction(() => {
      logout().then((res) => {
        if (res.data.success) {
          this.cleanAuth();
          Toast.success('退出登陆')
          window.location.href = process.env.PUBLIC_URL + "/"
        }
      })
    })
  }


  /** 
   * 重置密码
   */
  resetPasswordButton = () => {
    if (!this.userInfo.email) {
      Toast.warning("请先绑定邮箱")
      return;
    }
    runInAction(() => {
      this.resetPasswordModelShow = true
    })
  }

  /** 
   * 重置密码确认
   */
  onclickresetPasswordOk = () => {
    let param = this.resetPasswordMode;
    param.userId = this.userInfo.id
    this.loading();
    resetPassword(param).then((result) => {
      if (result.data.success) {
        runInAction(() => {
          this.resetPasswordModelShow = false
        })
        Toast.success("修改成功,请重新登录。")
        setTimeout(() => {
          this.sendEmailButtonDisable = false
          this.cleanAuth();
        }, 1000);
      }
      this.loading();
    })
  }

  /** 
   * 重置密码取消
   */
  onclickresetPasswordCancel = () => {
    runInAction(() => {
      this.resetPasswordModelShow = false
    })
  }

  /** 
   * 重置密码form表单更新
   */
  resetPasswordValueOnChange = (value: any) => {
    this.resetPasswordMode = value;
  }

  /**
* 发送修改密码邮箱验证码
*/
  sendresetPasswordEmailcode = () => {
    this.loading();
    let email = this.userInfo.email;
    sendEmailcode(email).then((result) => {
      if (result.data.success) {
        runInAction(() => {
          this.emailCodeDisable = false
        })
        Toast.success("发送成功,请尽快填写。")
        setTimeout(() => {
          this.sendEmailButtonDisable = false
        }, 60000);
      }
      this.loading();
    })
  }

  /**
   * 根据token自动登录
   */
  autoLoginBytoken = () => {
    let token = this.getToken();
    if (!token){
      token = cookie.load("token");
    }
    if (token) {
      this.getUserInfoRequst();
    }
  }


  /**
   * 保存tokon
   * @param token 
   */
  saveToken = (token: string) => {
    cookie.save('token', token, { expires: this.inFifteenMinutes })
    localStorage.setItem("token", token);
  }


  /**
   * 获取用户信息
   */
  getUserInfoRequst = () => {
    if (localStorage.getItem("token")) {
      getUserInfo().then((result) => {
        if (result.data.success) {
          const user = result.data.data;
          localStorage.setItem("userInfo", JSON.stringify(user));
          localStorage.setItem("loginFlag",'true');
          runInAction(() => {
            this.userInfo = user;
            this.loginFlag = true;
          })
          Toast.success("加载用户信息成功")
          return this.userInfo;
        }
        return null;
      })
    }else{
      return null;
    }
  }


  getToken = () => {
    const search = window.location.search;
    const param = new URLSearchParams(search);
    const token = param.get('token');
    console.log(token)
    if(token){
      this.saveToken(token)
      return token;
    }
    if(localStorage.getItem("token")){

    }
    return null;
  }



  cleanAuth = () => {
    cookie.remove('secret');
    cookie.remove('token');
    localStorage.clear()
    sessionStorage.clear()
    runInAction(() => {
      this.loginFlag = false;
    })
  }


  userMenuTreeRequest = () => {
    const userMenuTree = sessionStorage.getItem('userMenuTree');
    if(userMenuTree){
      runInAction(() => {
        this.userMenuTree = JSON.parse(userMenuTree)
      })
      return
    }
    userMenu().then((result) => {
      if (result.data.success) {
        runInAction(() => {
          this.userMenuTree = result.data.data
          sessionStorage.setItem('userMenuTree',JSON.stringify(result.data.data))
        })

      }
    })

  }


}




export default new OauthStore();