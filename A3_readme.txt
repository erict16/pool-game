---
1. Overview
A simple pool game with JavaFx extension. Using the mouse to hit the cue ball, to kicking all the other balls into the pockets, you will win the game! Also there is timer and score system in the game, there will keep showing until you win or quit the game. For score, different color matches different score. Red: 1, Yellow: 2, Green: 3, Brown: 4, Blue: 5, Purple: 6, Black: 7, Orange: 8. After you win the game, the score and the time duration will show on the screen.

Enjoy the game!

---
2. How to run the program
Open the terminal, input `gradle run` to start the game.

---
3. Extension implementation
	1. Pockets
	2. Colour Balls
	3. Difficulty level
	4. Time
	5. Score
	6. Cheat
	7. Undo (Still in progress)

---
4. Design pattern (Class and file names provided)
	4.1 Observer Design Pattern:
		Under the package `observer`. Classes including 'Observer' interface and 'ScoreObserver' class. 
		
	4.2 Singleton Design Pattern
		Under the package 'singleton'. Classes including 'TimeSingleton'
		
	4.3 State Design Pattern
		Under the package 'state'. Classes including abstract class 'Level', classes 'Easy', 'Normal' and 'Hard'.
		
	4.4 Memento Design Pattern
		Under the package 'memento'. Classes including 'Undo'.

---
5. How to select difficulty level
Before begin the game, you may select three difficulty level, they are 'easy', 'normal' and 'hard'. Simply click the button corresponding to the level you would like to choose.

---
6. How to cheat (Remove balls)
When the game begin, click the combobox to choose the colour of balls you would like to remove. After selecting the colour, click the 'Remove' button to remove the ball from table.

Removing balls is different with hitting a ball into pockets. If you remove the call by this function, you will not obtain the score from the removed balls.

--
7. Plus information
You can adjust value of of the configuration of the pockets, table friction, length and width.

But please don't input any invalid number (negative number), further more, with the friction of the table, the value could only be 0-1.


