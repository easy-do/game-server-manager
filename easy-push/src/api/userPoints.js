import { postRequestBody} from "../utils/request"


export const page = (param) => postRequestBody("/uc/userPoints/page",param);

export const userPointsOperation = (param) => postRequestBody("/uc/userPoints/userPointsOperation",param);