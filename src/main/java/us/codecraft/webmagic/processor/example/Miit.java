package us.codecraft.webmagic.processor.example;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.pipeline.*;

/**
 * @author code4crafter@gmail.com <br>
 */
public class Miit implements PageProcessor {

	public static final String URL_LIST = "http://cn\\.bing\\.com/search\\?q。*";
	// public static final String
	// KEJI_WEB_SITE="http://www\\.most\\.gov\\.cn/\\.*";
	// public static final String KEJI_WEB_SITE="www.most.gov.cn";
	// public static final String KEJI_WEB_SITE="www.most.gov.cn";

	// public static final String URL_POST =
	// "http://blog\\.sina\\.com\\.cn/s/blog_\\w+\\.html";

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
					.regex("http://www\\.miit\\.gov\\.cn/n.*").all());
			page.addTargetRequests(page.getHtml().xpath("//li[@class='b_pag']")
					.links().all());
			page.setSkip(true);
			// 文章页
		} else {
			String temp = page.getHtml().xpath("body/tidyText()").toString();
			if (temp.indexOf("发布时间") != -1) {
				temp = temp.substring(temp.indexOf("发布时间") + 5,
						temp.indexOf("发布时间") + 15);
				page.putField("time", temp);
			} else {
				page.setSkip(true);
				return;
			}
			page.putField("title", page.getHtml().xpath("title/text()"));

			page.putField("content", page.getHtml().smartContent());

			temp = page.getHtml().xpath("body/tidyText()").toString();
			if (temp.indexOf("来源") != -1
					&& (temp.indexOf("】", temp.indexOf("来源") + 3) - (temp
							.indexOf("来源") + 3)) < 10) {
				temp = temp.substring(temp.indexOf("来源") + 3,
						temp.indexOf("】", temp.indexOf("来源") + 3));
				page.putField("author", temp);
			}

			page.putField("baseURL", page.getUrl());
			page.putField("type", "政府");
		}

	}

	@Override
	public Site getSite() {
		return site;
	}

	public static void main(String[] args) {
		Spider.create(new Miit())
				.addUrl("http://cn.bing.com/search?q=site%3Awww.miit.gov.cn+%22数控机床%22+filetype%3Ahtml")
				// "http://www.baidu.com/ns?word=机床"http://www.baidu.com/s?wd=机床
				// site:www.most.gov.cn
				.addPipeline(new ConsolePipeline()).thread(5)
				.run();
		System.out.println("finish!");
	}
}
