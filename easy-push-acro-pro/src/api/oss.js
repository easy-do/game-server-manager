import { get, postFormData} from "../utils/request"


export const upload = (param) => postFormData("/server/minio/upload", param);
export const remove = (url) => get("/server/minio/remove/"+url);
export const uploads = (param) => postFormData("/server/minio/uploads", param);

export const downloadPath = "/api/server/minio/"
