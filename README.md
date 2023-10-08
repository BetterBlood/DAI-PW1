# ~ DAI : Practical Work 1~

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
  - Path pointing to a directory whose content matching -i parameter will be converted to -o parameter format type, ie : 'images'
  - The extension of the conversion will be appended onto the name of the input file. ex. a file 'a.png' will be converted to 'a.png.webp' for a conversion png to webp.
  - Can be used with -r parameter to loop recursively in subdirectories.
- -h, --[help](#help) Display help.
- -l, --[lossless](#lossless) Enable lossless conversion. Only useful if the output is in webp format.
- -r, --[recursive](#recursive) Enable recursion in subdirectories. Only useful with -d parameter.
- -v, --[verbose](#verbose) Enable verbose mode.

## Known Issues

- This tool will overwrite any existing file matching the output name.

## Examples
Any path variables can be written with or without quotation mark. The latter only if there is no space in it.

Relative paths can be written with or without the leading "./"

### Help
To call the application and show the help :
```bash
java -jar target/dai-pw1-1.0-SNAPSHOT.jar -h
```

### Convert
To call the application and convert a file from 'images/stanleypng.png' to 'output/output.webp' :
```bash
java -jar target/dai-pw1-1.0-SNAPSHOT.jar -i "images/stanleypng.png" -o "output/output.webm"
```

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
To call the application and convert a file from 'images/stanleypng.png' to 'output/output.webp', with a lossless compression :
```bash
java -jar target/dai-pw1-1.0-SNAPSHOT.jar -i "images/stanleypng.png" -o "output/output.webp" -l
```
```bash
java -jar target/dai-pw1-1.0-SNAPSHOT.jar -i "images/stanleypng.png" -lo "output/output.webp"
```
```bash
java -jar target/dai-pw1-1.0-SNAPSHOT.jar -li "images/stanleypng.png" -o "output/output.webp"
```

### Verbose
To call the application and convert a file from 'images/pets/cat.png' to 'output/output.webp', with a lossless compression in a verbose mode.

You can, for example, use one of the following non-exhaustive commands bellow :
```bash
java -jar target/dai-pw1-1.0-SNAPSHOT.jar -i "images/pets/cat.png" -o "output/output.webp" -lv
```
```bash
java -jar target/dai-pw1-1.0-SNAPSHOT.jar -lv -i "images/pets/cat.png" -o "output/output.webp"
```
```bash
java -jar target/dai-pw1-1.0-SNAPSHOT.jar -lvi "images/pets/cat.png" -o "output/output.webp"
```
```bash
java -jar target/dai-pw1-1.0-SNAPSHOT.jar -v -l -i "images/pets/cat.png" -o "output/output.webp"
```
With the directory 'images' from 'png' to 'webp' :
```bash
java -jar target/dai-pw1-1.0-SNAPSHOT.jar -rvld "images" -i "png" -o "webp"
```

### Execution examples

```bash
java -jar target/dai-pw1-1.0-SNAPSHOT.jar -i images\stanley.jpg -o images\test.webp
Conversion Running...
Converted : images\stanley.jpg to : images\test.webp
Success ! Elapsed time: 4196ms / 4196541100ns
```

```bash
java -jar target/dai-pw1-1.0-SNAPSHOT.jar -rd images -i webp -o jpeg                    
Conversion Running...
Converted : images\test.webp to : images\test.webp.jpeg
Converted : images\test2.webp to : images\test2.webp.jpeg
Success ! Elapsed time: 1442ms / 1442195800ns
```