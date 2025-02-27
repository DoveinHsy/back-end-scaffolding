package org.xiaoxingbomei.leetcode;

import java.util.HashMap;
import java.util.Map;

/**
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
 */
public class Easy_1_TwoSumSolution
{

    /**
     * 思路
     * 遍历整个目标数组，看看剩余元素有没有自己的另一半
     * 有的话则返回两者下标
     * 不存在则存入map
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
}
