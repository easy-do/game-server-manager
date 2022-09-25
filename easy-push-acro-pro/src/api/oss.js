import { get, postFormData} from "../utils/request"


export const upload = (param) => postFormData("/oss/minio/upload", param);
export const remove = (url) => get("/oss/minio/remove/"+url);
export const uploads = (param) => postFormData("/oss/minio/uploads", param);

export const downloadPath = "/api/oss/minio/"
