package ImageCoder;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Encoder {

	static String msg;
	static File inFile;
	static File outFile;
	static int width;
	static int height;
	static BufferedImage img;

	public Encoder(String message, File inputFile, File outputFile) throws IOException {
		msg = message;
		inFile = inputFile;
		outFile = outputFile;
		img = ImageIO.read(inFile);
		width = img.getWidth();
		height = img.getHeight();
	}

	public void encode() throws IOException {
		if(validMsg(msg)) {
			System.out.println("Nachricht wird verschlüsselt...");
			Frame.prog.setText("Nachricht wird verschlüsselt...");

			Graphics2D g = (Graphics2D) img.getGraphics();

			int len = msg.length();

			int cP = img.getRGB(0, 0);
			int red = (cP & 0x00ff0000) >> 16;
			int green = (cP & 0x0000ff00) >> 8;
			int blue = (cP & 0x000000ff);

			int lenPrt = len & 0xff00;
			lenPrt >>= 8;

			int charModRed = (lenPrt & 0b111000000) >> 6;
			int redNew = mod2Bits(red, charModRed);
			int charModGreen = (lenPrt & 0b000111000) >> 3;
			int greenNew = mod2Bits(green, charModGreen);
			int charModBlue = (lenPrt & 0b000000111);
			int blueNew = mod2Bits(blue, charModBlue);

			Color color = new Color(redNew, greenNew, blueNew);
			g.setColor(color);
			g.drawLine(0, 0, 0, 0);

			cP = img.getRGB(1, 0);
			red = (cP & 0x00ff0000) >> 16;
			green = (cP & 0x0000ff00) >> 8;
			blue = (cP & 0x000000ff);

			lenPrt = len & 0xff;
			colorModification(g, red, green, blue, lenPrt);
			g.drawLine(1, 0, 1, 0);

			int pixelCtr = 2;

			for (int i = 0; i < msg.length(); i++) {
				int c = msg.charAt(i);

				

				int x = pixelCtr - (pixelCtr / width) * width;
				int y = pixelCtr / width;
				int cCurrent = img.getRGB(x, y);
				red = (cCurrent & 0x00ff0000) >> 16;
				green = (cCurrent & 0x0000ff00) >> 8;
				blue = (cCurrent & 0x000000ff);


				colorModification(g, red, green, blue, c);
				g.drawLine(x, y, x, y);
				pixelCtr++;
			}

			// System.out.println(pixelCtr);

			

			ImageIO.write(img, "png", outFile);
			
			String outputMessage = "Erfolgreich verschlüsselt und gespeichert in\n" +  outFile.getAbsolutePath();
			Frame.prog.setText(outputMessage);
		} else {
			System.err.println("Fehler! Nachricht zu lange für Bild. Maximal " + (int)getMaxChar() + " Zeichen.");
		}
	}

	private void colorModification(Graphics2D g, int red, int green, int blue, int lenPrt) {
		int charModRed;
		int redNew;
		int charModGreen;
		int greenNew;
		int charModBlue;
		int blueNew;
		Color color;
		charModRed = (lenPrt & 0b111000000) >> 6;
		redNew = mod2Bits(red, charModRed);
		charModGreen = (lenPrt & 0b000111000) >> 3;
		greenNew = mod2Bits(green, charModGreen);
		charModBlue = (lenPrt & 0b000000111);
		blueNew = mod2Bits(blue, charModBlue);

		color = new Color(redNew, greenNew, blueNew);
		g.setColor(color);
	}

	public double getMaxChar() {
		return width * height - 3;
	}

	public boolean validMsg(String msg) {
		return !(msg.length() > this.getMaxChar());
	}

	public int mod2Bits(int value, int suffix) {
		int retInt = value & 0xfffffff8;
		retInt ^= suffix;
		return retInt;
	}

}
