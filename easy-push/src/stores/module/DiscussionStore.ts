import { Toast } from '@douyinfe/semi-ui';
import { makeAutoObservable, runInAction } from 'mobx'
import { page, managerPage, info, remove, edit, add } from '../../api/discussion';


class DiscussionStore {

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
    orders: [],
    params: {}
  }

  dataList = new Array<object>();

  loadingData = false

  addDataShow = false

  editDataShow = false

  hasMore = false

  showLoadMore = false

  formApi = {}

  addDiscussionShow = false

  markdownTitle = ""

  markdownContent = ""

  savePopconfirmShow = false

  infoModelshow = false

  commentMarkdown = ''

  discussionId = 0

  myDiscussionShow = false

  auditId = 0

  auditshow = false

  loading = () => {
    runInAction(() => {
      this.loadingData = !this.loadingData;
    })
  }


  /**
* 分页请求
*/
  pageRequest = (currentPage: number) => {
    this.loading()
    let param = this.pageParam;
    param.currentPage = currentPage
    page(param).then((result) => {
      if (result.data.success) {
        let oldData: any[] = []
        if (this.currentPage === 1) {
          oldData = result.data.data;
        } else {
          oldData = this.dataList;
          result.data.data.map((item: any) => {
            oldData.push(item)
          })
        }
        runInAction(() => {
          this.dataList = oldData;
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

  /** 改变页码 */
  onPageChange = (currentPage: number) => {
    runInAction(() => {
      this.pageParam = { ...this.pageParam, currentPage }
      this.managerPage()
    })
  }

  /** 改变每页条数 */
  onPageSizeChange = (pageSize: number) => {
    runInAction(() => {
      this.pageParam = { ...this.pageParam, pageSize }
      this.managerPage()
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

  /** 添加请求 */
  addDataRequest = (param: object) => {
    this.loading()
    add(param).then((result) => {
      if (result.data.success) {
        Toast.success("保存成功。")
        runInAction(() => {
          this.savePopconfirmShow = false;
          this.addDiscussionShow = false;
          this.currentPage = 1;
        })
        this.pageRequest(1)
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
          this.editDataShow = false
          this.managerPage();
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
        this.managerPage();
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
  onClickEditButton = (id: number, event: any) => {
    this.dataInfoRequest(id);
  }

  /**点击编辑确认按钮 */
  editDataOk = () => {
    this.editDataRequest(this.dataInfo)
  }

  /**点击编辑取消按钮 */
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
    })
  }

  /**点击删除按钮 */
  onClickDeleteButton = (id: object) => {
    this.deleteDataRequest(id)
  }

  /**点击发起讨论按钮 */
  addDiscussionButton = () => {
    this.addDiscussionShow = true;
  }

  /**markdown标题变化 */
  onChangeMarkdownTitle = (value: string) => {
    runInAction(() => {
      this.markdownTitle = value;
    })
  }

  /**markdown内容变化 */
  onChangeMarkdownContent = (value: string) => {
    runInAction(() => {
      this.markdownContent = value;
    })
  }

  /**点击编辑讨论弹窗的保存按钮呼出确认提示 */
  addDiscussionOk = () => {
    this.savePopconfirmShow = true;
  }

  /**点击编辑讨论弹窗的取消按钮 */
  addDiscussionCancel = () => {
    runInAction(() => {
      this.addDiscussionShow = false;
      this.savePopconfirmShow = false;
    })
  }

  /**点击提交确认弹窗是按钮 */
  savePopconfirmOK = () => {
    if (this.markdownTitle === undefined || this.markdownTitle === '') {
      Toast.warning("标题不能为空");
      return;
    }
    if (this.markdownContent === undefined || this.markdownContent === '') {
      Toast.warning("正文不能为空");
      return;
    }
    let param = {
      'title': this.markdownTitle,
      'content': this.markdownContent
    }
    this.addDataRequest(param);
  }

  /**点击提交确认弹窗否按钮,隐藏确认提示 */
  savePopconfirmCancel = () => {
    runInAction(() => {
      this.savePopconfirmShow = false;
    })
  }

  /**点击详情按钮 */
  showInfoButton = (id: number) => {
    runInAction(() => {
      this.discussionId = id;
      this.infoModelshow = true;
    })
  }

  /**关闭详情页 */
  infoModalCancel = () => {
    runInAction(() => {
      // this.markdownTitle = '';
      // this.markdownContent = '';
      // this.commentMarkdown = '';
      this.infoModelshow = false;
    })
  }

  /**获取话题详情 */
  getDiscussionDtails = (id: number) => {
    runInAction(() => {
      this.dataInfo = {};
      this.dataInfoRequest(id);
    })
  }

  /**管理我的话题弹窗打开 */
  myDiscussionButton = () => {
    runInAction(() => {
      this.myDiscussionShow = true;
    })
  }

  /**管理我的话题弹窗关闭 */
  myDiscussionCancel = () => {
    runInAction(() => {
      this.myDiscussionShow = false;
    })
  }

  /**评论输入框参数变化 */
  commentValueChange = (value: string) => {
    runInAction(() => {
      this.commentMarkdown = value;
    })
  }

      /** 审核按钮 */
      auditButton = (id: number) => {
        runInAction(() => {
          this.auditId = id;
          this.auditshow = true;
        })
      }

}




export default new DiscussionStore();


