/**
 * 币种
 */
import { getRequest, postRequest } from '/@/lib/axios';

export const coinApi = {
  /**
   * 列表 分页
   */
  list: (param) => {
    return postRequest('/api/admin/coin/list', param);
  },
  /**
  * 得到单个详情
  */
  find: (param) => {
    return getRequest('/api/admin/coin/find', param);
  },

  /**
   * 启用禁用
   */
  updateStatus: (param) => {
    return getRequest('/api/admin/coin/updateStatus', param);
  },

  /**
   * 添加或者修改
   */
  addOrUpdate: (param) => {
    return postRequest('/api/admin/coin/addOrUpdate', param);
  },
  /**
  * 删除
  */
  delete: (param) => {
    return getRequest('/api/admin/coin/delete', param);
  },
};
