import { get,postRequestBody} from "../utils/request"

export const info = (id) => get("/server/job/info/" + id);
export const removeRequest = (id) => get("/server/job/remove/" + id);
export const run = (id) => get("/server/job/run/" + id);
export const page = (param) => postRequestBody("/server/job/page",param);
export const addRequest = (param) => postRequestBody("/server/job/add",param);
export const updateRequest = (param) => postRequestBody("/server/job/update",param);
