import { get,postRequestBody} from "../utils/request"

export const list = (param) => get("/gen/dataSource/list",param);
export const infoRequest = (id) => get("/gen/dataSource/info/" + id);
export const removeRequest = (id) => get("/gen/dataSource/remove/" + id);
export const managerPage = (param) => postRequestBody("/gen/dataSource/page",param);
export const addRequest = (param) => postRequestBody("/gen/dataSource/saveOrUpdate",param);
export const updateRequest = (param) => postRequestBody("/gen/dataSource/saveOrUpdate",param);