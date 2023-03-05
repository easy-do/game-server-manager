import { get,postRequestBody} from "../utils/request"

export const list = () => get("/server/ossManagement/list");
export const infoRequest = (id) => get("/server/ossManagement/info/" + id);
export const removeRequest = (id) => get("/server/ossManagement/remove/" + id);
export const managerPage = (param) => postRequestBody("/server/ossManagement/page",param);
export const addRequest = (param) => postRequestBody("/server/ossManagement/add",param);
export const updateRequest = (param) => postRequestBody("/server/ossManagement/update",param);