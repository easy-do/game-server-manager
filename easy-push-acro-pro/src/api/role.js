import { get, postRequestBody } from "../utils/request"


export const managerPage = (param) => postRequestBody("/uc/role/page",param);
export const infoRequest = (id) => get("/uc/role/info/" + id);
export const changeStatus = (param) => postRequestBody("/uc/role/changeStatus",param);
export const addRequest = (param) => postRequestBody("/uc/role/add",param);
export const updateRequest = (param) => postRequestBody("/uc/role/update",param);
export const removeRequest = (id) => get("/uc/role/remove/" + id);
export const selectList = () => get("/uc/role/list");