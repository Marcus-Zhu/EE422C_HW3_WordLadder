
public class testCases {
	public static void test1(){ // same word
		ArrayList<String> result = getWordLadderBFS("peach", "peach");
		if(result.size() == 0){
			System.out.println("Test 1 Passed for BFS");
		}
		result = getWordLadderDFS("peach", "peach");
		if(result.size() == 0){
			System.out.println("Test 1 Passed for DFS");
		}
	}
public static void test2(){ // invalid word
	ArrayList<String> result = getWordLadderBFS("preach", "peach");
	if(result == null){
		System.out.println("Test 2 Passed for BFS");
	}
	result = getWordLadderDFS("preach", "peach");
	if(result == null){
		System.out.println("Test 2 Passed for DFS");
	}
	}
public static void test3(){ // exists word ladder
	ArrayList<String> result = getWordLadderBFS("punch", "brawl");
	if(result.size() != 0){
		System.out.println("Test 3 Passed for BFS");
	}
	result = getWordLadderDFS("punch", "brawl");
	if(result.size() != 0){
		System.out.println("Test 3 Passed for DFS");
	}
	printLadder(result);
}
public static void test4(){
	ArrayList<String> result = getWordLadderBFS("pants", "pangs");
	if(result.size() <= 2){
		System.out.println("Test 4 Passed for BFS");
	}
	result = getWordLadderDFS("pants", "pangs");
	if(result.size() <= 2){
		System.out.println("Test 4 Passed for DFS");
	}
	printLadder(result);
}
public static void test5(){ // small word ladder
	ArrayList<String> result = getWordLadderBFS("panda", "pangs");
	if(result.size() <= 3){
		System.out.println("Test 5 Passed for BFS");
	}
	result = getWordLadderDFS("panda", "pangs");
	if(result.size() <= 3){
		System.out.println("Test 5 Passed for DFS");
	}
	printLadder(result);
}
public static void test6(){ // no word ladder exists
	ArrayList<String> result = getWordLadderBFS("parse", "zebra");
	if(result == null || result.size() == 0){
		System.out.println("Test 6 Passed for BFS");
	}
	result = getWordLadderDFS("parse", "zebra");
	if(result == null || result.size() == 0){
		System.out.println("Test 6 Passed for DFS");
	}
}
public static void test7(){ // longer ladder
	ArrayList<String> result = getWordLadderBFS("house", "about");
	if(result.size() < 15){
		System.out.println("Test 7 Passed for BFS");
	}
	result = getWordLadderDFS("house", "about");
	if(result.size() < 15){
		System.out.println("Test 7 Passed for DFS");
	}
	printLadder(result);
}
public static void test8(){ // optimal result
	ArrayList<String> result = getWordLadderBFS("child", "golds");
	if(result.size() <= 9){
		System.out.println("Test 8 Passed for BFS");
	}
	result = getWordLadderDFS("child", "golds");
	if(result.size() <= 9){
		System.out.println("Test 8 Passed for DFS");
	}
	printLadder(result);
}
public static void test9(){ // test for duplicates
	ArrayList<String> result = getWordLadderBFS("hover", "grant");
	boolean failed = false;
	for (int i = 0; i < result.size(); i++){
		String word = result.get(i);
		if(result.indexOf(word) != result.lastIndexOf(word)){
			System.out.println("Test 9 Failed for BFS");
			failed = true;
			break;
		}
	}
	if(!failed){
		System.out.println("Test 9 Passed for BFS");
	}
	result = getWordLadderDFS("hover", "grant");
	failed = false;
	for (int i = 0; i < result.size(); i++){
		String word = result.get(i);
		if(result.indexOf(word) != result.lastIndexOf(word)){
			System.out.println("Test 9 Failed for DFS");
			failed = true;
			break;
		}
	}
	if(!failed){
		System.out.println("Test 9 Passed for DFS");
	}
	printLadder(result);
}

public static void test10(){ // test quit
	Scanner kb;
	kb = new Scanner(System.in);
	System.out.println("Test 10 will now terminate the system...(Enter /quit to quit)");
	parse(kb);
}
}
