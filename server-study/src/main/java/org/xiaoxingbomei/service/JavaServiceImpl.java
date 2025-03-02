package org.xiaoxingbomei.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.stereotype.Service;
import org.xiaoxingbomei.entity.Dog;
import org.xiaoxingbomei.entity.Person;

import java.io.*;

@Service
@Slf4j
public class JavaServiceImpl
{

    /**
     * 值传递 & 引用传递
     */
    void swap(int a,int b)
    {
        int temp=a;
        a=b;
        b=temp;
    }

    void change(int x)
    {
        x = 10;
    }

    void change(Person person)
    {
        person.setName("changed");
    }

    /**
     * 每次方法调用，jvm都会在栈中创建一个栈帧，存局部变量
     * 调用这个方法的时候 person1 和 person2 就是栈帧中的局部变量，存的就是引用的副本
     * 当方法结束的时候，栈帧就会销毁了，不会影响原先的应用，所以java对象的传递看着像是引用传递 实际还是值传递 因为传递的是引用的副本
     * @param person1
     * @param person2
     */
    void changePerson(Person person1,Person person2)
    {
        Person temp = person1;
        person1 = person2;
        person2 = temp;
    }

    @Test
    public void JavaTransType()
    {
        int num = 5;
        change(num);
        System.out.println("基本数据类型值传递传的是副本，不会改变原先"+num);

        Person person1 = new Person();
        person1.setName("lvxianghe");
        change(person1);
        System.out.println("对象值传递传的是引用的副本，会改变原先值"+person1.getName());

        Person person2 = new Person();
        person2.setName("lvhexiang");
        changePerson(person1,person2);
        System.out.println("对象值传递传的是引用的副本，局部变量中改变的引用 不会影响原引用"+person1.getName()+person2.getName());
    }


    /**
     * 反射
     */


    /**
     * 浅拷贝 - Cloneable
     */
    @Test
    public void copyOfCloneable()
    {
        Person person1 = new Person();
        person1.setName("吕相赫");
        Dog dog = new Dog();
        dog.setName("博美");
        person1.setDog(dog);
        Person person2 = null;
        try
        {
            person2 = (Person) person1.clone();
            person2.getDog().setName("大型博美");
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        person2.setName("吕和翔");
        System.out.println("浅拷贝不会改变原先基本变量和引用字段，但是不会复制引用指向的对象"+person1.getName()+person1.getDog().getName()+person2.getName()+person2.getDog().getName());
    }


    /**
     * 浅拷贝 - 序列化
     */
    @Test
    public void deepCopyOfSerializable() throws Exception
    {
        Person person1 = new Person();
        person1.setName("lvxianghe");
        Dog dog = new Dog();
        dog.setName("博美");
        person1.setDog(dog);
        // 序列化
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        //
        oos.writeObject(person1);
        // 反序列化
        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bis);
        Person person2 = (Person)ois.readObject();

        person2.setName("lvhexiang");
        person2.getDog().setName("大型博美");
        System.out.println("深拷贝不会改变原先，已经是全新的对象了，包括引用的对象："+person1.getName()+person2.getName()+person1.getDog().getName()+person2.getDog().getName());

    }

    /**
     * 深拷贝 -
     */

    /**
     * 深拷贝
     */


}
