// Vinh T Nguyen
// Version 11/18/2014

import java.util.HashMap;

public class Memoizer {
	private HashMap<Object, Object> map;
	private Functor myfun;

	public Memoizer(Functor f) {
		myfun = f;
		map = new HashMap<Object,Object>();
	}

	public Object call(Object x){
		if( !map.containsKey(x)) {
			map.put(x, myfun.fn(x));
		}
		return map.get(x);
	}
}
