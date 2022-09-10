import { get, postRequestBody } from "../utils/request"


export const managerPage = (param) => postRequestBody("/uc/userManager/page",param);
export const info = (id) => get("/uc/userManager/info/" + id);
export const changeStatus = (param) => postRequestBody("/uc/userManager/changeStatus",param);
export const remove = (id) => get("/uc/userManager/delete/" + id);
export const authRole = (param) => postRequestBody("/uc/userManager/authRole",param);
export const getAuthRoles = (id) => get("/uc/userManager/authRole/"+id);
