import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;

public class Board {
    boolean isBlack; // It's black's turn
	public static boolean blackHasMoves, whiteHasMoves;
	JButton[] buttons;
	private static int grayCount;
	private boolean playerIsBlack;
	private Move lastMove;

	public Move getLastMove(){
		return this.lastMove;
	}

	public void setLastMove(Move lastMove){
		this.lastMove=lastMove;
	}

	public int getGrayCount(){
		return this.grayCount;
	}
	
	public void setGrayCount(int grayCount){
		this.grayCount=grayCount;
	}

	public boolean getIsBlack(){
	return this.isBlack;
	}

	public void setIsBlack(boolean isBlack){
	this.isBlack=isBlack;
	}

	public boolean getBlackHasMoves(){
		return this.blackHasMoves;
	}
	
	public void setBlackHasMoves(boolean blackHasMoves){
		this.blackHasMoves=blackHasMoves;
	}

	public boolean getWhiteHasMoves(){
		return this.whiteHasMoves;
	}
	
	public void setWhiteHasMoves(boolean whiteHasMoves){
		this.whiteHasMoves=whiteHasMoves;
	}

	public JButton[] getButtons() {
		return this.buttons;
	}

	public void setButtons(JButton[] buttons) {
		this.buttons = buttons;
	}

	public boolean getPlayerIsBlack() {
		return this.playerIsBlack;
	}

	public void setPlayerIsBlack(boolean playerIsBlack) {
		this.playerIsBlack = playerIsBlack;
	}

    public Board(boolean playerIsBlack){
		this.lastMove= new Move();
		this.isBlack=true;
        this.grayCount=0;
        this.playerIsBlack=playerIsBlack;
        this.blackHasMoves=true;
        this.whiteHasMoves=true;
        this.buttons= new JButton[64];
		for(int i=0;i<64;i++) {
			buttons[i] = new JButton();
			ReversAI.button_panel.add(buttons[i]);
			buttons[i].setFont(new Font("",Font.PLAIN,60)); //Fonts name helps to find out what disk(or not) is placed there
			buttons[i].setFocusable(false);
            buttons[i].setBackground(new Color(0,255,0));
		}
    }

    public Board(Board board){
		this.lastMove=board.getLastMove();
		this.isBlack=board.getIsBlack();
        this.grayCount=board.getGrayCount();
        this.playerIsBlack=board.getPlayerIsBlack();
        this.blackHasMoves=board.getBlackHasMoves();
        this.whiteHasMoves=board.getWhiteHasMoves();
        this.buttons= board.getButtons();
    }

    int evaluate()
    {
        int scoreB = 0;
        int scoreW = 0;

        for(int i=0; i<buttons.length;i++){
			if(buttons[i].getFont().getName().equals("B")){
				scoreB++;
			}
		}

		for(int i=0; i<buttons.length;i++){
			if(buttons[i].getFont().getName().equals("W")){
				scoreW++;
			}
		}

        return scoreB - scoreW;
    }

    /* Generates the children of the state
     * Any square in the board that is empty results to a child
     */
    ArrayList<Board> getChildren(String color)
    {
        ArrayList<Board> children = new ArrayList<>();
        for(int i = 0; i < 64; i++)
        {
			if(buttons[i].getFont().getName().equals("G")) {
                    Board child = new Board(this);
                    child.placeDisk(i,color);
					child.flipDisks(i);
					child.setIsBlack(!this.isBlack);
					child.setLastMove(new Move(i,child.evaluate()));
                    children.add(child);
                }
        }
        return children;
    }

    //Flips disks after player's move
    public void flipDisks(int i){
		String oponentsColor="";
		String ourColor="";
		int j;
		Boolean flipable=false;
		if(isBlack){
			ourColor="B";
			oponentsColor="W";

			//checking for RIGHT direction--------------------
			if((i+1)%8!=0){
				j=i+1;
				flipable=false;
				while(buttons[j].getFont().getName().equals(oponentsColor) && j<=i+8-(i%8+1)){
					j++;
					if(buttons[j].getFont().getName().equals(ourColor)){
						flipable=true;
					}
				}
				j=i+1;
				if(flipable){
					while(buttons[j].getFont().getName().equals(oponentsColor)){
						placeDisk(j,ourColor);
						j++;
					}
				}
			}
				//checking for RIGHT direction--------------------
				//checking for LEFT direction--------------------
				if(i%8!=0){
				j=i-1;
				flipable=false;
				while(buttons[j].getFont().getName().equals(oponentsColor) && j>i-(i%8)){
					j--;
					if(buttons[j].getFont().getName().equals(ourColor)){
						flipable=true;
					}
				}
				j=i-1;
				if(flipable){
					while(buttons[j].getFont().getName().equals(oponentsColor)){
						placeDisk(j,ourColor);
						j--;
					}
				}
			}
				//checking for LEFT direction--------------------
				//checking for UP direction--------------------
				if(i>=8){
				j=i-8;
				flipable=false;
				while(buttons[j].getFont().getName().equals(oponentsColor) && j>=8){
					j-=8;
					if(buttons[j].getFont().getName().equals(ourColor)){
						flipable=true;
					}
				}
				j=i-8;
				if(flipable){
					while(buttons[j].getFont().getName().equals(oponentsColor)){
						placeDisk(j,ourColor);
						j-=8;
					}
				}
			}
				//checking for UP direction--------------------
				//checking for DOWN direction--------------------
				if(i<=55){
				j=i+8;
				flipable=false;
				while(buttons[j].getFont().getName().equals(oponentsColor) && j<=55){
					j+=8;
					if(buttons[j].getFont().getName().equals(ourColor)){
						flipable=true;
					}
				}
				j=i+8;
				if(flipable){
					while(buttons[j].getFont().getName().equals(oponentsColor)){
						placeDisk(j,ourColor);
						j+=8;
					}
				}
			}
				//checking for DOWN direction--------------------
				//checking for LEFT UP DIAGONAL direction--------------------
				if(i>=8 && i%8!=0){
				j=i-9;
				flipable=false;
				while(buttons[j].getFont().getName().equals(oponentsColor) && j%8!=0 && j>=8){
					j-=9;
					if(buttons[j].getFont().getName().equals(ourColor)){
						flipable=true;
					}
				}
				j=i-9;
				if(flipable){
					while(buttons[j].getFont().getName().equals(oponentsColor)){
						placeDisk(j,ourColor);
						j-=9;
					}
				}
			}
				//checking for LEFT UP DIAGONAL direction--------------------
				//checking for LEFT DOWN DIAGONAL direction--------------------
				if(i<=55 && i%8!=0){
				j=i+7;
				flipable=false;
				while(buttons[j].getFont().getName().equals(oponentsColor) && j%8!=0 && j<=55){
					j+=7;
					if(buttons[j].getFont().getName().equals(ourColor)){
						flipable=true;
					}
				}
				j=i+7;
				if(flipable){
					while(buttons[j].getFont().getName().equals(oponentsColor)){
						placeDisk(j,ourColor);
						j+=7;
					}
				}
			}
				//checking for LEFT DOWN DIAGONAL direction--------------------
				//checking for RIGHT UP DIAGONAL direction--------------------
				if((i+1)%8!=0 && i>=8){
				j=i-7;
				flipable=false;
				while(buttons[j].getFont().getName().equals(oponentsColor) && j%8!=7 && j>=8){
					j-=7;
					if(buttons[j].getFont().getName().equals(ourColor)){
						flipable=true;
					}
				}
				j=i-7;
				if(flipable){
					while(buttons[j].getFont().getName().equals(oponentsColor)){
						placeDisk(j,ourColor);
						j-=7;
					}
				}
			}
				//checking for RIGHT UP DIAGONAL direction--------------------
				//checking for RIGHT DOWN DIAGONAL direction--------------------
				if((i+1)%8!=0 && i<=55){
				j=i+9;
				flipable=false;
				while(buttons[j].getFont().getName().equals(oponentsColor) && j%8!=7 && j<=54){
					j+=9;
					if(buttons[j].getFont().getName().equals(ourColor)){
						flipable=true;
					}
				}
				j=i+9;
				if(flipable){
					while(buttons[j].getFont().getName().equals(oponentsColor)){
						placeDisk(j,ourColor);
						j+=9;
					}
				}
			}
				//checking for RIGHT DOWN DIAGONAL direction--------------------
		}else{
			ourColor="W";
			oponentsColor="B";

			//checking for RIGHT direction--------------------
			if((i+1)%8!=0){
				j=i+1;
				flipable=false;
				while(buttons[j].getFont().getName().equals(oponentsColor) && j<=i+8-(i%8+1)){
					j++;
					if(buttons[j].getFont().getName().equals(ourColor)){
						flipable=true;
					}
				}
				j=i+1;
				if(flipable){
					while(buttons[j].getFont().getName().equals(oponentsColor)){
						placeDisk(j,ourColor);
						j++;
					}
				}
			}
				//checking for RIGHT direction--------------------
				//checking for LEFT direction--------------------
				if(i%8!=0){
				j=i-1;
				flipable=false;
				while(buttons[j].getFont().getName().equals(oponentsColor) && j>i-(i%8)){
					j--;
					if(buttons[j].getFont().getName().equals(ourColor)){
						flipable=true;
					}
				}
				j=i-1;
				if(flipable){
					while(buttons[j].getFont().getName().equals(oponentsColor)){
						placeDisk(j,ourColor);
						j--;
					}
				}
			}
				//checking for LEFT direction--------------------
				//checking for UP direction--------------------
				if(i>=8){
				j=i-8;
				flipable=false;
				while(buttons[j].getFont().getName().equals(oponentsColor) && j>=8){
					j-=8;
					if(buttons[j].getFont().getName().equals(ourColor)){
						flipable=true;
					}
				}
				j=i-8;
				if(flipable){
					while(buttons[j].getFont().getName().equals(oponentsColor)){
						placeDisk(j,ourColor);
						j-=8;
					}
				}
			}
				//checking for UP direction--------------------
				//checking for DOWN direction--------------------
				if(i<=55){
				j=i+8;
				flipable=false;
				while(buttons[j].getFont().getName().equals(oponentsColor) && j<=55){
					j+=8;
					if(buttons[j].getFont().getName().equals(ourColor)){
						flipable=true;
					}
				}
				j=i+8;
				if(flipable){
					while(buttons[j].getFont().getName().equals(oponentsColor)){
						placeDisk(j,ourColor);
						j+=8;
					}
				}
			}
				//checking for DOWN direction--------------------
				//checking for LEFT UP DIAGONAL direction--------------------
				if(i>=8 && i%8!=0){
				j=i-9;
				flipable=false;
				while(buttons[j].getFont().getName().equals(oponentsColor) && j%8!=0 && j>=8){
					j-=9;
					if(buttons[j].getFont().getName().equals(ourColor)){
						flipable=true;
					}
				}
				j=i-9;
				if(flipable){
					while(buttons[j].getFont().getName().equals(oponentsColor)){
						placeDisk(j,ourColor);
						j-=9;
					}
				}
			}
				//checking for LEFT UP DIAGONAL direction--------------------
				//checking for LEFT DOWN DIAGONAL direction--------------------
				if(i<=55 && i%8!=0){
				j=i+7;
				flipable=false;
				while(buttons[j].getFont().getName().equals(oponentsColor) && j%8!=0 && j<=55){
					j+=7;
					if(buttons[j].getFont().getName().equals(ourColor)){
						flipable=true;
					}
				}
				j=i+7;
				if(flipable){
					while(buttons[j].getFont().getName().equals(oponentsColor)){
						placeDisk(j,ourColor);
						j+=7;
					}
				}
			}
				//checking for LEFT DOWN DIAGONAL direction--------------------
				//checking for RIGHT UP DIAGONAL direction--------------------
				if((i+1)%8!=0 && i>=8){
				j=i-7;
				flipable=false;
				while(buttons[j].getFont().getName().equals(oponentsColor) && j%8!=7 && j>=8){
					j-=7;
					if(buttons[j].getFont().getName().equals(ourColor)){
						flipable=true;
					}
				}
				j=i-7;
				if(flipable){
					while(buttons[j].getFont().getName().equals(oponentsColor)){
						placeDisk(j,ourColor);
						j-=7;
					}
				}
			}
				//checking for RIGHT UP DIAGONAL direction--------------------
				//checking for RIGHT DOWN DIAGONAL direction--------------------
				if((i+1)%8!=0 && i<=55){
				j=i+9;
				flipable=false;
				while(buttons[j].getFont().getName().equals(oponentsColor) && j%8!=7 && j<=54){
					j+=9;
					if(buttons[j].getFont().getName().equals(ourColor)){
						flipable=true;
					}
				}
				j=i+9;
				if(flipable){
					while(buttons[j].getFont().getName().equals(oponentsColor)){
						placeDisk(j,ourColor);
						j+=9;
					}
				}
			}
				//checking for RIGHT DOWN DIAGONAL direction--------------------
		}
	
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
	public Boolean checkGame(){
		if(!blackHasMoves && !whiteHasMoves){
			endGame();
			return true; //game ended
		}
		return false;
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
			ReversAI.textfield.setText("Black WINS \n"+ black_disks + " vs " + white_disks);
		}else if(black_disks<white_disks){
			ReversAI.textfield.setText("White WINS \n"+ white_disks + " vs " + black_disks);
		}else{
			ReversAI.textfield.setText("It's a DRAW");
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
}
