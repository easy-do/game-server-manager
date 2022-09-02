import { Toast } from "@douyinfe/semi-ui";
import { makeAutoObservable, runInAction } from "mobx";
import { audit, commitAudit } from "../../api/audit";


class AuditStore {
  constructor() {
    makeAutoObservable(this);
  }

  loadingData = false

  auditType = 0

  auditId = 0

  auditshow = false

  auditParam: any = {
    points: 10,
    status: 2,
    description: '无'
  }


  callBack:() => void = ()=>{} 

  loading = () => {
    runInAction(() => {
      this.loadingData = !this.loadingData;
    })
  }

    /**审核请求 */
    auditRequest = () => {
      this.loading()
      let param = this.auditParam;
      param.id = this.auditId
      param.auditType = this.auditType
      audit(param).then((result) => {
        if (result.data.success) {
          Toast.success("审核成功")
          runInAction(() => {
            this.auditshow = false
          })
          this.callBack();
        }
        this.loading()
      })
    }

    initAuditEnv = (visible:boolean, auditId:any, auditType:number,callBack:() => void) => {
      runInAction(() => {
        this.auditshow = visible
        this.auditId = auditId
        this.auditType = auditType
        this.callBack = callBack
      })
    }

    /** 确认审核 */
    auditButtonOk = () => {
      this.auditRequest();
    }
  
    /** 取消审核 */
    auditButtonCancel = () => {
      runInAction(() => {
        this.auditshow = false;
      })
      this.callBack()
    }
  
    /** 审核表单发生变化 */
    auditshowFormOnChage = (values: any) => {
      runInAction(() => {
        this.auditParam = values;
      })
    }

      /**提交审核请求 */
  commitAuditRequest = (id:number,type:number,callBack: () => void) => {
    this.loading()
    commitAudit({id:id,auditType:type}).then((result) => {
      if (result.data.success) {
        Toast.success("提交成功")
        callBack();
      }
      this.loading()
    })
  }
  
}

export default new AuditStore();
