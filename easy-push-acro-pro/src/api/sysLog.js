import { get,postRequestBody} from "../utils/request"

export const managerPage = (param) => postRequestBody("/uc/sysLog/page",param);
export const infoRequest = (id) => get("/uc/sysLog/info/" + id);
export const removeRequest = (id) => get("/uc/sysLog/remove/" + id);
export const clean = () => get("/uc/sysLog/clean");

