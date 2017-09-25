package com.libertymutual.goforcode.makeloans.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.libertymutual.goforcode.makeloans.models.CreditRecord;


public interface CreditRecordRepository extends JpaRepository<CreditRecord, Long> {
	
	// List<CreditRecord> findByClientId(long clientId);
	// List<CreditRecord> findByClientIdAndLineItemIsNull(long clientId);
	// List<CreditRecord> findByIdIn(long[] recordIds);
}
