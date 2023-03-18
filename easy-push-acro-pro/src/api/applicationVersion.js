import { get,postRequestBody} from "../utils/request"

export const list = () => get("/server/applicationVersion/list");
export const infoRequest = (id) => get("/server/applicationVersion/info/" + id);
export const removeRequest = (id) => get("/server/applicationVersion/remove/" + id);
export const managerPage = (param) => postRequestBody("/server/applicationVersion/page",param);
export const addRequest = (param) => postRequestBody("/server/applicationVersion/add",param);
export const updateRequest = (param) => postRequestBody("/server/applicationVersion/update",param);
export const versionList = (id) => get("/server/applicationVersion/versionList/"+id);