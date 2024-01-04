/**
 * 网站设置
 */
import { getRequest, postRequest } from '/@/lib/axios';

export const webConfigApi = {
  /**
  * 得到网站信息
  */
  find: (param) => {
    return getRequest('/api/admin/webConfig/find', param);
  },

  /**
   * 添加或者修改
   */
  addOrUpdate: (param) => {
    return postRequest('/api/admin/webConfig/addOrUpdate', param);
  }
};
