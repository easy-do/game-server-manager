import { Toast } from '@douyinfe/semi-ui';
import { makeAutoObservable, runInAction } from 'mobx'
import { page, managerPage, remove } from '../../api/commentDetails';


class CommentDetailsStore {

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
    order: {},
    params: {}
  }

  dataList = new Array<object>();

  loadingData = false

  editDataShow = false

  formApi = {}

  loading = () => {
    runInAction(() => {
      this.loadingData = !this.loadingData;
    })
  }



  /**
* 分页请求
*/
  pageRequest = () => {
    this.loading()
    let param = this.pageParam;
    page(param).then((result) => {
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
   * 分页请求
   */
  managerPage = () => {
    this.loading()
    let param = this.pageParam;
    managerPage(param).then((result) => {
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


  /**点击删除按钮 */
  onClickDeleteButton = (id: object) => {
    this.deleteDataRequest(id)
  }

}

export default new CommentDetailsStore();



