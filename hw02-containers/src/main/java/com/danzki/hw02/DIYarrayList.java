package com.danzki.hw02;

import java.util.*;

//DIYarrayList должен имплементировать ТОЛЬКО ОДИН интерфейс - List.
//Если метод не имплементирован, то он должен выбрасывать исключение UnsupportedOperationException.
public class DIYarrayList<T> implements List<T> {

    private int size;
    Object[] elementData;

    //copied from ArrayList
    private static final Object[] EMPTY_ELEMENTDATA = {};
    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;

    private static final int DEFAULT_SIZE = 10;
    private static final int DEFAULT_EMPTY_SIZE = 0;

    public DIYarrayList() {
        this.elementData = EMPTY_ELEMENTDATA;
        this.size = DEFAULT_EMPTY_SIZE;
    }

    public DIYarrayList(int capacity) {
        if (capacity > 0) {
            this.elementData = new Object[capacity];
        }
        else if (capacity == 0) {
            this.elementData = EMPTY_ELEMENTDATA;
        }
        else {
            throw new IllegalArgumentException("Illegal capacity " + capacity);
        }

    }

    private Object[] grow(int minCapacity) {
        Object[] oldData = this.elementData;

        return elementData = Arrays.copyOf(oldData, newCapacity(minCapacity));
    }

    private void ensureCapacity(int minCapacity) {
        if (this.elementData.length < minCapacity) {
            grow(minCapacity);
        }
    }

    @Override
    public boolean add(T t) {
        ensureCapacity(this.size + 1);
        this.elementData[this.size++] = t;
        return true;
    }
    
    private int newCapacity(int minCapacity) {
        int oldCapacity = this.size;
        int newCapacity = (oldCapacity * 3) / 2 + 1;

        if (minCapacity < 0) {
            throw new OutOfMemoryError();
        }

        return (newCapacity - MAX_ARRAY_SIZE < 0) ? newCapacity : MAX_ARRAY_SIZE;
    }

    private class Itr implements ListIterator {
        int cursor;
        int lastRet = -1;

        public Itr(int index) {
            cursor = index;
        }

        @Override
        public boolean hasNext() {
            return (cursor < size);
        }

        @Override
        public boolean hasPrevious() {
            return (cursor > 1);
        }


        @Override
        public Object next() {
            int i = cursor;
            if (i >= size) {
                throw new NoSuchElementException();
            }
            cursor = i + 1;
            return elementData[lastRet = i];
        }

        @Override
        public void set(Object o) {
            if (lastRet < 0) {
                throw new IllegalArgumentException();
            }
            try {
                DIYarrayList.this.set(lastRet, (T) o);
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }

        @Override
        public Object previous() {
            throw new UnsupportedOperationException("Unknown function");
        }

        @Override
        public int nextIndex() {
            throw new UnsupportedOperationException("Unknown function");
        }

        @Override
        public int previousIndex() {
            throw new UnsupportedOperationException("Unknown function");
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Unknown function");
        }

        @Override
        public void add(Object o) {
            throw new UnsupportedOperationException("Unknown function");
        }
    }

    @Override
    public ListIterator<T> listIterator() {
        return new Itr(0);
    }

    @Override
    public T set(int index, T element) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        T oldValue = (T) elementData[index];
        elementData[index] = element;
        return oldValue;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(elementData, size);
    }

    @Override
    public boolean contains(Object o) {
        throw new UnsupportedOperationException("Unknown method");
    }

    @Override
    public Iterator<T> iterator() {
        throw new UnsupportedOperationException("Unknown method");
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        throw new UnsupportedOperationException("Unknown method");
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException("Unknown method");
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException("Unknown method");
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        throw new UnsupportedOperationException("Unknown method");
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        throw new UnsupportedOperationException("Unknown method");
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException("Unknown method");
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException("Unknown method");
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException("Unknown method");
    }

    @Override
    public T get(int index) {
        throw new UnsupportedOperationException("Unknown method");
    }

    @Override
    public void add(int index, T element) {
        throw new UnsupportedOperationException("Unknown method");
    }

    @Override
    public T remove(int index) {
        throw new UnsupportedOperationException("Unknown method");
    }

    @Override
    public int indexOf(Object o) {
        throw new UnsupportedOperationException("Unknown method");
    }

    @Override
    public int lastIndexOf(Object o) {
        throw new UnsupportedOperationException("Unknown method");
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        throw new UnsupportedOperationException("Unknown method");
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException("Unknown method");
    }
}
