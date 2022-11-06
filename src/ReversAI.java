import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;

import javax.imageio.ImageIO;
import javax.swing.*;

public class ReversAI implements ActionListener{

	JFrame frame = new JFrame();
	JPanel title_panel = new JPanel();
	JPanel button_panel = new JPanel();
	JLabel textfield = new JLabel();
	JButton[] buttons = new JButton[64];
	boolean isBlack=true; //It's black's turn
	private static int grayCount=0;
	private boolean playerIsBlack;
	private int minimaxDepth;
	private boolean blackHasMoves,whiteHasMoves=true;

	ReversAI(boolean playerstarts, int minimaxDepth){
		this.playerIsBlack=playerstarts;
		this.minimaxDepth=minimaxDepth;
		System.out.println("Player is black: "+playerIsBlack + "\nDepth: " + minimaxDepth);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800,800);
		frame.getContentPane().setBackground(new Color(50,50,50));
		frame.setLayout(new BorderLayout());
		frame.setVisible(true);
		
		textfield.setBackground(new Color(25,25,25));
		textfield.setForeground(new Color(25,255,0));
		textfield.setFont(new Font("MV Boli",Font.PLAIN,75));
		textfield.setHorizontalAlignment(JLabel.CENTER);
		textfield.setText("Black's Turn");
		textfield.setOpaque(true);
		
		title_panel.setLayout(new BorderLayout());
		title_panel.setBounds(0,0,800,100);
		
		button_panel.setLayout(new GridLayout(8,8));
		
		for(int i=0;i<64;i++) {
			buttons[i] = new JButton();
			button_panel.add(buttons[i]);
			buttons[i].setFont(new Font("",Font.PLAIN,60)); //Fonts name helps to find out what disk(or not) is placed there
			buttons[i].setFocusable(false);
			buttons[i].addActionListener(this);
            buttons[i].setBackground(new Color(0,255,0));
		}
		
		title_panel.add(textfield);
		frame.add(title_panel,BorderLayout.NORTH);
		frame.add(button_panel);
		
		placeDisk(27, "W");
		placeDisk(28, "B");
		placeDisk(35, "B");
		placeDisk(36, "W");

		possibleMoves();
		//TODO: here if player choses not to play first we have to implement ai's first move
	}

	//Flips disks after player's move
    public void flipDisks(){
        
    }

	//Genetrates current player's possible moves
	public void possibleMoves(){
		//First removes previous player's possible moves
		for(int i=0;i<64;i++) {
			if(buttons[i].getFont().getName().equals("G")) {
				placeDisk(i,"");
			}
		}
		grayCount=0;

		//Then puts gray to the current player's possible moves (in case its player's turn and not ai's)
		
			for(int i=0;i<64;i++) {
				if(isBlack){ //means black is playing
					if(buttons[i].getFont().getName().equals("B")){
						//checking for RIGHT direction--------------------
						int j;
						JButton b;
						if(i!=63){
							j=1;
							b=buttons[i+j];
							while(b.getFont().getName().equals("W") && j<=8-(i%8+2)){
								j++;
								try{
									b=buttons[i+j];
								}
								catch(ArrayIndexOutOfBoundsException ex){
									System.out.println("OUT OF BOUNTS: "+ i+j);
									break;
								}
								
							}
							if(b.getFont().getName().equals("") && j!=1){
								placeDisk(i+j, "G");
							}
						}
						//checking for RIGHT direction--------------------
						//checking for LEFT direction--------------------
						if(i!=0){
							j=-1;
							b=buttons[i+j];
							while(b.getFont().getName().equals("W") && j>=(i%8)*(-1)+1){
								j--;
								try{
									b=buttons[i+j];
								}
								catch(ArrayIndexOutOfBoundsException ex){
									System.out.println("OUT OF BOUNTS: "+ i+j);
									break;
								}
								
							}
							if(b.getFont().getName().equals("") && j!=-1){
								placeDisk(i+j, "G");
							}
						}
						//checking for LEFT direction--------------------
						//checking for UP direction--------------------
						if(i-8>=0){
							j=-8;
							b=buttons[i+j];
							while(b.getFont().getName().equals("W") && i+j-8>=0){
								j-=8;
								try{
									b=buttons[i+j];
								}
								catch(ArrayIndexOutOfBoundsException ex){
									System.out.println("OUT OF BOUNTS: "+ i+j);
									break;
								}
									
							}
							if(b.getFont().getName().equals("") && j!=-8){
								placeDisk(i+j, "G");
							}
						}
						//checking for UP direction--------------------
						//checking for DOWN direction--------------------
						if(i+8<=63){
							j=8;
							b=buttons[i+j];
							while(b.getFont().getName().equals("W") && i+j+8<=63){
								j+=8;
								try{
									b=buttons[i+j];
								}
								catch(ArrayIndexOutOfBoundsException ex){
									System.out.println("OUT OF BOUNTS: "+ i+j);
									break;
								}
									
							}
							if(b.getFont().getName().equals("") && j!=8){
								placeDisk(i+j, "G");
							}
						}
						//checking for DOWN direction--------------------
						//checking for UP LEFT DIAGONAL direction--------------------
						if(i%8!=0 && i>8){ // In this case it has no up left for sure
							j=-9;
							b=buttons[i+j];
							while(b.getFont().getName().equals("W") && (i+j)%8!=0 && i+j>=8){
								j-=9;
								try{
									b=buttons[i+j];
								}
								catch(ArrayIndexOutOfBoundsException ex){
									System.out.println("OUT OF BOUNTS: "+ i+j);
									break;
								}
									
							}
							if(b.getFont().getName().equals("") && j!=-9){
								placeDisk(i+j, "G");
							}
						}
						//checking for UP LEFT DIAGONAL direction--------------------
						//checking for UP RIGHT DIAGONAL direction--------------------
						if(i%8!=7 && i>8){ // In this case it has no up right for sure
							j=-7;
							b=buttons[i+j];
							while(b.getFont().getName().equals("W") && (i+j)%8!=7 && i+j>=8){
								j-=7;
								try{
									b=buttons[i+j];
								}
								catch(ArrayIndexOutOfBoundsException ex){
									System.out.println("OUT OF BOUNTS: "+ i+j);
									break;
								}
									
							}
							if(b.getFont().getName().equals("") && j!=-7){
								placeDisk(i+j, "G");
							}
						}
						//checking for UP RIGHT DIAGONAL direction--------------------
						//checking for DOWN RIGHT DIAGONAL direction--------------------
						if(i<=54 && i%8!=7){ // In this case it has no  for sure
							j=9;
							b=buttons[i+j];
							while(b.getFont().getName().equals("W") && (i+j)<=54 && (i+j)%8!=7){
								j+=9;
								try{
									b=buttons[i+j];
								}
								catch(ArrayIndexOutOfBoundsException ex){
									System.out.println("OUT OF BOUNTS: "+ i+j);
									break;
								}
									
							}
							if(b.getFont().getName().equals("") && j!=9){
								placeDisk(i+j, "G");
							}
						}
						//checking for DOWN RIGHT DIAGONAL direction--------------------
						//checking for DOWN LEFT DIAGONAL direction--------------------
						if(i%8!=0 && i<=54){ // In this case it has no  for sure
							j=7;
							b=buttons[i+j];
							while(b.getFont().getName().equals("W") && (i+j)%8!=0 && i+j>=8 && i+j<=54){
								j+=7;
								try{
									b=buttons[i+j];
								}
								catch(ArrayIndexOutOfBoundsException ex){
									System.out.println("OUT OF BOUNTS: "+ i+j);
									break;
								}
									
							}
							if(b.getFont().getName().equals("") && j!=7){
								placeDisk(i+j, "G");
							}
						}
						//checking for DOWN LEFT DIAGONAL direction--------------------
					}
				}
				else {//means white is playing
					if(buttons[i].getFont().getName().equals("W")){
						//checking for RIGHT direction--------------------
						int j;
						JButton b;
						if(i!=63){
							j=1;
							b=buttons[i+j];
							while(b.getFont().getName().equals("B") && j<=8-(i%8+2)){
								j++;
								try{
									b=buttons[i+j];
								}
								catch(ArrayIndexOutOfBoundsException ex){
									System.out.println("OUT OF BOUNTS: "+ i+j);
									break;
								}
								
							}
							if(b.getFont().getName().equals("") && j!=1){
								placeDisk(i+j, "G");
							}
						}
						//checking for RIGHT direction--------------------
						//checking for LEFT direction--------------------
						if(i!=0){
							j=-1;
							b=buttons[i+j];
							while(b.getFont().getName().equals("B") && j>=(i%8)*(-1)+1){
								j--;
								try{
									b=buttons[i+j];
								}
								catch(ArrayIndexOutOfBoundsException ex){
									System.out.println("OUT OF BOUNTS: "+ i+j);
									break;
								}
								
							}
							if(b.getFont().getName().equals("") && j!=-1){
								placeDisk(i+j, "G");
							}
						}
						//checking for LEFT direction--------------------
						//checking for UP direction--------------------
						if(i-8>=0){
							j=-8;
							b=buttons[i+j];
							while(b.getFont().getName().equals("B") && i+j-8>=0){
								j-=8;
								try{
									b=buttons[i+j];
								}
								catch(ArrayIndexOutOfBoundsException ex){
									System.out.println("OUT OF BOUNTS: "+ i+j);
									break;
								}
									
							}
							if(b.getFont().getName().equals("") && j!=-8){
								placeDisk(i+j, "G");
							}
						}
						//checking for UP direction--------------------
						//checking for DOWN direction--------------------
						if(i+8<=63){
							j=8;
							b=buttons[i+j];
							while(b.getFont().getName().equals("B") && i+j+8<=63){
								j+=8;
								try{
									b=buttons[i+j];
								}
								catch(ArrayIndexOutOfBoundsException ex){
									System.out.println("OUT OF BOUNTS: "+ i+j);
									break;
								}
									
							}
							if(b.getFont().getName().equals("") && j!=8){
								placeDisk(i+j, "G");
							}
						}
						//checking for DOWN direction--------------------
						//checking for UP LEFT DIAGONAL direction--------------------
						if(i%8!=0 && i>8){ // In this case it has no up left for sure
							j=-9;
							b=buttons[i+j];
							while(b.getFont().getName().equals("B") && (i+j)%8!=0 && i+j>=8){
								j-=9;
								try{
									b=buttons[i+j];
								}
								catch(ArrayIndexOutOfBoundsException ex){
									System.out.println("OUT OF BOUNTS: "+ i+j);
									break;
								}
									
							}
							if(b.getFont().getName().equals("") && j!=-9){
								placeDisk(i+j, "G");
							}
						}
						//checking for UP LEFT DIAGONAL direction--------------------
						//checking for UP RIGHT DIAGONAL direction--------------------
						if(i%8!=7 && i>8){ // In this case it has no up right for sure
							j=-7;
							b=buttons[i+j];
							while(b.getFont().getName().equals("B") && (i+j)%8!=7 && i+j>=8){
								j-=7;
								try{
									b=buttons[i+j];
								}
								catch(ArrayIndexOutOfBoundsException ex){
									System.out.println("OUT OF BOUNTS: "+ i+j);
									break;
								}
									
							}
							if(b.getFont().getName().equals("") && j!=-7){
								placeDisk(i+j, "G");
							}
						}
						//checking for UP RIGHT DIAGONAL direction--------------------
						//checking for DOWN RIGHT DIAGONAL direction--------------------
						if(i<=54 && i%8!=7){ // In this case it has no  for sure
							j=9;
							b=buttons[i+j];
							while(b.getFont().getName().equals("B") && (i+j)<=54 && (i+j)%8!=7){
								j+=9;
								try{
									b=buttons[i+j];
								}
								catch(ArrayIndexOutOfBoundsException ex){
									System.out.println("OUT OF BOUNTS: "+ i+j);
									break;
								}
									
							}
							if(b.getFont().getName().equals("") && j!=9){
								placeDisk(i+j, "G");
							}
						}
						//checking for DOWN RIGHT DIAGONAL direction--------------------
						//checking for DOWN LEFT DIAGONAL direction--------------------
						if(i%8!=0 && i<=54){ // In this case it has no  for sure
							j=7;
							b=buttons[i+j];
							while(b.getFont().getName().equals("B") && (i+j)%8!=0 && i+j>=8 && i+j<=54){
								j+=7;
								try{
									b=buttons[i+j];
								}
								catch(ArrayIndexOutOfBoundsException ex){
									System.out.println("OUT OF BOUNTS: "+ i+j);
									break;
								}
									
							}
							if(b.getFont().getName().equals("") && j!=7){
								placeDisk(i+j, "G");
							}
						}
						//checking for DOWN LEFT DIAGONAL direction--------------------
				
				}
			}
		}
	}

	//Checks the board to find out if the game is over
	public void checkGame(){
		if(!blackHasMoves && !whiteHasMoves){
			endGame();
		}
	}
	public void endGame(){
		int black_disks=0,white_disks=0;
		for(int i=0;i<64;i++) {
			if(buttons[i].getFont().getName().equals("B")) {
				black_disks++;
			}else if(buttons[i].getFont().getName().equals("W")){
				white_disks++;
			}
		}
		if(black_disks>white_disks){
			textfield.setText("Black WINS \n"+ black_disks + " vs " + white_disks);
		}else if(black_disks<white_disks){
			textfield.setText("White WINS \n"+ white_disks + " vs " + black_disks);
		}else{
			textfield.setText("It's a DRAW");
		}
	}

	//Places (or removes) disk. You need to give i for buttons possition ang "B"/"W"/"G"/"" for color ("" for removing disk in case of possible moves generation)
	public void placeDisk(int i,String color){
		if(color.equals("B")){
			try {
				Image img = ImageIO.read(new FileInputStream(new File("Assets\\black_disk.png")));
				buttons[i].setIcon(new ImageIcon(img));
				buttons[i].setFont(new Font("B",Font.PLAIN,75));
			}catch(Exception ex){
				System.out.println("Asset NOT FOUND");
			}
		}
		else if(color.equals("W")){
			try {
				Image img = ImageIO.read(new FileInputStream(new File("Assets\\white_disk.png")));
				buttons[i].setIcon(new ImageIcon(img));
				buttons[i].setFont(new Font("W",Font.PLAIN,75));
			}catch(Exception ex){
				System.out.println("Asset NOT FOUND");
			}
		}
		else if(color.equals("G")){
			try {
				Image img = ImageIO.read(new FileInputStream(new File("Assets\\gray_disk.png")));
				buttons[i].setIcon(new ImageIcon(img));
				buttons[i].setFont(new Font("G",Font.PLAIN,75));
				grayCount++;
			}catch(Exception ex){
				System.out.println("Asset NOT FOUND");
			}
		}
		else{
			buttons[i].setIcon(null);
			buttons[i].setFont(new Font("",Font.PLAIN,60));
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		for(int i=0;i<64;i++) {
			if(e.getSource()==buttons[i]) {
				if(isBlack) {
					if(buttons[i].getFont().getName().equals("G")) {
						placeDisk(i,"B");
						isBlack=false;
						textfield.setText("White's turn");
						flipDisks();
					}
				}
				else {
					if(buttons[i].getFont().getName().equals("G")) {
						placeDisk(i,"W");
						isBlack=true;
						textfield.setText("Black's turn");
						flipDisks();
					}
				}
			}			
		}
		possibleMoves();
		System.out.println(grayCount);
		if(isBlack && grayCount==0){
			blackHasMoves=false;
			isBlack=!isBlack;
			textfield.setText("White's turn");
			possibleMoves();
			if(grayCount!=0){
				whiteHasMoves=true;
			}else{
				whiteHasMoves=false;
				checkGame();
			} 
		}else if(!isBlack && grayCount==0){
			whiteHasMoves=false;
			isBlack=!isBlack;
			textfield.setText("Black's turn");
			possibleMoves();
			if(grayCount!=0){
				blackHasMoves=true;
			}else{
				blackHasMoves=false;
				checkGame();
			}
		}else if(isBlack && grayCount!=0){
			blackHasMoves=true;
		}else if(!isBlack && grayCount!=0){
			whiteHasMoves=true;
		}
	}
}
