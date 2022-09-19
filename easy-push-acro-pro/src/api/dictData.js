import { get,postRequestBody} from "../utils/request"

export const list = () => get("/uc/dictData/list");
export const infoRequest = (id) => get("/uc/dictData/info/" + id);
export const remove = (id) => get("/uc/dictData/delete/" + id);
export const managerPage = (param) => postRequestBody("/uc/dictData/page",param);
export const add = (param) => postRequestBody("/uc/dictData/add",param);
export const edit = (param) => postRequestBody("/uc/dictData/edit",param);
export const changeStatus = (param) => postRequestBody("/uc/dictData/changeStatus",param);
export const listByCode = (code) => get("/uc/dictData/listByCode/"+code);
