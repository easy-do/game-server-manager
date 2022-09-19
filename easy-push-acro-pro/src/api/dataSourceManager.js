import { get,postRequestBody} from "../utils/request"

export const list = (param) => postRequestBody("/gen/datasource/list",param);
export const infoRequest = (id) => get("/gen/datasource/info/" + id);
export const removeRequest = (id) => get("/gen/datasource/remove/" + id);
export const managerPage = (param) => postRequestBody("/gen/datasource/page",param);
export const addRequest = (param) => postRequestBody("/gen/datasource/saveOrUpdate",param);
export const updateRequest = (param) => postRequestBody("/gen/datasource/saveOrUpdate",param);