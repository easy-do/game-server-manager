import { get,postRequestBody} from "../utils/request"

export const list = () => get("/uc/authCode/list");
export const info = (id) => get("/uc/authCode/info/" + id);
export const page = (param) => postRequestBody("/uc/authCode/page",param);
export const updateRequest = (param) => postRequestBody("/uc/authCode/update",param);
export const gen = (param) => postRequestBody("/uc/authCode/generateAuthCode",param);
export const removeRequest = (id) => get("/uc/authCode/remove/" + id);