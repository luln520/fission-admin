/**
 * 资金流水列表
 */
import { getRequest, postRequest } from '/@/lib/axios';

export const amountLogApi = {
  /**
   * 所有用户对话列表
   */
  list: (param) => {
    return postRequest('/api/admin/amountlog/list', param);
  }
};
