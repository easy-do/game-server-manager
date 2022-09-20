import { get,postRequestBody} from "../utils/request"

export const list = () => get("/uc/resource/list");
export const infoRequest = (id) => get("/uc/resource/info/" + id);
export const removeRequest = (id) => get("/uc/resource/remove/" + id);
export const managerPage = (param) => postRequestBody("/uc/resource/page",param);
export const addRequest = (param) => postRequestBody("/uc/resource/add",param);
export const updateRequest = (param) => postRequestBody("/uc/resource/update",param);



export const roleResource = (id) => get("/uc/resource/roleResource/"+id);
export const roleMenuIds = (id) => get("/uc/resource/roleResourceIds/"+id);
export const authRoleMenu = (param) => postRequestBody("/uc/resource/authRoleMenu",param);
export const userResource = () => get("/uc/resource/userResource");