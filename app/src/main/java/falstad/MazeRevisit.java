package falstad;

import generation.BSPNode;
import generation.Cells;
import generation.Order.Builder;

/**
 * Created by markxsimu on 11/29/17.
 */

public class MazeRevisit {

    public static Cells cells;
    public static int[][] dists;
    public static int skill;
    public static BSPNode root;
    public static int width;
    public static int height;
    public static int startX;
    public static int startY;
    public static int rooms;
    public static int expectedPartiters;
    public static int builder;
    public static   int driver;


    public static void setDistance(int[][] distanceSetter){
        dists = distanceSetter;
    }


    public static int[][] getDistance(){
        return dists;
    }


    public static Cells getCells(){
        return cells;
    }


    public static void setCells(Cells cellsSetter){
        cells = cellsSetter;
    }


    public static void setSkill(int skillSetter){
        skill = skillSetter;
    }


    public static int getSkill(){
        return skill;
    }

    public static int getRooms(){
        return Constants.SKILL_ROOMS[skill];
    }


    public static int getExpectedPartiters(){
        return Constants.SKILL_PARTCT[skill];
    }


    public static void setRoot(BSPNode rootSetter){
        root = rootSetter;
    }


    public static BSPNode getRoot(){
        return root;
    }


    public static void setWidth(int widthSetter){
        width = widthSetter;
    }


    public static int getWidth(){
        return width;
    }

    public static void setHeight(int heightSetter){
        height = heightSetter;
    }

    public static int getHeight(){
        return height;
    }


    public static void setStartX(int startXSetter){
        startX = startXSetter;
    }


    public static int getStartX(){
        return startX;
    }


    public static void setStartY(int startYSetter){
        startY = startYSetter;
    }


    public static int getStartY(){
        return startY;
    }


    public static void setBuilder(int build){
        builder = build;
    }


    public static int getBuilder(){
        return builder;
    }
    public static void setDriver(int drive){
         driver = drive;
    }
    public static int getDriver(){
        return driver;
    }


    public static void setRooms(int roomsSetter){
        rooms = roomsSetter;
    }


    public static void setExpectedPartiters(int partitersSetter){
        expectedPartiters = partitersSetter;
    }


}
