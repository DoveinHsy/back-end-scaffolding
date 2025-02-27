package org.xiaoxingbomei.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Range;
import org.xiaoxingbomei.exception.BusinessException;

import java.util.Objects;


@Schema(description = "分页")
@Getter
@Setter
@ToString
@Accessors(chain = true)
public class PaginationRequest
{
    /**
     * 最小页码
     */
    public static final int MIN_PAGE_NUMBER = 1;

    /**
     * 页码
     */
    @Schema(description = "页码，不小于1")
    @Range(min = 1, max = Integer.MAX_VALUE, message = "页码为正整数")
    private int pageNumber = 1;

    /**
     * 页大小
     */
    @Schema(description = "页大小")
    @Range(min = 5, max = 10000, message = "页大小取值范围[5,10000]")
    private int pageSize = 10;

    /**
     * 总数
     */
    @Schema(description = "总数")
    private long total = -1;

    /**
     * 是否尾页
     *
     * @return true:尾页;false:非尾页
     */
    public boolean isLastPage()
    {
        if (this.pageNumber < MIN_PAGE_NUMBER)
        {
            throw new BusinessException("页码超出范围");
        }
        if (this.pageSize < 1) {
            throw new BusinessException("分页大小不能小于1");
        }
        if (this.total < 0) {
            return true;
        }
        // 最大页码为（总数/页大小）向上取整
        long maxPageNumber = (long) Math.ceil(1.0 * this.total / this.pageSize);
        // 考虑到击穿情况，只要实际页码大于等于最大页码就表示是尾页
        return this.pageNumber >= maxPageNumber;
    }

    /**
     * 是否第一页
     *
     * @return true:是第一页；false:非第一页
     */
    public boolean isFirstPage() {
        return Objects.equals(this.pageNumber, MIN_PAGE_NUMBER);
    }

    /**
     * 获取开始行数
     *
     * @return 开始行数
     */
    public long getStartRow() {
        return (long) (this.pageNumber - 1) * pageSize;
    }

    /**
     * 获取结束行数
     *
     * @return 结束行数
     */
    public long getEndRow() {
        return getStartRow() + pageSize;
    }
}
