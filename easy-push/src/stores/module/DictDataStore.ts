import { Toast } from '@douyinfe/semi-ui'
import { makeAutoObservable, runInAction } from 'mobx'
import { add, changeStatus, edit, info, list, listByCode, page, remove } from '../../api/dictData';



class DictDataStore {
  constructor() {
    makeAutoObservable(this)
  }

  dataInfo: any = {}

  currentPage = 1

  pageSize = 10

  total = 0

  dictTypeId = 0

  searchParam = {
    dictTypeId: this.dictTypeId
  }

  pageParam = {
    currentPage: this.currentPage,
    pageSize: this.pageSize,
    order: [
      { column: 'dictSort', asc: true }
    ],
    columns: ['id','dictLabel','dictKey','dictValue','dictValueType','dictSort','isDefault','status','remark'],
    params: {
      dictTypeId: this.dictTypeId
    }
  }

  dataList = new Array<object>();

  loadingData = false

  dataInfoShow = false

  addDataShow = false

  editDataShow = false

  searchFormapi: any = {}

  
  deletecConfirmShow = false
  
  deleteId = 0
  
  dictDataList = []
  




  loading = () => {
    runInAction(() => {
      this.loadingData = !this.loadingData;
    })
  }

  /**
   * 分页请求
   */
  pageRequest = (dictTypeId:number) => {
    this.loading()
    let param = this.pageParam
    param.params.dictTypeId = dictTypeId
    page(param).then((result) => {
      if (result.data.success) {
        runInAction(() => {
          this.dictTypeId = dictTypeId;
          this.dataList = result.data.data;
          this.total = result.data.total;
          this.currentPage = result.data.currentPage;
          this.pageSize = result.data.pageSize;
        })

        this.loading()
      }
    })

  }

  /**
  * 搜索请求
  */
  searchRequest = (params: any) => {
    this.loading()
    page(params).then((result) => {
      if (result.data.success) {
        runInAction(() => {
          this.dataList = result.data.data;
          this.total = result.data.total;
          this.currentPage = result.data.currentPage;
          this.pageSize = result.data.pageSize;
        })
        this.loading()
      }
    })

  }


  /** 改变页码 */
  onPageChange = (currentPage: number) => {
    runInAction(() => {
      this.pageParam = { ...this.pageParam, currentPage }
      this.pageRequest(this.dictTypeId)
    })
  }

  /** 改变每页条数 */
  onPageSizeChange = (pageSize: number) => {
    runInAction(() => {
      this.pageParam = { ...this.pageParam, pageSize }
      this.pageRequest(this.dictTypeId)
    })
  }

  /**获取详情 */
  dataInfoRequest = (param:any) => {
    this.loading()
    info(param).then((result) => {
      if (result.data.success) {
        runInAction(() => {
          this.dataInfo = result.data.data;
        })
      }
      this.loading()
    })

  }

    /**获取详情 */
    editDataInfoRequest = (param:any) => {
      this.loading()
      info(param).then((result) => {
        if (result.data.success) {
          runInAction(() => {
            this.dataInfo = result.data.data;
            this.editDataShow = true
          })
        }
        this.loading()
      })
  
    }

/**编辑请求 */
  editDataRequest = (param:any) => {
    this.loading()
    edit(param).then((result) => {
      if (result.data.success) {
        runInAction(() => {
          this.dataInfo = {}
          this.editDataShow = false
        })
        this.pageRequest(this.dictTypeId)
      }
      this.loading()
    })

  }

  /**删除请求 */
  deleteDataRequest = (id: number) => {
    this.loading()
    remove(id).then((result) => {
      if (result.data.success) {
        Toast.success("删除成功")
        this.pageRequest(this.dictTypeId);
      }
      this.loading()
    })
  }

  /** 修改状态 */
  changeStatusRequest = (id: number,status:number) => {
    this.loading()
    let param = {
      id:id,
      status:status,
    }
    changeStatus(param).then((result) => {
      if (result.data.success) {
        Toast.success("操作成功")
        runInAction(() => {
          this.pageRequest(this.dictTypeId)
          this.editDataShow = false
        })
      }
      this.loading()
    })

  }

    /** 添加请求 */
    addDataRequest = (param: object) => {
      this.loading()
      add(param).then((result) => {
        if (result.data.success) {
          Toast.success("添加成功")
          runInAction(() => {
            this.pageRequest(this.dictTypeId)
            this.addDataShow = false
          })
        }
        this.loading()
      })
    }
  

    /** 修改信息 */
    editRequest = (id: number) => {
      this.loading()
      edit(this.dataInfo).then((result) => {
        if (result.data.success) {
          Toast.success("操作成功")
          runInAction(() => {
            this.pageRequest(this.dictTypeId)
            this.editDataShow = false
          })
        }
        this.loading()
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
    let searchParam = this.searchParam
    searchParam.dictTypeId = this.dictTypeId
    param.params = searchParam
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
    this.pageRequest(this.dictTypeId);
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
      let data = this.dataInfo
      data.dictTypeId = this.dictTypeId
      this.addDataRequest(data)
    }
  
    /**点击编辑按钮 */
    onClickEditButton = (id: number) => {
      this.editDataInfoRequest(id)
    }
  
    /**点击更新确认按钮 */
    editDataOk = () => {
      this.editDataRequest(this.dataInfo)
    }
  
    /**点击更新取消按钮 */
    editDataCancel = () => {
      runInAction(() => {
        this.dataInfo = {}
        this.editDataShow = false
      })
    }
    
}




export default new DictDataStore();

