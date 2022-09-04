import { get,postRequestBody} from "../utils/request"

export const list = () => get("/gen/template/list");
export const info = (id) => get("/gen/template/info/" + id);
export const remove = (id) => get("/gen/template/delete/" + id);
export const page = (param) => postRequestBody("/gen/template/page",param);
export const add = (param) => postRequestBody("/gen/template/add",param);
export const edit = (param) => postRequestBody("/gen/template/edit",param);