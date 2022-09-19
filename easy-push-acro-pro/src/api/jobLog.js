import { get,postRequestBody} from "../utils/request"

export const page = (param) => postRequestBody("/server/job/log/page",param);
export const listByJobId = (id) => get("/server/job/log/listByJobId/" + id);
export const info = (id) => get("/server/job/log/info/" + id);
export const removeRequest = (id) => get("/server/job/log/remove/" + id);
export const clean = () => get("/server/job/log/clan");
export const logResultById = (id) => get("/server/job/log/logResult/" + id);
