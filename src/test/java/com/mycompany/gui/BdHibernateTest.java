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
public class BdHibernateTest
{

    public BdHibernateTest()
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
     * Test of dodajZdarzenie method, of class BdHibernate.
     */
    @Test
    public void testDodajZdarzenie()
    {
        System.out.println("dodajZdarzenie");
        BdHibernate instance = new BdHibernate();
        instance.dodajZdarzenie("2001-12-05", 20, 12, "sdasfaaa", "dsadasd", 0);
    }

    @Test
    public void testCzysc()
    {
        System.out.println("czyscZdarzenia");
        BdHibernate instance = new BdHibernate();
        instance.czysc();
    }

    @Test
    public void testZapiszCsv()
    {
        System.out.println("zapiszCsv");
        BdHibernate instance = new BdHibernate();
        instance.zapisCSV();
    }

    @Test
    public void testCzytajCsv()
    {
        System.out.println("czytajCsv");
        BdHibernate instance = new BdHibernate();
        instance.czytajCSV();
    }

}
