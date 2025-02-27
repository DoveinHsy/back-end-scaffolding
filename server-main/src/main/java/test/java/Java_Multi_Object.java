package test.java;

/**
 *
 */
public class Java_Multi_Object
{
    /**
     * 类变量-a
     * 共享变量
     * 方法区
     */
    private static int a;

    /**
     * 成员变量-b
     * 共享变量
     * 堆
     */
    private int b ;

    /**
     * 局部变量-c d
     * 非共享变量
     * 栈
     */
    public void test(int c)
    {
        int d;
    }

}
