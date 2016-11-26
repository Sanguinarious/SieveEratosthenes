/*  
  Copyright (C) 2016 William Welna (wwelna@occultusterra.com)
  
  Permission is hereby granted, free of charge, to any person obtaining a copy
  of this software and associated documentation files (the "Software"), to deal
  in the Software without restriction, including without limitation the rights
  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
  copies of the Software, and to permit persons to whom the Software is
  furnished to do so, subject to the following conditions:

  The above copyright notice and this permission notice shall be included in
  all copies or substantial portions of the Software.

  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
  THE SOFTWARE.
*/

import java.io.FileOutputStream;
import java.nio.ByteBuffer;

public class SieveEratosthenes {
	public static void main(String args[]) throws Exception {
		long timeStart, runtime;
		// Maxint = 536870911   =   4294967295 / 8
		MegaBitMap map = new MegaBitMap(536900000); // Rounded off, just the tip
		long findto = 0xFFFFFFFFl;//4294967295l;
		timeStart = System.currentTimeMillis();
		for(long x=2; x*x<findto; ++x)
			if(!map.get(x))
				for(long y=x; x*y <= findto; ++y)
					map.set(x*y, true);
                runtime = System.currentTimeMillis()-timeStart;
                System.out.println("Finished at "+runtime/1000);
		FileOutputStream ou = new FileOutputStream("SieveEratosthenes.dat", true);
		long count=0;
		for(long x=3; x<=findto; ++x)
			if(!map.get(x)) {
				ou.write(ByteBuffer.allocate(4).putInt((int) x).array());
				count++;
			}
		System.out.println("Primes "+count);
		runtime = System.currentTimeMillis()-timeStart;
		System.out.println("Total Runtime "+runtime/1000);
	}
}
