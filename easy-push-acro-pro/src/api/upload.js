
import { get, postRequestBody, postFormData } from "../utils/request"

export const page = (param) => postRequestBody("/server/fileStore/page", param);
export const info = (id) => get("/server/fileStore/info/" + id);
export const removeRequest = (id) => get("/server/fileStore/remove/" + id);
export const upload = (param) => postFormData("/server/fileStore/upload", param);
export const downloadFile = (id) => get("/server/fileStore/dl/" + id);
export const getBase64 = (id) => get("/server/fileStore/base64/" + id);
export const uploadAddress = process.env.PUBLIC_URL + '/api//server/fileStore/upload'
export const dlAddress = process.env.PUBLIC_URL + '/api/server/fileStore/dl/'