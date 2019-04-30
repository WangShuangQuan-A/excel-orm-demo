package com.wsq.excel.ormdemo.web;

import com.wsq.excel.EasyExcelFactory;
import com.wsq.excel.ExcelWriter;
import com.wsq.excel.metadata.Sheet;
import com.wsq.excel.ormdemo.listener.UploadAnalysisEventListener;
import com.wsq.excel.ormdemo.pojo.Demo;
import com.wsq.excel.ormdemo.pojo.TemplateInfo;
import com.wsq.excel.ormdemo.service.DemoService;
import com.wsq.excel.ormdemo.service.TemplateInfoService;
import com.wsq.excel.support.ExcelTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.wsq.orm.ReflectUtil.firstCharToUpperCase;

/**
 * Excel上传，下载
 *
 * @author wangshuangquan<wangshuangquan-a@qq.com>
 * @date 2019-01-11 15:09
 */
@RestController
@RequestMapping("excel")
@Slf4j
public class ExcelController {

    private final DemoService demoService;

    private final TemplateInfoService templateInfoService;

    @Autowired
    public ExcelController(DemoService demoService, TemplateInfoService templateInfoService) {
        this.demoService = demoService;
        this.templateInfoService = templateInfoService;
    }

    /**
     * 数据Excel下载
     * @param response  响应
     * @param infoNum   编号
     * @param fileName  下载文件名
     * @param no        模板编号
     */
    @GetMapping("/down")
    public void downExcel(HttpServletResponse response,
                       @RequestParam("infoNum") @NotNull String infoNum,
                       @RequestParam("fileName") String fileName,
                       @RequestParam("tempNo") Long no) {
        try {
            ServletOutputStream out = response.getOutputStream();
            ExcelWriter writer = EasyExcelFactory.getWriter(out);
            if (StringUtils.isBlank(fileName)) {
                fileName = infoNum + new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            }

            Method[] methods = Demo.class.getMethods();
            Map<String, Method> methodTable = new HashMap<>((int) (methods.length / 0.75) + 1);
            for (Method method : methods) {
                methodTable.put(method.getName(), method);
            }

            List<TemplateInfo> templateInfos = templateInfoService.queryByNo(no);
            if (templateInfos == null) {
                return;
            }
            List<String> headerList = new ArrayList<>();
            List<String> getMethodNameList = new ArrayList<>();
            for (TemplateInfo templateInfo : templateInfos) {
                headerList.add(templateInfo.getHeadName());
                getMethodNameList.add("get" + firstCharToUpperCase(templateInfo.getFieldName()));
            }

            Map<String, Object> parameters = new HashMap<>(2);
            parameters.put("info_num", infoNum);
            List<Demo> awards = demoService.queryByOpType("q_byInfoNumAndStatus", parameters);
            if (awards == null) {
                return;
            }
            List<List<String>> data = new ArrayList<>();
            for (Demo award : awards) {
                List<String> da = new ArrayList<>();
                for (String getMethodName : getMethodNameList) {
                    Method method = methodTable.get(getMethodName);
                    if (method == null) {
                        continue;
                    }
                    Object result = method.invoke(award);
                    da.add(result == null ? null : result.toString());
                }
                data.add(da);
            }
            response.setContentType("application/force-download");
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-disposition", "attachment;filename=" +
                    URLEncoder.encode(fileName + ".xlsx", "utf-8"));
            Sheet sheet = new Sheet(1, 0);
            sheet.setSheetName("发奖结果");
            sheet.setAutoWidth(Boolean.TRUE);
            sheet.setHead(headerList);
            writer.write0(data, sheet);
            writer.finish();
            out.flush();
        } catch (Exception e) {
            log.error("下载异常：" + e.getMessage());
        }
    }

    /**
     * Excel数据导入数据库
     * @param file      文件
     * @param infoNum   编号
     * @return          导入返回信息
     */
    @RequestMapping("/2DB")
    public String uploadExl(@RequestParam("file") MultipartFile file,
                            @RequestParam("infoNum") @NotBlank String infoNum) {
        if (file.isEmpty()) {
            return "文件不能为空";
        }
        String filename = file.getOriginalFilename();
        if (StringUtils.isBlank(filename)) {
            return "文件名不能为空";
        }
        String extname = filename.substring(filename.lastIndexOf("."));
        boolean isExcel = false;
        for (ExcelTypeEnum value : ExcelTypeEnum.values()) {
            if (value.getValue().equals(extname)) {
                isExcel = true;
            }
        }
        if (!isExcel) {
            return "文件类型错误，请重新选择文件";
        }

        try (InputStream inputStream  = file.getInputStream()) {
            final long l = System.currentTimeMillis();
            final boolean successFlag = EasyExcelFactory.readBySax(inputStream, new Sheet(1, 1, Demo.class), new UploadAnalysisEventListener(demoService,infoNum));
            return "入库操作" + (successFlag ? "成功":"失败") + ", 总用时：" + (System.currentTimeMillis() - l) / 1000 ;
        }catch (RuntimeException e){
            throw e;
        }catch (Exception e) {
            return "请重新上传";
        }
    }
}