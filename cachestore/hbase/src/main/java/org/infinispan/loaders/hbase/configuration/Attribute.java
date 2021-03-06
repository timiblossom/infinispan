package org.infinispan.loaders.hbase.configuration;

import java.util.HashMap;
import java.util.Map;

import org.infinispan.loaders.hbase.HBaseCacheStore;

/**
 * Enumerates the attributes used by the {@link HBaseCacheStore} configuration
 *
 * @author Tristan Tarrant
 * @since 5.2
 */
public enum Attribute {
   // must be first
   UNKNOWN(null),
   AUTO_CREATE_TABLE("autoCreateTable"),
   ENTRY_COLUMN_FAMILY("entryColumnFamily"),
   ENTRY_TABLE("entryTable"),
   ENTRY_VALUE_FIELD("entryValueField"),
   EXPIRATION_COLUMN_FAMILY("expirationColumnFamily"),
   EXPIRATION_TABLE("expirationTable"),
   EXPIRATION_VALUE_FIELD("expirationValueField"),
   HBASE_ZOOKEEPER_QUORUM_HOST("hbaseZookeeperQuorumHost"),
   HBASE_ZOOKEEPER_CLIENT_PORT("hbaseZookeeperClientPort"),
   KEY_MAPPER("keyMapper"),
   SHARED_TABLE("sharedTable"),
   ;

   private final String name;

   private Attribute(final String name) {
      this.name = name;
   }

   /**
    * Get the local name of this element.
    *
    * @return the local name
    */
   public String getLocalName() {
      return name;
   }

   private static final Map<String, Attribute> attributes;

   static {
      final Map<String, Attribute> map = new HashMap<String, Attribute>(64);
      for (Attribute attribute : values()) {
         final String name = attribute.getLocalName();
         if (name != null) {
            map.put(name, attribute);
         }
      }
      attributes = map;
   }

   public static Attribute forName(final String localName) {
      final Attribute attribute = attributes.get(localName);
      return attribute == null ? UNKNOWN : attribute;
   }
}
