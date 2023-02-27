import { get,postRequestBody} from "../utils/request"

export const list = () => get("/uc/oauthClient/list");
export const infoRequest = (id) => get("/uc/oauthClient/info/" + id);
export const removeRequest = (id) => get("/uc/oauthClient/remove/" + id);
export const managerPage = (param) => postRequestBody("/uc/oauthClient/page",param);
export const addRequest = (param) => postRequestBody("/uc/oauthClient/add",param);
export const updateRequest = (param) => postRequestBody("/uc/oauthClient/update",param);