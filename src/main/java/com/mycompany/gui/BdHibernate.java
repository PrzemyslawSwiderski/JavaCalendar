/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Przemyslaw Swiderski, Adam Gorazda
 */
public class BdHibernate
{

    public static void dodajZdarzenie(String data, Integer godzina, Integer minuta, String nazwa, String miejsce, Integer przypomnienie)
    {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date d = null;
        try
        {
            d = formatter.parse(data);

        } catch (ParseException ex)
        {
            Logger.getLogger(BdHibernate.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try
        {
            tx = session.beginTransaction();
            Zdarzenie zd = new Zdarzenie(d, godzina, minuta, nazwa, miejsce, przypomnienie);
            session.save(zd);
            tx.commit();
        } catch (HibernateException e)
        {
            if (tx != null)
            {
                tx.rollback();
            }
            e.printStackTrace();
        } finally
        {
            session.close();
        }
    }

    public static void czysc()
    {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try
        {
            session.createSQLQuery("truncate table Zdarzenie").executeUpdate();
        } catch (HibernateException e)
        {
            e.printStackTrace();
        } finally
        {
            session.close();
        }
    }

    public static TableModel getTableModel()
    {
        Session session = null;
        Vector<String> tableHeaders = new Vector<String>();
        Vector tableData = new Vector();

        try
        {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query q = session.createQuery("from Zdarzenie");
            List resultList = q.list();
            tableHeaders.add("Id");
            tableHeaders.add("Data");
            tableHeaders.add("Godzina");
            tableHeaders.add("Minuta");
            tableHeaders.add("Nazwa");
            tableHeaders.add("Miejsce");
            tableHeaders.add("Przypomnienie");

            for (Object o : resultList)
            {
                Zdarzenie z = (Zdarzenie) o;
                Vector<Object> oneRow = new Vector<Object>();

                oneRow.add(z.getIdZdarzenie());
                oneRow.add(z.getData());
                oneRow.add(z.getGodzina());
                oneRow.add(z.getMinuta());
                oneRow.add(z.getNazwa());
                oneRow.add(z.getMiejsce());
                oneRow.add(z.getPrzypomnienie());

                tableData.add(oneRow);
            }

            session.getTransaction().commit();
        } catch (HibernateException he)
        {
            he.printStackTrace();
        } finally
        {
            session.close();
        }
        return (new DefaultTableModel(tableData, tableHeaders));
    }

    public static void usunStarsze(String data) throws ParseException
    {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date d = null;
        d = formatter.parse(data);
        Session session = null;
        try
        {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query q = session.createQuery("from Zdarzenie");
            List resultList = q.list();
            for (Object o : resultList)
            {
                Zdarzenie z = (Zdarzenie) o;
                if (z.getData().compareTo(d) == -1)
                {
                    session.delete(o);
                }
            }

            session.getTransaction().commit();
        } catch (HibernateException he)
        {
            he.printStackTrace();
        } finally
        {
            session.close();
        }

    }

    public static void usunZdarzenie(Integer id)
    {
        Session session = null;
        try
        {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query q = session.createQuery("from Zdarzenie");
            List resultList = q.list();
            for (Object o : resultList)
            {
                Zdarzenie zdarz = (Zdarzenie) o;
                if (zdarz.getIdZdarzenie() == id)
                {
                    session.delete(o);
                }
            }
            session.getTransaction().commit();
        } catch (HibernateException he)
        {
            he.printStackTrace();
        } finally
        {
            session.close();
        }

    }

    public static void serializujXML()
    {
        Session session = null;
        try
        {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query q = session.createQuery("from Zdarzenie");
            List resultList = q.list();
            XMLSerializer.zapisz(resultList, "xml/resultset.xml");
            session.getTransaction().commit();
        } catch (HibernateException he)
        {
            he.printStackTrace();
        } catch (Exception ex)
        {
            Logger.getLogger(BdHibernate.class.getName()).log(Level.SEVERE, null, ex);
        } finally
        {
            session.close();
        }

    }

    public static void deserializujXML()
    {
        czysc();
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try
        {
            tx = session.beginTransaction();
            List resultList = (List) XMLSerializer.czytaj("xml/resultset.xml");
            for (Object o : resultList)
            {
                Zdarzenie zdarz = (Zdarzenie) o;
                session.save(zdarz);
            }
            tx.commit();
        } catch (HibernateException e)
        {
            if (tx != null)
            {
                tx.rollback();
            }
            e.printStackTrace();
        } catch (Exception ex)
        {
            Logger.getLogger(BdHibernate.class.getName()).log(Level.SEVERE, null, ex);
        } finally
        {
            session.close();
        }

    }

    public static void zapisCSV()
    {

        String COMMA_DELIMITER = ",";
        String NEW_LINE_SEPARATOR = "\n";

        String FILE_HEADER = "id,data,godzina,minuta,nazwa,miejsce,przypomnienie";

        List<Zdarzenie> zdarzenia = new ArrayList<Zdarzenie>();

        Session session = null;
        try
        {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query q = session.createQuery("from Zdarzenie");
            List resultList = q.list();
            for (Object o : resultList)
            {
                Zdarzenie zdarz = (Zdarzenie) o;
                zdarzenia.add(zdarz);
            }
            session.getTransaction().commit();
        } catch (HibernateException he)
        {
            he.printStackTrace();
        } finally
        {
            session.close();
        }

        FileWriter fileWriter = null;

        try
        {
            fileWriter = new FileWriter("csv/zdarzenia.txt");

            fileWriter.append(FILE_HEADER);

            fileWriter.append(NEW_LINE_SEPARATOR);

            for (Zdarzenie z : zdarzenia)
            {
                fileWriter.append(String.valueOf(z.getIdZdarzenie()));
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(z.getData().toString());
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(String.valueOf(z.getGodzina()));
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(String.valueOf(z.getMinuta()));
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(z.getNazwa());
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(z.getMiejsce());
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(String.valueOf(z.getPrzypomnienie()));
                fileWriter.append(NEW_LINE_SEPARATOR);
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        } finally
        {

            try
            {
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e)
            {
                e.printStackTrace();
            }

        }
    }

    public static void czytajCSV()
    {
        String COMMA_DELIMITER = ",";

        BufferedReader fileReader = null;

        try
        {

            List<Zdarzenie> zdarzenia = new ArrayList<Zdarzenie>();

            String line = "";

            fileReader = new BufferedReader(new FileReader("csv/zdarzenia.txt"));

            fileReader.readLine();

            while ((line = fileReader.readLine()) != null)
            {
                String[] tokens = line.split(COMMA_DELIMITER);
                if (tokens.length > 0)
                {
                    Zdarzenie zdarzenie = new Zdarzenie(tokens[1], tokens[2], tokens[3], tokens[4], tokens[5], tokens[6]);
                    zdarzenia.add(zdarzenie);
                }
            }

            czysc();
            
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = null;
            try
            {
                tx = session.beginTransaction();
                for (Object o : zdarzenia)
                {
                    Zdarzenie zdarz = (Zdarzenie) o;
                    session.save(zdarz);
                }
                tx.commit();
            } catch (HibernateException e)
            {
                if (tx != null)
                {
                    tx.rollback();
                }
                e.printStackTrace();
            } catch (Exception ex)
            {
                Logger.getLogger(BdHibernate.class.getName()).log(Level.SEVERE, null, ex);
            } finally
            {
                session.close();
            }

        } catch (FileNotFoundException ex)
        {
            Logger.getLogger(BdHibernate.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex)
        {
            Logger.getLogger(BdHibernate.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
