import { get,postRequestBody} from "../utils/request"


export const info = (id) => get("/server/appStore/info/" + id);
export const page = (param) => postRequestBody("/server/appStore/page",param);

