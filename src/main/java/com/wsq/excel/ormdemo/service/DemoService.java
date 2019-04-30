package com.wsq.excel.ormdemo.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @author wangshuangquan<wangshuangquan-a @ qq.com>
 * @date 2019-01-11 16:44
 */
public interface DemoService {

    <E> List<E> queryByOpType(String opType, Map<String, Object> parameters)  throws SQLException;

    void insetBatch(List parameters) throws Exception;
}
