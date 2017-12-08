package junittest;

import java.util.Arrays;
import java.util.Random;

/**
 * 双色球产生随机数
 * 
 * @author 85726
 *
 */
public class RandomTest {

	public static void main(String[] args) {

		String[] getball = getBalls();
		System.out.println(Arrays.toString(getball));
		
		String str = "abcdef";
		System.out.println("结果"+str.substring(2));
	}

	public static String[] getBalls() {
		// 随机生成双色球号码
		// 案例：6颗红球(33选1) 1颗蓝球(16选1)
		String[] pool = { "01", "02", "03", "04", "05", "06", "07", "08", "09",
				"10", "11", "12", "13", "14", "15", "16", "17", "18", "19",
				"20", "21", "22", "23", "24", "25", "26", "27", "28", "29",
				"30", "31", "32", "33" };
		// 用一个数组存放6个蓝色的球
		String[] balls = new String[6];
		int length = 0;
		// 从pool数组中随机选取6个球，放入数组中，并且不能有重复
		/*
		 * 1、如何随机取 Random r = new Random(); r.nextInt(n); //取到0到n之间的随机数 2、如果不重复
		 * 一个下标的球被使用我们给它加个标识 boolean[] flag = new
		 * bootlean[pool.length];数组中元素默认为false
		 */
//		Arrays.sort(pool);
		boolean[] used = new boolean[pool.length];
		Random r = new Random();
		while (true) {
			int index = r.nextInt(pool.length);
			// 说明该下标已经被使用过
			if (used[index])
				continue; // 结束本次循环，继续下一次循环
			balls[length++] = pool[index];// 把选中的球放入球数组
			used[index] = true;// 把选中的球标识为已经使用过。
			if (length == balls.length) // 如果下标等于6，证明选完了
				break;
		}
		// 红色的球已经选完了
		Arrays.sort(balls);
		// 扩容一个空间，最后一个放蓝球
		// balls = Arrays.copyOf(balls,balls.length+1); 不知道为什么不行，疑惑中
		String[] str = new String[balls.length + 1]; // 所以我只能使用这种老土的方法来实现数组扩容了
		for (int i = 0; i < balls.length; i++)
			str[i] = balls[i];
		balls = str;
		// balls = Arrays.copyOf(balls,balls.length+1);
		// 蓝色的球16选1即可
		balls[balls.length - 1] = pool[r.nextInt(16)];
		return balls;
	}
	

}
