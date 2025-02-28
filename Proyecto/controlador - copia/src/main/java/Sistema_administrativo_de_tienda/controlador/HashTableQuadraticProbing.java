package Sistema_administrativo_de_tienda.controlador;

public class HashTableQuadraticProbing<K, V> extends HashTableOpenAddressingBase<K, V> {

	  public HashTableQuadraticProbing() {
	    super();
	  }

	  public HashTableQuadraticProbing(int capacity) {
	    super(capacity);
	  }

	  // Designated constructor
	  public HashTableQuadraticProbing(int capacity, double loadFactor) {
	    super(capacity, loadFactor);
	  }

	  // Given a number this method finds the next
	  // power of two above this value.
	  private static int nextPowerOfTwo(int n) {
	    return Integer.highestOneBit(n) << 1;
	  }

	  // No setup required for quadratic probing.
	  @Override
	  protected void setupProbing(K key) {}

	  @Override
	  protected int probe(int x) {
	    // Quadratic probing function (x^2+x)/2
	    return (x * x + x) >> 1;
	  }

	  // Increase the capacity of the hashtable to the next power of two.
	  @Override
	  protected void increaseCapacity() {
	    capacity = nextPowerOfTwo(capacity);
	  }

	  // Adjust the capacity of the hashtable to be a power of two.
	  @Override
	  protected void adjustCapacity() {
	    int pow2 = Integer.highestOneBit(capacity);
	    if (capacity == pow2) return;
	    increaseCapacity();
	  }
	}
