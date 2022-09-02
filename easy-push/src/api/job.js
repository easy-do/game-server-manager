import { get,postRequestBody} from "../utils/request"

export const info = (id) => get("/server/job/info/" + id);
export const remove = (id) => get("/server/job/delete/" + id);
export const run = (id) => get("/server/job/run/" + id);
export const page = (param) => postRequestBody("/server/job/page",param);
export const add = (param) => postRequestBody("/server/job/add",param);
export const edit = (param) => postRequestBody("/server/job/edit",param);
