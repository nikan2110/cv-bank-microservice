package telran.cvbank.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import telran.cvbank.model.Employee;


public interface EmployeeMongoRepository extends MongoRepository<Employee, String> {

}
