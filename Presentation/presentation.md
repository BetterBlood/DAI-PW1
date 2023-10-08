# Command Converter Picture


## Introduction

## Presents the CLI application
![files](Files.png)
![main](Main.png)

## Demo of the CLI application

### help

```bash
java -jar ../target/dai-pw1-1.0-SNAPSHOT.jar
```
```bash
java -jar ../target/dai-pw1-1.0-SNAPSHOT.jar -h
```

### Convert from file to file

```bash
java -jar ../target/dai-pw1-1.0-SNAPSHOT.jar -i "../images/stanleypng.png" -o "../images/output.webp"
```
```bash
java -jar ../target/dai-pw1-1.0-SNAPSHOT.jar -l -i "../images/stanleypng.png" -o "../images/output.webp"
```

### Convert from a directory

```bash
java -jar ../target/dai-pw1-1.0-SNAPSHOT.jar -d "../images" -i "jpg" -o "png"
```
```bash
java -jar ../target/dai-pw1-1.0-SNAPSHOT.jar -r -d "../images" -i "jpg" -o "png"
```

### Verbose
```bash
java -jar ../target/dai-pw1-1.0-SNAPSHOT.jar -i "../images/pets/cat.jpg" -o "../images/output.webp" -l
```
```bash
java -jar ../target/dai-pw1-1.0-SNAPSHOT.jar -i "../images/pets/cat.jpg" -o "../images/output.webp" -l -v
```
Example :

![before](treeBefore.png)
![after](treeAfter.png)