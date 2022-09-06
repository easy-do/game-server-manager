import { Toast } from "@douyinfe/semi-ui";
import { makeAutoObservable, runInAction } from "mobx";
import { getBase64, info, page, upload , uploadAddress, dlAddress, remove} from "../../api/upload";


class UploadStore {
    constructor() {
        makeAutoObservable(this);
    }

    dlAddress = dlAddress

    uploadAddress = uploadAddress

    dataInfo: object = {}

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

    fileInfo = {}

    fileBase64 = ''

    fileId = ''

    uploadHeaders = {
        'secret': localStorage.getItem("secret") ? localStorage.getItem("secret") : "",
        "token": localStorage.getItem("token") ? localStorage.getItem("token") : "",
      }


    loading = () => {
        runInAction(() => {
            this.loadingData = !this.loadingData;
        })
    }

    setFileId = (fileId:string) =>{
        runInAction(() => {
            this.fileId = fileId
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

        /**删除 */
        delteRequest = (id: number) => {
            this.loading()
            remove(id).then((result) => {
                if (result.data.success) {
                    runInAction(() => {
                        Toast.success('删除成功')
                        this.pageRequest()
                    })
                }
                this.loading()
            })
    
        }

    /**
   * 上传请求
   */
    uploadRequest = (file: any) => {
        this.loading()
        let fileFormData = new FormData();
        fileFormData.append('file',file)
        upload(fileFormData).then((result) => {
            if (result.data.success) {
                Toast.success('上传成功')
                this.setFileId(result.data.data)
            }
            this.loading()
        })

    }

    /**
   * 获得图片base64
   */
    getBase64 = (id: number) => {
        this.loading()
        getBase64(id).then((result) => {
            if (result.data.success) {
                runInAction(() => {
                    this.fileBase64 = result.data.data;
                })
            }
            this.loading()
        })

    }

    onClickDeleteButton = (id:number) =>{
        this.delteRequest(id)
    }

}

export default new UploadStore();
