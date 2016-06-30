package us.codecraft.webmagic.processor.example;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.pipeline.*;

/**
 * @author code4crafter@gmail.com <br>
 */
public class Most implements PageProcessor {

	public static final String URL_LIST = "http://cn\\.bing\\.com/search\\?q。*";
	private Site site = Site.me()
			// .setDomain("blog.sina.com.cn")
			.setSleepTime(3000)
			.setUserAgent(
					"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_2) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31");

	@Override
	public void process(Page page) {
		// 列表页
		if (page.getUrl().regex(URL_LIST).match()) {
			page.addTargetRequests(page.getHtml()
					.xpath("//li[@class='b_algo']").links()
					.regex("http://www\\.most\\.gov\\.cn/.*").all());
			page.addTargetRequests(page.getHtml().xpath("//li[@class='b_pag']")
					.links().all());
			page.setSkip(true);
			// 文章页
		} else {
			String temp = page.getHtml()
					.xpath("div[@class='gray12 lh22']/allText()").toString();
			if (temp == null) {
				page.setSkip(true);
				return;
			}
			page.putField("time", temp.substring(3, 13));
			page.putField("title", page.getHtml().xpath("title/text()"));

			page.putField("content",
					page.getHtml().smartContent());

			page.putField("author",
					temp.substring(temp.indexOf("日 ") + 2, temp.length()).toString().replaceAll(" ", "").replaceAll("　", ""));
			page.putField("baseURL", page.getUrl());
			page.putField("type", "政府");
		}

	}

	@Override
	public Site getSite() {
		return site;
	}

	public static void main(String[] args) {
		Spider.create(new Most())
				.addUrl("http://www.most.gov.cn/kjbgz/200604/t20060417_32873.htm")//http://cn.bing.com/search?q=site%3awww.most.gov.cn+%2204专项%22+filetype%3ahtml
				.addPipeline(new ConsolePipeline())
				.run();
	}
}
