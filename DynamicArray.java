// TO DO: add your implementation and JavaDocs.

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * DynamicArray: custom implementation of a dynamic array that resizes automatically. 
 * Custom array.
 * 
 * @param <T> the type of elements stored in the array
 * @author Tyler Youk
 */
public class DynamicArray<T> implements Iterable<T> {
	/**
	 * The default initial capacity of the storage.
	 */
	private static final int INITCAP = 2; 
	/**
	 * The underlying array to store elements.
	 */
	private T[] storage; 
	/**	
	 * The current number of elements stored.
	 */
	private int size;    
	
	/**
	 * Default constructor.
	 */
	@SuppressWarnings("unchecked")
	public DynamicArray(){
		// constructor
		// Initial capacity of the storage should be INITCAP
		storage = (T[]) new Object[INITCAP];
		size = 0;
	}

	/**
	 * Parameterized constructor.
	 * 	 
	 * @param initCapacity the initial capactiy
	 * @throws IllegalArgumentException if initCapacity is less than 1 with message:
	 *         "Capacity cannot be zero or negative."
	 */
	@SuppressWarnings("unchecked")
	public DynamicArray(int initCapacity) {
		// The initial capacity of the storage should be initCapacity
		// Throw IllegalArgumentException if initCapacity is not positive
		if (initCapacity < 1) {
			throw new IllegalArgumentException("Capacity cannot be zero or negative.");
		}
		storage = (T[]) new Object[initCapacity];
		size = 0;
	}

	/**
	 * Current number of elements.
	 * O(1)
	 *
	 * @return the number of elements in the array
	 */
	public int size() {	
		return size;
	}  
		
	/**
	 * Max amount of elements we can store.
	 * O(1)
	 *
	 * @return the current capacity of the storage
	 */
	public int capacity() {
		return storage.length;
	}
	
	/**
	 * Change the item at the index.
	 * Returns the old item at that index.
	 * O(1)
	 *
	 * @param index the index to update
	 * @param value the new value
	 * @return the old value at the specified index
	 * @throws IndexOutOfBoundsException if the index is invalid.
	 */
	public T set(int index, T value) {
		checkIndex(index);
		T old = storage[index];
		storage[index] = value;
		return old;
	}

	/**
	 * Returns the item at the index.
	 * O(1)
	 *
	 * @param index the index of the element to retrieve
	 * @return the element at the given index
	 * @throws IndexOutOfBoundsException if the index is invalid.
	 */
	public T get(int index) {
		checkIndex(index);
		return storage[index];
	}

	/**
	 * Append an element.
	 * Doubles the capacity if theres no space is avialable.
	 * O(1)
	 *
	 * @param value the value to add
	 * @return true after addition 
	 */
	@SuppressWarnings("unchecked")
	public boolean add(T value) {
		if (size == storage.length) {
			expandCapacity();
		}
		storage[size] = value;
		size++;
		return true;
	}
	
	/**
	 * Insert element and shift elements.
	 * O(N) 
	 *
	 * @param index the index at which to insert the value
	 * @param value the value to insert
	 * @throws IndexOutOfBoundsException if the index is invalid.
	 */
	@SuppressWarnings("unchecked")
	public void add(int index, T value) {
		if(index < 0 || index > size) {
			throw new IndexOutOfBoundsException("Index " + index + " out of bounds!");
		}
		if (size == storage.length) {
			expandCapacity();
		}
		// Shift elements to the right to make space.
		for (int i = size - 1; i >= index; i--) {
			storage[i + 1] = storage[i];
		}
		storage[index] = value;
		size++;
	}
	
	
	/**
	 * Remove and return element, then shift elements.
	 * If the size is less than 1/3, then the capacity is shrunk by half.
	 * O(N) worst case
	 *
	 * @param index the index to remove
	 * @return the removed element
	 * @throws IndexOutOfBoundsException if the index is invalid.
	 */
	@SuppressWarnings("unchecked")
	public T remove(int index) {
		checkIndex(index);
		T removed = storage[index];
		// Shift elements left to fill the gap.
		for (int i = index; i < size - 1; i++) {
			storage[i] = storage[i+1];
		}
		storage[size - 1] = null; // Help garbage collection.
		size--;
		
		// Halve capacity if the number of elements is less than or equal to one-third of the capacity.
		if (storage.length > INITCAP && size <= storage.length / 3) {
			shrinkCapacity();
		}
		
		return removed;
	}
	
	/**
	 * Checks whether the param index is valid.
	 *
	 * @param index the index to check
	 * @throws IndexOutOfBoundsException if index is invalid with message: "Index " + index + " out of bounds!"
	 */
	private void checkIndex(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException("Index out of bounds: " + index);
		}
	}

	/**
	 * Doubles the capacity of the storage.
	 */
	@SuppressWarnings("unchecked")
	private void expandCapacity() {
		int newCapacity = storage.length * 2;
		T[] newStorage = (T[]) new Object[newCapacity];
		for (int i = 0; i < storage.length; i++) {
			newStorage[i] = storage[i];
		}
		storage = newStorage;
	}
	
	/**
	 * Shrink to half.
	 */
	@SuppressWarnings("unchecked")
	private void shrinkCapacity() {
		int newCapacity = storage.length / 2;
		// Ensure that capacity does not drop below the initial capacity.
		if(newCapacity < INITCAP) {
			newCapacity = INITCAP;
		}
		T[] newStorage = (T[]) new Object[newCapacity];
		for (int i = 0; i < size; i++) {
			newStorage[i] = storage[i];
		}
		storage = newStorage;
	}
	
	/**
	 * Returns an iterator over the elements in the array.
	 *
	 * @return an Iterator.
	 */
	public Iterator<T> iterator() {
		return new Iterator<>() {
			private int currentIndex = 0;
			
			public boolean hasNext() {
				return currentIndex < size;
			}
			
			public T next() {
				if (!hasNext()) {
					throw new NoSuchElementException();
				}
				return storage[currentIndex++];
			}
		};
	}
	
	//******************************************************
	//*******     BELOW THIS LINE IS TESTING CODE    *******
	//*******      Edit it as much as you'd like!    *******
	//******************************************************
	
	/**
	 * Returns a string representation of the dynamic array for debugging.
	 *
	 * @return a String representation.
	 */
	public String toString() {
		//This method is provided for debugging purposes
		//(use/modify as much as you'd like), it just prints
		//out the list ifor easy viewing.
		StringBuilder s = new StringBuilder("Dynamic array with " + size()
			+ " items and a capacity of " + capacity() + ":");
		for (int i = 0; i < size(); i++) {
			s.append("\n  ["+i+"]: " + get(i));
		}
		return s.toString();
		
	}
	
	/**
	 * Main method for testing the DynamicArray.
	 * Uncomment the iterator test to view iterated values.
	 *
	 * @param args command-line arguments
	 */
	public static void main(String args[]){
		//These are _sample_ tests. If you're seeing all the "yays" that's
		//an excellend first step! But it might not mean your code is 100%
		//working... You may edit this as much as you want, so you can add
		//own tests here, modify these tests, or whatever you need!
		
		DynamicArray<Integer> ida = new DynamicArray<>();
		if ((ida.size() == 0) && (ida.capacity() == 2)){
			System.out.println("Yay 1");
		}

		boolean ok = true;
		for (int i=0; i<3;i++)
			ok = ok && ida.add(i*5);
		
		if (ok && ida.size()==3 && ida.get(2) == 10 && ida.capacity() == 4 ){
			System.out.println("Yay 2");
		}
		
		ida.add(1,-10);
		ida.add(4,100);
		if (ida.set(1,-20)==-10 && ida.get(2) == 5 && ida.size() == 5 
			&& ida.capacity() == 8 ){
			System.out.println("Yay 3");
		}
		
		if (ida.remove(0) == 0 && ida.remove(0) == -20 && ida.remove(2) == 100 
			&& ida.size() == 2 && ida.capacity() == 4 ){
			System.out.println("Yay 4");
		}
		
		//Uncomment this after doing the iterator for testing
		
		System.out.print("Printing values: ");
		for(Integer i : ida) {
			System.out.print(i);
			System.out.print(" ");
		}
		
	}
}