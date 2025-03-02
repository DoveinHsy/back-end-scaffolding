package org.xiaoxingbomei.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Person implements Serializable,Cloneable
{
    private static final long serialVersionUID = 1L;

    String name;
    String age;

    Dog dog;



    @Override
    public Object clone() throws CloneNotSupportedException
    {
        return super.clone();
    }
}
