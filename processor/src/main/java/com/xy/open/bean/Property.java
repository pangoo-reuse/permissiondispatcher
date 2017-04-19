package com.xy.open.bean;


import org.apache.commons.lang3.StringEscapeUtils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yonehsiung@gmail.com on 2017/4/17.
 */
public final class Property {
    public List<Properties> properties;

    public static class Properties {
        public int grantCode;
        public String denyMessage;
        public String method;
        public LinkedHashMap<String, String> paramPairs;
        public List<String> permissions;
        public boolean invoke;

        @Override
        public final String toString() {
            return "{" +
                    "\"invoke\":" + invoke +
                    ",\"grantCode\":" + grantCode +
                    ",\"denyMessage\":\"" + denyMessage + "\"" +
                    ",\"method\":\"" + method + '\"' +
                    ",\"paramPairs\":" +
                    "[" +
                    buildParams(paramPairs) +
                    "],\"permissions\":" +

                    "[" +
                    buildPermissions(permissions) +
                    "]" +
                    "}";
        }

        private final String buildPermissions(List<String> permissions) {
            StringBuilder permission = new StringBuilder();
            if (permissions != null && permissions.size() != 0) {
                int i = 0;
                for (String perm : permissions) {
                    permission.append("\"");
                    permission.append(perm);
                    permission.append("\"");
                    if (i != permissions.size() - 1) {
                        permission.append(",");
                    }
                    ++i;
                }

            }
            return permission.toString();
        }

        private final String buildParams(Map<String, String> paramPairs) {
            StringBuilder params = new StringBuilder();
            if (paramPairs != null && paramPairs.size() != 0) {
                int i = 0;
                for (Map.Entry<String, String> entry : paramPairs.entrySet()) {
                    params.append("{");
                    params.append("\"")
                            .append(entry.getKey())
                            .append("\"")
                            .append(":")
                            .append("\"")
                            .append(entry.getValue())
                            .append("\"");

                    params.append("}");
                    if (i != paramPairs.size() - 1) {
                        params.append(",");
                    }
                    ++i;
                }

            }


            return params.toString();
        }
    }

    @Override
    public final String toString() {
//        "{[{"grantCode":-1,"denyMessage":"Reject this permission may not work properly","method":"com.xy.open.permission.test.Test.tes","paramPairs":[{\"int\":\"i\"},{\"java.lang.String\":\"m\"},{\"java.lang.Object\":\"object\"}],"permissions":[\"android.permission.WRITE_EXTERNAL_STORAGE\"]}],[{"grantCode":20000,"denyMessage":"Reject this permission may not work properly","method":"com.xy.open.permission.test.Test.test","paramPairs":[{\"int\":\"i\"},{\"java.lang.String\":\"m\"},{\"android.app.Activity\":\"activity\"}],"permissions":[\"android.permission.WAKE_LOCK\",\"android.permission.ACCESS_FINE_LOCATION\"]}]}\";
        StringBuilder builder = new StringBuilder();
        if (properties != null && properties.size() != 0) {
            int i = 0;
            for (Properties property : properties) {
                builder.append(property.toString());
                if (i != properties.size() - 1) {
                    builder.append(",");
                }
                ++i;
            }
        }
        return "\"["+ StringEscapeUtils.escapeJson(builder.toString())+"]\"";
    }
}
