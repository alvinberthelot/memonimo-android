package com.webyousoon.android.memonimo;

import android.support.annotation.NonNull;

import com.webyousoon.android.memonimo.model.GameCard;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by hackorder on 13/04/2015.
 */
public class MemoryGame {

    private List<GameCard> mCardGameList = new List<GameCard>() {
        @Override
        public void add(int location, GameCard object) {

        }

        @Override
        public boolean add(GameCard object) {
            return false;
        }

        @Override
        public boolean addAll(int location, Collection<? extends GameCard> collection) {
            return false;
        }

        @Override
        public boolean addAll(Collection<? extends GameCard> collection) {
            return false;
        }

        @Override
        public void clear() {

        }

        @Override
        public boolean contains(Object object) {
            return false;
        }

        @Override
        public boolean containsAll(Collection<?> collection) {
            return false;
        }

        @Override
        public GameCard get(int location) {
            return null;
        }

        @Override
        public int indexOf(Object object) {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @NonNull
        @Override
        public Iterator<GameCard> iterator() {
            return null;
        }

        @Override
        public int lastIndexOf(Object object) {
            return 0;
        }

        @NonNull
        @Override
        public ListIterator<GameCard> listIterator() {
            return null;
        }

        @NonNull
        @Override
        public ListIterator<GameCard> listIterator(int location) {
            return null;
        }

        @Override
        public GameCard remove(int location) {
            return null;
        }

        @Override
        public boolean remove(Object object) {
            return false;
        }

        @Override
        public boolean removeAll(Collection<?> collection) {
            return false;
        }

        @Override
        public boolean retainAll(Collection<?> collection) {
            return false;
        }

        @Override
        public GameCard set(int location, GameCard object) {
            return null;
        }

        @Override
        public int size() {
            return 0;
        }

        @NonNull
        @Override
        public List<GameCard> subList(int start, int end) {
            return null;
        }

        @NonNull
        @Override
        public Object[] toArray() {
            return new Object[0];
        }

        @NonNull
        @Override
        public <T> T[] toArray(T[] array) {
            return null;
        }
    };


    public void MemoryGame(int _numFamily) {

        mCardGameList = GameCard.getRandomList(_numFamily);


    }

}
