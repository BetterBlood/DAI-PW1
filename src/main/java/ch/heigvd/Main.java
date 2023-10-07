package ch.heigvd;

import com.luciad.imageio.webp.WebPReadParam;
import com.luciad.imageio.webp.WebPWriteParam;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import javax.imageio.*;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.FileImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.Callable;

@Command(
        name = "image file converter",
        description = "convert jpg-png-webp format, with lossless if needed",
        version = "1.0.0"
)
public class Main implements Callable<Integer> {

    @Option(names={"-i", "--inFile", "--inputFile"},
            description = "path to the file to get in input ie : 'image/test.jpg'",
            required = true,
            defaultValue = Option.NULL_VALUE)
    private String inFile;

    @Option(names={"-o", "--outFile", "--outputFile"},
            description = "path to the new file ie : 'image/test.png'",
            required = true,
            defaultValue = Option.NULL_VALUE)
    private String outFile;

    @Option(names={"-l", "--lossless"}, description = "quality of the conversion, lossly by default")
    private boolean lossless = false;

    @Option(names={"-h", "--help"}, description = "display Help default : ${DEFAULT-VALUE}", usageHelp = true)
    private boolean help = false;

    @Option(names={"-v", "--verbose"}, description = "Verbose mode default : ${DEFAULT-VALUE}")
    private boolean verbose = false;

    @Option(names={"-e", "--echo"}, description = "echo ${DEFAULT-VALUE}", required = false, defaultValue = "test")
    private String echoValue;

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

    public static void main(String[] args)
    {
        System.out.println("Hello world!");
        new CommandLine(new Main()).execute(args);
        System.out.println("Good bye world!");
    }

    @Override
    public Integer call() throws Exception {
        if (echoValue.length() > 0) System.out.println("echo : '" + echoValue + "'");
        if (verbose) System.out.println("explain a bit more what is going on during the conversion (progress bar for example)");

        System.out.println("lossless : '" + lossless + "'");

        System.out.println("source File : '" + inFile + "'");
        System.out.println("destination File : '" + outFile + "'");
        return 0;
    }
}