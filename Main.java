/* WORD LADDER Main.java
 * EE422C Project 3 submission by
 * Yilin Zhu
 * yz22778
 * 16450
 * <Student2 Name>
 * <Student2 EID>
 * <Student2 5-digit Unique No.>
 * Slip days used: <0>
 * Git URL: https://github.com/Marcus-Zhu/EE422C_HW3_WordLadder
 * Fall 2016
 */


package assignment3;
import java.util.*;
import java.io.*;

public class Main {
	
	// static variables and constants only here.
	
	public static void main(String[] args) throws Exception {
		
		Scanner kb;	// input Scanner for commands
		PrintStream ps;	// output file
		// If arguments are specified, read/write from/to files instead of Std IO.
		if (args.length != 0) {
			kb = new Scanner(new File(args[0]));
			ps = new PrintStream(new File(args[1]));
			System.setOut(ps);			// redirect output to ps
		} else {
			kb = new Scanner(System.in);// default from Stdin
			ps = System.out;			// default to Stdout
		}
		initialize();

		ArrayList<String> inputArr = parse(kb);
		String startWord = inputArr.get(0);
		String endWord = inputArr.get(1);

		ArrayList<String> result = getWordLadderDFS(startWord, endWord);

		if (result == null || result.size() == 0) {
			System.out.println("no word ladder can be found between <" 
					+ startWord + "> and <"	+ endWord + ">.");
			return;					
		}
		else 
			printLadder(result);
	}
	
	public static void initialize() {
		// initialize your static variables or constants here.
		// We will call this method before running our JUNIT tests.  So call it 
		// only once at the start of main.
	}
	
	/**
	 * @param keyboard Scanner connected to System.in
	 * @return ArrayList of 2 Strings containing start word and end word. 
	 * If command is /quit, return empty ArrayList. 
	 */
	public static ArrayList<String> parse(Scanner keyboard) {
		String str1 = new String();
		String str2 = new String();
		ArrayList<String> array = new ArrayList<String>();
		if (keyboard.hasNext()){
			str1 = keyboard.next();
		}
		if (str1.equals("/quit"))
			System.exit(-1);
		if (keyboard.hasNext()){
			str2 = keyboard.next();
		}
		array.add(str1);
		array.add(str2);
		return array;
	}
	
	/**
	 * DFS implementation of word ladder
	 * @param start word
	 * @param end word
	 * @return word ladder found between start and end
	 */
	public static ArrayList<String> getWordLadderDFS(String start, String end) {
//		Set<String> dict = makeDictionary();
//		Map<String, Boolean> visitedDict = new HashMap<String, Boolean>();
		Map<String, ArrayList<String>> dictTree = new HashMap<String, ArrayList<String>>();
		start = start.toUpperCase();
		end = end.toUpperCase();
		dictTree = generateMST(start);
		if (dictTree == null){
			return null;
		}
		
		ArrayList<String> ladder = dfs(start, end, dictTree, 0);
		return ladder;
	}
	
	/**
	 * Generate the Minimal Supporting Tree from start word for the dictionary using BFS
	 * @param start
	 * @return mst
	 */
	private static Map<String, ArrayList<String>> generateMST(String start){
		Map<String, ArrayList<String>> tree = new HashMap<String, ArrayList<String>>();
		Set<String> dict = makeDictionary();
		Queue<String> bfsQueue = new LinkedList<String>();
		start = start.toUpperCase();
		if (!dict.contains(start)){
			return null;			
		}		
		bfsQueue.add(start);
		dict.remove(start);
		while (!bfsQueue.isEmpty()) {
			String w = bfsQueue.poll();
			for (int i = 0; i < w.length(); i++) {
				char [] wChar = w.toCharArray().clone();
				for (char c = 'A'; c <= 'Z'; c++){
					if (wChar[i] == c)
						continue;
					wChar[i] = c;
					String wNew = String.valueOf(wChar);
					if (dict.contains(wNew)) {
						bfsQueue.add(wNew);
						if (tree.containsKey(w))
							tree.get(w).add(wNew);
						else {
							ArrayList<String> a = new ArrayList<String>();
							a.add(wNew);
							tree.put(w, a);
						}
						dict.remove(wNew);
					}
				}
			}
		}
		return tree;
	}
	
	/**
	 * private recursive method for DFS
	 * @param start word
	 * @param end word
	 * @param dict word dictionary
	 * @param visitedDict visited word map
	 * @param cnt recursion depth
	 * @return word ladder found
	 */
	private static ArrayList<String> dfs(String start, String end, 
			Map<String, ArrayList<String>> dictTree, int cnt) {
		ArrayList<String> ladder = new ArrayList<String>();

		if (start.equals(end)){
			ladder.add(start);
			ladder.add(end);
			return ladder;
		}
		
		String w = start;
		if (dictTree.containsKey(w)){
			for (String wNew : dictTree.get(w)) {
				if (wNew.equals(end)) {
					ladder.add(wNew);
					ladder.add(w);
					return ladder;
				}
				ladder = dfs(wNew, end, dictTree, 1+cnt);
				if (ladder.size() != 0){
					ladder.add(0, w);
					return ladder;
				}
			}
		}
		return ladder;
	}
	
    public static ArrayList<String> getWordLadderBFS(String start, String end) {
		Set<String> dict = makeDictionary();
		Map<String, String> backDict = new HashMap<String, String>();
		ArrayList<String> ladder = new ArrayList<String>();
		Queue<String> bfsQueue = new LinkedList<String>();
		start = start.toUpperCase();
		end = end.toUpperCase();
		if (!dict.contains(start) || !dict.contains(end))
			return null;		
		if (start.equals(end)){
			ladder.add(start);
			ladder.add(end);
			return ladder;
		}
		
		bfsQueue.add(start);
		dict.remove(start);
		while (!bfsQueue.isEmpty()) {
			String w = bfsQueue.poll();
			for (int i = 0; i < w.length(); i++) {
				char [] wChar = w.toCharArray().clone();
				for (char c = 'A'; c <= 'Z'; c++){
					if (wChar[i] == c)
						continue;
					wChar[i] = c;
					String wNew = String.valueOf(wChar);
					if (wNew.equals(end)) {
						String wBack = new String();
						ladder.add(0, w);
						while(backDict.containsKey(w)) {
							wBack = backDict.get(w);
							ladder.add(0, wBack);
							w = wBack;
						}
						ladder.add(ladder.size(),end);
						return ladder;
					}
					if (dict.contains(wNew)) {
						bfsQueue.add(wNew);
						backDict.put(wNew, w);
						dict.remove(wNew);
					}
				}
			}
		}

		return null; // replace this line later with real return
	}
    
	public static Set<String>  makeDictionary () {
		Set<String> words = new HashSet<String>();
		Scanner infile = null;
		try {
			infile = new Scanner (new File("five_letter_words.txt"));
		} catch (FileNotFoundException e) {
			System.out.println("Dictionary File not Found!");
			e.printStackTrace();
			System.exit(1);
		}
		while (infile.hasNext()) {
			words.add(infile.next().toUpperCase());
		}
		return words;
	}
	
	/**
	 * Print the word ladder with prompt
	 * @param ladder
	 */
	public static void printLadder(ArrayList<String> ladder) {
		int length = ladder.get(0).length();
		System.out.println("A " + (ladder.size()-2) + "-rung word ladder exists"
				+ " between " + ladder.get(0).toLowerCase() + " and "
				+ ladder.get(ladder.size()-1).toLowerCase() + ".");
		
		// To print lower case words
		for (String s : ladder){
			System.out.println(s.toLowerCase());
		}
		
//		// Highlight the changes
//		if (ladder.size() == 2){		
//			if (!ladder.get(0).equals(ladder.get(1))){
//				System.err.println("Illegal argument");
//				return;
//			}		
//			System.out.println(ladder.get(0));
//			System.out.println(ladder.get(1));
//			return;			
//		}
//		System.out.println(ladder.get(0).toLowerCase());
//		for (int i = 1; i < ladder.size(); i++) {
//			int label = -1;
//			for (int j = 0; j < length; j++) {
//				if (ladder.get(i).charAt(j) != ladder.get(i-1).charAt(j))
//					label = j;
//			}		
//			if (label < 0 || label == length){
//				System.err.println("Illegal argument");
//				return;
//			}			
//			StringBuilder w = new StringBuilder(ladder.get(i).toLowerCase());
//			w.setCharAt(label, Character.toUpperCase(w.charAt(label)));
//			System.out.println(w);
//		}
		
	}
}
