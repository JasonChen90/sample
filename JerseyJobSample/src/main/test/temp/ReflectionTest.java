package temp;

import java.lang.reflect.Method;

import org.junit.Test;

public class ReflectionTest {

	@Test
	public void test(){
		try {
			Class clazz = Class.forName("java.lang.ClassNotFoundException");
			Class[] interfaces = clazz.getInterfaces();
			Class superC = clazz.getSuperclass();
			Method[] methods = clazz.getMethods();
			System.out.println("Interfaces:");
			for(Class inter : interfaces){
				System.out.println(inter.getName());
			}
			
			System.out.println("Method:");
			for(Method met : methods){
				System.out.println(met.getName());
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
