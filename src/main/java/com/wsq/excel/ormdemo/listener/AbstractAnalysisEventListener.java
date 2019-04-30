package com.wsq.excel.ormdemo.listener;

import com.wsq.excel.context.AnalysisContext;
import com.wsq.excel.event.AnalysisEventListener;

import java.util.List;

/**
 * 抽象解析监听
 *
 * @author wangshuangquan<wangshuangquan-a@qq.com>
 * @date 2019-01-16 9:49
 */
public abstract class AbstractAnalysisEventListener<T> implements AnalysisEventListener {

    ThreadLocal<List<T>> rows = new ThreadLocal<>();

    @Override
    public void invoke(Object object, AnalysisContext context) {
    }

    @Override
    public boolean doAfterAllAnalysed(AnalysisContext context) {
        rows.get().clear();
        rows.remove();
        return true;
    }
}
