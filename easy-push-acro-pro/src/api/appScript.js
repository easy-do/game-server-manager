import { get,postRequestBody} from "../utils/request"

export const list = () => get("/server/appScript/list");
export const info = (id) => get("/server/appScript/info/" + id);
export const removeRequest = (id) => get("/server/appScript/remove/" + id);
export const page = (param) => postRequestBody("/server/appScript/page",param);
export const addRequest = (param) => postRequestBody("/server/appScript/add",param);
export const updateRequest = (param) => postRequestBody("/server/appScript/update",param);
export const listByAppId = (appId) => get("/server/appScript/listByAppid/" + appId);