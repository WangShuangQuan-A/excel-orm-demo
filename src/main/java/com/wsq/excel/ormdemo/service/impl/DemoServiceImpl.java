package com.wsq.excel.ormdemo.service.impl;

import com.wsq.excel.ormdemo.dao.DemoDao;
import com.wsq.excel.ormdemo.service.DemoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * 发奖实现
 *
 * @author wangshuangquan<wangshuangquan-a@qq.com>
 * @date 2019-01-11 16:45
 */

@Service("awardService")
@Slf4j
public class DemoServiceImpl implements DemoService {

    private final DemoDao demoDao;

    @Autowired
    public DemoServiceImpl(DemoDao demoDao) {
        this.demoDao = demoDao;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void insetBatch(List parameters) throws Exception {
        demoDao.insertBatch("i_batch",parameters);
    }

    @Override
    public <E> List<E> queryByOpType(String opType, Map<String, Object> parameters) throws SQLException {
        return demoDao.queryBySqlId(opType,parameters);
    }
}
