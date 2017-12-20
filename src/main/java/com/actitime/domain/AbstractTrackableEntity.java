package com.actitime.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.MappedSuperclass;
import javax.persistence.PostLoad;
import javax.persistence.Transient;
import java.util.*;

@MappedSuperclass
public abstract class AbstractTrackableEntity extends AbstractEntity{
    @JsonIgnore
    @Transient
    protected Map<String, Object> initialValues = new HashMap<>();

    @PostLoad
    private void onPostLoad() throws IllegalAccessException {
        initialValues = getFieldsMap();
    }

    @JsonIgnore
    @Transient
    protected abstract Map<String, Object> getFieldsMap();

    @JsonIgnore
    @Transient
    public List<String> getDirtyFieldNames() {
        List<String> dirtyFieldNames = new ArrayList<>();
        Map<String, Object> newValues = getFieldsMap();
        for (String key : newValues.keySet())
            if (!Objects.equals(newValues.get(key), initialValues.get(key)))
                dirtyFieldNames.add(key);
        return Collections.unmodifiableList(dirtyFieldNames);
    }

}
