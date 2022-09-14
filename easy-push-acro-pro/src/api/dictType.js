import { get,postRequestBody} from "../utils/request"

export const list = () => get("/uc/dictType/list");
export const infoRequest = (id) => get("/uc/dictType/info/" + id);
export const remove = (id) => get("/uc/dictType/delete/" + id);
export const managerPage = (param) => postRequestBody("/uc/dictType/page",param);
export const add = (param) => postRequestBody("/uc/dictType/add",param);
export const edit = (param) => postRequestBody("/uc/dictType/edit",param);
export const changeStatus = (param) => postRequestBody("/uc/dictType/changeStatus",param);
