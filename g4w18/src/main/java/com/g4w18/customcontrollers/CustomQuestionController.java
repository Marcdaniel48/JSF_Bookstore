package com.g4w18.customcontrollers;

import com.g4w18.controllers.QuestionJpaController;
import com.g4w18.controllers.exceptions.NonexistentEntityException;
import com.g4w18.controllers.exceptions.RollbackFailureException;
import com.g4w18.entities.Question;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * @author Jephthia
 */
public class CustomQuestionController
{
    @Inject
    private QuestionJpaController questionController;
    
    @PersistenceContext(unitName = "bookstorePU")
    private EntityManager em;
    
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
    
    public Question getActiveQuestion()
    {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery();
        Root<Question> question = cq.from(Question.class);
        
        cq.select(question);
        cq.where(cb.isTrue(question.get("isActive")));
        
        TypedQuery<Question> query = em.createQuery(cq);
        Question toReturn = query.getSingleResult();
        return toReturn;
    }
}