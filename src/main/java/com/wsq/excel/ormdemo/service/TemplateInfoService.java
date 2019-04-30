package com.wsq.excel.ormdemo.service;

import com.wsq.excel.ormdemo.pojo.TemplateInfo;

import java.sql.SQLException;
import java.util.List;

/**
 * 模板信息
 *
 * @author wangshuangquan<wangshuangquan-a @ qq.com>
 * @date 2019-01-25 15:37
 */
public interface TemplateInfoService {

    List<TemplateInfo> queryByNo(Long no) throws SQLException;
}
