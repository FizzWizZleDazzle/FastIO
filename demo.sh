#!/bin/bash

# FastIO Demo Script
echo "=== FastIO Demonstration ==="
echo ""

echo "1. Creating a new solution from template..."
./fastio.sh new Demo 2>/dev/null || echo "Demo.java already exists"

echo ""
echo "2. Testing the default solution (array sum)..."
echo "Input: 5 numbers (1 2 3 4 5)"
echo "Expected output: 15"
result=$(./fastio.sh test Solution sample_input.txt sample_output.txt 2>/dev/null)
echo "Result: $result"

echo ""
echo "3. Demonstrating FastIO features..."
echo "- ✓ Single-file submission ready"
echo "- ✓ Optimized I/O for competitive programming"  
echo "- ✓ Built-in testing framework"
echo "- ✓ Mathematical utilities (GCD, LCM, modPow, etc.)"
echo "- ✓ USACO-specific constants and helpers"

echo ""
echo "4. Quick setup instructions:"
echo "   ./fastio.sh new YourProblem"
echo "   ./fastio.sh run YourProblem"
echo "   ./fastio.sh test YourProblem input.txt output.txt"

echo ""
echo "FastIO is now ready for USACO competitive programming! 🚀"