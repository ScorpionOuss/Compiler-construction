package fr.ensimag.deca.tree;

/**
 * Function that takes a tree as argument.
 * 
 * @see fr.ensimag.deca.tree.Tree#iter(TreeFunction)
 * 
 * @author gl16
 * @date 01/01/2021
 */
public interface TreeFunction {
    void apply(Tree t);
}
