/*
* Name: Justen Minamitani
* Homework: #2
* Due: 11/29/17
* Course: CS-241-02-f17
*Description:
*This code was to read in a file from terminal that holds a maze in text file.
*This code then decodes the start and finish from the file and determine the shortest path from start to end.
*
* */

package Maze;
import java.util.*;
import java.io.File;
import java.io.IOException;


public class Maze {
	String [][] arr;
    int vertical, horizontal;
	private static String filePath;
	Scanner input = new Scanner(System.in);
	
    //constructor
    public Maze() throws IOException{
		System.out.println("Please input file name: ");
		filePath  = input.next();
		createMatrix(filePath);
	}
    public Maze(String fn) throws IOException{
    		createMatrix(fn);
    }
    //////////////////////////////////////////////////////////////////////
	public void createMatrix(String fileName){
        String line;
        ArrayList<String> lines = new ArrayList<String>();
        try {
            File file = new File(fileName); //reads in file
            input = new Scanner(file); //put file string into one string
        }catch(Exception e){
        		System.out.println("Unable to open file '" + fileName + "'");
            System.out.println("Please Input another file name: ");
            filePath = input.next();
            createMatrix(filePath);
        }
        
        while(input.hasNext()){
            line = input.nextLine();
			lines.add(line);
        }
        input.close();
        
        vertical = lines.size();
        horizontal = lines.get(0).length();
        
        arr = new String[vertical][horizontal];
        for(int ArrIndex = 0; ArrIndex < vertical; ArrIndex++){
            for(int i = 0; i< horizontal; i++){
                arr[ArrIndex][i] = lines.get(ArrIndex).substring(i,i+1);
            }
        }
        printArr();
        
        List<Vertex> shortestPath = findShortestPath(arr);
        int count = 0;
        for (Vertex f : shortestPath) {
        		arr[f.x][f.y] = ".";
        		count++;
            System.out.println("(" + f.x + ";" + f.y + ")");
        }
        printArr();
        System.out.println("Path Distance: " + count);
        
	}
	
	public void printArr(){////////////////////////////////////////
        for(int i=0; i < vertical; i++ ){
            for(int j = 0; j < horizontal; j++){
                System.out.print(arr[i][j]);
            }
            System.out.println();
        }
	}/////////////////////////////
	
	
    public static List<Vertex> findShortestPath(String[][] area) {//////////////////////////
        Vertex[][] vertex = new Vertex[area.length][area[0].length];
        for (int i = 0; i < vertex.length; i++) {
            for (int j = 0; j < vertex[0].length; j++) {
                if (!area[i][j].equals("X")) 
                    vertex[i][j] = new Vertex(i, j, Integer.MAX_VALUE, null);
            }
        }

        LinkedList<Vertex> q = new LinkedList<>();

        Vertex start = null;
        for(int i=0; i < vertex.length;i++) {
        		for(int j = 0; j < vertex[i].length;j++) {
        			if(area[i][j].equals("S"))
            			start = vertex[i][j];
        		}
        }
        start.dist = 0;
        q.add(start);

        Vertex dest = null;
        Vertex cur;
        while ((cur = q.poll()) != null) {
            if (area[cur.x][cur.y].equals("F")) {
                dest = cur;
            }
            visitNeighbour(vertex, q, cur.x - 1, cur.y, cur);
            visitNeighbour(vertex, q, cur.x + 1, cur.y, cur);
            visitNeighbour(vertex, q, cur.x, cur.y - 1, cur);
            visitNeighbour(vertex, q, cur.x, cur.y + 1, cur);
        }

        if (dest == null) {
            return Collections.emptyList();
        } else {
            LinkedList<Vertex> path = new LinkedList<>();
            cur = dest;
            do {
                path.addFirst(cur);
            } while ((cur = cur.prev) != null);

            return path;
        }
    }/////////////////////////////////////////////////////////////

    private static void visitNeighbour(Vertex[][] vertex, LinkedList<Vertex> q, int x, int y, Vertex parent) {
        int dist = parent.dist + 1;
        if (x < 0 || x >= vertex.length || y < 0 || y >= vertex[0].length || vertex[x][y] == null) {
            return;
        }
        Vertex cur = vertex[x][y];
        if (dist < cur.dist) {
            cur.dist = dist;
            cur.prev = parent;
            q.add(cur);
        }
    }/////////////////////////////////////////////////////////////

    private static class Vertex implements Comparable<Vertex> {
        public int x;
        public int y;
        public int dist;
        public Vertex prev;

        private Vertex(int x, int y, int dist, Vertex prev) {
            this.x = x;
            this.y = y;
            this.dist = dist;
            this.prev = prev;
        }

        @Override
        public int compareTo(Vertex o) {
            return dist - o.dist;
        }
    }
/////////-------------------------------------------------------------------------------------
    public static void main(String[] args) throws IOException {
    		Maze mz;
    		try{
    			mz = new Maze(args[0]);
    		}catch(ArrayIndexOutOfBoundsException ex) {
    			mz = new Maze();
    		}
    }
}




	 