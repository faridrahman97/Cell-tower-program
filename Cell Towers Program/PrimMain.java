/*
 * Project Title - Minimum Spanning Tree
 * Group members: Yadukumari Shanthigrama Ramesh, Farid Rahman, David, Xanthia
 * Windows10 and Java compiler 1.8
 */
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Map.Entry;

//Program merge by Xanthia and yadukumari
//Xanthia Victuelles
public class PrimMain {
	public static Scanner userScanner = new Scanner(System.in);

	public static void main(String[] args) throws IOException {

		MinimumSpanningTree<CellTower> primGraph = new MinimumSpanningTree<>(); // MST
																				// instantiated
		String filename;
		// System.out.print("Enter the input filename: ");
		filename = "CellTowers1.txt";// userScanner.nextLine();
		if (readFile(primGraph, filename)) { // if the file is read successfully
												// then
			// menus will appear
			mainMenus(primGraph, filename);
		}

	}

	public static boolean readFile(MinimumSpanningTree<CellTower> m1, String fname) {

		/*
		 * Reading the file, calling the openInputFile, and populating the graph
		 */
		Scanner console = openInputFile(fname);
		CellTower cell1 = null;
		CellTower cell2;
		try {
			while (console.hasNext()) {
				String c1 = console.next();
				String c2 = console.next();
				double weight = console.nextDouble();

				cell1 = new CellTower(c1);
				cell2 = new CellTower(c2);
				m1.addEdge(cell1, cell2, weight);
			}
			return true;
		} catch (Exception e) {
			System.err.println("\n\nUnable to read file.");
			System.exit(1);
		}
		return false;

	}

	public static void mainMenus(MinimumSpanningTree<CellTower> m1, String fname) throws IOException {

		/*
		 * Menus for the graph NOTE: The traversals are needed in order to
		 * preview BOTH the graph and the minimum spanning tree. RIGHT NOW, we
		 * need menus 2,3,5: The delete celltower menu#(2), the Show Existing
		 * graph menu#(3),and the Show Minimum spanning tree menu#(5).
		 * 
		 **/

		CellTower obj1;
		Vertex<CellTower> ver1;
		int choice = 7;
		do {
			System.out.println("\n---------------------------------" + "\n| Prim's Algorithms' Main Menu: |"
					+ "\n---------------------------------" + "\n" + "1.Add a New CellTower connection to the Graph\n"
					+ "2.Delete Existing CellTower Connection\n" + "3.Show Existing Graph\n" + "4.Show Adjacency List\n"
					+ "5.Show The Minimum Spanning Tree\n" + "6.Undo remove\n" + "7.Exit");

			System.out.print("\n\nPlease enter your choice:");

			try {
				choice = userScanner.nextInt(); // try catch
			} catch (InputMismatchException e) {
				System.out.println("Not a Number!");
			}

			switch (choice) {
			case 1: // add
				addToGraphMenu(m1, fname);
				break;
			case 2: // delete existing cellTower
				removeFromGraphMenu(m1,fname);
				break;
			case 3:// show graph
				traversal(m1);
				break;
			case 4:// Show adjacency list
				m1.showAdjTable();
				writeFile(m1);
				break;
			case 5:// Show minimum spanning tree
					// File outfile = new Fle()
				Scanner input = new Scanner(System.in);
				System.out.println("Enter the city name frome where you want to search? ");
				String city = input.next();
				obj1 = new CellTower(city);
				ver1 = new Vertex<>(obj1);
				ArrayList<Edge<CellTower>> results = m1.applyPrim(ver1);
				if(results == null){
					break;
				}
				for (int i = 0; i < results.size(); i++) {
					System.out.println(results.get(i));
				}
				writePrimFile(results);
				break;
			case 6:// exit
				m1.undoRemoval();
				m1.showAdjTable();
				replaceInputFile(m1,fname);
				break;
			case 7:
				System.out.println("\n\nProgram ended.");
				break;
			default:
				break;
			}
		} while (choice != 7);

	}

	public static void addToGraphMenu(MinimumSpanningTree<CellTower> m1, String fname) throws IOException {
		String city1, city2;
		double distance;
		Vertex<CellTower> vertex;
		System.out.println("1. Add a new CellTower connection to the Graph");

		System.out.print("\nEnter CellTower1 Name:");
		userScanner.nextLine();
		city1 = userScanner.nextLine();
		CellTower addC1 = new CellTower(city1);

		System.out.print("\nEnter CellTower2 Name:");
		city2 = userScanner.nextLine();
		CellTower addC2 = new CellTower(city2);
		

		System.out.print("\nEnter the distance between CellTowers:");
		distance = userScanner.nextDouble();
		userScanner.nextLine();
		
		Iterator<Entry<CellTower, Vertex<CellTower>>> vertexIter;

		// Find starting vertex
		for (vertexIter = m1.vertexSet.entrySet().iterator(); vertexIter.hasNext();) {
			vertex = vertexIter.next().getValue();
			if (vertex.data.equals(addC1) || vertex.data.equals(addC2)) {
				m1.addEdge(addC1, addC2, distance);
				System.out.println("Added Successfully! ");
				replaceInputFile(m1, fname);
				return;
			}
		}

		System.out.println("\nCannot add this edge since it results in a disconnected graph");
		System.out.println("Please try again with a valid edge");
	}

	public static void removeFromGraphMenu(MinimumSpanningTree<CellTower> m1, String fname) {

		@SuppressWarnings("resource")
		Scanner userIn = new Scanner(System.in);
		String city1, city2;
		CellTower obj1, obj2;
		Vertex<CellTower> ver1, ver2;
		Iterator<Entry<CellTower, Pair<Vertex<CellTower>, Double>>> edgeIter;
		Pair<Vertex<CellTower>, Double> singleEdge = null;
		double weight = 0;

		System.out.println("1. Enter the CellTower you want to want to remove");

		System.out.print("\nEnter CellTower1 Name:");

		city1 = userIn.nextLine();
		obj1 = new CellTower(city1);
		ver1 = new Vertex<>(obj1);

		System.out.print("\nEnter CellTower2 Name:");
		city2 = userIn.nextLine();
		obj2 = new CellTower(city2);
		ver2 = new Vertex<>(obj2);

		Vertex<CellTower> source = null;
		Iterator<Entry<CellTower, Vertex<CellTower>>> vertexIter;

		// Find starting vertex
		for (vertexIter = m1.vertexSet.entrySet().iterator(); vertexIter.hasNext();) {
			source = vertexIter.next().getValue();
			if (source.equals(ver1)) {
				break;
			}
		}

		for (edgeIter = source.adjList.entrySet().iterator(); edgeIter.hasNext();) {
			singleEdge = edgeIter.next().getValue();
			if (singleEdge.first == null) {
				System.out.println("This path doesn't exists in the graph ");
				System.out.println("Unable to remove");
				return;
			} else if (singleEdge.first.equals(ver2)) {
				weight = singleEdge.second;
				System.out.println(weight);
				break;
			}
		}
		if (m1.remove(ver1.getData(), ver2.getData())) {
			m1.undoAdd(ver1, ver2, weight);
			System.out.println("Removed " + obj1.toString() + " to " + obj2.toString() + " successfully!");
			removeEmptyCellTowerEntriesFromVertexSet(m1);
			replaceInputFile(m1, fname);

		} else {
			System.out.println("Unable to remove");
		}
	}
	/**
	 * Yadukumari
	 * Remove vertex entries that have no edges
	 */
	private static void removeEmptyCellTowerEntriesFromVertexSet(MinimumSpanningTree<CellTower> m1) {
		HashMap<CellTower, Vertex<CellTower>> vertexSet = m1.vertexSet;
		List<CellTower> emptyCellTowers = new ArrayList<CellTower>();
		
		for (Map.Entry<CellTower, Vertex<CellTower>> entry : vertexSet.entrySet()) {
			CellTower ct = entry.getKey();
			Vertex<CellTower> cellTowerVertex = entry.getValue();
			if (cellTowerVertex.adjList.size() == 0) {
				emptyCellTowers.add(ct);
			}
		}
		for (CellTower emptyCellTower : emptyCellTowers) {
			vertexSet.remove(emptyCellTower);
			System.out.println("Removed empty CellTower = " + emptyCellTower);
		}
	}

	public static void traversal(MinimumSpanningTree<CellTower> m1) {
		Vertex<CellTower> startElement = getStartingVertex(m1);
		m1.breadthFirstTraversalHelper(startElement, new CellTowerVisitor());
	}

	private static Vertex<CellTower> getStartingVertex(MinimumSpanningTree<CellTower> m) {
		Vertex<CellTower> source = null;
		Iterator<Entry<CellTower, Vertex<CellTower>>> vertexIter;

		// Find starting vertex
		for (vertexIter = m.vertexSet.entrySet().iterator(); vertexIter.hasNext();) {
			source = vertexIter.next().getValue();
			if (source != null) {
				break;
			}
		}

		return source;
	}

	// opens a text file for input, returns a Scanner:
	public static Scanner openInputFile(String filename) {
		Scanner scanner = null;

		// System.out.print("Enter the input filename: ");
		filename = "CellTowers1.txt";// userScanner.nextLine();
		File file = new File(filename);

		try {
			scanner = new Scanner(file);
		} // end try
		catch (FileNotFoundException fe) {
			System.out.println("Can't open input file\n");
			return null; // array of 0 elements
		} // end catch
		return scanner;
	}

	// David
	// It writes the graph to the new text file
	public static void writeFile(MinimumSpanningTree<CellTower> m1) {
		String wf;
		System.out.print("Would you like to write to a file? (Y/y): ");
		wf = userScanner.next();
		userScanner.nextLine();

		if (wf.equals("y") || wf.equals("Y")) {
			System.out.print("What file would you like to write to? ");
			String fileName = userScanner.next();
			userScanner.nextLine();

			try {
				m1.writeToFile(new PrintStream(new FileOutputStream(fileName)));
			} catch (FileNotFoundException e) {
				System.err.println("Unable to write to file");
			}
		}
	}
   //David
	public static void writePrimFile(ArrayList<Edge<CellTower>> results) {
		String writeFile = "y";
		System.out.print("\nWhat filename would you like to write to? ");
		String fileName = userScanner.next();
		userScanner.nextLine();

		while (writeFile.equalsIgnoreCase("Y")) {
			try {

				PrintStream write = new PrintStream(new FileOutputStream(fileName));

				for (int i = 0; i < results.size(); ++i) {
					write.println(results.get(i));
				}
				write.close();

				writeFile = "n";
			}

			catch (FileNotFoundException e) {
				System.err.println("Cannot open file.");
				System.out.print("Would you like to write to a different file? (Y/y) ");
				writeFile = userScanner.next();
				userScanner.nextLine();
			}
		}
	}

	//Yadukumari
	/*
	 * It replace the existing file with new file 
	 */
	public static void replaceInputFile(MinimumSpanningTree<CellTower> m1, String fname) {
		String writeFile = "y";
		Pair<Vertex<CellTower>, Double> singleEdge;
		Iterator<Entry<CellTower, Vertex<CellTower>>> vertexIter;
		Iterator<Entry<CellTower, Pair<Vertex<CellTower>, Double>>> edgeIter;
		ArrayList<String> comp = new ArrayList<String>();
		int count = 0;
		String var1 = null,var2 = null;
		while (writeFile.equalsIgnoreCase("Y")) {
			try {
				PrintStream write = new PrintStream(new FileOutputStream(fname));
				Vertex<CellTower> source = null;

				// Find starting vertex
				while (count < m1.vertexSet.size()) {
					for (vertexIter = m1.vertexSet.entrySet().iterator(); vertexIter.hasNext();) {
						source = vertexIter.next().getValue();

						for (edgeIter = source.adjList.entrySet().iterator(); edgeIter.hasNext();) {
							singleEdge = edgeIter.next().getValue();
							if (singleEdge == null) {
								// source = singleEdge.first;
								continue;
							}
							Vertex<CellTower> dest = singleEdge.first;
							double weight = singleEdge.second;
							var1 = source.data+" "+dest.data;
							var2 = dest.data+" "+source.data;
							if(comp.contains(var1)||comp.contains(var2)){
								continue;
							}
							comp.add(var1);
							write.println(source.data + " " + dest.data + " " + weight);
						}
						count++;
					}
					
					writeFile = "n";
					write.close();
				}
			}

			catch (FileNotFoundException e) {
				System.err.println("Cannot open file.");
				System.out.print("Would you like to write to a different file? (Y/y) ");
				writeFile = userScanner.next();
				userScanner.nextLine();
			}
		}
	}

}
