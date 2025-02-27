package org.xiaoxingbomei.Enum;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


/**
 * 业务操作类型枚举
 */
@AllArgsConstructor
@NoArgsConstructor
public enum OperateTypeEnum
{

    OTHER("0","其他"),
    CLICK("1","点击"),
    DRAG("2","拖拽"),


    ;

    String operateTypeId;
    String operateTypeName;


}
