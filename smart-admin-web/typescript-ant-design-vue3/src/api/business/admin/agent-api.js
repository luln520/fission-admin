/**
 * 代理管理
 */
import { getRequest, postRequest } from '/@/lib/axios';

export const agentApi = {
  /**
   * 列表 分页
   */
  list: (param) => {
    return postRequest('/api/admin/agent/list', param);
  },
  /**
  * 设置代理或者 取消
  */
   setAgent: (param) => {
    return getRequest('/api/admin/agent/setAgent', param);
  },
};
