# ~ Labo 1 DAI ~

## Description 

This CLI tool is used to convert webp files to png/jpg and vice versa.
See [parameters](#parameters) and [examples](#examples) for more information.

## Usage

Use ```mvn package``` to build the application.

After build, use the command bellow (with required [parameters](#parameters)) to run the application.
```bash
java -jar target/dai-pw1-1.0-SNAPSHOT.jar
``` 

Be carefully when using this tool, the output is cleared if already exist.


## Parameters
- -d, --[directory](#directory) if we want to convert multiple files in the directory, ie 'images'
- -e, --echo echo test
- -h, --[help](#help)               display Help default : false
- -i, --[inFile](#convert), --[inputFile](#convert) path to the file to get as input, ie : 'image/test.jpg' __OR if -d given : type of input files, ie : 'jpg'__
- -l, --[lossless](#lossless)           quality of the conversion, lossly by default
- -o, --[outFile](#convert), --[outputFile](#convert) path to the new file, ie : 'image/test.png' __OR if -d given : type of output files, ie : 'png'__
- -r, --[recursive](#recursive)          recursivity of the folder creation if needed
- -v, --[verbose](#verbose)            Verbose mode, default : false

### Examples

#### Help
To call the application and show the help :
```bash
java -jar target/dai-pw1-1.0-SNAPSHOT.jar -h
```

#### Convert
To call the application and convert a file from 'images/image.png' to 'images/image.jpg' :
```bash
java -jar target/dai-pw1-1.0-SNAPSHOT.jar -i "images/image.png" -o "images/image.jpg"
```
The "" are not needed if the path has no space, like in the example above.

TODO : Directory avant Recursive ! + lien depuis commandes !

#### Directory
To call the application and convert all files from the directory 'images' of the type 'jpg' and convert them into 'png' :
```bash
java -jar target/dai-pw1-1.0-SNAPSHOT.jar -d "images" -i "jpg" -o "png"
```

#### Recursive
To call the application and convert files in 'images' recursively in subFolder of 'images' :
```bash
java -jar target/dai-pw1-1.0-SNAPSHOT.jar -d "images" -i "jpg" -o "png" -r
```
```bash
java -jar target/dai-pw1-1.0-SNAPSHOT.jar -rd "images" -i "jpg" -o "png"
```

#### Lossless
To call the application and convert a file from 'images/image.png' to 'images/image.jpg', with a lossless compression :
```bash
java -jar target/dai-pw1-1.0-SNAPSHOT.jar -i "images/image.png" -o "images/image.jpg" -l
```
```bash
java -jar target/dai-pw1-1.0-SNAPSHOT.jar -i "images/image.png" -lo "images/image.jpg"
```
```bash
java -jar target/dai-pw1-1.0-SNAPSHOT.jar -li "images/image.png" -o "images/image.jpg"
```

#### Verbose
To call the application and convert a file from 'images/image.png' to 'images/image.jpg', with a lossless compression in a verbose mode.

You can, for example, use one of the following non-exhaustive commands bellow :
```bash
java -jar target/dai-pw1-1.0-SNAPSHOT.jar -i "images/image.png" -o "images/image.jpg" -lv
```
```bash
java -jar target/dai-pw1-1.0-SNAPSHOT.jar -lv -i "images/image.png" -o "images/image.jpg"
```
```bash
java -jar target/dai-pw1-1.0-SNAPSHOT.jar -lvi "images/image.png" -o "images/image.jpg"
```
```bash
java -jar target/dai-pw1-1.0-SNAPSHOT.jar -v -l -i "images/image.png" -o "images/image.jpg"
```
With the directory 'images' from 'jpg' to 'png' :
```bash
java -jar target/dai-pw1-1.0-SNAPSHOT.jar -rvld "images" -i "jpg" -o "png"
```

### Execution

// TODO add execution examples