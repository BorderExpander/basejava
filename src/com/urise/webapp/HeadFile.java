package com.urise.webapp;

import java.io.File;
import java.util.Objects;

public class HeadFile {
    public static void main(String[] args) {
        File file = new File(".//");
        printDir(file, 0);
    }

    public static void printDir(File file, int nesting) {

        System.out.println("⃒" + " ".repeat(nesting * 3) + "⃒" + "—".repeat(3) + file.getName());
        for (File f : Objects.requireNonNull(file.listFiles())) {
            if (f.isDirectory()) {
                printDir(f, nesting + 1);
            } else {
                System.out.println("⃒" + " ".repeat((nesting + 1) * 3) + "⃒" + "—".repeat(3) + f.getName());
            }
        }
    }
}
