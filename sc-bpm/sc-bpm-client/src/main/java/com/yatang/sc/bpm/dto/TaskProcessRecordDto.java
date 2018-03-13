package com.yatang.sc.bpm.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * @author tangqi
 * @create 2017-11-28 13:53
 * @desc 任务处理生成的记录
 **/
@Getter
@Setter
public class TaskProcessRecordDto implements Serializable{

    private static final long serialVersionUID = -5464727353705276548L;
    private String id;
    //处理人
    private String handler;
    //处理的时间
    private Date handlerDate;
    //处理结果
    private boolean pass;
    //备注
    private String content;

    @Override
    public String toString() {
        return "TaskProcessRecordDto{" +
                "id='" + id + '\'' +
                ", handler='" + handler + '\'' +
                ", handlerDate=" + handlerDate +
                ", pass=" + pass +
                ", content='" + content + '\'' +
                '}';
    }
}
