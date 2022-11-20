import { get} from "../utils/request"

export const imagesList = (id) => get("/server/docker/image/v1/list/" + id);
export const removeImage = (dockerId,imageId) => get("/server/docker/image/v1/remove/" + dockerId+"/"+imageId);


export const containerList = (id) => get("/server/docker/container/v1/list/" + id);
export const startContainer = (dockerId,containerId) => get("/server/docker/container/v1/start/" + dockerId+"/"+containerId);
export const restartContainer = (dockerId,containerId) => get("/server/docker/container/v1/restart/" + dockerId+"/"+containerId);
export const stopContainer = (dockerId,containerId) => get("/server/docker/container/v1/stop/" + dockerId+"/"+containerId);
export const logContainer = (dockerId,containerId) => get("/server/docker/container/v1/log/" + dockerId+"/"+containerId);
export const renameContainer = (dockerId,containerId,name) => get("/server/docker/container/v1/rename/" + dockerId+"/"+containerId+"?name="+name);
export const removeContainer = (dockerId,containerId) => get("/server/docker/container/v1/remove/" + dockerId+"/"+containerId);