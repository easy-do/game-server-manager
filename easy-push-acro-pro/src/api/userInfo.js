import { get } from "../utils/request"


/** 授权登录 */
export const avatarRequest = (id) => get("/uc/user/avatar/" + id);