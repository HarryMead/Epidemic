import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class epidemic_loop {
	
	//Class for Universes
    public static class Universe {
		StringBuilder data = null;
		int length = 0;
		int height = 0;
		char[][] universe = null;
        
		public Universe(StringBuilder data, int length) {
			this.data = data;
			this.length = length;
			this.height = data.length() / length;
			this.universe = new char[height][length];
			
			int counter = 0;
			for (int row = 0; row < universe.length; row++) { 
				for (int col = 0; col < universe[row].length; col++) { 
					universe[row][col] = data.charAt(counter);
					counter++;
				} 
			}
		}
		
		public char[][] getArray() {
			return universe;
		}
		
    }	
	
	//Get the four adjacent neighbours for a given cell
	public static List<int[]> getNeighbors(int x, int y, int maxX, int maxY) {
		List<int[]> neighbors = new ArrayList<int[]>();
		if (x > 0) {
			int[] neighbour = {x-1, y};
			neighbors.add(neighbour);
		}
		if (y > 0) {
			int[] neighbour = {x, y-1};
			neighbors.add(neighbour);
		}
		if (x < maxX) {
			int[] neighbour = {x+1, y};
			neighbors.add(neighbour);
		}
		if (y < maxY) {
			int[] neighbour = {x, y+1};
			neighbors.add(neighbour);
		}
		return neighbors;
	}
	
	public static void printUniverse(char[][] universe) {
		if (universe[0][0] == 'O') {
			for (int row = 1; row < universe.length-1; row++) { 
				for (int col = 1; col < universe[row].length-1; col++) { 
					System.out.print(universe[row][col]);
				} 
				System.out.println();
			}
			System.out.println();
		} else {
			for (int row = 0; row < universe.length; row++) { 
				for (int col = 0; col < universe[row].length; col++) { 
					System.out.print(universe[row][col]);
				} 
				System.out.println();
			}
		}
	}
	


	public static char[][] deepCopy(char[][] matrix) {
		return java.util.Arrays.stream(matrix).map(el -> el.clone()).toArray($ -> matrix.clone());
	}
	
	public static void minimumSickIndividuals(char[][] universe) {
		char[][] temp = deepCopy(universe);
		int sickIndividuals = 2;
		int vulnerableIndividuals = 0;
		int immuneIndividuals = 0;
		
		//Count vulnerable individuals
		for (int row = 0; row < safeArray.length; row++) { 
			for (int col = 0; col < safeArray[row].length; col++) { 
				if (safeArray[row][col] == '.') {
					vulnerableIndividuals++;
				} else if (safeArray[row][col] == 'I') {
					immuneIndividuals++;
				}
			} 
		}

                for (int row = 0; row < temp.length; row++) { 
                    for (int col = 0; col < temp[row].length; col++) { 
                        if (temp[row][col] == '.') {
                            temp[row][col] = 'S';
                            infect(temp);
							printUniverse(temp);
							System.out.println();
                            if (bestCount == (vulnerableIndividuals)) {
                                temp[row][col] = 'S';
                                
                                int counter = 0;
                                for (int row2 = 0; row2 < temp.length; row2++) { 
                                    for (int col2 = 0; col2 < temp[row].length; col2++) { 
                                        if (temp[row2][col2] == 'S') {
                                            counter++;
                                        }
                                    } 
                                }
                                System.out.println(counter);
                                printUniverse(temp);
                                System.out.println();
                                return;
                            }
                            temp = deepCopy(universe);
                        }
                    } 
		}
                minimumSickIndividuals(store);
		
	
	}

    static char[][] store = null;
    static int bestCount = 0;
    static int count = 0;

    public static void infect(char[][] array) {
        bestCount = 0;
        char[][] temp = deepCopy(array);
        char[][] finalState = getFinalState(temp);
        int row, col;
        countSickIndividuals(finalState);

        if (bestCount > count) {
            count = bestCount;
            store = deepCopy(temp);
        }
    }

    public static void countSickIndividuals(char[][] array) {
        int counter = 0;
        for (int row = 0; row < array.length; row++) { 
            for (int col = 0; col < array[row].length; col++) { 
                if (array[row][col] == 'S') {
                    counter++;
                }
            } 
        }
        if (counter > bestCount) {
            bestCount = counter;
        }
    }
	
	public static char[][] getFinalState(char[][] universe) {
		char[][] universeArray = universe;
		int sickNeighbours = 0;
		char sick = 'S';
		boolean anyChanges = true;
		
		//Add padding to the universe array
		int numOfPads = 1;
		char padWith = 'O';
		char[][] temp = new char[universeArray.length + numOfPads*2][universeArray[0].length + numOfPads*2];
		for (int i = 0; i < temp.length; i++) {
			for (int j = 0; j < temp[i].length; j++) {
				temp[i][j] = padWith;
			}
		}
		for (int i = 0; i < universeArray.length; i++) {
			for (int j = 0; j < universeArray[i].length; j++) {
				temp[i+numOfPads][j+numOfPads] = universeArray[i][j];
			}
		}
		
		universeArray = temp;
		
		//Loop through all the cells in the array and check if they have sick neighbours
		while (anyChanges == true) {
			anyChanges = false;
			for (int row = 0; row < universeArray.length; row++) { 
					for (int col = 0; col < universeArray[row].length; col++) { 
						if (universeArray[row][col] != 'O') {
							for (int[] cords : getNeighbors(row, col, universeArray.length, universeArray[0].length)) {
								if (universeArray[cords[0]][cords[1]] == sick) {
									sickNeighbours++;
								}
							}
							if (sickNeighbours >= 2 && universeArray[row][col] != 'I' && universeArray[row][col] != 'S') {
								universeArray[row][col] = 'S';
								anyChanges = true;
							}
						}
						sickNeighbours = 0;
					} 
			}
		}
		
		return universeArray;
	}

    static char[][] safeArray = null;
    
    public static void main(String[] args) throws FileNotFoundException {
		
		List<Universe> Universes = new ArrayList<Universe>();
		StringBuilder sb = new StringBuilder("");
		int lineLength = 0;
        try {
            Scanner scan = new Scanner(new File(args[0]));
            while (scan.hasNext()) {
				String nextLine = scan.nextLine();
				
				if (nextLine.isEmpty()) {
					Universes.add(new Universe(sb, lineLength));
					sb = new StringBuilder("");
					lineLength = 0;
				} else {
					sb.append(nextLine);
					lineLength = nextLine.length();
				}
            }
			Universes.add(new Universe(sb, lineLength));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
		
		if (args[1].equals("finalstate")) {
			for (Universe universe: Universes) {
				char[][] array = getFinalState(universe.getArray());
				printUniverse(array);
			}
		} else if (args[1].equals("minimum")) {
			for (Universe universe: Universes) {
                            safeArray = universe.getArray();
                            store = null;
                            bestCount = 0;
                            count = 0;
				minimumSickIndividuals(universe.getArray());
			}
		}
    }
}
