package com.futures.knowledge.bean;

/**
 * @ClassName: ProblemBean
 * @Description:
 * @Author: dongxie
 * @CreateDate: 2020/6/29 10:30
 */
public class ProblemBean {
    private int id;
    private String title;
    private int index;
    private String type;
    private String right_answer;
    private String knowledge_point;
    private String explain;
    private String my_answer;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRight_answer() {
        return right_answer;
    }

    public void setRight_answer(String right_answer) {
        this.right_answer = right_answer;
    }

    public String getKnowledge_point() {
        return knowledge_point;
    }

    public void setKnowledge_point(String knowledge_point) {
        this.knowledge_point = knowledge_point;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public String getMy_answer() {
        return my_answer;
    }

    public void setMy_answer(String my_answer) {
        this.my_answer = my_answer;
    }
}
