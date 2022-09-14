import { get,postRequestBody} from "../utils/request"

export const list = (param) => postRequestBody("/gen/datasource/list",param);
export const infoRequest = (id) => get("/gen/datasource/info/" + id);
export const remove = (id) => get("/gen/datasource/delete/" + id);
export const managerPage = (param) => postRequestBody("/gen/datasource/page",param);
export const add = (param) => postRequestBody("/gen/datasource/saveOrUpdate",param);
export const edit = (param) => postRequestBody("/gen/datasource/saveOrUpdate",param);