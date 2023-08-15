package com.example.taskqueue.dto;

import jakarta.annotation.Nullable;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TaskDto {
  private String task;

  @Nullable
  private String worker;
}
