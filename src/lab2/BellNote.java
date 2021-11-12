package lab2;

import lab2.BellNote;
import lab2.Conductor;
import lab2.Member;
import lab2.Note;

public class BellNote {
    final Note note;
    final NoteLength length;
    BellNote(Note note, NoteLength length) {
        this.note = note;
        this.length = length;
    }
}
