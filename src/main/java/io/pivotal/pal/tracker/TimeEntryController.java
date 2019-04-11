package io.pivotal.pal.tracker;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TimeEntryController {

    private TimeEntryRepository timeEntryRepository;
    private final DistributionSummary timeEntrySummary;
    private final Counter actionCounter;

    public TimeEntryController(
            TimeEntryRepository timeEntriesRepo,
            MeterRegistry meterRegistry
    ) {
        this.timeEntryRepository = timeEntriesRepo;

        timeEntrySummary = meterRegistry.summary("timeEntry.summary");
        actionCounter = meterRegistry.counter("timeEntry.actionCounter");
    }

    @RequestMapping(value = "/time-entries", method = RequestMethod.POST)
    public ResponseEntity create(@RequestBody TimeEntry timeEntryToCreate) {
        ResponseEntity<TimeEntry> responseEntity = null;
        TimeEntry timeEntry = timeEntryRepository.create(timeEntryToCreate);
        if(timeEntry != null){
            responseEntity = new ResponseEntity<>(timeEntry, HttpStatus.CREATED);
        }
        actionCounter.increment();
        timeEntrySummary.record(timeEntryRepository.list().size());
        return responseEntity;
    }

    @RequestMapping(value = "/time-entries/{id}")
    public ResponseEntity<TimeEntry> read(@PathVariable(name = "id") long timeEntryId) {

        ResponseEntity<TimeEntry> responseEntity = null;
        TimeEntry timeEntry = timeEntryRepository.find(timeEntryId);
        if(timeEntry != null){
            responseEntity = new ResponseEntity<>(timeEntry, HttpStatus.OK);
            actionCounter.increment();
        }else {
            responseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return responseEntity;
    }

    @RequestMapping(value = "/time-entries")
    public ResponseEntity<List<TimeEntry>> list() {

        ResponseEntity<List<TimeEntry>> responseEntity = null;
        List<TimeEntry> timeEntryList = timeEntryRepository.list();
        responseEntity = new ResponseEntity<>(timeEntryList, HttpStatus.OK);
        actionCounter.increment();

        return responseEntity;
    }

    @RequestMapping(value = "/time-entries/{id}", method = RequestMethod.PUT)
    public ResponseEntity update(@PathVariable(name = "id") long timeEntryId, @RequestBody  TimeEntry timeEntry) {

        ResponseEntity<TimeEntry> responseEntity = null;
        TimeEntry updatedTimeEntry = timeEntryRepository.update(timeEntryId,timeEntry);
        if(updatedTimeEntry != null){
            responseEntity = new ResponseEntity<>(updatedTimeEntry, HttpStatus.OK);
            actionCounter.increment();
        }else {
            responseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return responseEntity;
    }

    @RequestMapping(value = "/time-entries/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<TimeEntry> delete(@PathVariable(name = "id") long timeEntryId) {

        ResponseEntity<TimeEntry> responseEntity = null;
        timeEntryRepository.delete(timeEntryId);
        responseEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT);
        actionCounter.increment();
        timeEntrySummary.record(timeEntryRepository.list().size());
        return responseEntity;

    }
}
