import { get,postRequestBody} from "../utils/request"

export const list = () => get("/uc/authCode/list");
export const info = (id) => get("/uc/authCode/info/" + id);
export const page = (param) => postRequestBody("/uc/authCode/page",param);
export const edit = (param) => postRequestBody("/uc/authCode/edit",param);
export const gen = (param) => postRequestBody("/uc/authCode/generateAuthCode",param);
export const remove = (id) => get("/uc/authCode/delete/" + id);