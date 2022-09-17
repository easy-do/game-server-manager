import { get,postRequestBody,postRequestParam,downLoadGet} from "../utils/request"

export const list = () => get("/gen/genTable/list");
export const infoRequest = (id) => get("/gen/genTable/info/" + id);
export const remove = (id) => get("/gen/genTable/delete/" + id);
export const managerPage = (param) => postRequestBody("/gen/genTable/page",param);
export const add = (param) => postRequestBody("/gen/genTable/add",param);
export const edit = (param) => postRequestBody("/gen/genTable/edit",param);
export const dbList = (param) => postRequestBody("/gen/genTable/db/list",param);
export const importTable = (param) => postRequestParam("/gen/genTable/importTable",param);
export const batchGenCode = (ids) => downLoadGet("/gen/genTable/batchGenCode?ids=" + ids,'zip');
export const preview = (id) => get("/gen/genTable/preview/" + id);


