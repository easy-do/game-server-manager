import { Toast } from '@douyinfe/semi-ui'
import { makeAutoObservable, runInAction } from 'mobx'
import { add, getInstallCmd, info, list, managerPage, onlineInstall, remove } from '../../api/clientManager';


class ClientInfoStore {
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
    order: [
      { column: 'createTime', asc: false }
    ],
    columns: [],
    params: {}
  }

  dataList = new Array<object>();

  loadingData = false

  addDataShow = false

  dataInfoShow = false

  searchFormapi: any = {}

  searchParam = {}

  deletecConfirmShow = false

  deleteId = 0

  installShow = false

  installCmd = ''

  currentClientId = '0'

  deployLogShow = false

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
  dataInfoRequest = (id: number) => {
    this.loading()
    info(id).then((result) => {
      if (result.data.success) {
        runInAction(() => {
          this.dataInfo = result.data.data;
          this.dataInfoShow = true
        })
      }
      this.loading()
    })

  }



    /**获取详情 */
    installInofoRequest = (id: number) => {
      this.loading()
      this.getInstallCmdRequest(id)
      info(id).then((result) => {
        if (result.data.success) {
          runInAction(() => {
            this.dataInfo = result.data.data;
            this.installShow = true;
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
    managerPage(this.pageParam).then((result) => {
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

  /**
  * 搜索请求
  */
     searchRequest = (params: any) => {
      this.loading()
      managerPage(params).then((result) => {
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

      /**获取离线安装命令 */
      getInstallCmdRequest = (id: number) => {
        getInstallCmd(id).then((result) => {
          if (result.data.success) {
            runInAction(() => {
              this.installCmd = result.data.data;
            })
          }
        })
    
      }

    /**在线安装 */
    onlineInstallRequest = (id: number) => {
      this.loading()
      onlineInstall(id).then((result) => {
        if (result.data.success) {
          runInAction(() => {
            this.dataInfo = result.data.data;
            this.installShow = false
          })
          Toast.success("提交成功")
        }
        this.pageRequest()
        this.loading()
      })
  
    }

  
  /**删除请求 */
  deleteDataRequest = (id: number) => {
    this.loading()
    remove(id).then((result) => {
      if (result.data.success) {
        Toast.success(result.data.msg)
        this.pageRequest();
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

  /**获得搜索表单 */
  getSerchFormapi = (searchFormapi: any) => {
    runInAction(() => {
      this.searchFormapi = searchFormapi;
    })
  }

  /**搜索表单参数变化 */
  serchFormValueChange = (values: any) => {
    runInAction(() => {
      this.searchParam = values;
    })
  }

  /**点击搜索按钮 */
  searchButton = () => {
    let param = this.pageParam;
    param.params = this.searchParam
    this.searchRequest(param)
  }

  /**点击重置按钮 */
  cleanSearchform = () => {
    this.searchFormapi.setValues({})
    this.searchButton()
  }

  /**点击删除按钮 */
  onClickDeleteButton = (id: number) => {
    runInAction(() => {
      this.deleteId = id;
      this.deletecConfirmShow = true;
    })
  }

  /**删除确认 */
  deletecConfirmOK = () => {
    this.deleteDataRequest(this.deleteId);
    runInAction(() => {
      this.deletecConfirmShow = false;
    })
  }

  /**删除取消 */
  deletecConfirmCancel = () => {
    runInAction(() => {
      this.deleteId = 0;
      this.deletecConfirmShow = false;
    })
    this.pageRequest();
  }

  /**详情按钮 */
  dataInfoButton = (id:number) => {
    runInAction(() => {
      this.dataInfoShow = true;
    })
    this.dataInfoRequest(id);
  }

  /**隐藏详情 */
  dataInfoHiden = () => {
    runInAction(() => {
      this.dataInfo = {}
      this.dataInfoShow = false;
    })
    this.pageRequest();
  }

  /**点击安装按钮 */
  oninstallButton = (id:number) => {
    this.installInofoRequest(id)
  }

  /**在线安装 */
  installOk = (id:number) => {
    this.onlineInstallRequest(id)
  }

  /**安装取消*/
  installCancel = (id:number) => {
    runInAction(() => {
      this.dataInfo = {}
      this.installShow = false;
    })
  }


  deployLogButton = async (id: string) => {
    this.loading()
    runInAction(async () => {
      this.currentClientId = id;
      this.deployLogShow = !this.deployLogShow
    })
    this.loading()
  }

  deployLogButtonOkOrCe = () => {
    runInAction(async () => {
      this.deployLogShow = !this.deployLogShow
    })
    this.pageRequest();
  }


}




export default new ClientInfoStore();