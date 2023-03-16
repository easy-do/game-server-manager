import { get,postRequestBody} from "../utils/request"

export const list = () => get("/server/scriptData/list");
export const infoRequest = (id) => get("/server/scriptData/info/" + id);
export const removeRequest = (id) => get("/server/scriptData/remove/" + id);
export const managerPage = (param) => postRequestBody("/server/scriptData/page",param);
export const addRequest = (param) => postRequestBody("/server/scriptData/add",param);
export const updateRequest = (param) => postRequestBody("/server/scriptData/update",param);
export const listByAppId = (appId) => get("/server/scriptData/listByAppid/" + appId);
export const execScript = (param) => postRequestBody("/server/scriptData/execScript",param);