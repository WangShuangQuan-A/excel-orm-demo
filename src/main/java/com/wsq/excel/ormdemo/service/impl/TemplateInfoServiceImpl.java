package com.wsq.excel.ormdemo.service.impl;

import com.wsq.excel.ormdemo.dao.TemplateInfoDao;
import com.wsq.excel.ormdemo.pojo.TemplateInfo;
import com.wsq.excel.ormdemo.service.TemplateInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wangshuangquan<wangshuangquan-a @ qq.com>
 * @date 2019-01-25 15:38
 */
@Service("templateInfoService")
public class TemplateInfoServiceImpl implements TemplateInfoService {

    private final TemplateInfoDao templateInfoDao;

    @Autowired
    public TemplateInfoServiceImpl(TemplateInfoDao templateInfoDao) {
        this.templateInfoDao = templateInfoDao;
    }

    @Override
    public List<TemplateInfo> queryByNo(Long no) throws SQLException {
        Map<String,Object> parameters = new HashMap<>(2);
        parameters.put("no",no);
        return templateInfoDao.queryBySqlId("q_byNo",parameters);
    }
}
