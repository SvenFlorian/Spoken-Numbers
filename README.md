# Spoken Numbers Training Software

Spoken Numbers is a discipline in all memory competitions. The aim is to commit to memory and recall as many Spoken Numbers as possible, which are read out at a pace of 1 digit per second. This software is the official program that is used at memory competitions by the World Memory Sports Council.

## Requirements

Java Runtime Environment (JRE) 1.6 or later

## Features
* Playback of up to 990 numbers in intervals between 500 and 3000 milliseconds.
* Generate numbers into excel-files for easy printing.
* Evaluates and highlights wrong/correct answers.
* Can store your results in a textfile.
* Generate sheets with binary numbers and practice spoken binaries.
* *New feature* - Spoken Flash Numbers (one digit spoken + one digit shown, at a time)

# Running the Program

This java application can run on any Operating System that supports the Java Runtime Environment (1.6 or later). Generally, you just have to double-click the SpokenNumbers.jar-file and you should be ready to go. If that fails, go ahead and look below for further instructions based on your Operating System.

## Windows
Double-click on the 'run.bat'-file. If you want to create a desktop connection you can create your own shortcut by right-clicking on the batch file.

## Unix & Mac OS X

Open the terminal and locate the spoken numbers software. If you don't know how to do that, look on the next page for some basic commands. Note that the batch-file does NOT work on Unix and Mac OS X. When you have located the program, write the following: 
` java -jar "Spoken Numbers.jar" `  
OR  
`start javaw -jar "Spoken Numbers.jar"`

### Some Basic Command Line Commands

`$ cd ..`  
Changes to parent directory 

`$ cd foobar`  
Changes to subdirectory called foobar 

`$ ls`  
Lists all files/directories of the current location 

`$ java –jar file.jar`  
Runs the jar-file called ‘file.jar’


## Generating Numbers
The generating numbers feature can be found in the menu, and accepts up to 990 numbers. The generated sheet is then found in a folder called generated_sheets in the programs root directory. You can also generate binary numbers.

## Sound Files

The program is currently using the official [english] sounds, but you are free to use your own. You can do this by replacing the sound-files in the resources-folder. Please note that the sounds file-names must remain the same, ie '0.wav', '1.wav', '2.wav' etc.
Users who want to practice spoken numbers with a pace faster than 700 ms are recommended to use other sound files, as the current ones are too long for a clean playback. They may sound truncated, but they still work.


## Problems?

Make sure you have a working version of the Java runtime environment (at least Java JRE 1.6) installed on your computer. You may also want to check that your PATH system variable is set properly (for more information, see http://www.java.com/en/download/help/path.xml). If you still experience problems, please contact me and I will try to help.

# Contact

If you have any questions, bug reports or other wishes, feel free to contact me on florianminges@gmail.com. I check my emails frequently.