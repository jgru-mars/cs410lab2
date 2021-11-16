package lab2;

import javax.sound.sampled.SourceDataLine;

import lab2.BellNote;
import lab2.Conductor;
import lab2.Member;
import lab2.Note;
import lab2.Song;

/**
 * So This entire class is for the Member. Each Member is assinged one not to
 * play in the song. This class also tells the Member how long to play the note
 * for.
 **/

public class Member implements Runnable {

	private final Note note;
	private final Thread t;
	private volatile boolean running;
	private boolean myTurn;
	private NoteLength length;
	private SourceDataLine line; // added to this from the original

	Member(Note note, SourceDataLine line) {
		this.note = note;
		this.line = line; // added
		t = new Thread(this, note.name()); //
		t.start(); // this starts it all
		myTurn = false;
	}

	/**
	 * This method stops the member thread from running
	 */
	public void stopMember() {
		running = false;
	}

	/**
	 * This method controls the turns with the threads and which thread to play.
	 * 
	 */
	public void giveTurn(NoteLength length) {
		synchronized (this) {
			if (myTurn) {
				throw new IllegalStateException(
						"Attempt to give a turn to a player who's hasn't completed the current turn");
			}
			this.length = length;
			myTurn = true;
			// this.length = length;
			notify();
			while (myTurn) {
				try {
					wait();
				} catch (InterruptedException ignored) {
				}
			}
		}
	}

	/**
	 * This is where the threads are ran. It waits until the program is ready for it
	 * and then it runs the thread When running the thread it will play the note It
	 * will also notify the program when it is done running the thread so a new one
	 * can start
	 */

	public void run() {
		running = true;
		synchronized (this) {
			do {
				// Wait for my turn
				while (!myTurn) {
					try {
						wait();
					} catch (InterruptedException ignored) {
					}
				}
				// My turn!
				playNote(); // added

				// Done, complete turn and wakeup the waiting process
				myTurn = false;
				length = null; // added
				notify();
			} while (running);
		}
	}

	/**
	 * This is the method that the thread will call in order to play the notes
	 */
	private void playNote() { // from Tone.java
		try {
			final int ms = Math.min(this.length.timeMs(), Note.MEASURE_LENGTH_SEC * 1000);
			final int length = Note.SAMPLE_RATE * ms / 1000;
			line.write(this.note.sample(), 0, length);
			line.write(Note.REST.sample(), 0, 50);
			System.out.println("playing note"); // get rid of this after testing
		} catch (Exception Ignored) {
		}
	}

}
