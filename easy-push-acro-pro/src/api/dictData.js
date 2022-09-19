import { get,postRequestBody} from "../utils/request"

export const list = () => get("/uc/dictData/list");
export const infoRequest = (id) => get("/uc/dictData/info/" + id);
export const removeRequest = (id) => get("/uc/dictData/remove/" + id);
export const managerPage = (param) => postRequestBody("/uc/dictData/page",param);
export const addRequest = (param) => postRequestBody("/uc/dictData/add",param);
export const updateRequest = (param) => postRequestBody("/uc/dictData/update",param);
export const changeStatus = (param) => postRequestBody("/uc/dictData/changeStatus",param);
export const listByCode = (code) => get("/uc/dictData/listByCode/"+code);
