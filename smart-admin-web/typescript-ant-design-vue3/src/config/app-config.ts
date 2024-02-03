/*
 * 应用默认配置
 *
 * @Author:    1024创新实验室-主任：卓大
 * @Date:      2022-09-03 22:07:01
 * @Wechat:    zhuda1024
 * @Email:     lab1024@163.com
 * @Copyright  1024创新实验室 （ https://1024lab.net ），Since 2012
 */
import { AppConfig } from '/@/types/config';
import { BASE_API_URL,BASE_IMG_URL } from '/@/lib/axios';
/**
 * 应用默认配置
 */

export const appDefaultConfig: AppConfig = {
  // i18n 语言选择
  language: 'zh_CN',
  // 布局: side 或者 side-expand
  layout: 'side',
  // 侧边菜单宽度 ， 默认为200px
  sideMenuWidth: 200,
  // 菜单主题
  sideMenuTheme: 'dark',
  // 标签页
  pageTagFlag: true,
  // 面包屑
  breadCrumbFlag: true,
  // 页脚
  footerFlag: true,
  // 帮助文档
  helpDocFlag: false,
  // 网站名称
  websiteName: 'GQ全球量化平台',
};

export const imageConfig = {
  uploadUrl: `${BASE_API_URL}/api/admin/file/upload`,
  baseImageUrl:`${BASE_IMG_URL}/imgs/`
};