package com.mycompany.gui;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;


public class Dzwiek
{

	private static int DEFAULT_EXTERNAL_BUFFER_SIZE = 128000;
	private String nazwaPliku;
	private File plik;
	private double glosnosc;
	 
	public Dzwiek(String _nazwaPliku, double g)
	{		
		nazwaPliku = _nazwaPliku;
		plik = new File(nazwaPliku);
		glosnosc = g;
	}
	
	public void odtworzDzwiek(int ileRazy, boolean wTle)
	{
		if (wTle)
		{
			OdtwarzanieWtle watek = new OdtwarzanieWtle(this, ileRazy);
			watek.start();
			return;
		}
		
		for (int i=0; i<ileRazy; i++)
		{
		
		AudioInputStream strumienAudio;
		try {
			strumienAudio = AudioSystem.getAudioInputStream(plik);
		} catch (UnsupportedAudioFileException e1) {
			e1.printStackTrace();
			return;
		} catch (IOException e1) {
			e1.printStackTrace();
			return;
		}
	
		AudioFormat	audioFormat = strumienAudio.getFormat();
		SourceDataLine	line = getSourceDataLine(null, audioFormat, AudioSystem.NOT_SPECIFIED);
		
		DataLine.Info info = null;
		    //info = new DataLine.Info(Clip.class, sound.getFormat());
		info = new DataLine.Info(Clip.class, audioFormat);
		    
		    
		FloatControl gainControl = (FloatControl)line.getControl(FloatControl.Type.MASTER_GAIN);
		float dB = (float)(Math.log(glosnosc)/Math.log(10.0)*20.0);
		gainControl.setValue(dB);
		
		line.start();
		
		int	nBytesRead = 0;
		byte[]	abData = new byte[DEFAULT_EXTERNAL_BUFFER_SIZE];
		while (nBytesRead != -1)
		{
			try
			{
				nBytesRead = strumienAudio.read(abData, 0, abData.length);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			if (nBytesRead >= 0) line.write(abData, 0, nBytesRead);
		}
		line.drain();
		line.close();
		}		
	}
	
	private static SourceDataLine getSourceDataLine(String strMixerName,
							AudioFormat audioFormat, int nBufferSize)
	{
		SourceDataLine	line = null;
		DataLine.Info	info = new DataLine.Info(SourceDataLine.class,
							 audioFormat, nBufferSize);
		try
		{			
			line = (SourceDataLine) AudioSystem.getLine(info);			
			line.open(audioFormat, nBufferSize);
		}
		catch (LineUnavailableException e)
		{
		}
		catch (Exception e)
		{
		}
		return line;
	}
	
	static class OdtwarzanieWtle extends Thread
	{
		Dzwiek dz;
		int ile;
		public OdtwarzanieWtle(Dzwiek d, int ileRazy)
		{
			dz = d;
			ile = ileRazy;
		}
		public void run()
		{
			dz.odtworzDzwiek(ile, false);
		}
	}
}