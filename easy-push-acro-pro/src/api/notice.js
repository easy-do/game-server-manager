import { get,postRequestBody} from "../utils/request"

export const list = () => get("/uc/notice/list");
export const infoRequest = (id) => get("/uc/notice/info/" + id);
export const removeRequest = (id) => get("/uc/notice/remove/" + id);
export const managerPage = (param) => postRequestBody("/uc/notice/page",param);
export const addRequest = (param) => postRequestBody("/uc/notice/add",param);
export const updateRequest = (param) => postRequestBody("/uc/notice/update",param);