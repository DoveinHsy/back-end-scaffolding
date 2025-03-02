package org.xiaoxingbomei.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Dog implements Serializable
{
    private static final long serialVersionUID = 1L;

    private String name;
    private String age;
    private String type;
}
