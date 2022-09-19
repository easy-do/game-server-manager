import { get,postRequestBody} from "../utils/request"


export const page = (param) => postRequestBody("/server/commentDetails/page",param);
export const managerPage = (param) => postRequestBody("/server/commentDetails/managerPage",param);
export const info = (id) => get("/server/commentDetails/info/" + id);
export const addRequest = (param) => postRequestBody("/server/commentDetails/add",param);
export const removeRequest = (id) => get("/server/commentDetails/remove/" + id);