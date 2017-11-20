/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uiowa.cs.similarity;

import java.util.HashMap;

/**
 *
 * @author User
 */
public interface GenericVector<keyT, valT> {
    public HashMap<keyT, valT> mapForOneWord(keyT kt);
}
