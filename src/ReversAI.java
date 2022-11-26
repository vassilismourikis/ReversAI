import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class ReversAI implements ActionListener{

	JFrame frame = new JFrame();
	JPanel title_panel = new JPanel();
	public static JPanel button_panel = new JPanel();
	public static JLabel textfield = new JLabel();
	boolean isBlack=true; //It's black's turn
	private Board board;
	private int minimaxDepth;
	private Minimax miniMax;
	private JButton[] buttons;

	ReversAI(boolean playerstarts, int minimaxDepth){
		this.board= new Board(playerstarts);
		this.minimaxDepth=minimaxDepth;
		this.miniMax=new Minimax(minimaxDepth);
		System.out.println("Player is black: "+board.getPlayerIsBlack() + "\nDepth: " + minimaxDepth);
		
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
		
		title_panel.add(textfield);
		frame.add(title_panel,BorderLayout.NORTH);
		frame.add(button_panel);
		
		board.placeDisk(27, "W");
		board.placeDisk(28, "B");
		board.placeDisk(35, "B");
		board.placeDisk(36, "W");
		buttons=board.getButtons();
		for(int i=0;i<64;i++) {
			buttons[i].addActionListener(this);
		}

		board.possibleMoves();
		if(!board.getPlayerIsBlack()){
			int miniMaxValue=miniMax.nextBestMove(board, "B").getPos();
			board.placeDisk(miniMaxValue,"B");
			board.flipDisks(miniMaxValue);
			isBlack=false;
			textfield.setText("White's turn");
			board.setLastMove(new Move(miniMaxValue,board.evaluate()));
			buttons=board.getButtons();
		}
	}

	

	@Override
	public void actionPerformed(ActionEvent e) {
		int miniMaxValue;
		for(int i=0;i<64;i++) {
			if(e.getSource()==board.getButtons()[i]) {
				if(board.getPlayerIsBlack()){
					
						if(board.getButtons()[i].getFont().getName().equals("G")) {
							board.placeDisk(i,"B");
							board.flipDisks(i);
							isBlack=false;
							textfield.setText("White's turn");
							board.setLastMove(new Move(i,board.evaluate()));
							buttons=board.getButtons();
					
					
							miniMaxValue=miniMax.nextBestMove(board, "W").getPos();
							board.placeDisk(miniMaxValue,"W");
							board.flipDisks(miniMaxValue);
							isBlack=true;
							textfield.setText("Black's turn");
							board.setLastMove(new Move(miniMaxValue,board.evaluate()));
							buttons=board.getButtons();
						}
					
				}else{
					
					if(board.getButtons()[i].getFont().getName().equals("G")) {
						board.placeDisk(i,"W");
						board.flipDisks(i);
						isBlack=true;
						textfield.setText("Black's turn");
						board.setLastMove(new Move(i,board.evaluate()));
						buttons=board.getButtons();
					
						miniMaxValue=miniMax.nextBestMove(board, "B").getPos();
						board.placeDisk(miniMaxValue,"B");
						board.flipDisks(miniMaxValue);
						isBlack=false;
						textfield.setText("White's turn");
						board.setLastMove(new Move(miniMaxValue,board.evaluate()));
						buttons=board.getButtons();
						
					}
						
					}
				}
				
			}			

		board.possibleMoves();
		if(isBlack && board.getGrayCount()==0){
			board.setBlackHasMoves(false);
			isBlack=!isBlack;
			textfield.setText("White's turn");
			board.possibleMoves();
			if(board.getGrayCount()!=0){
				board.setWhiteHasMoves(true);
			}else{
				board.setWhiteHasMoves(false);
				board.checkGame();
			} 
		}else if(!isBlack && board.getGrayCount()==0){
			board.setWhiteHasMoves(false);
			isBlack=!isBlack;
			textfield.setText("Black's turn");
			board.possibleMoves();
			if(board.getGrayCount()!=0){
				board.setBlackHasMoves(true);
			}else{
				board.setBlackHasMoves(false);
				board.checkGame();
			}
		}else if(isBlack && board.getGrayCount()!=0){
			board.setBlackHasMoves(true);
		}else if(!isBlack && board.getGrayCount()!=0){
			board.setWhiteHasMoves(true);
		}
	}
}
