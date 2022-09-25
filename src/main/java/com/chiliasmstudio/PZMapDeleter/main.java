/*
 *     PZMapDeleter - delete map/chunkdata/zpop with command
 *     Copyright (C) 2022-2022 chiliasmstudio
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.chiliasmstudio.PZMapDeleter;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

public class main {
    public static ArrayList<String> ModeList = new ArrayList<String>();
    public static File SavePath = null;
    public static String Mode = "";
    public static int pos1_x = -1;
    public static int pos1_y = -1;
    public static int pos2_x = -1;
    public static int pos2_y = -1;

    public static void main(String[] args) throws Exception {
        ModeList.add("map");
        ModeList.add("chunkdata");
        ModeList.add("zpop");
        try {
            for (String arg : args) {
                if (arg.startsWith("--Pos1")) {
                    String xy = arg.split("=")[1];
                    pos1_x = Integer.parseInt(xy.split(",")[0]);
                    pos1_y = Integer.parseInt(xy.split(",")[1]);
                }
                if (arg.startsWith("--Pos2")) {
                    String xy = arg.split("=")[1];
                    pos2_x = Integer.parseInt(xy.split(",")[0]);
                    pos2_y = Integer.parseInt(xy.split(",")[1]);
                }
                if (arg.startsWith("--SavePath")) {
                    SavePath = new File(arg.split("=")[1]);
                }
                if(arg.startsWith("--Mode")){
                    Mode = arg.split("=")[1].toLowerCase();
                    if(!ModeList.contains(Mode)){
                        System.out.println("Mode input error!");
                        return;
                    }
                }
                //end arg if
            }
            if (pos1_x >= 0 && pos1_y >= 0 && pos2_x >= 0 && pos2_y >= 0 && SavePath != null) {
                int temp = 0;
                if (pos1_x > pos2_x) {
                    temp = pos1_x;
                    pos1_x = pos2_x;
                    pos2_x = temp;
                }
                if (pos1_y > pos2_y) {
                    temp = pos1_y;
                    pos1_y = pos2_y;
                    pos2_y = temp;
                }
                System.out.println("Start del...");
            } else {
                System.out.println("Args not set!");
                return;
            }

            switch (Mode){//Mod select
                case "map"->{
                    System.out.println("DelMode: Map");
                    pos1_x = pos1_x/10;
                    pos1_y = pos1_y/10;
                    pos2_x = pos2_x/10;
                    pos2_y = pos2_y/10;
                    System.out.println("pos1: " + pos1_x + "," + pos1_y);
                    System.out.println("pos2: " + pos2_x + "," + pos2_y);
                    DelMap();
                }
                case "chunkdata" ->{
                    System.out.println("DelMode: ChunkData");
                    System.out.println("pos1: " + pos1_x + "," + pos1_y);
                    System.out.println("pos2: " + pos2_x + "," + pos2_y);
                    DelChunkData();
                }
                case "zpop" ->{
                    System.out.println("DelMode: Zpop");
                    System.out.println("pos1: " + pos1_x + "," + pos1_y);
                    System.out.println("pos2: " + pos2_x + "," + pos2_y);
                    DelZpop();
                }
                default-> System.out.println("Mode input error!");
            }

        } catch (NullPointerException e) {
            System.out.println("Fail to read Path");
            e.printStackTrace();
        }
    }

    public static boolean isInRange(int x, int y) {
        return pos1_x <= x && x <= pos2_x && pos1_y <= y && y <= pos2_y;
    }

    public static void DelMap(){
        for (File stuff : Objects.requireNonNull(SavePath.listFiles())) {
            if (stuff.getName().startsWith("map_") && stuff.getName().endsWith(".bin")) {
                String mpaXY = stuff.getName();
                mpaXY = mpaXY.substring(4);
                mpaXY = mpaXY.substring(0, mpaXY.length() - 4);
                //System.out.println(mpaXY);
                if (StringUtils.isNumeric(mpaXY.split("_")[0]) && StringUtils.isNumeric(mpaXY.split("_")[1])) {
                    int x = Integer.parseInt(mpaXY.split("_")[0]);
                    int y = Integer.parseInt(mpaXY.split("_")[1]);
                    if (isInRange(x,y)) {
                        stuff.delete();
                        System.out.println(stuff.getName() + " Deleted");
                    }
                }
            }
        }
    }

    public static void DelChunkData(){
        for (File stuff : Objects.requireNonNull(SavePath.listFiles())) {
            if (stuff.getName().startsWith("chunkdata_") && stuff.getName().endsWith(".bin")) {
                String mpaXY = stuff.getName();
                mpaXY = mpaXY.substring(10);
                mpaXY = mpaXY.substring(0, mpaXY.length() - 4);
                //System.out.println(mpaXY);
                if (StringUtils.isNumeric(mpaXY.split("_")[0]) && StringUtils.isNumeric(mpaXY.split("_")[1])) {
                    int x = Integer.parseInt(mpaXY.split("_")[0]);
                    int y = Integer.parseInt(mpaXY.split("_")[1]);
                    if (isInRange(x,y)) {
                        stuff.delete();
                        System.out.println(stuff.getName() + " Deleted");
                    }
                }
            }
        }
    }

    public static void DelZpop(){
        for (File stuff : Objects.requireNonNull(SavePath.listFiles())) {
            if (stuff.getName().startsWith("zpop_") && stuff.getName().endsWith(".bin")) {
                String mpaXY = stuff.getName();
                mpaXY = mpaXY.substring(5);
                mpaXY = mpaXY.substring(0, mpaXY.length() - 4);
                //System.out.println(mpaXY);
                if (StringUtils.isNumeric(mpaXY.split("_")[0]) && StringUtils.isNumeric(mpaXY.split("_")[1])) {
                    int x = Integer.parseInt(mpaXY.split("_")[0]);
                    int y = Integer.parseInt(mpaXY.split("_")[1]);
                    if (isInRange(x,y)) {
                        stuff.delete();
                        System.out.println(stuff.getName() + " Deleted");
                    }
                }
            }
        }
    }

    private static void DelFile(File stuff) {

    }

}
