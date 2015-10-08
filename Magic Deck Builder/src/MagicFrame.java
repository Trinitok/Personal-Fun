//  http://www.pinmobilesex.com/pics/t/990.jpg

import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.json.JSONObject;

import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * 
 * @author Max Kelly. Created Sept 19, 2015.
 */
@SuppressWarnings("javadoc")
public class MagicFrame extends JFrame {
	private Panel sp = new Panel();
	int TX = 60;
	int TY = 30;
	private Console cs = new Console();
	JTabbedPane tabs = new JTabbedPane();
	int tabNum = 0;
	HashMap<String, String[]> archived = new HashMap<String, String[]>();

	// constructor for the frame
	public MagicFrame() throws IOException {
		super("Magic the Gathering Deck Builder");
		this.setSize(800, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container content = getContentPane();
		content.setLayout(new BorderLayout());
		
		(new File("Magic the Gathering Archived Files")).mkdir();
		
		//  Add the conent for the control panel and button panel
		ControlPanel cpanel = new ControlPanel();

		content.add(cpanel, BorderLayout.WEST);
//		content.add(new ButtonPanel(), BorderLayout.WEST);
		
		MagicFrame.this.sp.addChangeListener(new ChangeListener() {
			int lastTabIndex = 0;
			
			@Override
			public void stateChanged(ChangeEvent e){
				int newIndex = MagicFrame.this.sp.getSelectedIndex();
				System.out.println("changing state");
				System.out.println("last index = " + lastTabIndex);
				System.out.println("new tab " + newIndex);
				
				if(lastTabIndex == 0 && newIndex == 1){
					// switch from archive to search
					cpanel.In.setVisible(true);
					cpanel.add.setVisible(true);
					cpanel.getComponent(0).setVisible(true);
					// hide all the buttons for archived cards
					if(cpanel.getComponentCount() > 3){
						for(int i = 3; i < cpanel.getComponentCount(); i ++){
							cpanel.getComponent(i).setVisible(false);
						}
					}
				}
				if(lastTabIndex == 0 && newIndex == 0){
					// switch from search to archive
					cpanel.In.setVisible(false);
					cpanel.add.setVisible(false);
					cpanel.getComponent(0).setVisible(false);
					// show all the buttons for archived cards
					if(cpanel.getComponentCount() > 3){
						for(int i = 3; i < cpanel.getComponentCount(); i ++){
							cpanel.getComponent(i).setVisible(true);
						}
					}
				}
				
			}
			
			
		});
		
		TitledBorder border = BorderFactory.createTitledBorder(
				BorderFactory.createLoweredBevelBorder(), "Display Panel");
		border.setTitleJustification(TitledBorder.LEFT);
		
		this.sp.setBorder(border);
		this.sp.setPreferredSize(new Dimension(1000,1000));
//		this.sp.addTab("test", this.sp);
		
//		this.sp.addTab("test", temporaryLostComponent);
		JScrollPane scrollPane = new JScrollPane(sp);
		
		// troll icon img if someone hax this
		String imageUrl = "http://www.pinmobilesex.com/pics/t/990.jpg";
        String imageDest = "magicFrameIcon.jpg";
        
        // check to see if the icon has already been downloaded so you do not have to go through it all again
        File test = new File(imageDest);
        if(!test.exists() && !test.isDirectory()){
        	saveImage(imageUrl, imageDest);
        	String os = System.getProperty("os.name");
        	
        }
        
        
        
        //  set the icon for the image
        BufferedImage image = ImageIO.read(new File("magicFrameIcon.jpg"));
        ImageIcon icon = new ImageIcon(image);
        this.setIconImage(icon.getImage());
		
        //  set the frame to the center of the screen
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		
		// add the archived tab
		JLabel lbl=new JLabel();
        MagicFrame.this.sp.add(lbl);
        
		
        //add search tab
        JLabel lbl2=new JLabel();
        MagicFrame.this.sp.add(lbl2);
        sp.setTitleAt(0, "Archived");
        sp.setTitleAt(1, "Search");
        sp.setSelectedIndex(1);
        
        
		
		//  add the various panels and console
		content.add(scrollPane, BorderLayout.CENTER);
		content.add(this.cs, BorderLayout.SOUTH);
		
		setVisible(true);
	}
	
	

	// save the icon img
	private void saveImage(String imageUrl, String destinationFile) throws IOException {
		imageUrl.replaceAll(" ", "+");
	    URL url = new URL(imageUrl);
	    InputStream is = url.openStream();
	    OutputStream os = new FileOutputStream(destinationFile);

	    byte[] b = new byte[2048];
	    int length;

	    while ((length = is.read(b)) != -1) {
	        os.write(b, 0, length);
	    }

	    is.close();
	    os.close();
		
	}

	//  generate everything
	public static void main(String[] args) throws IOException {
		
		
		// Change the look and feel of the frame to make it look prettier
		try {
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		} catch (Exception e) {
		    // If Nimbus is not available, you can set the GUI to another look and feel.
		}
		
		// Construct the frame here
		
		MagicFrame frame = new MagicFrame();
		
		

	}
	
	//  where I will put the jinputfield and the button for adding
	//  where I will put the buttons for archived files
	class ControlPanel extends JPanel {
		InputField In = new InputField();
		AddButton add = new AddButton();

		@SuppressWarnings("deprecation")
		public ControlPanel() {
			
			TitledBorder border = BorderFactory.createTitledBorder(
					BorderFactory.createLoweredBevelBorder(), "Control Panel");
			border.setTitleJustification(TitledBorder.LEFT);
			this.setBorder(border);
			this.setLayout(new FlowLayout());
			this.setSize(500,500);
			// ///////////////slider
			JSlider TXControl = new JSlider(SwingConstants.HORIZONTAL, 50, 1200,
					60);
			TXControl.addChangeListener(new SliderListener1());
			TXControl.setMajorTickSpacing(50);
			TXControl.setMinorTickSpacing(50);
			TXControl.setPaintTicks(true);
			TXControl.setPaintLabels(true);
			// //////////////////slider end
			// ///////////////slider
			JSlider TYControl = new JSlider(SwingConstants.HORIZONTAL, 30, 180,
					30);
			TYControl.addChangeListener(new SliderListener2());
			TYControl.setMajorTickSpacing(50);
			TYControl.setMinorTickSpacing(50);
			TYControl.setPaintTicks(true);
			TYControl.setPaintLabels(true);
			// //////////////////slider end
			
			this.In.setText("Full Name Here");
			
			this.add(new JLabel("Put in the full card name:"));
			this.add(this.In);
			this.add(this.add);
			this.loadArchivedFiles();
		}
		
		

		private void loadArchivedFiles() {
			File URL = new File("Magic the Gathering Archived Files");
			// check to see if files are there before moving forward
			if(!URL.exists()){
				return;
			}
			else{
				File[] archivedFiles = URL.listFiles();
				for(int i = 0; i < archivedFiles.length; i ++){
					if(archivedFiles[i].getName().contains(".jpg")){
						File curr = archivedFiles[i];
						JButton archivedFile = new JButton(archivedFiles[i].getName());
						archivedFile.addMouseListener(new MouseAdapter() {
							
							@Override
							public void mousePressed(MouseEvent e) {
								
					            BufferedImage image;
								try {
									
									image = ImageIO.read(curr);
									ImageIcon icon = new ImageIcon(image);
									MagicFrame.this.sp.remove(0);
						        	JLabel lbl=new JLabel();
							        lbl.setIcon(icon);
							        MagicFrame.this.sp.add(lbl, 0);
							        MagicFrame.this.sp.setTitleAt(0, "Archive");
							        MagicFrame.this.sp.setTitleAt(1, "Search");
						          
							        MagicFrame.this.sp.setSelectedIndex(0);
								} catch (IOException e1) {
									e1.printStackTrace();
								}
					            
					            
					            
					            
					            
							}
						});
						ControlPanel.this.add(archivedFile);
					}
				}
			}
				
		}



		class SliderListener1 implements ChangeListener {
			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider) e.getSource();
				if (!source.getValueIsAdjusting()) {
					MagicFrame.this.TX = source.getValue();
					MagicFrame.this.sp.repaint();
				}
			}
		}
		class SliderListener2 implements ChangeListener {
			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider) e.getSource();
				if (!source.getValueIsAdjusting()) {
					MagicFrame.this.TY = source.getValue();
					MagicFrame.this.sp.repaint();
				}
			}
		}

		//  addbutton for adding pictures to the frame
		class AddButton extends JButton {
			public AddButton() {
				super("Add");
				this.addMouseListener(new MouseAdapter() {
					
					@Override
					public void mousePressed(MouseEvent e) {
						
						
						
						
						//  Search the library of cards on gatherer
						try {
							
							// get the string and handle spaces for the google image search
							String a = ControlPanel.this.In
									.getText();
							a = a.replaceAll(" ", "+");
							// check to see if file exists
				            if(!(new File("Magic the Gathering Archived Files\\"+a + ".jpg").exists())){
							
							
							URL url = new URL("https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q="+a+"+magiccards.info");
				            URLConnection connection = url.openConnection();
				            System.out.println("Success 1");

				            String line;
				            StringBuilder builder = new StringBuilder();
				            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				            while((line = reader.readLine()) != null) {
				                builder.append(line);
				            }
				            System.out.println("Success 2");

				            JSONObject json = new JSONObject(builder.toString());
				            String imageUrl = json.getJSONObject("responseData").getJSONArray("results").getJSONObject(0).getString("url");
				            String imageDest = "Magic the Gathering Archived Files\\"+a + ".jpg";
				            System.out.println("Success 3");
				            
				            
					            saveImage(imageUrl, imageDest);
					            
					            
					         // add the new cards to the archive
					            JButton archivedCard = new JButton(a);
					            archivedCard.addMouseListener(new MouseAdapter() {
									
									@Override
									public void mousePressed(MouseEvent e) {
										File readThis = new File("Magic the Gathering Archived Files\\" + archivedCard.getText() + ".jpg");
							            BufferedImage image;
										try {
											image = ImageIO.read(readThis);
											ImageIcon icon = new ImageIcon(image);
											sp.remove(0);
								        	JLabel lbl=new JLabel();
									        lbl.setIcon(icon);
									        MagicFrame.this.sp.add(lbl, 0);
									        sp.setTitleAt(0, "Archive");
									        sp.setTitleAt(1, "Search");
								          
									        sp.setSelectedIndex(0);
										} catch (IOException e1) {
											// TODO Auto-generated catch block
											e1.printStackTrace();
										}
							            
							            
							            
							            
							            
									}
								});
					            
					            ControlPanel.this.add(archivedCard);
					            
					            ControlPanel.this.repaint();
				            }
				            else{
				            				            	
				            }
				            
				            File readThis = new File("Magic the Gathering Archived Files\\"+a + ".jpg");
				            
				            BufferedImage image = ImageIO.read(readThis);
				            
				            ImageIcon icon = new ImageIcon(image);
				          
				            
				            sp.remove(1);
				        	JLabel lbl=new JLabel();
					        lbl.setIcon(icon);
					        MagicFrame.this.sp.add(lbl, 1);
					        sp.setTitleAt(0, "Archive");
					        sp.setTitleAt(1, "Search");
				          
					        sp.setSelectedIndex(1);
				          
				          
				          
				          // Delete the file when closing
//				          File file = new File(imageDest);
//				          file.deleteOnExit();
//							
							
							MagicFrame.this.sp.repaint();
							
							
							MagicFrame.this.cs
							.setText("Card Name:" 
									+ "\nCard Type:"
									+ "\nMana Cost:"
									+ "\nCard Text:");
							
							// search through gatherer
							URL oracle = new URL("http://gatherer.wizards.com/Pages/Search/Default.aspx?name=+["+a+"]");
					        URLConnection yc = oracle.openConnection();
					        BufferedReader in = new BufferedReader(new InputStreamReader(
					                                yc.getInputStream()));
					        String inputLine;
					        String cost = "";
					        String cardName = "";
					        String cardType = "";
					        String cardText = "";
					        while ((inputLine = in.readLine()) != null){
					        	
					        	if(inputLine.contains("Card Name")){
					        		inputLine = in.readLine();
					        		inputLine = in.readLine();
					        		inputLine = inputLine.replaceAll(" ", "");
					        		inputLine = inputLine.replaceAll("</div>", "");
					        		cardName = inputLine;
					        		
					        		
					        	}
					        	
					        	if(inputLine.contains("Types:")){
					        		inputLine = in.readLine();
					        		inputLine = in.readLine();
					        		inputLine = inputLine.replaceAll(" ", "");
					        		inputLine = inputLine.replaceAll("</div>", "");
					        		cardType = inputLine;
					        	}
					        	
					        	if(inputLine.contains("<div class=\"label\" style=\"font-size: .7em;\">")){
					        		inputLine = in.readLine();
					        		inputLine = in.readLine();
					        		inputLine = in.readLine();
					        		inputLine = inputLine.replaceAll(" ", "");
					        		inputLine = inputLine.replaceAll("</div>", "");
					        		cost = inputLine;
					        		
					        	}
					        	
					        	if(inputLine.contains("cardtextbox")){
					        		inputLine = inputLine.replaceAll("</div>", "");
					        		inputLine = inputLine.replaceAll("  ", "");
					        		inputLine = inputLine.replaceAll("<div class=\"cardtextbox\" style=\"padding-left:10px;\">", "");
					        			cardText = cardText.concat(inputLine);
					        		
					        	}
					        }
					        
							
					            
					        in.close();
					        
					        
					        MagicFrame.this.cs
							.setText("Card Name:   "+cardName
									+ "\nCard Type:   "+cardType
									+ "\nMana Cost:   "+cost
									+ "\nCard Text:   "+cardText
									);
							
						} catch (Exception nFE) {
							MagicFrame.this.cs
									.setText("Error:\n Please Put a valid card name into the search.  Spelling matters!"
											+ "\n\n\n"
											+ nFE.getMessage());
							
						}
						
					}

					public void saveImage(String imageUrl, String destinationFile) throws IOException {
						
						
						System.out.println(imageUrl);
						System.out.println(destinationFile);
					    URL url = new URL(imageUrl);
					    // open the IO stream for the image
					    InputStream is = url.openStream();
					    //  Go to the destination file
					    OutputStream os = new FileOutputStream(destinationFile);
					    System.out.println("success3.1");

					    byte[] b = new byte[2048];
					    int length;
					    
					    //  Write to the file with the number of bytes
					    while ((length = is.read(b)) != -1) {
					        os.write(b, 0, length);
					    }
					    System.out.println("success3.2");

					    is.close();
					    System.out.println("success3.3");
					    os.close();
					    System.out.println("success3.4");
					}

					
				});
			}
			
			
		}

		
		// the jinputfield.  Will work for button or if enter is pressed
		class InputField extends JTextField {
			public InputField() {
				this.setColumns(10);
				this.selectAll();
				
				
				
				this.addActionListener(new ActionListener() {

				    @Override
				    public void actionPerformed(ActionEvent e) {
				       // do everything that the add button does
				    //  Search the library of cards
						try {
							
							// get the string and handle spaces for the google image search
							String a = ControlPanel.this.In
									.getText();
							
							a = a.replaceAll(" ", "+");
							if(!(new File("Magic the Gathering Archived Files\\"+a + ".jpg").exists())){
							
								URL url = new URL("https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q="+a+"+magiccards.info");
					            URLConnection connection = url.openConnection();
					            System.out.println("Success 1");
					            
	
					            String line;
					            StringBuilder builder = new StringBuilder();
					            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
					            while((line = reader.readLine()) != null) {
					                builder.append(line);
					            }
					            System.out.println("Success 2");
	
					            JSONObject json = new JSONObject(builder.toString());
					            String imageUrl = json.getJSONObject("responseData").getJSONArray("results").getJSONObject(0).getString("url");
					            String imageDest = "Magic the Gathering Archived Files\\"+a + ".jpg";
					            System.out.println("Success 3");
					            
					            saveImage(imageUrl, imageDest);
					            System.out.println("Success 4");
					            
					            // add the new cards to the archive
					            JButton archivedCard = new JButton(a);
					            archivedCard.addMouseListener(new MouseAdapter() {
									
									@Override
									public void mousePressed(MouseEvent e) {
										File readThis = new File("Magic the Gathering Archived Files\\" + archivedCard.getText() + ".jpg");
							            BufferedImage image;
										try {
											image = ImageIO.read(readThis);
											ImageIcon icon = new ImageIcon(image);
											sp.remove(0);
								        	JLabel lbl=new JLabel();
									        lbl.setIcon(icon);
									        MagicFrame.this.sp.add(lbl, 0);
									        sp.setTitleAt(0, "Archive");
									        sp.setTitleAt(1, "Search");
								          
									        sp.setSelectedIndex(0);
										} catch (IOException e1) {
											// TODO Auto-generated catch block
											e1.printStackTrace();
										}
							            
							            
							            
							            
							            
									}
								});
					            
					            ControlPanel.this.add(archivedCard);
					            
					            ControlPanel.this.repaint();
					            
							}
							else{
								
							}
							File readThis = new File("Magic the Gathering Archived Files\\"+a + ".jpg");
				            BufferedImage image = ImageIO.read(readThis);
				            ImageIcon icon = new ImageIcon(image);
				            
				            System.out.println(sp.getTabCount());
				            
				            sp.remove(1);
				        	JLabel lbl=new JLabel();
					        lbl.setIcon(icon);
					        MagicFrame.this.sp.add(lbl, 1);
					        sp.setTitleAt(0, "Archive");
					        sp.setTitleAt(1, "Search");
				          
					        sp.setSelectedIndex(1);

				          
				          
				          // Delete the file when closing
//				          File file = new File(imageDest);
//				          file.deleteOnExit();
							
							MagicFrame.this.sp.repaint();
							
							MagicFrame.this.cs
							.setText("Card Name:"
									+ "\nCard Type:"
									+ "\nMana Cost:"
									+ "\nCard Text:");
							
							// search through gatherer
							URL oracle = new URL("http://gatherer.wizards.com/Pages/Search/Default.aspx?name=+["+a+"]");
					        URLConnection yc = oracle.openConnection();
					        BufferedReader in = new BufferedReader(new InputStreamReader(
					                                yc.getInputStream()));
					        String inputLine;
					        String cost = "";
					        String cardName = "";
					        String cardType = "";
					        String cardText = "";
					        while ((inputLine = in.readLine()) != null){
					        	
					        	if(inputLine.contains("Card Name")){
					        		inputLine = in.readLine();
					        		inputLine = in.readLine();
					        		inputLine = inputLine.replaceAll(" ", "");
					        		inputLine = inputLine.replaceAll("</div>", "");
					        		cardName = inputLine;
					        	}
					        	
					        	if(inputLine.contains("Types:")){
					        		inputLine = in.readLine();
					        		inputLine = in.readLine();
					        		inputLine = inputLine.replaceAll(" ", "");
					        		inputLine = inputLine.replaceAll("</div>", "");
					        		cardType = inputLine;
					        	}
					        	
					        	if(inputLine.contains("<div class=\"label\" style=\"font-size: .7em;\">")){
					        		inputLine = in.readLine();
					        		inputLine = in.readLine();
					        		inputLine = in.readLine();
					        		inputLine = inputLine.replaceAll("  ", "");
					        		inputLine = inputLine.replaceAll("</div>", "");
					        		cost = inputLine;
					        		
					        	}
					        	
					        	if(inputLine.contains("<div class=\"cardtextbox\" style=\"padding-left:10px;\">")){
					        		inputLine = inputLine.replaceAll("</div>", "");
					        		inputLine = inputLine.replaceAll("  ", "");
					        		inputLine = inputLine.replaceAll("<div class=\"cardtextbox\" style=\"padding-left:10px;\">", "");
					        			cardText = cardText.concat(inputLine);
					        	}
					        }
					            
					        in.close();
					        
					        MagicFrame.this.cs
							.setText("Card Name:   "+cardName
									+ "\nCard Type:   "+cardType
									+ "\nMana Cost:   "+cost
									+ "\nCard Text:   "+cardText
									);
							
						}catch (Exception nFE) {
							MagicFrame.this.cs
							.setText("Error:\n Please Put a valid card name into the search.  Spelling matters!"
									+ "\n\n\n"
									+ nFE.getMessage());
					
				}
				    }
				});
			}
		}
	}

	// Panel where the card picture is going to be displayed
	@SuppressWarnings("rawtypes")
	class Panel extends JTabbedPane {
		
		
		
		public Panel(){
//			this.setTitleAt(0, "test");
			JTabbedPane tabs = new JTabbedPane();
			JComponent panel1 = makeTextPanel("Panel #1");
	        tabs.addTab("Tab 1", null,panel1,
	                "Does nothing");
			tabs.setMnemonicAt(0, KeyEvent.VK_1);
			
			
			
//			add(tabs);
			
		}

		@Override
		public void paintComponent(Graphics comp) {
			super.paintComponent(comp);
		}
		
		protected JComponent makeTextPanel(String text) {
	        JPanel panel = new JPanel(false);
	        JLabel filler = new JLabel(text);
	        filler.setHorizontalAlignment(JLabel.CENTER);
	        panel.setLayout(new GridLayout(1, 1));
	        panel.add(filler);
	        return panel;
	    }
		
		

		
	}

	// creates the console at the bottom of the frame
	class Console extends JTextArea {
		public Console() {
			this.setEditable(false);
			TitledBorder border = BorderFactory.createTitledBorder(
					BorderFactory.createLoweredBevelBorder(), "Card Text");
			border.setTitleJustification(TitledBorder.LEFT);
			this.setBorder(border);
			this.setPreferredSize(new Dimension(145, 100));
			this.append("Magic the Gathering Deck Constructor:"
					+ "\nPlease input a card then push the 'add' button"
					+ "\n                  @author Max Kelly");

		}
	}

}
