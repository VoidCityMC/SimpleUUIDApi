package com.voidcitymc.plugins.SimpleUUIDApi;

import org.mapdb.db.DB;
import org.mapdb.DBMaker;

public class Storage {
    public void storeUUID(String username, String uuid) {

    }
    public String getUUID(String username) {
        DB db = DBMaker.fileDB("uuidStorage.db").make();
    }
}
