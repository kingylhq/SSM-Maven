package other;

/**
 * 子类
 * @author 85726
 * 2017年9月14日 18:41:35
 */
public class Child extends Thread{
		
	/**
	 * 
	 */
	public String grade;
	
	public static void main(String[] args) {
		
		//1、测试继承
//		Person persion = new Child();
//		System.out.println("结果： "+persion.name);
		
		float num = (float) (3*0.1);
		
		//2、测试float
//		if(num == 0.3){
//			System.out.println(num+"等于0.3");
//		}else{
//			System.out.println("不等于");
//		}
		
		//3、Integer对象判断比较
//		Integer integer = new Integer(50);
//		Double db = new Double(50);
//		Float fl = new Float(50);
//		
//		// integer.equals(50) true
//		// fl.equals(db) false
//		if(fl.equals(db)){
//			System.out.println("true");
//		}else{
//			System.out.println("false");
//		}
		
		//4、 两个String对象比较
		String str = new String("yh");
		String rts = new String("yh");
		if(str.equals(rts)){
			System.out.println("yh is a pig");
		}else{
			System.out.println("no");
		}
		
	}

}
