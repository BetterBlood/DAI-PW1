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

    private static final String WEBP_FORMAT = ".webp";
    private static final String PNG_FORMAT = ".png";
    private static final String JPEG_FORMAT = ".jpeg";
    private static final String JPG_FORMAT = ".jpg";
    private static final Map<Format, String> formatStrings = new HashMap<Format, String>();

    private static boolean isLossless;
    private static boolean isRecursive;

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

    public static int convert(String input, String output) {
        // Read
        BufferedImage image;
        File inputFile = new File(input);
        if (!inputFile.exists()) {
            System.out.println("Error, the input file does not exist");
            return 1;
        }

        try {
            if (isWebp(input)) {
                ImageReader reader = ImageIO.getImageReadersByMIMEType("image/webp").next();
                WebPReadParam readParam = new WebPReadParam();
                readParam.setBypassFiltering(true);
                reader.setInput(new FileImageInputStream(inputFile));
                image = reader.read(0, readParam);
            } else if (isPng(input) || isJpg(input)) {
                image = ImageIO.read(new File(input));
            } else {
                System.out.println("Input file type not supported");
                return 1;
            }
        } catch (IOException e) {
            System.err.println("Error while reading the file : " + e);
            return 1;
        }

        // Write
        File outputFile = new File(output);
        try {
            createFolderRecursively(outputFile);
        }
        catch (RuntimeException e) {
            System.out.println("Error while creating folder structure : " + e);
            return 1;
        }
        try {
            if (isWebp(output)) {
                ImageWriter writer = ImageIO.getImageWritersByMIMEType("image/webp").next();
                WebPWriteParam writeParam = new WebPWriteParam(writer.getLocale());
                writeParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
                writeParam.setCompressionType(writeParam.getCompressionTypes()[isLossless ? WebPWriteParam.LOSSLESS_COMPRESSION : WebPWriteParam.LOSSY_COMPRESSION]);
                writer.setOutput(new FileImageOutputStream(outputFile));
                writer.write(null, new IIOImage(image, null, null), writeParam);
            } else if (isPng(output)) {
                ImageIO.write(image, "png", outputFile);
            } else if (isJpg(output)) {
                ImageIO.write(image, "jpg", outputFile);
            } else {
                System.out.println("Output file type not supported");
                return 1;
            }
        } catch (IOException e) {
            System.err.println("Error while writing a file : " + e);
        }

        System.out.println("Converted : " + input + " to : " + output);
        return 0;
    }

    public static int convert(String path, String input, String output) {
        File folder = new File(path);
        if (folder.exists() && folder.isDirectory()) {
            try {
                Converter.convertFolder(folder, stringToFormat(input), stringToFormat(output), isRecursive);
            } catch (IllegalArgumentException e) {
                System.err.println("A specified format is not a valid format : " + e);
                return 1;
            }
        } else {
            System.out.println("The specified path is not a valid directory.");
            return 1;
        }
        return 0;
    }

    public static void changeParameters(boolean isLossless, boolean isRecursive) {
        Converter.isLossless = isLossless;
        Converter.isRecursive = isRecursive;
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

    private static void convertFolder(File folder, Format inputFormat, Format outputFormat, boolean isRecursive) {
        File[] files = folder.listFiles();
        if (files == null) {
            return;
        }

        for (File file : files) {
            if (isRecursive && file.isDirectory()) {
                convertFolder(file, inputFormat, outputFormat, true);
            } else if (file.getName().endsWith(formatStrings.get(inputFormat))) {
                convert(file.getPath(), file.getPath() + formatStrings.get(outputFormat));
            }
        }
    }
}
