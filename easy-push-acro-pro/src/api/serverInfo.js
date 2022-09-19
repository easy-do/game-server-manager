import { get,postRequestBody} from "../utils/request"

export const list = () => get("/server/serverInfo/list");
export const info = (id) => get("/server/serverInfo/info/" + id);
export const removeRequest = (id) => get("/server/serverInfo/remove/" + id);
export const page = (param) => postRequestBody("/server/serverInfo/page",param);
export const addRequest = (param) => postRequestBody("/server/serverInfo/add",param);
export const testServer = (param) => postRequestBody("/server/serverInfo/test",param);
export const updateRequest = (param) => postRequestBody("/server/serverInfo/update",param);
