# Hack-Assembler
A Java implementation of a 16-bit machine language assembler for the Hack Assembly language. Written as part of the NandToTetris course: https://www.nand2tetris.org/project06

# Description

1. **HackAssembler.Java**: Drives the process of translating code written in the Hack assembly language into machine code, 
according to the Hack Language specifications.
2. **Parser.java**: Unpacks a line of assembly instructions into underlying fields as specified by the Hack Language specification.
3. **Code.java**: Translates a given field into its corresponding value.
4. **SymbolTable.java**: Manages a table of symbols including predefined variables, user-defined variables and labels.

## Usage
```bash
$ javac HackAssembler.java
$ java HackAssembler HelloWorld.asm
```

## License
This project is licensed under the [GNU General Public License v3.0](LICENSE)

