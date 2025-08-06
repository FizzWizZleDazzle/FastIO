import java.io.*;
import java.nio.file.*;
import java.util.*;

/**
 * Cross-platform utility for FastIO competitive programming library
 * Replaces shell scripts with Java-based tools that work on all platforms
 */
public class FastIOUtil {
    
    private static final String TEMPLATE_FILE = "../templates/Solution.java";
    private static final String FASTIO_FILE = "../src/FastIO.java";
    private static final String LEGACY_FASTIO = "../FastIO.java";
    private static final String LEGACY_TEMPLATE = "../Solution.java";
    
    public static void main(String[] args) {
        if (args.length == 0) {
            showUsage();
            return;
        }
        
        try {
            String command = args[0].toLowerCase();
            switch (command) {
                case "check":
                    checkInstallation();
                    break;
                case "compile":
                    compile(args.length > 1 ? args[1] : "Solution.java");
                    break;
                case "run":
                    run(args.length > 1 ? args[1] : "Solution");
                    break;
                case "test":
                    if (args.length < 4) {
                        System.err.println("Error: test command requires [file] [input] [output]");
                        System.exit(1);
                    }
                    test(args[1], args[2], args[3]);
                    break;
                case "new":
                    createNew(args.length > 1 ? args[1] : "NewSolution");
                    break;
                case "help":
                case "-h":
                case "--help":
                    showUsage();
                    break;
                default:
                    System.err.println("Unknown command: " + command);
                    showUsage();
                    System.exit(1);
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
    
    private static void showUsage() {
        System.out.println("FastIO Cross-Platform Utility");
        System.out.println("Usage: java FastIOUtil [command] [options]");
        System.out.println();
        System.out.println("Commands:");
        System.out.println("  check              - Verify FastIO installation");
        System.out.println("  compile [file]     - Compile Java file (default: Solution.java)");
        System.out.println("  run [file]         - Compile and run Java file");
        System.out.println("  test [file] [in] [out] - Run with test input/output files");
        System.out.println("  new [name]         - Create new solution from template");
        System.out.println();
        System.out.println("Examples:");
        System.out.println("  java FastIOUtil check");
        System.out.println("  java FastIOUtil compile Solution.java");
        System.out.println("  java FastIOUtil run MyProblem");
        System.out.println("  java FastIOUtil test Solution input.txt output.txt");
        System.out.println("  java FastIOUtil new Problem1");
    }
    
    private static void checkInstallation() {
        System.out.println("Checking FastIO installation...");
        System.out.println();
        
        // Check Java compiler
        if (!isJavaCompilerAvailable()) {
            System.err.println("✗ Java compiler (javac) not found. Please install Java JDK.");
            System.exit(1);
        }
        System.out.println("✓ Java compiler found");
        
        // Check FastIO.java
        boolean fastioFound = false;
        if (Files.exists(Paths.get(FASTIO_FILE))) {
            System.out.println("✓ FastIO.java found");
            fastioFound = true;
            if (compileFile(FASTIO_FILE, true)) {
                System.out.println("✓ FastIO.java compiles successfully");
            } else {
                System.out.println("✗ FastIO.java compilation failed");
            }
        } else if (Files.exists(Paths.get(LEGACY_FASTIO))) {
            System.out.println("✓ FastIO.java found (legacy location)");
            fastioFound = true;
            if (compileFile(LEGACY_FASTIO, true)) {
                System.out.println("✓ FastIO.java compiles successfully");
            } else {
                System.out.println("✗ FastIO.java compilation failed");
            }
        }
        
        if (!fastioFound) {
            System.out.println("⚠ FastIO.java not found (not required if using template)");
        }
        
        // Check template
        boolean templateFound = false;
        String templatePath = null;
        if (Files.exists(Paths.get(TEMPLATE_FILE))) {
            templatePath = TEMPLATE_FILE;
            templateFound = true;
        } else if (Files.exists(Paths.get(LEGACY_TEMPLATE))) {
            templatePath = LEGACY_TEMPLATE;
            templateFound = true;
        }
        
        if (templateFound) {
            System.out.println("✓ Solution.java template found");
            if (compileFile(templatePath, true)) {
                System.out.println("✓ Solution.java template compiles successfully");
            } else {
                System.out.println("✗ Solution.java template compilation failed");
            }
        } else {
            System.out.println("⚠ Solution.java template not found");
        }
        
        System.out.println();
        System.out.println("FastIO is ready to use!");
        System.out.println("To get started:");
        System.out.println("  1. java FastIOUtil new YourProblem");
        System.out.println("  2. Edit YourProblem.java and implement your solution");
        System.out.println("  3. java FastIOUtil run YourProblem");
    }
    
    private static boolean isJavaCompilerAvailable() {
        try {
            Process process = Runtime.getRuntime().exec("javac -version");
            process.waitFor();
            return process.exitValue() == 0;
        } catch (Exception e) {
            return false;
        }
    }
    
    private static void compile(String fileName) {
        if (!fileName.endsWith(".java")) {
            fileName += ".java";
        }
        
        String fullPath = "../" + fileName;
        if (!Files.exists(Paths.get(fullPath))) {
            System.err.println("Error: File " + fileName + " not found.");
            System.exit(1);
        }
        
        System.out.println("Compiling " + fileName + "...");
        if (compileFile(fullPath, false)) {
            System.out.println("✓ Compilation successful");
        } else {
            System.err.println("✗ Compilation failed");
            System.exit(1);
        }
    }
    
    private static boolean compileFile(String fileName, boolean quiet) {
        try {
            ProcessBuilder pb = new ProcessBuilder("javac", fileName);
            pb.inheritIO();
            if (quiet) {
                pb.redirectError(ProcessBuilder.Redirect.DISCARD);
                pb.redirectOutput(ProcessBuilder.Redirect.DISCARD);
            }
            Process process = pb.start();
            process.waitFor();
            return process.exitValue() == 0;
        } catch (Exception e) {
            if (!quiet) {
                System.err.println("Error compiling: " + e.getMessage());
            }
            return false;
        }
    }
    
    private static void run(String className) {
        String javaFile = className + ".java";
        
        // Compile first
        compile(javaFile);
        
        System.out.println("Running " + className + "...");
        try {
            ProcessBuilder pb = new ProcessBuilder("java", className);
            pb.directory(new File(".."));  // Run from parent directory
            pb.inheritIO();
            Process process = pb.start();
            process.waitFor();
        } catch (Exception e) {
            System.err.println("Error running: " + e.getMessage());
            System.exit(1);
        }
    }
    
    private static void test(String className, String inputFile, String outputFile) {
        String inputPath = "../" + inputFile;
        String outputPath = "../" + outputFile;
        
        if (!Files.exists(Paths.get(inputPath))) {
            System.err.println("Error: Input file " + inputFile + " not found.");
            System.exit(1);
        }
        
        if (!Files.exists(Paths.get(outputPath))) {
            System.err.println("Error: Output file " + outputFile + " not found.");
            System.exit(1);
        }
        
        String javaFile = className.endsWith(".java") ? className : className + ".java";
        
        // Compile first
        compile(javaFile);
        
        System.out.println("Testing " + className + " with " + inputFile + " and " + outputFile + "...");
        try {
            ProcessBuilder pb = new ProcessBuilder("java", className.replace(".java", ""));
            pb.directory(new File(".."));  // Run from parent directory
            pb.environment().put("TEST_MODE", "true");
            pb.environment().put("TEST_INPUT", inputFile);
            pb.environment().put("TEST_OUTPUT", outputFile);
            pb.inheritIO();
            Process process = pb.start();
            process.waitFor();
        } catch (Exception e) {
            System.err.println("Error testing: " + e.getMessage());
            System.exit(1);
        }
    }
    
    private static void createNew(String className) {
        String fileName = "../" + className + ".java";
        
        if (Files.exists(Paths.get(fileName))) {
            System.err.println("Error: File " + className + ".java already exists.");
            System.exit(1);
        }
        
        // Try to find template
        String templatePath = null;
        if (Files.exists(Paths.get(TEMPLATE_FILE))) {
            templatePath = TEMPLATE_FILE;
        } else if (Files.exists(Paths.get(LEGACY_TEMPLATE))) {
            templatePath = LEGACY_TEMPLATE;
        } else if (Files.exists(Paths.get("../templates/Solution.java"))) {
            templatePath = "../templates/Solution.java";
        }
        
        if (templatePath == null) {
            System.err.println("Error: Solution.java template not found.");
            System.exit(1);
        }
        
        try {
            String content = new String(Files.readAllBytes(Paths.get(templatePath)));
            content = content.replaceAll("public class Solution", "public class " + className);
            content = content.replaceAll("Solution::", className + "::");
            
            Files.write(Paths.get(fileName), content.getBytes());
            System.out.println("✓ Created " + className + ".java from template");
            System.out.println("You can now edit " + className + ".java and implement your solution.");
        } catch (IOException e) {
            System.err.println("Error creating file: " + e.getMessage());
            System.exit(1);
        }
    }
}