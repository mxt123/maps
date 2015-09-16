import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


public class DrawMap extends JPanel  implements KeyListener{
		static JTextArea displayArea;
		static JFrame f;
		static int fontSize = 40;
		int mapHeight;
		int mapWidth;
		static int spacing = fontSize;
        //Location of your map, you'll need other methods to change this value to move your map around on the screen. But this will start your generation at 0,0 NOTE: Since you are dealing with ASCII you will probably be fine just using ints instead of floats.
         float mapX = 0;
         float mapY = 0 + spacing;
        static int displayWidth =640; //Screen size width.
        static int displayHeight = 480; //Screen size height.
        private byte[][] yourMap = new byte[mapHeight][mapWidth]; //Create byte array matching map height/width. (May need to be an int[][] or whatever[][] depending on what you're doing)

        private double getMapWidth() {
        	return (double) (displayWidth / spacing);
        }
        
        private double getMapHeight() {
        	return (double) (displayHeight / spacing);
        }
        
        public DrawMap(final String fileName) throws IOException{
        	mapHeight = Read2D.countLines(fileName) +1;
        	this.yourMap = Read2D.read2dArray(fileName,mapHeight);
        	mapWidth = this.yourMap[0].length;
        }
       
        public void paint(Graphics g) {
                Graphics2D g2 = (Graphics2D)g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                RenderingHints.VALUE_ANTIALIAS_ON);
                Font font = new Font(Font.MONOSPACED,Font.PLAIN, fontSize);
                g2.setFont(font);
                g2.setColor(Color.red);

                //  g2.scale(420, 320);
                //Start looping through the entire byte array.
                for (int x = 0; x < mapWidth; x++) {
                        for (int y = 0; y < mapHeight; y++) {
                        	  System.out.println(x + "," + y + "," + (char)yourMap[y][x]);
                                // Check to see if the coordinates being looked for are visible on the screen. If not, don't bother rendering them.
                                if ((x*spacing+mapX > -10) && (x*spacing+mapX < displayWidth+10) && (y*spacing+mapY > -10) && (y*spacing+mapY < displayHeight+10)){
                                       // if (!(yourMap[x][y] == 0)){ //Assuming 0 is a "blank space", but whatever == Blank space here, so you're not trying to output nothing.
                                                //However you output the text would go here. For example, if you were using the Font class in slick2d:
                                              
                                        	
                                        		final char ch = (char)yourMap[y][x];
                                                g2.drawString(String.valueOf(ch), (x*spacing)+mapX, (y*spacing)+mapY); 
                                                //So basically, however you call it, the coords would be (x*spacing)+mapX, (y*spacing)+mapY and you'll be calling character yourMap[x][y]. 
                                     //   }
                                }
                        }
                }
        }

        //YOUR CODE TO FILL SAID BYTE/INT/CHAR/WHATEVER ARRAY WITH YOUR "ASCII MAP"!//

        public static void main(String[] args) throws IOException {
                f = new JFrame("xzz");
                f.getContentPane().setBackground(Color.black);
                f.pack();
                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                DrawMap map = new DrawMap("/home/matt/workspace/drawstring/src/map.txt");
                f.getContentPane().add(map);
                
                displayArea = new JTextArea();
                displayArea.setEditable(false);
                JScrollPane scrollPane = new JScrollPane(displayArea);
                scrollPane.setPreferredSize(new Dimension(40, 40));
                f.getContentPane().add(scrollPane, BorderLayout.SOUTH);
                displayArea.setFont(new Font(Font.MONOSPACED,Font.PLAIN, 20));
                displayArea.setText("hello");
                
                f.setSize(640, 480);
                f.setPreferredSize(new Dimension(640, 480));                           
                f.setVisible(true);
                displayArea.addKeyListener(map);
        }

		@Override
		public void keyPressed(KeyEvent key) {	
			if (key.getKeyCode() == KeyEvent.VK_RIGHT) {
				if ( Math.abs(this.getMapWidth()) > Math.abs(mapX/spacing)) {
					mapX -= spacing;
				}
			}
			if (key.getKeyCode() == KeyEvent.VK_LEFT) {
				if (Math.round(mapX) <=-spacing ) {
					mapX += spacing;
				}
			}
			if (key.getKeyCode() == KeyEvent.VK_DOWN) {
				mapY -= spacing;
			}
			if (key.getKeyCode() == KeyEvent.VK_UP) {
				if (Math.round(mapY) <=0 ) {
					mapY += spacing;
				}
			}
			if (key.getKeyCode() == KeyEvent.VK_1) {
				fontSize += 10;
				spacing +=10;
			}
			if (key.getKeyCode() == KeyEvent.VK_2) {
				fontSize -= 10;
				spacing -=10;
			}
			f.repaint();
			displayArea.setText("width:" + this.getMapWidth() + "-X:" +String.valueOf(mapX/spacing) + "-Y:" + String.valueOf(mapY/spacing));
		}

		@Override
		public void keyReleased(KeyEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyTyped(KeyEvent key) {
			//displayArea.setText(String.valueOf(key.getKeyChar()));
			
		}

}
