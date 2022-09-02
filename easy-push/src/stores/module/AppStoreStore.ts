import { makeAutoObservable, runInAction } from 'mobx'
import { info, page } from '../../api/appStore';


class AppStoreStore {
  constructor() {
    makeAutoObservable(this)
  }

  dataInfo: object = {}

  currentPage = 1

  pageSize = 5

  total = 0

  pageParam = {
    currentPage: this.currentPage,
    pageSize: this.pageSize,
    order: {}
  }

  dataList = new Array<object>();

  loadingData = false

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

}




export default new AppStoreStore();

