package io.pivotal.pal.tracker;

import java.util.*;
import java.util.stream.Collectors;

public class InMemoryTimeEntryRepository implements TimeEntryRepository{

    Map<Long, TimeEntry> timeEntryMap = new HashMap<>();
    Long maxId = 0L;

    @Override
    public TimeEntry create(TimeEntry timeEntry) {

        if(!timeEntryMap.isEmpty()) {
            Set<Long> idSet = timeEntryMap.keySet();
            Iterator iterator = idSet.iterator();
            while(iterator.hasNext()){
                Long id = (Long) iterator.next();
                if(id > maxId){
                    maxId = id;
                }
            }
        }
        timeEntry.setId(maxId + 1L);
        timeEntryMap.put(timeEntry.getId(), timeEntry);
        return timeEntry;
    }

    @Override
    public TimeEntry find(Long timeEntryId) {
        TimeEntry timeEntry = timeEntryMap.get(timeEntryId);
        return timeEntry;
    }

    @Override
    public List<TimeEntry> list() {
        return timeEntryMap.values().stream().collect(Collectors.toList());
    }

    @Override
    public TimeEntry update(Long id, TimeEntry timeEntry) {
        TimeEntry existingTimeEntry = timeEntryMap.get(id);

        if(existingTimeEntry != null) {
            existingTimeEntry.setProjectId(timeEntry.getProjectId());
            existingTimeEntry.setHours(timeEntry.getHours());
            existingTimeEntry.setUserId(timeEntry.getUserId());
            existingTimeEntry.setDate(timeEntry.getDate());
        }

        return existingTimeEntry;
    }

    @Override
    public void delete(Long timeEntryId) {
        timeEntryMap.remove(timeEntryId);
        if(timeEntryId > maxId){
            maxId = timeEntryId;
        }
    }
}
