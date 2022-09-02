import { Toast } from '@douyinfe/semi-ui'
import { makeAutoObservable, runInAction } from 'mobx'
import { info, listByJobId, page, remove, clean, logResultById } from '../../api/jobLog';


class JobLogStore {
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
    order: {}
  }

  dataList = new Array<object>();

  loadingData = false

  addDataShow = false

  editDataShow = false

  logResults = []

  loading = () => {
    runInAction(() => {
      this.loadingData = !this.loadingData;
    })
  }

  /**根据jobid获取列表 */
  listRequst = (id: number) => {
    this.loading()
    listByJobId(id).then((result) => {
      if (result.data.success) {
        runInAction(() => {
          this.dataList = result.data.data;
        })
      }
      this.loading()
    })

  }


  /**获取详情 */
  dataInfoRequest = (id: object) => {
    this.loading()
    info(id).then((result) => {
      if (result.data.success) {
        runInAction(() => {
          this.dataInfo = result.data.data;
          this.editDataShow = true
        })
      }
      this.loading()
    })

  }


  /**获取日志返回结果 */
  logResultById = (id: object) => {
    this.loading()
    logResultById(id).then((result) => {
      if (result.data.success) {
        runInAction(() => {
          this.logResults = result.data.data;
          this.editDataShow = true
        })
      }
      this.loading()
    })

  }

  /**
   * 分页请求
   */
  pageRequest = () => {
    this.loading()
    page(this.pageParam).then((result) => {
      if (result.data.success) {
        runInAction(() => {
          this.dataList = result.data.data;
          this.total = result.data.total;
          this.currentPage = result.data.currentPage;
          this.pageSize = result.data.pageSize;
        })
      }
      this.loading()
    })

  }

  /** 改变页码 */
  onPageChange = (currentPage: number) => {
    runInAction(() => {
      this.pageParam = { ...this.pageParam, currentPage }
      this.pageRequest()
    })
  }

  /** 改变每页条数 */
  onPageSizeChange = (pageSize: number) => {
    runInAction(() => {
      this.pageParam = { ...this.pageParam, pageSize }
      this.pageRequest()
    })
  }




  /**删除请求 */
  deleteDataRequest = (id: object) => {
    this.loading()
    remove(id).then((result) => {
      if (result.data.success) {
        Toast.success("删除成功")
        this.pageRequest();
      }
      this.loading()
    })

  }


  /**清空请求 */
  cleanDataRequest = () => {
    this.loading()
    clean().then((result) => {
      if (result.data.success) {
        Toast.success("清空成功")
        this.pageRequest();
      }
      this.loading()
    })

  }


  /**点击日志详情按钮 */
  onClickLogInfoButton = (id: object, event: any) => {
    this.logResultById(id)
  }


  /**点击更新取消按钮 */
  editDataCancel = () => {
    runInAction(() => {
      this.editDataShow = false
    })
  }

  /**获取form表单api */
  getFormApi = (api: any) => {
    api.setValues(this.dataInfo, { "isOverride": true })
  }

  /** form表单参数值改变 */
  onValueChange = (dataInfo: any) => {
    runInAction(() => {
      this.dataInfo = dataInfo
    })
  }

  /**点击删除按钮 */
  onClickDeleteButton = (id: object) => {
    this.deleteDataRequest(id)
  }

  /**点击清空按钮 */
  onClickClanButton = () => {
    this.cleanDataRequest()
  }

}




export default new JobLogStore();


