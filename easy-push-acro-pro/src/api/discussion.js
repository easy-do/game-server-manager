import { get,postRequestBody} from "../utils/request"


export const page = (param) => postRequestBody("/server/discussion/page",param);
export const managerPage = (param) => postRequestBody("/server/discussion/managerPage",param);
export const info = (id) => get("/server/discussion/info/" + id);
export const addRequest = (param) => postRequestBody("/server/discussion/add",param);
export const updateRequest = (param) => postRequestBody("/server/discussion/update",param);
export const removeRequest = (id) => get("/server/discussion/remove/" + id);
