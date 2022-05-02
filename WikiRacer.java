/*
 * AUTHOR: Aditya Pise
 * FILE: WikiRacer.java
 * ASSIGNMENT: Programming Assignment 10 - WikiRacer
 * COURSE: CSC 210; Spring 2022
 * PURPOSE: This is a WikiRacer program which prints the ladder from the 
 * provided start page to the provided end page.  It contains functions 
 * findWikiLadder which computes the ladder and the main function prints
 * the ladder for the user. The start page and the end page are provided
 * as arguments by the user.
 * 
 * USAGE: 
 * java WikiRacer Germany Philosophy
 * 
 * Where Germany is the start page and Philosophy is the end page.
 * 
 * ------Example Input------
 * Germany Philosophy
 * 
 * ------Example Output------
 * [Germany, Rationalism, Philosophy]
 * 
 */
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WikiRacer {

	public static void main(String[] args) {
		List<String> ladder = findWikiLadder(args[0], args[1]);
		System.out.println(ladder);
	}


	/*
	 * This computes the ladder between the provided start and end wikipedia pages.
	 * It returns an ArrayList containing the ladder.
	 * 
	 * @param - start, is the string suggesting the starting page. It is a string.
	 * 		  end, is the string suggesting the end page. It is a string.
	 * 
	 * @return - It returns an ArrayList containing the ladder in the form of the 
	 *         string. Each individual index contains the string suggesting the
	 *         name of the page. 
	 */
	private static List<String> findWikiLadder(String start, String end) {
		MaxPQ laddersQueue = new MaxPQ();
		List<String> ladder = new ArrayList<String>();
		ladder.add(start);
		laddersQueue.enqueue(ladder, 0);
		while(!laddersQueue.isEmpty()) {
			List<String> currentLadder = laddersQueue.dequeue();
			Set<String> setOfLinksOnCurrent = 
					WikiScraper.findWikiLinks(currentLadder.get(currentLadder.size() - 1));
			if(setOfLinksOnCurrent.contains(end)) {
				currentLadder.add(end);
				return currentLadder;
			}
			setOfLinksOnCurrent.parallelStream().forEach(link -> {
				WikiScraper.findWikiLinks(link);
			});
			for(String link : setOfLinksOnCurrent) {
				if(!currentLadder.contains(link)) {
					List<String> copy = new ArrayList<String>(currentLadder);
					Set<String> setOfLinksOnNeighbor = WikiScraper.findWikiLinks(link);
					copy.add(link);
					setOfLinksOnNeighbor.retainAll(WikiScraper.findWikiLinks(end));
					laddersQueue.enqueue(copy, setOfLinksOnNeighbor.size());
				}
			}
		}
		return new ArrayList<String>();
	}

}
