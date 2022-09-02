import { Toast } from '@douyinfe/semi-ui'
import { makeAutoObservable, runInAction } from 'mobx'
import { authorization, bingdingEmail, sendEmailcode, resetSecretRequest, logout } from '../../api/oauth'

class SettingStore {
  constructor() {
    makeAutoObservable(this)
  }


  loadingData = false

  authModelShow = false
  authorizationCode = ""

  emailModelShow = false
  emailCodeDisable = true
  sendEmailButtonDisable = true

  showPointsModal = false

  emailModel = {
    email: "",
    emailCode: ""
  }


  loading = () => {
    runInAction(() => {
      this.loadingData = !this.loadingData;
    })
  }

  //点击授权按钮
  onclickOauthButton = () => {
    runInAction(() => {
      this.authModelShow = true;
    })
  };

  //授权取消
  onclickOauthCancel = () => {
    runInAction(() => {
      this.authModelShow = false;
    })
  };

  //授权码输入框内容变化
  authorizationCodeOnChange = (value: string, event: object) => {
    runInAction(() => {
      this.authorizationCode = value;
    })
  };

  //授权确认
  onclickOauthOk = () => {
    this.authorizationRequest();
  };

  /**
* 发起授权请求
*/
  async authorizationRequest() {
    this.loading()
    let result = await authorization(this.authorizationCode);
    runInAction(() => {
      if (result.data.success) {
        Toast.success("授权成功")
        window.location.reload();
      }
      this.loading()
    })
  }


  //点击绑定邮箱按钮
  onclickBingEmailButton = () => {
    runInAction(() => {
      this.emailModelShow = true;
    })
  }

  //取消绑定邮箱
  onclickBingEmailCancel = () => {
    runInAction(() => {
      this.emailModelShow = false;
    })
  };

  //邮箱表单内容变化
  emailValueOnChange = (value: any) => {
    runInAction(() => {
      if (value) {
        this.emailModel = value;
      }
    })
  };

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


  //绑定确认
  onclickBingEmailOk = () => {
    this.bindingEmail();
  };


  /**
 * 发送邮箱验证码
 */
  sendEmailcode = () => {
    let email = this.emailModel.email;
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
    })
  }


  /**
* 发起绑定邮箱请求
*/
  bindingEmail = () => {
    this.loading()
    bingdingEmail(this.emailModel.email, this.emailModel.emailCode).then((result) => {
      if (result.data.success) {
        Toast.success("绑定成功")
        this.emailModelShow = false;
        window.location.reload();
      }
      this.loading()
    })
  }

  /** 重置密钥 */
  resetSecret = () =>{
    resetSecretRequest().then((result) => {
      if (result.data.success) {
        Toast.success("重置成功,请重新登录")
      }
      this.loading()
    })
  }

  showPointsLogButton = () =>{
    runInAction(() => {
      this.showPointsModal = true;
    })
  }

  showPointsCancel = () =>{
    runInAction(() => {
      this.showPointsModal = false;
    })
  }


}

export default new SettingStore();