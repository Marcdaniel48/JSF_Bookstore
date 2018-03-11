package com.g4w18.backingbeans;

import com.g4w18.customcontrollers.CustomQuestionController;
import com.g4w18.entities.Question;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jephthia
 */
@Named
@SessionScoped
public class SurveyBackingBean implements Serializable
{
    @Inject
    private CustomQuestionController surveyController;
    
    private Logger logger = Logger.getLogger(getClass().getName());
    
    private String answer;
//    private static Map<String, Object> answers;
//    static
//    {
//        answers = new LinkedHashMap<String, Object>();
//        answers.put("Color2 - Red", "Red"); //label, value
//        answers.put("Color2 - Green", "Green");
//        answers.put("Color3 - Blue", "Blue");
//    }

    public String getAnswer()
    {
        return answer;
    }
    
    public void setAnswer(String answer)
    {
        this.answer = answer;
    }
    
    public Map<String, Object> getAnswers()
    {
        Map<String, Object> answers = new LinkedHashMap<String, Object>();
        
        Question question = getActiveQuestion();
        String answerOne = question.getAnswerOne();
        String answerTwo = question.getAnswerTwo();
        String answerThree = question.getAnswerThree();
        String answerFour = question.getAnswerFour();
        
        if(answerOne != null && answerOne.length() != 0)
            answers.put(answerOne, "answerOne");
        
        if(answerTwo != null && answerTwo.length() != 0)
            answers.put(answerTwo, "answerTwo");
        
        if(answerThree != null && answerThree.length() != 0)
            answers.put(answerThree, "answerThree");
        
        if(answerFour != null && answerFour.length() != 0)
            answers.put(answerFour, "answerFour");

        return answers;
    }
    
    public Question getActiveQuestion()
    {
        return surveyController.getActiveQuestion();
    }
    
    public void submitVote()
    {
        Question question = getActiveQuestion();
        
        switch(answer)
        {
            case "answerOne":
                question.setVoteOne(question.getVoteOne() + 1);
                break;
                
            case "answerTwo":
                question.setVoteTwo(question.getVoteTwo() + 1);
                break;
                
            case "answerThree":
                question.setVoteThree(question.getVoteThree() + 1);
                break;
                
            case "answerFour":
                question.setVoteFour(question.getVoteFour() + 1);
                break;
        }
        
        try
        {
            surveyController.edit(question);
        }
        catch(Exception e)
        {
            logger.log(Level.INFO, "Question >>> {0} ", question);
            logger.log(Level.INFO, "Couldn't save the question's result.", e);
        }
    }
}