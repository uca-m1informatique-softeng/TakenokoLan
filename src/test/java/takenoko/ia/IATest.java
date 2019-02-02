package takenoko.ia;

import org.junit.Assert;
import org.junit.Test;

public class IATest {
    @Test
    public void IATest() {
        IA b = IA.newIA(IA.Type.RANDOM);
        Assert.assertTrue(typeIA(b));
        IA j = IA.newIA(IA.Type.PANDA);
        Assert.assertTrue(typeIA(j));
    }

    private boolean typeIA(IA i) {
        return (i instanceof IARandom || i instanceof IAPanda);
    }
}
