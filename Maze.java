/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample;

/**
 *
 * @author Raji
 */
import java.io.*;
import java.util.*;
import java.awt.*;
import javax.swing.*;

public class Maze {
    static int w, h;
    static DisjointSet setMaze;
    static int[][] maze;
    static int[][] index;
    static Vertex[][] vMaze;
    
    static class CreateMaze extends JPanel{
	private int[][] x;
	private int mSize;
        public CreateMaze(int[][] array,int boxsize){
            x=array;
            this.mSize=boxsize;
            setPreferredSize(new Dimension(300, 300));
	}
        public void paintComponent(Graphics g){
            int startx=5;
            g.setColor(Color.RED);
            int curry=startx;
            for(int i=0;i<x.length;i++){
		int currx=startx;
		for(int j=0;j<x[i].length;j++){
                    currx+=mSize;
                    if((x[i][j]==1 || x[i][j]==3) && j!=x[i].length-1)
			g.drawLine(currx,curry,currx,curry+mSize);
                }
                curry+=mSize;
            }
            g.drawLine(startx+mSize,startx,mSize*x[0].length+5,startx);
            g.drawLine(startx,mSize*x.length+5,mSize*x[0].length+5,mSize*x.length+5);
            g.drawLine(startx,startx,startx,mSize*x.length+5);
            g.drawLine(mSize*x[0].length+5,startx,mSize*x[0].length+5,mSize*x.length+5-mSize);
            curry=startx;
            for(int i=0;i<x.length;i++){
		int currx=startx;
		curry+=mSize;
                for(int j=0;j<x[0].length;j++){
                    if((x[i][j]==2) || (x[i][j]==3))
                        g.drawLine(currx,curry,currx+mSize,curry);
			currx+=mSize;
		}
            }
	}
    }
    
    public static int getWidth(int ci){
        for(int i=0;i<index.length;i++){
            for(int j=0;j<index[i].length;j++){
		if(index[i][j]==ci)
                    return j;
            }
        }
        return -1;
    }

    public static int getHeight(int ci){
	for(int i=0;i<index.length;i++){
            for(int j=0;j<index[i].length;j++){
                if(index[i][j]==ci)
                    return i;
            }
	}
	return -1;
    }
 
    public static void genMaze(){
	boolean flag=false;
	while (!flag){
            int r1=(int)(Math.random()*10000)%(h*w);
            int rt1H=getHeight(r1);
            int rt1W=getWidth(r1);
            while(adjVal(r1)<0){
		r1=(int)(Math.random()*10000)%(h*w);
            }
            int r2=adjVal(r1);
            int rt2H=getHeight(r2);
            int rt2W=getWidth(r2);
            int minW=getWidth(Math.min(r1,r2));
            int minH=getHeight(Math.min(r1,r2));
            if(!(setMaze.find(r1)==setMaze.find(r2))){
                setMaze.union(setMaze.find(r1),setMaze.find(r2));
		if(rt1H==rt2H){
                    if(maze[rt1H][minW]==3)
			maze[rt1H][minW]=2;
                    else
			maze[rt1H][minW]=0;
                }else if(rt1W==rt2W){
                    if(maze[minH][rt1W]==3)
                        maze[minH][rt1W]=1;
                    else
			maze[minH][rt1W]=0;
		}
            }
            for(int i=0;i<h*w;i++){
		if(setMaze.s[i]==-1*(h*w))
                    flag=true;	
            }
	}
    }

    public static void createVertice(){
	for(int i=0;i<h;i++){
            for(int j=0;j<w;j++){
		vMaze[i][j]=new Vertex();
		vMaze[i][j].index=index[i][j];
            }
	}
	for(int i=0;i<h;i++){
            for(int j=0;j<w;j++){
		if(i!=0){
                    if(maze[i-1][j]==0||maze[i-1][j]==1){
			vMaze[i][j].makeAdj(vMaze[i-1][j]);
                    }
		}
		if(j!=0){
                    if(maze[i][j-1]==0 || maze[i][j-1]==2){
			vMaze[i][j].makeAdj(vMaze[i][j-1]);
                    }
		}
		if(i!=(h-1)){
                    if(maze[i][j]==0||maze[i][j]==1){
			vMaze[i][j].makeAdj(vMaze[i+1][j]);
                    }
		}
		if(j!=(w-1)){
                    if(maze[i][j]==0 ||maze[i][j]==2){
			vMaze[i][j].makeAdj(vMaze[i][j+1]);
                    }
		}
            }
	}
    }

    public static boolean checkAdj(int x,int y){
	if(getHeight(x)==getHeight(y)){
            if(Math.abs(getWidth(x)-getWidth(y))==1)
                return true;
        }
        if(getWidth(x)==getWidth(y)){
            if(Math.abs(getHeight(x)-getHeight(y))==1)
		return true;
        }
	return false;
    }

    public static int adjVal(int c) {
	int cW = getWidth(c);
	int cH = getHeight(c);
	int pCount = 0;
	int[] t = new int[4];
	for (int i = 0; i < t.length; i++)
            t[i] = -1;
        if (cH != 0) {
            if (maze[cH - 1][cW] != 0 || maze[cH - 1][cW] != 1) {
                t[pCount] = index[cH - 1][cW];
                pCount++;
            }
        }
        if (cH != index.length - 1) {
            if (maze[cH][cW] != 0 || maze[cH][cW] != 1) {
                t[pCount] = index[cH + 1][cW];
                pCount++;
            }
        }
        if (cW != 0) {
            if (maze[cH][cW - 1] != 0 || maze[cH][cW] != 2) {
                t[pCount] = index[cH][cW - 1];
                pCount++;
            }
        }
            if (cW != index[0].length - 1) {
		if (maze[cH][cW] != 0 || maze[cH][cW] != 2) {
                    t[pCount] = index[cH][cW + 1];
                    pCount++;
		}
            }
        if (t[0] < 0 && t[1] < 0 && t[2] < 0 && t[3] < 0)
            return -1;
        int tempDex = (int) (Math.random() * 10) % pCount;
        while (t[tempDex] < 0) {
            tempDex = (int) (Math.random() * 10) % pCount;
        }
        return t[tempDex];
    }

    public static boolean diffSets(int x, int y) {
	if (setMaze.find(x) == setMaze.find(y)) {
            return false;
	}
	return true;
    }
     	
    public static void main(String[] args) throws FileNotFoundException{
        Scanner s=new Scanner(System.in);
        System.out.print("Enter the height of the maze: ");
        h=s.nextInt();
        System.out.print("Enter the width of the maze: ");
        w=s.nextInt();  
        if(h<=1 && w<=1){
            System.out.println("Invalid dimension");
            return ;
        }
        s.close();
        maze=new int[h][w];
        index=new int[h][w];
        int t=0;
        for(int i=0;i<h;i++){
            for(int j=0;j<w;j++){
                index[i][j]=t;
                maze[i][j]=3;
                t++;
            }
        }
        setMaze=new DisjointSet(h*w);
        genMaze();
        vMaze=new Vertex[h][w];
        createVertice();
        JFrame f=new JFrame();
        f.setTitle("Maze generated");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.getContentPane().add(new CreateMaze(maze, 10));
        f.pack();
        f.setBackground(Color.BLACK);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }
}

class Vertex{
    public Vertex path;
    public int index;
    public Double dist;
    public boolean visited;
    public LinkedList<Vertex> a;   
    public Vertex(){
        a=new LinkedList();
        dist=Double.POSITIVE_INFINITY;
        visited=false;
    }
    public void makeAdj(Vertex x) {
        a.add(x);
    }
}
