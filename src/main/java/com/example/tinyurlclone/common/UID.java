package com.example.tinyurlclone.common;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.google.common.primitives.Ints;
import com.google.common.primitives.Longs;
import com.google.common.primitives.Shorts;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.checkerframework.checker.guieffect.qual.UI;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;

// UID is method to generate a virtual unique identifier for whole system
// its structure contains 64 bits:  LocalID - ObjectType - ShardID
// 64 bits for Local ID, max (2^64) - 1 = 18_446_744_073_709_551_617
// 16 bits for Object Type, max (2^16) -1 = 65535
// 16 bits for Machine Id, max (2^16) -1 = 65535

@Getter
@RequiredArgsConstructor
@JsonSerialize(using = JacksonUIDSerializer.class)
@JsonDeserialize(using = JacksonUIDDeserializer.class)
public class UID {
    private final long localID;
    private final ObjectID objectID;
    private short machineID = 1;

    public UID(String uid) {
        byte[] bytes = Base58.decode(uid);
        this.localID = Longs.fromByteArray(Arrays.copyOfRange(bytes, 0, 8));
        this.objectID = ObjectID.values()[Shorts.fromByteArray(Arrays.copyOfRange(bytes, 8, 10))];
        this.machineID = Shorts.fromByteArray(Arrays.copyOfRange(bytes, 10, 12));
    }

    public static boolean isUID(String s, ObjectID objectID) {
        byte[] bytes = Base58.decode(s);
        if (bytes.length != 12) {
            return false;
        }
        short oidIndex = Shorts.fromByteArray(Arrays.copyOfRange(bytes, 8, 10));
        if (oidIndex >= ObjectID.values().length) {
            return false;
        }
        if (objectID != ObjectID.values()[oidIndex]) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        byte[] bytes = new byte[12];
        byte[] localIdBytes = Longs.toByteArray(localID);
        System.arraycopy(localIdBytes, 0, bytes, 0, localIdBytes.length);
        byte[] objectIdBytes = Shorts.toByteArray((short) objectID.ordinal());
        System.arraycopy(objectIdBytes, 0, bytes,localIdBytes.length,  objectIdBytes.length);
        byte[] machineIdBytes = Shorts.toByteArray(machineID);
        System.arraycopy(machineIdBytes, 0, bytes,localIdBytes.length + objectIdBytes.length, machineIdBytes.length);
        return Base58.encode(bytes);
    }
}

class JacksonUIDSerializer extends StdSerializer<UID> {


    public JacksonUIDSerializer() {
        this(null);
    }
    protected JacksonUIDSerializer(Class<UID> t) {
        super(t);
    }

    @Override
    public void serialize(UID uid, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            jsonGenerator.writeString(uid.toString());
    }
}

class JacksonUIDDeserializer extends StdDeserializer<UID> {

    public JacksonUIDDeserializer() {
        this(null);
    }

    public JacksonUIDDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public UID deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        String id = jsonParser.readValueAs(String.class);
        try {
            return new UID(id);
        } catch (Exception e) {
            throw new JsonMappingException(e.getMessage());
        }
    }
}
