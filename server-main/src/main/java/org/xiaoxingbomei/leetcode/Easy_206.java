package org.xiaoxingbomei.leetcode;

/**
 * 反转链表
 */
public class Easy_206
{
    // 定义一个链表
    class ListNode
    {
        int value;
        ListNode next;
        ListNode(){}
        ListNode(int value){ this.value = value; }
        ListNode(int value, ListNode next){ this.value = value; this.next = next; }
    }

    /**
     * 空间:O(1)
     * 时间:O(n)
     * 创建一个临时节点来保存下一个节点，用于打破当前和下一个的连接，然后重新构建指向关系
     */
    public class Solution
    {
        public ListNode reverseList(ListNode head)
        {
            // 先定义一个空的节点用作反转之后的前驱
            ListNode prevNode = null;
            // 然后定义一个节点指向头结点
            ListNode currentNode = head;
            // 从当前节点开始遍历，逐个完成反转
            while (currentNode != null)
            {
                // 先保存下一个节点，用于打破本次的连接
                ListNode nextNode = currentNode.next;
                // 然后将当前节点指向前一个节点
                currentNode.next = prevNode;
                // 当前节点成为 下一个前驱节点
                nextNode = currentNode;
                // 下一个需要反转的节点 成为 当前节点
                currentNode = nextNode;
            }
            return prevNode;
        }
    }

}
