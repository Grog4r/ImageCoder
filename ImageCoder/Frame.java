package ImageCoder;

import java.awt.*;
import java.awt.event.*;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * Beschreibung
 *
 * @version 1.0 vom 23.07.2019
 * @author kuni1021@h-ka.de
 */

@SuppressWarnings("serial")
public class Frame extends JFrame {
	// Anfang Attribute
	private JLabel labelEncoderDecoder = new JLabel();
	private JTextArea textArea = new JTextArea("");
	private JScrollPane textAreaScrollPane = new JScrollPane(textArea);
	private JLabel outLabel = new JLabel();
	private JTextField outName = new JTextField();
	private JLabel png = new JLabel();
	String userhome = System.getProperty("user.home");
	private JFileChooser fileChooser = new JFileChooser(userhome + System.getProperty("file.separator") + "Desktop");
	private JButton decode = new JButton();
	private JButton encode = new JButton();
	private JButton help = new JButton();
	protected static JTextArea prog = new JTextArea();
	// Ende Attribute
	
	private String text;

	public Frame() {
		// Frame-Initialisierung
		super();
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		int frameWidth = 600;
		int frameHeight = 650;
		setSize(frameWidth, frameHeight);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (d.width - getSize().width) / 2;
		int y = (d.height - getSize().height) / 2;
		setLocation(x, y);
		setTitle("ImageCoder");
		setResizable(false);
		Container cp = getContentPane();
		cp.setLayout(null);
		// Anfang Komponenten

		labelEncoderDecoder.setBounds(200, 15, 200, 30);
		labelEncoderDecoder.setText("Encoder/Decoder");
		labelEncoderDecoder.setOpaque(false);
		labelEncoderDecoder.setHorizontalAlignment(SwingConstants.CENTER);
		labelEncoderDecoder.setFont(new Font("Consolas", Font.BOLD, 22));
		cp.add(labelEncoderDecoder);

		textAreaScrollPane.setBounds(30, 55, 540, 200);
		text = "Zu codierenden Text hier eingeben.\n"
				+ "Dann Bild Auswählen, Name für Ausgabe-Datei wählen und auf Encode drücken.\n"
				+ "Oder zu entschlüsselndes Bild auswählen und auf Decode drücken.";
		textArea.setText(text);
		textArea.setWrapStyleWord(true);
		cp.add(textAreaScrollPane);
		
		outLabel.setBounds(30, 270, 160, 20);
		outLabel.setText("Dateiname Ausgabe-Datei:");
		cp.add(outLabel);
		
		outName.setBounds(200, 270, 200, 20);
		outName.setText("output");
		cp.add(outName);
		
		png.setBounds(405, 270, 100, 20);
		png.setText(".png");
		cp.add(png);

		fileChooser.setBounds(25, 310, 415, 260);
		fileChooser.setDragEnabled(true);
		FileNameExtensionFilter imageFilter = new FileNameExtensionFilter("Image files",
				ImageIO.getReaderFileSuffixes());
		fileChooser.setFileFilter(imageFilter);
		fileChooser.setControlButtonsAreShown(false);
		cp.add(fileChooser);

		encode.setBounds(455, 320, 115, 25);
		encode.setText("Encode");
		encode.setMargin(new Insets(2, 2, 2, 2));
		encode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				encode_ActionPerformed(evt);
			}
		});
		cp.add(encode);

		decode.setBounds(455, 370, 115, 25);
		decode.setText("Decode");
		decode.setMargin(new Insets(2, 2, 2, 2));
		decode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				decode_ActionPerformed(evt);
			}
		});
		cp.add(decode);
		
		help.setBounds(455, 420, 115, 25);
		help.setText("Hilfe");
		help.setMargin(new Insets(2, 2, 2, 2));
		help.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				help_ActionPerformed(evt);
			}
		});
		cp.add(help);
		
		prog.setBounds(30, 570, 540, 60);
		prog.setText("Warte auf Eingabe...");
		prog.setOpaque(false);
		prog.setFont(new Font("Consolas",Font.PLAIN , 12));
		prog.setEditable(false);
		prog.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		prog.setLineWrap(true);
		cp.add(prog);

		// Ende Komponenten

		setVisible(true);
	} // end of public ImageCoder

	// Anfang Methoden

	public File jFileChooser1_openFile() {
		if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			return fileChooser.getSelectedFile();
		} else {
			return null;
		}
	}

	

	public void encode_ActionPerformed(ActionEvent evt) {
		String message = textArea.getText();
		File inputFile = fileChooser.getSelectedFile();
		try {
			String outFile = fileChooser.getSelectedFile().getParent() + System.getProperty("file.separator") + outName.getText() + ".png";
			System.out.println(outFile);
			File outputFile = new File(outFile);
			Encoder e = new Encoder(message, inputFile, outputFile);
			e.encode();
		} catch (Exception e) {
			
		}
	} // end of encode_ActionPerformed

	public void decode_ActionPerformed(ActionEvent evt) {
		String decMsg = "";
		File inputFile = fileChooser.getSelectedFile();
		try {
			Decoder d = new Decoder(inputFile);
			decMsg = d.decode();
			textArea.setText(decMsg);
		} catch(Exception e) {
			
		}
	} // end of bDecode_ActionPerformed
	
	public void help_ActionPerformed(ActionEvent evt) {
		textArea.setText(text);
	}
	
	// Ende Methoden
} // end of class ImageCoder
