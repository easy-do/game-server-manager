import { get,postRequestBody} from "../utils/request"

export const list = () => get("/uc/dictType/list");
export const infoRequest = (id) => get("/uc/dictType/info/" + id);
export const removeRequest = (id) => get("/uc/dictType/remove/" + id);
export const managerPage = (param) => postRequestBody("/uc/dictType/page",param);
export const addRequest = (param) => postRequestBody("/uc/dictType/add",param);
export const updateRequest = (param) => postRequestBody("/uc/dictType/update",param);
export const changeStatus = (param) => postRequestBody("/uc/dictType/changeStatus",param);
