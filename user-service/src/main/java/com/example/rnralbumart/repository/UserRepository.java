package com.example.rnralbumart.repository;

import com.example.rnralbumart.model.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends CrudRepository<UserEntity,Integer> {
}
