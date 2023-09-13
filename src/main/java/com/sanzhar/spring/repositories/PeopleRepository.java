package com.sanzhar.spring.repositories;




import com.sanzhar.spring.models.Book;
import com.sanzhar.spring.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {

}
