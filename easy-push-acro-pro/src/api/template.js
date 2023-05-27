import { get,postRequestBody} from "../utils/request"

export const listRequest = (param) => get("/gen/template/list",param);
export const infoRequest = (id) => get("/gen/template/info/" + id);
export const removeRequest = (id) => get("/gen/template/remove/" + id);
export const managerPage = (param) => postRequestBody("/gen/template/page",param);
export const addRequest = (param) => postRequestBody("/gen/template/add",param);
export const updateRequest = (param) => postRequestBody("/gen/template/update",param);