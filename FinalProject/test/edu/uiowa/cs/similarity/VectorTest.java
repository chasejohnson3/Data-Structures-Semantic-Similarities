/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uiowa.cs.similarity;

import java.util.HashMap;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author User
 */
public class VectorTest {
    
    public VectorTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getVector method, of class Vector.
     */
    @Test
    public void testGetVector() {
        System.out.println("getVector");
        Vector instance = null;
        HashMap<String, Integer> expResult = null;
        HashMap<String, Integer> result = instance.getVector();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of containsBaseWord method, of class Vector.
     */
    @Test
    public void testContainsBaseWord() {
        System.out.println("containsBaseWord");
        Vector instance = null;
        boolean expResult = false;
        boolean result = instance.containsBaseWord();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getWord method, of class Vector.
     */
    @Test
    public void testGetWord() {
        System.out.println("getWord");
        Vector instance = null;
        String expResult = "";
        String result = instance.getWord();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMagnitude method, of class Vector.
     */
    @Test
    public void testGetMagnitude() {
        System.out.println("getMagnitude");
        Vector instance = null;
        double expResult = 0.0;
        double result = instance.getMagnitude(instance.getVector());
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of cosineSimilarity method, of class Vector.
     */
    @Test
    public void testCosineSimilarity() {
        System.out.println("cosineSimilarity");
        Vector comparisonVec = null;
        Vector instance = null;
        double expResult = 0.0;
        double result = instance.cosineSimilarity(comparisonVec);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of negEuclideanDist method, of class Vector.
     */
    @Test
    public void testNegEuclideanDist() {
        System.out.println("negEuclideanDist");
        HashMap<String, Integer> comparisonVec = new HashMap<>();
        comparisonVec.put("test1", 1);
        comparisonVec.put("test2", 4);
        comparisonVec.put("test3", 1);
        comparisonVec.put("test4", 0);
        comparisonVec.put("test5", 0);
        comparisonVec.put("test6", 0);
        Vector instance = new Vector();
        HashMap<String, Integer> instanceVec = new HashMap<>();
        instanceVec.put("test1", 3);
        instanceVec.put("test2", 0);
        instanceVec.put("test3", 0);
        instanceVec.put("test4", 1);
        instanceVec.put("test5", 1);
        instanceVec.put("test6", 2);
        instance.setMap(instanceVec);
        
        double expected = -1*5.1961524227;
        double result = instance.negEuclideanDist(comparisonVec);
        assertEquals(expected, result, 0.00001);
    }

    /**
     * Test of negEuclideanDist method, of class Vector.
     */
    @Test
    public void testnormalizedNegEuclideanDist() {
        System.out.println("negEuclideanDist");
        HashMap<String, Integer> comparisonVec = new HashMap<>();
        comparisonVec.put("test1", 1);
        comparisonVec.put("test2", 4);
        comparisonVec.put("test3", 1);
        comparisonVec.put("test4", 0);
        comparisonVec.put("test5", 0);
        comparisonVec.put("test6", 0);
        edu.uiowa.cs.similarity.Vector instance = new edu.uiowa.cs.similarity.Vector();
        HashMap<String, Integer> instanceVec = new HashMap<>();
        instanceVec.put("test1", 3);
        instanceVec.put("test2", 0);
        instanceVec.put("test3", 0);
        instanceVec.put("test4", 1);
        instanceVec.put("test5", 1);
        instanceVec.put("test6", 2);
        instance.setMap(instanceVec);
        
        double expected = -1*1.27861316602;
        double result = instance.normalizedNegEuclideanDist(comparisonVec);
        assertEquals(expected, result, 0.00001);
    }
    
    /**
     * Test of mapForOneWord method, of class Vector.
     */
    @Test
    public void testMapForOneWord() {
        System.out.println("mapForOneWord");
        Vector instance = null;
        HashMap<String, Integer> expResult = null;
        HashMap<String, Integer> result = instance.mapForOneWord();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
