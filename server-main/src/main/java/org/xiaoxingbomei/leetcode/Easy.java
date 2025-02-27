package org.xiaoxingbomei.leetcode;

import org.xiaoxingbomei.entity.ListNode;

import java.util.HashMap;

/**
 *
 */
public class Easy
{


    // =================================================================================================

    /**
     * easy-1-两数之和
     * 给定一个整数数组 nums 和一个整数目标值 target，请你在该数组中找出 和为目标值 target  的那 两个 整数，并返回它们的数组下标。
     * 你可以假设每种输入只会对应一个答案，并且你不能使用两次相同的元素。
     * 你可以按任意顺序返回答案。
     * 示例 1：
     *
     * 输入：nums = [2,7,11,15], target = 9
     * 输出：[0,1]
     * 解释：因为 nums[0] + nums[1] == 9 ，返回 [0, 1] 。
     * 示例 2：
     *
     * 输入：nums = [3,2,4], target = 6
     * 输出：[1,2]
     * 示例 3：
     *
     * 输入：nums = [3,3], target = 6
     * 输出：[0,1]
     *
     * 思路：
     * 遍历整个目标数组，看看剩余元素有没有自己的另一半
     * 有的话则返回两者下标
     * 不存在则存入map
     * 时间：O(n)
     * 空间：O(1)
     */
    public static int[] twoSum(int[] nums, int target)
    {
        HashMap<Integer, Integer> resultMap = new HashMap<>();
        for (int i = 0; i < nums.length; i++)
        {
            int complement = target - nums[i];
            if(resultMap.containsKey(complement))
            {
                return new int[]{resultMap.get(complement),i};
            }
            resultMap.put(nums[i], i);
        }
        throw new IllegalArgumentException("No two sum solution");
    }


    /**
     * easy-136-只出现一次的数字
     *
     * 思路：
     * 空间：O(1)
     * 时间：O(n)
     */
    public static int singleNumber(int[] nums)
    {
        int result = 0;
        for (int num : nums)
        {
            result ^= num;
        }
        return result;
    }

    /**
     * easy-160-相交链表
     *
     *
     * 思路：
     * 空间：O()
     * 时间：O()
     */
    public static ListNode getInterectionNode(ListNode headA, ListNode headB)
    {
        return null;
    }


    /**
     * easy-206-反转链表
     */
    /**
     * 思路：
     * 空间:O(1)
     * 时间:O(n)
     * 创建一个临时节点来保存下一个节点，用于打破当前和下一个的连接，然后重新构建指向关系
     */
    public static ListNode reverseListNode(ListNode head)
    {
        ListNode prevNode    = null;    // 初始化角色：前驱节点
        ListNode currentNode = head; // 初始化角色：当前节点
        //
        while(currentNode!= null)
        {
            // 获取当前节点的后驱节点
            ListNode nextNode = currentNode.nextNode;
            // 重新构建（反转）当前节点的指向
            currentNode.nextNode = prevNode;
            // 重新赋予角色：前驱节点
            prevNode = currentNode;
            // 重新赋予角色：当前节点
            currentNode = nextNode;
        }
        // 返回反转后的头结点
        return prevNode;
    }



}
