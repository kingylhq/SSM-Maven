package other;

import java.io.Serializable;

/**
 * 
 * @author 85726
 * 2017年9月14日 18:38:43
 *
 */
public class Person implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5312994242644386559L;
	
//	private String name = "Persion(人类)";
	protected String name = "Persion(人类)";
	
	int age = 0;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
	
	
	
}
