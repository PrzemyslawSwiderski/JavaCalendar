/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Przemyslaw Swiderski, Adam Gorazda
 */
public class Kalendarz
{

    private Calendar calendar = Calendar.getInstance();

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    public Calendar currentTime = Calendar.getInstance();
    public Calendar przypomnienie = Calendar.getInstance();

    public boolean flaga = false;

    public String getMiesiac()
    {
        String month = "zly";
        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] months = dfs.getMonths();
        if (calendar.get(Calendar.MONTH) >= 0 && calendar.get(Calendar.MONTH) <= 11)
        {
            month = months[calendar.get(Calendar.MONTH)];
        }
        return month;
    }

    public int getIntMiesiac()
    {

        return calendar.get(Calendar.MONTH) + 1;
    }

    public String getData()
    {
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        String formatted = format1.format(calendar.getTime());
        return formatted;
    }

    public String getPrzypomnienie()
    {
        String formatted = dateFormat.format(przypomnienie.getTime());
        return formatted;
    }

    public String getBiezacyCzas()
    {
        String formatted = dateFormat.format(currentTime.getTime());
        return formatted;
    }

    public Date getData2()
    {
        return calendar.getTime();
    }

    public void porownajCzas()
    {
        if (przypomnienie != null && getBiezacyCzas().contentEquals(getPrzypomnienie()) && flaga == true)
        {
            new Dzwiek("01.wav", 1).odtworzDzwiek(1, true);
            flaga = false;
        }
    }

    public String getRok()
    {
        return Integer.toString(calendar.get(Calendar.YEAR));
    }

    public int getliczbaDni()
    {
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    public String getDni()
    {
        return Integer.toString(calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
    }

    public String[] getlistaDni()
    {
        String[] strlist = new String[calendar.getActualMaximum(Calendar.DAY_OF_MONTH)];
        for (int i = 0; i < calendar.getActualMaximum(Calendar.DAY_OF_MONTH); i++)
        {
            strlist[i] = "Dzien: " + (i + 1) + ".";
        }
        return strlist;
    }

    public void nastepnyMiesiac()
    {
        calendar.add(
                Calendar.MONTH, 1);
    }

    public void poprzedniMiesiac()
    {
        calendar.add(
                Calendar.MONTH, -1);
    }

    public void nastepnyDzien()
    {
        calendar.add(
                Calendar.DAY_OF_MONTH, 1);
    }

    public void poprzedniDzien()
    {
        calendar.add(
                Calendar.DAY_OF_MONTH, -1);
    }

    /**
     * @return the dateFormat
     */
    public SimpleDateFormat getDateFormat()
    {
        return dateFormat;
    }

    /**
     * @param dateFormat the dateFormat to set
     */
    public void setDateFormat(SimpleDateFormat dateFormat)
    {
        this.dateFormat = dateFormat;
    }

    /**
     * @return the calendar
     */
    public Calendar getCalendar()
    {
        return calendar;
    }

    /**
     * @param calendar the calendar to set
     */
    public void setCalendar(Calendar calendar)
    {
        this.calendar = calendar;
    }

}
