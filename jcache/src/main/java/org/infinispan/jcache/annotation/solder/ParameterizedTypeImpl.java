package org.infinispan.jcache.annotation.solder;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;

public class ParameterizedTypeImpl implements ParameterizedType {

   private final Type[] actualTypeArguments;
   private final Type rawType;
   private final Type ownerType;

   public ParameterizedTypeImpl(Type rawType, Type[] actualTypeArguments, Type ownerType) {
      this.actualTypeArguments = actualTypeArguments;
      this.rawType = rawType;
      this.ownerType = ownerType;
   }

   public Type[] getActualTypeArguments() {
      return Arrays.copyOf(actualTypeArguments, actualTypeArguments.length);
   }

   public Type getOwnerType() {
      return ownerType;
   }

   public Type getRawType() {
      return rawType;
   }

   @Override
   public int hashCode() {
      return Arrays.hashCode(actualTypeArguments) ^ (ownerType == null ? 0 : ownerType.hashCode()) ^ (rawType == null ? 0 : rawType.hashCode());
   }

   @Override
   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (obj instanceof ParameterizedType) {
         ParameterizedType that = (ParameterizedType) obj;
         Type thatOwnerType = that.getOwnerType();
         Type thatRawType = that.getRawType();
         return (ownerType == null ? thatOwnerType == null : ownerType.equals(thatOwnerType)) && (rawType == null ? thatRawType == null : rawType.equals(thatRawType)) && Arrays.equals(actualTypeArguments, that.getActualTypeArguments());
      } else {
         return false;
      }

   }

   @Override
   public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append(rawType);
      if (actualTypeArguments.length > 0) {
         sb.append("<");
         for (Type actualType : actualTypeArguments) {
            sb.append(actualType);
            sb.append(",");
         }
         sb.delete(sb.length() - 1, sb.length());
         sb.append(">");
      }
      return sb.toString();
   }
}
