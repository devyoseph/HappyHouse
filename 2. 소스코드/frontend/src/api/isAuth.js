import { apiInstance } from "./index.js";

const api = apiInstance();

async function isAuth(access_token, success, fail) {
  await api.get(`user/isAuth/${access_token}`).then(success).catch(fail);
}

export { isAuth };
