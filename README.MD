# FieryDragon

FieryDragon is a Java application designed to run on macOS. This README provides detailed instructions on how to set up and run the application on your macOS system.

![alt text](<docs/Screenshot 2024-08-01 at 22.54.02.png>)
![alt text](<docs/Screenshot 2024-08-01 at 22.53.44.png>)
![alt text](<docs/Screenshot 2024-08-01 at 22.53.15.png>)
## Prerequisites

Before you begin, ensure you have the following installed on your macOS system:

- [Java Development Kit (JDK)](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html) (version 11 or later)

You can verify your Java installation by running the following command in your terminal:

```sh
java -version
```

## Installation

1. **Download the FieryDragon.jar file**

   Obtain the `FieryDragon.jar` file.

2. **Open Terminal**

   You can open Terminal by navigating to `Applications` > `Utilities` > `Terminal`, or by pressing `Command + Space` and typing `Terminal` in the Spotlight search.

## Running the Application

1. **Navigate to the directory containing FieryDragon.jar**

   Use the `cd` (change directory) command to navigate to the directory where the `FieryDragon.jar` file is located. Replace `/path/to/your/jar` with the actual path to your `.jar` file.

   ```sh
   cd /path/to/your/jar
   ```

2. **Run the FieryDragon.jar file**

   Execute the `.jar` file using the `java -jar` command:

   ```sh
   java -jar FieryDragon.jar
   ```

   This command will start the FieryDragon application.

## Notes

- The application was developed and tested on macOS. While it may work on other operating systems, its performance and compatibility are optimized for macOS.
- Ensure you have the correct version of Java installed to avoid compatibility issues.

## Troubleshooting

If you encounter any issues while running the application, consider the following steps:

1. **Check Java Version**

   Ensure you are using the correct version of Java:

   ```sh
   java -version
   ```

2. **Verify File Path**

   Double-check the path to the `FieryDragon.jar` file to ensure it is correct.

3. **Permissions**

   Ensure you have the necessary permissions to execute the file. You may need to adjust the file permissions using the `chmod` command:

   ```sh
   chmod +x FieryDragon.jar
   ```

4. **Consult Logs**

   Check any error messages or logs output in the Terminal for clues on what might be going wrong.

