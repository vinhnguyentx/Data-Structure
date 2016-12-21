/**
 * this class Cons implements a Lisp-like Cons cell
 * 
 * @author  Gordon S. Novak Jr.
 * @version 29 Nov 01; 25 Aug 08; 05 Sep 08; 08 Sep 08; 12 Sep 08; 24 Sep 08
 *          02 Oct 09; 12 Feb 10; 04 Oct 12
 */

// Vinh Nguyen
// Version 10/02/2014

interface Functor { Object fn(Object x); }

interface Predicate { boolean pred(Object x); }

public class Cons
{
	// instance variables
	private Object car;
	private Cons cdr;
	private Cons(Object first, Cons rest)
	{ car = first;
	cdr = rest; }
	public static Cons cons(Object first, Cons rest)
	{ return new Cons(first, rest); }
	public static boolean consp (Object x)
	{ return ( (x != null) && (x instanceof Cons) ); }
	// safe car, returns null if lst is null
	public static Object first(Cons lst) {
		return ( (lst == null) ? null : lst.car  ); }
	// safe cdr, returns null if lst is null
	public static Cons rest(Cons lst) {
		return ( (lst == null) ? null : lst.cdr  ); }
	public static Object second (Cons x) { return first(rest(x)); }
	public static Object third (Cons x) { return first(rest(rest(x))); }
	public static void setfirst (Cons x, Object i) { x.car = i; }
	public static void setrest  (Cons x, Cons y) { x.cdr = y; }
	public static Cons list(Object ... elements) {
		Cons list = null;
		for (int i = elements.length-1; i >= 0; i--) {
			list = cons(elements[i], list);
		}
		return list;
	}

	// convert a list to a string for printing
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

	// iterative destructive merge using compareTo
	public static Cons dmerj (Cons x, Cons y) {
		if ( x == null ) return y;
		else if ( y == null ) return x;
		else { Cons front = x;
		if ( ((Comparable) first(x)).compareTo(first(y)) < 0)
			x = rest(x);
		else { front = y;
		y = rest(y); };
		Cons end = front;
		while ( x != null )
		{ if ( y == null ||
		((Comparable) first(x)).compareTo(first(y)) < 0)
		{ setrest(end, x);
		x = rest(x); }
		else { setrest(end, y);
		y = rest(y); };
		end = rest(end); }
		setrest(end, y);
		return front; } }

	public static Cons midpoint (Cons lst) {
		Cons current = lst;
		Cons prev = current;
		while ( lst != null && rest(lst) != null) {
			lst = rest(rest(lst));
			prev = current;
			current = rest(current); };
			return prev; }

	// Destructive merge sort of a linked list, Ascending order.
	// Assumes that each list element implements the Comparable interface.
	// This function will rearrange the order (but not location)
	// of list elements.  Therefore, you must save the result of
	// this function as the pointer to the new head of the list, e.g.
	//    mylist = llmergesort(mylist);
	public static Cons llmergesort (Cons lst) {
		if ( lst == null || rest(lst) == null)
			return lst;
		else { Cons mid = midpoint(lst);
		Cons half = rest(mid);
		setrest(mid, null);
		return dmerj( llmergesort(lst),
				llmergesort(half)); } }


	// ****** your code starts here ******
	// add other functions as you wish.

	public static Cons union (Cons x, Cons y) {
		return mergeunion(llmergesort(x), llmergesort(y));
	}

	//following is a helper function for union
	private static Cons mergeunion (Cons x, Cons y) {
		if (x == null) {
			return y;
		} else if (y == null) {
			return x;
		} else 
			if (first(x).equals(first(y))) {
				return cons(first(x), mergeunion(rest(x), rest(y)));
			} else if ( ((Comparable) first(x)) .compareTo(first(y)) < 0) {
				return cons(first(x), mergeunion(rest(x), y));
			} else {
				return cons(first(y), mergeunion(x, rest(y)));
			}
	}

	public static Cons setDifference (Cons x, Cons y) {
		return mergediff(llmergesort(x), llmergesort(y));
	}

	// following is a helper function for setDifference
	public static Cons mergediff (Cons x, Cons y) {
		if (x == null) {
			return null;
		} else if (y == null) {
			return x;
		} else if (first(x).equals(first(y))) {
			return mergediff(rest(x), rest(y));
		} else if ( ((Comparable) first(x)) .compareTo(first(y)) < 0){
			return cons(first(x), mergediff(rest(x), y));
		} else {
			return mergediff(x, rest(y));
		}
	}

	public static Cons bank(Cons accounts, Cons updates) {
		Cons updatesb = llmergesort(updates);
		return bankb(accounts, updatesb);
	}
	
	private static Cons bankb(Cons accounts, Cons updatesb) {
		if (updatesb == null || accounts == null) {
			return accounts;
			
		// if a name in "updates" is present on the "accounts"
		} else if ( ((Account) first(updatesb)).name() .equals( ((Account) first(accounts)).name() )) {
			
			// update account's balance
			setfirst(accounts, updateExistingAcc(((Account) first(accounts)).name(), 
					((Account) first(accounts)).amount(), ((Account) first(updatesb)).amount()));
			
			return accounts = bankb(accounts, rest(updatesb));
			
		// if the name in updates is after the current name in alphabetic order
		} else if ( ((Account) first(updatesb)).name() .compareTo( ((Account) first(accounts)).name()) > 0) {
			return accounts = cons(first(accounts), bankb(rest(accounts), updatesb));
		
		// if the name in updates is before the current name the in alphabetic order
		} else {
			
			if (((Account) first(updatesb)).amount() > 0) {
				// create a new account with positive balance
				Cons newAccount = list(new Account ( ((Account) first(updatesb)).name(), ((Account) first(updatesb)).amount()));
				System.out.println("New account " + ((Account) first(updatesb)).name() + 
						" " + ((Account) first(updatesb)).amount());
				return accounts = cons(first(newAccount), bankb((accounts), rest(updatesb)));
			} else {
				// ignore the account with negative balance
				System.out.println("No account " + ((Account) first(updatesb)).name() + 
						" " + ((Account) first(updatesb)).amount());
				return accounts = bankb((accounts), rest(updatesb));
			}
		}
	}
	
	// helper method, update balance of an account
	private static Account updateExistingAcc (String name, Integer oldamount, Integer newamount) {
		Integer newbalance = oldamount + newamount;
		Integer finalBalance;
		if (newbalance > 0) {
			finalBalance = newbalance;
		} else {
			finalBalance = newbalance - 30;
			System.out.println("Overdraft " + name + " " + finalBalance);
		}
		return new Account(name, finalBalance);
	}
	
	
	public static String [] mergearr(String [] x, String [] y) {
		String [] result = new String [x.length + y.length];
		int k = 0;
		int i = 0;
		int j = 0;
		while (i < x.length  && j < y.length ) {
			if (x[i] .compareTo(y[j]) <= 0) {
				result[k ++] = x[i ++];
			} else {
				result[k ++] = y[j ++];
			}
		}

		// if there's still elements on x
		for (int m = j; m < y.length; m ++) {
			result[k ++] = y[m];
		}

		// if there's still elements on y
		for (int m = i; m < x.length; m ++) {
			result[k ++] = x[m];
		}
		
		return result;
	}
	
	public static boolean markup (Cons text) {
		boolean result = true;
		int index = 0;
		Cons openingstack = list();
		Cons closingstack = list();
		
		for (; text != null; text = rest(text)) {
			index ++;
			String s = (String) first(text);

			// if s is opening tag, add it into stack without braces and slashes
			if ( s.contains("<") && !s.contains("/") ) {
				openingstack = cons (s.substring(1, s.length() - 1), openingstack);	//push
			
			// if s is closing tag, add it into stack without braces and slashes
			} else if ( s.contains("<") && s.contains("/") ) {
				closingstack = cons (s.substring(2, s.length() - 1), closingstack);	//push

				// if opening stack is null
				if ( openingstack == null) {
					result = false;
					System.out.println("Bag tag " + s.substring(2, s.length() - 1) + " at position " 
							+ index + " should be null");
					closingstack = rest(closingstack);	//pop
				// if there is a match
				} else if (first(closingstack).equals(first(openingstack))) {
					openingstack = rest(openingstack);	//pop
					closingstack = rest(closingstack);	//pop
				// searching for bag tag	
				} else if (openingstack != null && closingstack != null) {
					result = false;
					System.out.println("Bag tag " + s.substring(2, s.length() - 1) + " at position " 
							+ index + " should be " + ( (String) first(openingstack)));
					openingstack = rest(openingstack);	//pop
					closingstack = rest(closingstack);	//pop
				}
			}
			
		}
		
		// check opening stack for unbalanced tag, in this casewe just print out the last one
		if ( openingstack != null) {
			result = false;
			System.out.println("Unbalanced tag " + ( ((String) first(openingstack)).toString() ));
		}
		
		// if we want to print out all unbalanced tags in openingstack
//		while ( openingstack != null) {
//			result = false;
//			System.out.println("Unbalanced tag " + ( ((String) first(openingstack)).toString() ));
//			openingstack = rest(openingstack);
//		}
		
		return result;
	}

	// ****** your code ends here ******

	public static void main( String[] args )
	{ 

		Cons set1 = list("d", "b", "c", "a");
		Cons set2 = list("f", "d", "b", "g", "h");
		System.out.println("set1 = " + Cons.toString(set1));
		System.out.println("set2 = " + Cons.toString(set2));
		System.out.println("union = " + Cons.toString(union(set1, set2)));

		Cons set3 = list("d", "b", "c", "a");
		Cons set4 = list("f", "d", "b", "g", "h");
		System.out.println("set3 = " + Cons.toString(set3));
		System.out.println("set4 = " + Cons.toString(set4));
		System.out.println("difference = " +
				Cons.toString(setDifference(set3, set4)));

		Cons accounts = list(
				new Account("Arbiter", new Integer(498)),
				new Account("Flintstone", new Integer(102)),
				new Account("Foonly", new Integer(123)),
				new Account("Kenobi", new Integer(373)),
				new Account("Rubble", new Integer(514)),
				new Account("Tirebiter", new Integer(752)),
				new Account("Vader", new Integer(1024)) );

		Cons updates = list(
				new Account("Foonly", new Integer(100)),
				new Account("Flintstone", new Integer(-10)),
				new Account("Arbiter", new Integer(-600)),
				new Account("Garble", new Integer(-100)),
				new Account("Rabble", new Integer(100)),
				new Account("Flintstone", new Integer(-20)),
				new Account("Foonly", new Integer(10)),
				new Account("Tirebiter", new Integer(-200)),
				new Account("Flintstone", new Integer(10)),
				new Account("Flintstone", new Integer(-120))  );
		        System.out.println("accounts = " + accounts.toString());
		        System.out.println("updates = " + updates.toString());
		        
		        Cons newaccounts = bank(accounts, updates);
		        System.out.println("result = " + newaccounts.toString());
	
		        String[] arra = {"a", "big", "dog", "hippo"};
		        String[] arrb = {"canary", "cat", "fox", "turtle"};
		        String[] resarr = mergearr(arra, arrb);
		        for ( int i = 0; i < resarr.length; i++ )
		        	System.out.println(resarr[i]);
		
		Cons xmla = list( "<TT>", "foo", "</TT>");
		Cons xmlb = list( "<TABLE>", "<TR>", "<TD>", "foo", "</TD>",
				"<TD>", "bar", "</TD>", "</TR>",
				"<TR>", "<TD>", "fum", "</TD>", "<TD>",
				"baz", "</TD>", "</TR>", "</TABLE>" );
		Cons xmlc = list( "<TABLE>", "<TR>", "<TD>", "foo", "</TD>",
				"<TD>", "bar", "</TD>", "</TR>",
				"<TR>", "<TD>", "fum", "</TD>", "<TD>",
				"baz", "</TD>", "</WHAT>", "</TABLE>" );
		Cons xmld = list( "<TABLE>", "<TR>", "<TD>", "foo", "</TD>",
				"<TD>", "bar", "</TD>", "", "</TR>",
				"</TABLE>", "</NOW>" );
		Cons xmle = list( "<THIS>", "<CANT>", "<BE>", "foo", "<RIGHT>" );
		Cons xmlf = list( "<CATALOG>",
				"<CD>",
				"<TITLE>", "Empire", "Burlesque", "</TITLE>",
				"<ARTIST>", "Bob", "Dylan", "</ARTIST>",
				"<COUNTRY>", "USA", "</COUNTRY>",
				"<COMPANY>", "Columbia", "</COMPANY>",
				"<PRICE>", "10.90", "</PRICE>",
				"<YEAR>", "1985", "</YEAR>",
				"</CD>",
				"<CD>",
				"<TITLE>", "Hide", "your", "heart", "</TITLE>",
				"<ARTIST>", "Bonnie", "Tyler", "</ARTIST>",
				"<COUNTRY>", "UK", "</COUNTRY>",
				"<COMPANY>", "CBS", "Records", "</COMPANY>",
				"<PRICE>", "9.90", "</PRICE>",
				"<YEAR>", "1988", "</YEAR>",
				"</CD>", "</CATALOG>");
		        System.out.println("xmla = " + xmla.toString());
		        System.out.println("result = " + markup(xmla));
		        System.out.println("xmlb = " + xmlb.toString());
		        System.out.println("result = " + markup(xmlb));
		        System.out.println("xmlc = " + xmlc.toString());
		        System.out.println("result = " + markup(xmlc));
		        System.out.println("xmld = " + xmld.toString());
		        System.out.println("result = " + markup(xmld));
		        System.out.println("xmle = " + xmle.toString());
		        System.out.println("result = " + markup(xmle));
		        System.out.println("xmlf = " + xmlf.toString());
		        System.out.println("result = " + markup(xmlf));

	}

}
