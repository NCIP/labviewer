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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

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
import javax.swing.JPasswordField;
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

	
	private static final long serialVersionUID = 1L;
	private String csvDirectory;
	private String hl7v2Directory;
	private String mapDirectory;
	private String hl7v2mapDirectory;
	private String processedDirectory;
	private String inProcessDirectory;
	private String preProcessorFile;
	private File inProcessFolder;
	private File rawFilesBackupFolder;
	private File errorFolder;
	private static JFrame aWindow = new JFrame("Cancer Center Hub Client (CCHC)");

	private JTextField jtxtHL7V2Dir = new JTextField();
	private JTextField jtxtProcessedFilesDir = new JTextField();
	private JTextField jtxtCSVDir = new JTextField();
	private JTextField jtxtMAPDir = new JTextField();
	private JTextField jtxtMAPHL7V2Dir = new JTextField();
	private JTextField jtxtInProcessFilesDir = new JTextField();
	private JTextField jtxtPollingInterval = new JTextField();
	private JTextField jtxtInitialInterval = new JTextField();
	private JTextField jtxtLocation = new JTextField();
	//private JTextField jtxtIDPLocation = new JTextField();
	private JTextField jtxtOrgName = new JTextField();
	private JTextField jtxtUserName = new JTextField();
	private JTextField jtxtPassword = new JPasswordField();
	private JTextField jtxtHubURL = new JTextField();
	private JTextField jtxtstudyLookupServiceURL = new JTextField();
	private JTextField jtxtpreProcessorProFile = new JTextField();
	private DefaultListModel msgDispBox = new DefaultListModel();
	private JList dispList = new JList();
	private static Logger logger = Logger
			.getLogger("gov.nih.nci.caxchange.client.TestCancerCenterClientUI");
	private BufferedReader buffReader;
	private Border blackline = BorderFactory.createLineBorder(Color.black);

	/**
	 * Default constructor.
	 */
	public TestCancerCenterClientUI() {
		init();
		aWindow.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		aWindow.addWindowListener(new WindowAdapter() {
			public void windowClosed(WindowEvent e) {
			  System.exit(0);
			}
			});
		msgDispBox.addElement("Progress Shown Here");

		//Add the tabs to the frame
		JTabbedPane jtab = new JTabbedPane();
		JPanel jpanelMain = createMainPanel();
		jtab.addTab("Main", jpanelMain);
		JPanel jpanelStatus = createStatusPanel();
		jtab.addTab("Status", jpanelStatus);
		aWindow.getContentPane().add(jtab, BorderLayout.CENTER);
		aWindow.setSize(950, 650);
		aWindow.setVisible(true);

	}

	/**
	 * The init() method is used to read the default properties file and initialize the 
	 * input text fields with the user selected preferences. If the Tool is run for the 
	 * first time; the text fields are blank. 
	 */
	public void init() {

		try {
			/*InputStream is = TestCancerCenterClientUI.class.getClassLoader().
								getResourceAsStream("./properties/DefaultProperties.properties");
			*/
			String path = System.getProperty("DefaultProperties.File");
			FileInputStream is = new FileInputStream(new File(path));
			if (is!=null) {

				Properties props = new Properties();
				//Read in the stored properties
				props.load(is);
				jtxtHL7V2Dir.setText(props.getProperty("HL7V2Dir"));
				jtxtCSVDir.setText(props.getProperty("rawFilesFolder"));
				jtxtMAPDir.setText(props.getProperty("mapFileName"));
				jtxtPollingInterval.setText(props
						.getProperty("pollingDelayInSeconds"));
				jtxtProcessedFilesDir.setText(props
						.getProperty("processedFolder"));
				jtxtMAPHL7V2Dir.setText(props.getProperty("hl7v2mapFileName"));
				jtxtInProcessFilesDir.setText(props
						.getProperty("inProcessDirectory"));
				jtxtInitialInterval.setText(props
						.getProperty("initialDelayInSeconds"));
				jtxtLocation.setText(props.getProperty("Location"));
				//jtxtIDPLocation.setText(props.getProperty("IDPLocation"));
				jtxtOrgName.setText(props.getProperty("ORGANIZATIONNAME"));
				jtxtUserName.setText(props.getProperty("userName"));
				jtxtPassword.setText(props.getProperty("userPasswd"));
				jtxtHubURL.setText(props.getProperty("HubURL"));
				jtxtpreProcessorProFile.setText(props
						.getProperty("preProcessorPropertiesFile"));

				csvDirectory = jtxtCSVDir.getText();
				inProcessDirectory = jtxtInProcessFilesDir.getText();
				mapDirectory = jtxtMAPDir.getText();
				hl7v2Directory = jtxtHL7V2Dir.getText();
				hl7v2mapDirectory = jtxtMAPHL7V2Dir.getText();
				processedDirectory = jtxtProcessedFilesDir.getText();
				preProcessorFile = jtxtpreProcessorProFile.getText();
			}
		} catch (Exception e) {
			logger.error("Exception processing Cancer Center Properties File");
		}
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
		csvBox.add(Box.createVerticalStrut(10));
		//add the csv, map & HL7V3 file directory selection 
		Box csvBox1 = Box.createHorizontalBox();
		csvBox1.add(Box.createHorizontalStrut(10));
		JLabel jlbCSVLabel = new JLabel("Select the raw files directory");
		csvBox1.add(jlbCSVLabel);
		csvBox1.add(Box.createHorizontalStrut(10));
		csvBox1.add(jtxtCSVDir);
		JButton jfileCSV = new JButton("Browse...");
		jfileCSV.addActionListener(this);
		jfileCSV.setActionCommand("BrowseCSVFile");
		jtxtCSVDir.add(jfileCSV);
		csvBox1.add(jfileCSV);
		csvBox.add(csvBox1);

		Box csvBox2 = Box.createHorizontalBox();
		csvBox2.add(Box.createHorizontalStrut(10));
		JLabel jlbMAPLabel = new JLabel("Select the MAP file");

		csvBox2.add(jlbMAPLabel);
		csvBox2.add(Box.createHorizontalStrut(67));
		csvBox2.add(jtxtMAPDir);
		JButton jfileMAP = new JButton("Browse...");
		jfileMAP.addActionListener(this);
		jfileMAP.setActionCommand("BrowseMAPFile");
		jtxtMAPDir.add(jfileMAP);
		csvBox2.add(jfileMAP);
		csvBox.add(csvBox2);

		//HL7V2 box
		Box HL7V2Box = Box.createVerticalBox();
		HL7V2Box
				.setBorder(BorderFactory.createTitledBorder(blackline, "HL7V2"));
		HL7V2Box.add(Box.createVerticalStrut(10));
		Box csvBox3 = Box.createHorizontalBox();
		csvBox3.add(Box.createHorizontalStrut(10));
		JLabel jlbHL7V2Label = new JLabel("Select the HL7V2 directory");

		csvBox3.add(jlbHL7V2Label);
		csvBox3.add(Box.createHorizontalStrut(15));
		csvBox3.add(jtxtHL7V2Dir);
		JButton jfileHL7V2 = new JButton("Browse...");
		jfileHL7V2.addActionListener(this);
		jfileHL7V2.setActionCommand("BrowseHL7V2File");
		jtxtHL7V2Dir.add(jfileHL7V2);
		csvBox3.add(jfileHL7V2);
		HL7V2Box.add(csvBox3);

		Box csvBox6 = Box.createHorizontalBox();
		csvBox6.add(Box.createHorizontalStrut(10));
		JLabel jlbMAPHL7V2Label = new JLabel("Select the MAP file");
		csvBox6.add(jlbMAPHL7V2Label);
		csvBox6.add(Box.createHorizontalStrut(60));
		csvBox6.add(jtxtMAPHL7V2Dir);
		JButton jfileMAPHL7V2 = new JButton("Browse...");
		jfileMAPHL7V2.addActionListener(this);
		jfileMAPHL7V2.setActionCommand("BrowseMAPHL7V2File");
		jtxtMAPHL7V2Dir.add(jfileMAPHL7V2);
		csvBox6.add(jfileMAPHL7V2);
		HL7V2Box.add(csvBox6);

		//1. Processed files & In process directory 2. polling delay & Initial delay
		Box csvBox4 = Box.createVerticalBox();
		csvBox4.add(Box.createVerticalStrut(10));
		csvBox4.setBorder(BorderFactory.createTitledBorder(blackline,
				"Common Settings"));
		JLabel jlbPreProcessedLabel = new JLabel(
				"Select the Pre Processed Property file");
		Box secondBox = Box.createHorizontalBox();
		secondBox.add(Box.createHorizontalStrut(10));
		secondBox.add(jlbPreProcessedLabel);
		secondBox.add(Box.createHorizontalStrut(10));
		secondBox.add(jtxtpreProcessorProFile);
		JButton jfilePreProcessed = new JButton("Browse...");
		jfilePreProcessed.addActionListener(this);
		jfilePreProcessed.setActionCommand("BrowsePreProcessedFile");
		jtxtpreProcessorProFile.add(jfilePreProcessed);
		secondBox.add(jfilePreProcessed);
		csvBox4.add(secondBox);

		JLabel jlbProcessedLabel = new JLabel(
				"Select the Processed file(s) directory");
		Box thirdBox = Box.createHorizontalBox();
		thirdBox.add(Box.createHorizontalStrut(10));
		thirdBox.add(jlbProcessedLabel);
		thirdBox.add(Box.createHorizontalStrut(15));
		thirdBox.add(jtxtProcessedFilesDir);
		JButton jfileProcessed = new JButton("Browse...");
		jfileProcessed.addActionListener(this);
		jfileProcessed.setActionCommand("BrowseProcessedDir");
		jtxtProcessedFilesDir.add(jfileProcessed);
		thirdBox.add(jfileProcessed);
		csvBox4.add(thirdBox);

		JLabel jlbInProcessLabel = new JLabel(
				"Select the InProcess file(s) directory");
		Box fourthBox = Box.createHorizontalBox();
		fourthBox.add(Box.createHorizontalStrut(10));
		fourthBox.add(jlbInProcessLabel);
		fourthBox.add(Box.createHorizontalStrut(19));
		fourthBox.add(jtxtInProcessFilesDir);
		JButton jfileInProcess = new JButton("Browse...");
		jfileInProcess.addActionListener(this);
		jfileInProcess.setActionCommand("BrowseInProcessDir");
		jtxtInProcessFilesDir.add(jfileInProcess);
		fourthBox.add(jfileInProcess);
		csvBox4.add(fourthBox);

		Box csvBox5 = Box.createHorizontalBox();
		csvBox5.add(Box.createHorizontalStrut(10));
		JLabel jlbPollingLabel = new JLabel(
				"Enter the polling delay in seconds");
		csvBox5.add(jlbPollingLabel);
		csvBox5.add(Box.createHorizontalStrut(37));
		jtxtPollingInterval.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				JTextField textField = (JTextField) e.getSource();
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
		csvBox5.add(Box.createHorizontalStrut(75));
		JLabel jlbInitialLabel = new JLabel(
				"Enter the Initial delay in seconds");
		csvBox5.add(jlbInitialLabel);
		csvBox5.add(Box.createHorizontalStrut(19));
		jtxtInitialInterval.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				JTextField textField = (JTextField) e.getSource();
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
		csvBox5.add(jtxtInitialInterval);
		csvBox4.add(csvBox5);

		//Location and IDPLocation
		Box csvBox8 = Box.createHorizontalBox();
		csvBox8.add(Box.createHorizontalStrut(10));
		JLabel jlbLocationLabel = new JLabel("Enter the Location name");
		csvBox8.add(jlbLocationLabel);
		csvBox8.add(Box.createHorizontalStrut(88));
		csvBox8.add(jtxtLocation);
		csvBox4.add(csvBox8);
	/*	csvBox8.add(Box.createHorizontalStrut(125));
		JLabel jlbIDPLocationLabel = new JLabel("Enter the IDP Location");
		csvBox8.add(jlbIDPLocationLabel);
		csvBox8.add(Box.createHorizontalStrut(120));
		csvBox8.add(jtxtIDPLocation);*/
		csvBox4.add(csvBox8);

		//Organization name
		Box csvBox9 = Box.createHorizontalBox();
		csvBox9.add(Box.createHorizontalStrut(10));
		JLabel jlbOrgNameLabel = new JLabel("Enter the Organization name");
		csvBox9.add(jlbOrgNameLabel);
		csvBox9.add(Box.createHorizontalStrut(65));
		csvBox9.add(jtxtOrgName);
		csvBox4.add(csvBox9);

		//Hub URL
		Box csvBox10 = Box.createHorizontalBox();
		csvBox10.add(Box.createHorizontalStrut(10));
		JLabel jlbhubURLLabel = new JLabel("Enter the Hub URL");
		csvBox10.add(jlbhubURLLabel);
		csvBox10.add(Box.createHorizontalStrut(124));
		csvBox10.add(jtxtHubURL);
		csvBox4.add(csvBox10);

		//User Creds
		Box userBox = Box.createVerticalBox();
		userBox.add(Box.createVerticalStrut(10));
		userBox.setBorder(BorderFactory.createTitledBorder(blackline,
				"User Credentials"));

		Box csvBox11 = Box.createHorizontalBox();
		csvBox11.add(Box.createHorizontalStrut(10));
		JLabel jlbuserNameLabel = new JLabel("Enter the User Name");
		csvBox11.add(jlbuserNameLabel);
		csvBox11.add(Box.createHorizontalStrut(105));
		csvBox11.add(jtxtUserName);
		userBox.add(csvBox11);
		csvBox11.add(Box.createHorizontalStrut(10));
		JLabel jlbpasswordLabel = new JLabel("Enter the Password");
		csvBox11.add(jlbpasswordLabel);
		csvBox11.add(Box.createHorizontalStrut(10));
		csvBox11.add(jtxtPassword);
		userBox.add(csvBox11);

		//Button box
		Box buttonBox = Box.createVerticalBox();
		buttonBox.add(Box.createVerticalStrut(10));

		Box csvBox7 = Box.createHorizontalBox();
		csvBox7.add(Box.createHorizontalStrut(10));
		JButton jbnClear = new JButton("Clear");
		jbnClear.addActionListener(this);
		jbnClear.setActionCommand("Clear");
		csvBox7.setAlignmentX(Component.CENTER_ALIGNMENT);
		csvBox7.add(jbnClear);

		JButton jbnAccept = new JButton("Accept");
		jbnAccept.addActionListener(this);
		jbnAccept.setActionCommand("Accept");
		csvBox7.add(jbnAccept);
		JButton jbnClose = new JButton("Close");
		jbnClose.addActionListener(this);
		jbnClose.setActionCommand("Close");
		csvBox7.setAlignmentX(Component.CENTER_ALIGNMENT);
		csvBox7.add(jbnClose);
		buttonBox.add(csvBox7);
		JLabel jlbStar = new JLabel(star);
		jlbStar.setForeground(Color.RED);
		jlbStar.setFont(new Font("Ariel", Font.ITALIC, 12));
		jlbStar.setAlignmentX(Component.CENTER_ALIGNMENT);
		buttonBox.add(jlbStar);

		jplPanel.add(topBox);
		jplPanel.add(csvBox);
		jplPanel.add(HL7V2Box);
		jplPanel.add(csvBox4);
		jplPanel.add(userBox);
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
		dispList.setFont(new Font("Ariel", Font.PLAIN, 15));
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
			JFileChooser fileChooser = fileChoose();
			fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int result = fileChooser.showSaveDialog(this);
			if (result != JFileChooser.CANCEL_OPTION) {
				csvDirectory = fileSeparatorReplace(fileChooser
						.getSelectedFile().getAbsolutePath());
				jtxtCSVDir.setText(csvDirectory);
			}
		} else if ("BrowseMAPFile".equals(e.getActionCommand())) {
			msgDispBox.addElement("Selecting the MAP file directory");
			JFileChooser fileChooser = fileChoose();
			fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			int result = fileChooser.showSaveDialog(this);
			if (result != JFileChooser.CANCEL_OPTION) {
				mapDirectory = fileSeparatorReplace(fileChooser
						.getSelectedFile().getAbsolutePath());
				jtxtMAPDir.setText(mapDirectory);
			}
		} else if ("BrowseMAPHL7V2File".equals(e.getActionCommand())) {
			msgDispBox.addElement("Selecting the HL7V2 MAP file directory");
			JFileChooser fileChooser = fileChoose();
			fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			int result = fileChooser.showSaveDialog(this);
			if (result != JFileChooser.CANCEL_OPTION) {
				hl7v2mapDirectory = fileSeparatorReplace(fileChooser
						.getSelectedFile().getAbsolutePath());
				jtxtMAPHL7V2Dir.setText(hl7v2mapDirectory);
			}
		} else if ("BrowseHL7V2File".equals(e.getActionCommand())) {
			msgDispBox.addElement("Selecting the HL7V2 file directory");
			JFileChooser fileChooser = fileChoose();
			fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int result = fileChooser.showSaveDialog(this);
			if (result != JFileChooser.CANCEL_OPTION) {
				hl7v2Directory = fileSeparatorReplace(fileChooser
						.getSelectedFile().getAbsolutePath());
				jtxtHL7V2Dir.setText(hl7v2Directory);
			}
		} else if ("BrowseProcessedDir".equals(e.getActionCommand())) {
			msgDispBox
					.addElement("Selecting the directory for Processed files");
			JFileChooser fileChooser = fileChoose();
			fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int result = fileChooser.showSaveDialog(this);
			if (result != JFileChooser.CANCEL_OPTION) {
				processedDirectory = fileSeparatorReplace(fileChooser
						.getSelectedFile().getAbsolutePath());
				jtxtProcessedFilesDir.setText(processedDirectory);
			}
		} else if ("BrowseInProcessDir".equals(e.getActionCommand())) {
			msgDispBox
					.addElement("Selecting the directory for In Processed files");
			JFileChooser fileChooser = fileChoose();
			fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int result = fileChooser.showSaveDialog(this);
			if (result != JFileChooser.CANCEL_OPTION) {
				inProcessDirectory = fileSeparatorReplace(fileChooser
						.getSelectedFile().getAbsolutePath());
				jtxtInProcessFilesDir.setText(inProcessDirectory);
			}
		} else if ("BrowsePreProcessedFile".equals(e.getActionCommand())) {
			msgDispBox.addElement("Selecting the Pre Processed property file");
			JFileChooser fileChooser = fileChoose();
			fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			int result = fileChooser.showSaveDialog(this);
			if (result != JFileChooser.CANCEL_OPTION) {
				preProcessorFile = fileSeparatorReplace(fileChooser
						.getSelectedFile().getAbsolutePath());
				jtxtpreProcessorProFile.setText(preProcessorFile);
			}
		} else if ("Clear".equals(e.getActionCommand())) {
			jtxtHL7V2Dir.setText("");
			jtxtCSVDir.setText("");
			jtxtMAPDir.setText("");
			jtxtPollingInterval.setText("");
			jtxtProcessedFilesDir.setText("");
			jtxtMAPHL7V2Dir.setText("");
			jtxtInProcessFilesDir.setText("");
			jtxtInitialInterval.setText("");
			jtxtLocation.setText("");
			//jtxtIDPLocation.setText("");
			jtxtOrgName.setText("");
			jtxtUserName.setText("");
			jtxtPassword.setText("");
			jtxtHubURL.setText("");
			jtxtpreProcessorProFile.setText("");
		}else if ("Close".equals(e.getActionCommand())) {
			System.exit(0);
		}else if ("Accept".equals(e.getActionCommand())) {
			msgDispBox.addElement("Saving the selection");
			File file = saveDefaults();
			CancerCenterClient testClient = CancerCenterClient.getInstance();
			testClient.test(file);
			//updating the status panel with the log file updates.
			int delay = 10; //milliseconds
			try {
				FileReader fipStream = new FileReader(
						"../log/CancerCenterClient.log");
				buffReader = new BufferedReader(fipStream);
				ActionListener taskPerformer = new ActionListener() {
					public synchronized void actionPerformed(ActionEvent evt) {
						try{
							while (buffReader.readLine() != null) {
								msgDispBox.addElement(buffReader.readLine());
							}
						} catch (IOException e2) {
							logger.error("IOException"
									+ e2.getLocalizedMessage());
						}
					}
				};
				new Timer(delay, taskPerformer).start();
			}catch (FileNotFoundException e1) {
				logger
				.error("FileNotFoundException"
						+ e1.getLocalizedMessage());
			}
		}
	}

	/**
	 * Updates the UI with the path of the browsed directory
	 * @return fileChooser
	 */
	private JFileChooser fileChoose() {
		JFileChooser fileChooser = new JFileChooser() {
			public void updateUI() {
				putClientProperty("FileChooser.useShellFolder", Boolean.FALSE);
				super.updateUI();
			}
		};
		return fileChooser;
	}

	/**
	 * Replaces the file separator from '\' to'/' for the user selected directories.
	 * @param path
	 * @return placeholder
	 */
	private String fileSeparatorReplace(String path) {
		String placeholder;
		placeholder = path.replace('\\', '/');
		return placeholder;
	}

	/**
	 * Creates the inProcessFolder, rawFilesBackUpFolder, errorFolder in the user selected 
	 * inProcess Directory
	 * @return created
	 */
	private boolean createdInProcessFolders() {
		boolean created = true;
		inProcessFolder = new File(inProcessDirectory + "/inProcessFolder");
		if (!inProcessFolder.exists())
			created = inProcessFolder.mkdir();
		rawFilesBackupFolder = new File(inProcessDirectory
				+ "/rawFilesBackupFolder");
		if (!rawFilesBackupFolder.exists())
			created = rawFilesBackupFolder.mkdir();
		errorFolder = new File(inProcessDirectory + "/errorFolder");
		if (!errorFolder.exists())
			created = errorFolder.mkdir();
		return created;
	}

	/**
	 * Saves the user selected values into DefaultProperties.properties file.
	 * @return file DefaultProperties file
	 */
	private File saveDefaults() {
		String path =System.getProperty("DefaultProperties.File");
		File file = new File(path);
		try{
		  	if (createdInProcessFolders()) {
				FileWriter fstream = new FileWriter(file,false);
				fstream.write("rawFilesFolder=" + csvDirectory);
				fstream.write("\n");
				fstream.write("mapFileName=" + mapDirectory);
				fstream.write("\n");
				fstream.write("HL7V2Dir=" + hl7v2Directory);
				fstream.write("\n");
				fstream.write("hl7v2mapFileName=" + hl7v2mapDirectory);
				fstream.write("\n");
				fstream.write("inProcessDirectory=" + inProcessDirectory);
				fstream.write("\n");
				fstream.write("inProcessFolder="
						+ inProcessFolder.getAbsolutePath().replace('\\', '/')
						+ "/");
				fstream.write("\n");
				fstream.write("rawFilesBackupFolder="
						+ rawFilesBackupFolder.getAbsolutePath().replace('\\',
								'/') + "/");
				fstream.write("\n");
				fstream.write("errorFolder="
						+ errorFolder.getAbsolutePath().replace('\\', '/')
						+ "/");
				fstream.write("\n");
				fstream.write("processedFolder=" + processedDirectory);
				fstream.write("\n");
				fstream.write("pollingDelayInSeconds="
						+ jtxtPollingInterval.getText());
				fstream.write("\n");
				fstream.write("initialDelayInSeconds="
						+ jtxtInitialInterval.getText());
				fstream.write("\n");
				fstream.write("Location=" + jtxtLocation.getText());
				fstream.write("\n");
				/*fstream.write("IDPLocation=" + jtxtIDPLocation.getText());
				fstream.write("\n");*/
				fstream.write("ORGANIZATIONNAME=" + jtxtOrgName.getText());
				fstream.write("\n");
				fstream.write("userName=" + jtxtUserName.getText());
				fstream.write("\n");
				fstream.write("userPassword=" + jtxtPassword.getText());
				fstream.write("\n");
				fstream.write("HubURL=" + jtxtHubURL.getText());
				fstream.write("\n");
				fstream.write("preProcessorPropertiesFile=" + preProcessorFile);
				fstream.write("\n");
				fstream.flush();
				fstream.close();
				}
			
		} catch (IOException e) {
			logger.error("File not found" + e.getLocalizedMessage());
		}
	  return file;
	}

}
