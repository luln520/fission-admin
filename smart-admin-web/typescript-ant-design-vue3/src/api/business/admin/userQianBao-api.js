/**
 * 用户钱包
 */
import { getRequest, postRequest } from '/@/lib/axios';

export const userQianBaoApi = {
  /**
   * 列表 分页
   */
  list: (param) => {
    return postRequest('/api/admin/userqianbao/list', param);
  },
  /**
  * 得到单个详情
  */
  addUpdate: (param) => {
    return postRequest('/api/admin/userqianbao/addUpdate', param);
  },

  /**
   * 删除
   */
  del: (param) => {
    return getRequest('/api/admin/userqianbao/del', param);
  }
};
