/**
 * 设置
 */
import { getRequest, postRequest } from '/@/lib/axios';

export const leversetApi = {
  /**
   * 列表 分页
   */
  list: (param) => {
    return postRequest('/api/admin/leverset/list', param);
  },
  /**
  * 得到单个详情
  */
  find: (param) => {
    return getRequest('/api/admin/leverset/find', param);
  },

  /**
   * 添加或者修改
   */
  addOrUpdate: (param) => {
    return postRequest('/api/admin/leverset/addOrUpdate', param);
  },
  /**
  * 删除
  */
  delete: (param) => {
    return getRequest('/api/admin/leverset/delete', param);
  },
};
