import { get,postRequestBody} from "../utils/request"

export const page = (param) => postRequestBody("/server/system/log/page",param);
export const info = (id) => get("/server/system/log/info/" + id);
export const remove = (id) => get("/server/system/log/delete/" + id);
export const clean = () => get("/server/system/log/clean");

