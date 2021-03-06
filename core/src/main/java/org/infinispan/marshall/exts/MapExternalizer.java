package org.infinispan.marshall.exts;

import org.infinispan.commons.marshall.AbstractExternalizer;
import org.infinispan.commons.marshall.MarshallUtil;
import org.infinispan.commons.util.FastCopyHashMap;
import org.infinispan.commons.util.Util;
import org.infinispan.marshall.core.Ids;
import org.jboss.marshalling.util.IdentityIntMap;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Map externalizer for all map implementations except immutable maps and singleton maps, i.e. FastCopyHashMap, HashMap,
 * TreeMap.
 *
 * @author Galder Zamarreño
 * @since 4.0
 */
public class MapExternalizer extends AbstractExternalizer<Map> {
   private static final int HASHMAP = 0;
   private static final int TREEMAP = 1;
   private static final int FASTCOPYHASHMAP = 2;
   private final IdentityIntMap<Class<?>> numbers = new IdentityIntMap<Class<?>>(3);

   public MapExternalizer() {
      numbers.put(HashMap.class, HASHMAP);
      numbers.put(TreeMap.class, TREEMAP);
      numbers.put(FastCopyHashMap.class, FASTCOPYHASHMAP);
   }

   @Override
   public void writeObject(ObjectOutput output, Map map) throws IOException {
      int number = numbers.get(map.getClass(), -1);
      output.write(number);
      MarshallUtil.marshallMap(map, output);
   }

   @Override
   public Map readObject(ObjectInput input) throws IOException, ClassNotFoundException {
      int magicNumber = input.readUnsignedByte();
      Map subject = null;
      switch (magicNumber) {
         case HASHMAP:
            subject = new HashMap();
            break;
         case TREEMAP:
            subject = new TreeMap();
            break;
         case FASTCOPYHASHMAP:
            subject = new FastCopyHashMap();
            break;
      }
      MarshallUtil.unmarshallMap(subject, input);
      return subject;
   }

   @Override
   public Integer getId() {
      return Ids.MAPS;
   }

   @Override
   public Set<Class<? extends Map>> getTypeClasses() {
      return Util.<Class<? extends Map>>asSet(
            HashMap.class, TreeMap.class, FastCopyHashMap.class);
   }
}
