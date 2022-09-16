package com.version1.mapper;

import com.version1.pojo.Question;

import java.util.List;

public interface QuestionMapper {
    List<Question> selectAllQuestions();
    Question selectQuestionByID(int id);
}
