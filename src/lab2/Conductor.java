package lab2;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

import lab2.BellNote;
import lab2.Conductor;
import lab2.Member;
import lab2.Note;
import lab2.Song;

public class Conductor {

	/**
	 * This is the Conductor class. It controls the Member threads that play the
	 * different notes, opens the txt file, and reads it and starts to implement all
	 * the other classes. This is my main class and the one that I will run the
	 * program through.
	 * 
	 */

	private static Song song;

	public Conductor() {
		this.af = new AudioFormat(Note.SAMPLE_RATE, 8, 1, true, false);
	}

	private AudioFormat af;

	/**
	 * This next method will open the txt files and assign the notes to the
	 * different Members. There will be only one Member for each note
	 */

	void playSong(Song song) throws LineUnavailableException {
		// this is the txt file reader that I had for the Tone class
		if (((Song) song).isValid()) {
			try (final SourceDataLine line = AudioSystem.getSourceDataLine(af)) {
				line.open();
				line.start();
				final Member[] members = new Member[Note.values().length]; // added
				for (Note note : Note.values()) {
					members[note.ordinal()] = new Member(note, line);
				}

				for (BellNote bn : song.getBellNotes()) {
					Note note = bn.note;
					NoteLength len = bn.length;
					members[bn.note.ordinal()].giveTurn(len); // added
				}

				for (Member a : members) {
					a.stopMember();
				}
				line.drain();
				System.exit(0); // added this so it would get out of its loop, might not be needed

			} catch (LineUnavailableException e) {
				// the AF for the dataline doesn't work, exit the program
				System.err.println("Bad song");
				System.exit(0);
			}
		} else {
			// print that the song is invalid if it was not valid
			System.out.println(
					"This song file is not valid. Choose another song or fix the errors in the file and restart the program.");
		}
	}

	/**
	 * The main method which is needed for the program to run, this is also where
	 * the txt file that the program will run is passed in Ant After it gets the
	 * name of the file, this method will play the song and call the method right
	 * above this for reading and playing the song which is connected to the other
	 * classes like notes
	 */

	public static void main(String[] args) throws Exception {
		Song t = new Song("Little Lamb", args[0]);
		Conductor c = new Conductor();
		c.playSong(t);
		System.out.println(t.getBellNotes());
		// program does not run except in Ant, so works with Ant Run -Dsong
		// src/songs/lamb.txt
	}

}
