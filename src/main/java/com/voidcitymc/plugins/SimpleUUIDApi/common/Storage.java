package com.voidcitymc.plugins.SimpleUUIDApi.common;


import org.mapdb.BTreeMap;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.Serializer;

public class Storage {
    public void storeUUID(String username, String uuid) {
        if (uuid.replaceAll("-","").length() == 32 && username.length() <= 16){
            DB db = DBMaker.fileDB("uuidStorage.db").make();
            BTreeMap<String, String> usernameToUUID = db.treeMap("usernameToUUID").keySerializer(Serializer.STRING).valueSerializer(Serializer.STRING).createOrOpen();
            usernameToUUID.put(username.toLowerCase(), uuid.replaceAll("-", ""));
            BTreeMap<String, String> uuidToUsername = db.treeMap("UUIDToUsername").keySerializer(Serializer.STRING).valueSerializer(Serializer.STRING).createOrOpen();
            uuidToUsername.put(uuid.replaceAll("-", ""), username);
            db.close();
        }
    }
    public String getUUID(String username) {
        DB db = DBMaker.fileDB("uuidStorage.db").make();
        BTreeMap<String, String> map = db.treeMap("usernameToUUID").keySerializer(Serializer.STRING).valueSerializer(Serializer.STRING).createOrOpen();
        String returnValue = map.getOrDefault(username.toLowerCase(), null);
        if (returnValue != null) {
            returnValue = formatUUID(returnValue);
        }
        db.close();
        return returnValue;
    }
    public String getUUIDNoDash(String username) {
        DB db = DBMaker.fileDB("uuidStorage.db").make();
        BTreeMap<String, String> map = db.treeMap("usernameToUUID").keySerializer(Serializer.STRING).valueSerializer(Serializer.STRING).createOrOpen();
        String returnValue = map.getOrDefault(username.toLowerCase(), null);
        db.close();
        return returnValue;
    }
    private String formatUUID(String uuidUnformatted) {
        return uuidUnformatted.replaceFirst("(\\p{XDigit}{8})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}+)", "$1-$2-$3-$4-$5");
    }

    public String getUsername(String uuid) {
        DB db = DBMaker.fileDB("uuidStorage.db").make();
        BTreeMap<String, String> map = db.treeMap("UUIDToUsername").keySerializer(Serializer.STRING).valueSerializer(Serializer.STRING).createOrOpen();
        String returnValue = map.getOrDefault(uuid.replaceAll("-",""), null);
        db.close();
        return returnValue;
    }
}
