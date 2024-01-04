/**
 * 管理员日志
 */
import { getRequest, postRequest } from '/@/lib/axios';

export const adminlogApi = {
  /**
   * 列表 分页
   */
  list: (param) => {
    return postRequest('/api/admin/adminlog/list', param);
  }
};
