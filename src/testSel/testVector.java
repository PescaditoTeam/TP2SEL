package testSel;

import org.junit.Test;

import org.junit.*;
import sel.VectorMath;

public class testVector {

	@SuppressWarnings("deprecation")
	@Test
	public void testnormaUno(){
		VectorMath vec1 = new VectorMath(3);
		for (int i = 0; i < vec1.getDim(); i++) {
			vec1.setValor(i, i+1);
		}
		Assert.assertEquals(6.0, vec1.normaUno());
	}

}
