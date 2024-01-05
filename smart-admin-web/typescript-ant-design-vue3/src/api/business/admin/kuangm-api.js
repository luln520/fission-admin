/**
 * kuangm
 */
import { getRequest, postRequest } from '/@/lib/axios';

export const kuangmApi = {
  /**
   * kuangm列表 分页
   */
  getkuangjiList: (param) => {
    return postRequest('/api/admin/kuangm/getkuangjiList', param);
  },
  /**
  * 会员kuangm列表  分页
  */
  kjlist: (param) => {
    return postRequest('/api/admin/kuangm/kjlist', param);
  },
  /**
 * kuangm收益列表 分页
 */
  kjsylist: (param) => {
    return postRequest('/api/admin/kuangm/kjsylist', param);
  },

  /**
   * 启用禁用 删除2
   */
  kuangjStatus: (param) => {
    return getRequest('/api/admin/kuangm/kuangjStatus', param);
  },

  /**
   * 添加或者修改
   */
  addkj: (param) => {
    return postRequest('/api/admin/kuangm/addkj', param);
  },
  /**
  * 启用会员kj收益，停用会员矿kj收益，删除会员kj收益
  */
  userkjStatus: (param) => {
    return getRequest('/api/admin/kuangm/userkjStatus', param);
  }
};
