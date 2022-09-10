import { get,postRequestBody} from "../utils/request"

export const list = () => get("/server/appScript/list");
export const info = (id) => get("/server/appScript/info/" + id);
export const remove = (id) => get("/server/appScript/delete/" + id);
export const page = (param) => postRequestBody("/server/appScript/page",param);
export const add = (param) => postRequestBody("/server/appScript/add",param);
export const edit = (param) => postRequestBody("/server/appScript/edit",param);
export const listByAppId = (appId) => get("/server/appScript/listByAppid/" + appId);