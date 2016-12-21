// libtest.java      GSN    03 Oct 08; 21 Feb 12; 26 Dec 13


// Vinh T Nguyen
// Version 10/08/2014

import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

interface Functor { Object fn(Object x); }

interface Predicate { boolean pred(Object x); }

public class libtest {

	// ****** your code starts here ******


	public static Integer sumlist(LinkedList<Integer> lst) {
		Integer sum = 0;
		for (Integer item : lst) {
			sum += item;
		}
		return sum;
	}

	public static Integer sumarrlist(ArrayList<Integer> lst) {
		Integer sum = 0;
		for (Integer item : lst) {
			sum += item;
		}
		return sum;
	}

	public static LinkedList<Object> subset (Predicate p,
			LinkedList<Object> lst) {
		LinkedList<Object> newlist = new LinkedList<Object> ();
		for (Object item : lst) {
			if (p.pred(item)) {
				newlist.add(item);
			}
		}
		return newlist;
	}

	public static LinkedList<Object> dsubset (Predicate p,
			LinkedList<Object> lst) {
		for (Iterator<Object> it = lst.iterator(); it.hasNext(); ) {
			if (!p.pred(it.next())) {
				it.remove();
			}
		}
		return lst;
	}

	public static Object some (Predicate p, LinkedList<Object> lst) {
		boolean stop = false;
		Object result = null;
		Iterator<Object> it = lst.iterator();
		while (!stop && it.hasNext()) {
			Object item = it.next();
			if (p.pred(item)) {
				stop = true;
				result = item;
			}
		}
		return result;
	}

	public static LinkedList<Object> mapcar (Functor f, LinkedList<Object> lst) {
		LinkedList<Object> newlist = new LinkedList<Object> ();
		for (Object o : lst) {
			newlist.add(f.fn(o));
		}
		return newlist;
	}


	public static LinkedList<Object> merge (LinkedList<Object> lsta,
			LinkedList<Object> lstb) {
		ListIterator<Object> ita = lsta.listIterator();
		ListIterator<Object> itb = lstb.listIterator();
		LinkedList<Object> newlist = new LinkedList<Object>();
		while (ita.hasNext() || itb.hasNext()) {
			if (!itb.hasNext()) {
				newlist.add(ita.next());
			} else if (!ita.hasNext()) {
				newlist.add(itb.next());
			} else {
				Object itema =  ita.next();
				Object itemb =  itb.next();
				if ( ((Comparable) itema) .compareTo(itemb) < 0) {
					newlist.add(itema);
					itb.previous();
				} else if ( ((Comparable) itema) .compareTo(itemb) > 0) {
					newlist.add(itemb);
					itema = ita.previous();
				} else {
					newlist.add(itema);
					newlist.add(itemb);
				}
			}
		}
		return newlist;
	}


	public static LinkedList<Object> sort (LinkedList<Object> lst) {
		if (lst.size() <= 1) {
			return lst;
		} else {
			LinkedList<Object> lsta = new LinkedList<Object> ();
			LinkedList<Object> lstb = new LinkedList<Object> ();
			sortb(lst, lsta, lstb);
			return merge(sort(lsta), sort(lstb));
		}
	}

	// auxiliary method of sortb, produce 2 new sublists
	private static void sortb(LinkedList<Object> lst,
			LinkedList<Object> lsta, LinkedList<Object> lstb) {
		int center = lst.size() / 2;
		int index = 0;
		for (Object o : lst) {
			if (index < center) {
				lsta.add(o);
			} else {
				lstb.add(o);
			}
			index ++;
		}
	}

	// O(n^2)
//	public static LinkedList<Object> intersection (LinkedList<Object> lsta,
//			LinkedList<Object> lstb) {
//		LinkedList<Object> newlist = new LinkedList<Object> ();
//		for (Object elema : sort(lsta) ) {
//			for (Object elemb : sort(lstb)) {
//				if ( ((Comparable) elema).compareTo(elemb) == 0) {
//					newlist.add(elema);
//				}
//			}
//		}
//		return newlist;
//	}
	
	// O(n) ???
	public static LinkedList<Object> intersection (LinkedList<Object> lsta,
			LinkedList<Object> lstb) {
		LinkedList<Object> newlist = new LinkedList<Object> ();
		lsta = sort(lsta);
		lstb = sort(lstb);
		ListIterator<Object> ita = lsta.listIterator ();
		ListIterator<Object> itb = lstb.listIterator ();
		while (ita.hasNext() || itb.hasNext()) {
			if (!itb.hasNext()) {
				ita.next();
			} else if (!ita.hasNext()) {
				itb.next();
			} else {
				Object itema =  ita.next();
				Object itemb =  itb.next();
				if ( ((Comparable) itema). compareTo(itemb) < 0) {
					itb.previous();
				} else if (((Comparable) itema). compareTo(itemb) > 0) {
					ita.previous();
				} else {
					newlist.addLast(itema);
				}
			}
		}
		return newlist;
	}


	public static LinkedList<Object> reverse (LinkedList<Object> lst) {
		LinkedList<Object> result = new LinkedList<Object> ();
		for (Object o : lst) {
			result.addFirst(o);
		}
		return result;
	}

	// ****** your code ends here ******

	public static void main(String args[]) {
		LinkedList<Integer> lst = new LinkedList<Integer>();
		lst.add(new Integer(3));
		lst.add(new Integer(17));
		lst.add(new Integer(2));
		lst.add(new Integer(5));
		System.out.println("lst = " + lst);
		System.out.println("sum = " + sumlist(lst));

		ArrayList<Integer> lstb = new ArrayList<Integer>();
		lstb.add(new Integer(3));
		lstb.add(new Integer(17));
		lstb.add(new Integer(2));
		lstb.add(new Integer(5));
		System.out.println("lstb = " + lstb);
		System.out.println("sum = " + sumarrlist(lstb));

		final Predicate myp = new Predicate()
		{ public boolean pred (Object x)
		{ return ( (Integer) x > 3); }};

		LinkedList<Object> lstc = new LinkedList<Object>();
		lstc.add(new Integer(3));
		lstc.add(new Integer(17));
		lstc.add(new Integer(2));
		lstc.add(new Integer(5));
		System.out.println("lstc = " + lstc);
		System.out.println("subset = " + subset(myp, lstc));

		System.out.println("lstc     = " + lstc);
		System.out.println("dsubset  = " + dsubset(myp, lstc));
		System.out.println("now lstc = " + lstc);

		LinkedList<Object> lstd = new LinkedList<Object>();
		lstd.add(new Integer(3));
		lstd.add(new Integer(17));
		lstd.add(new Integer(2));
		lstd.add(new Integer(5));
		System.out.println("lstd = " + lstd);
		System.out.println("some = " + some(myp, lstd));

		final Functor myf = new Functor()
		{ public Integer fn (Object x)
		{ return new Integer( (Integer) x + 2); }};

		System.out.println("mapcar = " + mapcar(myf, lstd));

		LinkedList<Object> lste = new LinkedList<Object>();
		lste.add(new Integer(1));
		lste.add(new Integer(3));
		lste.add(new Integer(5));
		lste.add(new Integer(6));
		lste.add(new Integer(9));
		lste.add(new Integer(11));
		lste.add(new Integer(23));
		System.out.println("lste = " + lste);
		LinkedList<Object> lstf = new LinkedList<Object>();
		lstf.add(new Integer(2));
		lstf.add(new Integer(3));
		lstf.add(new Integer(6));
		lstf.add(new Integer(7));
		System.out.println("lstf = " + lstf);
		System.out.println("merge = " + merge(lste, lstf));

		lste = new LinkedList<Object>();
		lste.add(new Integer(1));
		lste.add(new Integer(3));
		lste.add(new Integer(5));
		lste.add(new Integer(7));
		System.out.println("lste = " + lste);
		lstf = new LinkedList<Object>();
		lstf.add(new Integer(2));
		lstf.add(new Integer(3));
		lstf.add(new Integer(6));
		lstf.add(new Integer(6));
		lstf.add(new Integer(7));
		lstf.add(new Integer(10));
		lstf.add(new Integer(12));
		lstf.add(new Integer(17));
		System.out.println("lstf = " + lstf);
		System.out.println("merge = " + merge(lste, lstf));

		LinkedList<Object> lstg = new LinkedList<Object>();
		lstg.add(new Integer(39));
		lstg.add(new Integer(84));
		lstg.add(new Integer(5));
		lstg.add(new Integer(59));
		lstg.add(new Integer(86));
		lstg.add(new Integer(17));
		System.out.println("lstg = " + lstg);

		System.out.println("intersection(lstd, lstg) = "
				+ intersection(lstd, lstg));

		System.out.println("reverse lste = " + reverse(lste));

		System.out.println("sort(lstg) = " + sort(lstg));

	}
}
