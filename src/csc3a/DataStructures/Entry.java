package csc3a.DataStructures;
/**
 * Class for the map entry
 * @param <K>
 * @param <V>
 */
public class Entry<K, V> {

	//Attributes
	private K key;
    private V value;

    /**
     * Constructor of the map entry
     * @param key
     * @param value
     */
     Entry(K key, V value) {
         this.key = key;
         this.value = value;
     }
     
     /**
      * Function to return the value
      * @return
      */
     public V getValue()
     {
    	 return value;
     }
     
     /**
      * Function to return the key
      * @return
      */
     
     public K getKey()
     {
    	 return key;
     }
     /**
      * Function to set the key
      * @return
      */
     
     public void setKey(K key)
     {
    	 this.key = key;
     }
     
     /**
      * Function to set the value and return the old value
      * @return
      */
     public V setValue(V value)
     {
    	 V oldval = this.value;
    	 this.value = value;
    	 
    	 return oldval;
     }
     
     
}
