import { get,postRequestBody} from "../utils/request"

export const list = () => get("/oss/ossManagement/list");
export const infoRequest = (id) => get("/oss/ossManagement/info/" + id);
export const removeRequest = (id) => get("/oss/ossManagement/remove/" + id);
export const managerPage = (param) => postRequestBody("/oss/ossManagement/page",param);
export const addRequest = (param) => postRequestBody("/oss/ossManagement/add",param);
export const updateRequest = (param) => postRequestBody("/oss/ossManagement/update",param);