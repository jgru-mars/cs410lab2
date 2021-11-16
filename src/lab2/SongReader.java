package lab2;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * This is the class that reads through txt file and throws all the errors and exceptions that it runs into while obtaining the notes
 * 
 */
public class SongReader {

	// method to read a song file in bell notes
	public static List<BellNote> readSong(String filename) {
		// initialize the empty song to be added to
		List<BellNote> song = new ArrayList<>();

		// initialize length to null so it may be replaced
		NoteLength length = null;

		// initialize variables for scanner
		String nextLine;
		String[] splitLine;
		int lineNum = 0;

		// open a scanner 
		try (Scanner betterThanBR = new Scanner(new File(filename))) {
			while (betterThanBR.hasNextLine()) {
				nextLine = betterThanBR.nextLine();
				// ignore empty lines
				if (!nextLine.isEmpty()) {
					lineNum++;
					// get note and length separated
					splitLine = nextLine.split(" ");

					// Account for flat notation by reassigning them to sharps
					// got this from Jake
					String note = null;
					switch (splitLine[0].toString()) {
					case "B0F":
						note = "A0S";
						break;
					case "D0F":
						note = "C0S";
						break;
					case "E0F":
						note = "D0S";
						break;
					case "G0F":
						note = "F0S";
						break;

					// can't use find and replace
					case "A1F":
						note = "G0S";
						break;

					case "B1F":
						note = "A1S";
						break;
					case "D1F":
						note = "C1S";
						break;
					case "E1F":
						note = "D1S";
						break;
					case "G1F":
						note = "F1S";
						break;

					// can't use find and replace
					case "A2F":
						note = "G1S";
						break;

					case "B2F":
						note = "A2S";
						break;
					case "D2F":
						note = "C2S";
						break;
					case "E2F":
						note = "D2S";
						break;
					case "G2F":
						note = "F2S";
						break;

					// can't use find and replace
					case "A3F":
						note = "G2S";
						break;

					case "B3F":
						note = "A3S";
						break;
					case "D3F":
						note = "C3S";
						break;
					case "E3F":
						note = "D3S";
						break;
					case "G3F":
						note = "F3S";
						break;

					// can't use find and replace
					case "A4F":
						note = "G3S";
						break;

					case "B4F":
						note = "A4S";
						break;
					case "D4F":
						note = "C4S";
						break;
					case "E4F":
						note = "D4S";
						break;
					case "G4F":
						note = "F4S";
						break;

					// can't use find and replace
					case "A5F":
						note = "G4S";
						break;

					case "B5F":
						note = "A5S";
						break;
					case "D5F":
						note = "C5S";
						break;
					case "E5F":
						note = "D5S";
						break;
					case "G5F":
						note = "F5S";
						break;

					// can't use find and replace
					case "A6F":
						note = "G5S";
						break;

					case "B6F":
						note = "A6S";
						break;
					case "D6F":
						note = "C6S";
						break;
					case "E6F":
						note = "D6S";
						break;
					case "G6F":
						note = "F6S";
						break;

					// can't use find and replace
					case "A7F":
						note = "G6S";
						break;

					case "B7F":
						note = "A7S";
						break;
					case "D7F":
						note = "C7S";
						break;
					case "E7F":
						note = "D7S";
						break;
					case "G7F":
						note = "F7S";
						break;

					case "A8F":
						note = "G7S";
						break;

					case "B8F":
						note = "A8S";
						break;

					default:
						note = splitLine[0].toString();
						break;
					}

					/////////////////////////////////////////
					// FIRST SWTICH ENDS SECOND SWITCH BEGINS
					/////////////////////////////////////////
					// Get note length in terms of a NoteLength variable
					try {
						switch (splitLine[1].toString()) {
						case "1":
							length = NoteLength.WHOLE;
							break;
						case "2":
							length = NoteLength.HALF;
							break;
						case "4":
							length = NoteLength.QUARTER;
							break;
						case "8":
							length = NoteLength.EIGTH;
							break;
						case "16":
							length = NoteLength.SIXTEENTH;
							break;

						default: 
							//exception for note length being wrong
							throw new InvalidNoteLengthException(
									"Invalid note length at line " + lineNum + " in " + filename);
						}
						// add a new song with the corrected note string and the note length
						song.add(new BellNote(Note.valueOf(note), length));
					}
					// exception for note length being wrong
					catch (InvalidNoteLengthException e1) {
						System.err.println("Invalid note length at line " + lineNum + " in " + filename);

					// exception for note being wrong
					} catch (Exception e) {
						System.err.println("Invalid note at line " + lineNum + " in " + filename);
					}
				}

			}
			// if empty txt file
			if (song.isEmpty()) {
				System.err.println("WARNING: This song file is empty and will not play any sound.");
			}
			// return the song
			return song;
		} catch (IOException e) {
			// catch for file not found
			System.err.println("ERROR: Invalid File Path");

			// if the file is not found, set song invalid and return null for the list
			return null;
		}
	}
}

/**
 * exception class for invalid note length that is used in the above class a lot
 */
class InvalidNoteLengthException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3340958657691481436L;

	InvalidNoteLengthException(String message) {
		super(message);
	}
}
