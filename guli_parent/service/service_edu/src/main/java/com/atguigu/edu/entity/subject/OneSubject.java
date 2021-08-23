package com.atguigu.edu.entity.subject;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class OneSubject {
    private String id;
    private String title;

    List<TwoSubject> children = new ArrayList<>();
}
