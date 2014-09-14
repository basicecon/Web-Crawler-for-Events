/*
Author: Mengyao Wang
Description: Web Crawler for events
*/

import java.io.*;
import java.net.*;
import java.util.regex.*;
import java.util.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Crawler
{
	int nextURLIDScanned = 0;
	int nextURLID;
	int max = Integer.MAX_VALUE; //the maximum number of links stored in urls_table
	int eventURLid = 0;
	int num = 10;
	String domain = null;
	

	static Hashtable<String, Integer> urls_table = new Hashtable<String, Integer>();
	static Hashtable<String, Integer> eventURLs_table = new Hashtable<String, Integer>(); 

	Crawler() {
		nextURLID = 1;
	}

	// urlScanned is the next url scanned in the urls_table
	public void fetchURLs(String inputURL, String urlScanned) {
		try {
			URL url = new URL(urlScanned);
			// open reader for URL
	    	InputStreamReader in = new InputStreamReader(url.openStream());
	    	StringBuilder input = new StringBuilder();

	    	int ch;
			while ((ch = in.read()) != -1) {
	         	input.append((char) ch);
			}

			// finding urls in web source code using regex 
	    	String patternString = "<a\\s+.*href\\s*=\\s*(\"[^\"]*\"|[^\\s>]*)\\s*";
	    	Pattern pattern = Pattern.compile(patternString, Pattern.CASE_INSENSITIVE);
	    	Matcher matcher = pattern.matcher(input);

			while (matcher.find()) {
				
	    		int start = matcher.start();
	    		int end = matcher.end();
	    		String match = input.substring(start, end);
				String urlFound = matcher.group(1);

				if (urlFound.startsWith("\"mailto")) {
					continue;
				}

				if (urlFound.startsWith("\"javascript:")) { // urls like "asdf/javascript:asdf" is valid
					continue;
				}

				String new_URL = transformURL(urlScanned, urlFound);

				if (insertURLs(inputURL, new_URL)) {  // put in url_table
					if (nextURLID >= max) {
						return;
					}
					if (eventURLid == num) {
						eventURLid ++;
						return;
					}
				}
			}
		} catch(Exception e) {
			//System.out.println("ERROR WHEN FECTCHING URLS...");
			//System.out.println(e.getMessage());
		}
	}

	public String getDomain(String inputURL) {
		int fpos = inputURL.indexOf('.');
		int cnt = 0;
		for (int i = 0; i < inputURL.length(); i ++) {
			if (inputURL.charAt(i) == '/') {
				if (cnt == 2) {
					return inputURL.substring(fpos+1, i);
				}
				cnt ++;
			}
		}
		return null;
	}

	public boolean insertURLs(String inputURL, String url) {
		if (urls_table.containsKey(url)) {
			return false;
		}
		if (!url.contains(domain)) {
			return false;
		}
		if (url.contains("help")) {
			return false;
		}
		if (url.contains("detail") || url.contains("detail")) {
			return false;
		}
		if (url.endsWith(".pdf")) {
			return false;
		}
		int pos = url.indexOf("/#");
		if (pos > 0) {
			url = url.substring(0, pos);
		}

		String originalText = "";
		try {
			Document doc = Jsoup.connect(url).ignoreContentType(true).get();

			/*
			judge a URL is an event from its content... (furthur implementation)
			originalText = doc.text();
			originalText = originalText.replaceAll("[^a-zA-Z0-9 ]", "");
			*/

			urls_table.put(url, nextURLID);
			nextURLID ++;
			//System.out.println("INSERTED URL____" + url);
			// check if it is a event, if yes, put in the eventURL_table
			if (isEventURL(inputURL, url) && isDiffEvent(inputURL, url)) {
				eventURLs_table.put(url, eventURLid);
				System.out.println(eventURLid + " th event__" + url);
				eventURLid ++;
			} /*
			else if (!isEventURL(inputURL, url)) {
				System.out.println("DIDNT PASS isEventURL...");
			} else if (!isDiffEvent(inputURL, url)) {
				System.out.println("DIDNT PASS isDiffEvent...");
			} else {
				System.out.println("ERROR...");
			} */
			
			return true;
		} catch(Exception e) {
			return false;
		}
	}

	public boolean isDiffEvent(String inputURL, String url) {
		//make sure no duplication
		if (url.contains(inputURL)) {
			return false;
		}
		if (eventURLs_table.containsKey(url)) {
			return false;
		}
		return true;
	}

	public boolean isEventURL(String inputURL, String url) {
		String prefix = getEventPrefix(inputURL);
		if (url.contains(prefix)) {
			return true;
		} else {
			return false;
		}
	}

	public String getEventPrefix(String inputURL) { // not necessarily a legal url

/*
	enlarge prefix range!!!!!!
	TODO: the following should all be considered as events:
	http://calendar.boston.com/lowell_ma/events/show/blablabla
	http://calendar.boston.com/lowell_ma/events/dance../blablabla
	http://calendar.boston.com/boston_ma/events/show/blablabla

	TODO: could used some hacky way of solving cases like the ones above, but thats not general
*/

		for (int i = inputURL.length()-1; i >= 0; i --) {
			if (inputURL.charAt(i) == '/' && i != inputURL.length()-1) { // last /
				return inputURL.substring(0, i+1);
			}
		}
		return "";
	}

	public String getBaseURL(String inputURL) {  // get root
		if (inputURL.startsWith("http") || inputURL.startsWith("https")) {
			int cnt = 0;
			for (int i = 0; i < inputURL.length(); i ++) {
				if (inputURL.charAt(i) == '/') {
					if (cnt == 2) {
						return inputURL.substring(0, i);
					} 
					cnt ++;
				}
			}
		} else {
			for (int i = 0; i < inputURL.length(); i ++) {
				if (inputURL.charAt(i) == '/') {
					return inputURL.substring(0, i);
				}
			}
		}
		return "";
	}

	public String transformURL(String url_scanned, String url) {

		if (url_scanned.charAt(url_scanned.length()-1) == '/') {
			url_scanned = url_scanned.substring(0, url_scanned.length()-1);
		}
		
		String url_base = getBaseURL(url_scanned);
		url = url.substring(1, url.length()-1);

		if (url.equals("#")) {
			return url_scanned;
		}

		if (url.equals("/")) {
			return url_scanned;
		}

		// special case1: "//twitter.com/blablabla" 
		// special case2: "//movies"
		if (url.startsWith("//")) {  
			
			String patternString = "//\\w+\\.\\w+.*";
	    	Pattern pattern = Pattern.compile(patternString, Pattern.CASE_INSENSITIVE);
	    	Matcher matcher = pattern.matcher(url);

			if (matcher.find()) { //first case
				return "http:" + url;
			} else {
				return url_base + url.substring(1);
			}
		}

		if (url.charAt(0) == '/') {
			return url_base + url;
		}

		if (url.length() == 0) {
			return url_scanned;
		}

		if (url.contains("..") || url.contains("./") || url.contains("/.")) {
			//if (url.contains("../.."))
			Stack<String> stack = stringToStack(url_scanned);
			if (stack.size() > 1) {
				stack.pop();
			}
			while (!url.isEmpty()) {
				int i = url.indexOf('/');
				String tmp = "";
				if (i != -1) {
					tmp = url.substring(0, i);
					url = url.substring(i + 1);
				} else {
					tmp = url;
					url = "";
				}

				if (tmp.equals("..")) {
					stack.pop();
				} else if (tmp.equals(".")) {
				} else {
					stack.push(tmp);
				}
			}
			
			String result = restoreStack(stack);
			return result.substring(1);
		}

		if (url_scanned.endsWith(".html")) {
			int i = url_scanned.length()-1;
			while (url_scanned.charAt(i) != '/') {
				i --;
			}
			url_scanned = url_scanned.substring(0, i);
		}
		
		if (url.startsWith("http")) {
			return url;
		} else {
			if (url.startsWith("/")) {
				url = url_scanned + url;
			} else {
				url = url_scanned + "/" + url;
			}
			return url;
		}
	}

	public Stack<String> stringToStack(String url_str) {
		String[] paths = url_str.split("/");
		Stack<String> stack = new Stack<String>();
		for (String path : paths) {
			stack.push(path);
		}
		return stack;
	}

	public String restoreStack(Stack<String> stack) {
		String result = "";
		while (!stack.isEmpty()) {
			result = "/" + stack.pop() + result;
		}
		return result;
	}

/*
	hacky way of trying to make the searching faster
	idea: searching the most relavant link first
 	most relavant --> parent directory which has the highest possibility of containing similar event_link
*/
	public void switchToNextPriority(String inputURL) {
		String primaryURL = getEventPrefix(inputURL);
		fetchURLs(inputURL, primaryURL);
		if (eventURLid == 0) {
			primaryURL = getEventPrefix(primaryURL);
			fetchURLs(inputURL, primaryURL);
		}
	}

	public void crawl(String inputURL) {
		System.out.println("CRAWLING...");
		System.out.println("CRAWLING...");
		System.out.println("CRAWLING...");

		urls_table.put(inputURL, 0);
		domain = getDomain(inputURL);
		String primaryURL = getEventPrefix(inputURL);
		int cnt = 0;
		
		while (nextURLIDScanned < nextURLID && eventURLid <= num && nextURLID < max) {
			if (cnt == 0) {
				switchToNextPriority(inputURL);
				cnt ++;
			}
			if (eventURLid >= num) {
				return;
			}
			// if not enough, searching the links from the original URL source code
			String link = fetchURLfromHT(nextURLIDScanned);
			fetchURLs(inputURL, link);
			nextURLIDScanned ++;
		}
	}

	public String fetchURLfromHT(int index) {
		Set<Map.Entry<String, Integer>> urls_set = urls_table.entrySet();
		Iterator<Map.Entry<String, Integer>> it = urls_set.iterator();
		while(it.hasNext()) {
			Map.Entry<String, Integer> url_obj = it.next();
			if (url_obj.getValue() == index) {
				return url_obj.getKey();
			}
		}
		return "";
    }

   	public static void main(String[] args) {
		Crawler crawler = new Crawler();
		System.out.println("Enter a activity url ");
		Scanner in = new Scanner(System.in);
		String inputURL = in.nextLine();

		try {
			crawler.crawl(inputURL);	
		} catch(Exception e) {
			System.out.println("NOT EXECUTING.....");
			System.out.println(e.getMessage());
		}
		//crawler.printTable(eventURLs_table);
    }
/*
    public void printTable(Hashtable<String, Integer> table) {
    	Set<Map.Entry<String, Integer>> set = table.entrySet();
		Iterator<Map.Entry<String, Integer>> it = set.iterator();
		while (it.hasNext()) {
			Map.Entry<String, Integer> entry = it.next();
			System.out.println(entry.getKey() + " --------  " + entry.getValue());
		}
    }	
*/
}