package org.xiaoxingbomei.service.implement;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.xiaoxingbomei.common.entity.GlobalEntity;
import org.xiaoxingbomei.entity.ListNode;
import org.xiaoxingbomei.leetcode.Easy;
import org.xiaoxingbomei.service.LeetCodeService;
import org.xiaoxingbomei.common.utils.Request_Utils;

import java.util.HashMap;

@Service
@Slf4j
public class LeetCodeServiceImpl implements LeetCodeService
{

    @Override
    public GlobalEntity easy_1(String requestParam)
    {
        // 1、接收前端参数
        String      target       = Request_Utils.getParam(requestParam, "target");
        String      numsString   = Request_Utils.getParam(requestParam, "numsString");
        String[]    split        = numsString.split(",", -1);

        int[]       nums         = new int[split.length];
        for (int i = 0; i < split.length; i++)
        {
            nums[i] = Integer.parseInt(split[i].trim());  // 使用trim去除可能的空格
        }

        // 2、调用核心算法
        int[] resultIntArray = Easy.twoSum(nums, Integer.parseInt(target));

        // 3、封装返回参数
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("numsString",numsString);
        resultMap.put("target",target);
        resultMap.put("resultIntIndex",resultIntArray);

        return GlobalEntity.success(resultMap,"leetcode-两数之和");
    }

    @Override
    public GlobalEntity easy_136(String paramString)
    {
        // 1、接收前端参数
        String numsString = Request_Utils.getParam(paramString, "numsString");
        String[] split = numsString.split(",", -1);
        int[] nums = new int[split.length];
        for (int i = 0; i < split.length; i++)
        {
            nums[i] = Integer.parseInt(split[i].trim());
        }

        // 2、调用核心算法
        int result = Easy.singleNumber(nums);

        // 3、构建返回体
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("numsString",numsString);
        resultMap.put("result",result);

        return GlobalEntity.success(resultMap,"");
    }


    @Override
    public GlobalEntity easy_160(String RequestParam)
    {
        return null;
    }

    @Override
    public GlobalEntity easy_206(String paramString)
    {
        // 1、接收前端参数
        String listNodeValueString = Request_Utils.getParam(paramString, "listNodeValueString");

        // 2、构建链表
        String[] listNodeValues = listNodeValueString.split(",", -1);
        int[] nums = new int[listNodeValues.length];
        for (int i = 0; i < listNodeValues.length; i++)
        {
            nums[i] = Integer.parseInt(listNodeValues[i].trim());
        }

        // 3、翻转链表
        ListNode head = ListNode.buildListNode(nums);
        ListNode prevNode = Easy.reverseListNode(head);

        // 4、封装返回体
        HashMap<Object, Object> resultMap = new HashMap<>();
        resultMap.put("listNodeValue",listNodeValueString);
        resultMap.put("prevNode",prevNode);
        return GlobalEntity.success(resultMap,"easy-206-翻转链表-成功");
    }

    @Override
    public GlobalEntity normal_1(String RequestParam)
    {
        return null;
    }

    @Override
    public GlobalEntity hard_1(String RequestParam) {
        return null;
    }
}
