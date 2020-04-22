package SD.Discord.Music;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class MusicSearch {
	
	private String url;
	
	public MusicSearch(String search) throws IOException {
		Document doc = Jsoup.connect("http://www.youtube.com/results")
	            .data("search_query", search)
	            .userAgent("Mozilla/5.0")
	            .get();
		Element video = doc.selectFirst(".yt-lockup-title > a[title]");
		url = "http://www.youtube.com" + video.attr("href");
	}
	
	public String getURL() {
		return url;
	}

}
