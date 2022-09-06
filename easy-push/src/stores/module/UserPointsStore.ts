import { makeAutoObservable, runInAction } from 'mobx'
import { page } from '../../api/userPoints';


class UserPointsStore {
  constructor() {
    makeAutoObservable(this)
  }


  currentPage = 1

  pageSize = 10

  total = 0

  pageParam = {
    currentPage: this.currentPage,
    pageSize: this.pageSize,
    order: [{
      column:'createTime',
      asc:false

    }],
    params: {},
    userId:0
  }

  dataList = new Array<object>();

  loadingData = false

  userId = 0


  loading = () => {
    runInAction(() => {
      this.loadingData = !this.loadingData;
    })
  }



  /**
 * 分页请求
 */
  pageRequest = (userId: number) => {
    this.loading()
    let param = this.pageParam;
    param.userId = userId;
    page(param).then((result) => {
      if (result.data.success) {
        runInAction(() => {
          this.dataList = result.data.data;
          this.total = result.data.total;
          this.currentPage = result.data.currentPage;
          this.pageSize = result.data.pageSize;
          this.userId = userId;
        })
      }
      this.loading()
    })

  }

  /** 改变页码 */
  onPageChange = (currentPage: number) => {
    runInAction(() => {
      this.pageParam = { ...this.pageParam, currentPage }
      this.pageRequest(this.userId)
    })
  }

  /** 改变每页条数 */
  onPageSizeChange = (pageSize: number) => {
    runInAction(() => {
      this.pageParam = { ...this.pageParam, pageSize }
      this.pageRequest(this.userId)
    })
  }


}




export default new UserPointsStore();


