package pullUpExample;

public class PullUpMain {
	
	public static void printResults(SubClassA a, SubClassB b) {
		System.out.println("Called from SubClassA: " + a.function() + "\n" + "Called from SubClassB: " + b.function());
	}

	public static void main(String[] args) {
		SubClassA classA = new SubClassA();
		SubClassB classB = new SubClassB();
		printResults(classA, classB);		
	}

}
