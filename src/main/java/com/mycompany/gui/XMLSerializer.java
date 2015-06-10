/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 *
 * @author Przemyslaw Swiderski, Adam Gorazda
 */
public class XMLSerializer
{

    public static void zapisz(Object f, String filename) throws Exception
    {
        XMLEncoder encoder
                = new XMLEncoder(
                        new BufferedOutputStream(
                                new FileOutputStream(filename)));
        encoder.writeObject(f);
        encoder.close();
    }

    public static Object czytaj(String filename) throws Exception
    {
        XMLDecoder decoder
                = new XMLDecoder(new BufferedInputStream(
                                new FileInputStream(filename)));
        Object o = (Object) decoder.readObject();
        decoder.close();
        return o;
    }
}
