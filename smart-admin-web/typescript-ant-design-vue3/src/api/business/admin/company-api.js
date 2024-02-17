/**
 * 公司列表
 */
import { getRequest, postRequest } from '/@/lib/axios';

export const companyApi = {
  /**
   * 列表 分页
   */
  list: (param) => {
    return postRequest('/api/admin/company/list', param);
  },
  /**
  * 得到单个详情
  */
  find: (param) => {
    return getRequest('/api/admin/company/find', param);
  },

  /**
   * 添加或者修改
   */
  addOrUpdate: (param) => {
    return postRequest('/api/admin/company/addOrUpdate', param);
  },
  /**
  * 删除
  */
  delete: (param) => {
    return getRequest('/api/admin/company/delete', param);
  },
   /**
   * 启用禁用
   */
    updateStatus: (param) => {
      return getRequest('/api/admin/company/updateStatus', param);
    },
};
