import { get,postRequestBody} from "../utils/request"

export const list = () => get("/server/application/list");
export const infoRequest = (id) => get("/server/application/info/" + id);
export const remove = (id) => get("/server/application/delete/" + id);
export const managerPage = (param) => postRequestBody("/server/application/page",param);
export const add = (param) => postRequestBody("/server/application/add",param);
export const edit = (param) => postRequestBody("/server/application/edit",param);
export const deploy = (param) => postRequestBody("/server/application/deployment",param);