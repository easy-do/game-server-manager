import { get,postRequestBody} from "../utils/request"

export const list = () => get("/uc/resource/list");
export const infoRequest = (id) => get("/uc/resource/info/" + id);
export const removeRequest = (id) => get("/uc/resource/remove/" + id);
export const managerPage = (param) => postRequestBody("/uc/resource/page",param);
export const addRequest = (param) => postRequestBody("/uc/resource/add",param);
export const updateRequest = (param) => postRequestBody("/uc/resource/update",param);
export const resourceTreeList = (param) => postRequestBody("/uc/resource/treeList",param);


export const roleResource = (id) => get("/uc/resource/roleResource/"+id);
export const roleResourceIds = (id) => get("/uc/resource/roleResourceIds/"+id);
export const authRoleResource = (param) => postRequestBody("/uc/resource/authRoleResource",param);
export const userResource = () => get("/uc/resource/userResource");
export const resourceInfoTree = () => get("/uc/resource/resourceInfoTree");
export const changeStatus = (param) => postRequestBody("/uc/resource/changeStatus",param);

