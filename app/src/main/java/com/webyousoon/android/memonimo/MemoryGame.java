package com.webyousoon.android.memonimo;

import android.support.annotation.NonNull;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by hackorder on 13/04/2015.
 */
public class MemoryGame {

    private List<CardGame> mCardGameList = new List<CardGame>() {
        @Override
        public void add(int location, CardGame object) {

        }

        @Override
        public boolean add(CardGame object) {
            return false;
        }

        @Override
        public boolean addAll(int location, Collection<? extends CardGame> collection) {
            return false;
        }

        @Override
        public boolean addAll(Collection<? extends CardGame> collection) {
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
        public CardGame get(int location) {
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
        public Iterator<CardGame> iterator() {
            return null;
        }

        @Override
        public int lastIndexOf(Object object) {
            return 0;
        }

        @NonNull
        @Override
        public ListIterator<CardGame> listIterator() {
            return null;
        }

        @NonNull
        @Override
        public ListIterator<CardGame> listIterator(int location) {
            return null;
        }

        @Override
        public CardGame remove(int location) {
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
        public CardGame set(int location, CardGame object) {
            return null;
        }

        @Override
        public int size() {
            return 0;
        }

        @NonNull
        @Override
        public List<CardGame> subList(int start, int end) {
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

        mCardGameList = CardGame.getRandomList(_numFamily);


    }

}
