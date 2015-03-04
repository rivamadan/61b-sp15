import java.util.AbstractList;

public class ArrayList61B<Lsty> extends AbstractList<Lsty> {
	private int capacity;
	private int length = 0;
	private Lsty[] array;

	public ArrayList61B(int initialCapacity) {
		if (initialCapacity < 1) {
			throw new IllegalArgumentException();
		}
		capacity = initialCapacity;
		array = (Lsty[]) new Object[capacity];
	}

	public ArrayList61B() {
		capacity = 1;
		array = (Lsty[]) new Object[capacity];
	}

	public Lsty get(int i) {
		if (i < 0 || i >= length) {
			throw new IllegalArgumentException();	
		} else {
			return array[i];
		}

	}

	public boolean add(Lsty item) {
		if (length == capacity) {
			capacity = capacity * 2;
			Lsty[] resized = (Lsty[]) new Object[capacity];
			System.arraycopy(array, 0, resized, 0, length);
			array = resized;
		}
		array[length] = item;
		length += 1;
		return true;
	}

	public int size() {
		return length;
	}	
}