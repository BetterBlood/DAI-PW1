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

/**
 * Static class used to convert webp-png-jpg files
 */
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

    /**
     * Converts a given string into a Format
     * @param s the string to convert
     * @return the Format type expressed by the string
     * @throws IllegalArgumentException if the string does not match any Format type
     */
    private static Format stringToFormat(String s) throws IllegalArgumentException {
        return Format.valueOf(s.toUpperCase());
    }

    /**
     * Tests wether the path to a file points to a webp file
     * @param file the path to the file
     * @return true if webp
     */
    private static boolean isWebp(String file) {
        return file.toLowerCase().endsWith(WEBP_FORMAT);
    }

    /**
     * Tests wether the path to a file points to a png file
     * @param file the path to the file
     * @return true if png
     */
    private static boolean isPng(String file) {
        return file.toLowerCase().endsWith(PNG_FORMAT);
    }

    /**
     * Tests wether the path to a file points to a jpg, or jpeg, file
     * @param file the path to the file
     * @return true if jpg or jpeg
     */
    private static boolean isJpg(String file) {
        return file.toLowerCase().endsWith(JPEG_FORMAT) || file.toLowerCase().endsWith(JPG_FORMAT);
    }

    /**
     * Converts a file to another file format
     * If the path to the output file does not exist because of missing folders, those are created on the fly.
     * @param input the path to the input file
     * @param output the path to the output file
     */
    public static void convert(String input, String output) {
        // Read
        BufferedImage image;
        File inputFile = new File(input);
        if (!inputFile.exists()) {
            System.out.println("Error, the input file does not exist");
            return;
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
                return;
            }
        } catch (IOException e) {
            System.err.println("Error while reading the file : " + e);
            return;
        }

        // Write
        File outputFile = new File(output);
        try {
            createFolderRecursively(outputFile);
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
                writeParam.setCompressionType(writeParam.getCompressionTypes()[isLossless ? WebPWriteParam.LOSSLESS_COMPRESSION : WebPWriteParam.LOSSY_COMPRESSION]);
                writer.setOutput(new FileImageOutputStream(outputFile));
                writer.write(null, new IIOImage(image, null, null), writeParam);
            } else if (isPng(output)) {
                ImageIO.write(image, "png", outputFile);
            } else if (isJpg(output)) {
                ImageIO.write(image, "jpg", outputFile);
            } else {
                System.out.println("Output file type not supported");
                return;
            }
        } catch (IOException e) {
            System.err.println("Error while writing a file : " + e);
        }

        System.out.println("Converted : " + input + " to : " + output);
    }

    /**
     * Convers the content of a folder from a file format to another
     * Only "webp", "png", "jpeg" or "jpg" are accepted for input and output. not case-sensitive
     * @param path the path to the folder
     * @param input the input format
     * @param output the output format
     */
    public static void convert(String path, String input, String output) {
        File folder = new File(path);
        if (folder.exists() && folder.isDirectory()) {
            try {
                Converter.convertFolder(folder, stringToFormat(input), stringToFormat(output));
            } catch (IllegalArgumentException e) {
                System.err.println("A specified format is not a valid format : " + e);
            }
        } else {
            System.out.println("The specified path is not a valid directory.");
        }
    }

    /**
     * Changes the static parameters of the converter. This is to avoid too many overloaded functions when converting
     * @param isLossless wether the conversion will be lossless, or lossy
     * @param isRecursive wether the conversion, if targeting a folder, will be recursive, or not
     */
    public static void changeParameters(boolean isLossless, boolean isRecursive) {
        Converter.isLossless = isLossless;
        Converter.isRecursive = isRecursive;
    }

    /**
     * Creates missing folders recursively from a given folder path
     * @param folder The folder that is desired to be created
     * @throws RuntimeException if creating a folder fails
     */
    private static void createFolderRecursively(File folder) throws RuntimeException {
        File parent = folder.getParentFile();
        if (!parent.exists()) {
            createFolderRecursively(parent);
            if (!parent.mkdir()) {
                throw new RuntimeException("Error while creating a folder");
            }
        }
    }

    /**
     * Converts the content of a folder from inputFormat to outputFormat
     * @param folder the folder to be converted
     * @param inputFormat the Format type to search for as an input
     * @param outputFormat the Format type to convert to
     */
    private static void convertFolder(File folder, Format inputFormat, Format outputFormat) {
        File[] files = folder.listFiles();
        if (files == null) {
            return;
        }

        for (File file : files) {
            if (isRecursive && file.isDirectory()) {
                convertFolder(file, inputFormat, outputFormat);
            } else if (file.getName().endsWith(formatStrings.get(inputFormat))) {
                convert(file.getPath(), file.getPath() + formatStrings.get(outputFormat));
            }
        }
    }
}
