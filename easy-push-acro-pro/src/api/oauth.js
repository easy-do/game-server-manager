import { get,postRequestBody } from "../utils/request"


/** 授权登录 */
export const platformLogin = (platform) => get("/uc/oauth/login/" + platform);
export const getUserInfo = () => get("/uc/oauth/getUserInfo");
export const loginRequst = (param) => postRequestBody("/uc/oauth/login",param);
export const resetPassword = (param) => postRequestBody("/uc/oauth/resetPassword",param);
export const logout = () => get("/uc/oauth/logout");
export const authorization = (authorizationCode) => get("/uc/oauth/authorization/" + authorizationCode )
export const sendEmailcode = (email) => get("/uc/email/sendMailCode?email="+email);
export const bingdingEmail = (email,code) => get("/uc/oauth/bindingEmail?email="+email+"&code="+code);
export const resetSecretRequest = () => get("/uc/oauth/resetSecret")


