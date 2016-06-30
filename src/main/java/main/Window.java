package main;

import java.awt.EventQueue;

import javax.swing.JFrame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Window.Type;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;

import java.awt.Component;

import javax.swing.Box;

import java.awt.FlowLayout;

import javax.swing.BoxLayout;

import java.awt.GridLayout;
import java.awt.CardLayout;
import java.awt.GridBagConstraints;

import javax.swing.JCheckBox;

import AutoComplete.AutoCompleteComponet;
import AutoComplete.HistoricRecords;
import NLP.NLP;
import crawl.Crawler;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.EventObject;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.JProgressBar;
import javax.swing.event.CellEditorListener;
import javax.swing.plaf.IconUIResource;
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

import org.omg.CORBA.PRIVATE_MEMBER;

import output.Information;
import output.MakeReport;
import chart.Chart;
import main.ResultPanel1.ResultTableFiller;
import motion.Motion;
import database.SQLop;
import format.Fonts;

public class Window {
	

	private JFrame frame;
	private JTextField textField;
	private JButton button;
	private JPanel panel = new JPanel();
	private Component verticalGlue;
	private Component verticalGlue_1;
	private JPanel panel_1;
	private JPanel panel_2;
	private JCheckBox ck1;
	private JCheckBox ck2;
	private JCheckBox ck3;
	private JPanel panel_3;
	private JPanel panel_4;
	private JCheckBox ck4;
	private JCheckBox ck5;
	private JCheckBox ck6;
	private JPanel panel_5;
	private Component verticalGlue_2;
	private Component verticalGlue_3;
	private Component verticalGlue_4;
	private Component verticalGlue_5;
	private JProgressBar progressBar;
	private JButton button_1;
	private JPanel searchPanel;
	private ResultPanel resultPanel;

	private Crawler crawler;

	private boolean isRunning = false;
	public String keyword = null;
	private JLabel lblNewLabel;
	private JPanel panel_logo;
	private JLabel lable_logo;
	private Component verticalStrut;
	private JLabel label_1;
	private JPanel panel_6;

	private HistoricRecords history;
	private AutoCompleteComponet auto;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Window window = new Window();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Window() {

		//控制台信息输出到out.txt
	    /*File f=new File("out.txt");
	    try {
			f.createNewFile();
			FileOutputStream fileOutputStream = new FileOutputStream(f);
			PrintStream printStream = new PrintStream(fileOutputStream);
			System.setOut(printStream);
		} catch (IOException e) {
			e.printStackTrace();
		}*/
	    
		initialize();
		actionListner();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		//设置窗口大小
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int screenHeight = dimension.height;
		int glueHeight = screenHeight / 2 - 100;

		//主窗口
		frame = new JFrame();
		frame.setTitle("舆论监测系统");//窗口标题
		frame.setBackground(Color.WHITE);//背景颜色
		frame.setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);// 最大化窗口
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//退出按钮动作
		frame.getContentPane().setLayout(
				new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));

		//新架构总面板
		JPanel panel_frame = new JPanel();
		panel_frame.setLayout(new BorderLayout(0, 0));
		frame.getContentPane().add(panel_frame);
		
		
		//页面上部面板
		JPanel panel_north = new JPanel();
		panel_north.setLayout(new BoxLayout(panel_north, BoxLayout.Y_AXIS));
		panel_frame.add(panel_north, BorderLayout.NORTH);
		
		//标题
		JPanel panel_title = new JPanel();
		panel_title.setLayout(new BorderLayout(0, 0));
		panel_north.add(panel_title);		
		JLabel label_title = new JLabel("舆论监测系统", JLabel.CENTER);
		label_title.setFont(Fonts.title);
		panel_title.add(label_title,BorderLayout.CENTER);
		
		//搜索栏
		JPanel panel_search = new JPanel();
		panel_search.setLayout(new BorderLayout(0, 0));
		panel_north.add(panel_search);
		
		JPanel panel_searchbar = new JPanel();
		panel_searchbar.setLayout(new BoxLayout(panel_searchbar, BoxLayout.X_AXIS));
		panel_search.add(panel_searchbar,BorderLayout.EAST);
		
		final JTextField textField_searchBar = new JTextField("公车改革");
		textField_searchBar.setFont(Fonts.searchBar);
		textField_searchBar.setToolTipText("请键入您想要搜索的关键词");
		textField_searchBar.setColumns(30);
		panel_searchbar.add(textField_searchBar);
		
		//TODO
		JProgressBar progressBar = new JProgressBar();
		progressBar.setStringPainted(true);
		progressBar.setString("正在搜索...");
		progressBar.setFont(new Font("宋体", Font.PLAIN, 15));
		progressBar.setVisible(false);
		progressBar.setIndeterminate(true);

		final JButton button_search = new JButton("搜索");
		button_search.setFont(Fonts.searchButton);
		panel_searchbar.add(button_search);
		JButton button_show = new JButton("显示结果");
		button_show.setFont(Fonts.searchButton);
		panel_searchbar.add(button_show);
		button_search.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				keyword = textField_searchBar.getText();
				if (keyword.trim().equals("")) {
					JOptionPane.showConfirmDialog(frame, "请在文本框中输入关键词", "提示",
							JOptionPane.CLOSED_OPTION,
							JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				boolean[] options = {true,true,true,true,false,false};
				if (isRunning) {
					crawler.stop();
				} else {
					crawler = new Crawler(keyword, options);
				}
				stateChange(button_search, textField_searchBar);
			}
		});
		button_show.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				keyword = textField_searchBar.getText();
				if (keyword.trim().equals("")) {
					JOptionPane.showConfirmDialog(frame, "请在文本框中输入关键词", "提示",
							JOptionPane.CLOSED_OPTION,
							JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				resultPanel.getResult(keyword);
				UIswitch_search();
			}
		});
		
		//页面中央面板
		JPanel panel_center = new JPanel();
		panel_center.setLayout(new BorderLayout(0, 0));
		panel_frame.add(panel_center, BorderLayout.CENTER);
		
		JTabbedPane tabPanel = new JTabbedPane(JTabbedPane.TOP);
		//TODO panel_center.add(tabPanel);
		
		//第一页
		JPanel panel_tab1 = new JPanel();
		panel_tab1.setLayout(new BorderLayout(0, 0));
		tabPanel.addTab("舆情分析", null, panel_tab1, null);
		
		JPanel panel_chartsCenter = new JPanel();
		panel_chartsCenter.setLayout(new BorderLayout(0, 0));
		JScrollPane scroll_tab1 = new JScrollPane(panel_chartsCenter);
		panel_tab1.add(scroll_tab1,BorderLayout.CENTER);
		JPanel panel_charts = new JPanel();
		panel_charts.setLayout(new BoxLayout(panel_charts, BoxLayout.Y_AXIS));
		panel_chartsCenter.add(panel_charts,BorderLayout.CENTER);

		JPanel panel_chart_row0 = new JPanel();
		panel_chart_row0.setLayout(new BoxLayout(panel_chart_row0, BoxLayout.Y_AXIS));
		panel_charts.add(panel_chart_row0);

		JLabel label_chart00 = new JLabel("舆论评分");
		label_chart00.setFont(Fonts.opinion_title);
		JLabel label_chart01 = new JLabel();
		label_chart01.setFont(Fonts.opinion_index);
		panel_chart_row0.add(label_chart00);
		panel_chart_row0.add(label_chart01);
		
		JLabel label_chart11 = new JLabel();
		JLabel label_chart12 = new JLabel();
		JLabel label_chart21 = new JLabel();
		JLabel label_chart22 = new JLabel();
		JLabel label_chart31 = new JLabel();
		JLabel label_chart32 = new JLabel();
		JLabel label_chart41 = new JLabel();
		JLabel label_chart42 = new JLabel();
		JLabel label_chart51 = new JLabel();

		
		JPanel panel_chart1 = new JPanel();
		panel_chart1
				.setLayout(new BoxLayout(panel_chart1, BoxLayout.X_AXIS));
		panel_charts.add(panel_chart1);

		panel_chart1.add(label_chart11);
		panel_chart1.add(label_chart12);

		JPanel panel_chart2 = new JPanel();
		panel_chart2
				.setLayout(new BoxLayout(panel_chart2, BoxLayout.X_AXIS));
		panel_charts.add(Box.createVerticalStrut(10));
		panel_charts.add(panel_chart2);

		panel_chart2.add(label_chart21);
		//panel_chart2.add(label_chart22);

		JPanel panel_chart3 = new JPanel();
		panel_chart3
				.setLayout(new BoxLayout(panel_chart3, BoxLayout.X_AXIS));
		panel_charts.add(Box.createVerticalStrut(10));
		panel_charts.add(panel_chart3);

		panel_chart3.add(label_chart31);
		panel_chart3.add(label_chart32);
		
		
		
		//搜索面板
		searchPanel = new JPanel();
		frame.getContentPane().add(searchPanel);
		searchPanel.setVisible(false);
		searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.Y_AXIS));

		verticalGlue = Box.createVerticalGlue();
		searchPanel.add(verticalGlue);

		panel_logo = new JPanel();
		searchPanel.add(panel_logo);
		ImageIcon icon = new ImageIcon(".\\resource\\logo.png");
		// icon.setImage(icon.getImage().getScaledInstance(icon.getIconWidth(),icon.getIconHeight(),
		// Image.SCALE_DEFAULT));
		panel_logo.setLayout(new BorderLayout(0, 0));

		panel_6 = new JPanel();
		panel_logo.add(panel_6, BorderLayout.SOUTH);
		panel_6.setLayout(new BorderLayout(0, 0));

		lable_logo = new JLabel();
		panel_6.add(lable_logo, BorderLayout.CENTER);
		lable_logo.setIcon(icon);
		lable_logo.setBounds(0, 0, icon.getIconWidth(), icon.getIconHeight());
		lable_logo.setHorizontalAlignment(JLabel.CENTER);

		label_1 = new JLabel("智能信息搜索管理系统", JLabel.CENTER);
		label_1.setFont(Fonts.title);
		panel_6.add(label_1, BorderLayout.SOUTH);
		searchPanel.add(panel);

		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		panel_1 = new JPanel();
		panel.add(panel_1);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.Y_AXIS));

		verticalGlue_2 = Box.createVerticalGlue();
		panel_1.add(verticalGlue_2);

		panel_5 = new JPanel();
		panel_1.add(panel_5);

		textField = new JTextField("");
		textField.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		panel_5.add(textField);
		textField.setToolTipText("请键入您想要搜索的关键词");
		textField.setColumns(30);

		auto = new AutoCompleteComponet();
		history = new HistoricRecords();
		auto.updateHistory(this.getItems());
		auto.setupAutoComplete(textField);

		button = new JButton("搜索");
		button.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		panel_5.add(button);

		button_1 = new JButton("显示结果");
		button_1.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		panel_5.add(button_1);

		verticalGlue_3 = Box.createVerticalGlue();
		panel_1.add(verticalGlue_3);

		panel_2 = new JPanel();
		panel.add(panel_2);
		panel_2.setLayout(new BoxLayout(panel_2, BoxLayout.Y_AXIS));

		verticalGlue_4 = Box.createVerticalGlue();
		panel_2.add(verticalGlue_4);

		panel_3 = new JPanel();
		panel_2.add(panel_3);

		ck1 = new JCheckBox("科技部", false);
		panel_3.add(ck1);

		ck2 = new JCheckBox("工信部", false);
		panel_3.add(ck2);

		ck3 = new JCheckBox("发改委", false);
		panel_3.add(ck3);

		panel_4 = new JPanel();
		panel_2.add(panel_4);

		//ck4 = new JCheckBox("论文", true);
		ck4 = new JCheckBox("评论", true);
		panel_4.add(ck4);

		ck5 = new JCheckBox("专利", false);
		panel_4.add(ck5);

		ck6 = new JCheckBox("新闻", false);
		panel_4.add(ck6);

		verticalGlue_5 = Box.createVerticalGlue();
		panel_2.add(verticalGlue_5);
		verticalGlue_1 = Box.createVerticalGlue();
		searchPanel.add(verticalGlue_1);

		verticalStrut = Box.createVerticalStrut(20);
		searchPanel.add(verticalStrut);

		JPanel panelSign = new JPanel();
		panelSign.setLayout(new BorderLayout());
		searchPanel.add(panelSign);
		lblNewLabel = new JLabel("北航分布与移动计算实验室 v0.2       ", JLabel.RIGHT);
		panelSign.add(lblNewLabel, BorderLayout.EAST);

		resultPanel = new ResultPanel();
		panel_center.add(resultPanel,BorderLayout.CENTER);
		resultPanel.setVisible(true);
	}

	//for auto complete
	public ArrayList<String> getItems() {
		ArrayList<String> items = new ArrayList<String>();
		Map map = auto.getStationMap(history.getHistory());
		Iterator iterator = map.keySet().iterator();
		while (iterator.hasNext()) {
			String stationName = iterator.next().toString();
			items.add(stationName);
		}
		return items;
	}

	private void actionListner() {

		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				keyword = textField.getText();
				if (keyword.trim().equals("")) {
					JOptionPane.showConfirmDialog(frame, "请在文本框中输入关键词", "提示",
							JOptionPane.CLOSED_OPTION,
							JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				boolean[] options = new boolean[6];
				options[0] = ck1.isSelected();
				options[1] = ck2.isSelected();
				options[2] = ck3.isSelected();
				options[3] = ck4.isSelected();
				//options[4] = ck5.isSelected();
				options[5] = ck6.isSelected();
				if (isRunning) {
					crawler.stop();
				} else {
					crawler = new Crawler(keyword, options);
				}
				//TODO stateChange();
			}
		});

		button_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				keyword = textField.getText();
				if (keyword.trim().equals("")) {
					JOptionPane.showConfirmDialog(frame, "请在文本框中输入关键词", "提示",
							JOptionPane.CLOSED_OPTION,
							JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				resultPanel.getResult(keyword);
				UIswitch_search();
			}
		});
	}

	public void UIswitch_search() {
		searchPanel.setVisible(false);
		resultPanel.validate();
		resultPanel.setVisible(true);
	}

	public void UIswitch_back() {
		resultPanel.setVisible(false);
		searchPanel.setVisible(true);
	}

	private void stateChange(JButton button, JTextField textField) {

		isRunning = !isRunning;
		if (isRunning) {
			button.setText("停止");
			//textField.setVisible(false);
			//progressBar.setString("正在搜索: " + keyword);
			//progressBar.setVisible(true);

		} else {
			button.setText("搜索");
			//progressBar.setVisible(false);
			//textField.setVisible(true);

		}
	}

	public class ResultPanel extends JPanel implements ActionListener {
		private JTable table_result;
		private JTable table_all;
		private StyledDocument styleModel;
		private JTextPane textPane;
		private JLabel text;
		private SQLop sqlop;
		private JPanel panel_resultList;
		private JPanel panel_allTab;
		JLabel label_chart00;
		JLabel label_chart021;
		JLabel label_chart023;
		JLabel label_chart025;
		JLabel label_chart027;
		JLabel label_chart031;
		JLabel label_chart033;
		JLabel label_chart035;
		JLabel label_chart037;
		JLabel label_chart11;
		JLabel label_chart12;
		JLabel label_chart21;
		JLabel label_chart22;
		JLabel label_chart31;
		JLabel label_chart32;
		JLabel label_chart41;
		JLabel label_chart42;
		JLabel label_chart51;

		private JButton button_gov;
		private JButton button_news;
		private JButton button_pa;

		private DefaultTableModel tableModel;
		private JPopupMenu menu;
		private JMenuItem item;
		int count = 0;// judge the table item be clicked before

		private List<Map<String, String>> result;
		private List<Map<String, String>> resultAll;
		private int resultSize;
		private int resultAllSize;

		String keyword = null;

		// SQLop database = new SQLop();

		//初始化，加载界面
		public ResultPanel() {

			iniStyleModel();
			setLayout(new BorderLayout(0, 0));

			JPanel panel_overall = new JPanel();
			add(panel_overall, BorderLayout.CENTER);
			panel_overall.setLayout(new BorderLayout(0, 0));

			/*JPanel panel_2 = new JPanel();
			//TODO panel_4.add(panel_2, BorderLayout.NORTH);
			panel_2.setLayout(new BorderLayout(0, 0));

			JPanel panel_2_1 = new JPanel();
			JButton button_history = new JButton("保存搜索记录");
			panel_2_1.add(button_history);
			button_history.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
					JOptionPane.showConfirmDialog(frame, "操作完成！", "完成",
							JOptionPane.CLOSED_OPTION,
							JOptionPane.INFORMATION_MESSAGE);
					SimpleDateFormat df = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss");// 设置日期格式
					history.addHistory(keyword + " " + df.format(new Date()));
					auto.updateHistory(getItems());
				}
			});

			JButton button_report = new JButton("生成报告");
			panel_2_1.add(button_report);
			button_report.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
					new MakeReport(keyword, 
							".\\output\\report.html");
					JOptionPane.showConfirmDialog(frame,
							"操作完成！\n存储在.\\output\\report.html文件中", "完成",
							JOptionPane.CLOSED_OPTION,
							JOptionPane.INFORMATION_MESSAGE);
				}
			});

			panel_2.add(panel_2_1, BorderLayout.EAST);

			JButton button_back = new JButton("返回搜索界面");
			button_back.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
					UIswitch_back();// TODO
					
				}
			});
			JPanel panel_2_2 = new JPanel();
			panel_2_2.add(button_back);
			panel_2.add(panel_2_2, BorderLayout.WEST);
			*/

			JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
			panel_overall.add(tabbedPane,BorderLayout.CENTER);

			JPanel panel_resultTab = new JPanel();
			tabbedPane.addTab("搜索结果", null, panel_resultTab, null);
			panel_resultTab.setLayout(new BorderLayout(0, 0));
			panel_resultList = new JPanel();
			panel_resultTab.add(panel_resultList);
			
			JPanel panel_chartsTab = new JPanel();
			panel_chartsTab.setLayout(new BorderLayout(0, 0));
			tabbedPane.addTab("结果统计", null, panel_chartsTab, null);
			JPanel panel_charts = new JPanel();
			panel_charts.setLayout(new BoxLayout(panel_charts, BoxLayout.Y_AXIS));
			JPanel panel_charts_out = new JPanel();
			panel_charts_out.setLayout(new BorderLayout(0,0));
			panel_charts_out.add(panel_charts,BorderLayout.CENTER);
			JScrollPane scroll_charts = new JScrollPane(panel_charts_out);
			panel_chartsTab.add(scroll_charts, BorderLayout.CENTER);

			label_chart11 = new JLabel();
			label_chart12 = new JLabel();
			label_chart21 = new JLabel();
			label_chart22 = new JLabel();
			label_chart31 = new JLabel();
			label_chart32 = new JLabel();
			label_chart41 = new JLabel();
			label_chart42 = new JLabel();
			label_chart51 = new JLabel();

			JPanel panel_chart0 = new JPanel();
			panel_chart0.setLayout(new BorderLayout(0,0));
			panel_charts.add(panel_chart0);
			JPanel panel_chart0_center = new JPanel();
			panel_chart0_center.setLayout(new BoxLayout(panel_chart0_center, BoxLayout.Y_AXIS));
			panel_chart0.add(panel_chart0_center, BorderLayout.CENTER);

			label_chart00 = new JLabel("舆论指数", JLabel.CENTER);
			label_chart00.setFont(Fonts.opinion_title);
			JLabel label_chart01 = new JLabel("舆论指数范围为-5到5，-5表示极差，5表示极好，0表示中立",JLabel.CENTER);
			label_chart01.setFont(Fonts.normal);
			JPanel panel_chart02 = new JPanel();
			panel_chart02.setLayout(new BoxLayout(panel_chart02, BoxLayout.X_AXIS));
			JLabel label_chart020 = new JLabel("    媒体：");
			label_chart020.setFont(Fonts.normal);
			label_chart021 = new JLabel();
			label_chart021.setFont(Fonts.opinion_index);
			JLabel label_chart022 = new JLabel("    公众：");
			label_chart022.setFont(Fonts.normal);
			label_chart023 = new JLabel();
			label_chart023.setFont(Fonts.opinion_index);
			JLabel label_chart024 = new JLabel("    政府：");
			label_chart024.setFont(Fonts.normal);
			label_chart025 = new JLabel();
			label_chart025.setFont(Fonts.opinion_index);
			JLabel label_chart026 = new JLabel("全网：");
			label_chart026.setFont(Fonts.normal);
			label_chart027 = new JLabel();
			label_chart027.setFont(Fonts.opinion_index);
			panel_chart02.add(label_chart026);
			panel_chart02.add(label_chart027);
			panel_chart02.add(label_chart020);
			panel_chart02.add(label_chart021);
			panel_chart02.add(label_chart022);
			panel_chart02.add(label_chart023);
			panel_chart02.add(label_chart024);
			panel_chart02.add(label_chart025);
			
			JPanel panel_chart03 = new JPanel();
			panel_chart03.setLayout(new BoxLayout(panel_chart03, BoxLayout.X_AXIS));
			JLabel label_chart030 = new JLabel("媒体：");
			label_chart030.setFont(Fonts.normal);
			label_chart031 = new JLabel();
			label_chart031.setFont(Fonts.keyword);
			JLabel label_chart032 = new JLabel("公众：");
			label_chart032.setFont(Fonts.normal);
			label_chart033 = new JLabel();
			label_chart033.setFont(Fonts.keyword);
			JLabel label_chart034 = new JLabel("政府：");
			label_chart034.setFont(Fonts.normal);
			label_chart035 = new JLabel();
			label_chart035.setFont(Fonts.keyword);
			JLabel label_chart036 = new JLabel("峰值年度");
			label_chart036.setFont(Fonts.normal);
			label_chart037 = new JLabel();
			label_chart037.setFont(Fonts.keyword);
			/*panel_chart03.add(label_chart030);
			panel_chart03.add(label_chart031);
			panel_chart03.add(label_chart032);
			panel_chart03.add(label_chart033);
			panel_chart03.add(label_chart034);
			panel_chart03.add(label_chart035);
			panel_chart03.add(label_chart036);
			panel_chart03.add(label_chart037);*/
			
			JLabel label_chart04 = new JLabel("关键词", JLabel.CENTER);
			label_chart04.setFont(Fonts.opinion_title);
			
			panel_chart0_center.add(label_chart00);
			panel_chart0_center.add(label_chart01);
			panel_chart0_center.add(panel_chart02);
			panel_chart0_center.add(label_chart04);
			//panel_chart0_center.add(panel_chart03);
			panel_chart0_center.add(label_chart030);
			panel_chart0_center.add(label_chart031);
			panel_chart0_center.add(label_chart032);
			panel_chart0_center.add(label_chart033);
			panel_chart0_center.add(label_chart034);
			panel_chart0_center.add(label_chart035);
			panel_chart0_center.add(label_chart036);
			panel_chart0_center.add(label_chart037);
			
			JPanel panel_chart1 = new JPanel();
			panel_chart1
					.setLayout(new BoxLayout(panel_chart1, BoxLayout.X_AXIS));
			panel_charts.add(panel_chart1);

			panel_chart1.add(label_chart11);
			panel_chart1.add(label_chart12);

			JPanel panel_chart2 = new JPanel();
			panel_chart2
					.setLayout(new BoxLayout(panel_chart2, BoxLayout.X_AXIS));
			//panel_charts.add(Box.createVerticalStrut(10));
			panel_charts.add(panel_chart2);

			panel_chart2.add(label_chart21);
			panel_chart2.add(label_chart22);

			JPanel panel_chart3 = new JPanel();
			panel_chart3
					.setLayout(new BoxLayout(panel_chart3, BoxLayout.X_AXIS));
			//panel_charts.add(Box.createVerticalStrut(10));
			//panel_charts.add(panel_chart3);

			panel_chart3.add(label_chart31);
			panel_chart3.add(label_chart32);
			



			//筛选选项
			JPanel panel_up = new JPanel();
			panel_up.setVisible(false);//筛选部分不显示
			panel_resultTab.add(panel_up, BorderLayout.NORTH);
			panel_up.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			JLabel label = new JLabel("筛选：");
			panel_up.add(label);
			button_gov = new JButton("政府公文");
			panel_up.add(button_gov);
			button_pa = new JButton("论文专利");
			panel_up.add(button_pa);
			button_news = new JButton("新闻报道");
			panel_up.add(button_news);


			panel_allTab = new JPanel();
			panel_allTab.setLayout(new BorderLayout(0, 0));
			tabbedPane.addTab("全体数据", null, panel_allTab, null);


			button_gov.addActionListener(this);
			button_news.addActionListener(this);
			button_pa.addActionListener(this);
		}

		//调整表格行高
		private void updateRowHeights() {
			for (int row = 0; row < table_result.getRowCount(); row++) {
				int rowHeight = table_result.getRowHeight();

				for (int column = 0; column < table_result.getColumnCount(); column++) {
					Component comp = table_result.prepareRenderer(
							table_result.getCellRenderer(row, column), row,
							column);
					rowHeight = Math.max(rowHeight,
							comp.getPreferredSize().height);
				}

				table_result.setRowHeight(row, rowHeight);
			}
		}

		
		class MyTableModel extends DefaultTableModel{
			public MyTableModel(int row, int column){
				super(row, column);
			}

			public boolean isCellEditable(int row, int column){
				return false;
			}
		}
		
		//建立表格，设置属性，并添加进面板，但在这里并不填充内容
		private void getTableResult(String keyword, int type) {
			sqlop = new SQLop();
			sqlop.initialize();
			result = sqlop.search(keyword, type);
			sqlop.close();

			resultSize = result.size();
			table_result = new JTable();
			MyTableModel model = new MyTableModel(resultSize,1);
			table_result.setModel(model);
			table_result.setEnabled(true);
			table_result.getTableHeader().setVisible(false);
			table_result.setDefaultRenderer(Object.class,new ResultTableFiller());

			this.updateRowHeights();
			table_result.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(java.awt.event.MouseEvent evt) {
					int row = table_result.rowAtPoint(evt.getPoint());
					int col = table_result.columnAtPoint(evt.getPoint());
					if (row >= 0 && col >= 0) {
						String url = result.get(row).get("url");
						// new DetailFrame(NLP.stringsummary(article), article)
						// .setVisible(true);
						try {
							java.net.URI uri = java.net.URI.create(url);
							// 获取当前系统桌面扩展
							java.awt.Desktop dp = java.awt.Desktop.getDesktop();
							// 判断系统桌面是否支持要执行的功能
							if (dp.isSupported(java.awt.Desktop.Action.BROWSE)) {
								dp.browse(uri);// 获取系统默认浏览器打开链接
							}
						} catch (java.io.IOException e) {
							// 此为无法获取系统默认浏览器
							e.printStackTrace();
							String article = result.get(row).get("content");
							new DetailFrame(NLP.stringsummary(article), article)
									.setVisible(true);
						}
					}
				}
			});

			panel_resultList.setLayout(new BorderLayout(0, 0));
			JScrollPane scrollPane = new JScrollPane(table_result);
			panel_resultList.add(scrollPane, BorderLayout.CENTER);
		}

		//加载关键词相关信息（主要）
		public void getResult(String keyword) {
			Motion motion = new Motion(keyword);
			new Chart(keyword, motion);
			this.keyword = keyword;
			sqlop = new SQLop();
			sqlop.initialize();
			resultAll = sqlop.getAll();
			List<Map<String, String>> result_media = sqlop.search(keyword, 3);
			List<Map<String, String>> result_public = sqlop.search(keyword, 4);
			List<Map<String, String>> result_gov = sqlop.search(keyword, 0);
			List<Map<String, String>> result_hottest_year = sqlop.search(keyword, 6);
			String hottest_year = sqlop.hottestYear(keyword);
			sqlop.close();
			
			output.Information info = Information.getInstance();
			
			resultAllSize = resultAll.size();
			
			java.text.DecimalFormat df =new java.text.DecimalFormat("0.00");
			String tmp = df.format(motion.get_aver_media());
			label_chart021.setText(tmp);
			info.setMedia_attitude(tmp);
			tmp = df.format(motion.get_aver_public());
			label_chart023.setText(tmp);
			info.setPublic_attitude(tmp);
			tmp = df.format(motion.get_aver_gov());
			label_chart025.setText(tmp);
			info.setGov_attitude(tmp);
			tmp = df.format(motion.get_aver_total());
			label_chart027.setText(tmp);
			info.setGlobal_attitude(tmp);
			
			List<String> list = getKeyword(result_media);
			label_chart031.setText(List2Str(list));
			info.setMedia_theme(list);
			list = getKeyword(result_public);
			label_chart033.setText(List2Str(list));
			info.setPublic_theme(list);
			list = getKeyword(result_gov);
			label_chart035.setText(List2Str(list));
			info.setGov_theme(list);
			list = getKeyword(result_hottest_year);
			label_chart037.setText(hottest_year + ":" + List2Str(list));
			info.setYear_theme(list);
			

			label_chart021.invalidate();
			label_chart021.repaint();
			label_chart023.invalidate();
			label_chart023.repaint();
			label_chart025.invalidate();
			label_chart025.repaint();
			label_chart027.invalidate();
			label_chart027.repaint();
			

			// 搜索结果表格
			getTableResult(keyword, 5);// 5 for gov and media

			// 统计图表
			//Image img = Toolkit.getDefaultToolkit().createImage(".\\output\\patent_type.jpg");
			label_chart11.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage(".\\output\\motion.jpg")));
			info.setMotion_jpg(".\\output\\motion.jpg");
			label_chart12.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage(".\\output\\source.jpg")));
			info.setSource_jpg(".\\output\\source.jpg");
			label_chart21.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage(".\\output\\year_comments.jpg")));
			info.setYear_comments_jpg(".\\output\\year_comments.jpg");
			//label_chart22.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage(".\\output\\year_gov.jpg")));
			//label_chart31.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage(".\\output\\news_source.jpg")));
			//label_chart32.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage(".\\output\\year_news.jpg")));
			//label_chart41.setIcon(new ImageIcon(
			//		Toolkit.getDefaultToolkit().createImage(".\\output\\patent_applicant.jpg")));
			//label_chart42.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage(".\\output\\year_patent.jpg")));
			//label_chart51.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage(".\\output\\patent_type.jpg")));
			this.validate();
			
			// 所有数据
			tableModel = new DefaultTableModel(resultAllSize, 7) {
				@Override
				public boolean isCellEditable(int arg0, int arg1) {
					return false;
				}

				public String getColumnName(int n) {
					String columnNames[] = { "类型", "标题", "时间", "作者", "其他",
							"正文", "源地址" };
					return columnNames[n];
				}
			};

			for (int row = 0; row < resultAllSize; row++) {
				for (int column = 0; column < 7; column++) {
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

			table_all = new JTable(tableModel);
			table_all.getColumnModel().getColumn(0).setPreferredWidth(3);
			// tableAll.getColumnModel().getColumn(1).setPreferredWidth(30);
			// tableAll.getColumnModel().getColumn(2).setPreferredWidth(20);
			// tableAll.getColumnModel().getColumn(3).setPreferredWidth(20);
			// tableAll.getColumnModel().getColumn(4).setPreferredWidth(20);
			// tableAll.getColumnModel().getColumn(5).setPreferredWidth(200);
			// tableAll.getColumnModel().getColumn(6).setPreferredWidth(30);
			table_all.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
			table_all.setFont(Fonts.normal);

			menu = new JPopupMenu();
			item = new JMenuItem("删除该行");
			menu.add(item);

			item.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					int result = JOptionPane.showConfirmDialog(frame,
							"确定删除所选记录吗", "删除", JOptionPane.YES_NO_OPTION,
							JOptionPane.INFORMATION_MESSAGE);
					if (result == JOptionPane.YES_OPTION) {
						String deleteTitle = tableModel.getValueAt(
								table_all.getSelectedRow(), 1).toString();
						sqlop = new SQLop();
						sqlop.initialize();
						sqlop.removeRecord(deleteTitle);
						sqlop.close();
						tableModel.removeRow(table_all.getSelectedRow());
					}
				}
			});

			table_all.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if (e.getButton() == MouseEvent.BUTTON1) {
						if (count == 0)
							count++;
					}

					if (e.getButton() == MouseEvent.BUTTON3) {// right key click
						if (count == 1) {
							menu.show(e.getComponent(), e.getX(), e.getY());
						}
						count = 0;
					}
				}
			});
			JScrollPane scrollPane_all = new JScrollPane(table_all);
			panel_allTab.add(scrollPane_all, BorderLayout.CENTER);

		}

		//获取关键词
		private List<String> getKeyword(List<Map<String,String>> result){
			StringBuffer sb = new StringBuffer();
			for(int i = 0;i<result.size();i++){
				sb.append(result.get(i).get("content"));
			}
			String keyword_media = sb.toString();
			List<String> keywords = NLP.getKeyword(keyword_media,10);
			for(int i = 0;i<keywords.size();i++){
				if(keywords.get(i).contains("公车")
						||keywords.get(i).contains("改革")
						||keywords.get(i).contains("车改")){
					keywords.remove(i);
					i--;
				}
			}
			System.out.print("keyword:" + keywords);
			return keywords;
		}
		
		//填充表格内容
		class ResultTableFiller implements TableCellRenderer {

			@Override
			public Component getTableCellRendererComponent(JTable arg0,
					Object arg1, boolean arg2, boolean arg3, int row, int column) {
				textPane = new JTextPane();
				StyledDocument sd = getNewStyledDocument();
				insertDoc(sd, result.get(row).get("type"), "STYLE_type");
				insertDoc(sd, "  " + result.get(row).get("title").toString()
						+ "\n", "STYLE_title");
				if (result.get(row).get("time") != null)
					insertDoc(sd,
							result.get(row).get("time").toString() + "\t",
							"STYLE_author");
				//insertDoc(sd, result.get(row).get("author").toString() + "\n", "STYLE_author");
				insertDoc(sd, result.get(row).get("url").toString() + "\n",
						"STYLE_url");
				new NLP();
				insertDoc(
						sd,
						NLP.stringsummary(result.get(row).get("content")
								.toString())
								+ "\n", "STYLE_text");
				textPane.setStyledDocument(sd);
				return textPane;
			}

		}
		

		//表格格式
		public StyledDocument getNewStyledDocument() {

			return new DefaultStyledDocument();
		}

		//用于填充表格时添加字段
		public StyledDocument insertDoc(StyledDocument styledDoc,
				String content, String currentStyle) {
			if (content == null)
				content = "";
			try {
				int length = styledDoc.getLength();
				styledDoc.insertString(length, content,
						styleModel.getStyle(currentStyle));
			} catch (BadLocationException e) {
				System.err.println("BadLocationException: " + e);
			}
			return styledDoc;
		}

		//用于编辑表格字段的格式
		private void iniStyleModel() {
			styleModel = new DefaultStyledDocument();
			createStyle("STYLE_url", styleModel, 16, false, false, true,
					Color.GRAY, Color.WHITE, "Times New Roman");
			createStyle("STYLE_title", styleModel, 20, true, false, false,
					Color.BLACK, Color.WHITE, "黑体");
			createStyle("STYLE_abstract", styleModel, 16, false, false, false,
					Color.BLACK, Color.WHITE, "宋体");
			createStyle("STYLE_author", styleModel, 16, false, false, false,
					Color.BLACK, Color.WHITE, "楷体");
			createStyle("STYLE_type", styleModel, 20, false, false, false,
					Color.WHITE, Color.BLACK, "黑体");
			createStyle("STYLE_text", styleModel, 15, true, false, false,
					Color.BLACK, Color.WHITE, "宋体");
		}

		//用于创建表格字段的格式
		public void createStyle(String style, StyledDocument doc, int size,
				boolean bold, boolean italic, boolean underline, Color color,
				Color bcolor, String fontName) {
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
			StyleConstants.setBackground(s, bcolor);// 背景颜色
		}

		//筛选按钮的响应
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == button_gov) {
				panel_resultList.removeAll();
				this.getTableResult(keyword, 0);
				panel_resultList.validate();
			} else if (e.getSource() == button_news) {
				panel_resultList.removeAll();
				this.getTableResult(keyword, 2);
				panel_resultList.validate();
			} else if (e.getSource() == button_pa) {
				panel_resultList.removeAll();
				this.getTableResult(keyword, 1);
				panel_resultList.validate();
			}
		}

		//用于把List<String>转为String
		private String List2Str(List<String> list){
			StringBuffer sb = new StringBuffer();
			for(int i = 0;i<list.size();i++){
				sb.append(list.get(i));
				sb.append(" ");
			}
			return sb.toString();
		}
	
	}
	
	
}
