/**
 * 杠杆
 */
import { getRequest, postRequest } from '/@/lib/axios';

export const leverOrderApi = {
  /**
   * 查询列表
   */
  list: (param) => {
    return postRequest('/api/admin/leverorder/list', param);
  },
  /**
  * 盈亏 设 置
  */
  editKongylo: (param) => {
    return getRequest('/api/admin/leverorder/editKongylo', param);
  },
};
