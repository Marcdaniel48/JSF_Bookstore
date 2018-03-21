package com.g4w18.backingbeans;

import com.g4w18.customcontrollers.CustomQuestionController;
import com.g4w18.entities.Question;
import java.io.Serializable;
import java.time.LocalDateTime;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.servlet.http.HttpSession;
import org.primefaces.model.chart.PieChartModel;

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
    private PieChartModel chart;

    @PostConstruct
    public void init()
    {
        //HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        //session.setAttribute("surveyQuestionID", null);
        //initChart();
    }
    
    private void initChart(Question question)
    {
        chart = new PieChartModel();
        
        String answerOne = question.getAnswerOne();
        String answerTwo = question.getAnswerTwo();
        String answerThree = question.getAnswerThree();
        String answerFour = question.getAnswerFour();
        
        if(answerOne != null && answerOne.length() != 0)
            chart.set(answerOne, question.getVoteOne());
        
        if(answerTwo != null && answerTwo.length() != 0)
            chart.set(answerTwo, question.getVoteTwo());
        
        if(answerThree != null && answerThree.length() != 0)
            chart.set(answerThree, question.getVoteThree());
        
        if(answerFour != null && answerFour.length() != 0)
            chart.set(answerFour, question.getVoteFour());
        
        chart.setTitle(question.getDescription());
        chart.setLegendPosition("e");
        chart.setFill(false);
        chart.setShowDataLabels(true);
        chart.setDiameter(150);
    }
    
    public PieChartModel getChart()
    {
        return chart;
    }
    
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
        logger.log(Level.INFO, LocalDateTime.now() + " >>> submit vote");

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
            HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            session.setAttribute("surveyQuestionID", question.getQuestionId());
        }
        catch(Exception e)
        {
            logger.log(Level.INFO, "Question >>> {0} ", question);
            logger.log(Level.INFO, "Couldn't save the question's result.", e);
        }
    }
    
    public boolean showResults()
    {       
        HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        Object questionID = session.getAttribute("surveyQuestionID");
        
        logger.log(Level.INFO, LocalDateTime.now() + " >>> questionID: {0}", questionID);
        
        if(questionID != null)
        {
            Question question = surveyController.findQuestion((int)questionID);
            initChart(question);
            logger.log(Level.INFO, LocalDateTime.now() + " >>> question: {0}", question);
            return true;
        }
        
        return false;
    }
}