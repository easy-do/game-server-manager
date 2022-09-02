import { get, postRequestBody } from "../utils/request"


export const managerPage = (param) => postRequestBody("/uc/role/page",param);
export const info = (id) => get("/uc/role/info/" + id);
export const changeStatus = (param) => postRequestBody("/uc/role/changeStatus",param);
export const add = (param) => postRequestBody("/uc/role/add",param);
export const edit = (param) => postRequestBody("/uc/role/edit",param);
export const remove = (id) => get("/uc/role/delete/" + id);
export const selectList = () => get("/uc/role/list");