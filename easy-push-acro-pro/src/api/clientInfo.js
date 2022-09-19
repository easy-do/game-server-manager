import { get, postRequestBody } from "../utils/request"


export const managerPage = (param) => postRequestBody("/server/client/page",param);
export const addRequest = (param) => postRequestBody("/server/client/add",param);
export const list = () => get("/server/client/list");
export const infoRequest = (id) => get("/server/client/info/" + id);
export const onlineInstall = (id) => get("/server/client/onlineInstall/" + id);
export const getInstallCmd = (id) => get("/server/client/installCmd/" + id);
export const removeRequest = (id) => get("/server/client/remove/" + id);

