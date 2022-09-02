import { postRequestBody} from "../utils/request"


export const audit = (param) => postRequestBody("/server/audit/audit" , param);
export const commitAudit = (param) => postRequestBody("/server/audit/commitAudit", param);