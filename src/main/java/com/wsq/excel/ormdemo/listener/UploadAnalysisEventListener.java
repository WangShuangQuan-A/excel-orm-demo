package com.wsq.excel.ormdemo.listener;

import com.wsq.excel.context.AnalysisContext;
import com.wsq.excel.ormdemo.pojo.Demo;
import com.wsq.excel.ormdemo.service.DemoService;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * 文件解析，写入list,完成后入库操作
 *
 * @author wangshuangquan<wangshuangquan-a@qq.com>
 * @date 2019-02-12 9:48
 */
@Slf4j
public class UploadAnalysisEventListener extends AbstractAnalysisEventListener<Demo>{

    private DemoService service;

    private String infoNum;

    public UploadAnalysisEventListener(DemoService service, String infoNum) {
        this.service = service;
        this.infoNum = infoNum;
    }

    @Override
    public void invoke(Object object, AnalysisContext context) {
        List<Demo> list = rows.get();
        if (list == null ) {
            list = new ArrayList<>(180000);
            rows.set(list);
        }
        Demo demo = (Demo)object;
        demo.setInfoNum(infoNum);
        demo.setStatus("0");
        list.add(demo);
    }

    @Override
    public boolean doAfterAllAnalysed(AnalysisContext context) {
        long startTime = System.currentTimeMillis();
        try {
            service.insetBatch(this.rows.get());
            log.info("数据入库用时:" + (System.currentTimeMillis() - startTime) + "ms");
        } catch (Exception e) {
            return false;
        } finally {
            super.doAfterAllAnalysed(context);
        }
        return true;
    }
}
