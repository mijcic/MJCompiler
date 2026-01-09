# MJCompiler – MicroJava Compiler
## Project Description

MJCompiler is a **full-featured compiler** for the **MicroJava programming language**. It translates syntactically and semantically correct MicroJava programs into **bytecode** executable on the **MicroJava Virtual Machine (MJVM)**. The compiler implements all major compilation phases: lexical analysis, syntax analysis, semantic analysis, and code generation.

Key features include:

- Lexical analysis with tokenization using **JFlex**

- Syntax analysis with **AST construction** using CUP/AST-CUP

- Semantic analysis with **symbol table validation** and type checking

- Robust **error detection** and recovery for syntactic and semantic mistakes

- Code generation to **MJVM-compatible bytecode**

## Tech Stack

- **Language:** Java (JDK 1.8)

- **Lexical Analyzer:** JFlex

- **Parser / AST:** CUP / AST-CUP

- **Execution Environment:** MJVM

- **Libraries:** symboltable-1-1.jar, mj-runtime-1.1.jar, log4j-1.2.17.jar, cup_v10k.jar, JFlex.jar

## Project Highlights

- Complete compiler covering all major phases: lexical, syntax, semantic, and code generation

- Supports error recovery for **robust parsing** of incorrect programs

- Generates **executable bytecode** compatible with MJVM

- Includes **test programs** for verifying all compiler functionalities
