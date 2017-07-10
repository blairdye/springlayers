package net.blairdye.springactor.layers.dao;

import net.blairdye.springactor.layers.service.AskDataSourceService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest(classes=PersonDao.class)
public class PersonDaoTest {
    @Autowired
    private PersonDao personDao;

    @Test
    public void canGetPerson(){
        PersonDao.Person p =personDao.getPerson("123");
        assertEquals("123", p.id);
        assertEquals("My person", p.name);
    }

}