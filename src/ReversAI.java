import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class ReversAI implements ActionListener{

	JFrame frame = new JFrame();
	JPanel title_panel = new JPanel();
	public static JPanel button_panel = new JPanel();
	public static JLabel textfield = new JLabel();
	private Board board;
	private int minimaxDepth;
	private Minimax miniMax;
	private boolean AIplay;

	ReversAI(boolean playerstarts, int minimaxDepth){
		this.minimaxDepth=minimaxDepth;
		if(minimaxDepth==-1){
			AIplay=false;
		}else{
			AIplay=true;
			this.miniMax=new Minimax(minimaxDepth);
		}
		this.board= new Board(playerstarts);
		System.out.println("Player is black: "+board.getPlayerIsBlack() + "\nDepth: " + minimaxDepth + " AI plays: " +AIplay);
		
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
			board.getButtons()[i] = new JButton();
			ReversAI.button_panel.add(board.getButtons()[i]);
			board.getButtons()[i].setFont(new Font("",Font.PLAIN,60)); //Fonts name helps to find out what disk(or not) is placed there
			board.getButtons()[i].setFocusable(false);
            board.getButtons()[i].setBackground(new Color(0,255,0));
		}
		
		title_panel.add(textfield);
		frame.add(title_panel,BorderLayout.NORTH);
		frame.add(button_panel);
		
		board.placeDisk(27, "W");
		board.placeDisk(28, "B");
		board.placeDisk(35, "B");
		board.placeDisk(36, "W");
		for(int i=0;i<64;i++) {
			board.getButtons()[i].addActionListener(this);
		}

		board.possibleMoves();
		if(AIplay){
		if(!board.getPlayerIsBlack()){
			int miniMaxValue=miniMax.nextBestMove(board, "B").getPos();
			board.placeDisk(miniMaxValue,"B");
			board.flipDisks(miniMaxValue);
			board.setIsBlack(false);
			textfield.setText("White's turn");
			board.setLastMove(new Move(miniMaxValue,true));
			board.possibleMoves();
		}
	}
	}

	

	@Override
	public void actionPerformed(ActionEvent e) {
		int miniMaxValue;
		for(int i=0;i<64;i++) {
			if(e.getSource()==board.getButtons()[i]) {
				if(board.getIsBlack()){
						if(board.getButtons()[i].getFont().getName().equals("G")) {
							board.placeDisk(i,"B");
							board.flipDisks(i);
							board.setIsBlack(false);
							textfield.setText("White's turn");
							board.setLastMove(new Move(i,true));
							board.possibleMoves();
						}
				}else{
					if(board.getButtons()[i].getFont().getName().equals("G")) {
						board.placeDisk(i,"W");
						board.flipDisks(i);
						board.setIsBlack(true);
						textfield.setText("Black's turn");
						board.setLastMove(new Move(i,true));
						board.possibleMoves();				
					}
				}
			}
		}
			if(AIplay){
				if(board.getPlayerIsBlack()){
					miniMaxValue=miniMax.nextBestMove(board, "W").getPos();
					board.placeDisk(miniMaxValue,"W");
					board.flipDisks(miniMaxValue);
					board.setIsBlack(true);
					textfield.setText("Black's turn");
					board.setLastMove(new Move(miniMaxValue,true));
				}else{
					miniMaxValue=miniMax.nextBestMove(board, "B").getPos();
					board.placeDisk(miniMaxValue,"B");
					board.flipDisks(miniMaxValue);
					board.setIsBlack(false);
					textfield.setText("White's turn");
					board.setLastMove(new Move(miniMaxValue,true));
				}
			}	
		if(board.getIsBlack() && board.getGrayCount()==0){
			board.setBlackHasMoves(false);
			board.setIsBlack(!board.getIsBlack());
			textfield.setText("White's turn");
			board.possibleMoves();
			if(board.getGrayCount()!=0){
				board.setWhiteHasMoves(true);
			}else{
				board.setWhiteHasMoves(false);
				board.checkGame();
			} 
		}else if(!board.getIsBlack() && board.getGrayCount()==0){
			board.setWhiteHasMoves(false);
			board.setIsBlack(!board.getIsBlack());
			textfield.setText("Black's turn");
			board.possibleMoves();
			if(board.getGrayCount()!=0){
				board.setBlackHasMoves(true);
			}else{
				board.setBlackHasMoves(false);
				board.checkGame();
			}
		}else if(board.getIsBlack() && board.getGrayCount()!=0){
			board.setBlackHasMoves(true);
		}else if(!board.getIsBlack() && board.getGrayCount()!=0){
			board.setWhiteHasMoves(true);
		}
	}
}
