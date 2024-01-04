/**
 * 客服
 */
import { getRequest, postRequest } from '/@/lib/axios';

export const onlineApi = {
  /**
   * 所有用户对话列表
   */
  list: (param) => {
    return postRequest('/api/admin/online/list', param);
  },
  /**
  * 得到单个用户对话信息
  */
   getId: (param) => {
    return getRequest('/api/admin/online/getId', param);
  },
  /**
   * 客服回复
   */
   backOnline: (param) => {
    return getRequest('/api/admin/online/backOnline', param);
  }
};
