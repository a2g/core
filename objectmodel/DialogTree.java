package com.github.a2g.core.objectmodel;


import java.util.LinkedList;
import java.util.List;

/**
 * Represents a single branch in the dialog tree - the current branch.
 * This contains sub branches, which are lines of dialog with the 
 * associated subBranchId these take you to.
 */
public class DialogTree {
    private List<Integer> subBranches;
    private List<String> linesOfDialog;
		
    DialogTree() {
        subBranches = new LinkedList<Integer>();
        linesOfDialog = new LinkedList<String>();
    }

    void clear() {
    	subBranches.clear();
        linesOfDialog.clear();
    }
	
    public void addSubBranch(int branchId, String text) {
    	subBranches.add(branchId);
        linesOfDialog.add(text);
    }
	
    public List<Integer> getSubBranchIds() {
        return subBranches;
    }
	
    public List<String> getLinesOfDialog() {
        return linesOfDialog;
    }
}
