import { Toast } from '@douyinfe/semi-ui';
import { makeAutoObservable, runInAction } from 'mobx'
import { page, managerPage, info, add, remove } from "../../api/commentDetails";
class CommentStore {
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
        orders: [],
        params:[],
        discussionId : 0,
        type:0,
    }

    dataList = new Array<object>();

    loadingData = false

    commentMarkdown = ''

    saveCommentShow = false

    commentModalShow = false
    
    discussionId = 0

    type = 0

    commentParentId = 0

    commentId = 0


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
    pageRequest = (discussionId: number,type:number) => {
        this.loading()
        let pageParam = this.pageParam;
        pageParam.type = type;
        pageParam.discussionId = discussionId;
        page(pageParam).then((result) => {
            if (result.data.success) {
                runInAction(() => {
                    this.discussionId = discussionId;
                    this.type = type
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
    managePageRequest = () => {
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

    /** 改变页码 */
    onPageChange = (currentPage: number) => {
        runInAction(() => {
            this.pageParam = { ...this.pageParam, currentPage }
            this.pageRequest(this.discussionId, this.type)
        })
    }

    /** 改变每页条数 */
    onPageSizeChange = (pageSize: number) => {
        runInAction(() => {
            this.pageParam = { ...this.pageParam, pageSize }
            this.pageRequest(this.discussionId, this.type)
        })
    }


    /** 添加请求 */
    addDataRequest = (param: object) => {
        this.loading()
        add(param).then((result) => {
            if (result.data.success) {
                Toast.success("评论成功")
                runInAction(() => {
                    this.pageRequest(this.discussionId, this.type)
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
                this.pageRequest(this.discussionId, this.type);
            }
            this.loading()
        })
    }

    commentValueChange = (value: string) => {
        runInAction(() => {
            this.commentMarkdown = value
        })
    }
    commentModalButton = (discussionId:number,parentId:number,commentId:number) =>{
        runInAction(() => {
            this.commentModalShow = true;
            this.discussionId=discussionId;
            this.commentParentId = parentId;
            this.commentId = commentId;
        })
    }


    saveComment = () => {
        this.saveCommentShow = true;
    }


    commentModalCancel = () => {
        runInAction(() => {
            this.saveCommentShow = false;
            this.commentModalShow = false;
        })
    }

    /**点击提交确认弹窗是按钮 */
    saveCommentOK = (discussionId: number, type: number) => {
        if (this.commentMarkdown === undefined || this.commentMarkdown === '') {
            Toast.warning("评论不能为空");
            return;
        }
        let param = {
            'businessId': discussionId,
            'parentId':this.commentParentId,
            'commentId':this.commentId,
            'type': type,
            'content': this.commentMarkdown
        }
        this.addDataRequest(param);
        runInAction(() => {
            this.saveCommentShow = false;
            this.commentModalShow = false;
            this.commentMarkdown = '';
            this.commentParentId = 0;
            this.commentId = 0;
        })
    }

    /**点击提交确认弹窗否按钮,隐藏确认提示 */
    saveCommentCancel = () => {
        runInAction(() => {
            this.saveCommentShow = false;
            this.commentModalShow = false;
        })
    }


}




export default new CommentStore();