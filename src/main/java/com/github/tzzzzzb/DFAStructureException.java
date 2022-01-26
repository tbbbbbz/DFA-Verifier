package com.github.tzzzzzb;

public class DFAStructureException extends RuntimeException{
    DFAStructureException(String msg) {
        super("DFAStructureException: " + msg);
    }
}
