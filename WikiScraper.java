/*
 * AUTHOR: Aditya Pise
 * FILE: WikiScraper.java
 * ASSIGNMENT: Programming Assignment 10 - WikiRacer
 * COURSE: CSC 210; Spring 2022
 * PURPOSE: The purpose of this code is to help access and process the 
 * Wikipedia pages we need. findWikiLinks() method returns the set 
 * containing all the links on the provided Wikipedia page, fetchHTML()
 * fetches the html contents of the provided Wikipedia page, getURL()
 * method returns the URL of the Wikipedia page, scrapeHTML() gets all
 * the links with the desired format from the Wikipedia page.
 */
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WikiScraper {
	
	private static Map<String, Set<String>> linksMap = new HashMap<String, Set<String>>();
			
	/*
	 * This method returns the Set containing all the links on the the provided Wikipedia
	 * page. 
	 * @param - link, is the string suggesting the link of the Wikipedia page from which
	 * 			we want the links.
	 * @return - links, is the set containing all the links in the form of string on the 
	 * 			 provided Wikipedia page.
	 */
	public static Set<String> findWikiLinks(String link) {
		if(linksMap.containsKey(link)) {
			return linksMap.get(link);
		}
		String html = fetchHTML(link);
		Set<String> links = scrapeHTML(html);
		linksMap.put(link, links);
		return links;
	}
	
	/*
	 * This method fetches the HTML of the requested link in the form of 
	 * the string.
	 * 
	 * @param - link, is the link of the requested Wikipedia page. It is a
	 * 			string.
	 * @return - String representing the HTML code of the Wikipedia page.
	 */
	private static String fetchHTML(String link) {
		StringBuffer buffer = null;
		try {
			URL url = new URL(getURL(link));
			InputStream is = url.openStream();
			int ptr = 0;
			buffer = new StringBuffer();
			while ((ptr = is.read()) != -1) {
			    buffer.append((char)ptr);
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		}
		return buffer.toString();
	}
	
	/*
	 * This method returns the URL of the Wikipedia page we want given the
	 * name of the page. i.e. if we want the Stanford University page we 
	 * pass Stanford_University as link and we will be returned 
	 * "https://en.wikipedia.org/wiki/Stanford_University".
	 * @param - link, is the string suggesting the link of the page we 
	 * 			want.
	 * @return - String suggesting the URL of the page we requested.
	 */
	private static String getURL(String link) {
		return "https://en.wikipedia.org/wiki/" + link;
	}
	
	/*
	 * This method scrapes the HTML code provided and returns all the links with
	 * of the Wikipedia pages embedded on the given HTML page. If the link contains
	 * ":" or "#" it will ignore such links. The links should start with /wiki/.
	 * These links will be added to a Set of all such similar links.
	 * @param - html, is the HTML code of the page which we want to scrape for the
	 * 			links.
	 * @return -  wikiLinks, is the Set containing the links of the pages on the 
	 * 			  provided page. The links are in the form of String.
	 */
	private static Set<String> scrapeHTML(String html) {
		Set<String> wikiLinks = new HashSet<String>();
		String[] patterns = html.split("<a href=\"/wiki/");
		for(int i = 1; i<patterns.length;i++) {
			int lastIndex = patterns[i].indexOf("\"");
			String reqLink = patterns[i].substring(0, lastIndex);
			if(!(reqLink.contains(":") || reqLink.contains("#"))) {
				wikiLinks.add(reqLink);
			}
		}
		
		return wikiLinks;
	}
	
}
