package ru.crock.app.utils;

import ru.crock.app.tiles.TileDuration;
import ru.crock.app.utils.MidiPlayer;

import javax.sound.midi.*;
import java.util.Arrays;
import java.util.List;

public class NoteGenerator {
    private List<String> extendedNotes;
    private MidiPlayer player;
    public NoteGenerator() throws MidiUnavailableException {
        this.extendedNotes = Arrays.asList("C","C#","D","D#","E","F","F#","G","G#","A","A#","B");
        this.player = new MidiPlayer();
    }

    public void playNote(String note, TileDuration tickDuration) {
        int finalNote = 0,finalInstrument = 0;
        String[] noteParts = note.split("_+");
        finalNote = Integer.parseInt(noteParts[1]) * 12 + extendedNotes.indexOf(noteParts[0]);
        Sequencer sequencer = null;
        try{
            sequencer = MidiSystem.getSequencer();
            sequencer.open();
            Sequence sequence = new Sequence(Sequence.PPQ, 4);
            Track track = sequence.createTrack();

            MidiEvent event = null;

            ShortMessage first = new ShortMessage();
            first.setMessage(192, 1, finalInstrument, 0);
            MidiEvent changeInstrument = new MidiEvent(first, 1);
            track.add(changeInstrument);

            ShortMessage a = new ShortMessage();
            a.setMessage(144, 1, finalNote, 100);
            MidiEvent noteOn = new MidiEvent(a, 1);
            track.add(noteOn);

            ShortMessage b = new ShortMessage();
            b.setMessage(128, 1, finalNote, 100);
            MidiEvent noteOff = new MidiEvent(b, tickDuration.getTicks() * 2);
            track.add(noteOff);

            sequencer.setSequence(sequence);
            sequencer.start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        finally{
            if(sequencer != null && !sequencer.isRunning())
                sequencer.close();
        }
    }



    public void playSong(String note,long duration){
        String[] noteParts = note.split("_+");
        int finalNote = Integer.parseInt(noteParts[1]) * 12 + extendedNotes.indexOf(noteParts[0]);
        player.play(finalNote,duration);
    }
}
