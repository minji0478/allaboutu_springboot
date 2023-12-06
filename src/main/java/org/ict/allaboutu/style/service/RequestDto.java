package org.ict.allaboutu.style.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestDto {
    private String orgPath;
    private String imagePath;
    private String changePath;
}
