import { get,postRequestBody} from "../utils/request"

export const managerPage = (param) => postRequestBody("/log-audit/sysLog/page",param);
export const infoRequest = (id) => get("/log-audit/sysLog/info/" + id);
export const removeRequest = (id) => get("/log-audit/sysLog/remove/" + id);
export const clean = () => get("/log-audit/sysLog/clean");

