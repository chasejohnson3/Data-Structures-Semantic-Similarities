/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uiowa.cs.similarity;

/**
 *
 * @author User
 */
public interface TopKFunction<InFunc, InVec, InMap>{
    public void topK(InFunc x, InVec vec, InMap map);
}
