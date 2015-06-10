/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Przemyslaw Swiderski, Adam Gorazda
 */
public class KalendarzTest
{
    
    public KalendarzTest()
    {
    }
    
    @BeforeClass
    public static void setUpClass()
    {
    }
    
    @AfterClass
    public static void tearDownClass()
    {
    }
    
    @Before
    public void setUp()
    {
    }
    
    @After
    public void tearDown()
    {
    }



    /**
     * Test of getCalendar method, of class Kalendarz.
     */
    @Test
    public void testGetData2()
    {
        System.out.println("getdata");
        Kalendarz instance = new Kalendarz();
        
        System.out.println(instance.getData());
        
        System.out.println(instance.getData2().getHours());
        
        System.out.println(instance.getData2().getMinutes());
        
        System.out.println(instance.getData2().getSeconds());
    }

    
}
