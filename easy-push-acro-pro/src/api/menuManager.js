import { get, postRequestBody } from "../utils/request"


export const list = (param) => postRequestBody("/uc/menu/list",param);
export const info = (id) => get("/uc/menu/info/" + id);
export const changeStatus = (param) => postRequestBody("/uc/menu/changeStatus",param);
export const add = (param) => postRequestBody("/uc/menu/add",param);
export const edit = (param) => postRequestBody("/uc/menu/edit",param);
export const remove = (id) => get("/uc/menu/delete/" + id);

export const treeSelect = () => get("/uc/menu/treeSelect");
export const roleTreeSelect = (id) => get("/uc/menu/roleTreeSelect/"+id);
export const roleMenuIds = (id) => get("/uc/menu/roleMenuIds/"+id);
export const authRoleMenu = (param) => postRequestBody("/uc/menu/authRoleMenu",param);
export const userMenu = () => get("/uc/menu/userMenu");
