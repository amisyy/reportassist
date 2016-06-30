package main;

import javax.swing.AbstractCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JSplitPane;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JLayeredPane;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JSeparator;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.UIManager;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JScrollPane;
import javax.swing.JCheckBox;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;

import database.SQLop;
import NLP.NLP;

import javax.swing.Box;
import javax.swing.JTabbedPane;

import output.MakeReport;
import main.Window.ResultPanel.ResultTableFiller;
import chart.Chart;

public class ResultPanel1 extends JPanel {
	private JTable tableResult;
	private JTable tableAll;
	private StyledDocument styleModel;
	private JTextPane textPane;
	private JLabel text;
	private SQLop sqlop;
	private JPanel panel_resultList;
	private	JPanel panel_allTab; 
	JLabel label_chart11;
	JLabel label_chart12;
	JLabel label_chart21;
	JLabel label_chart22;
	JLabel label_chart31;
	JLabel label_chart32;
	JLabel label_chart41;
	JLabel label_chart42;
	JLabel label_chart51;

	private List<Map<String, String>> result;
	private List<Map<String, String>> resultByType;
	private List<Map<String, String>> resultAll;
	private int resultSize;
	private int resultAllSize;

	String keyword = null;
	SQLop database = new SQLop();

	public ResultPanel1() {
		
		iniStyleModel();
		setLayout(new BorderLayout(0, 0));
		
		JPanel panel_4 = new JPanel();
		add(panel_4, BorderLayout.CENTER);
		panel_4.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_2 = new JPanel();
		panel_4.add(panel_2, BorderLayout.NORTH);
		panel_2.setLayout(new BorderLayout(0, 0));

		JButton button_report = new JButton("生成报告");
		panel_2.add(button_report, BorderLayout.EAST);
		button_report.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0){
				new MakeReport(keyword, ".\\output\\report.html");
			}
		});
		JButton button_back = new JButton("返回搜索界面");
		button_back.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0){
				//UIswitch_back();//TODO
			}
		});
		panel_2.add(button_back, BorderLayout.WEST);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		panel_4.add(tabbedPane);
		
		JPanel panel_resultTab = new JPanel();
		tabbedPane.addTab("搜索结果", null, panel_resultTab, null);
		panel_resultTab.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_up = new JPanel();
		panel_up.setVisible(true);
		panel_resultTab.add(panel_up, BorderLayout.NORTH);
		panel_up.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel label = new JLabel("显示：");
		panel_up.add(label);
		
		JButton button_gov = new JButton("政府公文");
		panel_up.add(button_gov);
		
		JButton button_paper = new JButton("论文专利");
		panel_up.add(button_paper);
		
		JButton button_news = new JButton("新闻报道");
		panel_up.add(button_news);
		
		JPanel panel_charts = new JPanel();
		
		JPanel panel_chartsTab = new JPanel();
		panel_chartsTab.setLayout(new BorderLayout(0, 0));
		JScrollPane scroll_charts = new JScrollPane(panel_charts);
		panel_chartsTab.add(scroll_charts);
		tabbedPane.addTab("结果统计", null, panel_chartsTab, null);
		panel_charts.setLayout(new BoxLayout(panel_charts, BoxLayout.Y_AXIS));

		label_chart11 = new JLabel();
		label_chart12 = new JLabel();
		label_chart21 = new JLabel();
		label_chart22 = new JLabel();
		label_chart31 = new JLabel();
		label_chart32 = new JLabel();
		label_chart41 = new JLabel();
		label_chart42 = new JLabel();
		label_chart51 = new JLabel();
		
		JPanel panel_chart1 = new JPanel();
		panel_chart1.setLayout(new BoxLayout(panel_chart1, BoxLayout.X_AXIS));
		panel_charts.add(panel_chart1);
		
		panel_chart1.add(label_chart11);
		panel_chart1.add(label_chart12);
		
		JPanel panel_chart2 = new JPanel();
		panel_chart2.setLayout(new BoxLayout(panel_chart2, BoxLayout.X_AXIS));
		panel_charts.add(Box.createVerticalStrut(10));
		panel_charts.add(panel_chart2);
		
		panel_chart2.add(label_chart21);
		panel_chart2.add(label_chart22);
		
		JPanel panel_chart3 = new JPanel();
		panel_chart3.setLayout(new BoxLayout(panel_chart3, BoxLayout.X_AXIS));
		panel_charts.add(Box.createVerticalStrut(10));
		panel_charts.add(panel_chart3);
		
		panel_chart3.add(label_chart31);
		panel_chart3.add(label_chart32);
		
		JPanel panel_chart4 = new JPanel();
		panel_chart4.setLayout(new BoxLayout(panel_chart4, BoxLayout.X_AXIS));
		panel_charts.add(Box.createVerticalStrut(10));
		panel_charts.add(panel_chart4);
		
		panel_chart4.add(label_chart41);
		panel_chart4.add(label_chart42);

		JPanel panel_chart5 = new JPanel();
		panel_chart5.setLayout(new BoxLayout(panel_chart5, BoxLayout.X_AXIS));
		panel_charts.add(panel_chart5);
		
		panel_chart5.add(label_chart51);
		
		panel_resultList = new JPanel();
		panel_allTab = new JPanel();
		panel_allTab.setLayout(new BorderLayout(0, 0));
		tabbedPane.addTab("全体数据", null, panel_allTab, null);
		
		panel_resultTab.add(panel_resultList,BorderLayout.CENTER);
		
	}
	
	public void getResult(String keyword){
		//new Chart(keyword);
		this.keyword = keyword;
		sqlop = new SQLop();
		sqlop.initialize();
		result = sqlop.search(keyword,0);
		resultAll = sqlop.getAll();
		sqlop.close();
		resultSize = result.size();
		resultAllSize = resultAll.size();

		//搜索结果表格
		tableResult = new JTable(new DefaultTableModel(resultSize,1){
			@Override
			public boolean isCellEditable(int arg0, int arg1) {
				return false;
			}
		});
		tableResult.setRowHeight(80);
		tableResult.getTableHeader().setVisible(false);
		tableResult.setDefaultRenderer(Object.class, new ResultTableFiller());
		tableResult.addMouseListener(new java.awt.event.MouseAdapter() {
		    @Override
		    public void mouseClicked(java.awt.event.MouseEvent evt) {
		        int row = tableResult.rowAtPoint(evt.getPoint());
		        int col = tableResult.columnAtPoint(evt.getPoint());
		        if (row >= 0 && col >= 0) {
		        	String article = result.get(row).get("content");
		        	new DetailFrame(NLP.stringsummary(article), article).setVisible(true);;
		        }
		    }
		});
		
		panel_resultList.setLayout(new BorderLayout(0, 0));
		JScrollPane scrollPane = new JScrollPane(tableResult);
		panel_resultList.add(scrollPane, BorderLayout.CENTER);
		
		//统计图表
		label_chart11.setIcon(new ImageIcon(".\\output\\site.jpg"));
		label_chart12.setIcon(new ImageIcon(".\\output\\year_gov.jpg"));
		label_chart21.setIcon(new ImageIcon(".\\output\\journal.jpg"));
		label_chart22.setIcon(new ImageIcon(".\\output\\year_paper.jpg"));
		label_chart31.setIcon(new ImageIcon(".\\output\\news_source.jpg"));
		label_chart32.setIcon(new ImageIcon(".\\output\\year_news.jpg"));
		label_chart41.setIcon(new ImageIcon(".\\output\\patent_applicant.jpg"));
		label_chart42.setIcon(new ImageIcon(".\\output\\year_patent.jpg"));
		label_chart51.setIcon(new ImageIcon(".\\output\\patent_type.jpg"));
		
		//所有数据
		TableModel tableModel = new DefaultTableModel(resultAllSize,7){
			@Override
			public boolean isCellEditable(int arg0, int arg1) {
				return false;
			}
			
			public String getColumnName(int n){
				String columnNames[] = {
						"类型",
						"标题",
						"时间",
						"作者",
						"其他",
						"正文",
						"源地址"
				};
				return columnNames[n];			
			}
		};
		
		
		for(int row = 0; row < resultAllSize; row++){
			for(int column = 0; column < 7; column++){
				String string = null;
				switch (column) {
				case 0:
					string = resultAll.get(row).get("type");
					break;
				case 1:
					string = resultAll.get(row).get("title");
					break;
				case 2:
					string = resultAll.get(row).get("time");
					break;
				case 3:
					string = resultAll.get(row).get("author");
					break;
				case 4:
					string = resultAll.get(row).get("other");
					break;
				case 5:
					string = resultAll.get(row).get("content");
					break;
				case 6:
					string = resultAll.get(row).get("url");
					break;
				default:
					break;
				}
				tableModel.setValueAt(string, row, column);
			}
		}
		tableAll = new JTable(tableModel);
		tableAll.getColumnModel().getColumn(0).setPreferredWidth(3);
		//tableAll.getColumnModel().getColumn(1).setPreferredWidth(30);
		//tableAll.getColumnModel().getColumn(2).setPreferredWidth(20);
		//tableAll.getColumnModel().getColumn(3).setPreferredWidth(20);
		//tableAll.getColumnModel().getColumn(4).setPreferredWidth(20);
		//tableAll.getColumnModel().getColumn(5).setPreferredWidth(200);
		//tableAll.getColumnModel().getColumn(6).setPreferredWidth(30);
		//tableAll.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		JScrollPane scrollPane_all = new JScrollPane(tableAll);
		panel_allTab.add(scrollPane_all, BorderLayout.CENTER);
		
	}
	
	class ResultTableFiller implements TableCellRenderer{

		@Override
		public Component getTableCellRendererComponent(JTable arg0,
				Object arg1, boolean arg2, boolean arg3, int row, int column) {
			textPane = new JTextPane();
			StyledDocument sd = getNewStyledDocument();
			insertDoc(sd, result.get(row).get("type"), "STYLE_type");
			String title=result.get(row).get("title").toString();
			if(title.indexOf(keyword)!=-1){
				insertDoc(sd, "  " + title.substring(0, title.indexOf(keyword)) , "STYLE_title");
				insertDoc(sd, keyword, "STYLE_keyword");
				insertDoc(sd, title.substring(title.indexOf(keyword)+keyword.length(), title.length()) + "\n", "STYLE_title");
			}
			else
				insertDoc(sd, "  " + title + "\n", "STYLE_title");
			if(result.get(row).get("time") != null)
				insertDoc(sd, result.get(row).get("time").toString() + "\t", "STYLE_author");
			insertDoc(sd, result.get(row).get("author").toString() + "\n", "STYLE_author");
			insertDoc(sd, result.get(row).get("url").toString() + "\n", "STYLE_url");
			textPane.setStyledDocument(sd);
			return textPane;
		}
		
	}
	
	public StyledDocument getNewStyledDocument() {
		
		return new DefaultStyledDocument();
	}
	

	public StyledDocument insertDoc(StyledDocument styledDoc, String content,
			String currentStyle) {
		if(content == null) content = "";
		try {
			int length = styledDoc.getLength();
			styledDoc.insertString(length, content,
					styleModel.getStyle(currentStyle));
		} catch (BadLocationException e) {
			System.err.println("BadLocationException: " + e);
		}
		return styledDoc;
	}
	
	private void iniStyleModel(){
		styleModel = new DefaultStyledDocument();
		createStyle("STYLE_url", styleModel, 16, false, false, true, Color.GRAY, Color.WHITE, "Times New Roman");
		createStyle("STYLE_title", styleModel, 20, true, false, false, Color.BLACK, Color.WHITE, "黑体");
		createStyle("STYLE_abstract", styleModel, 16, false, false, false, Color.BLACK, Color.WHITE, "宋体");
		createStyle("STYLE_author", styleModel, 16, false, false, false, Color.BLACK, Color.WHITE, "楷体");
		createStyle("STYLE_type", styleModel, 20, false, false, false, Color.WHITE, Color.BLACK, "黑体");	
		createStyle("STYLE_keyword", styleModel, 20, true, false, false, Color.RED, Color.WHITE, "黑体");	
	}

	public void createStyle(String style, StyledDocument doc, int size,
			boolean bold, boolean italic, boolean underline, Color color, Color bcolor, String fontName) {
		Style sys = StyleContext.getDefaultStyleContext().getStyle(
				StyleContext.DEFAULT_STYLE);
		try {
			doc.removeStyle(style);
		} catch (Exception e) {
		} // 先删除这种Style,假使他存在

		Style s = doc.addStyle(style, sys); // 加入
		StyleConstants.setFontSize(s, size); // 大小
		StyleConstants.setBold(s, bold); // 粗体
		StyleConstants.setItalic(s, italic); // 斜体
		StyleConstants.setUnderline(s, underline); // 下划线
		StyleConstants.setForeground(s, color); // 颜色
		StyleConstants.setFontFamily(s, fontName); // 字体
		StyleConstants.setBackground(s, bcolor);//背景颜色
	}
	
}