/*
 * Generic Tuple class because the Java API is fucking useless
 */

public class Tuple<T1, T2> {
	
	public T1 t1;
	public T2 t2;
	
	public Tuple(T1 t1, T2 t2) {
		this.t1 = t1;
		this.t2 = t2;
	}
	
	public boolean equals(Object o) {
		if (o == null) return false;
		if (!(o instanceof Tuple<?, ?>)) return false;
		
		Tuple<T1, T2> t = (Tuple<T1, T2>) o;
		return this.t1 == t.t1 && this.t2 == t.t2;
	}
}
