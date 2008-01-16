/**
 * 
 */
package gov.nih.nci.caxchange.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.Timer;
import javax.swing.WindowConstants;
import javax.swing.border.Border;

import org.apache.log4j.Logger;

/**
 * @author asharma
 *
 */
public class TestCancerCenterClientUI extends JPanel implements ActionListener {

	private String csvDirectory;
	private String hl7v2Directory;
	private String mapDirectory;
	private String hl7v2mapDirectory;
	private String processedDirectory;
	private static JFrame aWindow = new JFrame("File Conversion UI");

	private JTextField jtxtHL7V2Dir = new JTextField();
	private JTextField jtxtProcessedFilesDir = new JTextField();
	private JTextField jtxtCSVDir = new JTextField();
	private JTextField jtxtMAPDir = new JTextField();
	private JTextField jtxtMAPHL7V2Dir = new JTextField();
	private JTextField jtxtPollingInterval = new JTextField();

	private DefaultListModel msgDispBox = new DefaultListModel();
	private JList dispList = new JList();
	private static Logger logger = Logger.getLogger("gov.nih.nci.caxchange.client.TestCancerCenterClientUI");
    private BufferedReader buffReader; 
    private Border blackline = BorderFactory.createLineBorder(Color.black);
    
	/**
	 * Default constructor.
	 */
	public TestCancerCenterClientUI() {
		init();
		aWindow.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		msgDispBox.addElement("Progress Shown Here");

		//Add the tabs to the frame
		JTabbedPane jtab = new JTabbedPane();
		JPanel jpanelMain = createMainPanel();
		jtab.addTab("Main", jpanelMain);
		JPanel jpanelStatus = createStatusPanel();
		jtab.addTab("Status", jpanelStatus);
		aWindow.getContentPane().add(jtab, BorderLayout.CENTER);
		aWindow.setSize(950, 450);
		aWindow.setVisible(true);

	}
	
	/**
	 * The init() method is used to read the default properties file and initialize the 
	 * input text fields with the user selected preferences. If the Tool is run for the 
	 * first time; the text fields are blank. 
	 */
	public void init()
	{
		
	}

	/**
	 * Creates the Main Panel
	 * @return jplPanel
	 */
	protected JPanel createMainPanel() {
		
		//Heading display box
		String text = "Accepts and saves the user entered values to perform";
		String text1 = "the csv to HL7V3 and HL7V2 to HL7V3 conversion";
		String star = "* Please select/enter all the form fields";
		JPanel jplPanel = new JPanel();
		jplPanel.setLayout(new BoxLayout(jplPanel, BoxLayout.PAGE_AXIS));
		
		JLabel jlbDisplay = new JLabel(text);
		JLabel jlbDisplay1 = new JLabel(text1);
		jlbDisplay.setForeground(Color.BLUE);
		jlbDisplay1.setForeground(Color.BLUE);
		jlbDisplay.setFont(new Font("Serif", Font.BOLD, 15));
		jlbDisplay1.setFont(new Font("Serif", Font.BOLD, 15));
		Box topBox = Box.createVerticalBox();
		topBox.add(Box.createVerticalStrut(10));
		topBox.add(jlbDisplay);
		topBox.add(jlbDisplay1);
		
		
		Box csvBox = Box.createVerticalBox();
		csvBox.setBorder(BorderFactory.createTitledBorder(blackline, "CSV"));
		csvBox.add(Box.createVerticalStrut(20));
		//add the csv, map & HL7V3 file directory selection 
		Box csvBox1 = Box.createHorizontalBox();
		csvBox1.add(Box.createHorizontalStrut(5));
		JLabel jlbCSVLabel = new JLabel(
				"Select the directory to choose the .csv file");
		csvBox1.add(jlbCSVLabel);
		csvBox1.add(jtxtCSVDir);
		JButton jfileCSV = new JButton("Browse...");
		jfileCSV.addActionListener(this);
		jfileCSV.setActionCommand("BrowseCSVFile");
		jtxtCSVDir.add(jfileCSV);
		csvBox1.add(jfileCSV);
		csvBox.add(csvBox1);

		Box csvBox2 = Box.createHorizontalBox();
		csvBox2.add(Box.createHorizontalStrut(5));
		JLabel jlbMAPLabel = new JLabel(
				"Select the directory to choose the .map file");
		
		csvBox2.add(jlbMAPLabel);
		csvBox2.add(jtxtMAPDir);
		JButton jfileMAP = new JButton("Browse...");
		jfileMAP.addActionListener(this);
		jfileMAP.setActionCommand("BrowseMAPFile");
		jtxtMAPDir.add(jfileMAP);
		csvBox2.add(jfileMAP);
		csvBox.add(csvBox2);

		//HL7V2 box
		Box HL7V2Box = Box.createVerticalBox();
		HL7V2Box.setBorder(BorderFactory.createTitledBorder(blackline, "HL7V2"));
		HL7V2Box.add(Box.createVerticalStrut(10));
		Box csvBox3 = Box.createHorizontalBox();
		csvBox3.add(Box.createHorizontalStrut(5));
		JLabel jlbHL7V2Label = new JLabel(
				"Select the directory to choose the HL7V2 file");
		
		csvBox3.add(jlbHL7V2Label);
		csvBox3.add(jtxtHL7V2Dir);
		JButton jfileHL7V2 = new JButton("Browse...");
		jfileHL7V2.addActionListener(this);
		jfileHL7V2.setActionCommand("BrowseHL7V2File");
		jtxtHL7V2Dir.add(jfileHL7V2);
		csvBox3.add(jfileHL7V2);
		HL7V2Box.add(csvBox3);
		
		Box csvBox6 = Box.createHorizontalBox();
		csvBox6.add(Box.createHorizontalStrut(5));
		JLabel jlbMAPHL7V2Label = new JLabel(
				"Select the directory to choose the .map file");
		
		csvBox6.add(jlbMAPHL7V2Label);
		csvBox6.add(jtxtMAPHL7V2Dir);
		JButton jfileMAPHL7V2 = new JButton("Browse...");
		jfileMAPHL7V2.addActionListener(this);
		jfileMAPHL7V2.setActionCommand("BrowseMAPHL7V2File");
		jtxtMAPHL7V2Dir.add(jfileMAPHL7V2);
		csvBox6.add(jfileMAPHL7V2);
		HL7V2Box.add(csvBox6);
		
		//Processed files and polling interval box
		Box csvBox4 = Box.createVerticalBox();
		csvBox4.add(Box.createVerticalStrut(10));
		csvBox4.setBorder(BorderFactory.createLineBorder(Color.black));
		
		JLabel jlbProcessedLabel = new JLabel(
				"Select the directory to put the Processed file(s)");
		Box thirdBox = Box.createHorizontalBox();
		thirdBox.add(Box.createHorizontalStrut(5));
		thirdBox.add(jlbProcessedLabel);
		thirdBox.add(jtxtProcessedFilesDir);
		JButton jfileProcessed = new JButton("Browse...");
		jfileProcessed.addActionListener(this);
		jfileProcessed.setActionCommand("BrowseProcessedDir");
		jtxtProcessedFilesDir.add(jfileProcessed);
		thirdBox.add(jfileProcessed);
		csvBox4.add(thirdBox);
		
		Box csvBox5 = Box.createHorizontalBox();
		csvBox5.add(Box.createHorizontalStrut(5));
		JLabel jlbPollingLabel = new JLabel(
				"Enter the directory polling interval in seconds");
		csvBox5.add(jlbPollingLabel);
		jtxtPollingInterval.addFocusListener(new FocusAdapter() {
	         public void focusLost(FocusEvent e) {
	             JTextField textField =
	               (JTextField)e.getSource();
	             String content = textField.getText();
	             if (content.length() != 0) {
	               try {
	                 Integer.parseInt(content);
	               } catch (NumberFormatException nfe) {
	                 getToolkit().beep();
	                 textField.requestFocus();
	               }
	             }
	           }
	         });
		csvBox5.add(jtxtPollingInterval);
		csvBox4.add(csvBox5);
		
		//Button box
		Box buttonBox = Box.createVerticalBox();
		buttonBox.add(Box.createVerticalStrut(10));
		
		Box csvBox7 = Box.createHorizontalBox();
		csvBox7.add(Box.createHorizontalStrut(5));
		JButton jbnClear = new JButton("Clear");
		jbnClear.addActionListener(this);
		jbnClear.setActionCommand("Clear");
		csvBox7.setAlignmentX(Component.LEFT_ALIGNMENT);
		csvBox7.add(jbnClear);
		
		JButton jbnAccept = new JButton("Accept");
		jbnAccept.addActionListener(this);
		jbnAccept.setActionCommand("Accept");
		csvBox7.add(jbnAccept);
		buttonBox.add(csvBox7);
		JLabel jlbStar = new JLabel(star);
		jlbStar.setForeground(Color.RED);
		jlbStar.setFont(new Font("Ariel", Font.ITALIC, 12));
		jlbStar.setAlignmentX(Component.RIGHT_ALIGNMENT);
		buttonBox.add(jlbStar);
		
		jplPanel.add(topBox);
		jplPanel.add(csvBox);
		jplPanel.add(HL7V2Box);
		jplPanel.add(csvBox4);
		jplPanel.add(buttonBox);
		return jplPanel;
	}

	/**
	 * Creates the Status Panel
	 * @return jplPanel
	 */
	protected JPanel createStatusPanel() {
		
		dispList = new JList(msgDispBox);
		dispList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		dispList.setSelectedIndex(0);
		dispList.setFont(new Font("Ariel",Font.PLAIN,15));
		dispList.setForeground(Color.BLUE);
		JScrollPane listScrollPane = new JScrollPane(dispList);
		JPanel jplPanel = new JPanel();
		jplPanel.setLayout(new GridLayout(1, 1));
		jplPanel.add(listScrollPane);
		return jplPanel;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TestCancerCenterClientUI testUI = new TestCancerCenterClientUI();

	}

	/**
	 * actionPerformed is called whenever an action is executed on the swing
	 * interface.
	 * <P>
	 * @param e The ActionEvent that was fired
	 */
	public void actionPerformed(ActionEvent e) {
		if ("BrowseCSVFile".equals(e.getActionCommand())) {
			msgDispBox.addElement("Selecting the CVS file directory");
			JFileChooser fileChooser = new JFileChooser() {
				public void updateUI() {
					putClientProperty("FileChooser.useShellFolder",
							Boolean.FALSE);
					super.updateUI();
				}
			};
			fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int result = fileChooser.showSaveDialog(this);
			if (result != JFileChooser.CANCEL_OPTION) {
				csvDirectory = fileChooser.getSelectedFile().getAbsolutePath();
				jtxtCSVDir.setText(csvDirectory);
			}
		} else if ("BrowseMAPFile".equals(e.getActionCommand())) {
			msgDispBox.addElement("Selecting the MAP file directory");
			JFileChooser fileChooser = new JFileChooser() {
				public void updateUI() {
					putClientProperty("FileChooser.useShellFolder",
							Boolean.FALSE);
					super.updateUI();
				}
			};
			fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			int result = fileChooser.showSaveDialog(this);
			if (result != JFileChooser.CANCEL_OPTION) {
				mapDirectory = fileChooser.getSelectedFile().getAbsolutePath();
				jtxtMAPDir.setText(mapDirectory);
			}
		}else if ("BrowseMAPHL7V2File".equals(e.getActionCommand())) {
			msgDispBox.addElement("Selecting the HL7V2 MAP file directory");
			JFileChooser fileChooser = new JFileChooser() {
				public void updateUI() {
					putClientProperty("FileChooser.useShellFolder",
							Boolean.FALSE);
					super.updateUI();
				}
			};
			fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			int result = fileChooser.showSaveDialog(this);
			if (result != JFileChooser.CANCEL_OPTION) {
				hl7v2mapDirectory = fileChooser.getSelectedFile().getAbsolutePath();
				jtxtMAPHL7V2Dir.setText(hl7v2mapDirectory);
			}
		} else if ("BrowseHL7V2File".equals(e.getActionCommand())) {
			msgDispBox.addElement("Selecting the HL7V2 file directory");
			JFileChooser fileChooser = new JFileChooser() {
				public void updateUI() {
					putClientProperty("FileChooser.useShellFolder",
							Boolean.FALSE);
					super.updateUI();
				}
			};
			fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int result = fileChooser.showSaveDialog(this);
			if (result != JFileChooser.CANCEL_OPTION) {
				hl7v2Directory = fileChooser.getSelectedFile().getAbsolutePath();
				jtxtHL7V2Dir.setText(hl7v2Directory);
			}
		}else if ("BrowseProcessedDir".equals(e.getActionCommand())) {
			msgDispBox.addElement("Selecting the directory for Processed files");
			JFileChooser fileChooser = new JFileChooser() {
				public void updateUI() {
					putClientProperty("FileChooser.useShellFolder",
							Boolean.FALSE);
					super.updateUI();
				}
			};
			fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int result = fileChooser.showSaveDialog(this);
			if (result != JFileChooser.CANCEL_OPTION) {
				processedDirectory = fileChooser.getSelectedFile().getAbsolutePath();
				jtxtProcessedFilesDir.setText(processedDirectory);
			}
		}else if ("Clear".equals(e.getActionCommand())) {
			jtxtHL7V2Dir.setText("");
			jtxtCSVDir.setText("");
			jtxtMAPDir.setText("");
			jtxtPollingInterval.setText("");
		} 
		else if ("Accept".equals(e.getActionCommand())) {
			msgDispBox.addElement("Saving the selection");
			saveDefaults();
		     	TestCancerCenterClient testClient = new TestCancerCenterClient();
				testClient.test();
				//updating the staus panel with the log file updates.
				int delay = 100; //milliseconds
				  ActionListener taskPerformer = new ActionListener() {
				      public void actionPerformed(ActionEvent evt) {
				          //...Perform a task...
				      try{
				    	  FileReader fipStream = new FileReader("../log/CancerCenterClient.log");
				       	  buffReader =new BufferedReader(fipStream);
							while(buffReader.readLine()!=null)
							{
							msgDispBox.addElement(buffReader.readLine());
							}	
				        }  catch (FileNotFoundException e1) {
							logger.error("FileNotFoundException"+e1.getLocalizedMessage());
						} catch (IOException e2) {
							// TODO Auto-generated catch block
							logger.error("IOException"+e2.getLocalizedMessage());
						}
					}
				   };
				  new Timer(delay, taskPerformer).start();
		}
	}
	
	/**
	 * Saves the user selected values into DefaultProperties.properties file.
	 */
	private void saveDefaults()
	{
	  File file =new File("D:/Development/CancerCenterClient/src/java/main/properties/DefaultProperties.properties");
	  if(file.exists())
	  {
		  boolean wasDeleted = file.delete();
		  if(wasDeleted){
			   file =new File("D:/Development/CancerCenterClient/src/java/main/properties/DefaultProperties.properties");	  
		  }	  
	  }		  
	  try {
		FileWriter fstream = new FileWriter(file);
		fstream.write("CVSDir="+csvDirectory);
		fstream.write("\n");
		fstream.write("MAPDir="+mapDirectory);
		fstream.write("\n");
		fstream.write("HL7V2Dir="+hl7v2Directory);
		fstream.write("\n");
		fstream.write("ProcessedDir="+processedDirectory);
		fstream.write("\n");
		fstream.write("PollInterval="+jtxtPollingInterval.getText());
		fstream.write("\n");
		fstream.flush();
		fstream.close();
		
	    } catch (IOException e) {
		logger.error("File not found"+e.getLocalizedMessage());
	  }
	  
	}
	
	
}
