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
    static String WEBP_FORMAT = ".webp";
    static String PNG_FORMAT = ".png";
    static String JPEG_FORMAT = ".jpeg";
    static String JPG_FORMAT = ".jpg";

    private static boolean isWebp(String file) {
        return file.toLowerCase().endsWith(WEBP_FORMAT);
    }

    private static boolean isPng(String file) {
        return file.toLowerCase().endsWith(PNG_FORMAT);
    }

    private static boolean isJpg(String file) {
        return file.toLowerCase().endsWith(JPEG_FORMAT) || file.toLowerCase().endsWith(JPG_FORMAT);
    }

    private static void convert(String input, String output) throws IOException {
        // Read
        BufferedImage image;
        if (isWebp(input)) {
            ImageReader reader = ImageIO.getImageReadersByMIMEType("image/webp").next();
            WebPReadParam readParam = new WebPReadParam();
            readParam.setBypassFiltering(true);
            reader.setInput(new FileImageInputStream(new File(input)));
            image = reader.read(0, readParam);
        } else if (isPng(input) || isJpg(input)) {
            image = ImageIO.read(new File(input));
        } else {
            // Input file type not supported
            return;
        }

        // Write
        File file = new File(output);
        if (isWebp(output)) {
            ImageWriter writer = ImageIO.getImageWritersByMIMEType("image/webp").next();
            WebPWriteParam writeParam = new WebPWriteParam(writer.getLocale());
            writeParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            writeParam.setCompressionType(writeParam.getCompressionTypes()[WebPWriteParam.LOSSLESS_COMPRESSION]);
            writer.setOutput(new FileImageOutputStream(file));
            writer.write(null, new IIOImage(image, null, null), writeParam);
        } else if (isPng(output)) {
            ImageIO.write(image, "png", file);
        }
        else if (isJpg(output)) {
            ImageIO.write(image, "jpg", file);
        }
        else {
            // Output file type not supported
            return;
        }
    }

    public static void main(String[] args) {
        System.out.println("Hello world!");
        try {
            // Note: if the output path includes a nonexistent directory, this will not work
            convert("image/webp.webp", "image/output.png");
            convert("image/png.png", "image/output.webp");
        } catch (IOException e) {
            System.out.println("Exception IO : " + e);
        }
    }
}