import { Toast } from '@douyinfe/semi-ui'
import { makeAutoObservable, runInAction } from 'mobx'
import { add, edit, info, list, page, remove } from '../../api/appInfo';
import { dlAddress} from "../../api/upload";


class AppInfoStore {
  constructor() {
    makeAutoObservable(this)
  }

  dataInfo: any = {}

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

  auditId = 0
  
  auditshow = false
  

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
        let datainfo = result.data.data;
        let iconFiles: { status: string; preview: boolean; url: string; fileInstance: { uid: string; }; }[] = []
        let iconFileIds = result.data.data.icon.split(',')
        iconFileIds.forEach((fileId: string) => {
          iconFiles.push(
            {
              status: 'success',
              preview: true,
              url: dlAddress+fileId,
              fileInstance: {
                uid: fileId
              }
          }
          )
        });
        datainfo.icon = iconFiles
        let pictureFiles: { status: string; preview: boolean; url: string; fileInstance: { uid: string; }; }[] = []
        let pictureFileIds = result.data.data.picture.split(',')
        pictureFileIds.forEach((fileId: string) => {
          pictureFiles.push(
            {
              status: 'success',
              preview: true,
              url: dlAddress+fileId,
              fileInstance: {
                uid: fileId
              }
          }
          )
        });
        datainfo.picture = pictureFiles
        runInAction(() => {
          this.dataInfo = datainfo;
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
    let param = this.dataInfo
    let iconfiles = this.dataInfo.icon;
    let icons: any[] = []
    iconfiles.forEach((file: any) => {
      icons.push(file.fileInstance.uid)
    });
    param.icon = icons.toString()

    let picturefiles = this.dataInfo.picture;
    let pictures: any[] = []
    picturefiles.forEach((file: any) => {
      pictures.push(file.fileInstance.uid)
    });
    param.picture = pictures.toString()
    this.addDataRequest(param)
  }

  /**点击编辑按钮 */
  onClickEditButton = (id: object, event: any) => {
    this.dataInfoRequest(id)
  }

  /**点击更新确认按钮 */
  editDataOk = () => {
    let param = this.dataInfo
    let iconfiles = this.dataInfo.icon;
    let icons: any[] = []
    iconfiles.forEach((file: any) => {
      icons.push(file.fileInstance.uid)
    });
    param.icon = icons.toString()

    let picturefiles = this.dataInfo.picture;
    let pictures: any[] = []
    picturefiles.forEach((file: any) => {
      pictures.push(file.fileInstance.uid)
    });
    param.picture = pictures.toString()
    this.editDataRequest(param)
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

    /** 审核按钮 */
    auditButton = (id: number) => {
      runInAction(() => {
        this.auditId = id;
        this.auditshow = true;
      })
    }

}




export default new AppInfoStore();

