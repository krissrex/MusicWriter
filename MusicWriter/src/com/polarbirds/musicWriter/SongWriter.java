package com.polarbirds.musicWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class SongWriter {

	public static final void write(ArrayList<Note> notes, int bpm, String filename){
		if (filename.length() <= 0) {
			return;
		}
		
		
		StringBuilder notesBuilder = new StringBuilder("word notes[] = {");
		StringBuilder durationBuilder = new StringBuilder("byte durations[] = {");
		for (int i = 0; i < notes.size(); i++){
			Note n = notes.get(i);
			notesBuilder.append(n.freq);
			durationBuilder.append(n.duration);
			if (i < notes.size()-1){
				notesBuilder.append(", ");
				durationBuilder.append(", ");
			}
		}
		notesBuilder.append("};\n");
		durationBuilder.append("};\n\n");



		try (FileWriter w = new FileWriter(filename+".txt")) {
			w.write(notesBuilder.toString());
			w.write(durationBuilder.toString());
			w.write("int bpm = "+bpm+";\n\n");
			w.write("const int buzzerPin = FILL_ME_IN;\n");
			w.write("const int pauseTime = 0;\n\n");
			w.write("//Function for playback:\n"
					+ " void play(){\n"
					+ "  int quarterTime = 60000/bpm;\n" // 60s : Xb/m = Ys/b; *1000ms FIXME: Change this line to alter minimum tempo.
					+ "  for (int i=0; i < sizeof(durations); i++){\n"
					+ "    int time = quarterTime*durations[i];\n"
					+ "    tone(buzzerPin, notes[i], time);\n"
					+ "    delay(time + pauseTime);\n"
					+ "  }\n"
					+ "}\n\n");
		} catch (IOException e) {
			System.err.println("Failed to write file.");
			e.printStackTrace();
		}
	}
}
