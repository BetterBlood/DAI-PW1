package ch.heigvd;

public class Main {
    // -i input
    // -o output
    // -r recursive
    // -h help
    // (empty , same as help)

    public static void main(String[] args) {
        // Recursive folder creation
        Converter.convert("./image/webp.webp", "./image/a/b/output.png");
        // Conversion png->webp
        Converter.convert("./image/png.png", "./image/output.webp");
        // Recursive conversion
        Converter.convertRecursively("./image", "png", "webp");
    }
}