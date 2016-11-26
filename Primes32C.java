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

import java.io.FileInputStream;
import java.math.BigInteger;
import java.util.ArrayList;

public class Primes32C implements AutoCloseable {
	FileInputStream in = null;
	byte[] prime_bytes = new byte[4];
	
	ArrayList<BigInteger> cache = new ArrayList<BigInteger>();
	int position = 0;
	
	static final int HOWMANY=203280220;
	static final long LASTVALUE=4294967291l;
	
	public Primes32C(String path) throws Exception {
		in = new FileInputStream(path);
	}
	
	public long getprime() throws Exception {
		return getBigPrime().longValue();
	}
	
	public BigInteger getBigPrime() throws Exception {
		if((this.position>(cache.size()-1)) || cache.size() == 0)
			for(int x=0; x<100000; ++x)
				cache.add(new BigInteger(Long.toUnsignedString(readprime())));
		if(this.position>HOWMANY)
			throw new Exception("No More Primes");
		return cache.get(this.position++);
	}
	
	public int getCacheSize() {
		return cache.size();
	}
	
	long readprime() throws Exception {
		long r=0;
		if(in.read(prime_bytes)!=-1) {
			r |= (prime_bytes[3]&0xffL);
			r |= (prime_bytes[2]&0xffL)<<8;
			r |= (prime_bytes[1]&0xffL)<<16;
			r |= (prime_bytes[0]&0xffL)<<24;
		return r;
		} else {
			return 0;
		}
	}
	
	public void reset() {
		this.position = 0;
	}
	
	public void close() throws Exception {
		in.close();
	}
}