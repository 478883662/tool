const baseURL = getContextPath();
function getContextPath() {
    var pathName = document.location.pathname;
    var index = pathName.substr(1).indexOf("/");
    var result = pathName.substr(0, index + 1);
    return result;
}
const httpPost = function (url, data) {
    let result = axios({
            method: 'post',
            url: baseURL + url,
            data: data,
            header: {
                'Content-Type': 'application/json'
            }
        }).then(resp => {
            return resp.data;
}).catch(error => {
        return "exception=" + error;
});
    return result;
}
//axios封装post请求
const httpPostForm = function (url, data) {
    let result = axios({
            method: 'post',
            url: baseURL + url,
            data: data,
            transformRequest: [function (data) {
                let ret = '';
                for (let i in data) {
                    ret += encodeURIComponent(i) + '=' + encodeURIComponent(data[i]) + "&";
                }
                return ret;
            }],
            header: {
                'Content-Type': 'application/x-www-form-urlencoded'
            }
        }).then(resp => {
            return resp.data;
}).catch(error => {
        return "exception=" + error;
});
    return result;
}

//get请求
const httpGet = function (url) {
    var result = axios({
        method: 'get',
        url: baseURL + url
    }).then(function (resp) {
        return resp.data;
    }).catch(function (error) {
        return "exception=" + error;
    });
    return res
}