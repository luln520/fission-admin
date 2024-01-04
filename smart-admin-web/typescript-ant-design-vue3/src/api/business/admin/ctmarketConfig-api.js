/**
 * 市场配置
 */
import { getRequest, postRequest } from '/@/lib/axios';

export const ctmarketConfigApi = {
  /**
   * 列表 分页
   */
  list: (param) => {
    return postRequest('/api/admin/ctmarketConfig/list', param);
  },
  /**
  * 得到单个详情
  */
  find: (param) => {
    return getRequest('/api/admin/ctmarketConfig/find', param);
  },

  /**
   * 启用禁用
   */
  updateStatus: (param) => {
    return getRequest('/api/admin/ctmarketConfig/updateStatus', param);
  },

  /**
   * 添加或者修改
   */
  addOrUpdate: (param) => {
    return postRequest('/api/admin/ctmarketConfig/addOrUpdate', param);
  },
  /**
  * 删除
  */
  delete: (param) => {
    return getRequest('/api/admin/ctmarketConfig/delete', param);
  },
};
