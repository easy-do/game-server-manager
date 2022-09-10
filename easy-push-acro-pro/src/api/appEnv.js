import { get} from "../utils/request"


export const appEnvListByScriptId = (id) => get("/server/appEnv/list/" + id);