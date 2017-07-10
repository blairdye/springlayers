package net.blairdye.springactor.layers.dao;

import org.springframework.stereotype.Repository;

/**
 * Created by blaird on 7/07/17.
 */
@Repository
public class PersonDao {

    public Person getPerson(String id){
        return new Person(id,"My person");
    }

    public static class Person {
        public String id;
        public String name;
        public Person(String id, String s) {
            this.id = id;
            this.name = s;
        }
    }
}
