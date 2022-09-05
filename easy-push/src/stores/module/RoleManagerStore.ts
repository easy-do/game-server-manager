import { Toast } from "@douyinfe/semi-ui";
import { makeAutoObservable, runInAction } from "mobx";
import { threadId } from "worker_threads";
import { authRoleMenu, roleMenuIds, roleTreeSelect } from "../../api/menuManager";
import { managerPage, changeStatus, info, remove, edit, add, selectList } from "../../api/roleManager";



class RoleManagerStore {
  constructor() {
    makeAutoObservable(this);
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
    columns: ['roleId','roleName', 'roleKey','status','createTime','updateTime','isDefault'],
    params: [],
    applicationId:0
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

  selectList = []

  authMenuShow = false

  authRoleId = 0

  roleAuthMenus = []

  roleAuthMenuIds:number[] = []

  loading = () => {
    runInAction(() => {
      this.loadingData = !this.loadingData;
    })
  }


    /**
   * 分页请求
   */
     selectListRequest = () => {
      this.loading()
      selectList().then((result) => {
        if (result.data.success) {
          runInAction(() => {
            this.selectList = result.data.data;
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
        this.pageRequest()
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
        this.pageRequest();
      }
      this.loading()
    })
  }

  /** 修改角色状态 */
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
          this.pageRequest()
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
            this.pageRequest()
            this.addDataShow = false
          })
        }
        this.loading()
      })
    }
  

    /** 修改角色信息 */
    editRequest = (id: number) => {
      this.loading()
      edit(this.dataInfo).then((result) => {
        if (result.data.success) {
          Toast.success("操作成功")
          runInAction(() => {
            this.pageRequest()
            this.editDataShow = false
          })
        }
        this.loading()
      })
  
    }

    /**获取角色授权菜单树 */
    roleTreeSelectRequest = (id: number) => {
      this.loading()
      roleTreeSelect(id).then((result) => {
        if (result.data.success) {
          runInAction(() => {
            this.roleAuthMenus = result.data.data
            this.authMenuShow = true
          })
        }
        this.loading()
      })
    }

        /**获取角色授权菜单树 */
        roleMenuIdsRequest = (id: number) => {
          this.loading()
          roleMenuIds(id).then((result) => {
            if (result.data.success) {
              runInAction(() => {
                this.roleAuthMenuIds = result.data.data
                this.authMenuShow = true
              })
            }
            this.loading()
          })
        }

    
    /**设置角色授权菜单列表 */
    authRoleMenuRequest = () => {
      this.loading()
      let param = {
        roleId: this.authRoleId,
        menuIds: this.roleAuthMenuIds
      }
      authRoleMenu(param).then((result) => {
        if (result.data.success) {
          Toast.success('授权成功')
          runInAction(() => {
            this.authRoleId = 0
            this.roleAuthMenuIds = []
            this.roleAuthMenus = []
            this.authMenuShow = false
          })
          this.pageRequest()
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
    this.pageRequest();
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
    this.pageRequest();
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

    onClickAuthMenuButton = (id:number) => {
      this.roleMenuIdsRequest(id)
      runInAction(() => {
        this.authRoleId = id
      })
    }
    setRoleAuthMenuIds = (values:any) => {
      console.log(values)
      runInAction(() => {
        this.roleAuthMenuIds = values
      })
    }
    authMenuOk = () => {
      this.authRoleMenuRequest()
    }
    authMenuCancel = () => {
      runInAction(() => {
        this.authMenuShow = false
      })
    }
  
}

export default new RoleManagerStore();
