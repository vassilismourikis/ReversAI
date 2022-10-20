# ReversAI

## AUEB | Artificial Intelligence | 2022-2023

The purpose of the project is to create a Reversi game using minimax algorithm and alpha-beta pruning in order to make computer decide the best possible move against Player.

### How to play Othello


Othello is a strategy board game for two players (Black and White), played on an 8 by 8 board. The game traditionally begins with four discs placed in the middle of the board as shown below. Black moves first.

  ![](https://www.eothello.com/images/how_to_play_othello_0.png)

Black must place a black disc on the board, in such a way that there is at least one straight (horizontal, vertical, or diagonal) occupied line between the new disc and another black disc, with one or more contiguous white pieces between them. In the starting position, Black has the following 4 options indicated by translucent discs:

![](https://user-images.githubusercontent.com/61802563/195997981-6cba22df-e441-46ad-bd62-3c8c920d0ba7.png)

After placing the disc, Black flips all white discs lying on a straight line between the new disc and any existing black discs. All flipped discs are now black. If Black decides to place a disc in the topmost location, one white disc gets flipped, and the board now looks like this:

![](https://user-images.githubusercontent.com/61802563/195997999-0157d35d-2080-45ae-bcf8-bf606dac4ad8.png)

Now White plays. This player operates under the same rules, with the roles reversed: White lays down a white disc, causing black discs to flip. Possibilities at this time would be:

![](https://www.eothello.com/images/how_to_play_othello_3.png)

If White plays the bottom left option and flips one disc:

![](https://www.eothello.com/images/how_to_play_othello_4.png)

Players alternate taking turns. If a player does not have any valid moves, play passes back to the other player. When neither player can move, the game ends. A game of Othello may end before the board is completely filled.

The player with the most discs on the board at the end of the game wins. If both players have the same number of discs, then the game is a draw.

(Source: https://www.eothello.com/)
