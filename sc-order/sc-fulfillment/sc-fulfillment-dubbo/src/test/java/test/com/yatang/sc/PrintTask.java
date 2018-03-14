package test.com.yatang.sc;


public class PrintTask implements Runnable{

	String name;

	public PrintTask(String name) {
		this.name = name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void run() {
		System.out.println(name + " is running.");
		for (int i = 0; i < 100; i++) {
			System.out.println("index: " + i + ",name: " + name);
		}
		System.out.println(name + " is running again.");
	}
}
