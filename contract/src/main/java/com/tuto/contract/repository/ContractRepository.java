package com.tuto.contract.repository;

import com.tuto.contract.entity.Contract;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractRepository extends MongoRepository<Contract, String> {

    Contract getContractById(int id);

}
