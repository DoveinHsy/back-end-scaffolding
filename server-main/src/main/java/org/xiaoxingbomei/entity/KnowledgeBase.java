package org.xiaoxingbomei.entity;

import cn.idev.excel.annotation.ExcelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Getter
@Setter
public class KnowledgeBase
{

    private Long            id;            // 知识库主键 ID

    @ExcelProperty("所属用户")
    private String          userId;        // 所属用户 ID

    @ExcelProperty("知识库名称")
    private String          name;          // 知识库名称

    @ExcelProperty("知识库描述")
    private String          description;   // 知识库描述

    @ExcelProperty("可见性")
    private String          visibility;    // 可见性 (private/public)

    @ExcelProperty("封面图地址")
    private String          coverImageUrl; // 知识库封面图 URL

    @ExcelProperty("创建时间")
    private String          createdTime;   // 创建时间

    @ExcelProperty("更新时间")
    private String          updatedTime;   // 更新时间

    private String          isDeleted;     // 是否已删除
}
