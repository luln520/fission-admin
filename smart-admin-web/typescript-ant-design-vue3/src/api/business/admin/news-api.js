/**
 * 新闻
 */
import { getRequest, postRequest } from '/@/lib/axios';

export const newsApi = {
  /**
   * 列表 分页
   */
  list: (param) => {
    return postRequest('/api/admin/news/list', param);
  },
  /**
  * 得到单个详情
  */
  find: (param) => {
    return getRequest('/api/admin/news/find', param);
  },

  /**
   * 添加或者修改
   */
  addOrUpdate: (param) => {
    return postRequest('/api/admin/news/addOrUpdate', param);
  },
  /**
  * 删除
  */
  delete: (param) => {
    return getRequest('/api/admin/news/delete', param);
  },
};
