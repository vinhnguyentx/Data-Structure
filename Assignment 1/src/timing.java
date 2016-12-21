//       GSN    30 Aug 08; 22 Jan 10
//       Improved by Thomas Finsterbusch

import java.util.Arrays;
import java.util.Random;
import java.lang.reflect.Array;
import java.math.BigInteger;

final class timing {
	public static int fact(int n) {
		return (n <= 0) ? 1 : n * fact(n-1);
	}

	public static long fact(long n) {
		return (n <= 0) ? 1 : n * fact((long) (n-1));
	}

	public static Integer fact(Integer n) {
		return (n <= 0) ? 1 : n * fact((Integer) (n-1));
	}

	public static float fact(float n) {
		return (n <= 0) ? 1 : n * fact(n-1);
	}

	public static double fact(double n) {
		return (n <= 0) ? 1 : n * fact((double) (n-1));
	}


    // recursive factorial, gets stack overflow beyond 8000
	public static BigInteger factr(BigInteger n) {
		if (n.equals(BigInteger.ZERO))
			return BigInteger.ONE;
		else 
			return n.multiply(fact(n.subtract(BigInteger.ONE)));
	}

    // iterative factorial to avoid stack overflow
	public static BigInteger fact(BigInteger n) {
            BigInteger prod = BigInteger.ONE;
            BigInteger mult = BigInteger.ONE;
            int nn = n.intValue();
            for (int i = 0; i < nn; i++ ) {
                prod = prod.multiply(mult);
                mult = mult.add(BigInteger.ONE); }
            return prod;
	}

	public static int daffy(int n) {
		if (n < 2)
			return n;
		else
			return daffy(n-2) + daffy(n-1);
	}

	public static int donald(int n) { 
		return donaldb(0, 1, n);
	}

	public static int donaldb(int lo, int hi, int steps) {
		if (steps == 0)
			return lo;
		else 
			return donaldb(hi, lo+hi, steps-1);
	}
	
	public static int[] randomarr (int n) {
		int[] arr = new int[n];
		int val;
		Random ran = new Random();
		for (int i = 0; i < n; i++) {
			val = ran.nextInt() % n;
			if (val < 0)
				val += n;
			arr[i] = val;
		
		}
		return arr;
	}

	public static void printarr(int[] a) {
		for (int i = 0; i < a.length; i++)
			System.out.print(" " + a[i]);
		System.out.println();
	}

	public static void printIarr(Integer[] a) {
		for (int i = 0; i < a.length; i++)
			System.out.print(" " + a[i]);
		System.out.println();
	}

	public static int mickey(int[] a) {
		int min = a[0];
		for (int i = 0; i < a.length; i++)
			if (a[i] < min)
				min = a[i];
		return min;
	}

	public static int mickeyi(int[] a, int lo) {
		int min = a[lo];
		int mini = lo;
		for (int i = lo; i < a.length; i++)
			if (a[i] < min) { 
				min = a[i];
				mini = i;
			}
		return mini;
	}

	public static void minnie(int[] a) {
		int mini;
		int val;
		for (int i = 0; i < a.length; i++) {
			mini = mickeyi(a, i);
			val = a[mini];
			a[mini] = a[i];
			a[i] = val;
		}
	}

	public static void goofy(int[] a) {
		for (int i = 1; i < a.length; i++) {
			int hole;
			int item = a[i];
			for (hole = i; hole > 0 && item < a[hole - 1]; hole--)
				a[hole] = a[hole-1];
			a[hole] = item;
		}
	}

	public static int elmer(int[] a, int left, int right, int pivot) {
		int tmp;
		int pivotValue = a[pivot];
		a[pivot] = a[right];
		int storeindex = left;
		for (int i = left; i < right; i++) {
			if(a[i] <= pivotValue) {
				tmp = a[storeindex];      // swap
				a[storeindex++] = a[i];
				a[i] = tmp;
			}
		}
		a[right] = a[storeindex];
		a[storeindex] = pivotValue;
		return storeindex;
	}

	public static void pluto(int[] a) {
		plutob(a, 0, a.length - 1);
	}

	public static void plutob(int[] a, int left, int right) {
		if (right > left) {
			int pivot = (left + right) / 2;
			int mid = elmer(a, left, right, pivot);
			plutob(a, left, mid-1);
			plutob(a, mid+1, right);
		}
	}

	public static int gyrob(int [] arr, int low, int high, int goal) {
		if (high < low)
			return -1;       // goal not found
		else { 
			int root = (low + high) / 2;
			if (goal == arr[root])
				return root;     // found
			else if (goal < arr[root])
				return gyrob(arr, low, root-1, goal);
			else 
				return gyrob(arr, root+1, high, goal); 
		}
	}	

        public static int gyro (int [] arr) {
                return gyrob(arr, 0, arr.length, arr.length / 2); }
	
    public static void main(String args[]) {
        // your code here
    	
    	Stopwatch timer = new Stopwatch();
    	
//    	for (int i = 1; i < 400; i ++) {
//    		System.out.println("Factoral of int     " + i + " is: " + fact(i));
//    		System.out.println("Factoral of Integer " + i + " is: " + fact((Integer) i));
//    		System.out.println("Factoral of long    " + i + " is: " + fact((long) i));
//    		System.out.println("Factoral of float   " + i + " is: " + fact((float) i));
//    		System.out.println("Factoral of double  " + i + " is: " + fact((double) i));
//    		System.out.println();
//    	}
    	
//    	long n;
//    	for (int i = 30; i <= 44; i ++) {
//    		timer.start();
//    		n = daffy(i);
//    		timer.stop();
//    		System.out.println(timer);
//    	}
    	
//    	for (int i = 1000; i <= 8192000; i *= 2) {
//    		int[] myArr = randomarr(i);
////    		System.out.println(Arrays.toString(myArr));
//    		timer.start();
//    		for (int j = 0; j < 1000000; j ++) {
//    			mickey(myArr);
//    		}
//    		timer.stop();
//    		System.out.print("Size " + i + " : ");
//    		System.out.println(timer);
//    	}
    	
//    	for (int i = 1000; i <= 128000; i *= 2) {
//    		int[] myArr = randomarr(i);
//    		timer.start();
//    		minnie(myArr);
//    		timer.stop();
//    		System.out.print("Size " + i + " : ");
//    		System.out.println(timer);
//    	}
//    	
//    	for (int i = 1000; i <= 512000; i *= 2) {
//    		int[] myArr = randomarr(i);
//    		timer.start();
//    		goofy(myArr);
//    		timer.stop();
//    		System.out.print("Size " + i + " : ");
//    		System.out.println(timer);
//    	}
    	
//    	for (int i = 1000; i <= 512000; i *= 2) {
//    		int[] myArr = randomarr(i);
//    		timer.start();
//    		pluto(myArr);
//    		timer.stop();
//    		System.out.print("Size " + i + " : ");
//    		System.out.println(timer);
//    	}
    	
//    	for (int i = 1000; i <= 128000; i *= 2) {
//    		int[] myArr = randomarr(i);
////    		System.out.println(Arrays.toString(myArr));
//    		pluto(myArr);
////    		System.out.println(Arrays.toString(myArr));
//    		timer.start();
//    		gyro(myArr);
//    		timer.stop();
//    		System.out.print("Size " + i + " : ");
//    		System.out.println(timer);
//    	}
    	
    	BigInteger result;
    	for (int i = 1000; i <= 512000; i *= 2) {
    		
//    		BigInteger bign = BigInteger.valueOf((long) i);
//    		timer.start();
////    		result = fact(bign);
//    		fact(bign);
//    		timer.stop();
//    		System.out.print("Size " + bign + " : ");
//    		System.out.println(timer);
    		
    		BigInteger bign = BigInteger.valueOf((long) i);
    		timer.start();
    		result = factr(bign);
    		timer.stop();
    		System.out.print("Size " + bign + " : ");
    		System.out.println(timer);
    	}
   }
}
