package ch.heigvd;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

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

    private static void test() {
        // Recursive folder creation from output
        // Conversion webp->png
        Converter.changeParameters(true, false);
        Converter.convert("./image/webp.webp", "./image/a/b/webp.png");
        // Conversion png->webp
        Converter.changeParameters(true, false);
        Converter.convert("./image/png.png", "./image/png.webp");
        // Conversion jpg->webp
        Converter.changeParameters(false, false);
        Converter.convert("./image/jpg.jpg", "./image/jpg.webp");
        // Conversion webp->jpg
        Converter.changeParameters(false, false);
        Converter.convert("./image/webp.webp", "./image/webp.jpg");
        // Folder conversion
        Converter.changeParameters(true, false);
        Converter.convert("./image", "png", "webp");
        // Recursive folder conversion
        Converter.changeParameters(true, true);
        Converter.convert("./image", "png", "webp");
    }
}