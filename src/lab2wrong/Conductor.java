package lab2wrong;

import java.util.List;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

import lab2wrong.BellNote;
import lab2wrong.Conductor;
import lab2wrong.Member;
import lab2wrong.Note;

import java.util.LinkedList;

public class Conductor  {
  
	private static Song song = null;
	
//	Conductor(Song song) {
//		// make the audioformat suggested, just in a slightly different place.
//		this.af = new AudioFormat(Note.SAMPLE_RATE, 8, 1, true, false);
//		// assign the song
//		Conductor.song = song;
//	}
	
	 public static void main(String[] args) throws Exception {
	        final AudioFormat af =
	            new AudioFormat(Note.SAMPLE_RATE, 8, 1, true, false);
	        Song t = song;
	        
	  
	    }
	 
	 private AudioFormat af;
	    void Song(AudioFormat af) {
	        this.af = af;
	    }

	    
	// taking my read txt file reader thingie from Tone
	void playSong(List<BellNote> song) throws LineUnavailableException {
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
	
	class BellNote {
	    final Note note;
	    final NoteLength length;
	    BellNote(Note note, NoteLength length) {
	        this.note = note;
	        this.length = length;
	    }
		public int ordinal() {
			// TODO Auto-generated method stub
			return 0;
		}
	
	}

	}
