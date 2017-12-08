package com.lq.model;

import java.io.Serializable;

/**
 * dog实体类
 * @author liqian
 * 2017-12-7 16:53:31
 */
public class Dog implements Serializable{
	
	/**缓存用**/
	private static final long serialVersionUID = 237926414198304679L;
	
	
	private String type;
	
	private int age;
	
	private String color;
	
	

	//生成get、set方法
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	
	public static void main(String[] args) {
		Dog dog = new Dog();
		
		dog.setAge(30);
		dog.setColor("white");
		dog.setType("泰迪狗");
		
	}
	
	
	
	
	

}
