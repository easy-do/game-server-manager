import { Toast } from '@douyinfe/semi-ui'
import { makeAutoObservable, runInAction } from 'mobx'
import { appEnvListByScriptId } from '../../api/appEnv';
import { add, deploy, edit, info, list, page, remove } from '../../api/application';
import { listByAppId } from '../../api/appScript';


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

  deployAppShow = false

  deployLogShow = false

  scriptList: any = []

  deployAppId = ''

  appScriptId = 0

  deployEnvs: any[] = [];

  deployEnvData: any = {}

  loadingScriptFlag = false

  deployFormApi: any = {}

  currentApplicationId: string = ''

  dataInfoShow = false

  deviceType = 0


  loading = () => {
    runInAction(() => {
      this.loadingData = !this.loadingData;
    })
  }


  /**所有列表 */
  listRequst = () => {
    list().then((result) => {
      if (result.data.success) {
        runInAction(() => {
          this.dataList = result.data.data;
        })
      }
    })
  }


  /**获取详情 */
  dataInfoRequest = (id: string) => {
    this.loading();
    info(id).then((result) => {
      if (result.data.success) {
        runInAction(() => {
          this.dataInfo = result.data.data;
          this.dataInfoShow = true
        })
      }
      this.loading();
    })
  }

    /**获取详情 */
    editInfoRequest = (id: string) => {
      this.loading();
      info(id).then((result) => {
        if (result.data.success) {
          runInAction(() => {
            this.dataInfo = result.data.data;
            this.editDataShow = true
          })
        }
        this.loading();
      })
    }


  /**
   * 分页请求
   */
  pageRequest = () => {
    this.loading();
    page(this.pageParam).then((result) => {
      if (result.data.success) {
        runInAction(() => {
          this.dataList = result.data.data;
          this.total = result.data.total;
          this.currentPage = result.data.currentPage;
          this.pageSize = result.data.pageSize;
        })
      }
      this.loading();
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
    this.loading();
    add(param).then((result) => {
      if (result.data.success) {
        Toast.success("添加成功")
        runInAction(() => {
          this.pageRequest()
          this.addDataShow = false
        })
      }
      this.loading();
    })
  }


  /** 修改请求 */
  editDataRequest = (param: object) => {
    this.loading();
    edit(param).then((result) => {
      if (result.data.success) {
        Toast.success("修改成功")
        runInAction(() => {
          this.pageRequest()
          this.editDataShow = false
        })
      }
      this.loading();
    })
  }


  /**删除请求 */
  deleteDataRequest = (id: object) => {
    this.loading();
    remove(id).then((result) => {
      if (result.data.success) {
        Toast.success("删除成功")
        this.pageRequest();
      }
      this.loading();
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
  onClickEditButton = (id: string, event: any) => {
    this.editInfoRequest(id)
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


  // 点击部署按钮
  onClickDeployButton = (applicationId: string, appId: number) => {
    this.loading();
    this.deployEnvs = []
    listByAppId(appId).then((result) => {
      if (result.data.success) {
        Toast.success("加载脚本列表")
        runInAction(() => {
          this.scriptList = result.data.data;
          this.deployAppId = applicationId;
          this.deployAppShow = true;
        })
      }
      this.loading();
    })
  }

  //点击脚本选择框根据scriptId获取环境变量
  getScriptEnvList = (scriptId: number) => {
    this.loading();
    appEnvListByScriptId(scriptId).then((result) => {
      if (result.data.success) {
        Toast.success("加载参数设置")
        runInAction(() => {
          this.deployEnvs = result.data.data;
        })
      }
      this.loading();
    })
  }

  // 确认部署按钮
  deployAppOk = () => {
    let param = {
      applicationId: this.deployAppId,
      env: this.deployFormApi.getValues(),
      appScriptId: this.deployFormApi.getValue('appScriptId')
    }
    deploy(param).then((result) => {
      if (result.data.success) {
        Toast.success("成功提交部署请求")
        runInAction(() => {
          this.pageRequest()
          this.deployAppShow = false
        })
      }
    })

  }

  deployAppCancel = () => {
    runInAction(() => {
      this.deployAppShow = false
      this.deployAppId = ''
    })
  }

  getDeployFormApi = (api: any) => {
    this.deployFormApi = api;
  }

  /** 部署form表单参数值改变 */
  deployOnValueChange = (values: any) => {
    runInAction(() => {
      this.deployEnvData = values
    })
  }

  deployLogButton = async (id: string) => {
    this.loading()
    runInAction(async () => {
      this.currentApplicationId = id;
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




    /**详情按钮 */
    dataInfoButton = (id:string) => {
      runInAction(() => {
        this.dataInfoShow = true;
      })
      this.dataInfoRequest(id);
    }
  
    /**隐藏详情 */
    dataInfoHidden = () => {
      runInAction(() => {
        this.dataInfo = {}
        this.dataInfoShow = false;
      })
      this.pageRequest();
    }


    /**设备类型变更 */
    changeDeviceType = (type:number) =>{
      runInAction(() => {
        this.deviceType = type
      })
    }


}




export default new ApplicationStore();

