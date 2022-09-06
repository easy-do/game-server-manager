import { Toast } from "@douyinfe/semi-ui";
import { makeAutoObservable, runInAction } from "mobx";
import { count, page, readMessageRequest, readAllMessageRequest, cleanAllMessageRequest } from "../../api/userMessage";

class UserMessageStore {
    constructor() {
        makeAutoObservable(this);
    }

    messageCount = 0

    currentPage = 1

    pageSize = 10

    total = 0

    pageParam = {
        currentPage: this.currentPage,
        pageSize: this.pageSize,
        orders: []
    }

    dataList = new Array<object>();

    loadingData = false

    messageListShow = false;

    showLoadMore = false

    hasMore = false

    loadMore = true

    audio = new Audio('https://push.easydo.plus/audio/wechatMessage.mp3')

    loading = () => {
        runInAction(() => {
            this.loadingData = !this.loadingData;
        })
    }

    /**
     * 获取未读消息数量
     */
    countRequest = () => {
        count().then((result) => {
            if (result.data.success) {
                let count = result.data.data;
                if (count > 0) {
                    if (!sessionStorage.getItem('isPlayMessaudio')) {
                        this.audio.play()
                        Toast.success("您有" + count + "条未读消息。")
                        sessionStorage.setItem('isPlayMessaudio', 'true')
                    }
                } else {
                    sessionStorage.removeItem('isPlayMessaudio')
                }
                runInAction(() => {
                    this.messageCount = count
                    //每一分钟刷新一次未读消息数量
                    setTimeout(this.countRequest, 30000);
                })
            }
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

    /**
    * 标记消息已读
    */
    readMessage = (id: number) => {
        readMessageRequest(id).then((result) => {
            if (result.data.success) {
                this.pageRequest()
                this.countRequest()
            }
        })
    }

    /**
    * 清空所有消息
    */
    cleanAllMessage = () => {
        cleanAllMessageRequest().then((result) => {
            if (result.data.success) {
                this.dataList = [];
                this.countRequest()
                this.currentPage = 1
                this.messageListShow = false
            }
        })
    }

    /**
  * 标记所有消息已读
  */
    readAllMessage = () => {
        readAllMessageRequest().then((result) => {
            if (result.data.success) {
                this.pageRequest()
            }
        })
    }

    messageListShowButton = () => {
        this.pageRequest()
        runInAction(() => {
            this.messageListShow = true
        })
    }

    messageListShowCancel = () => {
        this.pageRequest()
        runInAction(() => {
            this.messageListShow = false
        })
    }


}

export default new UserMessageStore();
