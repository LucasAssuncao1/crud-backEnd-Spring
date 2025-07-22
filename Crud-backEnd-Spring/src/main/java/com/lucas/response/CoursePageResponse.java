package com.lucas.response;

import java.util.List;

public record CoursePageResponse(List<CourseResponse> courses,
                                 int totalPages,
                                 long totalElements
                                ) {
    
}
