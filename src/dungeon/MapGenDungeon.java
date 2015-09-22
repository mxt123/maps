package dungeon;

import java.util.ArrayList;
import java.util.List;

import util.Point;

// adapted from http://www.roguebasin.com/index.php?title=Complete_Roguelike_Tutorial,_using_python%2Blibtcod,_part_3
public class MapGenDungeon {
	
	static char WALL = '#';
	static char SPACE = '_';
	static int ROOM_MAX_SIZE = 10;
	static int ROOM_MIN_SIZE = 6;
	static int MAX_ROOMS = 150;
	
    private static int getRandom(int min, int max) {
    	return min + (int)(Math.random() * ((max - min) + 1));	
    }

	public static void createRoom(byte[][] map, Rect room) {
	    for (int x = room.x;x < room.x + room.w; x++) {
	        for (int y = room.y; y < room.y + room.h ; y++) {
	            map[y][x] = (byte) SPACE;
	        }
	    }
	}
	
	public static void createHTunnel(byte[][] map, int x1, int x2, int y)
	{
		for (int x = x1; x < x2; x++){
			 map[y][x] = (byte) SPACE;
		}
	}
	
	public static void createVTunnel(byte[][] map, int y1, int y2, int x)
	{
		for (int y = y1; y < y2; y++){
			 map[y][x] = (byte) SPACE;
		}
	}
	
	public static Point center(Rect rect) {
		int centerX = (rect.x + rect.x + rect.w) /2;
		int centerY = (rect.y + rect.y + rect.h) /2;
		return new Point(centerX,centerY);
	}
	
	public static boolean intersect(Rect rect1, Rect rect2){
		 return (rect1.x <= rect2.x + rect2.w && 
				 rect1.x+rect1.w >= rect2.x &&
	             rect1.y <= rect2.y+rect2.h && 
	             rect1.y+rect1.h  >= rect2.y);
	}	
 
	public static byte[][] newFullMap(byte[][] map) {
		for (int i =0;i < map.length; i++){
    		for (int j =0; j < map[0].length; j ++) {
    			map[i][j] = (byte) WALL;
    		}
    	}
	
		List<Rect> rooms = new ArrayList<Rect>();
		
		for (int r= 0; r< MAX_ROOMS;r++){
			int w = getRandom(ROOM_MIN_SIZE,ROOM_MAX_SIZE);
			int h = getRandom(ROOM_MIN_SIZE,ROOM_MAX_SIZE);
			int x = getRandom(0, map[0].length - w);
			int y = getRandom(0, map.length - h);
			Rect room = new Rect(x,y,w,h);
			boolean fail = false;
			for (Rect test : rooms) {
				if (intersect(room,test)) {
					fail = true;
					break;
				}
			}
			if (!fail) {
				rooms.add(room);
				createRoom(map,room);
			}
		}
		
		Point start = center(rooms.get(0));
		map[start.y][start.x] = (byte) '@';
				   
		return map;
	}
	
	            
}
