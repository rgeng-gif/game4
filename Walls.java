package game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Random;

import game.tank.Bullet;

public class Walls {
	private int map;
	ArrayList<Polygon> walls;
	public Walls() {
		walls = new ArrayList<Polygon>();
		//creates outer border which is the same between all maps, repeated values exist to make the program draw the shape properly
		walls.add(new Polygon(new Point[] {new Point(25,25), new Point(25, 575), new Point(775,575), new Point(775, 25), new Point(25,25),
					new Point(0,0), new Point(0,600), new Point(800,600), new Point(800,0), new Point(0,0)}, new Point(0,0), 0));
		Random n = new Random();
		map = n.nextInt(0,4);
		/*if(map == 0) walls1();
		else if(map == 1) walls2();
		else if(map == 2) walls3();
		else if(map == 3) walls4();*/
		walls1();
	}
	 public void draw(Graphics2D g) {
		 for(int j = 0; j < walls.size(); j++) {
			 Point[] polygon = walls.get(j).getPoints();
			 int[] xs = new int[polygon.length];
			 int[] ys = new int[polygon.length];
			 for (int i = 0; i < polygon.length; i++) {
				 xs[i] = (int) polygon[i].x;
				 ys[i] = (int) polygon[i].y;
			 }
			 g.setColor(Color.black);
			 g.fillPolygon(xs, ys, polygon.length);
		 }  
	        
	 }
	 //returns true if movement would collide with a wall
	 public boolean collisionCheck(tank t) {
		 Point[] p = t.getPoints();
		 for(int i = 0; i < walls.size();i++) {
			 for(int j = 0; j < t.getPoints().length; j++) {
				 if(walls.get(i).contains(p[j])) return true;
			 }
		 }
		 return false;
		 
	 }
	 public boolean collisionCheck(Bullet b) {
		 Point p = b.getPosition();
		 for(int i = 0; i < walls.size(); i++) {
			 if(walls.get(i).contains(p)) return true;
		 }
		 return false;
	 }
	 
	 public void walls1() {
		 walls.add(bs(360,260, 0));
		 walls.add(lr(210,130,0));
		 walls.add(lr(440,130,0));
		 walls.add(lr(325,450,0));
		 walls.add(sr(100,200,90));
		 walls.add(sr(650,200,90));
		 walls.add(sr(200,375,90));
		 walls.add(sr(550,375,90));
	 }
	 public void walls2() {
		 
	 }
	 public void walls3() {
		 
	 }
	 public void walls4() {
		 
	 }
	 
	//used to more easily make walls of a uniform shape
	//long rectangle
	public Polygon lr(int p1, int p2, int a) {
		return new Polygon(new Point[]{new Point(0,0), new Point(0,25), new Point(150,25), new Point(150,0)}, new Point(p1,p2), a);
	}
	//short rectangle
	public Polygon sr(int p1, int p2, int a) {
		return new Polygon(new Point[]{new Point(0,0), new Point(0,25), new Point(50,25), new Point(50,0)}, new Point(p1,p2), a);
	}
	//square
	public Polygon s(int p1, int p2, int a) {
		return new Polygon(new Point[]{new Point(0,0), new Point(0,50), new Point(50,50), new Point(50,0)}, new Point(p1,p2), a);
	}
	//big square
	public Polygon bs(int p1, int p2, int a) {
		return new Polygon(new Point[]{new Point(0,0), new Point(0,80), new Point(80,80), new Point(80,0)}, new Point(p1,p2), a);
	}
}
