/**
 * Code.Java
 * 
 * @author Franklin Ogidi
 * 
 * @description The Code class translates a given field into its corresponding value
 * 
 * @version 1.0
 * 
 */

 import java.util.*;

 public class Code {
	 // hash tables to store the specifications for the destination, jump and computation bits
     private static Hashtable<String, String> dest_table = new Hashtable<String, String>(8);
     private static Hashtable<String, String> jump_table = new Hashtable<String, String>(8);
     private static Hashtable<String, String> comp_table = new Hashtable<String, String>(28);

     /**
      * Stores the destination codes
      */
     private static void init_dest_table() {
        dest_table.put("null", "000");
        dest_table.put("M", "001");	// Memory (RAM)
        dest_table.put("D", "010");	// D-register
        dest_table.put("MD", "011");
        dest_table.put("A", "100");	// A-register
        dest_table.put("AM", "101");
        dest_table.put("AD", "110");
        dest_table.put("AMD", "111");
     } // end init_dest_table

     /**
      * Stores the jump codes
      */
     private static void init_jump_table() {
        jump_table.put("null", "000");	// no jump
        jump_table.put("JGT", "001");	// jump if greater than zero
        jump_table.put("JEQ", "010");	// jump if equal to zero
        jump_table.put("JGE", "011");	// jump is greater than or equal to zero
        jump_table.put("JLT", "100");	// jump if less than zero
        jump_table.put("JNE", "101");	// jump if not equal to zero
        jump_table.put("JLE", "110");	// jump if less than or equal to zero
        jump_table.put("JMP", "111");	// unconditional jump
     } // end init_jump_table

     /**
      * Stores the computation codes
      */
     private static void init_comp_table() {
        comp_table.put("0", "0101010");
        comp_table.put("1", "0111111");
        comp_table.put("-1", "0111010");
        comp_table.put("D", "0001100");
        comp_table.put("A", "0110000");
        comp_table.put("!D", "0001101");
        comp_table.put("!A", "0110001");
        comp_table.put("-D", "0001111");
        comp_table.put("-A", "0110011");
        comp_table.put("D+1", "0011111");
        comp_table.put("A+1", "0110111");
        comp_table.put("D-1", "0001110");
        comp_table.put("A-1", "0110010");
        comp_table.put("D+A", "0000010");
        comp_table.put("D-A", "0010011");
        comp_table.put("A-D", "0000111");
        comp_table.put("D&A", "0000000");
        comp_table.put("D|A", "0010101");
        comp_table.put("M", "1110000");
        comp_table.put("!M", "1110001");
        comp_table.put("-M", "1110011");
        comp_table.put("M+1", "1110111");
        comp_table.put("M-1", "1110010");
        comp_table.put("D+M", "1000010");
        comp_table.put("D-M", "1010011");
        comp_table.put("M-D", "1000111");
        comp_table.put("D&M", "1000000");
        comp_table.put("D|M", "1010101");
     } // end init_comp_table

     /**
      * Retrieves the binary code representing the computation bits given by the symbolic key
      *
	  * @param key The symbolic representation of the computation bits to be retrieved
	  *
      * @return A 7-bit binary String representing the computation instruction
      */
    public static String get_comp_code(final String key) {
		init_comp_table();
		return comp_table.get(key);
	} // end get_comp_code

  /**
   * Retrieves the binary code representing the destination instruction given by the symbolic key
   *
   * @param key The symbolic representation of the destination bits to be retrieved
   * 
   * @return A 3-bit binary String representing the destination instruction
   */
  public static String get_dest_code(final String key) {
	  init_dest_table();
	  return dest_table.get(key);
	} // end get_dest_code

  /**
   * Retrieves the binary code representing the jump instruction given by the
   * symbolic key
   *
   * @param key The symbolic representation of the jump bits to be retrieved
   * 
   * @return A 3-bit binary String representing the jump instruction
   */
  public static String get_jump_code(final String key) {
		init_jump_table();
        return jump_table.get(key);
    }// end get_jump_code
     
 }// end Code class
