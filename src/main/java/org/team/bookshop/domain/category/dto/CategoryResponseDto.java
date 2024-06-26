package org.team.bookshop.domain.category.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.team.bookshop.domain.category.entity.Category;

@Setter
@Getter
@NoArgsConstructor
public class CategoryResponseDto {

    private Long id;
    private String name;

    @JsonIgnore // 순환참조 방지
    private CategoryResponseDto parent;

    private List<CategoryResponseDto> children;

    @Builder
    public CategoryResponseDto(Long id, String name) {
        this.id = id;
        this.name = name;
        this.children = new ArrayList<>();
    }

    public void setChildren(List<CategoryResponseDto> children) {
        this.children = children;
    }

    public static CategoryResponseDto fromEntity(Category category) {
        CategoryResponseDto dto = new CategoryResponseDto();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setChildren(category.getChildren().stream()
            .map(CategoryResponseDto::fromEntity)
            .collect(Collectors.toList()));
        return dto;
    }

}
