/**
 * This class was made for Assignment 3 for CS 445 Spring 2018. This class uses recursion and backtracking to find a phrase in a word
 * search. These words searches templates are entered by the user along with the phrase to search for. 
 */

import java.io.*;
import java.util.*;

public class recursion_ws
{	
	public static void main(String [] args)
	{
		new recursion_ws();
	}
	/**
	 * This constructor for the Assig3 class prompts the user for a text file representing a word search with the coordinates
	 * on the top row. After reading in this file, the constructor prompts the user for a phrase to search for. Then the 
	 * constructor calls a recursive method which searches for this phrase. If it is found, then the coordinates of the whole
	 * phrase are printed. If not, then a message claiming 'phrase not found' is printed. The recursion is called for every place 
	 * letter on the board. 
	 * 
	 */
	public recursion_ws()
	{
		Scanner inScan = new Scanner(System.in);
		Scanner fReader;
		File fName;
        String fString = "", phrase = "";
       
       	// Make sure the file name is valid
        while (true)
        {
           try
           {
               System.out.println("Please enter grid filename:");
               fString = inScan.nextLine();
               fName = new File(fString);
               fReader = new Scanner(fName);
               break;
           }
           catch (IOException e)
           {
               System.out.println("Problem " + e);
           }
        }
		String [] dims = (fReader.nextLine()).split(" ");
		int rows = Integer.parseInt(dims[0]);
		int cols = Integer.parseInt(dims[1]);
		
		char [][] theBoard = new char[rows][cols];

		for (int i = 0; i < rows; i++)
		{
			String rowString = fReader.nextLine();
			for (int j = 0; j < rowString.length(); j++)
			{
				theBoard[i][j] = Character.toLowerCase(rowString.charAt(j));
			}
		}

		showBoard(theBoard, rows, cols);

		
		System.out.println("Please enter phrase (sep. by single spaces): ");
        phrase = inScan.nextLine().toLowerCase();
        String[] words = phrase.split(" ");
        String spaceLess = phrase.replace(" ", "");
        System.out.print("Looking for: " + phrase);
        System.out.println();
        System.out.println("containing " + words.length + " word(s)");
        System.out.println("The phrase: " + phrase);
		int a = -1;
		int b = -1;
		int x = -1;
		int y = -1;
		int[] endVals = new int[2]; 
		for (int r = 0; r < rows&& a == -1; r++)
		{
			for (int c = 0; c < cols && a == -1; c++)
			{
				endVals = findPhrase(r, c, spaceLess, 0, theBoard);
				if (endVals[0] != -1)
				{
					a = r; 
					b = c;
					x = endVals[0];
					y = endVals[1];
				}
			}
		}
		if(a != -1)
		{
			System.out.println("was found");
			System.out.println(phrase + ": (" + a + "," + b + ") to (" + x + "," + y +")");
		}
		else
			System.out.println("was not found");
		
		showBoard(theBoard, rows, cols);
	}
	/**
	 * This method searches for the next letters of the phrase recursively. Since this method is called in a nested for loop, 
	 * every letter in the phrase will be searched for. The recursive part calls the findPhrase method with loc + 1 passed in, as to
	 * search for the next letter. The recursion also searches for the next letter in the order: right, down, left, up. 
	 * 
	 * @param r; the row of the letter in the board where search is called
	 * @param c; the column of the letter in the board where search is called
	 * @param searchFor; the phrase to search for
	 * @param loc; the location in the phrase where the character is searched for using recursion
	 * @param board; the 2-dimensional character array that represents the word-search board
	 * @return int[]; int array of size two. If the phrase is not found, then this return will be {-1,-1}. 
	 * 		   If found, this will be the coordinates of the ending point of the searchFor string. 
	 */
	public int[] findPhrase(int r, int c, String searchFor, int loc, char[][] board)
    {
    		int[] endVals = {-1,-1};
    		if(loc == searchFor.length())
    		{	
    			endVals[0] = r;
    			endVals[1] = c-1;
    			return endVals; 
    		}
    		if (r >= board.length || r < 0 || c >= board[0].length || c < 0)
			return endVals;     			
		else if (board[r][c] != searchFor.charAt(loc))
		{
			return endVals;
		}
		else
		{
			char temp = board[r][c];
			board[r][c] = Character.toUpperCase(board[r][c]);
			if(endVals[0] == -1)
			{
				endVals = findPhrase(r, c+1, searchFor, loc + 1, board);
			}	
			if(endVals[0] == -1)
			{
				 endVals = findPhrase(r+1, c, searchFor, loc + 1, board);
			}	
			if(endVals[0] == -1)
			{
				endVals = findPhrase(r, c-1, searchFor, loc + 1, board);
			}	
			if(endVals[0] == -1)
			{
				endVals = findPhrase(r-1, c, searchFor, loc + 1, board);
			}
			board[r][c] = temp;
		}
    		return endVals; 
    }

    public static void showBoard(char[][] theBoard, int rows, int cols)
    {
    		for (int i = 0; i < rows; i++)
    		{
			for (int j = 0; j < cols; j++)
			{
				System.out.print(theBoard[i][j] + " ");
			}
			System.out.println();
    		}
    }
}

