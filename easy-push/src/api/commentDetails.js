import { get,postRequestBody} from "../utils/request"


export const page = (param) => postRequestBody("/server/commentDetails/page",param);
export const managerPage = (param) => postRequestBody("/server/commentDetails/managerPage",param);
export const info = (id) => get("/server/commentDetails/info/" + id);
export const add = (param) => postRequestBody("/server/commentDetails/add",param);
export const remove = (id) => get("/server/commentDetails/delete/" + id);