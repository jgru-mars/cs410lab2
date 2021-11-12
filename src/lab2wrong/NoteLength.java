package lab2wrong;

import lab2wrong.BellNote;
import lab2wrong.Conductor;
import lab2wrong.Member;
import lab2wrong.Note;
import lab2wrong.Song;


public enum NoteLength {
    WHOLE(1.0f),
    HALF(0.5f),
    QUARTER(0.25f),
    EIGTH(0.125f);
	

    private final int timeMs;

    private NoteLength(float length) {
        timeMs = (int)(length * Note.MEASURE_LENGTH_SEC * 1000);
    }

    public int timeMs() {
        return timeMs;
    }
}
