package lab2;

import lab2.BellNote;
import lab2.Conductor;
import lab2.Member;
import lab2.Note;
import lab2.Song;

/**
 * This is the class that stores how long the notes are. At the moment I just
 * have whole, half, quarter, eighth notes, and sixteenth notes
 **/

public enum NoteLength {
	WHOLE(1.0f), HALF(0.5f), QUARTER(0.25f), EIGTH(0.125f), SIXTEENTH(0.0625f);

	private final int timeMs;

	/**
	 * makes the note's length
	 */
	private NoteLength(float length) {
		timeMs = (int) (length * Note.MEASURE_LENGTH_SEC * 1000);
	}

	/**
	 * milliseconds of the note, length of time
	 */
	public int timeMs() {
		return timeMs;
	}
}
