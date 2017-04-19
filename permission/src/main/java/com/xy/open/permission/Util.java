package com.xy.open.permission;

import com.xy.open.bean.Property;

import com.xy.open.json.JSONArray;
import com.xy.open.json.JSONException;
import com.xy.open.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Created by admin on 2017/4/18.
 */

public class Util {
    public static final Property buildProperty(String json) {
        Property property = new Property();
        try {
            JSONArray jsonArray = new JSONArray(json);
            if (jsonArray != null) {
                if (jsonArray != null &&jsonArray.length() != 0){
                    List<Property.Properties> propertiesArrayList = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);

                        Property.Properties properties = new Property.Properties();
                        boolean invoke = jsonObject.getBoolean("invoke");
                        int grantCode = jsonObject.getInt("grantCode");
                        String denyMessage = jsonObject.getString("denyMessage");
                        String method = jsonObject.getString("method");
                        properties.invoke = invoke;
                        properties.grantCode = grantCode;
                        properties.denyMessage = denyMessage;
                        properties.method = method;

                        JSONArray paramArray = jsonObject.getJSONArray("paramPairs");
                        JSONArray permissionArray = jsonObject.getJSONArray("permissions");
                        LinkedHashMap<String, String> params = new LinkedHashMap<String, String>();
                        if (paramArray != null && paramArray.length() != 0){
                            for (int j = 0; j < paramArray.length(); j++) {
                                JSONObject param = (JSONObject) paramArray.get(j);
                                Set<String> sets = param.keySet();
                                for (String type : sets) {
                                    String filed = (String) param.get(type);
                                    params.put(type,filed);
                                }
                            }

                        }
                        properties.paramPairs = params;
                        List<String> permissList = new LinkedList<String>();
                        if (permissionArray != null && permissionArray.length() != 0){
                            for (int j = 0; j < permissionArray.length(); j++) {
                                String permission = (String) permissionArray.get(j);
                                permissList.add(permission);
                            }
                        }
                        properties.permissions = permissList;
                        propertiesArrayList.add(properties);
                        property.properties = propertiesArrayList;

                    }

                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return property;
    }
}
