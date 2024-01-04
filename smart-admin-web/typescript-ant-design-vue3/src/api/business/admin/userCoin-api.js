/**
 * 用户财产
 */
import { getRequest, postRequest } from '/@/lib/axios';

export const userCoinApi = {
  /**
   * 列表 分页
   */
  list: (param) => {
    return postRequest('/api/admin/usercoin/list', param);
  }
};
