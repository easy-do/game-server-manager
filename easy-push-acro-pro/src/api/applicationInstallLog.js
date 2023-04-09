import { get,postRequestBody} from "../utils/request"

export const list = () => get("/server/applicationInstallLog/list");
export const infoRequest = (id) => get("/server/applicationInstallLog/info/" + id);
export const removeRequest = (id) => get("/server/applicationInstallLog/remove/" + id);
export const managerPage = (param) => postRequestBody("/server/applicationInstallLog/page",param);
export const addRequest = (param) => postRequestBody("/server/applicationInstallLog/add",param);
export const updateRequest = (param) => postRequestBody("/server/applicationInstallLog/update",param);