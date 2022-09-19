import { get,postRequestBody} from "../utils/request"

export const list = () => get("/server/app/list");
export const info = (id) => get("/server/app/info/" + id);
export const removeRequest = (id) => get("/server/app/remove/" + id);
export const page = (param) => postRequestBody("/server/app/page",param);
export const addRequest = (param) => postRequestBody("/server/app/add",param);
export const updateRequest = (param) => postRequestBody("/server/app/update",param);
