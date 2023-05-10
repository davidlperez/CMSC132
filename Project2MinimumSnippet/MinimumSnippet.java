import java.util.List;
import java.util.ArrayList;

/**
 * This class returns the smallest snippet of the document that contains all the
 * terms and any accompanying information
 * 
 * @author davidperez
 *
 */
public class MinimumSnippet {

	private List<String> document;
	private List<String> terms;
	private int startingPos;
	private int endingPos;
	private ArrayList<String> minSnip;
	private int minSize;

	/**
	 * The constructor iterates through the document and creates a new ArrayList
	 * with the minimum snippet containing all the terms
	 * 
	 * @param document of Strings
	 * @param terms    to search for
	 */
	public MinimumSnippet(Iterable<String> document, List<String> terms) {
		this.terms = terms;
		this.document = new ArrayList<String>();

		minSnip = new ArrayList<String>();

		ArrayList<ArrayList<String>> possibleFSnip = new ArrayList<ArrayList<String>>();
		ArrayList<ArrayList<String>> possibleRSnip = new ArrayList<ArrayList<String>>();
		ArrayList<String> tempSnip = new ArrayList<>();

		ArrayList<Integer> possibleFStartingPos = new ArrayList<Integer>();
		ArrayList<Integer> possibleFEndingPos = new ArrayList<Integer>();
		ArrayList<Integer> possibleRStartingPos = new ArrayList<Integer>();
		ArrayList<Integer> possibleREndingPos = new ArrayList<Integer>();

		for (String s : document) {
			this.document.add(s);
		}
		if (this.terms.isEmpty()) {
			throw new IllegalArgumentException();
		}
		if (foundAllTerms() == true) {
			boolean add = false;
			for (int i = 0; i < this.document.size(); i++) {
				String s = this.document.get(i);
				if (terms.contains(s) && !add) {
					add = true;
					tempSnip.clear();
				}
				if (add && !(tempSnip.containsAll(terms))) {
					tempSnip.add(s);
					if (tempSnip.size() == 1) {
						possibleFStartingPos.add(i);
					}
					if (tempSnip.containsAll(terms)) {
						possibleFEndingPos.add(i);
					}
					if (tempSnip.containsAll(terms)) {
						add = false;
						possibleFSnip.add(new ArrayList<>(tempSnip));
					}
				}
			}
			add = false;
			for (int i = this.document.size() - 1; i >= 0; i--) {
				String s = this.document.get(i);
				if (terms.contains(s) && !add) {
					add = true;
					tempSnip.clear();
				}
				if (add && !(tempSnip.containsAll(terms))) {
					tempSnip.add(s);
					if (tempSnip.size() == 1) {
						possibleREndingPos.add(i);
					}
					if (tempSnip.containsAll(terms)) {
						possibleRStartingPos.add(i);
					}
					if (tempSnip.containsAll(terms)) {
						add = false;
						possibleRSnip.add(new ArrayList<>(reverseArrayList(tempSnip)));
					}
				}
			}
			minSize = this.document.size();
			tempSnip.clear();
			for (int i = 0; i < possibleRSnip.size(); i++) {
				int len = possibleRSnip.get(i).size() - possibleFSnip.get(i).size();
				if (len < 0) {
					tempSnip = new ArrayList<>(possibleRSnip.get(i));
				} else {
					tempSnip = new ArrayList<>(possibleFSnip.get(i));
				}
				if (tempSnip.size() < minSize) {
					minSnip = tempSnip;
					minSize = tempSnip.size();
				}

			}

			int fDifference = this.document.size() - 1, rDifference = this.document.size();
			int fStart = 0, fEnd = 0, rStart = 0, rEnd = 0;
			for (int i = 0; i < possibleFEndingPos.size(); i++) {
				if (possibleFEndingPos.get(i) - possibleFStartingPos.get(i) <= fDifference) {
					fStart = possibleFStartingPos.get(i);
					fEnd = possibleFEndingPos.get(i);
					fDifference = possibleFEndingPos.get(i) - possibleFStartingPos.get(i);
				}
			}
			for (int i = 0; i < possibleRStartingPos.size(); i++) {
				if (possibleREndingPos.get(i) - possibleRStartingPos.get(i) <= rDifference) {
					rStart = possibleRStartingPos.get(i);
					rEnd = possibleREndingPos.get(i);
					rDifference = possibleREndingPos.get(i) - possibleRStartingPos.get(i);
				}
			}
			if (fEnd - fStart < rEnd - rStart) {
				startingPos = fStart;
				endingPos = fEnd;
			} else {
				startingPos = rStart;
				endingPos = rEnd;
			}
		}
	}

	/**
	 * This method takes the reversed ArrayList from iterating backwards and
	 * reverses it so it's forward
	 * 
	 * @param forwardList
	 * @return reverse of forwardList
	 */
	private ArrayList<String> reverseArrayList(ArrayList<String> forwardList) {
		ArrayList<String> revList = new ArrayList<String>();
		for (int i = forwardList.size() - 1; i >= 0; i--) {
			revList.add(forwardList.get(i));
		}
		return revList;
	}

	/**
	 * Determines if the document contains all the terms
	 * 
	 * @return whether or not all the terms are in the document
	 */
	public boolean foundAllTerms() {
		if (document.containsAll(terms)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Finds the position of the first term
	 * 
	 * @return index of first term in snippet
	 */
	public int getStartingPos() {
		if (foundAllTerms() != true) {
			throw new IllegalArgumentException();
		} else {
			return startingPos;
		}
	}

	/**
	 * Finds the position of the last term
	 * @return index of last term in snippet
	 */
	public int getEndingPos() {
		if (foundAllTerms() != true) {
			throw new IllegalArgumentException();
		} else {
			return endingPos;
		}
	}

	/**
	 * Finds length of snippet
	 * @return length of snippet
	 */
	public int getLength() {
		if (foundAllTerms() != true) {
			throw new IllegalArgumentException();
		} else {
			return minSnip.size();
		}
	}

	/**
	 * Searches through document and returns the index of
	 * a specific term that is included in the snippet
	 * @param index
	 * @return index of specific term in snippet
	 */
	public int getPos(int index) {
		int pos = 0;
		if (foundAllTerms() != true) {
			throw new IllegalArgumentException();
		} else {
			for (int i = startingPos; i <= endingPos; i++) {
				if (terms.get(index).equals(document.get(i))) {
					pos = i;
				}
			}
		}
		return pos;
	}

}
