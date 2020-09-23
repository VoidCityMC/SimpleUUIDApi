package com.voidcitymc.plugins.SimpleUUIDApi;


import org.mapdb.BTreeMap;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.Serializer;

public class Storage {
    public void storeUUID(String username, String uuid) {
        DB db = DBMaker.fileDB("uuidStorage.db").make();
        BTreeMap<String, String> map = db.treeMap("usernameToUUID").keySerializer(Serializer.STRING).valueSerializer(Serializer.STRING).createOrOpen();
        map.put(username, uuid.replaceAll("-",""));
        db.close();
    }
    public String getUUID(String username) {
        DB db = DBMaker.fileDB("uuidStorage.db").make();
        BTreeMap<String, String> map = db.treeMap("usernameToUUID").keySerializer(Serializer.STRING).valueSerializer(Serializer.STRING).createOrOpen();
        String returnValue = map.getOrDefault(username, null);
        if (returnValue != null) {
            returnValue = formatUUID(returnValue);
        }
        db.close();
        return returnValue;
    }
    private String formatUUID(String uuidUnformatted) {
        return uuidUnformatted.replaceFirst("(\\p{XDigit}{8})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}+)", "$1-$2-$3-$4-$5");
    }
}
