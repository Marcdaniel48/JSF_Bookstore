package com.g4w18.customcontrollers;

import com.g4w18.controllers.QuestionJpaController;
import com.g4w18.controllers.exceptions.NonexistentEntityException;
import com.g4w18.controllers.exceptions.RollbackFailureException;
import com.g4w18.entities.Question;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * @author Jephthia
 */
public class CustomQuestionController
{
    @Inject
    private QuestionJpaController questionController;
    
    public void create(Question question) throws RollbackFailureException, Exception
    {
        questionController.create(question);
    }

    public void edit(Question question) throws NonexistentEntityException, RollbackFailureException, Exception
    {
        questionController.edit(question);
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception
    {
        questionController.destroy(id);
    }

    public List<Question> findQuestionEntities()
    {
        return questionController.findQuestionEntities();
    }

    public List<Question> findQuestionEntities(int maxResults, int firstResult)
    {
        return questionController.findQuestionEntities(maxResults, firstResult);
    }

    public Question findQuestion(Integer id)
    {
        return questionController.findQuestion(id);
    }

    public int getQuestionCount()
    {
        return questionController.getQuestionCount();
    }
    
//    public Question getActiveQuestion()
//    {
//        
//    }
}