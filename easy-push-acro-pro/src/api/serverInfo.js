import { get,postRequestBody} from "../utils/request"

export const list = () => get("/server/serverInfo/list");
export const info = (id) => get("/server/serverInfo/info/" + id);
export const remove = (id) => get("/server/serverInfo/delete/" + id);
export const page = (param) => postRequestBody("/server/serverInfo/page",param);
export const add = (param) => postRequestBody("/server/serverInfo/add",param);
export const testServer = (param) => postRequestBody("/server/serverInfo/test",param);
export const edit = (param) => postRequestBody("/server/serverInfo/edit",param);
