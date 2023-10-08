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
            description = "Path pointing to a directory whose content matching -i parameter will be converted to -o parameter format type, ie : 'images'\nThe extension of the conversion will be appended onto the name of the input file. ex. a file 'a.png' will be converted to 'a.png.webp' for a conversion png to webp.",
            defaultValue = Option.NULL_VALUE)
    private String directory;

    @Option(names={"-i", "--in", "--input"},
            description = "Path pointing to the input file for a conversion, ie : 'image/test.jpg'\nIf used with -d parameter, format type desired as an input, ie : 'jpg'",
            required = true,
            defaultValue = Option.NULL_VALUE)
    private String input;

    @Option(names={"-o", "--out", "--output"},
            description = "Path pointing to the output of a conversion. The file format type explicits the conversion output format type. If no such file exists it will be created, else it will overwrite the existing file, ie : 'image/test.png'\nIf used with -d parameter, format type desired as an output, ie : 'png'",
            required = true,
            defaultValue = Option.NULL_VALUE)
    private String output;

    @Option(names={"-l", "--lossless"}, description = "Enable lossless conversion.")
    private boolean lossless = false;


    @Option(names={"-r", "--recursive"}, description = "Enable recursion in subdirectories. Only work with -d parameter.")
    private boolean recursive = false;

    @Option(names={"-h", "--help"}, description = "Display Help", usageHelp = true)
    private boolean help = false;

    @Option(names={"-v", "--verbose"}, description = "Enable verbose mode")
    private boolean verbose = false;

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
        if (input == null || output == null)
        {
            spec.commandLine().usage(System.err);
            return 1;
        }

        if (verbose)
        {
            System.out.println("verbose : '" + verbose + "'");
            System.out.println("explain a bit more what is going on during the conversion.");
            System.out.println("lossless : '" + lossless + "'");
            System.out.println("recursive : '" + recursive + "'");
            System.out.println(directory != null ? "Directory selected : " + directory : "no directory");
            System.out.println("source File : '" + input + "'");
            System.out.println("destination File : '" + output + "'");
        }

        Converter.changeParameters(lossless, recursive);
        return directory == null ? Converter.convert(input, output) : Converter.convert(directory, input, output);
    }
}