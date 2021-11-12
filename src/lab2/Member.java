package lab2;


import javax.sound.sampled.SourceDataLine;
import lab2.BellNote;
import lab2.Conductor;

import lab2.Note;

/**
 * @desc A thread which is assigned a note and a place to play the note. It is
 *       told how long to play the note by the conductor before it is required
 *       to play. This is modeled around a woman's 88 sons all named Billy. Is
 *       that relevant... no. Is that fun... yes.
 * 
 * @author Jake Grosse w/ input from code by Nate Williams
 *
 */
public class Member implements Runnable {

	// instance variables
	private final Thread t;
	private final Note note;
	private final SourceDataLine line;

	// mutable instance variable to allow for changing length of play time
	private NoteLength length;

	// variable tells the thread that it is allowed to make noise with it's bell.
	// Just a fun name.
	private boolean myTurn;

	// is the thread running?
	private volatile boolean readyToPlay;

	/**
	 * @desc Constructor for a Bellman, takes a note between A0 and C8 and a
	 *       dataline to play the note into.
	 * 
	 * @param note note frequency from an enumeration in the range of A0 to C8
	 * @param line a sound output for java to compile the song before playing
	 */
	protected Member(Note note, SourceDataLine line) {
		this.t = new Thread(this, note.toString());
		this.t.setDaemon(true); // idea from Jaden, kills thread when it is done running to prevent initial
								// noise when playing one song after another

		this.note = note;
		this.line = line;
		this.length = null;
		this.myTurn = false;

		this.t.start();
	}

	/**
	 * @desc What the thread executes from the time it starts to the time it
	 *       finishes. Waits until it has a turn. When it does, it plays the note.
	 *       Then it gives up its turn and notifies the next thread.
	 */
	@Override
	public void run() {
		// running
		readyToPlay = true;

		// acquire lock
		synchronized (this) {
			// wait while it's not this bellman's turn
			while (readyToPlay) {
				while (!myTurn) {
					try {
						wait();
					} catch (InterruptedException ignored) {
						System.out.println("Billy " + this.t.getName() + " has been ignored.");
					}
				}

				// MOM SAID IT'S MY TURN TO PLAY
				playNote();

				// Mom told me to stop playing....
				myTurn = false;
				length = null;
				notify();
			}
		}
	}

	/**
	 * @desc Informs the thread that it should play and provides the length that it
	 *       should be played.
	 * 
	 * @param len the length of the note which should be played
	 */
	public synchronized void yourTurn(NoteLength len) {
		// if already his turn then throw an error because he can't have two
		if (myTurn) {
			throw new IllegalStateException(
					"Billy " + this.t.getName() + " hasn't finished his turn yet, he can't have another one!");
		}

		// grab passed in length to play note
		this.length = len;
		// can play now
		myTurn = true;

		// notify the thread that is running this method
		notify();

		// wait for it to finish playing
		while (myTurn) {
			try {
				wait();
			} catch (InterruptedException ignored) {
				System.out.println("Billy " + this.t.getName() + " has been ignored.");
			}
		}
	}

	/**
	 * @desc Informs the thread that is should choose to die.
	 */
	public void stopMember() {
		readyToPlay = false;
	}

	// Just the playnote method from conductor which has been adapted to fit the
	// member class since they will be the ones playing the notes
	private void playNote() {
		// ms is either note length or 10 seconds
		final int ms = Math.min(this.length.timeMs(), Note.MEASURE_LENGTH_SEC * 10000);
		// length is
		final int length = Note.SAMPLE_RATE * ms / 1000;
		line.write(note.sample(), 0, length);
		line.write(Note.REST.sample(), 0, 50);
	}

}
