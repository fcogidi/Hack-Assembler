/**
 * Parser.Java
 * 
 * @author Franklin Ogidi
 * 
 * @description Unpacks a string containing instructions into underlying fields as specified by the
 * Hack Language specification.
 * This program was written as part of the NandToTetris course: https://www.nand2tetris.org/project06
 * 
 * @version 1.0
 * 
 */

public class Parser {
    private String dest;    // holds the destination instruction
    private String comp;    // holds the computation instruction
    private String jump;    // holds the jump instruction
    private String addr;    // holds a 16-bit address

    // constructor
    public Parser() {
        dest = "null";
        jump = "null";
    } // end constructor

    /**
     * Parses a line of assembly instructions into difference fields according to
     * the Hack Language specification.
     * 
     * @param line An instruction to be parsed
     * 
     * @return True if line was successfully parsed; False otherwise.
     */
    public boolean parse_command(String line) {
        line = line.trim(); // remove leading and trailing whitespace

        if (!line.isEmpty()) {
            if (line.charAt(0) != '/') {
                if (line.contains("@")) { // A-instruction
                    addr = line.split("@")[1].trim(); // could be a label, variable or number representing an address
                }
                else { // C-instruction
                    if (line.contains("=")) { // contains dest and comp fields; may contain a jump field
                        final String[] fields = line.split("=");
                        dest = fields[0];
                        if (fields[1].contains(";")) { // checking for jump field
                            split_jump(fields[1]);
                        } else {
                            comp = fields[1].split("/")[0].trim(); // remove comments and whitespace
                        }
                    } else if (line.contains("+") || line.contains("-")) { // contains comp field; may contain a jump
                                                                           // field
                        if (line.contains(";")) { // checking for jump field
                            split_jump(line);
                        } else {
                            comp = line.split("/")[0].trim(); // remove comments and whitespace
                        }
                    } else if (line.contains(";")) { // contains jump field; may contain comp field
                        split_jump(line);
                    } else {
                        jump = line.split("/")[0].trim(); // remove comments and whitespace
                    }
                }
                return true; // successfully parsed
            }
        }

        return false; // parsing unsuccessful
    } // end parse_command

    /**
     * A helper function that checks for and handles the presence of a jump field
     * 
     * @param str A string that potentially contains a jump instruction
     */
    private void split_jump(final String str) {
        final String[] parts = str.split(";");
        comp = parts[0].trim();
        jump =  parts[1].split("/")[0].trim();
    } // end split_jump

    /**
     * Returns the dest field
     * 
     * @return The destination field of the instruction
     */
    public String dest() {
        return dest;
    } // end dest 

    /**
     * Returns the comp field
     * 
     * @return The computation field of the instruction
     */
    public String comp() {
        return comp;
    } // end comp

    /**
     * Returns the jump field
     * 
     * @return The jump field of the instruction
     */
    public String jump() {
        return jump;
    } // end jump

    /**
     * In the case of an A-instruction, returns a variable, label or address
     * 
     * @return A 16-bit address
     */
    public String addr() {
        return addr;
    } // end addr
} // end Parser class