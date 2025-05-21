package com.adeo.mcp.server.demo.service;

import com.adeo.mcp.server.demo.model.RunTopic;
import com.adeo.mcp.server.demo.repository.RunTopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RunTopicService {

    private final RunTopicRepository runTopicRepository;

    @Autowired
    public RunTopicService(RunTopicRepository runTopicRepository) {
        this.runTopicRepository = runTopicRepository;
    }

    public List<RunTopic> findAll() {
        return runTopicRepository.findAll();
    }

    public Optional<RunTopic> findById(String id) {
        return runTopicRepository.findById(id);
    }

    public RunTopic save(RunTopic runTopic) {
        return runTopicRepository.save(runTopic);
    }

    public void deleteById(String id) {
        runTopicRepository.deleteById(id);
    }

    public List<RunTopic> findByTicketId(String ticketId) {
        return runTopicRepository.findByTicketId(ticketId);
    }

    public List<RunTopic> findByBusinessUnit(String businessUnit) {
        return runTopicRepository.findByBusinessUnit(businessUnit);
    }

    public List<RunTopic> findByPersonInCharge(String personInCharge) {
        return runTopicRepository.findByPersonInCharge(personInCharge);
    }

    public List<RunTopic> findByModulesConcerned(List<String> modules) {
        return runTopicRepository.findByModulesConcernedIn(modules);
    }

    public List<RunTopic> findByDateRange(LocalDateTime start, LocalDateTime end) {
        return runTopicRepository.findByDateBetween(start, end);
    }

    public List<RunTopic> findByBusinessUnitAndDateRange(String businessUnit, LocalDateTime start, LocalDateTime end) {
        return runTopicRepository.findByBusinessUnitAndDateBetween(businessUnit, start, end);
    }
    
    /**
     * Retrieve the latest RunTopic entries with non-empty resolution procedure
     * 
     * @param limit The maximum number of tickets to return
     * @return List of RunTopic objects, ordered by date descending
     */
    public List<RunTopic> findLatestWithResolutionProcedure(int limit) {
        return runTopicRepository.findTopByResolutionProcedureNotEmptyOrderByDateDesc(
            PageRequest.of(0, limit, Sort.by(Sort.Direction.DESC, "date"))
        );
    }
}
