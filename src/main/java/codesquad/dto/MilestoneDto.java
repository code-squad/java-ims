package codesquad.dto;

import javax.validation.constraints.Size;

public class MilestoneDto {

    private Long id;

    @Size(min = 3, max = 20)
    private String title;

    public Long getId() {
        return id;
    }

    public MilestoneDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public MilestoneDto setTitle(String title) {
        this.title = title;
        return this;
    }
}
