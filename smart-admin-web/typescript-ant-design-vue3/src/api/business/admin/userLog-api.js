/**
 * 用户日志
 */
import { getRequest, postRequest } from '/@/lib/axios';

export const userlogApi = {
  /**
   * 列表 分页
   */
  list: (param) => {
    return postRequest('/api/admin/userlog/list', param);
  }
};
