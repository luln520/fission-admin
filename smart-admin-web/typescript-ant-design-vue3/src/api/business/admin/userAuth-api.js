/**
 * 用户认证
 */
import { getRequest, postRequest } from '/@/lib/axios';

export const userAuthApi = {
  /**
   * 列表 分页
   */
  authList: (param) => {
    return postRequest('/api/admin/user/authList', param);
  },
  /**
   *认证
   */
  authProcess: (param) => {
    return getRequest('/api/admin/user/authProcess', param);
  }
};
