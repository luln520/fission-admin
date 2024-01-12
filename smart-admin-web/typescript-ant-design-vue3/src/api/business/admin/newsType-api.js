/**
 * 公告类型
 */
import { getRequest, postRequest } from '/@/lib/axios';

export const newsTypeApi = {
  /**
   * 列表 分页
   */
  list: (param) => {
    return postRequest('/api/admin/newstype/list', param);
  },
  /**
  * 得到单个详情
  */
  find: (param) => {
    return getRequest('/api/admin/newstype/find', param);
  },

  /**
   * 添加或者修改
   */
  addOrUpdate: (param) => {
    return postRequest('/api/admin/newstype/addOrUpdate', param);
  },
  /**
  * 删除
  */
  delete: (param) => {
    return getRequest('/api/admin/newstype/delete', param);
  },
};
