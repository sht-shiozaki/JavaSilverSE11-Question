public class DNASynth {
	int aCount;
	int tCount;
	int cCount;
	int gCount;
	void setACount(int cCount) {
		cCount = cCount;
	}
	
	void setTCount() {
		this.tCount = tCount;
	}
	
	int setCCount() {
		return cCount;
	}
	
	int setGCount(int g) {
		gCount = g;
		return gCount;
	}
	
	void setAllCounts(int x) {
		aCount = tCount = this.cCount = setGCount(x);
	}
}