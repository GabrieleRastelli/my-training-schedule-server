package com.rastelligualtieri.trainingschedule.server.model;

import javax.persistence.*;

@Entity
@Table( name = "EXERCISE",
        indexes = {
                //@Index(columnList="exerciseId", name="exercise_id_index"),
        })
public class ExerciseEntity {

    @Id
    @Column(nullable = false)
    @GeneratedValue(generator = "SEC_EXERCISE", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "SEC_EXERCISE", sequenceName = "SEC_EXERCISE", allocationSize = 1)
    private Long exerciseId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = true)
    private String category;

    @Column(nullable = true)
    private String equipment;

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    public Long getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(Long exerciseId) {
        this.exerciseId = exerciseId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }


}
