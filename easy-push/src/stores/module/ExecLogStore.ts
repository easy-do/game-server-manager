import { makeAutoObservable, runInAction } from 'mobx'
import { info, page } from '../../api/ExecLog';


class ExecLogStore {
  constructor() {
    makeAutoObservable(this)
  }

  dataInfo: object = {}

  currentPage = 1

  pageSize = 10

  total = 0

  pageParam = {
    currentPage: this.currentPage,
    pageSize: this.pageSize,
    order: {},
    params: [],
    applicationId: ''
  }

  dataList = new Array<object>();

  loadingData = false

  logResults: Array<string> = []

  logDataShow = false;

  applicationId = ''

  logWebSocket: any = {}

  sockerAddress = "wss://push.easydo.plus/wss/deployLog"

  loading = () => {
    runInAction(() => {
      this.loadingData = !this.loadingData;
    })
  }




  /**获取详情 */
  dataInfoRequest = (id: object) => {
    this.loading()
    info(id).then((result) => {
      if (result.data.success) {
        runInAction(() => {
          this.dataInfo = result.data.data;
        })
      }
      this.loading()
    })

  }

  /**
 * 分页请求
 */
  pageRequest = (applicationId: string) => {
    this.loading()
    let param = this.pageParam;
    param.applicationId = applicationId
    page(param).then((result) => {
      if (result.data.success) {
        runInAction(() => {
          this.dataList = result.data.data;
          this.total = result.data.total;
          this.currentPage = result.data.currentPage;
          this.pageSize = result.data.pageSize;
          this.applicationId = applicationId;
        })
      }
      this.loading()
    })

  }

  /** 改变页码 */
  onPageChange = (currentPage: number) => {
    runInAction(() => {
      this.pageParam = { ...this.pageParam, currentPage }
      this.pageRequest(this.applicationId)
    })
  }

  /** 改变每页条数 */
  onPageSizeChange = (pageSize: number) => {
    runInAction(() => {
      this.pageParam = { ...this.pageParam, pageSize }
      this.pageRequest(this.applicationId)
    })
  }

  /**点击日志详情按钮 */
  onClickLogInfoButton = (logId: number) => {
    let store = this;
    let applicationId = this.applicationId;
    let logWebSocket = new WebSocket(this.sockerAddress);
    logWebSocket.onopen = function () {
      console.log('开启websocket连接.')
      let messageParam = {
        "applicationId": applicationId,
        "logId": logId,
        "token": localStorage.getItem("token") ? localStorage.getItem("token") : ""
      }
      logWebSocket.send(JSON.stringify(messageParam));
    }
    logWebSocket.onerror = function () {
      console.log('websocket连接异常.')
    };
    logWebSocket.onclose = function (e: CloseEvent) {
      console.log('websocket 断开: ' + e.code + ' ' + e.reason + ' ' + e.wasClean)
    }
    logWebSocket.onmessage = function (event: any) {
      console.log('接收到服务端消息')
      let logResult = JSON.parse(event.data);
      let finish = logResult.isfinish;
      store.addLogResuts(finish, logResult.logs)
    }
    runInAction(() => {
      this.logWebSocket = logWebSocket;
      this.logDataShow = true
    })
  }

  /**
   * 设置日志返回结果
   * @param logResults 日志
   */
  addLogResuts = (finish: boolean, logResults: string[]) => {
    runInAction(() => {
      this.logResults = logResults;
    })
  }

  /**点击日志详情确认或取消按钮 */
  logDataCancel = () => {
    this.logWebSocket.close();
    this.pageRequest(this.applicationId)
    runInAction(() => {
      this.logDataShow = false
      this.dataList = []
    })
  }

}




export default new ExecLogStore();


