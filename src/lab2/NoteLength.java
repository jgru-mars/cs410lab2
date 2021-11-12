package lab2;

import lab2.BellNote;
import lab2.Conductor;
import lab2.Member;
import lab2.Note;
import lab2.Song;


/*
 * This is the class that stores how long the notes are. At the moment I just 
 * have whole, half, quarter, and eighth notes
 */
public enum NoteLength {
    WHOLE(1.0f),
    HALF(0.5f),
    QUARTER(0.25f),
    EIGTH(0.125f);
	

    private final int timeMs;

    /*
     * makes the note's length
     */
    private NoteLength(float length) {
        timeMs = (int)(length * Note.MEASURE_LENGTH_SEC * 1000);
    }

    /*
     * milliseconds of the note
     */
    public int timeMs() {
        return timeMs;
    }
}
