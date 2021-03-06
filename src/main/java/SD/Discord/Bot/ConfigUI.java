package SD.Discord.Bot;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.util.HashMap;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JViewport;
import javax.swing.Spring;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;

public class ConfigUI {
	
	/*
	 	--------------
	 	FRAME CONTENTS
	 	--------------
	 
		Container content = frame.getContentPane();
		content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
		
		//Header
		JPanel header = addHeader(frame);
		content.add(header, BorderLayout.NORTH);
		
		
		//Token Entry
		JPanel tokenPanel = addTokenField(frame);
		content.add(tokenPanel, BorderLayout.SOUTH);
		
		
		//Prefix Entry
		JPanel prefixPanel = addPrefixField(frame);
		content.add(prefixPanel, BorderLayout.SOUTH);
		
		//Custom Commands
		for (int i = 0; i < 3; i++) {
			content.add(customCommandEntry());
		}
		
		JPanel submitCancel = new JPanel();
		//Add Custom Command Addition Button
		content.add(addAddButton(frame, content, submitCancel));
		
		//Submit Button
		content.add(addSubmitAndCancel(frame, submitCancel), BorderLayout.SOUTH);
		
		//Scrollbar
		JScrollPane scroll = new JScrollPane(content, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		frame.setContentPane(scroll);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	 
	 */
	 
	/**
	 * Creates the frame for the config window
	 * @return
	 * 		The frame
	 */
	public static JFrame openFirstWindow() {
		
		//Outline of the frame
		int width = 550;
		int height = 550;
		JFrame frame = new JFrame();
		frame.setName("ConfigWindow");
		frame.setTitle("SwagDragon Discord Bot");
		frame.setPreferredSize(new Dimension(width, height));
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation((int)dim.getWidth() / 2 - (width/2), (int)dim.getHeight() / 2 - (height/2));
		frame.pack();
		frame.setVisible(true);

		return frame;
	}
	
	/**
	 * Creates a command config entry
	 * @return
	 * 		Returns a panel that represents an entry field
	 */
	public static JPanel customCommandEntry(String command, String contents) {
		JPanel commandPanel = new JPanel();
		SpringLayout grid = new SpringLayout();
		commandPanel.setLayout(grid);
		JLabel commandLabel = new JLabel("Command: ");
		commandLabel.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		commandLabel.setAlignmentY(JLabel.CENTER_ALIGNMENT);
		commandLabel.setFont(new Font("Sans-Serif", Font.PLAIN, 14));
		commandLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		JTextField commandKey = new JTextField(command, 10);
		commandKey.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
		commandKey.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		JTextArea commandDesc = null;
		if (contents == "" || contents == null) {
			commandDesc = new JTextArea("What you want the bot to say back goes here", 5, 30);
			commandDesc.addFocusListener(new FocusAdapter() {
		    public void focusGained(FocusEvent e) {
		        JTextArea source = (JTextArea)e.getComponent();
		        source.setText("");
		        source.removeFocusListener(this);
		    	}
			});
		}
		else commandDesc = new JTextArea(contents, 5, 30);
		commandDesc.setLineWrap(true);
		commandDesc.setMinimumSize(new Dimension(commandDesc.getWidth(), 50));
		commandDesc.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
		JScrollPane descScroll = new JScrollPane(commandDesc);
		commandPanel.add(commandLabel);
		commandPanel.add(commandKey);
		commandPanel.add(descScroll);
		grid.putConstraint(SpringLayout.WEST, descScroll, 5, SpringLayout.EAST, commandKey);
		grid.putConstraint(SpringLayout.WEST, commandKey, 5, SpringLayout.EAST, commandLabel);
		grid.getConstraints(commandPanel).setHeight(Spring.constant(50, 60, 70));
		return commandPanel;
	}
	
	/**
	 * Returns the button of the config field addition
	 * @param frame
	 * 		The frame to be added to
	 * @param content
	 * 		The contents of that frame
	 * @param bottom
	 * 		The panel for the bottom panel, so it stays static
	 * @return
	 * 		The button
	 */
	public static JButton addAddButton(JFrame frame, Container content, JPanel bottom, List<JPanel> entryList) {
		JButton addCustom = new JButton("+");
		addCustom.setAlignmentY(SwingConstants.BOTTOM);
		addCustom.setFont(new Font("Serif", Font.BOLD, 16));
		addCustom.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JButton component = (JButton) e.getSource();
				content.remove(component);
				content.remove(bottom);
				JPanel newEntry = customCommandEntry(null, null);
				entryList.add(newEntry);
				content.add(newEntry);
				content.add(addAddButton(frame, content, bottom, entryList));
				content.add(bottom, BorderLayout.SOUTH);
				JScrollPane scroll = new JScrollPane(content, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
				frame.setContentPane(scroll);
				frame.revalidate();
				frame.pack();
			}
			
		});
		return addCustom;
	}
	
	/**
	 * Returns a new panel of the submit and cancel button
	 * @param frame
	 * 		The frame to be added to
	 * @param submitCancelPanel
	 * 		The panel that is premade
	 * @return
	 * 		The new panel
	 */
	public static JPanel addSubmitAndCancel(JFrame frame, JPanel submitCancelPanel, List<JPanel> entries, HashMap<String, Object> commandList, MouseAdapter clickEvent) {
		JButton submit = new JButton("Start");
		submitCancelPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		submit.setAlignmentX(SwingConstants.RIGHT);
		submit.setFont(new Font("SansSerif", Font.BOLD, 16));
		submit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//JButton component = (JButton) e.getSource();
				for (JPanel panel : entries) {
					String key = "";
					String entry = "";
					Component[] components = panel.getComponents();
					for (Component c : components) {
						if (c instanceof JTextField) {
							JTextField field = (JTextField) c;
							key = field.getText();
						}
						if (c instanceof JScrollPane) {
							JScrollPane pane = (JScrollPane) c;
							Component[] paneComps = pane.getComponents();
							for (Component c2 : paneComps) {
								if (c2 instanceof JViewport) {
									JViewport view = (JViewport) c2;
									if (view.getView() instanceof JTextArea) {
										JTextArea field = (JTextArea) view.getView();
										entry = field.getText();
									}
								}
							}
						}
					}
					commandList.put(key, entry);
				}
				
			}
			
		});
		submit.addMouseListener(clickEvent);
		JButton cancel = new JButton("Cancel");
		cancel.setAlignmentX(SwingConstants.RIGHT);
		cancel.setFont(new Font("SansSerif", Font.PLAIN, 16));
		cancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
			
		});
		submitCancelPanel.add(submit);
		submitCancelPanel.add(cancel);
		return submitCancelPanel;
	}
	
	/**
	 * Returns a panel for the bot token field
	 * @param frame
	 * 		The frame to add it to
	 * @return
	 * 		The panel
	 */
	public static JPanel addTokenField(JFrame frame, String token) {
		JPanel tokenPanel = new JPanel();
		JLabel tokenLabel = new JLabel("Bot Account Token:");
		tokenLabel.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		tokenLabel.setAlignmentY(JLabel.CENTER_ALIGNMENT);
		tokenLabel.setFont(new Font("Sans-Serif", Font.PLAIN, 14));
		JTextField tokenEntry = new JTextField("", 30);
		if (token == "") {
		tokenEntry.setText("Token goes here");
		tokenEntry.addFocusListener(new FocusAdapter() {
		    public void focusGained(FocusEvent e) {
		        JTextField source = (JTextField)e.getComponent();
		        source.setText("");
		        source.removeFocusListener(this);
		    }
		});
		}
		else tokenEntry.setText(token);
		tokenPanel.add(tokenLabel);
		tokenPanel.add(tokenEntry);
		tokenPanel.setBounds(10, 10, frame.getPreferredSize().width, 20);
		tokenPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, tokenPanel.getMinimumSize().height));
		return tokenPanel;
	}
	
	/**
	 * Returns a panel for the music bot channel
	 * @param frame
	 * 		The frame to add it to
	 * @return
	 * 		The panel
	 */
	public static JPanel addMusicField(JFrame frame, String field) {
		JPanel tokenPanel = new JPanel();
		JLabel tokenLabel = new JLabel("Music Bot Channel:");
		tokenLabel.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		tokenLabel.setAlignmentY(JLabel.CENTER_ALIGNMENT);
		tokenLabel.setFont(new Font("Sans-Serif", Font.PLAIN, 14));
		JTextField tokenEntry = new JTextField("", 20);
		tokenEntry.setText(field);
		tokenPanel.add(tokenLabel);
		tokenPanel.add(tokenEntry);
		tokenPanel.setBounds(10, 10, frame.getPreferredSize().width, 20);
		tokenPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, tokenPanel.getMinimumSize().height));
		return tokenPanel;
	}
	
	/**
	 * Returns a panel for the TOS channel
	 * @param frame
	 * 		The frame to add it to
	 * @return
	 * 		The panel
	 */
	public static JPanel addTOSField(JFrame frame, String field) {
		JPanel tokenPanel = new JPanel();
		JLabel tokenLabel = new JLabel("Minigames Channel:");
		tokenLabel.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		tokenLabel.setAlignmentY(JLabel.CENTER_ALIGNMENT);
		tokenLabel.setFont(new Font("Sans-Serif", Font.PLAIN, 14));
		JTextField tokenEntry = new JTextField("", 20);
		tokenEntry.setText(field);
		tokenPanel.add(tokenLabel);
		tokenPanel.add(tokenEntry);
		tokenPanel.setBounds(10, 10, frame.getPreferredSize().width, 20);
		tokenPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, tokenPanel.getMinimumSize().height));
		return tokenPanel;
	}
	
	/**
	 * Returns the panel of the header text
	 * @param frame
	 * 		The frame to add it to
	 * @return
	 * 		The panel with the text
	 */
	public static JPanel addHeader(JFrame frame) {
		JPanel header = new JPanel();
		header.setBackground(new Color(0, 0, 255, 40));
		JLabel label = new JLabel("<html><div style='text-align: center;'>Welcome to the SwagDragon Discord Bot!"
				+ "<br />Please make changes to your configuration down below!</div></html>");
		label.setAlignmentX(JLabel.CENTER);
		label.setAlignmentY(JLabel.TOP);
		label.setSize(frame.getPreferredSize().width, 100);
		label.setFont(new Font("Sans-Serif", Font.BOLD, 18));
		label.setBounds(50, 10, frame.getPreferredSize().width, 20);
		label.setBackground(Color.BLACK);
		header.add(label);
		header.setBounds(50, 10, frame.getPreferredSize().width, 20);
		header.setMaximumSize(new Dimension(Integer.MAX_VALUE, header.getMinimumSize().height));
		return header;
	}
	
	/**
	 * Returns the panel for the prefix entry field
	 * @param frame
	 * 		The frame to add it to
	 * @return
	 * 		The panel
	 */
	public static JPanel addPrefixField(JFrame frame, String prefix) {
		JPanel prefixPanel = new JPanel();
		JLabel prefixLabel = new JLabel("Command Prefix: ");
		prefixLabel.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		prefixLabel.setAlignmentY(JLabel.CENTER_ALIGNMENT);
		prefixLabel.setFont(new Font("Sans-Serif", Font.PLAIN, 14));
		JTextField prefixEntry = new JTextField(prefix, 7);
		prefixPanel.add(prefixLabel);
		prefixPanel.add(prefixEntry);
		prefixPanel.setBounds(10, 10, frame.getPreferredSize().width, 20);
		prefixPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, prefixPanel.getMinimumSize().height));
		return prefixPanel;
	}
	
	public static String getTextFromField(JPanel panel) {
		Component[] comps = panel.getComponents();
		for (Component c : comps) {
			if (c instanceof JTextField) {
				JTextField field = (JTextField) c;
				return field.getText();
			}
		}
		return null;
	}
	
	public static String getTextFromArea(JScrollPane scrollPanel) {
		Component[] components = scrollPanel.getComponents();
		for (Component c : components) {
			if (c instanceof JViewport) {
				JViewport view = (JViewport) c;
				if (view.getView() instanceof JTextArea) {
					JTextArea field = (JTextArea) view.getView();
					return field.getText();
				}
			}
		}
		return null;
	}
	
	public static JFrame openRunningFrame(boolean ableToLogin) {

		//Outline of the frame
		int width = 300;
		int height = 150;
		JFrame frame = new JFrame();
		frame.setName("RunningWindow");
		frame.setTitle("SwagDragon Discord Bot");
		frame.setPreferredSize(new Dimension(width, height));
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation((int)dim.getWidth() / 2 - (width/2), (int)dim.getHeight() / 2 - (height/2));
		
		JPanel header = new JPanel();
		header.setLayout(new GridBagLayout());
		header.setBackground(new Color(0, 255, 0, 40));
		JLabel label = new JLabel("<html><div style='text-align: center;'>The bot is running!</div></html>");
		if (!ableToLogin) {
			header.setBackground(new Color(255, 0, 0, 40));
			label.setText("<html><div style='text-align: center;'>The bot is <u>not</u> running!</div></html>");
		}
		label.setSize(frame.getPreferredSize().width, 100);
		label.setFont(new Font("Sans-Serif", Font.BOLD, 18));
		header.add(label);
		header.setBounds(50, 10, frame.getPreferredSize().width, 20);
		header.setMaximumSize(new Dimension(Integer.MAX_VALUE, header.getMinimumSize().height));
		frame.add(header);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);

		return frame;
	}
	
	public static JFrame openStartingFrame() {
		int width = 400;
		int height = 175;
		JFrame frame = new JFrame();
		frame.setName("StartingWindow");
		frame.setTitle("SwagDragon Discord Bot");
		frame.setPreferredSize(new Dimension(width, height));
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation((int)dim.getWidth() / 2 - (width/2), (int)dim.getHeight() / 2 - (height/2));
		
		JPanel header = new JPanel();
		header.setBackground(Color.LIGHT_GRAY);
		JLabel label = new JLabel("<html><div style='text-align: center;'>Revving up...</div></html>");
		label.setAlignmentX(JLabel.CENTER);
		label.setAlignmentY(JLabel.TOP);
		label.setSize(frame.getPreferredSize().width, 100);
		label.setFont(new Font("Sans-Serif", Font.BOLD, 18));
		label.setBounds(0, 0, frame.getPreferredSize().width, 20);
		label.setBackground(Color.BLACK);
		header.add(label);
		header.setBounds(0, 0, frame.getPreferredSize().width, 30);
		header.setMaximumSize(new Dimension(Integer.MAX_VALUE, header.getMinimumSize().height));
		frame.add(header);
		
		Icon img = new ImageIcon("images/loading.gif");
		JLabel gif = new JLabel(img);
		int gifwidth = 50;
		int x_coord = (int) (frame.getPreferredSize().getWidth() / 2) - (gifwidth / 2);
		int y_coord = (int) (frame.getPreferredSize().getHeight() / 2) - (gifwidth / 2);
		gif.setBounds(x_coord, y_coord, gifwidth, gifwidth);
		gif.setSize(gifwidth, gifwidth);
		gif.setMaximumSize(new Dimension(gifwidth, gifwidth));
		gif.setMinimumSize(new Dimension(gifwidth, gifwidth));
		gif.setPreferredSize(new Dimension(gifwidth, gifwidth));
		frame.add(gif);
		
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
		
		return frame;
	}
	
	public static JPanel addProfanityFilterButton(JFrame frame, String enabled) {
		JPanel filterPanel = new JPanel();
		JRadioButton filterButton = new JRadioButton();
		JLabel filterLabel = new JLabel("Profanity Filter:");
		filterLabel.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		filterLabel.setAlignmentY(JLabel.CENTER_ALIGNMENT);
		filterLabel.setFont(new Font("Sans-Serif", Font.PLAIN, 14));
		if (enabled.equals("true")) {
		filterButton.setSelected(true);
		}
		filterPanel.add(filterLabel);
		filterPanel.add(filterButton);
		filterPanel.setBounds(10, 10, frame.getPreferredSize().width, 20);
		filterPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, filterPanel.getMinimumSize().height));
		return filterPanel;
	}

}
