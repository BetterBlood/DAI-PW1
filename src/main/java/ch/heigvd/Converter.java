package ch.heigvd;

import com.luciad.imageio.webp.WebPReadParam;
import com.luciad.imageio.webp.WebPWriteParam;

import javax.imageio.*;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.FileImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Converter {
    private enum Format {
        WEBP,
        PNG,
        JPEG,
        JPG
    }

    static final String WEBP_FORMAT = ".webp";
    static final String PNG_FORMAT = ".png";
    static final String JPEG_FORMAT = ".jpeg";
    static final String JPG_FORMAT = ".jpg";
    private static final Map<Format, String> formatStrings = new HashMap<Format, String>();

    static {
        formatStrings.put(Format.WEBP, WEBP_FORMAT);
        formatStrings.put(Format.PNG, PNG_FORMAT);
        formatStrings.put(Format.JPEG, JPEG_FORMAT);
        formatStrings.put(Format.JPG, JPG_FORMAT);
    }

    private static Format stringToFormat(String s) throws IllegalArgumentException {
        return Format.valueOf(s.toUpperCase());
    }

    private static boolean isWebp(String file) {
        return file.toLowerCase().endsWith(WEBP_FORMAT);
    }

    private static boolean isPng(String file) {
        return file.toLowerCase().endsWith(PNG_FORMAT);
    }

    private static boolean isJpg(String file) {
        return file.toLowerCase().endsWith(JPEG_FORMAT) || file.toLowerCase().endsWith(JPG_FORMAT);
    }

    public static void convert(String input, String output) {
        convert(input, output, true);
    }

    public static void convert(String input, String output, boolean lossless) {
        // Read
        BufferedImage image;
        try {
            if (isWebp(input)) {
                ImageReader reader = ImageIO.getImageReadersByMIMEType("image/webp").next();
                WebPReadParam readParam = new WebPReadParam();
                readParam.setBypassFiltering(true);
                reader.setInput(new FileImageInputStream(new File(input)));
                image = reader.read(0, readParam);
            } else if (isPng(input) || isJpg(input)) {
                image = ImageIO.read(new File(input));
            } else {
                System.out.println("Input file type not supported");
                return;
            }
        } catch (IOException e) {
            System.err.println("Error while reading the file : " + e);
            return;
        }

        // Write
        File file = new File(output);
        try {
            createFolderRecursively(file);
        }
        catch (RuntimeException e) {
            System.out.println("Error while creating folder structure : " + e);
            return;
        }
        try {
            if (isWebp(output)) {
                ImageWriter writer = ImageIO.getImageWritersByMIMEType("image/webp").next();
                WebPWriteParam writeParam = new WebPWriteParam(writer.getLocale());
                writeParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
                writeParam.setCompressionType(writeParam.getCompressionTypes()[lossless ? WebPWriteParam.LOSSLESS_COMPRESSION : WebPWriteParam.LOSSY_COMPRESSION]);
                writer.setOutput(new FileImageOutputStream(file));
                writer.write(null, new IIOImage(image, null, null), writeParam);
            } else if (isPng(output)) {
                ImageIO.write(image, "png", file);
            } else if (isJpg(output)) {
                ImageIO.write(image, "jpg", file);
            } else {
                System.out.println("Output file type not supported");
                return;
            }
        } catch (IOException e) {
            System.err.println("Error while writing a file : " + e);
        }

        System.out.println("Converted : " + input + " to : " + output);
    }

    public static void convertRecursively(String path, String input, String output) {
        File folder = new File(path);
        if (folder.exists() && folder.isDirectory()) {
            try {
                Converter.traverseFolder(folder, stringToFormat(input), stringToFormat(output));
            } catch (IllegalArgumentException e) {
                System.err.println("A specified format is not a valid format : " + e);
            }
        } else {
            System.out.println("The specified path is not a valid directory.");
        }
    }

    private static void createFolderRecursively(File folder) throws RuntimeException {
        File parent = folder.getParentFile();
        if (!parent.exists()) {
            createFolderRecursively(parent);
            if (!parent.mkdir()) {
                throw new RuntimeException("Error while creating a folder");
            }
        }
    }

    private static void traverseFolder(File folder, Format inputFormat, Format outputFormat) {
        File[] files = folder.listFiles();
        if (files == null) {
            return;
        }

        for (File file : files) {
            if (file.isDirectory()) {
                traverseFolder(file, inputFormat, outputFormat);
            } else if (file.getName().endsWith(formatStrings.get(inputFormat))) {
                convert(file.getPath(), file.getPath() + formatStrings.get(outputFormat));
            }
        }
    }
}
