package ru.crock.app.utils;

import javax.sound.midi.*;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class MidiPlayer {

    private static final int DEFAULT_INSTRUMENT = 0;

    private MidiChannel channel;

    public MidiPlayer() throws MidiUnavailableException {
        this(DEFAULT_INSTRUMENT);
        setInstrument(DEFAULT_INSTRUMENT);
    }

    public MidiPlayer(int instrument) throws MidiUnavailableException {
        channel = getChannel(instrument);
        setInstrument(instrument);
    }

    public void setInstrument(int instrument) {
        channel.programChange(instrument);
    }

    public int getInstrument() {
        return channel.getProgram();
    }

    private void play(final int note) {
        channel.noteOn(note, 127);
    }

    private void release(final int note) {
        channel.noteOff(note);
    }

    public void play(final int note, final long length) {
        play(note);
        System.out.println(length);
        try {
            TimeUnit.MILLISECONDS.sleep(length);
        }
        catch (InterruptedException e){
            //System.out.println("Interrupted");
            Thread.currentThread().interrupt();
            channel.allSoundOff();
            return;
        }
        release(note);
    }

    public void stop() {
        channel.allNotesOff();
    }

    private static MidiChannel getChannel(int instrument) throws MidiUnavailableException {
        Synthesizer synth = MidiSystem.getSynthesizer();
        synth.open();
        synth.loadAllInstruments (synth.getDefaultSoundbank ());
        Instrument[] insts = synth.getLoadedInstruments ();
        MidiChannel channels[] = synth.getChannels ();
        for (int i = 0; i < channels.length; i++) {
            if (channels [i] != null) {
                return channels[i];
            }
        }
        return synth.getChannels()[instrument];
    }
}