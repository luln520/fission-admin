/**
 * 首页
 */
import { getRequest, postRequest } from '/@/lib/axios';

export const homeApi = {
  /**
   * 获取首页显示数据
   */
   getHomeData: () => {
    return getRequest('/api/admin/index/getIndexData');
  },
};
