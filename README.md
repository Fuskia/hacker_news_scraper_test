# Hacker News Scraper Test

A simple command line application that returns the top 'n' Hacker News posts in JSON. 

## Prerequisites

The program can be executed on any operating system (Linux, Windows, Mac OS X). 

The only requirement to run it is to have Java 8 installed.

Make sure you're installing JDK 1.8.

### Checking the Java version

Open a terminal.

Type 'java -version' and press Enter.

If Java is correctly installed on your computer, the name and version of the Java virtual machine is displayed:

```
java version "1.8.0_171"
Java(TM) SE Runtime Environment (build 1.8.0_171-b11)
Java HotSpot(TM) 64-Bit Server VM (build 25.171-b11, mixed mode)
```

### Installing Java

#### For Windows users

If the required version of Java is not installed on your computer:

Download it from the Oracle website (Java 8) and choose the appropriate platform for your hardware and Windows version.
Run the downloaded .exe file and follow the instructions displayed.


#### For Linux users

You can download Java 8 from Oracle website (choose the right architecture). You can find RPM and TGZ packages to install. If you use Ubuntu the following is a better method:

```
$ sudo add-apt-repository ppa:webupd8team/java
$ sudo apt-get update
$ sudo apt-get install oracle-java8-installer
```

This way your JDK will be upgraded every time a new release is available. 

In case you have another JDK installed (like GCJ), you can select the right one this way:

```
$ sudo update-alternatives --config java
```

#### For Mac OS X Users

Java packages and instructions for installation are available from the Oracle website.


## How to run

Download the 'hackernews.jar' file.
Open the terminal, go to the directory that contains the 'hackernews.jar' file and run the program with the following:

```
java -jar hackenews.jar n
```

where 'n' is the number of posts you want to print.

## Dockerfile

You need to have a working version of Docker running.

Clone the repository and go into that repository. 

Build the Docker image from the Dockerfile in the repository using the following command:

```
docker build -t <insert image name> .
```

Then run it:

```
docker run -it <image name>
```

Now you can run the program using the following command:

```
java -jar hackernews.jar n
```

where 'n' is the number of posts you want to print.


## Libraries used

### Jsoup 
Jsoup is a Java library for working with real-world HTML. It provides a very convenient API for extracting and manipulating data.
The library provides all the necessary functionality to get the required data from the html pages and is quite easy to use. It's the first time I use this library, after some researches and readings I decided to choose the most recommended one.


### Json
I used this library to create the output in the requested format.


### JUnit5

I've always used JUnit to test Java applications, so I'm quite familiar with it. The integration provided by Eclipse makes the unit tests creation and setup even easier.
In order to test the application I created a mocked html file, which contains a modified version of the Hacker News html page.
It contains 10 posts, some of them are correct, others have "problems", like titles too long, negative points, empty author, etc.
I created a suite of tests to ensure the parser works fine in all these cases.
The test folder contains the 'test.html' file and the test class.




