import { get,postRequestBody} from "../utils/request"

export const list = () => get("/server/application/list");
export const infoRequest = (id) => get("/server/application/info/" + id);
export const removeRequest = (id) => get("/server/application/remove/" + id);
export const managerPage = (param) => postRequestBody("/server/application/page",param);
export const addRequest = (param) => postRequestBody("/server/application/add",param);
export const updateRequest = (param) => postRequestBody("/server/application/update",param);
export const deploy = (param) => postRequestBody("/server/application/deployment",param);