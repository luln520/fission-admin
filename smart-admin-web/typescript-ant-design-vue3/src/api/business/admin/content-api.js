/**
 * 内容 公告
 */
import { getRequest, postRequest } from '/@/lib/axios';

export const contentApi = {
  /**
   * 列表 分页
   */
  list: (param) => {
    return postRequest('/api/admin/article/list', param);
  },
  /**
  * 得到单个详情
  */
  find: (param) => {
    return getRequest('/api/admin/article/find', param);
  },

  /**
   * 添加或者修改
   */
  addOrUpdate: (param) => {
    return postRequest('/api/admin/article/addOrUpdate', param);
  },
  /**
  * 删除
  */
  delete: (param) => {
    return getRequest('/api/admin/article/delete', param);
  },
};
