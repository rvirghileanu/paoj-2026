package com.pao.laboratory05.playlist;

import java.util.Comparator;

/**
 * Comparator extern pentru sortarea melodiilor după durată (crescător).
 */
public class SongDurationComparator implements Comparator<Song> {
    @Override
    public int compare(Song s1, Song s2) {
        // Sortare după durationSeconds crescător.
        // Folosim Integer.compare pentru siguranță.
        return Integer.compare(s1.durationSeconds(), s2.durationSeconds());
    }
}