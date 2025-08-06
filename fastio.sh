#!/bin/bash

# FastIO Utility Script for USACO Competitive Programming
# This script helps compile and test your Java solutions

usage() {
    echo "FastIO Utility Script"
    echo "Usage: $0 [command] [options]"
    echo ""
    echo "Commands:"
    echo "  compile [file]     - Compile Java file (default: Solution.java)"
    echo "  run [file]         - Compile and run Java file"
    echo "  test [file] [in] [out] - Run with test input/output files"
    echo "  new [name]         - Create new solution from template"
    echo "  check              - Verify FastIO installation"
    echo ""
    echo "Examples:"
    echo "  $0 compile           # Compile Solution.java"
    echo "  $0 run MyProblem     # Compile and run MyProblem.java"
    echo "  $0 test Solution input.txt output.txt"
    echo "  $0 new Problem1      # Create Problem1.java from template"
}

check_java() {
    if ! command -v javac &> /dev/null; then
        echo "Error: Java compiler (javac) not found. Please install Java JDK."
        exit 1
    fi
}

compile_file() {
    local file="$1"
    if [ -z "$file" ]; then
        file="Solution.java"
    elif [[ ! "$file" == *.java ]]; then
        file="$file.java"
    fi
    
    if [ ! -f "$file" ]; then
        echo "Error: File $file not found."
        exit 1
    fi
    
    echo "Compiling $file..."
    javac "$file"
    if [ $? -eq 0 ]; then
        echo "✓ Compilation successful"
    else
        echo "✗ Compilation failed"
        exit 1
    fi
}

run_file() {
    local file="${1:-Solution}"
    local java_file="$file.java"
    
    compile_file "$java_file"
    
    echo "Running $file..."
    java "$file"
}

test_file() {
    local file="${1:-Solution}"
    local input_file="$2"
    local output_file="$3"
    
    if [ -z "$input_file" ] || [ -z "$output_file" ]; then
        echo "Error: Please provide input and output files for testing."
        echo "Usage: $0 test [file] [input_file] [output_file]"
        exit 1
    fi
    
    if [ ! -f "$input_file" ]; then
        echo "Error: Input file $input_file not found."
        exit 1
    fi
    
    if [ ! -f "$output_file" ]; then
        echo "Error: Output file $output_file not found."
        exit 1
    fi
    
    # Add .java extension if not present
    local java_file="$file"
    if [[ ! "$java_file" == *.java ]]; then
        java_file="$file.java"
    fi
    
    compile_file "$java_file"
    
    echo "Testing $file with $input_file and $output_file..."
    TEST_MODE=true TEST_INPUT="$input_file" TEST_OUTPUT="$output_file" java "$file"
}

create_new() {
    local name="${1:-NewSolution}"
    local file="$name.java"
    
    if [ -f "$file" ]; then
        echo "Error: File $file already exists."
        exit 1
    fi
    
    # Copy template and replace class name
    if [ -f "Solution.java" ]; then
        sed "s/public class Solution/public class $name/g; s/Solution::/$(echo $name)::/g" Solution.java > "$file"
        echo "✓ Created $file from template"
        echo "You can now edit $file and implement your solution."
    else
        echo "Error: Solution.java template not found."
        exit 1
    fi
}

check_installation() {
    echo "Checking FastIO installation..."
    
    check_java
    echo "✓ Java compiler found"
    
    if [ -f "FastIO.java" ]; then
        echo "✓ FastIO.java found"
        javac FastIO.java 2>/dev/null
        if [ $? -eq 0 ]; then
            echo "✓ FastIO.java compiles successfully"
        else
            echo "✗ FastIO.java compilation failed"
        fi
    else
        echo "⚠ FastIO.java not found (not required if using template)"
    fi
    
    if [ -f "Solution.java" ]; then
        echo "✓ Solution.java template found"
        javac Solution.java 2>/dev/null
        if [ $? -eq 0 ]; then
            echo "✓ Solution.java template compiles successfully"
        else
            echo "✗ Solution.java template compilation failed"
        fi
    else
        echo "⚠ Solution.java template not found"
    fi
    
    echo ""
    echo "FastIO is ready to use!"
    echo "To get started:"
    echo "  1. Copy Solution.java as your starting template"
    echo "  2. Implement your solution in the solve() method"
    echo "  3. Use: $0 run YourSolution"
}

# Main script logic
case "${1:-help}" in
    "compile")
        check_java
        compile_file "$2"
        ;;
    "run")
        check_java
        run_file "$2"
        ;;
    "test")
        check_java
        test_file "$2" "$3" "$4"
        ;;
    "new")
        create_new "$2"
        ;;
    "check")
        check_installation
        ;;
    "help"|"-h"|"--help"|*)
        usage
        ;;
esac