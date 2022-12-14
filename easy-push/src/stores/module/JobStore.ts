import { Toast } from '@douyinfe/semi-ui'
import { makeAutoObservable, runInAction } from 'mobx'
import { appEnvListByScriptId } from '../../api/appEnv';
import { add, edit, info, page, remove, run } from '../../api/job';


class JobStore {
  constructor() {
    makeAutoObservable(this)
  }

  dataInfo: object = {
    env: {}
  }

  venData: object = {}

  currentPage = 1

  pageSize = 10

  total = 0

  pageParam = {
    currentPage: this.currentPage,
    pageSize: this.pageSize,
    orders: []
  }

  dataList = new Array<object>();

  loadingData = false

  addDataShow = false

  editDataShow = false

  formApi = {}

  deployEnvs: any[] = [];

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
          this.deployEnvs = []
          this.dataInfo = result.data.data;
          this.editDataShow = true
        })
      }
      this.loading()
      this.getScriptEnvList(result.data.data.appScriptId)
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


  /**运行请求 */
  runRequest = (id: object) => {
    this.loading()
    run(id).then((result) => {
      if (result.data.success) {
        Toast.success("提交成功")
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
      this.deployEnvs = []
    })
  }

  /**获取编辑form表单api */
  getAddFormApi = (api: any) => {
    this.formApi = api;
    api.setValues(this.dataInfo, { "isOverride": true })
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

  /**获取编辑form表单api */
  getFormApi = (api: any) => {
    this.formApi = api;
    api.setValues(this.dataInfo, { "isOverride": true })
  }



  /** form表单参数值改变 */
  onValueChange = (values: any) => {
    runInAction(() => {
      this.dataInfo = values
      this.venData = values
    })
  }

  /**点击删除按钮 */
  onClickDeleteButton = (id: object) => {
    this.deleteDataRequest(id)
  }

  /**点击运行按钮 */
  onRunButton = (id: object) => {
    this.runRequest(id)
  }

  //点击脚本选择框根据scriptId获取环境变量
  getScriptEnvList = (scriptId: number) => {
    appEnvListByScriptId(scriptId).then((result) => {
      if (result.data.success) {
        Toast.success("加载参数设置")
        runInAction(() => {
          this.deployEnvs = result.data.data;
        })
      }
    })
  }
}






export default new JobStore();

