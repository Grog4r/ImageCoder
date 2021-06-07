package ImageCoder;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class Decoder {
	
	static File inFile;
	static int width;
	static int height;
	
	public Decoder(File inputFile) {
		inFile = inputFile;
	}
	
	public String decode() throws IOException {
		BufferedImage image = ImageIO.read(inFile);
		width = image.getWidth();
		height = image.getHeight();
		int len = 0;
		int cP = image.getRGB(0, 0);

		int red = (cP & 0x00ff0000) >> 16;
		int green = (cP & 0x0000ff00) >> 8;
		int blue = (cP & 0x000000ff);

		int redPrt = red & 0b00000111;
		int greenPrt = green & 0b00000111;
		int bluePrt = blue & 0b00000111;

		int lenPrt = 0;
		lenPrt ^= redPrt << 6;
		lenPrt ^= greenPrt << 3;
		lenPrt ^= bluePrt;

		len = lenPrt << 8;

		cP = image.getRGB(1, 0);

		red = (cP & 0x00ff0000) >> 16;
		green = (cP & 0x0000ff00) >> 8;
		blue = (cP & 0x000000ff);

		redPrt = red & 0b00000111;
		greenPrt = green & 0b00000111;
		bluePrt = blue & 0b00000111;

		lenPrt = 0;
		lenPrt ^= redPrt << 6;
		lenPrt ^= greenPrt << 3;
		lenPrt ^= bluePrt;

		len ^= lenPrt;

		// System.out.println(len);

		String outMsg = "";

		int pixelCtr = 2;
		for (int i = 0; i < len; i++) {
			int x = pixelCtr - (pixelCtr / width) * width;
			int y = pixelCtr / width;

			int cColor = image.getRGB(x, y);
			int r = (cColor & 0x00ff0000) >> 16;
			int g = (cColor & 0x0000ff00) >> 8;
			int b = (cColor & 0x000000ff);

			int rPrt = r & 0b00000111;
			int gPrt = g & 0b00000111;
			int bPrt = b & 0b00000111;

			int c = 0;
			c ^= rPrt << 6;
			c ^= gPrt << 3;
			c ^= bPrt;

			char msgChar = 0;
			msgChar = (char) c;
			// System.out.println(msgChar);
			// System.out.println(i);
			pixelCtr++;
			outMsg += msgChar;
		}
		Frame.prog.setText("Erfolgreich Decodiert...");
		return outMsg;
	}
	
	

}
