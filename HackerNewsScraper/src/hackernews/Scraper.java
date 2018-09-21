package hackernews;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Scraper {

	private static Scraper _instance;

	public static Scraper getInstance() {

		if(_instance == null) {

			_instance = new Scraper();
		}

		return _instance;
	}

	static public String base_url = "https://news.ycombinator.com/news?p="; 

	public String scrape(int posts) throws IOException, JSONException, URISyntaxException {

		JSONArray list = new JSONArray(); //result list

		//number of pages to scrape - 30 posts per page
		int pages = 1 + (posts/30); 

		//number of posts to get from the last page (if != 0)
		int last_page_posts = posts % 30; 

		for(int i=1; i<pages; i++) {
			
			//make the http get and parse the response
			Document doc = Jsoup.connect(base_url+String.valueOf(i)).get();
			list = this.parseDocument(doc, list, 30); //get all the posts from the page
		}
		if(last_page_posts != 0) { //parse the last page
			
			//make the http get and parse the response 
			Document doc = Jsoup.connect(base_url+String.valueOf(pages)).get();		
			list = this.parseDocument(doc, list, last_page_posts); //get only the number of posts needed
		}
		
		return list.toString();
	}

	public JSONArray parseDocument(Document doc, JSONArray list, int number_of_posts) throws JSONException {

		//get the item list table
		Element table = doc.select("table[class=itemlist]").get(0); 
		
		//collect all the rows
		Elements rows = table.select("tr"); 

		//create a list of new elements containing all the info needed for each post
		Elements items = new Elements();  
		for(int j=0; j<rows.size(); j++) {
			if(rows.get(j).hasClass("athing")) {  //merge two rows for each post
				items.add(rows.get(j).appendChild(rows.get(j+1)));
			}
		}

		int count = 0;

		for(Element item : items) {

			if(count<number_of_posts) {

				JSONObject obj = new JSONObject();

				int rank, points, comments;
				String title, uri, author;

				//get all the parameters needed 
				//verify the conditions for each one
				//if not verified, set default values

				//rank				
				try {
					rank = Integer.parseInt(item.select("span[class=rank]").get(0).text().replace(".",""));
					if(rank<0) { 
						rank = 0;
					}
				}catch(java.lang.IndexOutOfBoundsException | java.lang.NumberFormatException e) {
					rank = 0;
				}

				//title				
				try {
					title = item.select("a[class=storylink]").get(0).text();
					if(title.length()>256) {
						title = title.substring(0, 256);
					}
					else if(title.length()==0){
						title = "empty title";
					}
				}catch(java.lang.IndexOutOfBoundsException e) {
					title = "unknown";
				}

				//uri
				try {
					uri = item.select("a[class=storylink]").get(0).attr("href");
					new URL(uri); //verify if the uri is valid
				}catch(java.net.MalformedURLException e) {
					uri = "invalid uri";
				}catch(java.lang.IndexOutOfBoundsException e) {
					uri = "unknown";
				}

				//points
				try {
					points = Integer.parseInt(item.select("span[class=score]").get(0).text().split(" ")[0]);
					if(points<0) {
						points = 0;
					}
				}catch(java.lang.IndexOutOfBoundsException | java.lang.NumberFormatException e) {
					points = 0;
				}

				//author
				try {
					author = item.select("a[class=hnuser]").get(0).text();
					if(author.length()>256) {
						author = author.substring(0, 256);
					}
					else if(author.length()==0){
						author = "empty author";
					}
				}catch(java.lang.IndexOutOfBoundsException e) {
					author = "unknown";
				}

				//comments
				try {
					String comm = item.select("td[class=subtext]").select("a").get(3).text();

					if(comm.contains("comment")) { //sometimes the element exists but it's not the comment field (discuss)
						comments = Integer.parseInt(comm.split(" ")[0]);
						if(comments<0) {
							comments = 0;
						}
					}else {
						comments = 0;
					}				
				}catch(java.lang.IndexOutOfBoundsException | java.lang.NumberFormatException e) {
					comments = 0;					
				}

				//create the json object
				obj.put("title", title);
				obj.put("uri", uri);
				obj.put("author", author);
				obj.put("points", points);
				obj.put("comments", comments);
				obj.put("rank", rank);

				//add the json object to the list
				list.put(obj);		
				
				count++;
			}
		}
		return list;
	}
}