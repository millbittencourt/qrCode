package qrCoe;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

import javax.imageio.ImageIO;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

@RestController
public class QrCodeController {

	//private static final String QR_CODE_IMAGE_PATH = "./MyQRCode.png";
	private static final int QC_IMAGE_WIDTH = 350;
	private static final int QC_IMAGE_HEIGHT = 350;
	
    @GetMapping("/geraQrCode/") 
    private static String generateQRCodeImage(@RequestParam(value="link") String link)
			throws WriterException, IOException {
    	//aqui gera o QrCode
    	QRCodeWriter qrCodeWriter = new QRCodeWriter();
		BitMatrix bitMatrix = qrCodeWriter.encode(link, BarcodeFormat.QR_CODE, QC_IMAGE_WIDTH, QC_IMAGE_HEIGHT);
		//Aqui salva ele no path 
	//	Path path = FileSystems.getDefault().getPath(filePath);
	//	MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
		//aqui transforma em imagem pra guardar no banco
		BufferedImage imagem = MatrixToImageWriter.toBufferedImage(bitMatrix);
		//aqui transforma em byteArray pra virar b64 pra virar json
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(imagem, "jpg", baos);

		String b64QrCode = Base64.getEncoder().encodeToString(baos.toByteArray());
		
		return b64QrCode;		
	}
/*
	public static void main(String[] args) {
		try {
			System.out.println(generateQRCodeImage("https://endrylmarques.github.io/herptosite"));
			System.out.println("\nQR Code gerado =)");
		} catch (WriterException e) {
			System.out.println("Could not generate QR Code, WriterException :: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("Could not generate QR Code, IOException :: " + e.getMessage());
		}
	}
	*/
}
