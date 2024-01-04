/**
 * 财务记录
 */
import { getRequest, postRequest } from '/@/lib/axios';

export const financeApi = {
  /**
   * 财务明细列表 分页
   */
  billList: (param) => {
    return postRequest('/api/admin/financeConfig/billList', param);
  },
  /**
  * 确认充
  */
  confirm: (param) => {
    return getRequest('/api/admin/financeConfig/confirm', param);
  },
  /**
   * 确认提
   */
  confirmCoin: (param) => {
    return getRequest('/api/admin/financeConfig/confirmCoin', param);
  },
  /**
   * 提列表
   */
  list: (param) => {
    return postRequest('/api/admin/financeConfig/list', param);
  },
  /**
  * 充列表
  */
  rechargeList: (param) => {
    return postRequest('/api/admin/financeConfig/rechargeList', param);
  },
  /**
  * 驳回充
  */
  reject: (param) => {
    return getRequest('/api/admin/financeConfig/reject', param);
  },
  /**
    * 驳回提
    */
  rejectCoin: (param) => {
    return getRequest('/api/admin/financeConfig/rejectCoin', param);
  }
};
