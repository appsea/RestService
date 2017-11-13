package com.exuberant.rest.survey.persistence;

/**
 * Created by rakesh on 03-Nov-2017.
 */
public class MongoDbService implements DbService{

    @Override
    public boolean isValidUser(){
        return true;
    }
}