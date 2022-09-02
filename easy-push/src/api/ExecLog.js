import { get,postRequestBody} from "../utils/request"


export const info = (id) => get("/server/execLog/info/" + id);
export const page = (param) => postRequestBody("/server/execLog/page",param);

