
/**
 * HackAssembler.Java
 * 
 * @author Franklin Ogidi
 * 
 * @description Drives the process of translating code written in the Hack assembly
 * language into machine code, according to the Hack Language specifications.
 * This program was written as part of the NandToTetris course: https://www.nand2tetris.org/project06
 * 
 * @version 1.0
 * 
 */

import java.io.*;
import java.util.*;

public class HackAssembler {
    private final SymbolTable symbols;  // stores pre-defined and user-defined symbols and labels
    private int current_line;           // keeps track of the current valid line during file I/O
    private Parser parser;              // parses Hack Assembly commands into individual parts

    // constructor
    public HackAssembler() {
        symbols = new SymbolTable();
        current_line = 0;
    } // end constructor

    /**
     * Performs the first pass on the file specified by filename, noting only the
     * labels. Adds label to the SymbolTable only at the first occurrence.
     * 
     * @param filename The .asm file to parse.
     */
    private void first_pass(final String filename) {
        try {
            final BufferedReader input = new BufferedReader(new FileReader(filename));
            boolean parse_success; // flag for parse error
            String line;

            while ((line = input.readLine()) != null) {
                parser = new Parser();
                parse_success = parser.parse_command(line);

                if (parse_success) {
                    if (line.trim().charAt(0) == '(') { // checking for labels [ eg. (LABEL) ]
                        // extract the label's symbol
                        final String symbol = line.trim().substring(line.indexOf("(") + 1, line.lastIndexOf(")"));

                        // add label to SymbolTable if it is not already present
                        if (!symbols.contains(symbol))
                            symbols.put(symbol, current_line);

                        current_line--; // label declarations don't count
                    }
                    current_line++; // successfully parsed command counts as one line
                }
            }
            input.close();
        } catch (final IOException ioe) {
            System.out.println(ioe);
            return;
        }
    } // end first pass

    /**
     * Translates a Hack Assembly file (.asm) into machine code (.hack file)
     * according to the Hack Machine Language specifications, after the first pass.
     * 
     * @param filename The assembly file to translate into machine code
     */
    private void translate(final String filename) {
        try {
            final String output_filename = filename.substring(0, filename.indexOf(".")) + ".hack"; // change file
                                                                                                   // extension from
                                                                                                   // .asm to .hack
            final BufferedReader input = new BufferedReader(new FileReader(filename));
            final PrintWriter output = new PrintWriter(output_filename);

            current_line = 0; // reset counter for current line
            boolean parse_success; // flag for parsing error
            String line;

            while ((line = input.readLine()) != null) {
                parser = new Parser();
                parse_success = parser.parse_command(line);

                if (parse_success && line.trim().charAt(0) != '(') { // label declarations don't count
                    if (parser.addr() == null) { // parsing a C-instruction
                        final String comp = Code.get_comp_code(parser.comp());
                        final String dest = Code.get_dest_code(parser.dest());
                        final String jump = Code.get_jump_code(parser.jump());
                        output.printf("111%s%s%s\n", comp, dest, jump);
                    } else { // parsing an A-instruction
                        final String var = parser.addr();

                        final Scanner sc = new Scanner(var);
                        if (sc.hasNextInt()) { // check if var is an integer
                            final String addr_binary = Integer.toBinaryString(Integer.parseInt(var)); // convert to
                                                                                                      // binary
                            output.println(pad_binary(addr_binary)); // write 16-bit binary to output
                        } else {
                            symbols.addVariable(var);
                            final String addr_binary = Integer.toBinaryString(symbols.get(var));
                            output.println(pad_binary(addr_binary));
                        }
                        sc.close();
                    }
                    current_line++;
                }
            }
            input.close();
            output.close();
        } catch (final IOException ioe) {
            System.out.println(ioe);
            return;
        }
    } // end translate
    /**
     * Pads a binary String with zeros to ensure 16-bit binary format
     * 
     * @param unpadded_binary The binary String without leading zeros
     * @return A 16-bit binary String with leading zeros where necessary
     */
    private String pad_binary(final String unpadded_binary) {
        String padded_binary = "";
        final int num_zeros = 16 - unpadded_binary.length();

        for (int i = 0; i < num_zeros; i++) {
            padded_binary += "0";
        }

        return padded_binary + unpadded_binary;
    } // end pad_binary

    /**
     * Interface for running the Hack Assembler in the command line in the following
     * format: $ java HackAssembler filename
     * 
     * @param args only the filename argument is supported
     */
    public static void main(final String[] args) {
        final String filename = args[0];
        final HackAssembler assembly = new HackAssembler();
        assembly.first_pass(filename);
        assembly.translate(filename);
    } // end main
} // end HackAssembler class