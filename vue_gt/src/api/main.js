import { localAxios} from "@/util/http-commons";

const local = localAxios(); // axios instance

const url = "/board";

function getTopCourse(success, fail) {
    console.log("getTopCourse 호출 !!!");
    local.get(`${url}/main/course`).then(success).catch(fail);
}

function getTopPlace(success, fail) {
    console.log("getPlace 호출 !!!");
    local.get(`${url}/main/place`).then(success).catch(fail);
}

function getRecentCourse(success, fail) {
    console.log("getRecentCourse 호출 !!!");
    local.get(`${url}/main/recent`).then(success).catch(fail);
}

export { getTopCourse, getTopPlace, getRecentCourse};
