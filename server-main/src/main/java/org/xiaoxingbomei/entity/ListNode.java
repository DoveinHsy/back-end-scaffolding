package org.xiaoxingbomei.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * 单向链表
 */
@Getter
@Setter
public class ListNode
{
    public int value;     // 节点值
    public ListNode nextNode; // 后驱节点

    public ListNode(int value)
    {
        this.value = value;
        this.nextNode = null;
    }

    public ListNode(int value,ListNode nextNode)
    {
        this.value = value;
        this.nextNode = nextNode;
    }

    /**
     * 构建链表
     */
    public static ListNode buildListNode(int[] values)
    {
        //
        if(values == null || values.length == 0)
        {
            return null;
        }

        // 创建链表的头结点
        ListNode head = new ListNode(values[0]);
        ListNode currentNode = head;

        // 依次构建链表
        for (int i = 1; i < values.length; i++)
        {
            ListNode nextNode = new ListNode(values[i]);
            currentNode.nextNode = nextNode;
            currentNode = nextNode;
        }
        return head;
    }

}
