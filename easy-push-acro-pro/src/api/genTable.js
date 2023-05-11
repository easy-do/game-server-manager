import { get,postRequestBody,postRequestParam,downLoadGet,downLoadPost} from "../utils/request"

export const list = () => get("/gen/genTable/list");
export const infoRequest = (id) => get("/gen/genTable/info/" + id);
export const removeRequest = (id) => get("/gen/genTable/remove/" + id);
export const managerPage = (param) => postRequestBody("/gen/genTable/page",param);
export const addRequest = (param) => postRequestBody("/gen/genTable/add",param);
export const updateRequest = (param) => postRequestBody("/gen/genTable/update",param);
export const dbList = (param) => postRequestBody("/gen/genTable/db/list",param);
export const importTable = (param) => postRequestParam("/gen/genTable/importTable",param);
export const batchGenCode = (ids) => downLoadGet("/gen/genTable/batchGenCode?ids=" + ids,'zip');
export const preview = (id) => get("/gen/genTable/preview/" + id);
export const generateDataBaseDocx = (param) => downLoadPost("/gen/genTable/generateDataBaseDocx",param,'zip');
