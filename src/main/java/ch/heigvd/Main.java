package ch.heigvd;

import com.luciad.imageio.webp.WebPReadParam;
import com.luciad.imageio.webp.WebPWriteParam;
import javax.imageio.*;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.FileImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {
    private static void decode() throws IOException {
        // Obtain a WebP ImageReader instance
        ImageReader reader = ImageIO.getImageReadersByMIMEType("image/webp").next();
        // Configure decoding parameters
        WebPReadParam readParam = new WebPReadParam();
        readParam.setBypassFiltering(true);
        // Configure the input on the ImageReader
        reader.setInput(new FileImageInputStream(new File("input.webp")));
        // Decode the image
        BufferedImage image = reader.read(0, readParam);
    }

    private static void encode() throws IOException {
        // Obtain an image to encode from somewhere
        BufferedImage image = ImageIO.read(new File("input.png"));
        // Obtain a WebP ImageWriter instance
        ImageWriter writer = ImageIO.getImageWritersByMIMEType("image/webp").next();
        // Configure encoding parameters
        WebPWriteParam writeParam = new WebPWriteParam(writer.getLocale());
        writeParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        writeParam.setCompressionType(writeParam.getCompressionTypes()[WebPWriteParam.LOSSLESS_COMPRESSION]);        // Configure the output on the ImageWriter
        writer.setOutput(new FileImageOutputStream(new File("output.webp")));
        // Encode
        writer.write(null, new IIOImage(image, null, null), writeParam);
    }

    public static void main(String[] args) {
        System.out.println("Hello world!");
    }
}