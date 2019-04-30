package com.wsq.excel.ormdemo.pojo;

import com.wsq.excel.annotation.ExcelProperty;
import com.wsq.excel.metadata.BaseRowModel;
import com.wsq.orm.Column;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Table;
import java.sql.JDBCType;
import java.sql.Timestamp;

/**
 * @author wangshuangquan<wangshuangquan-a @ qq.com>
 * @date 2019-01-11 16:28
 */
@Setter
@Getter
@ToString
@Table(name = "demo_table")
public class Demo extends BaseRowModel {

    @ExcelProperty(value = "订单编号")
    @Column(primaryKey = true)
    private String orderId;

    @ExcelProperty(value = "入网手机号")
    @Column
    private String phoneNumber;

    @Column(jdbcType = JDBCType.TIMESTAMP)
    private Timestamp awardTime;

    @Column(jdbcType = JDBCType.NCHAR)
    private String status = "0";

    @Column(jdbcType = JDBCType.TIMESTAMP)
    private Timestamp insertTime;

    @Column
    private String sendType;

    @Column
    private String sendContext;

    @Column
    private String infoNum;

    public Timestamp getAwardTime() {
        return awardTime == null ? null : (Timestamp)awardTime.clone();
    }

    public void setAwardTime(Timestamp awardTime) {
        this.awardTime = (awardTime == null ? null : (Timestamp)awardTime.clone());
    }

    public Timestamp getInsertTime() {
        return insertTime == null ? null : (Timestamp)insertTime.clone();
    }

    public void setInsertTime(Timestamp insertTime) {
        this.insertTime = (insertTime == null ? null : (Timestamp)insertTime.clone());
    }
}
