package ch.heigvd;

public class Main {
    // -i input
    // -o output
    // -r recursive
    // -h help
    // (empty , same as help)

    public static void main(String[] args) {
        // Always call changeParameters with matching arguments before doing a conversion else it will keep the prior parameters saved
        Converter.changeParameters(true, true);
        // Recursive folder creation from output
        // Conversion webp->png
        Converter.convert("./image/webp.webp", "./image/a/b/webp.png");
        // Conversion png->webp
        Converter.convert("./image/png.png", "./image/png.webp");
        // Conversion jpg->webp
        Converter.convert("./image/jpg.jpg", "./image/jpg.webp");
        // Conversion webp->jpg
        Converter.convert("./image/webp.webp", "./image/webp.jpg");
        // Folder conversion
        Converter.convert("./image", "png", "webp");
        // Recursive folder conversion
        Converter.convert("./image", "png", "webp");
    }
}