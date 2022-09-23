import { get,postRequestBody} from "../utils/request"


export const infoRequest = (id) => get("/server/execLog/info/" + id);
export const managerPage = (param) => postRequestBody("/server/execLog/page",param);

