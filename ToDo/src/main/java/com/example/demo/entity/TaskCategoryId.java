package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class TaskCategoryId implements Serializable {
    private static final long serialVersionUID = 2260323873694564624L;
    @Column(name = "task_id", nullable = false)
    private Long taskId;

    @Column(name = "category_id", nullable = false)
    private Long categoryId;

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        TaskCategoryId entity = (TaskCategoryId) o;
        return Objects.equals(this.taskId, entity.taskId) &&
                Objects.equals(this.categoryId, entity.categoryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskId, categoryId);
    }

}