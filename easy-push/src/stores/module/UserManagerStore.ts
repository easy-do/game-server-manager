import { Toast } from "@douyinfe/semi-ui";
import { makeAutoObservable, runInAction } from "mobx";
import { managerPage, changeStatus, info, remove, authRole, getAuthRoles } from "../../api/userManager";
import { userPointsOperation } from "../../api/userPoints";



class UserManagerStore {
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
    columns: ['id','nickName', 'platform', 'state', 'authorization', 'loginIp', 'lastLoginTime', 'createTime'],
    params: {}
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

  editRoleShow = false

  editRoleUserId = 0

  userRolers = []

  userRolersIds:number[] = []

  showPointsModal = false
  
  showPointsUserid = 0

  userPointsOperationIds:number[] = []

  userPointsOperationShow = false

  userPointsOperationsShow = false

  selectUserList:any[] = []

  userPointsOperationFormValues = {
    userId:[0],
    points:10,
    description: '无'
  }

  userPointsOperationsFormApi:any = {}

  loading = () => {
    runInAction(() => {
      this.loadingData = !this.loadingData;
    })
  }


  /**获取详情 */
  dataInfoRequest = (id: number) => {
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

  /** 修改用户状态 */
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


    /**获取用户已经授权的角色 */
    getAuthRolesRequest = (id:number) => {
      this.loading()
      getAuthRoles(id).then((result) => {
        if (result.data.success) {
          let roles = result.data.data
          let roleIds = new Array<number>();
          roles.map((item:any)=>{
            roleIds.push(item.roleId)
          })
          runInAction(() => {
            this.editRoleUserId = id
            this.editRoleShow = true
            this.userRolersIds = roleIds
            this.userRolers = roles
          })
        }
        this.loading()
      })
    }


  /**授权用户角色 */
  editUserRoleRequest = () => {
    this.loading()
    let param = {
      userId:this.editRoleUserId,
      roleIds:this.userRolersIds,
    }
    authRole(param).then((result) => {
      if (result.data.success) {
        Toast.success("操作成功")
        runInAction(() => {
          this.editRoleUserId = 0
          this.editRoleShow = false
          this.userRolersIds = []
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

  /**授权角色 */
  editRoleButton = (id:number) => {
    this.getAuthRolesRequest(id)
  }

  /**角色选择改变值 */
  setUserRole = (values:any) =>{
    runInAction(() => {
      this.userRolersIds = values
    })
  }

  /**确认授权角色 */
  editRoleOk = () =>{
    this.editUserRoleRequest()
  }

  /**取消授权角色 */
  editRoleCancel = () =>{
    runInAction(() => {
      this.editRoleUserId = 0
      this.editRoleShow = false
    })
    this.pageRequest()
  }

  /**查看积分记录按钮 */
  showPointsButton = (id:number) => {
    runInAction(() => {
      this.showPointsUserid = id
      this.showPointsModal = true
    })
  }

  /**隐藏积分记录窗口 */
  showPointsCancel = () =>{
    runInAction(() => {
      this.showPointsModal = false
    })
  }

  /**积分操作按钮 */
  userPointsOperationButton = (id:number) =>{
    runInAction(() => {
      this.userPointsOperationShow = true
      this.userPointsOperationIds = [id]
    })
  }


  /**积分操作确认 */
  userPointsOperationOk= () =>{
    this.loading()
    let params = this.userPointsOperationFormValues
    params.userId = this.userPointsOperationIds;
    userPointsOperation(params).then((result) => {
      if (result.data.success) {
        runInAction(() => {
          this.userPointsOperationShow = false
        })
      }
      this.loading()
    })

  }

  /**积分操作取消 */
  userPointsOperationCancel= () =>{
    runInAction(() => {
      this.userPointsOperationShow = false
    })
  }

  /**积分操作form表单参数改变 */
  userPointsOperationFormOnChage= (valus:any) =>{
    runInAction(() => {
      this.userPointsOperationFormValues = valus
    })
  }



    /**积分批量操作按钮 */
    userPointsOperationsButton = () =>{
      runInAction(() => {
        this.userPointsOperationsShow = true
      })
    }


    getUserPointsOperationsFormApi = (api:any) =>{
      runInAction(() => {
        this.userPointsOperationsFormApi = api
      })
    }

    
  /**积分批量操作确认 */
  userPointsOperationsOk= () =>{
    this.loading()
    userPointsOperation(this.userPointsOperationsFormApi.getValue()).then((result) => {
      if (result.data.success) {
        runInAction(() => {
          this.userPointsOperationsShow = false
        })
      }
      this.loading()
    })

  }

  /**积分批量操作取消 */
  userPointsOperationsCancel= () =>{
    runInAction(() => {
      this.userPointsOperationsShow = false
    })
  }


  /**下拉框搜索 */
  userHandleSearch = (input:string) =>{
    this.loading()
    let pageParam = {
      currentPage: 1,
      pageSize: 20,
      columns: ['id','nickName','platform'],
      params: {}
    }
    if(input !== undefined && input !== ''){
      pageParam.params = {
        'nickName': input
      }
    }
    managerPage(pageParam).then((result) => {
      if (result.data.success) {
        let optionList: { label: any; value: any; key: any; }[] = []
        result.data.data.map((item:any)=>{
          optionList.push({
            label: item.nickName + '-' + item.id + '-' + item.platform,
            value: item.id,
            key: item.id
          })
        })

        runInAction(() => {
          this.selectUserList = optionList;
        })

      }
      this.loading()
    })

  
  }


}

export default new UserManagerStore();
