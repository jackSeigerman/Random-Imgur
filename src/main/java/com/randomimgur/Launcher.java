package com.randomimgur;

/**
 * Launcher class to work around JavaFX module system restrictions
 * when creating a fat JAR with the shade plugin.
 */
public class Launcher {
    public static void main(String[] args) {
        Main.main(args);
    }
}
