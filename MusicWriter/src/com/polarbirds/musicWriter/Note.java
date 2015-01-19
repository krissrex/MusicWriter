package com.polarbirds.musicWriter;

public class Note {
	public int freq;
	public int duration;
	public int octave = 4;
	
	@Override
	public String toString(){
		if (freq == 0){
			return "Note: pause";
		}
		return "Note: freq:"+freq +"\tduration:"+duration+"\toctave:"+octave;
	}
}
