package telran.cvbank.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import telran.cvbank.models.Employee;


public interface EmployeeMongoRepository extends MongoRepository<Employee, String> {

}
