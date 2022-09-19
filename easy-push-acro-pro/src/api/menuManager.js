import { get, postRequestBody } from "../utils/request"


export const list = (param) => postRequestBody("/uc/menu/list",param);
export const info = (id) => get("/uc/menu/info/" + id);
export const changeStatus = (param) => postRequestBody("/uc/menu/changeStatus",param);
export const addRequest = (param) => postRequestBody("/uc/menu/add",param);
export const updateRequest = (param) => postRequestBody("/uc/menu/update",param);
export const removeRequest = (id) => get("/uc/menu/remove/" + id);

export const treeSelect = () => get("/uc/menu/treeSelect");
export const roleTreeSelect = (id) => get("/uc/menu/roleTreeSelect/"+id);
export const roleMenuIds = (id) => get("/uc/menu/roleMenuIds/"+id);
export const authRoleMenu = (param) => postRequestBody("/uc/menu/authRoleMenu",param);
export const userMenu = () => get("/uc/menu/userMenu");
