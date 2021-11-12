package lab2;

import java.util.List;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

import lab2.BellNote;
import lab2.Conductor;
import lab2.Member;
import lab2.Note;

import java.util.LinkedList;

public class Conductor  {
  
	private static Song song = null;
	

	
	 public static void main(String[] args) throws Exception {
	        final AudioFormat af =
	            new AudioFormat(Note.SAMPLE_RATE, 8, 1, true, false);
	        Song t = song;
	    }
	 
	 private AudioFormat af;
	    void Song(AudioFormat af) {
	        this.af = af;
	    }
	    
	    /**
		 * This next method will open the txt files and assign the notes to the different Members.
		 * There will be only one Member for each note
		 */
	void playSong(List<BellNote> song) throws LineUnavailableException {
		// this is the txt file reader that I had for the Tone class
		if (((Song) song).isValid()) {
			try (final SourceDataLine line = AudioSystem.getSourceDataLine(af)) {
				line.open();
				line.start();
				final Member[] members = new Member[Note.values().length]; //added
				for (Note note : Note.values()) {
					members[note.ordinal()] = new Member(note, line);
				}
				
				for (BellNote bn: song) {
					//playNote(line, bn);
					Note note = bn.note;
					NoteLength len = bn.length;
					members[bn.ordinal()].giveTurn(len); //added
					}
			
			for (Member a : members) {
			a.stopMember();
		}
			line.drain();
		
		} catch (LineUnavailableException e) {
			// the AF for the dataline doesn't work, exit the program
			System.err.println("Fatal Error: Invalid SourceDataLine");
			System.exit(0);
		}
	} else {
		// print that the song is invalid if it was not valid
		System.out.println(
				"This song file is not valid. Choose another song or fix the errors in the file and restart the program.");
	}
}
}

