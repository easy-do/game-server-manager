import { Toast } from "@douyinfe/semi-ui";
import { makeAutoObservable, runInAction } from "mobx";
import { list, changeStatus, info, remove, edit, add, treeSelect, userMenu } from "../../api/menuManager";



class MenuManagerStore {
  constructor() {
    makeAutoObservable(this);
  }


  dataInfo: object = {}

  pageParam = {
    params: []
  }

  dataList = new Array<object>();

  loadingData = false

  dataInfoShow = false

  addDataShow = false

  editDataShow = false

  searchFormapi: any = {}

  searchParam = {}

  deletecConfirmShow = false

  deleteId = 0

  treeSelectList = new Array<any>();

  userMenuTree:any = []


  loading = () => {
    runInAction(() => {
      this.loadingData = !this.loadingData;
    })
  }


  treeSelectRequest = () => {
    treeSelect().then((result) => {
      if (result.data.success) {
        runInAction(() => {
          let rootTree = {
            label: '根目录',
            value: 0,
            key: '0',
            children: result.data.data
          }
          this.treeSelectList = new Array(rootTree);
        })

      }
    })

  }



  /**
   * 列表请求
   */
  listRequest = () => {
    this.loading()
    list(this.pageParam).then((result) => {
      if (result.data.success) {
        runInAction(() => {
          this.dataList = result.data.data;
        })
        
      }
      this.loading()
      this.treeSelectRequest();
    })

  }

  /**
  * 搜索请求
  */
  searchRequest = (params: any) => {
    this.loading()
    list(params).then((result) => {
      if (result.data.success) {
        runInAction(() => {
          this.dataList = result.data.data;
        })
      }
      this.loading()
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
        this.listRequest()
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
        this.listRequest();
      }
      this.loading()
    })
  }

  /** 修改菜单状态 */
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
          this.listRequest()
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
            this.listRequest()
            this.addDataShow = false
          })
        }
        this.loading()
      })
    }
  

    /** 修改菜单信息 */
    editRequest = (id: number) => {
      this.loading()
      console.log(this.dataInfo)
      edit(this.dataInfo).then((result) => {
        if (result.data.success) {
          Toast.success("操作成功")
          runInAction(() => {
            this.listRequest()
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
    // param.params = this.searchParam
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
    this.listRequest();
  }

  dataInfoButton = (id:number) => {
    runInAction(() => {
      this.dataInfoShow = true;
    })
    this.dataInfoRequest(id);
  }

  dataInfoHiden = () => {
    runInAction(() => {
      this.dataInfo = {}
      this.dataInfoShow = false;
    })
    this.listRequest();
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

    
  userMenuTreeRequest = () => {
    const loginFlag = localStorage.getItem("loginFlag") ? true : false;
    let userMenuTree:any = [];
    if(loginFlag){
       userMenuTree = sessionStorage.getItem('userMenuTree');
    }else{
      userMenuTree = sessionStorage.getItem('anonymousMenuTree');
    }
    
    if(userMenuTree){
      runInAction(() => {
        this.userMenuTree = JSON.parse(userMenuTree)
      })
      return
    }
    userMenu().then((result) => {
      if (result.data.success) {
        runInAction(() => {
          this.userMenuTree = result.data.data
          if(loginFlag){
            sessionStorage.setItem('userMenuTree',JSON.stringify(result.data.data))
          }else{
            sessionStorage.setItem('anonymousMenuTree',JSON.stringify(result.data.data))
          }
        })

      }
    })

  }
  
}

export default new MenuManagerStore();
