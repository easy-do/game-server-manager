import { get,postRequestBody} from "../utils/request"

export const list = () => get("/server/app/list");
export const info = (id) => get("/server/app/info/" + id);
export const remove = (id) => get("/server/app/delete/" + id);
export const page = (param) => postRequestBody("/server/app/page",param);
export const add = (param) => postRequestBody("/server/app/add",param);
export const edit = (param) => postRequestBody("/server/app/edit",param);
