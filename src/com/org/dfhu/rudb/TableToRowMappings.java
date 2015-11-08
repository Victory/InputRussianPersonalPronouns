package com.org.dfhu.rudb;

import java.util.HashMap;
import java.util.Map;

public class TableToRowMappings {
    private static final Map<String, String> map = initMap();

    private static Map<String, String> initMap() {
        map.put("test", "TestRow");
        return map;
    }


    public static IRow getInstanceByTableName (String tableName) {
        return getInstanceByName(map.get(tableName));
    }

    private static IRow getInstanceByName(String name) {
        try {
            return (IRow) Class.forName("org.dfhu.rudb." + name).getConstructor(String.class).newInstance();
        } catch (Throwable e) {
            throw new RuntimeException("Internal error");
        }
    }
}
