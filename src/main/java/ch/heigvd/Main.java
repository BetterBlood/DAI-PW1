package ch.heigvd;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Model.CommandSpec;
import picocli.CommandLine.Option;
import picocli.CommandLine.Spec;


import java.util.concurrent.Callable;

@Command(
        name = "image file converter : ",
        description = "convert jpg-png-webp image format, with/without lossless quality compression, is able to run recursively in subFolder",
        version = "1.0.2"
)
public class Main implements Callable<Integer> {

    // Console Color for UX
    public static final String RESET = "\033[0m";
    public static final String RED = "\033[1;31m";
    public static final String GREEN = "\033[1;32m";

    @Option(names={"-d", "--directory"},
            description = "if we want to convert multiple files in the directory, ie 'images'",
            defaultValue = Option.NULL_VALUE)
    private String directory;

    @Option(names={"-i", "--inFile", "--inputFile"},
            description = "path to the file to get as input, ie : 'image/test.jpg'\nOR if -d given : type of input files, ie : 'jpg'",
            required = true,
            defaultValue = Option.NULL_VALUE)
    private String inFile;

    @Option(names={"-o", "--outFile", "--outputFile"},
            description = "path to the new file, ie : 'image/test.png'\nOR if -d given : type of output files, ie : 'png'",
            required = true,
            defaultValue = Option.NULL_VALUE)
    private String outFile;

    @Option(names={"-l", "--lossless"}, description = "quality of the conversion, lossy by default")
    private boolean lossless = false;


    @Option(names={"-r", "--recursive"}, description = "recursivity of the folder creation if needed")
    private boolean recursive = false;

    @Option(names={"-h", "--help"}, description = "display Help default : ${DEFAULT-VALUE}", usageHelp = true)
    private boolean help = false;

    @Option(names={"-v", "--verbose"}, description = "Verbose mode, default : ${DEFAULT-VALUE}")
    private boolean verbose = false;

    @Option(names={"-e", "--echo"}, description = "echo 'input'", required = false, defaultValue = Option.NULL_VALUE)
    private String echoValue;

    @Spec
    CommandSpec spec; // display help if no parameters are given or if the required one are omitted
    
    public static void main(String[] args)
    {
        System.out.println("Conversion Running...");
        long start = System.nanoTime();
        int success = new CommandLine(new Main()).execute(args);
        long end = System.nanoTime();
        System.out.println((success == 0 ? GREEN + "Success ! " : RED + "Error ! ") + "Elapsed time: " + (end - start) / 1000000 + "ms / " + (end - start) + "ns" + RESET);
    }

    @Override
    public Integer call() throws Exception {
        if (inFile == null || outFile == null)
        {
            spec.commandLine().usage(System.err);
            return 1;
        }
        if (echoValue != null) System.out.println("echo : '" + echoValue + "'");

        if (verbose)
        {
            System.out.println("verbose : '" + verbose + "'");
            System.out.println("explain a bit more what is going on during the conversion.");

            System.out.println(echoValue != null ? "echoValue set": "echoValue not set");

            System.out.println("lossless : '" + lossless + "'");
            System.out.println("recursive : '" + recursive + "'");
            System.out.println(directory != null ? "Directory selected : " + directory : "no directory");
            System.out.println("source File : '" + inFile + "'");
            System.out.println("destination File : '" + outFile + "'");
        }

        Converter.changeParameters(lossless, recursive);
        return directory == null ? Converter.convert(inFile, outFile) : Converter.convert(directory, inFile, outFile);
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