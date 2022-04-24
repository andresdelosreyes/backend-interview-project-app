package com.ninjaone.backendinterviewproject.database.repository;

import com.ninjaone.backendinterviewproject.database.model.Sample;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SampleRepository extends CrudRepository<Sample, String> {}
