/**
 * 用户
 */
import { getRequest, postRequest } from '/@/lib/axios';

export const userApi = {
  /**
   * 列表 分页
   */
  list: (param) => {
    return postRequest('/api/admin/user/list', param);
  },
  /**
   * 添加或者修改
   */
  addOrUpdate: (param) => {
    return postRequest('/api/admin/user/addOrUpdate', param);
  },
  /**
   *禁止/允许交易
   */
  setBuy: (param) => {
    return getRequest('/api/admin/user/setBuy', param);
  },
  /**
  *修改用户余额
  */
  setMoney: (param) => {
    return getRequest('/api/admin/user/setMoney', param);
  },
  /**
   *修改会员状态（冻结，解冻，启动提币禁止提币 ，删除会员）
   */
  setUser: (param) => {
    return getRequest('/api/admin/user/setUser', param);
  },
  /**
   *指定必赢/指 定必输/正常输赢
   */
  setWin: (param) => {
    return getRequest('/api/admin/user/setWin', param);
  },
  /**
   *给会员发送通知或者群发
   */
  userNotice: (param) => {
    return getRequest('/api/admin/user/userNotice', param);
  }
};
