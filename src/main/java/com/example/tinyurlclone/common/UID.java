package com.example.tinyurlclone.common;

import com.google.common.primitives.Ints;
import com.google.common.primitives.Longs;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Base64;

// UID is method to generate a virtual unique identifier for whole system
// its structure contains 64 bits:  LocalID - ObjectType - ShardID
// 64 bits for Local ID, max (2^32) - 1
// 32 bits for Object Type

@Getter
@AllArgsConstructor
public class UID {
    private final long localID;
    private final ObjectID objectID;

    public UID(String uid) {
        byte[] bytes = Base64.getDecoder().decode(uid);
        this.localID = Longs.fromByteArray(Arrays.copyOfRange(bytes, 0, 8));
        this.objectID = ObjectID.values()[Ints.fromByteArray(Arrays.copyOfRange(bytes, 8, 12))];
    }

    @Override
    public String toString() {
        byte[] bytes = new byte[12];
        byte[] localIdBytes = Longs.toByteArray(localID);
        System.arraycopy(localIdBytes, 0, bytes, 0, localIdBytes.length);
        byte[] objectIdBytes = Ints.toByteArray(objectID.ordinal());
        System.arraycopy(objectIdBytes, 0, bytes,localIdBytes.length,  objectIdBytes.length);
        return Base64.getEncoder().encodeToString(bytes);
    }
}

