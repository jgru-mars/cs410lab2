package lab2wrong;

import javax.sound.sampled.SourceDataLine;

import lab2wrong.Conductor;
import lab2wrong.Member;
import lab2wrong.Note;

import javax.sound.sampled.AudioFileFormat;

public class Member implements Runnable {
    
    private final Note note;
    private final Thread t;
   // private Notelength length;
    private volatile boolean running;
    private boolean myTurn;
    private NoteLength length;
    private SourceDataLine line; //added
    

    Member(Note note, SourceDataLine line) {
        this.note = note;
        this.line = line; // added
        t = new Thread(this, note.name()); //
        t.start(); // starts the threads
        myTurn = false;
    }

    public void stopMember() { //
        running = false;
    }

    public void giveTurn(NoteLength length) {
        synchronized (this) {
            if (myTurn) {
                throw new IllegalStateException("Attempt to give a turn to a player who's hasn't completed the current turn");
            }
            myTurn = true;
           // this.length = length;
            notify();
            while (myTurn) {
                try {
                    wait();
                } catch (InterruptedException ignored) {}
            }
        }
    }

    public void run() {
        running = true;
        synchronized (this) {
            do {
                // Wait for my turn
                while (!myTurn) {
                    try {
                        wait();
                    } catch (InterruptedException ignored) {}
                }
                // My turn!
                playNote(); // added
      
                // Done, complete turn and wakeup the waiting process
                myTurn = false;
                length = null;
                notify();
            } while (running);
        }
    }

    private void playNote() { // from Tone.java
    	final int ms = Math.min(this.length.timeMs(), Note.MEASURE_LENGTH_SEC * 1000);
    	final int length = Note.SAMPLE_RATE * ms / 1000;
    	line.write(this.note.sample(), 0, length);
    	line.write(Note.REST.sample(), 0, 50);
    	System.out.println("playing note");
    	    }
    
    
	}

