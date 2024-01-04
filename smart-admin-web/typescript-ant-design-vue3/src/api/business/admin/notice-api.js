/**
 * 通知
 */
import { getRequest, postRequest } from '/@/lib/axios';

export const noticeApi = {
  /**
   * 列表 分页
   */
  list: (param) => {
    return postRequest('/api/admin/notice/list', param);
  }
};
