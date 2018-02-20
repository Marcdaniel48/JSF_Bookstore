/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g4w18.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "question", catalog = "bookstore", schema = "")
@NamedQueries({
    @NamedQuery(name = "Question.findAll", query = "SELECT q FROM Question q")
    , @NamedQuery(name = "Question.findByQuestionId", query = "SELECT q FROM Question q WHERE q.questionId = :questionId")
    , @NamedQuery(name = "Question.findByDescription", query = "SELECT q FROM Question q WHERE q.description = :description")
    , @NamedQuery(name = "Question.findByAnswerOne", query = "SELECT q FROM Question q WHERE q.answerOne = :answerOne")
    , @NamedQuery(name = "Question.findByAnswerTwo", query = "SELECT q FROM Question q WHERE q.answerTwo = :answerTwo")
    , @NamedQuery(name = "Question.findByAnswerThree", query = "SELECT q FROM Question q WHERE q.answerThree = :answerThree")
    , @NamedQuery(name = "Question.findByAnswerFour", query = "SELECT q FROM Question q WHERE q.answerFour = :answerFour")
    , @NamedQuery(name = "Question.findByVoteOne", query = "SELECT q FROM Question q WHERE q.voteOne = :voteOne")
    , @NamedQuery(name = "Question.findByVoteTwo", query = "SELECT q FROM Question q WHERE q.voteTwo = :voteTwo")
    , @NamedQuery(name = "Question.findByVoteThree", query = "SELECT q FROM Question q WHERE q.voteThree = :voteThree")
    , @NamedQuery(name = "Question.findByVoteFour", query = "SELECT q FROM Question q WHERE q.voteFour = :voteFour")})
public class Question implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "QUESTION_ID")
    private Integer questionId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "DESCRIPTION")
    private String description;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "ANSWER_ONE")
    private String answerOne;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "ANSWER_TWO")
    private String answerTwo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "ANSWER_THREE")
    private String answerThree;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "ANSWER_FOUR")
    private String answerFour;
    @Basic(optional = false)
    @NotNull
    @Column(name = "VOTE_ONE")
    private int voteOne;
    @Basic(optional = false)
    @NotNull
    @Column(name = "VOTE_TWO")
    private int voteTwo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "VOTE_THREE")
    private int voteThree;
    @Basic(optional = false)
    @NotNull
    @Column(name = "VOTE_FOUR")
    private int voteFour;

    public Question() {
    }

    public Question(Integer questionId) {
        this.questionId = questionId;
    }

    public Question(Integer questionId, String description, String answerOne, String answerTwo, String answerThree, String answerFour, int voteOne, int voteTwo, int voteThree, int voteFour) {
        this.questionId = questionId;
        this.description = description;
        this.answerOne = answerOne;
        this.answerTwo = answerTwo;
        this.answerThree = answerThree;
        this.answerFour = answerFour;
        this.voteOne = voteOne;
        this.voteTwo = voteTwo;
        this.voteThree = voteThree;
        this.voteFour = voteFour;
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAnswerOne() {
        return answerOne;
    }

    public void setAnswerOne(String answerOne) {
        this.answerOne = answerOne;
    }

    public String getAnswerTwo() {
        return answerTwo;
    }

    public void setAnswerTwo(String answerTwo) {
        this.answerTwo = answerTwo;
    }

    public String getAnswerThree() {
        return answerThree;
    }

    public void setAnswerThree(String answerThree) {
        this.answerThree = answerThree;
    }

    public String getAnswerFour() {
        return answerFour;
    }

    public void setAnswerFour(String answerFour) {
        this.answerFour = answerFour;
    }

    public int getVoteOne() {
        return voteOne;
    }

    public void setVoteOne(int voteOne) {
        this.voteOne = voteOne;
    }

    public int getVoteTwo() {
        return voteTwo;
    }

    public void setVoteTwo(int voteTwo) {
        this.voteTwo = voteTwo;
    }

    public int getVoteThree() {
        return voteThree;
    }

    public void setVoteThree(int voteThree) {
        this.voteThree = voteThree;
    }

    public int getVoteFour() {
        return voteFour;
    }

    public void setVoteFour(int voteFour) {
        this.voteFour = voteFour;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (questionId != null ? questionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Question)) {
            return false;
        }
        Question other = (Question) object;
        if ((this.questionId == null && other.questionId != null) || (this.questionId != null && !this.questionId.equals(other.questionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.g4w18.entities.Question[ questionId=" + questionId + " ]";
    }

}
