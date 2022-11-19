import { get} from "../utils/request"

export const imagesList = (id) => get("/server/docker/image/v1/list/" + id);
export const removeImage = (dockerId,imageId) => get("/server/docker/image/v1/removeImage/" + dockerId+"/"+imageId);
