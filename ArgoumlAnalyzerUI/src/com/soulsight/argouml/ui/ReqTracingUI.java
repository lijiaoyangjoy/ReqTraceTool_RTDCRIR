package com.soulsight.argouml.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.FocusAdapter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.SequenceInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.print.attribute.standard.JobMessageFromOperator;
import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.LayoutStyle;
import javax.swing.border.TitledBorder;

import org.jdesktop.application.Action;
import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;
import org.soulsight.argouml.coauthor.DataRespo;
import org.soulsight.argouml.coauthor.GroundTruth;
import org.soulsight.argouml.coauthor.ReqCodeTracing;
import org.soulsight.argouml.coauthor.cluster.AuthorCommitClassCluster;
import org.soulsight.argouml.coauthor.cluster.ClassCluster;
import org.soulsight.argouml.coauthor.cluster.NewNaiveClassCluster;
import org.soulsight.argouml.coauthor.evaluation.Evaluation;
import org.soulsight.argouml.coauthor.evaluation.FMeasureEval;
import org.soulsight.argouml.coauthor.evaluation.ThresholdPrecisionEval;
import org.soulsight.argouml.coauthor.evaluation.ThresholdRecallEval;
import org.soulsight.argouml.coauthor.evaluation.GenericFMeasureEval;
import org.soulsight.argouml.coauthor.evaluation.TopKPrecisionEval;
import org.soulsight.argouml.coauthor.evaluation.TopKRecallEval;
import org.soulsight.argouml.coauthor.modularity.DeltaScore;
import org.soulsight.argouml.coauthor.modularity.ModuleMap;
import org.soulsight.argouml.coauthor.recommend.ModuleRecommender;
import org.soulsight.argouml.coauthor.similarity.HybridSim;

import DynamicTraceability.Toolkit.Entity.JavaPackage;
import DynamicTraceability.Toolkit.JavaToXML.CodePreProcessor;
import DynamicTraceability.Toolkit.JavaToXML.PackageUtil;
import DynamicTraceability.Toolkit.JavaToXML.RawClassWordsProcessor;

/**
 * This code was edited or generated using CloudGarden's Jigloo
 * SWT/Swing GUI Builder, which is free for non-commercial
 * use. If Jigloo is being used commercially (ie, by a corporation,
 * company or business for any purpose whatever) then you
 * should purchase a license for each developer using Jigloo.
 * Please visit www.cloudgarden.com for details.
 * Use of Jigloo implies acceptance of these licensing terms.
 * A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
 * THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
 * LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
 */
/**
 * 
 */
public class ReqTracingUI extends SingleFrameApplication {
	private JButton reqDocPathBtn;
	private JTextField reqDocPathTextField;
	private JLabel reqDocPathLabel;
	private JLabel titleLabel;
	private JLabel jLabel6;
	private JTextField rankTextField;
	private JButton evalBtn;
	private JPanel jPanel7;
	private JButton jButton11;
	private JButton jButton8;
	private JTextField jTextField7;
	private JLabel jLabel7;
	private JPanel jPanel6;
	private JButton jButton10;
	private JButton rankBtn;
	private JTextField recCountTextField;
	private JLabel jLabel5;
	private JPanel jPanel5;
	private JButton coauthorAnalyzeBtn;
	private JButton jButton6;
	private JTextField coauthorLogTextField;
	private JLabel jLabel4;
	private JPanel jPanel4;
	private JButton textAnalyzeBtn;
	private JTextField textAnalyzeTextField;
	private JLabel jLabel3;
	private JButton jButton4;
	private JPanel jPanel2;
	private JLabel jLabel2;
	private JTextField preprocessSavePathTextField;
	private JButton jButton3;
	private JLabel jLabel1;
	private JTextField codePathTextField;
	private JButton jButton2;
	private JButton preprocessBtn;
	private JLabel jLabel12;
	private JTextField jTextField1;
	private JButton jButton12;
	private JLabel jLabel11;
	private JTextField evalResultTextField;
	private JButton jButton9;
	private JLabel jLabel10;
	private JTextField evalTextField;
	private JButton jButton7;
	private JLabel jLabel8;
	private JTextField coauthorTextField;
	private JButton jButton1;
	private JPanel jPanel3;
	private JPanel jPanel1;
	private JPanel preprocessPanel;
	private JPanel titlePanel;
	private JPanel topPanel;
	private JSeparator jSeparator;
	private JPanel toolBarPanel;
	private JPanel contentPanel;

	private JFileChooser fileChooser;

	@Action
	public void open() {
	}

	@Action
	public void save() {
	}

	@Action
	public void newFile() {
	}

	private ActionMap getAppActionMap() {
		return Application.getInstance().getContext().getActionMap(this);
	}

	@Override
	protected void startup() {
		{
		getMainFrame().setSize(619, 631);
		}
		{
			topPanel = new JPanel();
			BorderLayout panelLayout = new BorderLayout();
			topPanel.setLayout(panelLayout);
			topPanel.setPreferredSize(new java.awt.Dimension(500, 300));
			{
				contentPanel = new JPanel();
				BorderLayout contentPanelLayout = new BorderLayout();
				contentPanel.setLayout(contentPanelLayout);
				topPanel.add(contentPanel, BorderLayout.CENTER);
				contentPanel.setPreferredSize(new java.awt.Dimension(257, 495));
				{
					titlePanel = new JPanel();
					FlowLayout titlePanelLayout = new FlowLayout();
					titlePanel.setLayout(titlePanelLayout);
					contentPanel.add(titlePanel, BorderLayout.CENTER);
					titlePanel
							.setPreferredSize(new java.awt.Dimension(604, 590));
					{
						preprocessPanel = new JPanel();
						titlePanel.add(preprocessPanel);
						preprocessPanel
								.setPreferredSize(new java.awt.Dimension(608,
										24));
						{
							titleLabel = new JLabel();
							preprocessPanel.add(titleLabel);
							titleLabel.setName("titleLabel");
							titleLabel.setPreferredSize(new java.awt.Dimension(
									591, 22));
						}
					}
					{
						jPanel1 = new JPanel();
						titlePanel.add(jPanel1);
						GridLayout jPanel1Layout1 = new GridLayout(1, 1);
						jPanel1Layout1.setHgap(5);
						jPanel1Layout1.setVgap(5);
						jPanel1Layout1.setColumns(1);
						jPanel1.setLayout(jPanel1Layout1);
						jPanel1.setPreferredSize(new java.awt.Dimension(608,
								127));
						{
							jPanel3 = new JPanel();
							GroupLayout jPanel3Layout = new GroupLayout(
									(JComponent) jPanel3);
							jPanel3.setLayout(jPanel3Layout);
							jPanel1.add(jPanel3);
							jPanel3.setPreferredSize(new java.awt.Dimension(
									608, 108));
							jPanel3.setBorder(BorderFactory.createTitledBorder(
									null, "\u6570\u636e\u9884\u5904\u7406",
									TitledBorder.LEADING,
									TitledBorder.DEFAULT_POSITION));
							{
								reqDocPathLabel = new JLabel();
								reqDocPathLabel.setName("reqDocPathLabel");
							}
							{
								reqDocPathTextField = new JTextField();
								reqDocPathTextField
										.setName("reqDocPathTextField");
							}
							{
								preprocessBtn = new JButton();
								preprocessBtn.setName("preprocessBtn");
								preprocessBtn
										.addMouseListener(new MouseAdapter() {
											public void mouseClicked(
													MouseEvent evt) {
												preprocessBtnMouseClicked(evt);
											}
										});
							}
							{
								jButton2 = new JButton();
								jButton2.setName("jButton2");
								jButton2.addMouseListener(new MouseAdapter() {
									public void mouseClicked(MouseEvent evt) {
										jButton2MouseClicked(evt);
									}
								});
							}
							{
								codePathTextField = new JTextField();
								codePathTextField.setName("codePathTextField");
							}
							{
								jLabel1 = new JLabel();
								jLabel1.setName("jLabel1");
							}
							{
								jButton3 = new JButton();
								jButton3.setName("jButton3");
								jButton3.addMouseListener(new MouseAdapter() {
									public void mouseClicked(MouseEvent evt) {
										jButton3MouseClicked(evt);
									}
								});
							}
							{
								preprocessSavePathTextField = new JTextField();
								preprocessSavePathTextField
										.setName("preprocessSavePathTextField");
							}
							{
								jLabel2 = new JLabel();
								jLabel2.setName("jLabel2");
							}
							{
								reqDocPathBtn = new JButton();
								reqDocPathBtn.setName("reqDocPathBtn");
								reqDocPathBtn
										.addMouseListener(new MouseAdapter() {
											public void mouseClicked(
													MouseEvent evt) {
												reqDocPathBtnMouseClicked(evt);
											}
										});
							}
							jPanel3Layout
									.setHorizontalGroup(jPanel3Layout
											.createSequentialGroup()
											.addContainerGap(18, 18)
											.addGroup(
													jPanel3Layout
															.createParallelGroup()
															.addComponent(
																	reqDocPathLabel,
																	GroupLayout.Alignment.LEADING,
																	GroupLayout.PREFERRED_SIZE,
																	84,
																	GroupLayout.PREFERRED_SIZE)
															.addComponent(
																	jLabel1,
																	GroupLayout.Alignment.LEADING,
																	GroupLayout.PREFERRED_SIZE,
																	84,
																	GroupLayout.PREFERRED_SIZE)
															.addComponent(
																	jLabel2,
																	GroupLayout.Alignment.LEADING,
																	GroupLayout.PREFERRED_SIZE,
																	84,
																	GroupLayout.PREFERRED_SIZE))
											.addGroup(
													jPanel3Layout
															.createParallelGroup()
															.addComponent(
																	reqDocPathTextField,
																	GroupLayout.Alignment.LEADING,
																	GroupLayout.PREFERRED_SIZE,
																	263,
																	GroupLayout.PREFERRED_SIZE)
															.addComponent(
																	codePathTextField,
																	GroupLayout.Alignment.LEADING,
																	GroupLayout.PREFERRED_SIZE,
																	263,
																	GroupLayout.PREFERRED_SIZE)
															.addComponent(
																	preprocessSavePathTextField,
																	GroupLayout.Alignment.LEADING,
																	GroupLayout.PREFERRED_SIZE,
																	263,
																	GroupLayout.PREFERRED_SIZE))
											.addPreferredGap(
													LayoutStyle.ComponentPlacement.UNRELATED)
											.addGroup(
													jPanel3Layout
															.createParallelGroup()
															.addComponent(
																	reqDocPathBtn,
																	GroupLayout.Alignment.LEADING,
																	GroupLayout.PREFERRED_SIZE,
																	30,
																	GroupLayout.PREFERRED_SIZE)
															.addComponent(
																	jButton2,
																	GroupLayout.Alignment.LEADING,
																	GroupLayout.PREFERRED_SIZE,
																	30,
																	GroupLayout.PREFERRED_SIZE)
															.addComponent(
																	jButton3,
																	GroupLayout.Alignment.LEADING,
																	GroupLayout.PREFERRED_SIZE,
																	30,
																	GroupLayout.PREFERRED_SIZE))
											.addGap(76)
											.addComponent(preprocessBtn, 0,
													115, Short.MAX_VALUE));
							jPanel3Layout
									.setVerticalGroup(jPanel3Layout
											.createSequentialGroup()
											.addContainerGap()
											.addGroup(
													jPanel3Layout
															.createParallelGroup(
																	GroupLayout.Alignment.BASELINE)
															.addComponent(
																	reqDocPathBtn,
																	GroupLayout.Alignment.BASELINE,
																	GroupLayout.PREFERRED_SIZE,
																	22,
																	GroupLayout.PREFERRED_SIZE)
															.addComponent(
																	reqDocPathTextField,
																	GroupLayout.Alignment.BASELINE,
																	GroupLayout.PREFERRED_SIZE,
																	20,
																	GroupLayout.PREFERRED_SIZE)
															.addComponent(
																	reqDocPathLabel,
																	GroupLayout.Alignment.BASELINE,
																	GroupLayout.PREFERRED_SIZE,
																	20,
																	GroupLayout.PREFERRED_SIZE))
											.addPreferredGap(
													LayoutStyle.ComponentPlacement.RELATED)
											.addGroup(
													jPanel3Layout
															.createParallelGroup()
															.addGroup(
																	GroupLayout.Alignment.LEADING,
																	jPanel3Layout
																			.createParallelGroup(
																					GroupLayout.Alignment.BASELINE)
																			.addComponent(
																					preprocessBtn,
																					GroupLayout.Alignment.BASELINE,
																					0,
																					33,
																					Short.MAX_VALUE)
																			.addComponent(
																					jButton2,
																					GroupLayout.Alignment.BASELINE,
																					GroupLayout.PREFERRED_SIZE,
																					22,
																					GroupLayout.PREFERRED_SIZE)
																			.addComponent(
																					codePathTextField,
																					GroupLayout.Alignment.BASELINE,
																					GroupLayout.PREFERRED_SIZE,
																					20,
																					GroupLayout.PREFERRED_SIZE)
																			.addComponent(
																					jLabel1,
																					GroupLayout.Alignment.BASELINE,
																					GroupLayout.PREFERRED_SIZE,
																					20,
																					GroupLayout.PREFERRED_SIZE))
															.addGroup(
																	jPanel3Layout
																			.createSequentialGroup()
																			.addGap(0,
																					28,
																					Short.MAX_VALUE)
																			.addGroup(
																					jPanel3Layout
																							.createParallelGroup(
																									GroupLayout.Alignment.BASELINE)
																							.addComponent(
																									jButton3,
																									GroupLayout.Alignment.BASELINE,
																									GroupLayout.PREFERRED_SIZE,
																									22,
																									GroupLayout.PREFERRED_SIZE)
																							.addComponent(
																									preprocessSavePathTextField,
																									GroupLayout.Alignment.BASELINE,
																									GroupLayout.PREFERRED_SIZE,
																									20,
																									GroupLayout.PREFERRED_SIZE)
																							.addComponent(
																									jLabel2,
																									GroupLayout.Alignment.BASELINE,
																									GroupLayout.PREFERRED_SIZE,
																									20,
																									GroupLayout.PREFERRED_SIZE))))
											.addContainerGap(18, 18));
						}
					}
					{
						jPanel2 = new JPanel();
						titlePanel.add(jPanel2);
						GroupLayout jPanel2Layout = new GroupLayout(
								(JComponent) jPanel2);
						jPanel2.setPreferredSize(new java.awt.Dimension(608, 99));
						jPanel2.setBorder(BorderFactory
								.createTitledBorder("\u6587\u672c\u5206\u6790"));
						jPanel2.setLayout(jPanel2Layout);
						{
							jButton4 = new JButton();
							jButton4.setName("jButton4");
							jButton4.addMouseListener(new MouseAdapter() {
								public void mouseClicked(MouseEvent evt) {
									jButton4MouseClicked(evt);
								}
							});
						}
						{
							jLabel3 = new JLabel();
							jLabel3.setName("textAnalyzeLabel");
						}
						{
							textAnalyzeTextField = new JTextField();
							textAnalyzeTextField
									.setName("textAnalyzeTextField");
						}
						{
							textAnalyzeBtn = new JButton();
							textAnalyzeBtn.setName("textAnalyzeBtn");
							textAnalyzeBtn.addMouseListener(new MouseAdapter() {
								public void mouseClicked(MouseEvent evt) {
									textAnalyzeBtnMouseClicked(evt);
								}
							});
						}
						{
							jButton12 = new JButton();
							jButton12.setName("arffFileButton");
							jButton12.addMouseListener(new MouseAdapter() {
								public void mouseClicked(MouseEvent evt) {
									jButton12MouseClicked(evt);
								}
							});
						}
						{
							jTextField1 = new JTextField();
							jTextField1.setName("arffTextField");
						}
						{
							jLabel12 = new JLabel();
							jLabel12.setName("arffLabel");
						}
						jPanel2Layout.setHorizontalGroup(jPanel2Layout.createSequentialGroup()
							.addContainerGap(18, 18)
							.addGroup(jPanel2Layout.createParallelGroup()
							    .addComponent(jLabel3, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 84, GroupLayout.PREFERRED_SIZE)
							    .addComponent(jLabel12, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 84, GroupLayout.PREFERRED_SIZE))
							.addGroup(jPanel2Layout.createParallelGroup()
							    .addComponent(textAnalyzeTextField, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 263, GroupLayout.PREFERRED_SIZE)
							    .addComponent(jTextField1, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 263, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
							.addGroup(jPanel2Layout.createParallelGroup()
							    .addComponent(jButton4, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
							    .addComponent(jButton12, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE))
							.addGap(73)
							.addComponent(textAnalyzeBtn, 0, 115, Short.MAX_VALUE)
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED));
						jPanel2Layout.setVerticalGroup(jPanel2Layout.createSequentialGroup()
							.addContainerGap()
							.addGroup(jPanel2Layout.createParallelGroup()
							    .addGroup(jPanel2Layout.createSequentialGroup()
							        .addGroup(jPanel2Layout.createParallelGroup()
							            .addComponent(jButton12, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
							            .addComponent(jTextField1, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
							            .addComponent(jLabel12, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE))
							        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							        .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
							            .addComponent(jButton4, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
							            .addComponent(textAnalyzeTextField, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
							            .addComponent(jLabel3, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)))
							    .addGroup(GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
							        .addGap(10)
							        .addComponent(textAnalyzeBtn, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
							        .addGap(0, 8, Short.MAX_VALUE)))
							.addContainerGap(23, 23));
					}
					{
						jPanel4 = new JPanel();
						titlePanel.add(jPanel4);
						GroupLayout jPanel4Layout = new GroupLayout(
								(JComponent) jPanel4);
						jPanel4.setPreferredSize(new java.awt.Dimension(608, 84));
						jPanel4.setBorder(BorderFactory
								.createTitledBorder("\u534f\u4f5c\u5206\u6790"));
						jPanel4.setLayout(jPanel4Layout);
						{
							jLabel4 = new JLabel();
							jLabel4.setName("coauthorLogLabel");
						}
						{
							coauthorLogTextField = new JTextField();
							coauthorLogTextField.setName("coautherTextField");
						}
						{
							jButton6 = new JButton();
							jButton6.setName("jButton6");
							jButton6.addMouseListener(new MouseAdapter() {
								public void mouseClicked(MouseEvent evt) {
									jButton6MouseClicked(evt);
								}
							});
						}
						{
							coauthorAnalyzeBtn = new JButton();
							coauthorAnalyzeBtn.setName("coauthorAnalyzeBtn");
							coauthorAnalyzeBtn
									.addMouseListener(new MouseAdapter() {
										public void mouseClicked(MouseEvent evt) {
											coauthorAnalyzeBtnMouseClicked(evt);
										}
									});
						}
						{
							jButton1 = new JButton();
							jButton1.setName("jButton1");
							jButton1.addMouseListener(new MouseAdapter() {
								public void mouseClicked(MouseEvent evt) {
									jButton1MouseClicked(evt);
								}
							});
						}
						{
							coauthorTextField = new JTextField();
							coauthorTextField.setName("coauthorTextField");
						}
						{
							jLabel8 = new JLabel();
							jLabel8.setName("jLabel8");
						}
						jPanel4Layout
								.setHorizontalGroup(jPanel4Layout
										.createSequentialGroup()
										.addContainerGap(18, 18)
										.addGroup(
												jPanel4Layout
														.createParallelGroup()
														.addComponent(
																jLabel8,
																GroupLayout.Alignment.LEADING,
																GroupLayout.PREFERRED_SIZE,
																84,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																jLabel4,
																GroupLayout.Alignment.LEADING,
																GroupLayout.PREFERRED_SIZE,
																84,
																GroupLayout.PREFERRED_SIZE))
										.addGroup(
												jPanel4Layout
														.createParallelGroup()
														.addComponent(
																coauthorTextField,
																GroupLayout.Alignment.LEADING,
																GroupLayout.PREFERRED_SIZE,
																263,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																coauthorLogTextField,
																GroupLayout.Alignment.LEADING,
																GroupLayout.PREFERRED_SIZE,
																263,
																GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												LayoutStyle.ComponentPlacement.UNRELATED)
										.addGroup(
												jPanel4Layout
														.createParallelGroup()
														.addComponent(
																jButton1,
																GroupLayout.Alignment.LEADING,
																GroupLayout.PREFERRED_SIZE,
																27,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																jButton6,
																GroupLayout.Alignment.LEADING,
																GroupLayout.PREFERRED_SIZE,
																27,
																GroupLayout.PREFERRED_SIZE))
										.addGap(79)
										.addComponent(coauthorAnalyzeBtn, 0,
												115, Short.MAX_VALUE));
						jPanel4Layout
								.setVerticalGroup(jPanel4Layout
										.createSequentialGroup()
										.addPreferredGap(
												LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												jPanel4Layout
														.createParallelGroup()
														.addGroup(
																jPanel4Layout
																		.createSequentialGroup()
																		.addGroup(
																				jPanel4Layout
																						.createParallelGroup(
																								GroupLayout.Alignment.BASELINE)
																						.addComponent(
																								jButton6,
																								GroupLayout.Alignment.BASELINE,
																								GroupLayout.PREFERRED_SIZE,
																								22,
																								GroupLayout.PREFERRED_SIZE)
																						.addComponent(
																								jLabel4,
																								GroupLayout.Alignment.BASELINE,
																								GroupLayout.PREFERRED_SIZE,
																								20,
																								GroupLayout.PREFERRED_SIZE)
																						.addComponent(
																								coauthorLogTextField,
																								GroupLayout.Alignment.BASELINE,
																								GroupLayout.PREFERRED_SIZE,
																								20,
																								GroupLayout.PREFERRED_SIZE))
																		.addPreferredGap(
																				LayoutStyle.ComponentPlacement.RELATED,
																				0,
																				Short.MAX_VALUE)
																		.addGroup(
																				jPanel4Layout
																						.createParallelGroup(
																								GroupLayout.Alignment.BASELINE)
																						.addComponent(
																								jButton1,
																								GroupLayout.Alignment.BASELINE,
																								GroupLayout.PREFERRED_SIZE,
																								22,
																								GroupLayout.PREFERRED_SIZE)
																						.addComponent(
																								jLabel8,
																								GroupLayout.Alignment.BASELINE,
																								GroupLayout.PREFERRED_SIZE,
																								20,
																								GroupLayout.PREFERRED_SIZE)
																						.addComponent(
																								coauthorTextField,
																								GroupLayout.Alignment.BASELINE,
																								GroupLayout.PREFERRED_SIZE,
																								20,
																								GroupLayout.PREFERRED_SIZE)))
														.addGroup(
																GroupLayout.Alignment.LEADING,
																jPanel4Layout
																		.createSequentialGroup()
																		.addGap(12)
																		.addComponent(
																				coauthorAnalyzeBtn,
																				0,
																				28,
																				Short.MAX_VALUE)
																		.addGap(7)))
										.addContainerGap());
					}
					{
						jPanel5 = new JPanel();
						titlePanel.add(jPanel5);
						GroupLayout jPanel5Layout = new GroupLayout((JComponent)jPanel5);
						jPanel5.setPreferredSize(new java.awt.Dimension(608, 92));
						jPanel5.setBorder(BorderFactory
								.createTitledBorder("\u5339\u914d\u6392\u5e8f"));
						jPanel5.setLayout(jPanel5Layout);
						{
							jLabel5 = new JLabel();
							jLabel5.setName("jLabel5");
						}
						{
							recCountTextField = new JTextField();
							recCountTextField.setName("recCountTextField");
						}
						{
							rankBtn = new JButton();
							rankBtn.setName("rankBtn");
							rankBtn.addMouseListener(new MouseAdapter() {
								public void mouseClicked(MouseEvent evt) {
									rankBtnMouseClicked(evt);
								}
							});
						}
						{
							jButton10 = new JButton();
							jButton10.setName("jButton10");
							jButton10.addMouseListener(new MouseAdapter() {
								public void mouseClicked(MouseEvent evt) {
									jButton10MouseClicked(evt);
								}
							});
						}
						{
							rankTextField = new JTextField();
							rankTextField.setName("rankTextField");
						}
						{
							jLabel6 = new JLabel();
							jLabel6.setName("jLabel6");
						}
						jPanel5Layout.setHorizontalGroup(jPanel5Layout.createSequentialGroup()
							.addContainerGap(18, 18)
							.addGroup(jPanel5Layout.createParallelGroup()
							    .addComponent(jLabel5, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 84, GroupLayout.PREFERRED_SIZE)
							    .addComponent(jLabel6, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 84, GroupLayout.PREFERRED_SIZE))
							.addGroup(jPanel5Layout.createParallelGroup()
							    .addComponent(recCountTextField, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 263, GroupLayout.PREFERRED_SIZE)
							    .addComponent(rankTextField, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 263, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
							.addComponent(jButton10, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
							.addGap(0, 79, Short.MAX_VALUE)
							.addComponent(rankBtn, GroupLayout.PREFERRED_SIZE, 115, GroupLayout.PREFERRED_SIZE));
						jPanel5Layout.setVerticalGroup(jPanel5Layout.createSequentialGroup()
							.addContainerGap()
							.addGroup(jPanel5Layout.createParallelGroup()
							    .addGroup(jPanel5Layout.createSequentialGroup()
							        .addGroup(jPanel5Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
							            .addComponent(recCountTextField, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
							            .addComponent(jLabel5, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
							        .addGap(10)
							        .addGroup(jPanel5Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
							            .addComponent(jButton10, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
							            .addComponent(rankTextField, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
							            .addComponent(jLabel6, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)))
							    .addGroup(GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup()
							        .addGap(8)
							        .addComponent(rankBtn, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
							        .addGap(0, 10, Short.MAX_VALUE)))
							.addGap(6));
					}
					{
						jPanel7 = new JPanel();
						titlePanel.add(jPanel7);
						GroupLayout jPanel7Layout = new GroupLayout(
								(JComponent) jPanel7);
						jPanel7.setPreferredSize(new java.awt.Dimension(608,
								118));
						jPanel7.setBorder(BorderFactory
								.createTitledBorder("\u7ed3\u679c\u5c55\u793a\u533a\u57df"));
						jPanel7.setLayout(jPanel7Layout);
						{
							jButton9 = new JButton();
							jButton9.setName("jButton9");
							jButton9.addMouseListener(new MouseAdapter() {
								public void mouseClicked(MouseEvent evt) {
									jButton9MouseClicked(evt);
								}
							});
						}
						{
							evalResultTextField = new JTextField();
							evalResultTextField.setName("evalResultTextField");
						}
						{
							jLabel11 = new JLabel();
							jLabel11.setName("jLabel11");
						}
						{
							jButton7 = new JButton();
							jButton7.setName("jButton7");
							jButton7.addMouseListener(new MouseAdapter() {
								public void mouseClicked(MouseEvent evt) {
									jButton7MouseClicked(evt);
								}
							});
						}
						{
							evalTextField = new JTextField();
							evalTextField.setName("evalTextField");
						}
						{
							jLabel10 = new JLabel();
							jLabel10.setName("jLabel10");
						}
						{
							evalBtn = new JButton();
							evalBtn.setName("evalBtn");
							evalBtn.addMouseListener(new MouseAdapter() {
								public void mouseClicked(MouseEvent evt) {
									evalBtnMouseClicked(evt);
								}
							});
						}
						jPanel7Layout
								.setHorizontalGroup(jPanel7Layout
										.createSequentialGroup()
										.addContainerGap(18, 18)
										.addGroup(
												jPanel7Layout
														.createParallelGroup()
														.addComponent(
																jLabel11,
																GroupLayout.Alignment.LEADING,
																GroupLayout.PREFERRED_SIZE,
																84,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																jLabel10,
																GroupLayout.Alignment.LEADING,
																GroupLayout.PREFERRED_SIZE,
																84,
																GroupLayout.PREFERRED_SIZE))
										.addGroup(
												jPanel7Layout
														.createParallelGroup()
														.addComponent(
																evalResultTextField,
																GroupLayout.Alignment.LEADING,
																GroupLayout.PREFERRED_SIZE,
																263,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																evalTextField,
																GroupLayout.Alignment.LEADING,
																GroupLayout.PREFERRED_SIZE,
																263,
																GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												LayoutStyle.ComponentPlacement.UNRELATED)
										.addGroup(
												jPanel7Layout
														.createParallelGroup()
														.addComponent(
																jButton9,
																GroupLayout.Alignment.LEADING,
																GroupLayout.PREFERRED_SIZE,
																27,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																jButton7,
																GroupLayout.Alignment.LEADING,
																GroupLayout.PREFERRED_SIZE,
																27,
																GroupLayout.PREFERRED_SIZE))
										.addGap(81)
										.addComponent(evalBtn, 0, 113,
												Short.MAX_VALUE));
						jPanel7Layout
								.setVerticalGroup(jPanel7Layout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												jPanel7Layout
														.createParallelGroup()
														.addGroup(
																jPanel7Layout
																		.createSequentialGroup()
																		.addGroup(
																				jPanel7Layout
																						.createParallelGroup(
																								GroupLayout.Alignment.BASELINE)
																						.addComponent(
																								jButton7,
																								GroupLayout.Alignment.BASELINE,
																								GroupLayout.PREFERRED_SIZE,
																								22,
																								GroupLayout.PREFERRED_SIZE)
																						.addComponent(
																								evalTextField,
																								GroupLayout.Alignment.BASELINE,
																								GroupLayout.PREFERRED_SIZE,
																								20,
																								GroupLayout.PREFERRED_SIZE)
																						.addComponent(
																								jLabel10,
																								GroupLayout.Alignment.BASELINE,
																								GroupLayout.PREFERRED_SIZE,
																								20,
																								GroupLayout.PREFERRED_SIZE))
																		.addPreferredGap(
																				LayoutStyle.ComponentPlacement.RELATED)
																		.addGroup(
																				jPanel7Layout
																						.createParallelGroup(
																								GroupLayout.Alignment.BASELINE)
																						.addComponent(
																								jButton9,
																								GroupLayout.Alignment.BASELINE,
																								GroupLayout.PREFERRED_SIZE,
																								22,
																								GroupLayout.PREFERRED_SIZE)
																						.addComponent(
																								evalResultTextField,
																								GroupLayout.Alignment.BASELINE,
																								GroupLayout.PREFERRED_SIZE,
																								20,
																								GroupLayout.PREFERRED_SIZE)
																						.addComponent(
																								jLabel11,
																								GroupLayout.Alignment.BASELINE,
																								GroupLayout.PREFERRED_SIZE,
																								20,
																								GroupLayout.PREFERRED_SIZE)))
														.addGroup(
																GroupLayout.Alignment.LEADING,
																jPanel7Layout
																		.createSequentialGroup()
																		.addGap(7)
																		.addComponent(
																				evalBtn,
																				GroupLayout.PREFERRED_SIZE,
																				36,
																				GroupLayout.PREFERRED_SIZE)
																		.addGap(0,
																				8,
																				Short.MAX_VALUE)))
										.addContainerGap(89, 89));
					}
				}
			}
			{
				toolBarPanel = new JPanel();
				topPanel.add(toolBarPanel, BorderLayout.NORTH);
				BorderLayout jPanel1Layout = new BorderLayout();
				toolBarPanel.setLayout(jPanel1Layout);
				{
					jSeparator = new JSeparator();
					toolBarPanel.add(jSeparator, BorderLayout.SOUTH);
				}
			}
		}
		{
			jPanel6 = new JPanel();
			getMainFrame().getContentPane().add(jPanel6, BorderLayout.CENTER);
			GroupLayout jPanel6Layout = new GroupLayout((JComponent) jPanel6);
			jPanel6.setPreferredSize(new java.awt.Dimension(608, 604));
			jPanel6.setBorder(BorderFactory
					.createTitledBorder("\u534f\u4f5c\u5206\u6790"));
			jPanel6.setLayout(jPanel6Layout);
			{
				jLabel7 = new JLabel();
				jLabel7.setName("reqDocPathLabel");
			}
			{
				jTextField7 = new JTextField();
				jTextField7.setName("reqDocPathTextField");
			}
			{
				jButton8 = new JButton();
				jButton8.setName("reqDocPathBtn");
			}
			{
				jButton11 = new JButton();
				jButton11.setName("preprocessBtn");
			}
			jPanel6Layout.setVerticalGroup(jPanel6Layout
					.createSequentialGroup()
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGroup(
							jPanel6Layout
									.createParallelGroup(
											GroupLayout.Alignment.BASELINE)
									.addComponent(jButton11,
											GroupLayout.Alignment.BASELINE,
											GroupLayout.PREFERRED_SIZE, 23,
											GroupLayout.PREFERRED_SIZE)
									.addComponent(jButton8,
											GroupLayout.Alignment.BASELINE,
											GroupLayout.PREFERRED_SIZE, 22,
											GroupLayout.PREFERRED_SIZE)
									.addComponent(jLabel7,
											GroupLayout.Alignment.BASELINE,
											GroupLayout.PREFERRED_SIZE, 20,
											GroupLayout.PREFERRED_SIZE)
									.addComponent(jTextField7,
											GroupLayout.Alignment.BASELINE,
											GroupLayout.PREFERRED_SIZE, 20,
											GroupLayout.PREFERRED_SIZE)));
			jPanel6Layout.setHorizontalGroup(jPanel6Layout
					.createSequentialGroup()
					.addContainerGap(18, 18)
					.addComponent(jLabel7, GroupLayout.PREFERRED_SIZE, 84,
							GroupLayout.PREFERRED_SIZE)
					.addComponent(jTextField7, GroupLayout.PREFERRED_SIZE, 263,
							GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
					.addComponent(jButton8, GroupLayout.PREFERRED_SIZE, 63,
							GroupLayout.PREFERRED_SIZE).addGap(43)
					.addComponent(jButton11, 0, 115, Short.MAX_VALUE));
		}
		show(topPanel);
	}

	public static void main(String[] args) {
		launch(ReqTracingUI.class, args);
	}

	
	private void showMessage(String msg) {
		JOptionPane.showMessageDialog(null, msg, "Message",
				JOptionPane.YES_OPTION);
	}

	private String chooseFile() {
		fileChooser = new JFileChooser();
		fileChooser.setVisible(true);
		// 设置默认的打开目录,如果不设的话按照window的默认目录(我的文档)
		fileChooser.setCurrentDirectory(new File("d:/"));
		// 设置打开文件类型,此处设置成只能选择文件，不能选择文件夹
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);// 只能打开文件
		// 打开一个对话框
		int index = fileChooser.showDialog(null, "打开文件");
		if (index == JFileChooser.APPROVE_OPTION) {
			// 把获取到的文件的绝对路径显示在文本编辑框中
			// reqDocPathTextField.setText(fileChooser.getSelectedFile().getAbsolutePath());
			return fileChooser.getSelectedFile().getAbsolutePath();
		}
		return "";
	}

	private String chooseDir() {
		fileChooser = new JFileChooser();
		fileChooser.setVisible(true);
		// 设置默认的打开目录,如果不设的话按照window的默认目录(我的文档)
		fileChooser.setCurrentDirectory(new File("d:/"));
		// 设置打开文件类型,此处设置成只能选择文件，不能选择文件夹
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);// 只能打开文件
		// 打开一个对话框
		int index = fileChooser.showDialog(null, "打开文件");
		if (index == JFileChooser.APPROVE_OPTION) {
			// 把获取到的文件的绝对路径显示在文本编辑框中
			// reqDocPathTextField.setText(fileChooser.getSelectedFile().getAbsolutePath());
			return fileChooser.getSelectedFile().getAbsolutePath();
		}
		return "";
	}

	private void reqDocPathBtnMouseClicked(MouseEvent evt) {
		reqDocPathTextField.setText(chooseFile());
	}

	private void jButton2MouseClicked(MouseEvent evt) {
		codePathTextField.setText(chooseFile());
	}

	private void jButton3MouseClicked(MouseEvent evt) {
		preprocessSavePathTextField.setText(chooseFile());
	}

	private void jButton4MouseClicked(MouseEvent evt) {
		textAnalyzeTextField.setText(chooseFile());
	}

	private void jButton6MouseClicked(MouseEvent evt) {
		coauthorLogTextField.setText(chooseFile());
	}

	private void jButton10MouseClicked(MouseEvent evt) {
		rankTextField.setText(chooseDir());
	}

	private void jButton7MouseClicked(MouseEvent evt) {
		evalTextField.setText(chooseDir());
	}

	private void jButton9MouseClicked(MouseEvent evt) {
		evalResultTextField.setText(chooseFile());
	}

	private void jButton1MouseClicked(MouseEvent evt) {
		coauthorTextField.setText(chooseFile());
	}

	private void coauthorAnalyzeBtnMouseClicked(MouseEvent evt) {
		coauthorAnalyzeBtn.setEnabled(false);
		try {
			//ClassCluster cluster = new NewNaiveClassCluster();
			//cluster.cluster(coauthorLogTextField.getText(),
			//		coauthorTextField.getText());
			AuthorCommitClassCluster.preprocessLogFile(coauthorLogTextField.getText(),
					coauthorTextField.getText());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
		}
		coauthorAnalyzeBtn.setEnabled(true);
	}

	private void textAnalyzeBtnMouseClicked(MouseEvent evt) {
		textAnalyzeBtn.setEnabled(false);
		try {
			DataRespo.tfIdf(jTextField1.getText(),
					textAnalyzeTextField.getText());
		} catch (Exception e) {
			System.out.println(e);
		}
		textAnalyzeBtn.setEnabled(true);
	}

	public void testPreprocess(String requirementPath,String jarFilePath,String savePath) throws IOException, ClassNotFoundException {
		PackageUtil packageUtil = new PackageUtil();

		packageUtil.appClassesList = packageUtil.getClassInJar(packageUtil.targetJarPath);

		JavaPackage rootPack = packageUtil.classDetailToMap();
		System.out.println("-------------");
		System.out.println(rootPack.subElementMap);
		packageUtil.mapToXML(rootPack);

//		packageUtil.saveCodeWordsPath = "./data/argouml-codeWords-test-lalala.txt";
		
		packageUtil.saveCodeWordsPath = savePath;
		
		packageUtil.writeCodeBag(packageUtil.saveCodeWordsPath, packageUtil.codeWordsMap);
		


	}

	private void preprocessBtnMouseClicked(MouseEvent evt) {
		preprocessBtn.setEnabled(false);
		try {
			
			String requirementPath=reqDocPathTextField.getText();
			String jarFilePath=codePathTextField.getText();
			String saveFilePath_old=reqDocPathTextField.getText()+".mid";
			testPreprocess(requirementPath,jarFilePath,saveFilePath_old);
			
			

			
			RawClassWordsProcessor rp = new RawClassWordsProcessor();
			rp.fileStringToLower(requirementPath,
					requirementPath+".new");	
			rp.fileStringToLower("./data/codeWordsSupplement.txt",
					"./data/codeWordsSupplement-new.txt");
			
			combineFile(saveFilePath_old,"./data/codeWordsSupplement-new.txt",saveFilePath_old+".mid");
			
			String saveFilePath=preprocessSavePathTextField.getText();
						
			combineFile(saveFilePath_old+".mid","./data/requirements-new.txt",saveFilePath);

		} catch (Exception e) {
			System.out.println(e);
		}
		preprocessBtn.setEnabled(true);
	}

	private void rankBtnMouseClicked(MouseEvent evt) {
		rankBtn.setEnabled(false);
		try {
			DataRespo.init(textAnalyzeTextField.getText());
			HybridSim.init(coauthorTextField.getText());
			ReqCodeTracing.predict(rankTextField.getText() + " IR-only/");
			
			//ModuleMap map = new ModuleMap(moduleTextField.getText());
			//DeltaScore ds = new DeltaScore(map, 0.8);
			//ds.moduleDir(rankTextField.getText() + "IR-only/",
			//		rankTextField.getText() + "IR-Mod/");
			int recCount = Integer.parseInt(recCountTextField.getText().trim());
			
			ModuleRecommender recommeder = 
					new ModuleRecommender(coauthorTextField.getText(), 4, recCount);
			recommeder.recommendDir(rankTextField.getText() + " IR-only/", rankTextField.getText() + " IR-mod_rec/");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
		}
		rankBtn.setEnabled(true);
	}

	private void evalBtnMouseClicked(MouseEvent evt) {
		evalBtn.setEnabled(false);
		try {
			//DataRespo.init(textAnalyzeTextField.getText());
			GroundTruth.init(evalTextField.getText());
			//System.out
			//		.println("r1009 : " + GroundTruth.getGroundTruth("r1009"));

			double threshold = 0.1; // 0.15
			int b = 1;
			int topK = 10;

			Evaluation[] es = new Evaluation[3];
			//es[0] = new ThresholdRecallEval(threshold);
			//es[1] = new ThresholdPrecisionEval(threshold);
			//es[2] = new FMeasureEval(threshold, b);
			es[0] = new TopKPrecisionEval(topK);
			es[1] = new TopKRecallEval(topK);
			//es[2] = new FMeasureEval(threshold, b);
			//es[2] = new GenericFMeasureEval(topK, b);
			es[2] = new GenericFMeasureEval(es[0], es[1] , b);
			ReqCodeTracing.setEvaluations(es);

			String resultStr = ReqCodeTracing.evalAll(rankTextField.getText());
			// System.out.println(resultStr);

			JFrame frame = new JFrame();
			ResultJDialog inst = new ResultJDialog(frame);
			inst.setResult(resultStr);
			inst.setVisible(true);
			
			FileWriter fw = new FileWriter(evalResultTextField.getText());  
			fw.write(resultStr);
			fw.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		evalBtn.setEnabled(true);
	}
	
	// 合并两个文件
	public void combineFile(String from1, String from2, String toFile) throws Exception {
		File f1 = new File(from1);
		File f2 = new File(from2);
		File f3 = new File(toFile);
		FileInputStream fis = new FileInputStream(f1);
		FileInputStream fis1 = new FileInputStream(f2);
		InputStream sis = new SequenceInputStream(fis, fis1);
		InputStreamReader isr = new InputStreamReader(sis);
		FileOutputStream fos = new FileOutputStream(f3, true);
		OutputStreamWriter osw = new OutputStreamWriter(fos);
		int c;
		while((c=isr.read())!=-1){
			osw.write((char)c);
		}
		System.out.println("ok....");
		isr.close();
		osw.close();
	}
	
	private void jButton12MouseClicked(MouseEvent evt) {
		jTextField1.setText(chooseFile());
	}
}
