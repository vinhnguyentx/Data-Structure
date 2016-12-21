/**
 * this class Cons implements a Lisp-like Cons cell
 * 
 * @author  Gordon S. Novak Jr.
 * @version 29 Nov 01; 25 Aug 08; 05 Sep 08; 08 Sep 08; 12 Sep 08; 16 Feb 09
 *          01 Feb 12; 08 Feb 12; 22 Sep 13; 26 Dec 13
 */

// Vinh T Nguyen
// Version 09/24/2014
interface Functor { Object fn(Object x); }

interface Predicate { boolean pred(Object x); }

public class Cons
{
	// instance variables
	private Object car;   // traditional name for first
	private Cons cdr;     // "could-er", traditional name for rest
	private Cons(Object first, Cons rest)
	{ car = first;
	cdr = rest; }

	// Cons is the data type.
	// cons() is the method that makes a new Cons and puts two pointers in it.
	// cons("a", null) = (a)
	// cons puts a new thing on the front of an existing list.
	// cons("a", list("b","c")) = (a b c)
	public static Cons cons(Object first, Cons rest)
	{ return new Cons(first, rest); }

	// consp is true if x is a Cons, false if null or non-Cons Object
	public static boolean consp (Object x)
	{ return ( (x != null) && (x instanceof Cons) ); }

	// first returns the first thing in a list.
	// first(list("a", "b", "c")) = "a"
	// safe, first(null) = null
	public static Object first(Cons lst) {
		return ( (lst == null) ? null : lst.car  ); }

	// rest of a list after the first thing.
	// rest(list("a", "b", "c")) = (b c)
	// safe, rest(null) = null
	public static Cons rest(Cons lst) {
		return ( (lst == null) ? null : lst.cdr  ); }

	// second thing in a list
	// second(list("+", "b", "c")) = "b"
	public static Object second (Cons x) { return first(rest(x)); }

	// third thing in a list
	// third(list("+", "b", "c")) = "c"
	public static Object third (Cons x) { return first(rest(rest(x))); }

	// destructively change the first() of a cons to be the specified object
	// setfirst(list("a", "b", "c"), 3) = (3 b c)
	public static void setfirst (Cons x, Object i) { x.car = i; }

	// destructively change the rest() of a cons to be the specified Cons
	// setrest(list("a", "b", "c"), null) = (a)     
	// setrest(list("a", "b", "c"), list("d","e")) = (a d e)
	public static void setrest  (Cons x, Cons y) { x.cdr = y; }

	// make a list of the specified items
	// list("a", "b", "c") = (a b c)
	// list() = null
	public static Cons list(Object ... elements) {
		Cons list = null;
		for (int i = elements.length-1; i >= 0; i--) {
			list = cons(elements[i], list);
		}
		return list;
	}

	// convert a list to a string in parenthesized form for printing
	public String toString() {
		return ( "(" + toStringb(this) ); }
	public static String toString(Cons lst) {
		return ( "(" + toStringb(lst) ); }
	private static String toStringb(Cons lst) {
		return ( (lst == null) ?  ")"
				: ( first(lst) == null ? "()" : first(lst).toString() )
				+ ((rest(lst) == null) ? ")" 
						: " " + toStringb(rest(lst)) ) ); }

	public static int square(int x) { return x*x; }


	// ****** your code starts here ******


	// add up elements of a list of numbers
	public static int sum (Cons lst) {
		return sumb(lst, 0);
	}

	// sum auxiliary method
	public static int sumb (Cons lst, int sum) {
		if (lst == null) {
			return sum;
		} else {
			return sumb(rest(lst), sum + (Integer) first(lst));
		}
	}

	// mean = (sum x[i]) / n  
	public static double mean (Cons lst) {
		return meanb(lst, 0, 0);
	}

	//mean auxiliary method
	private static double meanb(Cons lst, int sum, int n) {
		if (lst == null) {
			return (double) sum/n;
		} else {
			return meanb(rest(lst), (sum + (Integer) first(lst)), n + 1); 
		}
	}

	// square of the mean = mean(lst)^2  

	// mean square = (sum x[i]^2) / n  
	public static double meansq (Cons lst) {
		return meansqb(lst, 0, 0);
	}

	// mean square auxiliary method
	private static double meansqb(Cons lst, double sum, int n) {
		if (lst == null) {
			return (double) (sum/n);
		} else {
			return meansqb(rest(lst), sum + square((Integer) first(lst)), n + 1);
		}
	}

	// variance
	public static double variance (Cons lst) {
		return meansq(lst) - squareb(mean(lst));
	}

	// square double
	// return the square of a double
	private static double squareb(double x) {
		return x*x;
	}

	//standard deviation method
	public static double stddev (Cons lst) {
		return Math.sqrt(variance(lst));
	}

	public static double sine (double x) {
		return sineb(x, 0, 1, 3);
	}

	//sine auxiliary method
	private static double sineb(double x, double sine, int plus, int minus) {
		if (plus > 21 && minus > 21) {
			return sine;
		} else {
			return sineb(x, sine + sinec(x, plus) - sinec(x, minus), plus + 4, minus + 4);
		}
	}

	// sineb auxiliary method
	// return (x^n)/(x!)
	private static double sinec(double x, int n) {
		if (n == 1) {
			return x;
		} else {
			return x / n * sinec(x, n - 1);
		}
	}

	public static Cons nthcdr (int n, Cons lst) {
		if (lst == null) {
			return null;
		} else if (n == 0) {
			return lst;
		} else {
			return nthcdr(n - 1, rest(lst));
		}
	}

	public static Object elt (Cons lst, int n) {
		if (n == 0) {
			return first(lst);
		} else {
			return elt(rest(lst), n - 1);
		}
	}

	public static double interpolate (Cons lst, double x) {
		int base = (int) x;
		double delta = x - base;
		return (Integer) elt(lst, base) + delta * ((Integer) elt(lst, base + 1) - (Integer) elt(lst, base));
	}

	public static int sumtr (Cons lst) {
		return sumtrb(lst, 0);
	}

	// sumtr auxiliary method
	private static int sumtrb(Cons lst, int sum) {
		if (lst == null) {
			return sum;
		} else if (!consp(first(lst))) {
			return sumtrb(rest(lst), sum + (Integer) first(lst));
		} else {
			return sumtrb(rest(lst), sum) + sumtrb((Cons) first(lst), 0);
		}
	}

	
	public static Cons subseq (Cons lst, int start, int end) {
		return subseqb(lst, start, end, null);
	}

	// subseq auxiliary method
	private static Cons subseqb(Cons lst, int start, int end, Cons lstb) {
		if (start == end) {
			return lstb;
		} else {
			return cons(elt(lst, start), subseqb(lst, start + 1, end, lstb));
		}
	}
	

	public static Cons posfilter (Cons lst) {
		return posfilterb(lst, null);
	}

	// posfilter auxiliary method
	private static Cons posfilterb(Cons lst, Cons lstb) {
		if (lst == null) {
			return lstb;
		} else if ((Integer) first(lst) >= 0) {
			return cons(first(lst), posfilterb(rest(lst), lstb));
		} else {
			return posfilterb(rest(lst), lstb);
		}
	}

	
	public static Cons subset (Predicate p, Cons lst) {
		return subsetb(p, lst, null);
	}

	//subset auxiliary method
	private static Cons subsetb (Predicate p, Cons lst, Cons lstb) {
		if (lst == null) {
			return lstb;
		} else if (p.pred(first(lst))) {
			return cons(first(lst), subsetb(p, rest(lst), lstb));		
		} else {
			return subsetb(p, rest(lst), lstb);
		}
	}

	
	public static Cons mapcar (Functor f, Cons lst) {
		return mapcarb(f, lst, null);
	}

	// mapcar auxiliary method
	private static Cons mapcarb (Functor f, Cons lst, Cons lstb) {
		if (lst == null) {
			return lstb;
		} else {
			return cons(f.fn((Integer) first(lst)), mapcarb(f, rest(lst), lstb));
		}
	}

	
	public static Object some (Predicate p, Cons lst) {
			return someb(p, lst, null);
	}

	//sone auxiliary method
	private static Object someb (Predicate p, Cons lst, Cons lstb) {
		if (lst == null) {
			return lstb;
		} else if (p.pred(first(lst))) {
			return first(lst);
		} else {
			return someb(p, rest(lst), lstb);
		}
	}

	
	public static boolean every (Predicate p, Cons lst) {
		if (lst == null) {
			return true;
		} else {
			return everyb(p, lst, false);
		}
	}

	// every auxiliary method
	private static boolean everyb(Predicate p, Cons lst, boolean b) {
		if (lst == null) {
			return b;
		} else if (p.pred(first(lst))) {
			return everyb(p, rest(lst), b);
		} else {
			return false;
		}
	}

	
	// Make a list of Binomial coefficients
	// binomial(2) = (1 2 1)
	public static Cons binomial(int n) {
		return binomialb(list(1), n);
	}

	// binomial auxiliary method
	private static Cons binomialb(Cons lst, int n) {
		if (n == 0) {
			return lst;
		} else {
			return binomialb(cons(1, binomialc(lst, list(1))), n - 1);
		}
	}

	// binomialb auxiliary method
	private static Cons binomialc(Cons lst, Cons lst2) {
		if (rest(lst) == null) {
			return lst2;
		} else {
			return binomialc(rest(lst), cons((Integer) first(lst) + (Integer) first(rest(lst)), lst2));
		}
	}


	// ****** your code ends here ******

	public static void main( String[] args )
	{ 

		Cons mylist =
				list(95, 72, 86, 70, 97, 72, 52, 88, 77, 94, 91, 79,
						61, 77, 99, 70, 91 );
		System.out.println("mylist = " + mylist.toString());
		System.out.println("sum = " + sum(mylist));
		System.out.println("mean = " + mean(mylist));
		System.out.println("meansq = " + meansq(mylist));
		System.out.println("variance = " + variance(mylist));
		System.out.println("stddev = " + stddev(mylist));
		System.out.println("sine(0.5) = " + sine(0.5));
		System.out.print("nthcdr 5 = ");
		System.out.println(nthcdr(5, mylist));
		System.out.print("nthcdr 18 = ");
		System.out.println(nthcdr(18, mylist));
		System.out.println("elt 5 = " + elt(mylist,5));

		Cons mylistb = list(0, 30, 56, 78, 96);
		System.out.println("mylistb = " + mylistb.toString());
		System.out.println("interpolate(3.4) = " + interpolate(mylistb, 3.4));
		Cons binom = binomial(12);
		System.out.println("binom = " + binom.toString());
		System.out.println("interpolate(3.4) = " + interpolate(binom, 3.4));

		Cons mylistc = list(1, list(2, 3), list(list(list(list(4)),
				list(5)),
				6));
		System.out.println("mylistc = " + mylistc.toString());
		System.out.println("sumtr = " + sumtr(mylistc));
		Cons mylistcc = list(1, list(7, list(list(2), 3)),
				list(list(list(list(list(list(list(4)))), 9))),
				list(list(list(list(5), 4), 3)),
				list(6));
		System.out.println("mylistcc = " + mylistcc.toString());
		System.out.println("sumtr = " + sumtr(mylistcc));

		Cons mylistd = list(0, 1, 2, 3, 4, 5, 6 );
		System.out.println("mylistd = " + mylistd.toString());
		System.out.println("subseq(2 5) = " + subseq(mylistd, 2, 5));

		Cons myliste = list(3, 17, -2, 0, -3, 4, -5, 12 );
		System.out.println("myliste = " + myliste.toString());
		System.out.println("posfilter = " + posfilter(myliste));

		final Predicate myp = new Predicate()
		{ public boolean pred (Object x)
		{ return ( (Integer) x > 3); }};

		System.out.println("subset = " + subset(myp, myliste).toString());

		final Functor myf = new Functor()
		{ public Integer fn (Object x)
		{ return  (Integer) x + 2; }};

		System.out.println("mapcar = " + mapcar(myf, mylistd).toString());

		System.out.println("some = " + some(myp, myliste).toString());

		System.out.println("every = " + every(myp, myliste));

	}

}
