import { get, postRequestBody } from "../utils/request"


export const managerPage = (param) => postRequestBody("/uc/userManager/page",param);
export const infoRequest = (id) => get("/uc/userManager/info/" + id);
export const changeStatusRequest = (param) => postRequestBody("/uc/userManager/changeStatus",param);
export const removeRequest = (id) => get("/uc/userManager/delete/" + id);
export const authRoleRequest = (param) => postRequestBody("/uc/userManager/authRole",param);
export const getAuthRolesRequest = (id) => get("/uc/userManager/authRole/"+id);
