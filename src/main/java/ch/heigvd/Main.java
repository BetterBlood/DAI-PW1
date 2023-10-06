package ch.heigvd;

public class Main {
    // -i input
    // -o output
    // -r recursive
    // -h help
    // (empty , same as help)

    public static void main(String[] args) {
        // Singles
        Converter.convert("image/webp.webp", "image/output.png");
        Converter.convert("image/png.png", "image/output.webp");
        // Recursion
        Converter.convertRecursively("./image", "png", "webp");
    }
}