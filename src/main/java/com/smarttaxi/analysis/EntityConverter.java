package com.smarttaxi.analysis;

import com.smarttaxi.data.domain.Call;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Iwan on 04.05.2015
 */

@Service
public class EntityConverter {

    public List<Entity> geEntityList(List<Call> callList) {
        List<Entity> entityList = new ArrayList<>(callList.size());
        for (Call call : callList) {
            entityList.add(getEntity(call));
        }
        return entityList;
    }

    private Entity getEntity(Call call) {
        Entity entity = new Entity();
        entity.setId(call.getId());
        entity.setLat(call.getLat());
        entity.setLon(call.getLon());
        entity.setCluster(call.getCluster());
        return entity;
    }

    public void updateClusters(List<Call> callList, List<Entity> entityList) {
        Map<Long, Call> callMap = getCallMap(callList);
        Map<Long, Entity> entityMap = getEntityMap(entityList);
        for (Long id : entityMap.keySet()) {
            Call call = callMap.get(id);
            if (call != null) {
                call.setCluster(entityMap.get(id).getCluster());
            }
        }
    }

    private Map<Long, Call> getCallMap(List<Call> callList) {
        Map<Long, Call> callMap = new HashMap<>(callList.size());
        for (Call call : callList) {
            callMap.put(call.getId(), call);
        }
        return callMap;
    }

    private Map<Long, Entity> getEntityMap(List<Entity> entityList) {
        Map<Long, Entity> entityMap = new HashMap<>(entityList.size());
        for (Entity entity : entityList) {
            entityMap.put(entity.getId(), entity);
        }
        return entityMap;
    }
}
