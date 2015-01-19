package com.polarbirds.musicWriter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {
	private int octave = 4;
	private int base = 440;
	private int bpm = 128;

	private final static String KEYS = "cCdDefFgGaAh0";
	private final HashMap<Character, Integer> semis = new HashMap<>();

	private final ArrayList<Note> notes = new ArrayList<>();

	private final Scanner sc = new Scanner(System.in);


	public Main(){
		init();
	}

	private void init(){
		for (int i = -9; i < 3; i++){
			semis.put(KEYS.charAt(i+9), Integer.valueOf(i));
		}
	}

	public void start(){
		boolean running = true;
		System.out.println("z/x for octave down/up. q to quit.");
		System.out.println("Notes from c to h:\n\tcCdDefFgGaAh\n\tCaptial letter is a semi up, and 0 means pause.\n");

		while (running){
			try {
				System.out.println("Enter notes (note[duration]): ");
				String in = sc.nextLine();
				boolean shouldRun = true;

				for (int i = 0; i < in.length(); i++)
				{
					int delta = 0;
					String token;

					if (i+1 < in.length())
					{
						Character dur = in.charAt(i+1);
						if (Character.isDigit(dur) && dur != '0')
						{
							token = in.substring(i, i+2);
							delta++;
						} 
						else 
						{
							token = in.substring(i, i+1);
						}
					} else {
						token = in.substring(i, i+1);
					}
					
					if (!parse(token)) {
						shouldRun = false;
					}
					
					i += delta;
				} //end for i

				running = shouldRun;
			} catch (NoSuchElementException ex){
				System.err.println("Wrong input. q to quit.");
			}
		} //end while

		System.out.println("\nAll notes:");
		for (Note n : notes){
			System.out.println(n);
		}
		System.out.print("Print to file (blank to skip): ");
		String file = sc.nextLine().trim();
		SongWriter.write(notes, bpm, file);
		sc.close();
	}


	private boolean parse(String in){
		System.out.println("Parsing: "+in);
		
		if (in.startsWith("q")){
			System.out.println("q found; quitting.");
			return false;
		}
		if (in.startsWith("x")){
			octave++;
			System.out.println("Octave up. "+octave);
			return true;
		}
		if (in.startsWith("z")){
			if (--octave <= 0){
				octave = 0;
			}
			System.out.println("Octave down. "+octave);
			return true;
		}
		
		for (char c : KEYS.toCharArray()){
			if (in.charAt(0) == c){
				Note n = new Note();
				if (c == '0'){
					n.freq = 0;
				} else {
					n.freq = (int)(base*Math.pow(2d, (12d*(octave-4)+semis.get(Character.valueOf(c)))/12d));
				}
				n.duration = (in.length() > 1) ? Integer.parseInt(in.substring(1, 2)) : 1;
				n.octave = octave;
				notes.add(n);
				System.out.println(n);
				return true;
			}
		}
		return true;
	}


	public static void main(String[] args) {
		Main main = new Main();
		main.start();
	}
}
