/**
 * SymbolTable.Java
 * 
 * @author Franklin Ogidi 
 * 
 * @description Manages a table of symbols including predefined variables, user-defined variables and labels.
 * This program was written as part of the NandToTetris course: https://www.nand2tetris.org/project06
 * 
 * @version 1.0
 * 
 */

import java.util.Hashtable;

public class SymbolTable {
    private int current_register;   // stores the register address (in decimal) to which the next variable is assigned
    private final Hashtable<String, Integer> sym_table; // stores the symbol table

    // constructor
    public SymbolTable() {
        current_register = 16;
        sym_table = new Hashtable<String, Integer>(25);

        // initialize pre-defined variables
        for (int i = 0; i <= 15; i++) {
            final String key = "R" + i;
            sym_table.put(key, i);
        }

        sym_table.put("SCREEN", 16384);
        sym_table.put("KBD", 24576);
        sym_table.put("SP", 0);
        sym_table.put("LCL", 1);
        sym_table.put("ARG", 2);
        sym_table.put("THIS", 3);
        sym_table.put("THAT", 4);
    } // end constructor

    /**
     * Adds a variable defined by 'symbol' if not present in the SymbolTable. New
     * variables get the value of 'current_register' beginining from 16.
     * 
     * @param symbol The symbol to be added to the table
     * 
     * @return True if the symbol was successfully added; False otherwise
     */
    public boolean addVariable(final String symbol) {
        if (!sym_table.containsKey(symbol)) {
            sym_table.put(symbol, current_register);
            current_register++;
            return true;
        }

        return false;
    } // end addVariable

    /**
     * An interface for putting a key-value pair into the SymbolTable
     * 
     * @param symbol The symbol to put into the table
     * 
     * @param value  The value associated with the symbol
     */
    public void put(final String symbol, final int value) {
        sym_table.put(symbol, value);
    } // end put

    /**
     * An interface method for checking if the SymbolTable contains the given key
     * 
     * @param symbol The symbol the check for in the SymbolTable
     * 
     * @return True if the SymbolTable contains the symbol; False otherwise
     */
    public boolean contains(final String symbol) {
        return sym_table.containsKey(symbol);
    } // end contains

    /**
     * An inteface method for retrieving the value of a given symbol
     * 
     * @param symbol The symbol for which the value is to be retrieved
     * 
     * @return The value of the symbol if in the SymbolTable; null otherwise
     */
    public int get(final String symbol) {
        return sym_table.get(symbol);
    } // end get
} // end SymbolTable class