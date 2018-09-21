package hackernews.test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import hackernews.Scraper;

class HackerNewsTest {

	private Document doc;

	@BeforeEach
	public void setUp() throws Exception {

		//load the file before each test
		URL path = ClassLoader.getSystemResource("hackernews\\test\\test.html"); //file with 10 posts
		File input = new File(path.toURI());	
		this.doc = Jsoup.parse(input, "UTF-8");

	}

	@Test
	public final void testGet2Posts() throws JSONException, IOException, URISyntaxException {
		//get only 2 posts
		JSONArray result = Scraper.getInstance().parseDocument(this.doc, new JSONArray(), 2);
		assertEquals(result.length(), 2);		
	}

	@Test
	public final void testGet15Posts() throws JSONException { 
		//the file contains only 10 post
		JSONArray result = Scraper.getInstance().parseDocument(this.doc, new JSONArray(), 15);
		assertEquals(result.length(), 10);		
	}

	@Test
	void testScrape() throws JSONException {
		JSONArray result = Scraper.getInstance().parseDocument(this.doc, new JSONArray(), 10);
		assertEquals(result.length(), 10);
		JSONObject article;
		String title, author, uri;
		int comments, rank, points;

		//ARTICLE 1 - OK
		article = result.getJSONObject(0);
		title = article.getString("title");
		author = article.getString("author");
		uri = article.getString("uri");
		comments = article.getInt("comments");
		rank = article.getInt("rank");
		points = article.getInt("points");

		assertTrue(title.length()<=256);
		assertTrue(author.length()<=256);
		assertTrue(!author.equals("empty author"));
		assertTrue(title.length()>0);
		assertTrue(author.length()>0);
		assertTrue(!uri.equals("invalid uri"));
		assertTrue(comments>=0);
		assertTrue(rank>=0);
		assertTrue(points>=0);

		//ARTICLE 2 - TITLE TOO LONG
		article = result.getJSONObject(1);
		title = article.getString("title");
		author = article.getString("author");
		uri = article.getString("uri");
		comments = article.getInt("comments");
		rank = article.getInt("rank");
		points = article.getInt("points");

		assertTrue(title.length()==256);
		assertTrue(author.length()<=256);
		assertTrue(!author.equals("empty author"));
		assertTrue(author.length()>0);
		assertTrue(!uri.equals("invalid uri"));
		assertTrue(comments>=0);
		assertTrue(rank>=0);
		assertTrue(points>=0);

		//ARTICLE 3 - AUTHOR TOO LONG
		article = result.getJSONObject(2);
		title = article.getString("title");
		author = article.getString("author");
		uri = article.getString("uri");
		comments = article.getInt("comments");
		rank = article.getInt("rank");
		points = article.getInt("points");

		assertTrue(title.length()<=256);
		assertTrue(author.length()==256);
		assertTrue(!author.equals("empty author"));
		assertTrue(title.length()>0);
		assertTrue(!uri.equals("invalid uri"));
		assertTrue(comments>=0);
		assertTrue(rank>=0);
		assertTrue(points>=0);

		//ARTICLE 4 - NO COMMENTS
		article = result.getJSONObject(3);
		title = article.getString("title");
		author = article.getString("author");
		uri = article.getString("uri");
		comments = article.getInt("comments");
		rank = article.getInt("rank");
		points = article.getInt("points");

		assertTrue(title.length()<=256);
		assertTrue(author.length()<=256);
		assertTrue(!author.equals("empty author"));
		assertTrue(title.length()>0);
		assertTrue(author.length()>0);
		assertTrue(!uri.equals("invalid uri"));
		assertTrue(comments==0);
		assertTrue(rank>=0);
		assertTrue(points>=0);

		//ARTICLE 5 - NO COMMENT - DISCUSS
		article = result.getJSONObject(4);
		title = article.getString("title");
		author = article.getString("author");
		uri = article.getString("uri");
		comments = article.getInt("comments");
		rank = article.getInt("rank");
		points = article.getInt("points");

		assertTrue(title.length()<=256);
		assertTrue(author.length()<=256);
		assertTrue(!author.equals("empty author"));
		assertTrue(title.length()>0);
		assertTrue(author.length()>0);
		assertTrue(!uri.equals("invalid uri"));
		assertTrue(comments==0);
		assertTrue(rank>=0);
		assertTrue(points>=0);

		//ARTICLE 6 - INVALID URI
		article = result.getJSONObject(5);
		title = article.getString("title");
		author = article.getString("author");
		uri = article.getString("uri");
		comments = article.getInt("comments");
		rank = article.getInt("rank");
		points = article.getInt("points");

		assertTrue(title.length()<=256);
		assertTrue(author.length()<=256);
		assertTrue(!author.equals("empty author"));
		assertTrue(title.length()>0);
		assertTrue(author.length()>0);
		assertTrue(uri.equals("invalid uri"));
		assertTrue(comments>=0);
		assertTrue(rank>=0);
		assertTrue(points>=0);

		//ARTICLE 7 - EMPTY AUTHOR
		article = result.getJSONObject(6);
		title = article.getString("title");
		author = article.getString("author");
		uri = article.getString("uri");
		comments = article.getInt("comments");
		rank = article.getInt("rank");
		points = article.getInt("points");

		assertTrue(title.length()<=256);
		assertTrue(author.equals("empty author"));
		assertTrue(title.length()>0);
		assertTrue(!uri.equals("invalid uri"));
		assertTrue(comments>=0);
		assertTrue(rank>=0);
		assertTrue(points>=0);

		//ARTICLE 8 - NEGATIVE SCORE
		article = result.getJSONObject(7);
		title = article.getString("title");
		author = article.getString("author");
		uri = article.getString("uri");
		comments = article.getInt("comments");
		rank = article.getInt("rank");
		points = article.getInt("points");

		assertTrue(title.length()<=256);
		assertTrue(author.length()<=256);
		assertTrue(!author.equals("empty author"));
		assertTrue(title.length()>0);
		assertTrue(author.length()>0);
		assertTrue(!uri.equals("invalid uri"));
		assertTrue(comments>=0);
		assertTrue(rank>=0);
		assertTrue(points==0);

		//ARTICLE 9 - OK
		article = result.getJSONObject(8);
		title = article.getString("title");
		author = article.getString("author");
		uri = article.getString("uri");
		comments = article.getInt("comments");
		rank = article.getInt("rank");
		points = article.getInt("points");

		assertEquals(author, "al_ramich");
		assertEquals(title, "ARTICLE 9 - OK");
		assertEquals(uri, "https://www.bbc.co.uk/news/business-45595149");
		assertEquals(comments, 13);
		assertEquals(rank, 9);
		assertEquals(points, 22);

		//ARTICLE 10 - OK
		article = result.getJSONObject(9);
		title = article.getString("title");
		author = article.getString("author");
		uri = article.getString("uri");
		comments = article.getInt("comments");
		rank = article.getInt("rank");
		points = article.getInt("points");

		assertEquals(author, "mtmail");
		assertEquals(title, "ARTICLE 10 - OK");
		assertEquals(uri, "http://yourcalendricalfallacyis.com/");
		assertEquals(comments, 101);
		assertEquals(rank, 10);
		assertEquals(points, 215);
	}
}

