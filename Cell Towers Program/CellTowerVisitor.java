/*
 * Project Title - Minimum Spanning Tree
 * Group members: Yadukumari Shanthigrama Ramesh, Farid Rahman, David, Xanthia
 * Windows10 and Java compiler 1.8
 */
public class CellTowerVisitor implements Visitor<CellTower> {

	@Override
	public void visit(CellTower cellTower) {
		System.out.println("cellTower = " + cellTower.getCellCityName() + " was visited");
	}

}
