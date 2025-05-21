package com.adeo.mcp.server.demo.repository;

import com.adeo.mcp.server.demo.model.RunTopic;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RunTopicRepository extends MongoRepository<RunTopic, String> {
    
    List<RunTopic> findByTicketId(String ticketId);
    List<RunTopic> findByBusinessUnit(String businessUnit);
    List<RunTopic> findByPersonInCharge(String personInCharge);
    List<RunTopic> findByModulesConcernedIn(List<String> modules);
    List<RunTopic> findByDateBetween(LocalDateTime start, LocalDateTime end);
    List<RunTopic> findByBusinessUnitAndDateBetween(String businessUnit, LocalDateTime start, LocalDateTime end);
    
    /**
     * Find the last X RunTopic documents with a non-empty resolutionProcedure field.
     * 
     * @param pageable Pageable object to limit results and define sorting order
     * @return List of RunTopic entities matching the criteria
     */
    @Query("{ 'resolutionProcedure': { $ne: null, $ne: '' } }")
    List<RunTopic> findTopByResolutionProcedureNotEmptyOrderByDateDesc(Pageable pageable);
}
