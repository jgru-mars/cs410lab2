package lab2;

import lab2.BellNote;
import lab2.Conductor;
import lab2.Member;
import lab2.Note;
import lab2.Song;

/**
 * This class stores the notes and the lengths of the notes
 * 
 **/
public class BellNote {
	final Note note;
	final NoteLength length;

	/**
	 * 
	 * This is the constructor for the BellNote right above. The notes are the like
	 * the A8 and C5S found in the Note class The length is how long the notes are
	 * 
	 **/
	BellNote(Note note, NoteLength length) {
		this.note = note;
		this.length = length;
	}
}
