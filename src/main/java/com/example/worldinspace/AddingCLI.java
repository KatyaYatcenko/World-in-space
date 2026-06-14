package com.example.worldinspace;

import java.util.Scanner;

public class AddingCLI {

    private final World world;

    public AddingCLI(World world) { this.world = world; }

    public void processCommandLine() {
        try (Scanner in = new Scanner(System.in)) {
            while (true) {
                System.out.println("COMMAND>");
                String input = in.nextLine().trim().toLowerCase();
                String[] cmds = input.split(" ");

                if (cmds != null) {
                    if (cmds.length == 1) {
                        if (cmds[0].equals("sortname")) {
                            world.CLIsortName();
                        } else if (cmds[0].equals("sortenergy")) {
                            world.CLIsortEnergy();
                        } else if (cmds[0].equals("sortcoord")) {
                            world.CLIsortCoord();
                        } else if (cmds[0].equals("count")) {
                            world.CLIcount();
                        } else if (cmds[0].equals("countact")) {
                            world.CLIcountAct();
                        } else if (cmds[0].equals("countnonact")) {
                            world.CLIcountNonAct();
                        } else if (cmds[0].equals("exit")) {
                            world.CLIexit();
                        }
                    } else if (cmds.length == 2) {
                        if (cmds[0].equals("find")) {
                            world.CLIfind(Integer.parseInt(cmds[1]));
                        }
                    }
                }
            }
        }
    }
}