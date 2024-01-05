/**
 * 交易中心
 */
import { getRequest, postRequest } from '/@/lib/axios';

export const tradeApi = {
  /**
   * 合约,平仓订单 查询列表 分页
   */
  hyorderlist: (param) => {
    return postRequest('/api/admin/trade/hyorderlist', param);
  },
  /**
  * 合约设置 编辑
  */
  edit: (param) => {
    return postRequest('/api/admin/trade/edit', param);
  },
  /**
  * 合约单控盈亏 设 置
  */
  editKongyK: (param) => {
    return getRequest('/api/admin/trade/editKongyK', param);
  },
  /**
  * 合约,平仓订单 查询单个
  */
  hyorderId: (param) => {
    return getRequest('/api/admin/trade/hyorderld', param);
  },
  /**
  * 合约设置
  */
  hysettingId: (param) => {
    return getRequest('/api/admin/trade/hysettingId', param);
  },
};
