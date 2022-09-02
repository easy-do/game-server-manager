import { Toast } from '@douyinfe/semi-ui'
import { makeAutoObservable, runInAction } from 'mobx'
import { add, edit, info, list, page, remove, testServer } from '../../api/serverInfo';


class ApplicationStore {
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

  loading = () => {
    runInAction(() => {
      this.loadingData = !this.loadingData;
    })
  }

  /**所有列表 */
  listRequst = () => {
    this.loading()
    list().then((result) => {
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


  /** 添加请求 */
  addDataRequest = (param: object) => {
    this.loading()
    add(param).then((result) => {
      if (result.data.success) {
        Toast.success("添加成功")
        runInAction(() => {
          this.pageRequest()
          this.addDataShow = false
        })
      }
      this.loading()
    })

  }

  

    /** 测试服务器请求 */
    testServerRequest = (param: object) => {
      this.loading()
      testServer(param).then((result) => {
        if (result.data.success) {
          Toast.success("测试成功")
        }
        this.loading()
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

  /** 修改请求 */
  editDataRequest = (param: object) => {
    this.loading()
    edit(param).then((result) => {
      if (result.data.success) {
        Toast.success("修改成功")
        runInAction(() => {
          this.pageRequest()
          this.editDataShow = false
        })
      }
      this.loading()
    })

  }

  /**点击添加按钮 */
  onClickAddButton = () => {
    runInAction(() => {
      this.dataInfo = {}
      this.addDataShow = true
    })
  }

  /**点击添加取消按钮 */
  addDataCancel = () => {
    runInAction(() => {
      this.addDataShow = false
    })
  }

  /**点击添加确认按钮 */
  addDataOk = () => {
    this.addDataRequest(this.dataInfo)
  }


  /**点击编辑按钮 */
  onClickEditButton = (id: object, event: any) => {
    this.dataInfoRequest(id)
  }

  /**点击更新确认按钮 */
  editDataOk = () => {
    this.editDataRequest(this.dataInfo)
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


    /**测试服务器连通性 */
    testServer = () => {
      this.testServerRequest(this.dataInfo)
    }
  


}




export default new ApplicationStore();