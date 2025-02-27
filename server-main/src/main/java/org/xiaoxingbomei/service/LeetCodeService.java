package org.xiaoxingbomei.service;

import org.xiaoxingbomei.common.entity.GlobalEntity;

/**
 * leetCode
 */
public interface LeetCodeService
{

    /**
     * easy
     */
    public GlobalEntity easy_1(String RequestParam);
    public GlobalEntity easy_136(String RequestParam);
    public GlobalEntity easy_160(String RequestParam);
    public GlobalEntity easy_206(String RequestParam);

    /**
     * normal
     */
    public GlobalEntity normal_1(String RequestParam);

    /**
     * hard
     */
    public GlobalEntity hard_1(String RequestParam);

}
