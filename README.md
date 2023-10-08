# ~ Labo 1 DAI ~

## Description 

This CLI tool is used to convert webp files to png/jpg and vice versa.
See [parameters](#parameters) and [examples](#examples) for more information.

## Installation

Requires maven to be installed.

Clone the repository and use ```mvn package``` to build the application.

## Usage

After build, use the following command to run the application. More examples listed below.
```bash
java -jar target/dai-pw1-1.0-SNAPSHOT.jar -i "input.webp" -o "output.jpg"
```

### Parameters

- -i, --[in](#convert), --[input](#convert) 
  - Path pointing to the input file for a conversion, ie : 'image/test.jpg'
  - If used with -d parameter, format type desired as an input, ie : 'jpg'
- -o, --[out](#convert), --[output](#convert) 
  - Path pointing to the output of a conversion. The file format type explicits the conversion output format type. If no such file exists it will be created, else it will overwrite the existing file, ie : 'image/test.png'
  - If used with -d parameter, format type desired as an output, ie : 'png'
- -d, --[directory](#directory) 
  - Path pointing to a directory whose content matching -i parameter will be converted to -o parameter format type. ie 'images'
  - Can be used with -r parameter to loop recursively in subdirectories.

//tests if paths works relatively or absolutely

//what does -e do ?!????

- -e, --echo echo test 
- -h, --[help](#help) Display help.
- -l, --[lossless](#lossless) Enable lossless conversion.
- -r, --[recursive](#recursive) Enable recursion in subdirectories. Only work with -d parameter.
- -v, --[verbose](#verbose) Enable verbose mode.

## Known Issues

- This tool will overwrite any existing file matching the name.

## Examples

### Help
To call the application and show the help :
```bash
java -jar target/dai-pw1-1.0-SNAPSHOT.jar -h
```

### Convert
To call the application and convert a file from 'images/image.png' to 'images/image.jpg' :
```bash
java -jar target/dai-pw1-1.0-SNAPSHOT.jar -i "images/image.png" -o "images/image.jpg"
```
The quotation marks are not needed if the path has no space like in the example above.

TODO : Directory avant Recursive ! + lien depuis commandes !

### Directory
To call the application and convert all files from the directory 'images' of the type 'jpg' and convert them into 'png' :
```bash
java -jar target/dai-pw1-1.0-SNAPSHOT.jar -d "images" -i "jpg" -o "png"
```

### Recursive
To call the application and convert files in 'images' recursively in subFolder of 'images' :
```bash
java -jar target/dai-pw1-1.0-SNAPSHOT.jar -d "images" -i "jpg" -o "png" -r
```
```bash
java -jar target/dai-pw1-1.0-SNAPSHOT.jar -rd "images" -i "jpg" -o "png"
```

### Lossless
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

### Verbose
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