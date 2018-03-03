/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.List;

/**
 *
 * @author User
 */
public interface ApplyFunction<InT, OutT> {
    List<OutT> apply(InT x);
}
