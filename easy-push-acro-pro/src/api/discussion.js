import { get,postRequestBody} from "../utils/request"


export const page = (param) => postRequestBody("/server/discussion/page",param);
export const managerPage = (param) => postRequestBody("/server/discussion/managerPage",param);
export const info = (id) => get("/server/discussion/info/" + id);
export const add = (param) => postRequestBody("/server/discussion/add",param);
export const edit = (param) => postRequestBody("/server/discussion/edit",param);
export const remove = (id) => get("/server/discussion/delete/" + id);
