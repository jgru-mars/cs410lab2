package lab2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.File;
import lab2.BellNote;
import lab2.Conductor;
import lab2.Member;
import lab2.Note;
import lab2.Song;

/**
 * This is the class made for the SongReader class to beter get it ready. Such as validSong.
 * got some of this from Jake, including comments
 * 
 */
public class Song {
	private final String name;
	private final String filePath;
	private List<BellNote> notes = new ArrayList<BellNote>();

	
	private boolean validSong = true;

	Song(String name, String filePath) {
		this.name = name;
		this.filePath = filePath;
		this.notes = SongReader.readSong(filePath);
		validSong = !notes.isEmpty() && notes != null;

	}

	/**
	 * @desc Getter for the name of the song
	 * 
	 * @return The title of the song
	 */
	public String getName() {
		return name;
	}

	/**
	 * @desc Getter for the path of the song
	 * 
	 * @return The relative path of the song
	 */
	public String getRelativePath() {
		return filePath;
	}

	/**
	 * @desc Getter for the list of notes in the song so it may be played.
	 * 
	 * @return The list of bellnotes to be played.
	 */
	public List<BellNote> getBellNotes() {
		return notes;
	}

	/**
	 * @desc Getter for whether the song is valid.
	 * 
	 * @return whether or not the song is valid
	 */
	public boolean isValid() {
		return validSong;
	}
}
