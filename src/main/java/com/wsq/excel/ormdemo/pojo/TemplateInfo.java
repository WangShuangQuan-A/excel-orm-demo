package com.wsq.excel.ormdemo.pojo;

import com.wsq.orm.Column;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Table;
import java.sql.JDBCType;

/**
 * 模板信息
 *
 * @author wangshuangquan<wangshuangquan-a@qq.com>
 * @date 2019-01-25 14:20
 */
@Setter
@Getter
@Table(name = "T_TEMPLATE_INFO")
public class TemplateInfo {

    /**
     * 编号
     */
    @Column(jdbcType = JDBCType.INTEGER)
    private Integer no;

    @Column(jdbcType = JDBCType.INTEGER)
    private Integer sort;

    @Column
    private String headName;

    @Column
    private String fieldName;
}
