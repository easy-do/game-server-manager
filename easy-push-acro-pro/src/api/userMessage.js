import { get, postRequestBody} from "../utils/request"


export const count = () => get("/uc/userMessage/count");
export const messagePage = (param) => postRequestBody("/uc/userMessage/page",param);
export const messageList = () => get("/uc/userMessage/list");
export const readMessageRequest = (id) => get("/uc/userMessage/readMessage/"+id);
export const readAllMessageRequest = () => get("/uc/userMessage/readAllMessage");
export const cleanAllMessageRequest = () => get("/uc/userMessage/cleanAllMessage");
