import { get,postRequestBody} from "../utils/request"

export const list = () => get("/server/dockerDetails/list");
export const infoRequest = (id) => get("/server/dockerDetails/info/" + id);
export const removeRequest = (id) => get("/server/dockerDetails/remove/" + id);
export const managerPage = (param) => postRequestBody("/server/dockerDetails/page",param);
export const addRequest = (param) => postRequestBody("/server/dockerDetails/add",param);
export const updateRequest = (param) => postRequestBody("/server/dockerDetails/update",param);