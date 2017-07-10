package net.blairdye.springactor.layers.service;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * Created by blaird on 7/07/17.
 */
@Service
public class AskDataSourceService {
    public String queryDataSource(String value){
        return value.toUpperCase();
    }
}
