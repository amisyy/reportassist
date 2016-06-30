package us.codecraft.webmagic.processor.example;

import java.util.List;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.pipeline.*;
import us.codecraft.webmagic.selector.JsonPathSelector;
import us.codecraft.webmagic.selector.Selectable;

/**
 * @author code4crafter@gmail.com <br>
 */
public class tencentComment implements PageProcessor {

	public static final String URL_LIST = "https://www\\.sogou\\.com/sogou\\?site=news\\.qq\\.com&query=。*";
	public static final String URL_comment = "http://coral\\.qq\\.com.*";
	public String keyword = null;
	private Site site = Site.me()
			// .setDomain("blog.sina.com.cn")
			.setSleepTime(3000)
			.setUserAgent(
					"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_2) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31");

	public tencentComment(String key) {
		keyword = key;
	}

	@Override
	public void process(Page page) {
		// 列表页
		if (page.getUrl().regex(URL_LIST).match()) {
			page.addTargetRequests(page.getHtml().xpath("//div[@id='main']")
					.links().regex("http://news\\.qq\\.com/a.*").all());
			page.addTargetRequests(page.getHtml()
					.xpath("//div[@id='pagebar_container']").links().all());
			page.setSkip(true);
			// js评论页
		} else if (page.getUrl().regex(URL_comment).match()) {
			String text = page.getRawText();
			if (text.charAt(0) != '{')
				text = text.substring(text.indexOf("{"), text.length() - 1);
			try {
				List<String> ids = new JsonPathSelector(
						"$.data.commentid[*].content").selectList(text);
				page.putField("comment", ids);
				ids = new JsonPathSelector("$.data.commentid[*].timeDifference")
						.selectList(text);
				page.putField("times", ids);
				page.putField("other", keyword);
				page.putField("baseURL", page.getUrl());
			} catch (Exception e) {
				System.out.println(e);
			}
			// 文章页
		} else {
			String content = "";
			List<String> temps = page.getHtml()
					.xpath("//div[@id='ArticleCnt']/p/allText()").all();
			if (temps.size() == 0) {
				temps = page.getHtml()
						.xpath("//div[@id='Cnt-Main-Article-QQ']/p/allText()")
						.all();
			}
			for (String temp : temps) {
				content = content + temp;
			}
			page.putField("title", page.getHtml().xpath("title/text()"));
			String time=page.getUrl().toString();
			time=time.substring(time.indexOf("a/")+2, time.indexOf("/", time.indexOf("a/")+2));
			time=time.substring(0, 4)+"-"+time.substring(4, 6)+"-"+time.substring(6, 8);
			
			page.putField("time", time);
			page.putField("content", content);

			// temp = page.getHtml().xpath("body/tidyText()").toString();
			// if (temp.indexOf("来源") != -1
			// && (temp.indexOf("】", temp.indexOf("来源") + 3) - (temp
			// .indexOf("来源") + 3)) < 10) {
			// temp = temp.substring(temp.indexOf("来源") + 3,
			// temp.indexOf("】", temp.indexOf("来源") + 3));
			// page.putField("author", temp);
			// }

			page.putField("baseURL", page.getUrl());
			page.putField("type", "媒体");
			// 添加评论的链接
			String aa = page.getHtml().regex("cmt_id = (.*)").toString();
			if (aa == null)
				return;
			aa = aa.substring(0, aa.indexOf(";"));
			page.addTargetRequest("http://coral.qq.com/article/" + aa
					+ "/comment?commentid=0&reqnum=50&tag=");

		}

	}

	@Override
	public Site getSite() {
		return site;
	}

	public static void main(String[] args) {
		Spider.create(new tencentComment("公车改革"))
				.addUrl("https://www.sogou.com/sogou?site=news.qq.com&query=%B9%AB%B3%B5%B8%C4%B8%EF&pid=sogou-wsse-b58ac8403eb9cf17-0004")
				//.addUrl("http://news.qq.com/a/20101205/000408.htm")
				.addPipeline(new MysqlPipeline())
				.addPipeline(new ConsolePipeline()).run();
	}
}
