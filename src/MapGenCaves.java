
public class MapGenCaves {
	
	static char TREE = 'T';
	static char WALL = '#';
	static char SPACE = '_';
	static double CHANCETOSTARTALIVE = 0.45;
	static double CHANCETREE = 0.3;
	
	public static int countAliveNeighbours(byte[][] grid,int x, int y)
	{
		int count = 0;
		for (int i= -1;i<2;i++) {
		    for (int j= -1;j<2;j++){
		      int checkX = x+j;
		      int checkY = y+i;  
		      if (checkX == x && checkY ==y) {
		        // do nothing
		      } else if (checkY <= 0 || checkX <= 0 || checkY >= grid.length || checkX >= grid[0].length) {
		          count +=1;
		      }
		      else if (((char)grid[checkY][checkX]) == WALL) {
		         count += 1;
		      } 
		    }
		}
		return count;
	}
	
	public static byte[][] evolveGrid(byte[][] grid) {
		byte[][] newgrid = grid;
		  for (int y = 0; y < grid.length ; y++) {
		    for (int x = 0; x < grid[0].length ; x++) {    
		      int count = countAliveNeighbours(grid,x,y);
		     // if (!((char)grid[y][x]== TREE) ) {
			      boolean isAlive = ((char)grid[y][x]) == WALL;
			      if (isAlive) {
			        if ( count < 3) {
			          newgrid[y][x] = (byte) SPACE;
			        } 
			        else {
			          newgrid[y][x] = (byte) WALL;
			        } 
			      }
			       else if (!isAlive) {
			        if (count > 4 ){
			          newgrid[y][x] = (byte) WALL;
			        } else {
			          newgrid[y][x] = (byte) SPACE;
			        }
			      }
		      	}
		    //}
		  }
		  grid = newgrid;
		  newgrid=null;
		  return grid;
	}

	public static byte[][] generateGrid(byte[][] grid){  
	  for (int y = 0; y < grid.length ; y++) {
	    for (int x = 0; x < grid[0].length ; x++) {    
	        if (  Math.random() < CHANCETOSTARTALIVE) {
	          grid[y][x] = (byte) WALL;
	       // } else if(Math.random() < CHANCETREE) {
	        //	grid[y][x] = (byte) TREE;
	        }
	        else {
	          grid[y][x] = (byte) SPACE;  
	        }
	      }
	    }
	return grid;
	}
	
	public static byte[][] newBlankMap(int height, int width) {
		byte[][] map = new byte[height][width];
		for (int i =0;i < height; i++){
    		for (int j =0; j < width; j ++) {
    			map[i][j] = (byte) MapGenCaves.SPACE;
    		}
    	}
		return map;
	}
	
}