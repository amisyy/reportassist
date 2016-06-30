package chart;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.RenderingHints;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import motion.Motion;

import org.jfree.data.*;
import org.jfree.data.category.*;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.chart.*;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.*;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.category.LineRenderer3D;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.title.TextTitle;

import database.SQLop;

;

public class Chart {
	static final int PIC_LENGTH = 400;
	static final int PIC_WIDTH = 500;

	static final int SITE = 1;
	static final int YEAR_gov = 2;
	static final int YEAR_patent = 3;
	static final int YEAR_paper = 4;
	static final int YEAR_news = 5;
	static final int JOURNAL = 6;
	static final int PATENT_type = 7;
	static final int PATENT_applicant = 8;
	static final int NEWS_SOURCE = 9;
	static final int MOTION = 10;
	static final int  YEAR_comments= 11;
	static final int  SOURCE= 12;

	static final int VERTICAL = 0;
	static final int HORIZONTAL = 1;

	SQLop sqlop = new SQLop();
	String keyword = new String();
	Motion motion;

	public Chart(String kw, Motion motion) {
		keyword = kw;
		this.motion = motion;
		sqlop.initialize();
		// System.out.println(sqlop.countAllResult(keyword));
		//barChart(SITE, "site.jpg", VERTICAL);
		//lineChart(YEAR_gov, "year_gov.jpg");
		//lineChart(YEAR_paper, "year_paper.jpg");
		//lineChart(YEAR_patent, "year_patent.jpg");
		//lineChart(YEAR_news, "year_news.jpg");
		//barChart(NEWS_SOURCE, "news_source.jpg", HORIZONTAL);
		//barChart(JOURNAL, "journal.jpg", HORIZONTAL);
		//pieChart(PATENT_type, "patent_type.jpg");
		pieChart(SOURCE, "source.jpg");
		//barChart(PATENT_applicant, "patent_applicant.jpg", HORIZONTAL);
		lineChart(MOTION, "motion.jpg");
		lineChart(YEAR_comments, "year_comments.jpg");
		sqlop.close();
	}

	public void barChart(int type, String fileName, int orient) {
		CategoryDataset dataset = getSiteDataset(type);
		String head = "统计";
		if (type == SITE)
			head = "来源站点" + head;
		if (type == JOURNAL)
			head = "发表期刊" + head;
		if (type == PATENT_applicant)
			head = "授权单位" + head;
		if (type == NEWS_SOURCE)
			head = "报道网站" + head;
		JFreeChart chart = null;
		if (orient == VERTICAL)
			chart = ChartFactory.createBarChart3D(head, "来源", "数量", dataset,
					PlotOrientation.VERTICAL, false, true, false);
		else
			chart = ChartFactory.createBarChart3D(head, "来源", "数量", dataset,
					PlotOrientation.HORIZONTAL, false, true, false);
		// 以下部分为柱状图的美化
		TextTitle title = chart.getTitle();
		title.setFont(Fonts.title);// 标题字体
		title.setPaint(Colors.a1);
		CategoryPlot plot = chart.getCategoryPlot();// plot用于设置外部属性，如坐标轴
		plot.setBackgroundPaint(Colors.a5);
		plot.setDomainGridlinePaint(Colors.a3);
		plot.setRangeGridlinePaint(Colors.a3);
		plot.setOutlinePaint(Color.BLACK);
		CategoryAxis domainAxis = plot.getDomainAxis();// DomainAxis
		domainAxis.setLabelFont(Fonts.axis_lable);
		domainAxis.setTickLabelFont(Fonts.axis);
		domainAxis.setLabelPaint(Colors.a1);
		domainAxis.setTickLabelPaint(Colors.a2);
		ValueAxis rangeAxis = plot.getRangeAxis();// RangAxis
		rangeAxis.setLabelFont(Fonts.axis_lable);
		rangeAxis.setTickLabelFont(Fonts.axis);
		rangeAxis.setLabelPaint(Colors.a1);
		rangeAxis.setTickLabelPaint(Colors.a2);
		BarRenderer3D renderer = new BarRenderer3D();// renderer用于设置内部属性，如柱子颜色
		renderer.setSeriesPaint(0, Colors.a3);
		renderer.setBaseOutlinePaint(Colors.a4);
		renderer.setWallPaint(Colors.a5.darker());
		// 柱子上面的值
		renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		renderer.setBaseItemLabelsVisible(true);
		renderer.setBaseItemLabelPaint(Colors.a5);
		plot.setRenderer(renderer);

		FileOutputStream pic_out = null;
		try {
			pic_out = new FileOutputStream(".\\output\\" + fileName);
			ChartUtilities.writeChartAsJPEG(pic_out, 1.0f, chart, PIC_WIDTH,
					PIC_LENGTH, null);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pic_out.close();
			} catch (Exception e) {
			}
		}
	}

	private CategoryDataset getSiteDataset(int type) {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		HashMap<String, Integer> hashmap = sqlop.count(type, keyword);
		List<Map.Entry<String, Integer>> entry = new ArrayList<Map.Entry<String, Integer>>(
				hashmap.entrySet());
		for (Map.Entry<String, Integer> i : entry) {
			dataset.addValue(i.getValue().intValue(), "source", i.getKey());
		}
		return dataset;
	}

	public void lineChart(int type, String fileName) {
		DefaultCategoryDataset dataset = getYearDataset(type);
		JFreeChart chart;
		String head = "统计";
		if (type == YEAR_patent)
			head = "发布专利" + head;
		if (type == YEAR_gov)
			head = "政府公文" + head;
		if (type == YEAR_paper)
			head = "发表论文" + head;
		if (type == YEAR_news)
			head = "新闻报道" + head;
		if (type == YEAR_comments)
			head = "关注热度" + head;
		if (type == MOTION) {
			head = "媒体舆情分析" + head;
			chart = ChartFactory.createLineChart(head, "态度", "数量", dataset,
					PlotOrientation.VERTICAL, false, true, false);
		} else {
			chart = ChartFactory.createLineChart(head, "年份", "数量", dataset,
					PlotOrientation.VERTICAL, false, true, false);
		}
		TextTitle title = chart.getTitle();
		title.setFont(Fonts.title);// 标题字体
		title.setPaint(Colors.a1);
		CategoryPlot plot = chart.getCategoryPlot();// plot用于设置外部属性，如坐标轴
		plot.setBackgroundPaint(Colors.a5);
		plot.setDomainGridlinePaint(Colors.a3);
		plot.setRangeGridlinePaint(Colors.a3);
		plot.setOutlinePaint(Color.BLACK);
		CategoryAxis domainAxis = plot.getDomainAxis();// DomainAxis
		domainAxis.setLabelFont(Fonts.axis_lable);
		domainAxis.setTickLabelFont(Fonts.axis);
		domainAxis.setLabelPaint(Colors.a1);
		domainAxis.setTickLabelPaint(Colors.a2);
		ValueAxis rangeAxis = plot.getRangeAxis();// RangAxis
		rangeAxis.setLabelFont(Fonts.axis_lable);
		rangeAxis.setTickLabelFont(Fonts.axis);
		rangeAxis.setLabelPaint(Colors.a1);
		rangeAxis.setTickLabelPaint(Colors.a2);
		LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot
				.getRenderer(); // 获取折线对象
		renderer.setBaseShapesVisible(true);// 设置拐点是否可见/是否显示拐点
		renderer.setDrawOutlines(true);// 设置拐点不同用不同的形状
		renderer.setUseFillPaint(true);// 设置线条是否被显示填充颜色
		renderer.setBaseFillPaint(Colors.a3);// 设置拐点颜色
		renderer.setSeriesStroke(0, new BasicStroke(3F));// 设置折线加粗
		renderer.setSeriesPaint(0, Colors.a3);

		// renderer.setBaseItemLabelGenerator(new
		// StandardCategoryItemLabelGenerator());
		// renderer.setBaseItemLabelsVisible(true);
		// renderer.setBaseItemLabelPaint(Colors.a3);

		FileOutputStream pic_out = null;
		try {
			pic_out = new FileOutputStream(".\\output\\" + fileName);
			ChartUtilities.writeChartAsJPEG(pic_out, 1.0f, chart, PIC_WIDTH,
					PIC_LENGTH, null);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pic_out.close();
			} catch (Exception e) {
			}
		}
	}

	private DefaultCategoryDataset getYearDataset(int type) {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		HashMap<String, Integer> hashmap;
		if (type == MOTION) {
			hashmap =new HashMap();
			Motion t = motion;
			for (int i=0;i<=10;i++)
				hashmap.put(Integer.toString(i-5), (Integer)t.get_count()[i]);
		} else {
			hashmap = sqlop.count(type, keyword);
		}
		List<Map.Entry<String, Integer>> entry = new ArrayList<Map.Entry<String, Integer>>(
				hashmap.entrySet());
		Collections.sort(entry, new Comparator<Map.Entry<String, Integer>>() {
			public int compare(Map.Entry<String, Integer> o1,
					Map.Entry<String, Integer> o2) {
				return (o1.getKey()).toString().compareTo(o2.getKey());
			}
		});

		for (Map.Entry<String, Integer> i : entry) {
			dataset.addValue(i.getValue(), "year", i.getKey());
		}

		return dataset;
	}

	public void pieChart(int type, String fileName) {
		PieDataset dataset = getJournalDataset(type);
		JFreeChart chart = ChartFactory.createPieChart("", dataset, false,
				true, false);
		String head = "统计";
		if (type == JOURNAL)
			head = "发表期刊" + head;
		if (type == PATENT_type)
			head = "专利类型" + head;
		if (type == PATENT_applicant)
			head = "申请单位" + head;
		if (type == SOURCE)
			head = "关注主体" + head;
		TextTitle title = new TextTitle(head, Fonts.title);
		title.setPaint(Colors.a1);
		chart.setTitle(title);
		chart.setBorderVisible(false);
		// chart.getLegend().setItemFont(Fonts.axis_lable);// 下方图例说明
		PiePlot pieplot = (PiePlot) chart.getPlot();
		pieplot.setBackgroundPaint(Colors.a5);
		pieplot.setOutlinePaint(Color.BLACK);
		pieplot.setLabelFont(Fonts.axis_lable);
		pieplot.setLabelPaint(Colors.a1);
		pieplot.setNoDataMessage("No data available");// 没有数据的时候显示的内容
		pieplot.setNoDataMessagePaint(Color.red);// 设置无数据时的信息显示颜色
		pieplot.setBackgroundAlpha(0.9f);// 设置背景透明度
		pieplot.setForegroundAlpha(0.6f);// 设置前景透明度
		pieplot.setBaseSectionOutlinePaint(Color.white);// 指定饼图轮廓线的颜色
		pieplot.setBaseSectionPaint(Color.BLACK);
		// 指定显示的饼图上圆形(true)还椭圆形(false)
		pieplot.setCircular(false);
		pieplot.setNoDataMessageFont(Fonts.axis_lable);

		// ("{0}: ({1}，{2})")是生成的格式，
		// {0}表示数据名，{1}表示数据的值，{2}表示百分比。可以自定义。
		pieplot.setLabelGenerator((PieSectionLabelGenerator) new StandardPieSectionLabelGenerator(
				("{0}({1}):{2}"), NumberFormat.getNumberInstance(),
				new DecimalFormat("0.00%")));

		FileOutputStream pic_out = null;
		try {
			pic_out = new FileOutputStream(".\\output\\" + fileName);
			ChartUtilities.writeChartAsJPEG(pic_out, 1.0f, chart, PIC_WIDTH,
					PIC_LENGTH, null);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pic_out.close();
			} catch (Exception e) {
			}
		}
	}

	private PieDataset getJournalDataset(int type) {
		DefaultPieDataset dataset = new DefaultPieDataset();
		HashMap<String, Integer> hashmap;
		hashmap = sqlop.count(type, keyword);
		List<Map.Entry<String, Integer>> entry = new ArrayList<Map.Entry<String, Integer>>(
				hashmap.entrySet());
		Collections.sort(entry, new Comparator<Map.Entry<String, Integer>>() {
			public int compare(Map.Entry<String, Integer> o1,
					Map.Entry<String, Integer> o2) {
				return (o1.getKey()).toString().compareTo(o2.getKey());
			}
		});

		for (Map.Entry<String, Integer> i : entry) {
			dataset.setValue(i.getKey(), i.getValue());
		}

		return dataset;
	}

	private static class Fonts {
		static final Font title = new Font("微软雅黑", Font.BOLD, 20);
		static final Font axis_lable = new Font("微软雅黑", Font.BOLD, 15);
		static final Font axis = new Font("微软雅黑", Font.PLAIN, 10);
	}

	private static class Colors {
		static final Color a0 = Color.WHITE;
		static final Color a1 = new Color(20, 26, 55);
		static final Color a2 = new Color(40, 71, 92);
		// static final Color a3 = new Color(74, 108, 116);
		static final Color a4 = new Color(139, 166, 147);
		// static final Color a5 = new Color(240, 227, 192);
		static final Color a5 = Color.WHITE;
		static final Color a3 = new Color(78, 130, 190);
	}
}
