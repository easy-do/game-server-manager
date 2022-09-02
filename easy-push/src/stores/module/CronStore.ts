import { makeAutoObservable, runInAction } from 'mobx'

class CronStore {
  constructor() {
    makeAutoObservable(this)
  }

  cronDataShow = false

  cronValue = "0 0 0 1/1 * ? *"

  show = () => {
    runInAction(() => {
      this.cronDataShow = true
    })
  }

  /**点击取消按钮 */
  cronDataCancel = () => {
    runInAction(() => {
      this.cronDataShow = false
    })
  }

  /**点击确认按钮 */
  cronDataOk = () => {
    this.cronDataShow = false
  }

  setValue = (formApi: any, field: string, value: string) => {
    formApi.setValue(field, value)
    runInAction(() => {
      this.cronValue = value
      if (value) {
        this.cronDataShow = false
      }
    })
  }
}




export default new CronStore();